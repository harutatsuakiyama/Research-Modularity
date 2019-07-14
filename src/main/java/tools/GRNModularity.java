package tools;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class GRNModularity {
    public static Graph getGRNGraph(List<Integer> aGRN) {
        Graph graph = new MultiGraph("GRN");
        int targetNumber = (int) Math.sqrt(aGRN.size());

        for (int i=0; i<targetNumber; i++) {
            graph.addNode(Integer.toString(i));
        }

        // i: Towards which that the GRN regulate
        for (int i=0; i<targetNumber; i++) {
            for (int j=0; j<targetNumber; j++) {
                int grnValue = aGRN.get(j * targetNumber + i);
                String potentialEdgeId = Integer.toString(j) + Integer.toString(i);
                if (grnValue == 1) {
                    graph.addEdge(potentialEdgeId + "+", Integer.toString(j), Integer.toString(i), true);
                } else if (grnValue == -1) {
                    graph.addEdge(potentialEdgeId + "-", Integer.toString(j), Integer.toString(i), true);
                }
            }
        }
        return graph;
    }

    public static double[] avgMod15To50 = {-0.013768888888888952, -0.0141462890625, -0.012709169550172728, -0.012259104938272066, -0.010496675900276671, -0.01279287500000008, -0.009620975056689279, -0.007072933884297498, -0.009089508506616148, -0.010616753472222393, -0.007369360000000272, -0.006886982248520721, -0.005068312757201668, -0.005784630102040765, -0.004981331747919129, -0.006090499999999959, -0.005157075962539058, -0.005286328125, -0.005808448117538956, -0.005329541522491441, -0.0049706122448976995, -0.0049611496913580504, -0.0048747626004382, -0.004281786703601195, -0.0029820841551609838, -0.005154187500000032, -0.0038505948839976448, -0.0044904195011338036, -0.004341049215792338, -0.0025635588842975107, -0.0030291604938270235, -0.003626016068052938, -0.0036050475328203773, -0.003118554687500096, -0.0026657017909204484, -0.002716939999999959};
    public static double[] maxMod15To50 = {0.43333333333333335, 0.435546875, 0.38062283737024216, 0.38888888888888884, 0.48753462603878117, 0.30000000000000004, 0.3945578231292518, 0.362603305785124, 0.32514177693761814, 0.32986111111111116, 0.30000000000000004, 0.3069526627218935, 0.31481481481481477, 0.28316326530612246, 0.2877526753864448, 0.2977777777777778, 0.27419354838709675, 0.248046875, 0.25711662075298436, 0.25778546712802763, 0.27102040816326534, 0.2777777777777778, 0.31081081081081086, 0.2600415512465373, 0.2406311637080869, 0.22468750000000004, 0.18292682926829273, 0.20408163265306123, 0.2441860465116279, 0.20428719008264462, 0.21012345679012345, 0.2152646502835539, 0.19646899049343614, 0.16666666666666663, 0.19304456476468135, 0.19819999999999993};
    public static double[] minMod15To50 = {-0.37555555555555553, -0.3828125, -0.4429065743944637, -0.39506172839506176, -0.39473684210526316, -0.40500000000000014, -0.3276643990929705, -0.3729338842975206, -0.3705103969754253, -0.3368055555555557, -0.3072000000000001, -0.30843195266272194, -0.31550068587105623, -0.29591836734693866, -0.3145065398335315, -0.3, -0.278876170655567, -0.28173828125, -0.2993572084481174, -0.2391868512110728, -0.24653061224489814, -0.2534722222222221, -0.285244704163623, -0.24930747922437674, -0.21827744904667978, -0.22531249999999992, -0.20731707317073172, -0.24064625850340138, -0.23066522444564636, -0.25025826446280997, -0.21728395061728395, -0.19659735349716445, -0.234495246717972, -0.19292534722222232, -0.19429404414827156, -0.1872000000000001};

    public static double getNormedMod(double aMod, int edgeNum) {
        double avgMod = avgMod15To50[edgeNum - 15];
        double maxMod = maxMod15To50[edgeNum - 15];
        double minMod = minMod15To50[edgeNum - 15];

//        System.out.println("a mod" + aMod);
//        System.out.println("max mod: " + maxMod);
//        System.out.println("edge num: " + edgeNum);

        return (aMod - avgMod) / (maxMod - avgMod);
    }

    public static double getGRNModularity(List<Integer> aGRN) {
        Graph graph = getGRNGraph(aGRN);
        HashMap<Object, HashSet<Node>> aCommunity = getModularityPartition(graph, 5);
        return QinModularity.getQWithCommunities(graph, aCommunity);
    }



    public static double getGRNModularity(Graph aGRN) {
        HashMap<Object, HashSet<Node>> aCommunity = getModularityPartition(aGRN, 5);
        return QinModularity.getQWithCommunities(aGRN, aCommunity);
    }

    public static HashMap<Object, HashSet<Node>> getModularityPartition(Graph graph, int modSize) {
        int targetNumber = graph.getNodeCount();
        int currentComId = 0;
        HashMap<Object, HashSet<Node>> aMap = new HashMap<>();
        for (int i=0; i<targetNumber; i=i+modSize) {
            List<Integer> aCom = GRNModularity.getARangedList(i, i+modSize);
            HashSet<Node> nodeCom = new HashSet<>();
            for (Integer n : aCom) {
                nodeCom.add(graph.getNode(Integer.toString(n)));
            }
            aMap.put(currentComId, nodeCom);
            currentComId += 1;
        }
        return aMap;
    }

    public static List<Integer> getARangedList(int from, int to) {
        return IntStream.rangeClosed(from, to-1).boxed().collect(toList());
    }


}
