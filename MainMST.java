import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainMST {

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

        System.out.println("Original Matrix:");
        FloydWarshallAlgorithm.printMatrix(adjacencyMatrix, labelsInOrder, numVertices);

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("Kruskal's Algorithm:");
        KruskalsAlgorithm.kruskalMST(adjacencyMatrix, numVertices, labelsInOrder, labelToIndex);

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("Prim's Algorithm:");
        PrimsAlgorithm.primMST(adjacencyMatrix, numVertices,labelsInOrder);

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("Floyd-Warshall's Algorithm:");
        double[][] result = FloydWarshallAlgorithm.floydWarshall(adjacencyMatrix,numVertices);
        System.out.println();

        FloydWarshallAlgorithm.printMatrix(result, labelsInOrder, numVertices);

    }
}