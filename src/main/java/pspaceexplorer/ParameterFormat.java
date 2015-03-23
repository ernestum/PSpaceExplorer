package pspaceexplorer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by m on 3/17/15.
 */
public class ParameterFormat {
    public enum Scales {
        Nominal, Ordinal, Interval, Ratio;
    }
    public final int numParameters;
    public final int numNumericalParameters;
    public final Scales[] scales;
    public final String[] names;


    public ParameterFormat(File folder) {
        // Check if this is actually a folder
        if(!folder.isDirectory())
            throw new IllegalArgumentException(folder + " is not a folder!");

        // Create a list of all files in folder ending with .scales
        String[] scaleDescriptionFiles = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".scales");
            }
        });

        // Issue a warning, if there is more than one .scales file in the directory.
        if(scaleDescriptionFiles.length > 1) {
            System.err.printf("Warning: more than one .scales file in %s. %s is used\n", folder, scaleDescriptionFiles[0]);
        }

        // Create a list of all files in folder ending with .names
        String[] nameDescriptionFiles = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".names");
            }
        });

        // Issue a warning, if there is more than one .names file in the directory.
        if(nameDescriptionFiles.length > 1) {
            System.err.printf("Warning: more than one .names file in %s. %s is used\n", folder, nameDescriptionFiles[0]);
        }

        String firstFilename = null;
        for(String filename : folder.list()) {
            if(filename.endsWith(".names") || filename.endsWith(".scales"))
                continue;
            else {
                firstFilename = filename;
                break;
            }
        }


        // Figure out how many parameters we have to deal with. If neither .scales nor .names file is in the directory,
        // the number of parameters is determined from the first file we find in the directory. Otherwise it is first
        // tried to determine it from the .names file and then from the .scales file.
        if(scaleDescriptionFiles.length == 0 && nameDescriptionFiles.length == 0) {
            numParameters = firstFilename.split("_").length;
            System.err.printf("Warning: neither .scales nor .names file found in %s.\nBased on the first file in this directory (%s) %d Parameters are assumed.\n",
                    folder, firstFilename, numParameters);
        } else if (nameDescriptionFiles.length != 0) {
            numParameters = parseNames(nameDescriptionFiles[0]).length;
        } else { //At least there is a scales file
            numParameters = parseScales(scaleDescriptionFiles[0]).length;
        }

        // Figure out what scales to use. Either from the .scales file or by using the default
        if(scaleDescriptionFiles.length > 0) {
            scales = parseScales(scaleDescriptionFiles[0]);
        } else {
            System.err.printf("Warning: no .scales file found in %s. Interval scales are assumed for numbers and nominal scales for strings.\n", folder);
            scales = deduceScales(firstFilename);
        }

        // Figure out what names to use either from the .names file or by generating default names.
        if(nameDescriptionFiles.length > 0) {
            names = parseNames(nameDescriptionFiles[0]);
        } else {
            System.err.printf("Warning: no .names file found in %s. \"Parameter [1..%d]\" is used as names instead.\n", folder, numParameters);
            names = new String[numParameters];
            for(int i = 0; i < numParameters; i++) names[i] = "Parameter " + (i+1);
        }

        if(names.length != scales.length)
            throw new IllegalArgumentException("ERROR: The number of names (" + names.length + ") and the number of scales (" + scales.length + ") as specified by .names and .scales files are not equal.");

        // Figure out how many of the parameters are numeric (which means not nominal)
        int tmpNumericalParams = 0;
        for(Scales s : scales) if(!s.equals(Scales.Nominal)) tmpNumericalParams++;
        numNumericalParameters = tmpNumericalParams;
    }

    public ParameterSet parseFilename(String filename) {
        filename = removeExtension(filename);
        String[] parameterStrings = filename.split("_");
        if(parameterStrings.length != numParameters) throw new IllegalArgumentException("The number of parameters in the file name '" + filename + "' does not match the number of parameters in this parameter format (" + numParameters + ")");
        Object[] parameters = new Object[numParameters];
        for(int i = 0; i < numParameters; i++) {
            if(scales[i] == Scales.Nominal) //Do not convert nominal parameters to numbers
                parameters[i] = parameterStrings[i];
            else
                try {
                    parameters[i] = Double.parseDouble(parameterStrings[i]);
                } catch (NumberFormatException e) {throw new IllegalArgumentException("The number " + parameters[i] + " in the file " + filename + " could not be parsed!"); }
        }
        return new ParameterSet(this, parameters);
    }

    public static Scales[] parseScales(String filename) {
        String[] scalestrings = removeExtension(filename).split("_");
        Scales[] scales = new Scales[scalestrings.length];
        for(int i = 0; i < scalestrings.length; i++) {
            if(scalestrings[i].toLowerCase().equals("nominal")) scales[i] = Scales.Nominal;
            else if(scalestrings[i].toLowerCase().equals("ordinal")) scales[i] = Scales.Ordinal;
            else if(scalestrings[i].toLowerCase().equals("interval")) scales[i] = Scales.Interval;
            else if(scalestrings[i].toLowerCase().equals("ratio")) scales[i] = Scales.Ratio;
            else throw new IllegalArgumentException("Could not parse the scale \'" + scalestrings[i] + "\'. Allowed scales are 'Nominal', 'Ordinal', 'Interval', 'Ratio'");
        }
        return scales;
    }

    /**
     * Tries to deduce the scales of the parameters in a filename. Everything parsable as a double will become Interval scale.
     * All the rest gets to be nominal scale.
     * @param filename The filename to deduce the scales from. The filename should consist of parameters separated by undescores '_'.
     * @return A list of guessed scales.
     */
    public static Scales[] deduceScales(String filename) {
        String[] scalestrings = removeExtension(filename).split("_");
        Scales[] scales = new Scales[scalestrings.length];
        for(int i = 0; i < scalestrings.length; i++) {
            try {
                Double.parseDouble(scalestrings[i]);
                scales[i] = Scales.Interval;
            } catch (NumberFormatException e) {
                scales[i] = Scales.Nominal;
            }
        }
        return scales;
    }

    public static String[] parseNames(String filename) {
        filename = removeExtension(filename);
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(filename.split("_")));
        if(filename.startsWith("_")) names.add(0, "'");
        if(filename.endsWith("_")) names.add("");
        return names.toArray(new String[0]);
    }

    public static String removeExtension(String filename)
    {
        int indexOfPoint = filename.lastIndexOf(".");
        if(indexOfPoint == -1) return filename;
        if(indexOfPoint == 0 || indexOfPoint == filename.length()-1) throw new IllegalArgumentException("Can not remove the extension of this file: " + filename);
        return filename.substring(0, indexOfPoint); // remove extension
    }


}
