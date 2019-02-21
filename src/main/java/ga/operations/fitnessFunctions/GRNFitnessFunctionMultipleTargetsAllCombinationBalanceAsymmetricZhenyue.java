package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;
import ga.others.GeneralMethods;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue extends GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricBob {

    public GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(int[][] targets, int maxCycle, double perturbationRate, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, perturbationSizes, stride);
    }

    public GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(int[][] targets, int maxCycle, double perturbationRate, List<Integer> thresholdOfAddingTarget, int[] perturbationSizes, double stride) {
        super(targets, maxCycle, perturbationRate, thresholdOfAddingTarget, perturbationSizes, stride);
    }

    public boolean printCyclePath = false;
    public List<Integer> cycleDists = new ArrayList<>();

    protected ArrayList<DataGene[]> cyclePath = new ArrayList<>();

    protected double evaluateOneTarget(@NotNull final SimpleMaterial phenotype, @NotNull final int[] target,
                                       DataGene[][] startAttractors) {
//        System.out.println("We are now evaluating one target. ");
//        System.out.println("Size of start attractors: " + startAttractors.length);
//        System.out.println("Whether this can reduce the duplications: " + GeneralMethods.getArrayDuplicateElementNo(startAttractors).size());
        HashMap<Integer, Integer> aDistribution = GeneralMethods.getPerturbationNumberDistribution(startAttractors, target);
//        System.out.println(aDistribution);

        double g = 0;

        Map<Integer, List<Double>> perturbationPathDistanceMap = new HashMap<>();

        for (DataGene[] startAttractor : startAttractors) {
            int perturbedLength = (int) GeneralMethods.getOriginalHammingDistance(startAttractor, target);
            DataGene[] currentAttractor = startAttractor;
            int currentRound = 0;
            boolean isNotStable;
            cyclePath.add(startAttractor.clone());
            do {
                DataGene[] updatedState = this.updateState(currentAttractor, phenotype);
                isNotStable = this.hasNotAttainedAttractor(currentAttractor, updatedState);
                currentAttractor = updatedState;
                currentRound += 1;
                cyclePath.add(updatedState.clone());
            } while (currentRound < this.maxCycle && isNotStable);

            if (currentRound < maxCycle) {
                double hammingDistance = this.getHammingDistance(currentAttractor, target);
                if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
                    perturbationPathDistanceMap.get(perturbedLength).add(hammingDistance);
                } else {
                    perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList(hammingDistance)));
                }
            } else {
                if (perturbationPathDistanceMap.containsKey(perturbedLength)) {
                    perturbationPathDistanceMap.get(perturbedLength).add((double) target.length);
                } else {
                    perturbationPathDistanceMap.put(perturbedLength, new ArrayList<>(Collections.singletonList((double) target.length)));
                }
            }

            cyclePath.remove(cyclePath.size()-1);

            if (this.printCyclePath) {
                System.out.println("currentRound: \t" + currentRound);
                System.out.println("s attractor: \t" + Arrays.toString(startAttractor));
                System.out.println("target: \t\t" + Arrays.toString(target));
                System.out.println("initial distance: \t" + this.getHammingDistance(startAttractor, target));
                System.out.println("final distance: \t" + this.getHammingDistance(currentAttractor, target));
                List<Double> cycleDists = new ArrayList<>();
                for (DataGene[] aCyclePath : cyclePath) {
                    cycleDists.add(this.getHammingDistance(aCyclePath, target));
                }
                System.out.println(Arrays.deepToString(cyclePath.toArray()));
                System.out.println(cycleDists);
                System.out.println();
            }
            cycleDists.add(currentRound);

            cyclePath = new ArrayList<>();


        }
//        if (target.length <= 5) {
//            System.out.println("target: " + Arrays.toString(target));
//            System.out.println("GRN: " + phenotype);
//            System.out.println(perturbationPathDistanceMap);
//        }

//        System.out.println(perturbationPathDistanceMap);
        for (Integer key : perturbationPathDistanceMap.keySet()) {
            List<Double> aPerturbationPathDistances = perturbationPathDistanceMap.get(key);
            double perturbedLengthWeight = perturbationLengthBinomialDistribution.get(key);
//            double perturbedLengthWeight = 1;
            List<Double> gammas = new ArrayList<>();
            for (Double aPerturbationPathDistance : aPerturbationPathDistances) {
                double aD = (1 - (aPerturbationPathDistance / ((double) target.length)));
                double oneGamma = Math.pow(aD, 5);
                gammas.add(oneGamma);
            }
            double averageGamma = GeneralMethods.getAverageNumber(gammas);
//            System.out.println("average Gamma: " + averageGamma);
            g += perturbedLengthWeight * averageGamma;
        }
//        System.out.println("g: " + g);
        return 1 - Math.pow(Math.E, (-3 * g));
    }
}
