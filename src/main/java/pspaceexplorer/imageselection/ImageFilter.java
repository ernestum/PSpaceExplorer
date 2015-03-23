package pspaceexplorer.imageselection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Range;
import pspaceexplorer.ParameterFormat;
import pspaceexplorer.ParameterSet;

import java.util.Map;
import java.util.Set;

import static pspaceexplorer.ParameterFormat.Scales.*;

/**
 * Created by m on 3/23/15.
 */
public class ImageFilter {
    private ParameterFormat format;
    private Predicate[] filterPredicates; //Stores a mix of common predicates (for nominal values) and ranges (for all other values)

    public ImageFilter(ParameterFormat format) {
        this.format = format;
        filterPredicates = new Predicate[format.numParameters];
        for(int paramID = 0; paramID < format.numParameters; paramID++) {
            filterPredicates[paramID] = format.scales[paramID] == Nominal ? Predicates.alwaysTrue() : Range.all();
        }
    }

    public Range<Double>[] getOnlyRanges() {
        Range<Double>[] ranges = new Range[format.numNumericalParameters];
        for(int paramID = 0, i = 0; paramID < format.numParameters; paramID++) {
            if(format.scales[paramID] != Nominal) {
                ranges[i] = (Range<Double>) filterPredicates[paramID];
                i++;
            }
        }
        assert ranges[format.numNumericalParameters-1] != null;
        return ranges;
    }

    public boolean applyOnlyNonRanges(ParameterSet parameterSet) {
        assert parameterSet.format == format;
        for(int i = 0; i < format.numParameters; i++) {
            if(format.scales[i] == Nominal && !filterPredicates[i].apply(parameterSet.parameters[i]))
                return false;
        }
        return true;
    }

    public boolean apply(ParameterSet parameterSet) {
        assert parameterSet.format == format;
        for(int i = 0; i < format.numParameters; i++) {
            if(!filterPredicates[i].apply(parameterSet.parameters[i]))
                return false;
        }
        return true;
    }

    public void setUpperBound(int index, double bound) {
        assert format.scales[index] != Nominal;
        Range<Double> oldRange = (Range<Double>) filterPredicates[index];
        filterPredicates[index] = Range.closed(oldRange.lowerEndpoint(), bound);
    }

    public void setLowerBound(int index, double bound) {
        assert format.scales[index] != Nominal;
        Range<Double> oldRange = (Range<Double>) filterPredicates[index];
        filterPredicates[index] = Range.closed(bound, oldRange.upperEndpoint());
    }

    public void setRange(int index, double lowerBound, double upperBound) {
        assert format.scales[index] != Nominal;
        filterPredicates[index] = Range.closed(lowerBound, upperBound);
    }

    public void setWhitelist(int index, final Set whitelist) {
        if(format.scales[index] != Nominal) System.err.println("Warning: a whitelist is used on a non nominal value.");
        filterPredicates[index] = new Predicate() {
            @Override
            public boolean apply(Object input) {
                return whitelist.contains(input);
            }
        };
    }

    public void setBlacklist(int index, final Set blacklist) {
        if(format.scales[index] != Nominal) System.err.println("Warning: a blacklist is used on a non nominal value.");
        filterPredicates[index] = new Predicate() {
            @Override
            public boolean apply(Object input) {
                return !blacklist.contains(input);
            }
        };
    }
}
