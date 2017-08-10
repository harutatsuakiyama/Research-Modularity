package ga.others;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.genes.EdgeGene;
import ga.components.hotspots.Hotspot;
import ga.components.hotspots.MatrixHotspot;
import ga.components.materials.GRN;
import ga.components.materials.GRNFactoryNoHiddenTarget;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

/**
 * Created by Zhenyue Qin (秦震岳) on 24/6/17.
 * The Australian National University.
 */
public class GeneralMethods<T> {

    /**
     * Get permutations of a string
     * @param str the target string that we want to get mutations
     * @return all the permutations
     */
    public static Set<String> permutation(String str) {
        return permutation("", str);
    }

    public static Set<String> permutation(String prefix, String str) {
        Set<String> set = new HashSet<>();
        int n = str.length();
        if (n == 0) {
            set.add(prefix);
            return set;
        } else {
            for (int i = 0; i < n; i++)
                set.addAll(permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n)));
        }
        return set;
    }

    public static <T> Set<List<T>> getAllListsRecursion(Set<T> elements, int lengthOfList, Set<List<T>> allCurrentLists) {
        Set<List<T>> rtn = new HashSet<List<T>>();
        for (T element : elements) {
            for (List<T> list : allCurrentLists) {
                List tmpList = new ArrayList<>(list);
                tmpList.add(0, element);
                rtn.add(tmpList);
            }
        }
        List<T> anElement = new ArrayList<>();
        for (List<T> element : rtn) {
            anElement = element;
        }
        if (anElement.size() == lengthOfList) {
            return rtn;
        } else {

        }
        return getAllListsRecursion(elements, lengthOfList, rtn);
    }

    /**
     * This returns all the permutations of n positions with a limited number of candidates,
     * e.g. if the set to choose from is {1, 2} and there are 2 positions. All the possibilities will be
     * (1, 1), (1, 2), (2, 1), (2, 2).
     * @param elements candidate elements to choose from
     * @param currentLengthOfList for recursion purpose to know when to conclude
     * @param lengthOfList the length of list that we want
     * @param <T> the type of elements
     * @return all the permutations in a list
     */
    public static <T> Set<List<T>> getAllLists(Set<T> elements, int currentLengthOfList, int lengthOfList) {
        if(currentLengthOfList == 1) {
            Set<List<T>> tmpOneElementSet = new HashSet<>(elements.size());
            for (T element : elements) {
                List<T> aOneElementList = new ArrayList<>();
                aOneElementList.add(element);
                tmpOneElementSet.add(aOneElementList);
            }
            return getAllListsRecursion(elements, lengthOfList, tmpOneElementSet);
        }
        else {
            return getAllLists(elements, currentLengthOfList - 1, lengthOfList);
        }
    }

    public static <T> void serializeObject(T population, String file_name) {

        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        try {
            fout = new FileOutputStream(file_name);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(population);

            System.out.println("Done");

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static <T> void deserializeObject(String file_name) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(file_name);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream (buffer);
        T recoveredQuarks = (T) input.readObject();

        System.out.println(recoveredQuarks);
    }

    public static void saveJSON(JSONObject obj, String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(obj.toString());
            System.out.println("Successfully Copied JSON Object to File...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GRN getGRNFromJSON(int index, String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(fileName));

        JSONObject jsonObject = (JSONObject) obj;
        String genes = ((String) jsonObject.get(Integer.toString(index))).replace("[", "").replace("]", "");
        List<String> elephantList = Arrays.asList(genes.split(", "));

        List<EdgeGene> edgeGenes = new ArrayList<>();

        for (String aGene : elephantList) {
            EdgeGene anEdgeGene = new EdgeGene(Integer.parseInt(aGene));
            edgeGenes.add(anEdgeGene);
        }
        GRN grn = new GRN(edgeGenes);
//        System.out.println(grn.getStrand().length);

        return grn;
    }

    public static MatrixHotspot getMatrixHotspotFromJSON(int index, String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(fileName));

        JSONObject jsonObject = (JSONObject) obj;
        String genes = ((String) jsonObject.get(Integer.toString(index))).replace("[", "").replace("]", "");
        List<String> elephantList = Arrays.asList(genes.split(", "));

        Map<Integer, Double> recombinationRates = new HashMap<>();

        for (int i=0; i<elephantList.size(); i++) {
            recombinationRates.put(i+1, Double.parseDouble(elephantList.get(i)));
        }

        MatrixHotspot matrixHotspot = new MatrixHotspot(8, 100, recombinationRates);
        return matrixHotspot;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
        GRNFactoryNoHiddenTarget grnFactory = new GRNFactoryNoHiddenTarget(10, 30);
        JSONObject json = new JSONObject();
        for (int i=0; i<50; i++) {
            GRN grn1 = grnFactory.generateModularizedGeneRegulatoryNetwork(5);
//            MatrixHotspot matrixHotspot = new MatrixHotspot(12, 196);
//            json.put(Integer.toString(i), matrixHotspot.getRecombinationRates().toString());
            json.put(Integer.toString(i), grn1.toString());
        }
        saveJSON(json, "jsons/grn_100_edge_30.json");
//        saveJSON(json, "jsons/hotspot_12.json");

//        for (int i=0; i<100; i++) {
//            getGRNFromJSON(i, "jsons/grn_100.json");
//        }

//        getMatrixHotspotFromJSON(0, "jsons/hotspot_50.json");
    }


}
