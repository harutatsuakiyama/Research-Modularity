package ga.components.materials;

import ga.components.genes.EdgeGene;
import ga.components.genes.Gene;

import java.util.List;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GeneRegulatoryNetwork extends EdgeMaterial {

    /**
     * Constructs a SimpleMaterial by a list of genes.
     *
     */
    public GeneRegulatoryNetwork(List<EdgeGene> edgeList) {
        super(edgeList);
    }
}
