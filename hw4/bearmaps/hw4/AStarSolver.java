package bearmaps.hw4;

import java.util.List;
import bearmaps.proj2ab.DoubleMapPQ;
import java.util.HashMap;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.ArrayList;

public class AStarSolver <Vertex> implements ShortestPathsSolver<Vertex> {

    /* Holds the goal vertex. */
    private Vertex endVertex;

    /* Holds the graph that is inputted. */
    private AStarGraph<Vertex> graph;

    /* This priority queue contains two items, a priority value(distance to start + heuristic) and the item(WeightedVertex). */
    private DoubleMapPQ<Vertex> holder = new DoubleMapPQ<>();

    /* Stores the best distance to a vertex */
    private HashMap<Vertex, Double> distTo = new HashMap<>();

    /* Stores the vertex that a vertex is connected to that provides the shortest distance to the start vertex.
    * The key vertex is the vertex and the value vertex is the vertex that the key comes from. */
    private HashMap <Vertex, Vertex> edgeTo = new HashMap<>();

    /* Stores the weight of the solution. Compute this after running the main part of the constructor. */
    private double solutionWeight;

    /* Total number of priority queue dequeue operations. */
    private int numStatesExplored;

    /* Amount of time it took for the constructor to run. */
    private double explorationTime;

    /* If 0, return solved. If 1, return timeout. If 2, return unsolvable. */
    private int resulting;

    /* Holds the vertices that is the shortest path. */
    private ArrayList<Vertex> answer = new ArrayList<>();

    /* Stores each vertex and their weight. This is used to compute to solutionWeight variable. Can I just use distTo? */
    private HashMap<Vertex, Double> weight = new HashMap<>();

    /* Constructor */
    /* Timeout passed in has unit of seconds. */
    /* Constructor computes everything to the point that each method runs in constant time. */
    /* The constructor finds the shortest weighted path from a start vertex to an end vertex. */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch timer = new Stopwatch();
        resulting = 0;
        numStatesExplored = 0;
        graph = input;
        endVertex = end;
        List<WeightedEdge<Vertex>> startWeightedVertexes = input.neighbors(start);
        for (WeightedEdge<Vertex> each: startWeightedVertexes) {
            holder.add(each.to(), each.weight());
            weight.put(each.to(), each.weight());
            distTo.put(each.to(), each.weight());
            edgeTo.put(each.to(), each.from());
        }

        while (true) {
            if (holder.size() == 0) {
                resulting = 2;
                break;
            }
            Vertex startVertex = holder.removeSmallest();
            solutionWeight += weight.get(startVertex);
            if (timer.elapsedTime() > timeout) {
                resulting = 1;
                break;
            }
            if (startVertex == end) {
                resulting = 0;
                break;
            }
            numStatesExplored += 1;
            relax(startVertex);
        }

        /* Computes a list of vertices from start to end as well as creates the solution weight. Consider reversing the list of vertices. */
        if (resulting == 0) {
            Vertex path = edgeTo.get(end);
            answer.add(end);
            while (path != start) {
                answer.add(path);
                path = edgeTo.get(path);
            }
            answer.add(start);
            /* holder1 is used to reverse answer. */
            ArrayList<Vertex> holder1 = new ArrayList<>();
            while (answer.size() != 0) {
                holder1.add(answer.remove(answer.size() - 1));
            }
            answer = holder1;
        }

        explorationTime = timer.elapsedTime();
    }

    /* Given a vertex, this method relaxes all the edges. Relaxing involves changing the neighbors edgeTo and distTo (if applicable).
     * It also includes adding each of it's neighbors to the priority queue. */
    private void relax(Vertex e) {
        List<WeightedEdge<Vertex>> startWeightedVertexes = graph.neighbors(e);
        for (WeightedEdge<Vertex> each: startWeightedVertexes) {
            Vertex p = each.from();
            Vertex q = each.to();
            double w = each.weight();

            if (!distTo.containsKey(q)) {
                distTo.put(q, (distTo.get(p) + w));
                weight.put(q, (distTo.get(p) + w));
                edgeTo.put(q, p);
                if (holder.contains(q)) {
                    holder.changePriority(q, distTo.get(q) + graph.estimatedDistanceToGoal(q, endVertex));
                } else {
                    holder.add(q, distTo.get(q) + graph.estimatedDistanceToGoal(q, endVertex));
                }
            } else if ((distTo.get(p) + w) < distTo.get(q)) {
                distTo.put(q, (distTo.get(p) + w));
                weight.put(q, (distTo.get(p) + w));
                /* Add q to the hashmap with the edgeTo value of p. */
                edgeTo.put(q, p);
                if (holder.contains(q)) {
                    holder.changePriority(q, distTo.get(q) + graph.estimatedDistanceToGoal(q, endVertex));
                } else {
                    holder.add(q, distTo.get(q) + graph.estimatedDistanceToGoal(q, endVertex));
                }
            }
        }
    }

    /* If 0, return solved. If 1, return timeout. If 2, return unsolvable. The variable that is checked is resulting. */
    public SolverOutcome outcome() {
        if (resulting == 0) {
            return SolverOutcome.SOLVED;
        }
        if (resulting == 1) {
            return SolverOutcome.TIMEOUT;
        }
        return SolverOutcome.UNSOLVABLE;
    }

    public List<Vertex> solution() {
        ArrayList<Vertex> holder = new ArrayList<>();
        if (resulting == 1) {
            return holder;
        }
        if (resulting == 2) {
            return holder;
        }
        return answer;
    }

    public double solutionWeight() {
        if (resulting == 1) {
            return 0;
        }
        if (resulting == 2) {
            return 0;
        }
        return distTo.get(endVertex);
        /* return solutionWeight; */
    }

    public int numStatesExplored() {
        return numStatesExplored;
    }

    public double explorationTime() {
        return explorationTime;
    }

}
