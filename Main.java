import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

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

        DijkstraAlgorithm graphD = new DijkstraAlgorithm(V);
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

                graphD.addEdge(source, destination, weight);

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

        source = "South Station"; // Specify the source 
        System.out.println("Source: " + source);

        System.out.println("Dijkstra's Algorithm:");
        graphD.dijkstra(source);

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("Bellman-Ford's Algorithm:");
        if (!graph.vertexMap.containsKey(source)) {
            System.out.println("Source node '" + source + "' not found in the graph.");
        } else {
            graph.BellmanFord(graph, source);
        }
    }
}
