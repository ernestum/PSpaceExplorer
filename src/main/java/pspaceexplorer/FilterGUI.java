package pspaceexplorer;

import controlP5.CheckBox;
import controlP5.ControlP5;
import controlP5.Range;
import processing.core.PApplet;

import static pspaceexplorer.ParameterFormat.Scales.Nominal;

/**
 * Created by m on 3/23/15.
 */
public class FilterGUI {
    public FilterGUI(PApplet parent, ControlP5 cp5, PSpaceImageStore imageStore) {

        float ypos = 20;
        float xpos = 30;
        float margin = 20;
        for(int i = 0; i < imageStore.format.numParameters; i++) {
            if(imageStore.format.scales[i] == Nominal) {
                CheckBox box = cp5.addCheckBox(imageStore.format.names[i]);
                for(Object uniqeValue : imageStore.uniqueValues[i])
                    box.addItem(uniqeValue.toString(), 0);
                ypos += box.setPosition(xpos, ypos).getHeight() + margin;
            }
            else {
                Range range = cp5.addRange(imageStore.format.names[i]);
                range.setMin((float) imageStore.minValues[i]);
                range.setMax((float) imageStore.maxValues[i]);
                range.setNumberOfTickMarks(5);
                range.showTickMarks(true);
                range.snapToTickMarks(true);
                ypos += range.setPosition(xpos, ypos).getHeight() + margin;
            }
        }
    }

}
