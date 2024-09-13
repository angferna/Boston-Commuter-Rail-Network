import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// A class to represent a connected, directed and weighted graph
class Graph {

    // A class to represent a weighted edge in graph
    class Edge {
        String src, dest;
        double weight;

        Edge() {
            src = dest = null;
            weight = 0.0;
        }
    }

    int V, E;
    Edge edge[];
    Map<String, Integer> vertexMap; // Map to store vertex indices

    // Creates a graph with V vertices and E edges
    Graph(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[e];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
        vertexMap = new HashMap<>();
    }

    // The main function that finds shortest distances from
    // src to all other vertices using Bellman-Ford
    // algorithm. The function also detects negative weight
    // cycle
    void BellmanFord(Graph graph, String src) {
        int V = graph.V, E = graph.E;
        double dist[] = new double[V];

        // Step 1: Initialize distances from src to all
        // other vertices as INFINITE
        Arrays.fill(dist, Double.MAX_VALUE);
        dist[vertexMap.get(src)] = 0.0;

        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < E; ++j) {
                int u = vertexMap.get(graph.edge[j].src);
                int v = vertexMap.get(graph.edge[j].dest);
                double weight = graph.edge[j].weight;
                if (dist[u] != Double.MAX_VALUE && dist[u] + weight < dist[v])
                    dist[v] = dist[u] + weight;
            }
        }

        // Step 3: check for negative-weight cycles. The
        // above step guarantees shortest distances if graph
        // doesn't contain negative weight cycle. If we get
        // a shorter path, then there is a cycle.
        for (int j = 0; j < E; ++j) {
            int u = vertexMap.get(graph.edge[j].src);
            int v = vertexMap.get(graph.edge[j].dest);
            double weight = graph.edge[j].weight;
            if (dist[u] != Double.MAX_VALUE && dist[u] + weight < dist[v]) {
                System.out.println("Graph contains negative weight cycle");
                return;
            }
        }
        printArr(dist, V);
    }

    public void addEdge(Graph graph, String source, String destination, double weight, int edgeIndex) {
        graph.edge[edgeIndex].src = source;
        graph.edge[edgeIndex].dest = destination;
        graph.edge[edgeIndex].weight = weight;
    }

    // A utility function used to print the solution
    void printArr(double dist[], int V) {
        DecimalFormat df = new DecimalFormat("#.#");
        System.out.println("Vertex Distance from Source");
        for (Map.Entry<String, Integer> entry : vertexMap.entrySet()) {
            String vertex = entry.getKey();
            int index = entry.getValue();
            System.out.println(vertex + "\t\t" + Double.parseDouble(df.format(dist[index])));
        }
    }

    

    // Utility function to get the index of a vertex
    int getIndex(String vertex) {
        return vertexMap.getOrDefault(vertex, -1);
    }

    // Utility function to get the name of a vertex
    String getVertex(int index) {
        for (Map.Entry<String, Integer> entry : vertexMap.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }

    // Driver's code
    public static void main(String[] args) {

        String fileName = "RailData.txt";

        // Variable to store the number of lines
        int V = 0;
        int E = 0;
        String source = "";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Counting the number of lines
            while (br.readLine() != null) {
                V++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        System.out.println("Number of lines in the file: " + V);
        Graph graph = new Graph(V, V); // Initialize with V edges initially

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int edgeIndex = 0;
            while ((line = br.readLine()) != null) {
                // Splitting the line by comma
                String[] parts = line.split(",");
                E++;

                source = parts[0].trim();
                String destination = parts[1].trim();
                double weight = Double.parseDouble(parts[2].trim());

                // Add vertices to the map if not already present
                if (!graph.vertexMap.containsKey(source)) {
                    graph.vertexMap.put(source, graph.vertexMap.size());
                }
                if (!graph.vertexMap.containsKey(destination)) {
                    graph.vertexMap.put(destination, graph.vertexMap.size());
                }

                graph.addEdge(graph, source, destination, weight, edgeIndex);
                edgeIndex++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing weight as double: " + e.getMessage());
        }

        source = "South Station"; // Specify the source node name
        System.out.println("Source node specified in Main: " + source);
        if (!graph.vertexMap.containsKey(source)) {
            System.out.println("Source node '" + source + "' not found in the graph.");
        } else {
            graph.BellmanFord(graph, source);
        }
    }
}


//DijkstraAlgorithm graphD = new DijkstraAlgorithm(V);
//graphD.addEdge(source, destination, weight);
//graphD.dijkstra(source);