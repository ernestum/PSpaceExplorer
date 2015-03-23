package pspaceexplorer;

import java.util.Date;

import controlP5.ControlP5;
import controlP5.Range;

public class SpecialRange extends Range
{
	private long timeStart, timeEnd;
	public SpecialRange(ControlP5 theControlP5, String theName, long timeStart, long timeEnd)
	{
		super(theControlP5, theName);
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		setSize(200,20);
		getValueLabel().align(ControlP5.LEFT, ControlP5.BOTTOM_OUTSIDE).setPaddingX(0);
		setRange(0, timeEnd - timeStart);
		setRangeValues(0, getMax());
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
	
	public long getTimeStart()
	{
		return getTime(getLowValue());
	}
	
	public long getTimeEnd()
	{
		return getTime(getHighValue());
	}

}
