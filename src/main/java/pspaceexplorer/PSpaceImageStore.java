package pspaceexplorer;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import processing.core.PApplet;
import processing.core.PImage;
import pspaceexplorer.kdtreelong.HPoint;
import pspaceexplorer.kdtreelong.MultiKDTreeLong;

public class PSpaceImageStore
{
	MultiKDTreeLong<PImageWithParams> kdtree;
	final ParameterFormat format;
    final PApplet parent;

//	public static final String legendString =
//			parameterNames[0] + "-" +
//			parameterNames[1] + "-" +
//			parameterNames[2] + "-" +
//			parameterNames[3] + "-" +
//			parameterNames[4] + "-" +
//			parameterNames[5] + "-" +
//			parameterNames[6];
    double[] minValues, maxValues;
    Set[] uniqueValues;


	public PSpaceImageStore(PApplet parent, String folder)
	{
		this(parent, new File(folder));
	}

	public PSpaceImageStore(PApplet parent, File folder)
	{
		format = new ParameterFormat(folder);
        this.parent = parent;
		kdtree = new MultiKDTreeLong<PImageWithParams>(format.numNumericalParameters);
        uniqueValues = new Set[format.numParameters];
        minValues = new double[format.numParameters];
        maxValues = new double[format.numParameters];

        for(int i = 0; i < format.numParameters; i++) {
            uniqueValues[i] = new HashSet();
            minValues[i] = Double.MAX_VALUE;
            maxValues[i] = Double.MIN_VALUE;
        }

		loadFiles(folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && !pathname.getName().endsWith(".names") && !pathname.getName().endsWith(".scales");
			}
		}));
	}

    public void loadFiles(File[] files) {
        for(File file : files) {
            ParameterSet parameters = format.parseFilename(file.getName());
            if(parameters != null) {
                PImageWithParams img = new PImageWithParams(parameters, file, parent);
                kdtree.insert(parameters.numbericalParameters, img);
                for(int i = 0; i < format.numParameters; i++) {
                    if(format.scales[i] != ParameterFormat.Scales.Nominal) {
                        minValues[i] = Math.min((Double) parameters.parameters[i], minValues[i]);
                        maxValues[i] = Math.max((Double) parameters.parameters[i], maxValues[i]);
                    }
                    uniqueValues[i].add(parameters.parameters[i]);
                }
            }
        }
    }

	private static String removeExtension(String filename)
	{
		return filename.substring(0, filename.lastIndexOf(".")); // remove extension
	}

//	public int numParameters()
//	{
//		return parameterNames.length;
//	}
//
//	public String getParamName(int pid)
//	{
//		return parameterNames[pid];
//	}
//
//	public double getParamMax(int pid)
//	{
//		return pmax[pid];
//	}
//
//	public double getParamMin(int pid)
//	{
//		return pmin[pid];
//	}

	private PImageWithParams prevQueryResult;

//	public PImageWithParams getImage(double... parameters)
//	{
//		if(prevQueryResult != null && Arrays.equals(parameters, prevQueryResult.params))
//		{
//			return prevQueryResult;
//		}
//		else
//		{
//			if(kdtree.nearest(parameters).size() > 1)
//			{
//				PApplet.println("candidates are:");
//				for(PImageWithParams i : kdtree.nearest(parameters))
//					PApplet.println(i.params);
//				PApplet.println();
//			}
//
//			PImageWithParams nearest = kdtree.nearest(parameters).iterator().next();
//			prevQueryResult = nearest;
//			return nearest;
//		}
//	}

	public ArrayList<PImageWithParams> range(double[] upperKey, double[] lowerKey)
	{
		return kdtree.range(upperKey, lowerKey);
	}

//	public ArrayList<PImageWithParams> all()
//	{
//
//	}

	public long[] computeCoordsFromRodlength(long attP1, long attP2, long rodL1, long rodL2)
	{
		long a = Math.abs(attP1 - attP2);
		long b = rodL1;
		long c = rodL2;
		long d = attP1;

		long x = (a*a + b*b - c*c)/(2*a);
		long y = (long) Math.sqrt(b*b - x*x);
		return new long[]{x + d, y};
	}
}
