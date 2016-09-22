package ga.operations.selectors;

import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * This class implements the proportionate selection scheme.
 * The given fitnessfunction values are assumed to be normalized.
 *
 * @author Siu Kei Muk (David)
 * @since 9/09/16.
 */
public class ProportionateScheme implements SelectionScheme{

    public ProportionateScheme() {
    }

    /**
     * @param fitnessValues descending sorted normalized (range from 0 to 1, sum to 1) fitnessfunction values of individuals.
     * @return index of selected individual
     */
    @Override
    public int select(@NotNull final List<Double> fitnessValues) {
        double r = Math.random();
        for (int i = 0; i < fitnessValues.size(); i++) {
            final double currentFitness = fitnessValues.get(i);
            if (r < currentFitness)
                return i;
            r -= currentFitness;
        }
        return fitnessValues.size()-1;
    }
}
