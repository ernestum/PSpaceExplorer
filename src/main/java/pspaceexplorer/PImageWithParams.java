package pspaceexplorer;

import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;

public class PImageWithParams
{
	private PImage image;
	public final ParameterSet params;

	public PImageWithParams(ParameterSet params, File file, PApplet parent)
	{
		this.image = parent.loadImage(file.getAbsolutePath()); //TODO Dynamic loading?
		this.params = params;
	}

	public PImage getImage() {
		return image;
	}
}
