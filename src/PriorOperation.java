import java.util.List;

/**
 * Created by david on 28/08/16.
 */
public interface PriorOperation<T extends Chromosome> {
    List<T> preSelect(Population<T> population);
}
