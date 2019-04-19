package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import edu.princeton.cs.algs4.TrieSET;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private ArrayList<Point> listOfPoints = new ArrayList<>();

    private HashMap<Point, Node> pointNodeMap = new HashMap<>();

    private WeirdPointSet graph;

    private TrieSET nameBank = new TrieSET();

    private HashMap<String, ArrayList<String>> nameMap = new HashMap<>();

    private ArrayList<Map<String, Object>> locationHolder = new ArrayList<>();

    HashMap<String, HashMap<String, Object>> locationInfo = new HashMap<>();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        for (Node eachNode: nodes) {
            if (eachNode.name() != null) {
                String actualName = eachNode.name();
                String cleanName = cleanString(actualName);
                if (!nameMap.containsKey(cleanName)) {
                    nameBank.add(cleanName);
                    ArrayList<String> input = new ArrayList<>();
                    input.add(actualName);
                    nameMap.put(cleanName, input);
                } else if (nameMap.containsKey(cleanName)) {
                    ArrayList<String> newInput = new ArrayList<>();
                    newInput = nameMap.get(cleanName);
                    newInput.add(actualName);
                    nameMap.put(cleanName, newInput);
                }
            }
            HashMap<String, Object> holder = new HashMap<>();
            holder.put("lat", eachNode.lat());
            holder.put("lon", eachNode.lon());
            holder.put("name", eachNode.name());
            holder.put("id", eachNode.id());
            locationInfo.put(eachNode.name(), holder);
            double size = this.neighbors(eachNode.id()).size();
            if (size > 0) {
                Point addition = new Point(eachNode.lon(), eachNode.lat());
                pointNodeMap.put(addition, eachNode);
                listOfPoints.add(addition);
            }
        }
        graph = new WeirdPointSet(listOfPoints);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point answer = graph.nearest(lon, lat);
        return pointNodeMap.get(answer).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */

    public List<String> getLocationsByPrefix(String prefix) {
        ArrayList<String> locations = new ArrayList<>();
        String cleanedString = cleanString(prefix);
        Iterable<String> cleanedLocations = nameBank.keysWithPrefix(cleanedString);
        ArrayList<String> output = new ArrayList<>();
        for (String eachLocation: cleanedLocations) {
            output = nameMap.get(eachLocation);
            for (String eachOutput: output) {
                locations.add(eachOutput);
            }
        }
        return locations;
    }


    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanInput = cleanString(locationName);
        List<String> output = nameMap.get(cleanInput);
        List<Map<String, Object>> solution = new ArrayList<>();
        for (String eachWord: output) {
            solution.add(locationInfo.get(eachWord));
        }
        return solution;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    public static void main(String args[]) {
        String x = cleanString("Hello there homie!");
        System.out.println(x);
        TrieSET holder = new TrieSET();
        holder.add("hello");
        holder.add("heathen");
        holder.add("her");
        Iterable<String> actual = holder.keysWithPrefix("he");
        System.out.println(actual);
    }

}
