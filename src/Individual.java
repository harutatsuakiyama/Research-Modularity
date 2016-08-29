import com.sun.istack.internal.NotNull;

/**
 * Created by david on 27/08/16.
 */
public class Individual<T extends Chromosome> implements Comparable<Individual<T>>, Copyable<Individual<T>>{

    private T chromosome;
    private double fitness = 0;
    private boolean evaluated = false;

    public Individual(@NotNull final T chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public int compareTo(final Individual<T> tIndividual) {
        final double tFitness = tIndividual.getFitness();
        if (fitness > tFitness)
            return 1;
        else if (fitness < tFitness)
            return -1;
        else
            return 0;
    }

    @Override
    public Individual<T> copy() {
        return null;
    }

    public void evaluate(final Fitness objective) {
        fitness = objective.evaluate(chromosome.getPhenotype());
        evaluated = true;
    }

    public boolean isEvaluated(){
        return evaluated;
    }

    public double getFitness(){
        return fitness;
    }

    public T getChromosome() {
        return chromosome;
    }
}
