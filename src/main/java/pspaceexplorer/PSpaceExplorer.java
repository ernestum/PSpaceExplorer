//package pspaceexplorer;
//
//import java.awt.print.PageFormat;
//import java.text.DateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map.Entry;
//import java.util.SortedSet;
//import java.util.TreeSet;
//
//import processing.core.PApplet;
//import processing.core.PFont;
//import processing.core.PGraphics;
//import processing.core.PImage;
//import processing.core.PVector;
//import controlP5.ControlP5;
//
//public class PSpaceExplorer extends PApplet
//{
//	public float model;
//	public float attP1;
//	public float attP2;
//	public float rodL1;
//	public float rodL2;
//	public float speed;
//	public float time;
//
//	public float poster_image_width;
//
//	public float x_axis_ordinal, y_axis_ordinal;
//
//	PSpaceImageStore imageStore;
//	ControlP5 cp5;
//	public void setup()
//	{
//		size((int)(1000*1.3), (int)(707*1.3));
//
//		imageStore = new PSpaceImageStore(this, "testimages");
//
//		cp5 = new ControlP5(this);
////		cp5.setFont(new PFont(PFont.findFont("Helvetica"), true), 8);
//		g.textFont(new PFont(PFont.findFont("Helvetica"), true), 48);
//
//		cp5.addTab("Browsing");
//
//		cp5.addSlider("model").linebreak()
//							  .setRange(imageStore.getParamMin(0), imageStore.getParamMax(0))
//							  .moveTo("Browsing");
//
//
//		cp5.addSlider("attP1").linebreak()
//							  .setRange(imageStore.getParamMin(1), imageStore.getParamMax(1))
//							  .moveTo("Browsing");
//
//		cp5.addSlider("attP2").linebreak()
//		  					  .setRange(imageStore.getParamMin(2), imageStore.getParamMax(2))
//		  					  .moveTo("Browsing");
//
//		cp5.addSlider("rodL1").linebreak()
//		  					  .setRange(imageStore.getParamMin(3), imageStore.getParamMax(3))
//		  					  .moveTo("Browsing");
//
//		cp5.addSlider("rodL2").linebreak()
//		  					  .setRange(imageStore.getParamMin(4), imageStore.getParamMax(4))
//		  					  .moveTo("Browsing");
//
//		cp5.addSlider("speed").linebreak()
//		  					  .setRange(imageStore.getParamMin(5), imageStore.getParamMax(5))
//		  					  .moveTo("Browsing");
//
//
//
//		new SpecialSlider(cp5, "time", System.currentTimeMillis(), System.currentTimeMillis() + 1000*60*60*24*14)
//		.setPosition(10, 200)
//		.moveTo("Poster");
//
//
//
//
//		cp5.addTab("Poster").setActive(true);
//
//		cp5.addSlider("poster_image_width")
//			.setPosition(10, 30)
//			.setRange(0.03f, 0.2f)
//			.setValue(0.01f)
//			.linebreak()
//			.moveTo("Poster");
//
//		cp5.addSlider("x_axis_ordinal")
//			.setPosition(10, 50)
//			.setRange(0, 1)
//			.moveTo("Poster");
//
//		cp5.addSlider("y_axis_ordinal")
//			.setPosition(10, 70)
//			.setRange(0, 1)
//			.moveTo("Poster");
//
//		cp5.addDropdownList("x_axis_param")
//			.setPosition(10, 100)
//			.addItems(imageStore.parameterNames)
//			.moveTo("Poster");
//
//		cp5.addDropdownList("y_axis_param")
//		.setPosition(10, 120)
//		.addItems(imageStore.parameterNames)
//		.moveTo("Poster");
//
//
//		cp5.addRange("modelRange")
//			.setPosition(300, 30)
//		  .setRange(imageStore.getParamMin(0), imageStore.getParamMax(0))
//		  .setRangeValues(imageStore.getParamMin(0), imageStore.getParamMax(0))
//		  .moveTo("Poster");
//
//		cp5.addRange("attP1Range")
//			.setPosition(300, 50)
//		  .setRange(imageStore.getParamMin(1), imageStore.getParamMax(1))
//		  .setRangeValues(imageStore.getParamMin(1), imageStore.getParamMax(1))
//		  .moveTo("Poster");
//
//		cp5.addRange("attP2Range")
//			.setPosition(300, 70)
//		  .setRange(imageStore.getParamMin(2), imageStore.getParamMax(2))
//		  .setRangeValues(imageStore.getParamMin(2), imageStore.getParamMax(2))
//		  .moveTo("Poster");
//
//		cp5.addRange("rodL1Range")
//			.setPosition(300, 90)
//		  .setRange(imageStore.getParamMin(3), imageStore.getParamMax(3))
//		  .setRangeValues(imageStore.getParamMin(3), imageStore.getParamMax(3))
//		  .moveTo("Poster");
//
//		cp5.addRange("rodL2Range")
//			.setPosition(300, 110)
//		  .setRange(imageStore.getParamMin(4), imageStore.getParamMax(4))
//		  .setRangeValues(imageStore.getParamMin(4), imageStore.getParamMax(4))
//		  .moveTo("Poster");
//
//		cp5.addRange("speedRange")
//			.setPosition(300, 130)
//		  .setRange(imageStore.getParamMin(5), imageStore.getParamMax(5))
//		  .setRangeValues(imageStore.getParamMin(5), imageStore.getParamMax(5))
//		  .moveTo("Poster");
//
//
//		new SpecialRange(cp5, "timeRange", imageStore.getParamMin(6), imageStore.getParamMax(6))
//		.setPosition(300,  150)
//		.moveTo("Poster")
//		;
//
//		cp5.addRange("xRange")
//			.setPosition(300, 190)
//			  .setRange(imageStore.getParamMin(7), imageStore.getParamMax(7))
//			  .setRangeValues(imageStore.getParamMin(7), imageStore.getParamMax(7))
//			  .moveTo("Poster");
//
//		cp5.addRange("yRange")
//		.setPosition(300, 220)
//		  .setRange(imageStore.getParamMin(8), imageStore.getParamMax(8))
//		  .setRangeValues(imageStore.getParamMin(8), imageStore.getParamMax(8))
//		  .moveTo("Poster");
//
//		cp5.addButton("exportPoster")
//			.setPosition(width/2, 50)
//			.moveTo("Poster");
//
//
//		cp5.addSlider("time").setPosition(10, 130)
//		  					  .setRange(imageStore.getParamMin(6), imageStore.getParamMax(6));
//	}
//
//	public void draw()
//	{
//		g.background(80);
//		if(cp5.getTab("Browsing").isActive())
//			browse();
//
//		if(cp5.getTab("Poster").isActive())
//			make_poster();
//	}
//
//	public void browse()
//	{
//		long[] params = {(long)model, (long)attP1, (long)attP2, (long)rodL1, (long)rodL2, (long)speed, 0, 0, 0};
//		PImageWithParams image = imageStore.getImage(params);
//		image(image.image, 0, 0, width);
//		noStroke();
//		double visibility = PSpaceImageStore.dist(image.params, params);
////		fill(255, 255, 255, (int) visibility);
//		fill(255, 255, 255, 0);
//		rect(0, 0, width, height);
//	}
//
//	public void make_poster()
//	{
//		double[] lowerKey = {
//				(long)(cp5.getController("modelRange").getArrayValue(0)),
//				(long)(cp5.getController("attP1Range").getArrayValue(0)),
//				(long)(cp5.getController("attP2Range").getArrayValue(0)),
//				(long)(cp5.getController("rodL1Range").getArrayValue(0)),
//				(long)(cp5.getController("rodL2Range").getArrayValue(0)),
//				(long)(cp5.getController("speedRange").getArrayValue(0)),
//				(long)(((SpecialRange)cp5.getController("timeRange")).getTimeStart()) - 1,
//				(long)(cp5.getController("xRange").getArrayValue(0))  + 1,
//				(long)(cp5.getController("yRange").getArrayValue(0))  + 1
//		};
//
//		double[] upperKey = {
//				(long)(cp5.getController("modelRange").getArrayValue(1)),
//				(long)(cp5.getController("attP1Range").getArrayValue(1))  + 1,
//				(long)(cp5.getController("attP2Range").getArrayValue(1))  + 1,
//				(long)(cp5.getController("rodL1Range").getArrayValue(1))  + 1,
//				(long)(cp5.getController("rodL2Range").getArrayValue(1))  + 1,
//				(long)(cp5.getController("speedRange").getArrayValue(1))  + 1,
//				(long)(((SpecialRange)cp5.getController("timeRange")).getTimeEnd()) + 1,
//				(long)(cp5.getController("xRange").getArrayValue(1))  + 1,
//				(long)(cp5.getController("yRange").getArrayValue(1))  + 1
//		};
//		println(imageStore.all().size());
//		make_poster(g, 4, 3, poster_image_width, imageStore.all());
//
//		make_poster(g, (int)cp5.getGroup("x_axis_param").getValue(), (int)cp5.getGroup("y_axis_param").getValue(), width*poster_image_width, imageStore.range(lowerKey, upperKey));
//		for(PImageWithParams image : imageStore.all())
//		{
//			int xParam = 5;
//			int yParam = 3;
//			int x = (int)map(image.params[xParam], imageStore.getParamMin(xParam), imageStore.getParamMax(xParam), 0, (float) (width - poster_image_width*2));
//			int y = (int)map(image.params[yParam], imageStore.getParamMin(yParam), imageStore.getParamMax(yParam), 0, (float) (height - poster_image_width*2));
//			imageMode(CENTER);
//			image(image.image, x + poster_image_width, y + poster_image_width, poster_image_width);
//			stroke(255, 0, 0);
//			noFill();
//
//
//			println(x + "  " + y);
//		}
//	}
//
//	public void exportPoster()
//	{
//		PGraphics poster = createGraphics(5000, 3535);
//		double[] lowerKey = {
//				(long)(cp5.getController("modelRange").getArrayValue(0)),
//				(long)(cp5.getController("attP1Range").getArrayValue(0)),
//				(long)(cp5.getController("attP2Range").getArrayValue(0)),
//				(long)(cp5.getController("rodL1Range").getArrayValue(0)),
//				(long)(cp5.getController("rodL2Range").getArrayValue(0)),
//				(long)(cp5.getController("speedRange").getArrayValue(0)),
//				(long)(((SpecialRange)cp5.getController("timeRange")).getTimeStart()) - 1,
//				(long)(cp5.getController("xRange").getArrayValue(0))  + 1,
//				(long)(cp5.getController("yRange").getArrayValue(0))  + 1
//		};
//
//		double[] upperKey = {
//				(long)(cp5.getController("modelRange").getArrayValue(1)),
//				(long)(cp5.getController("attP1Range").getArrayValue(1))  + 1,
//				(long)(cp5.getController("attP2Range").getArrayValue(1))  + 1,
//				(long)(cp5.getController("rodL1Range").getArrayValue(1))  + 1,
//				(long)(cp5.getController("rodL2Range").getArrayValue(1))  + 1,
//				(long)(cp5.getController("speedRange").getArrayValue(1))  + 1,
//				(long)(((SpecialRange)cp5.getController("timeRange")).getTimeEnd()) + 1,
//				(long)(cp5.getController("xRange").getArrayValue(1))  + 1,
//				(long)(cp5.getController("yRange").getArrayValue(1))  + 1
//		};
//		poster.beginDraw();
//		poster.stroke(0);
//		poster.fill(0);
//		poster.background(255);
//		make_poster(poster, (int)cp5.getGroup("x_axis_param").getValue(), (int)cp5.getGroup("y_axis_param").getValue(), poster.width*poster_image_width, imageStore.range(lowerKey, upperKey));
//		poster.endDraw();
//		poster.save("test.png");
//	}
//
//	public void make_poster(PGraphics g, int paramX, int paramY, float imageWidth, ArrayList<PImageWithParams> images)
//	{
//
////		g.textFont(cp5.getFont().getFont(), imageWidth/18);
//		g.textSize(imageWidth/20.0f);
//		float margin = g.width*0.04f;
//		float chartTopX = margin;
//		float chartTopY = margin;
//		float chartBottomX = g.width - margin;
//		float chartBottomY = g.height - margin;
//		float chartWidth = (chartBottomX - chartTopX);
//		float chartHeight = (chartBottomY - chartTopY);
//
//
//
//		HashMap<Long, Long> xOrder = getOrdinalOrder(images, paramX);
//		HashMap<Long, Long> yOrder = getOrdinalOrder(images, paramY);
//		float epsilon = 0.001f;
//		float scalarXMin = getParamMin(images, paramX) - epsilon;
//		float scalarXMax = getParamMax(images, paramX) + epsilon;
//		float scalarYMin = getParamMin(images, paramY) - epsilon;
//		float scalarYMax = getParamMax(images, paramY) + epsilon;
//
//		HashMap<Long, Float> xTics = new HashMap<Long, Float>();
//		HashMap<Long, Float> yTics = new HashMap<Long, Float>();
//		float imageHeight = 0;
//		g.textAlign(LEFT, TOP);
//		HashSet<String> usedPositions = new HashSet<String>();
//		for(PImageWithParams image : images)
//		{
//			imageHeight = image.image.height * (imageWidth/image.image.width);
//			float scalarX = map(image.params[paramX], scalarXMin, scalarXMax, chartTopX, chartBottomX - imageWidth);
//			float scalarY = map(image.params[paramY], scalarYMin, scalarYMax, chartBottomY - imageWidth, chartTopY);
//			float ordinalX = map(xOrder.get(image.params[paramX]), 0 - epsilon, xOrder.size()-1 + epsilon, chartTopX, chartBottomX - imageWidth);
//			float ordinalY = map(yOrder.get(image.params[paramY]), 0 - epsilon, yOrder.size()-1 + epsilon, chartBottomY - imageHeight, chartTopY);
//			float x = lerp(scalarX, ordinalX, x_axis_ordinal);
//			float y = lerp(scalarY, ordinalY, y_axis_ordinal);
//			if(!usedPositions.contains(image.params[paramX] + "x" + image.params[paramY]))
//			{
//				image(g, image.image, x, y, imageWidth);
//				g.text(image.filename, x, y + imageHeight);
//				xTics.put(image.params[paramX], x);
//				yTics.put(image.params[paramY], y);
//				usedPositions.add(image.params[paramX] + "x" + image.params[paramY]);
//			}
//		}
//
////		g.stroke(255, 0, 0);
////		g.noFill();
////		g.rectMode(CORNERS);
////		g.rect(chartTopX, chartTopY, chartBottomX, chartBottomY);
//
//		//prepare label drawing
//
////		poster.stroke(0);
////		poster.fill(0);
//		//draw x axis label
//		g.textAlign(LEFT, CENTER);
//		String xlabel = imageStore.getParamName(paramX);
//		g.pushMatrix();
//		g.translate(chartTopX, chartBottomY + margin*0.4f);
//		g.text(xlabel, 0, 0);
////		g.line(-chartWidth/2, 0, -g.textWidth(xlabel), 0);
////		g.line(chartWidth/2, 0, g.textWidth(xlabel), 0);
////		float tipX = chartWidth/2;
////		float tipY = 0;
////		g.triangle(tipX, tipY, tipX - margin/7, tipY - margin/7, tipX - margin/7, tipY + margin/7);
//		g.popMatrix();
//
//		//draw xtics
//		g.pushMatrix();
//		g.translate(0, chartBottomY + margin*0.1f);
//		g.textAlign(CENTER, TOP);
//		for(Entry<Long, Float> xtic : xTics.entrySet())
//		{
//			g.text(xtic.getKey() + "", xtic.getValue() + imageWidth/2, 1);
//		}
//		g.popMatrix();
//
////		draw y axis label
//		g.textAlign(LEFT, CENTER);
//		g.pushMatrix();
//		String ylabel = imageStore.getParamName(paramY);
//		g.translate(chartTopX - margin*0.4f, chartBottomY);
//		g.rotate(-HALF_PI);
//		g.text(ylabel, 0, 0);
////		g.line(-chartHeight/2, 0, -g.textWidth(ylabel), 0);
////		g.line(chartHeight/2, 0, g.textWidth(ylabel), 0);
////		tipX = chartHeight/2;
////		tipY = 0;
////		g.triangle(tipX, tipY, tipX - margin/7, tipY - margin/7, tipX - margin/7, tipY + margin/7);
//		g.popMatrix();
//
//		//draw ytics
//		g.pushMatrix();
//		g.translate(chartTopX - margin*0.1f, 0);
//		g.textAlign(RIGHT, CENTER);
//		for(Entry<Long, Float> ytic : yTics.entrySet())
//		{
//			println("hello " + ytic.getValue());
//			g.text(ytic.getKey() + "", 0, ytic.getValue() + imageHeight/2);
//		}
//		g.popMatrix();
//
//		//draw legend
//		g.textAlign(RIGHT, CENTER);
//		g.text(PSpaceImageStore.legendString, chartBottomX, chartBottomY + margin*0.4f);
//
//	}
//
//	public HashMap<Long, Long> getOrdinalOrder(ArrayList<PImageWithParams> images, final int paramIdx)
//	{
//		SortedSet<Double> distincValueSet = new TreeSet<Long>();
//		for(PImageWithParams image : images)
//			distincValueSet.add(image.params[paramIdx]);
//
//		HashMap<Long, Long> ordinalMap = new HashMap<Long, Long>();
//		long ordinal = 0;
//		for(Long value : distincValueSet)
//		{
//			ordinalMap.put(value, ordinal);
//			ordinal++;
//		}
//		return ordinalMap;
//	}
//
//	public double getParamMax(ArrayList<PImageWithParams> images, int paramIdx)
//	{
//		double max = Long.MIN_VALUE;
//		for(PImageWithParams image : images)
//			max = Math.max(max, image.params[paramIdx]);
//		return max;
//	}
//
//	public double getParamMin(ArrayList<PImageWithParams> images, int paramIdx)
//	{
//		double min = Long.MAX_VALUE;
//		for(PImageWithParams image : images)
//			min = Math.min(min, image.params[paramIdx]);
//		return min;
//	}
//
//	public void image(PImage image, float x, float y, float width)
//	{
//		float scaleFactor = width/(float)(image.width);
//		image(image, x, y, width, scaleFactor*image.height);
//	}
//
//	public void image(PGraphics g, PImage image, float x, float y, float width)
//	{
//		float scaleFactor = width/(float)(image.width);
//		g.image(image, x, y, width, scaleFactor*image.height);
//	}
//
//	public static void main(String[] args)
//	{
//		PApplet.main(new String[]{PSpaceExplorer.class.getCanonicalName()});
//	}
//
//}
