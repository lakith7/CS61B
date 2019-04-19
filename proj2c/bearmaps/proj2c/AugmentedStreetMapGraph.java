package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.lab9.MyTrieSet;

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

    private MyTrieSet nameBank = new MyTrieSet();

    private HashMap<String, String> nameMap = new HashMap<>();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        for (Node eachNode: nodes) {
            if (eachNode.name() != null) {
                String actualName = eachNode.name();
                String cleanName = cleanString(actualName);
                if (!nameMap.containsKey(cleanName)) {
                    nameBank.add(cleanName);
                    nameMap.put(cleanName, actualName);
                }
            }
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
        List<String> cleanedLocations = new ArrayList<>();
        ArrayList<String> locations = new ArrayList<>();
        String cleanedString = cleanString(prefix);
        cleanedLocations = nameBank.keysWithPrefix(cleanedString);
        for (String eachLocation: cleanedLocations) {
            locations.add(nameMap.get(eachLocation));
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
        return new LinkedList<>();
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

}
