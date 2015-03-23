/**
 * %SVN.HEADER%
 * 
 * based on work by Simon Levy
 * http://www.cs.wlu.edu/~levy/software/kd/
 */
package pspaceexplorer.kdtreelong;

// Hyper-Point class supporting KDTree class


import java.util.Arrays;

public class HPoint {

    protected double[] coord;

    protected HPoint(int n) {
        coord = new double[n];
    }

    protected HPoint(double[] x) {
        coord = Arrays.copyOf(x, x.length);
    }

    protected HPoint clone() {
        return new HPoint(coord);
    }

    protected boolean equals(HPoint p) {
        // seems faster than java.util.Arrays.equals(), which is not
        // currently supported by Matlab anyway
        for (int i = 0; i < coord.length; ++i)
            if (coord[i] != p.coord[i])
                return false;

        return true;
    }

    protected static double sqrdist(HPoint x, HPoint y) {
        double dist =  0.;
        for (int i = 0; i < x.coord.length; ++i) {
            double diff = (x.coord[i] - y.coord[i]);
            dist += (diff * diff);
        }
        return dist;
    }

    protected static double eucdist(HPoint x, HPoint y) {
        return Math.sqrt( sqrdist(x, y));
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < coord.length; ++i) {
            s = s + coord[i] + " ";
        }
        return s;
    }

    public static HPoint getMinPoint(int d) {
        HPoint vmin = new HPoint(d);
        for (int i=0; i<d; ++i) vmin.coord[i] = Long.MIN_VALUE;
        return vmin;
    }

    public static HPoint getMaxPoint(int d) {
        HPoint vmax = new HPoint(d);
        for (int i=0; i<d; ++i) vmax.coord[i] = Long.MAX_VALUE;
        return vmax;
    }
}
