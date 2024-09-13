import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KruskalsAlgorithm {

    // Helper class to represent an edge
    static class Edge implements Comparable<Edge> {
        String source;
        String destination;
        double weight;

        Edge(String source, String destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
        }
    }
    
    public static void main(String[] args) {
        String filename = "RailData.txt";

        // Initialize map to store mapping from string labels to indices
        Map<String, Integer> labelToIndex = new HashMap<>();
        ArrayList<String> labelsInOrder = new ArrayList<>();
        int currentIndex = 0;

        // Initialize map to store edge weights
        Map<String, Double> edgeWeights = new HashMap<>();

        // Read data from the file and populate the maps
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String source = parts[0];
                String destination = parts[1];
                double weight = Double.parseDouble(parts[2]);
                
                if (!labelToIndex.containsKey(source)) {
                    labelToIndex.put(source, currentIndex++);
                    labelsInOrder.add(source);
                }
                
                if (!labelToIndex.containsKey(destination)) {
                    labelToIndex.put(destination, currentIndex++);
                    labelsInOrder.add(destination);
                }

                // Store edge weight in the map
                edgeWeights.put(source + "_" + destination, weight);
                edgeWeights.put(destination + "_" + source, weight); //  the graph is undirected

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize the adjacency matrix
        int numVertices = labelToIndex.size();
        System.out.println("Vertices inside main : " + numVertices);
        double[][] adjacencyMatrix = new double[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                String sourceLabel = labelsInOrder.get(i);
                String destLabel = labelsInOrder.get(j);
                String edgeKey = sourceLabel + "_" + destLabel;

                // Set weight to 0 if source and destination are the same
                if (sourceLabel.equals(destLabel)) {
                    adjacencyMatrix[i][j] = 0;
                } else {
                    adjacencyMatrix[i][j] = edgeWeights.getOrDefault(edgeKey, Double.POSITIVE_INFINITY);
                }
            }
        }

        kruskalMST(adjacencyMatrix,numVertices, labelsInOrder, labelToIndex);
    }

    static void kruskalMST(double[][] adjacencyMatrix, int numVertices, ArrayList<String> labelsInOrder, Map<String, Integer> labelToIndex){
        // Apply Kruskal's Algorithm
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (adjacencyMatrix[i][j] != 0 && adjacencyMatrix[i][j] != Double.POSITIVE_INFINITY) {
                    edges.add(new Edge(labelsInOrder.get(i), labelsInOrder.get(j), adjacencyMatrix[i][j]));
                }
            }
        }
        
        // Sort edges based on weights
        Collections.sort(edges);

        // Create a parent array for union-find operations
        int[] parent = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            parent[i] = i;
        }

        // Kruskal's Algorithm
        ArrayList<Edge> mst = new ArrayList<>();
        for (Edge edge : edges) {
            int sourceIndex = labelToIndex.get(edge.source);
            int destIndex = labelToIndex.get(edge.destination);
            int sourceParent = findParent(parent, sourceIndex);
            int destParent = findParent(parent, destIndex);

            if (sourceParent != destParent) {
                mst.add(edge);
                parent[sourceParent] = destParent;
            }
        }

        System.out.println("Minimum Spanning Tree:");
        double sum = 0.0;
        for (Edge edge : mst) {
            System.out.println(edge.source + " - " + edge.destination + " : " + edge.weight);
            sum += edge.weight;
        }
        System.out.println("Minimum Cost: "+ sum);
    }

    // Helper method to find parent of a vertex in union-find
    static int findParent(int[] parent, int vertex) {
        if (parent[vertex] != vertex) {
            parent[vertex] = findParent(parent, parent[vertex]);
        }
        return parent[vertex];
    }
}
