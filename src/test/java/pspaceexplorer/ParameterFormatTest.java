package pspaceexplorer;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;
import static pspaceexplorer.ParameterFormat.Scales.*;
import static pspaceexplorer.ParameterFormat.parseNames;
import static pspaceexplorer.ParameterFormat.parseScales;
import static pspaceexplorer.ParameterFormat.removeExtension;

public class ParameterFormatTest {

    @Test
    public void testParseScales() throws Exception {
        assertArrayEquals(new ParameterFormat.Scales[] {Ordinal}, parseScales("orDinal.scales"));
        assertArrayEquals(new ParameterFormat.Scales[] {Ordinal, Nominal}, parseScales("orDinal_nominal.scales"));
        assertArrayEquals(new ParameterFormat.Scales[] {Ratio, Nominal}, parseScales("RATIO_nominal.scales"));
        assertArrayEquals(new ParameterFormat.Scales[] {Ratio, Interval}, parseScales("RATIO_Interval.scales"));
        try{ parseScales("RATIO_somethingstupid.scales"); assertTrue(false);} catch(IllegalArgumentException e) {}
    }

    @Test
    public void testParseNames() throws Exception {
        assertArrayEquals(new String[]{"Name1", "Name2"}, parseNames("Name1_Name2.names"));
        assertArrayEquals(new String[] {"Name1.", "Name2"}, parseNames("Name1._Name2.names"));
        assertArrayEquals(new String[] {"Name 1", "Name 2"}, parseNames("Name 1_Name 2.names"));
        assertArrayEquals(new String[] {"Name 1"}, parseNames("Name 1.names"));
        assertArrayEquals(new String[] {" "}, parseNames(" .names"));
        assertArrayEquals(new String[]{"Name1", ""}, parseNames("Name1_.names"));
    }

    @Test
    public void testRemoveExtension() throws Exception {
        assertEquals("filename", removeExtension("filename.txt"));
        assertEquals("filename.txt", removeExtension("filename.txt.bmp"));
        assertEquals("noextension", removeExtension("noextension"));
        assertEquals(".hidden", removeExtension(".hidden.jpg"));
        try { removeExtension(".hidden_without_extension"); assertTrue(false); } catch (IllegalArgumentException e) {}
        try { removeExtension("."); assertTrue(false); } catch (IllegalArgumentException e) {}
        try { removeExtension(".."); assertTrue(false); } catch (IllegalArgumentException e) {}
    }

    @Test
    public void testParseFilename() throws Exception {
        for(String sandbox : new String[] {"nometadata", "onlynames", "onlyscale"}) {
            // First load the folder
            String folder = "src/test/java/pspaceexplorer/parameterformat_sandbox/" + sandbox;
            System.err.println(folder);
            ParameterFormat format = new ParameterFormat(new File(folder));
            System.err.println();

            //Check if names are as expected
            if(sandbox.equals("nometadata") || sandbox.equals("onlyscale")) { //when names are not specified
                assertArrayEquals(new String[]{"Parameter 1", "Parameter 2", "Parameter 3", "Parameter 4"}, format.names);
            } else if(sandbox.equals("onlynames")) {
                assertArrayEquals(new String[] {"Colorcity", "Temperature", "Stupidness", "Larger"}, format.names);
            }

            //Check if scales are as expected
            if(sandbox.equals("nometadata") || sandbox.equals("onlynames")) {
                assertArrayEquals(new ParameterFormat.Scales[] {Nominal, Interval, Interval, Interval}, format.scales);
            } else if(sandbox.equals("onlyscale")) {
                assertArrayEquals(new ParameterFormat.Scales[] {Nominal, Ordinal, Interval, Ratio}, format.scales);
            }

            //Check if the files are parsed as expected
            assertEquals(new ParameterSet(format, new Object[] {"Blue", 4d, 3.3d, 10e3d}),
                         format.parseFilename("Blue_4_3.3_10e3.jpg"));
            assertEquals(new ParameterSet(format, new Object[] {"London", -8d, -3.3d, 10e3d}),
                    format.parseFilename("London_-8_-3.3_10e3.jpg"));
            assertEquals(new ParameterSet(format, new Object[] {"London", 8d, 3.3d, 10e3d}),
                    format.parseFilename("London_8_3.3_10e3.jpg"));
        }
    }

    @Test
    public void testDeduceScales() throws Exception {
        assertArrayEquals(new ParameterFormat.Scales[]{Nominal, Interval, Interval, Interval}, ParameterFormat.deduceScales("London_8_3.3_10e3.jpg"));
        assertArrayEquals(new ParameterFormat.Scales[]{Nominal, Interval, Interval, Interval}, ParameterFormat.deduceScales("Blue_4_3.3_10e3.jpg"));
        assertArrayEquals(new ParameterFormat.Scales[]{Nominal, Interval, Interval, Interval}, ParameterFormat.deduceScales("London_-8_-3.3_10e3.jpg"));
        assertArrayEquals(new ParameterFormat.Scales[]{Interval, Nominal, Interval, Nominal, Interval}, ParameterFormat.deduceScales("-8_Haus_-3.3_Hund_10e3.jpg"));
    }
}