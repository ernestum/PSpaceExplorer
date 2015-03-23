package pspaceexplorer;

import controlP5.ControlP5;
import controlP5.Slider;

import java.util.Date;

public class SpecialSlider extends Slider
{
	private long timeStart, timeEnd;
	
	public SpecialSlider(ControlP5 theControlP5, String theName, long timeStart, long timeEnd)
	{
		super(theControlP5, theName);
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		setSize(200,20);
		getValueLabel().align(ControlP5.LEFT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(0);
		setRange(0, timeEnd - timeStart);
	}

	@Override
	protected String adjustValue(final float theValue) 
	{
		
		return new Date(getTime(theValue)).toString();
	}
	
	public long getTime()
	{
		return getTime(getValue());
	}
	
	public long getTime(float value)
	{
		long asLong = (long) value;
		return timeStart + asLong;
	}

}
