package pspaceexplorer;

import controlP5.ControlP5;
import processing.core.PApplet;

import java.io.File;

/**
 * Created by m on 3/23/15.
 */
public class FilterGUITestbed extends PApplet {
    @Override
    public void setup() {
        size((int)(1000*1.3), (int)(707*1.3));
        new FilterGUI(this, new ControlP5(this), new PSpaceImageStore(this, new File("src/test/java/pspaceexplorer/parameterformat_sandbox/onlynames")));
    }

    @Override
    public void draw() {
        g.background(80);
    }

    public static void main(String[] args)
	{
		PApplet.main(new String[]{FilterGUITestbed.class.getCanonicalName()});
	}
}
