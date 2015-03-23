package pspaceexplorer;

import java.util.ArrayList;
import java.util.Arrays;

import static pspaceexplorer.ParameterFormat.Scales.*;

/**
 * Created by m on 3/17/15.
 */
public class ParameterSet {
    public final ParameterFormat format;
    public final double[] numbericalParameters;
    public final Object[] parameters;

    public ParameterSet(ParameterFormat format, Object[] parameters) {
        this.format = format;
        this.parameters = parameters;
        ArrayList<Double> numericalList = new ArrayList<Double>(format.numParameters);
        for(int i = 0; i < format.numParameters; i++) if(format.scales[i] != Nominal) numericalList.add((Double) parameters[i]);
        numbericalParameters = new double[numericalList.size()];
        for(int i = 0; i < numericalList.size(); i++) numbericalParameters[i] = numericalList.get(i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParameterSet that = (ParameterSet) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(parameters, that.parameters)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(parameters);
    }
}
