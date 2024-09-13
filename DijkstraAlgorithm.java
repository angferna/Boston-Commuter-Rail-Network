import java.text.DecimalFormat;
import java.util.*;

public class DijkstraAlgorithm {
    private int V; // Number of vertices
    private Map<String, List<Node>> adj; // Adjacency list representing the graph

    // Node class to represent each vertex with its weight
    class Node implements Comparable<Node> {
        String vertex;
        double weight;

        Node(String v, double weight1) {
            vertex = v;
            weight = weight1;
        }

        public int compareTo(Node other) {
            return Double.compare(weight, other.weight);
        }
    }

    // Constructor to initialize the graph
    DijkstraAlgorithm(int vertices) {
        V = vertices;
        adj = new HashMap<>();
        for (int i = 0; i < V; i++) {
            //adj.put(String.valueOf((char) ('A' + i)), new ArrayList<>());
        }
    }

    // Add an edge to the graph
    void addEdge(String source, String destination, double weight) {
        // Initialize the list for the source vertex if not already initialized
        if (!adj.containsKey(source)) {
            adj.put(source, new ArrayList<>());
        }
        // Initialize the list for the destination vertex if not already initialized
        if (!adj.containsKey(destination)) {
            adj.put(destination, new ArrayList<>());
        }
        adj.get(source).add(new Node(destination, weight));
        adj.get(destination).add(new Node(source, weight));
    }


    // Dijkstra's algorithm
    void dijkstra(String source) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Map<String, Double> dist = new HashMap<>();
        for (String vertex : adj.keySet()) {
            dist.put(vertex, Double.MAX_VALUE);
        }
        dist.put(source, 0.0);
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            String u = pq.poll().vertex;
            for (Node neighbor : adj.get(u)) {
                String v = neighbor.vertex;
                double w = neighbor.weight;
                if (dist.get(v) > dist.get(u) + w) {
                    dist.put(v, dist.get(u) + w);
                    pq.add(new Node(v, dist.get(v)));
                }
            }
        }

        // Print the shortest distances from the source vertex
        System.out.println("Shortest distances from vertex " + source + ":");
        DecimalFormat df = new DecimalFormat("#.#");
        for (String vertex : dist.keySet()) {
            System.out.println("Vertex " + vertex + ": " + Double.parseDouble(df.format(dist.get(vertex))));
        }

    }

    // Pretty print the graph
    void printGraph() {
        System.out.println("Graph:");
        for (String vertex : adj.keySet()) {
            System.out.print(vertex + " <-> ");
            List<Node> neighbors = adj.get(vertex);
            for (int i = 0; i < neighbors.size(); i++) {
                Node neighbor = neighbors.get(i);
                System.out.print(neighbor.vertex + "(" + neighbor.weight + ")");
                if (i < neighbors.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

}
