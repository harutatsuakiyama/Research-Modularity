package ga.components.materials;

/**
 * Created by zhenyueqin on 25/6/17.
 */
public class GRNFactoryWithHiddenTargets extends GRNFactory {

    public GRNFactoryWithHiddenTargets(final int finalTargetSize, final int edgeSize) {
        super(finalTargetSize, edgeSize);
    }

    @Override
    public GeneRegulatoryNetworkWithHiddenTargets generateGeneRegulatoryNetwork() {
        return new GeneRegulatoryNetworkWithHiddenTargets(this.initializeEdges(), this.manifestTargetSize);
    }
}
