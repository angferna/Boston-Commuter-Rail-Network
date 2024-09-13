import java.text.DecimalFormat;
import java.util.ArrayList;

public class FloydWarshallAlgorithm {

    // Run the Floyd Warshall algorithm
    static double[][] floydWarshall(double dist[][], int V)
    {
        System.out.println("Vertices: " + V);
        int i, j, k;
        for (k = 0; k < V; k++) {
            for (i = 0; i < V; i++) {
                for (j = 0; j < V; j++) {
                    if (dist[i][k] + dist[k][j]
                        < dist[i][j])
                        dist[i][j]
                            = dist[i][k] + dist[k][j];
                }
            }
        }

        // Round the values to 1 decimal place
        DecimalFormat df = new DecimalFormat("#.#");
        for (i = 0; i < V; i++) {
            for (j = 0; j < V; j++) {
                // Check if the value is infinity
                if (Double.isInfinite(dist[i][j])) {
                    continue; // Skip rounding for infinity
                }
                dist[i][j] = Double.parseDouble(df.format(dist[i][j]));
            }
        }

        return dist;

    }

    public static void printMatrix(double[][] adjacencyMatrix, ArrayList<String> labelsInOrder, int numVertices) {
        // Print the labels
        System.out.print("  ,");
        for (String label : labelsInOrder) {
            System.out.print(label + ", ");
        }
        System.out.println();

        // Print the adjacency matrix with labels
        for (int i = 0; i < numVertices; i++) {
            System.out.print(labelsInOrder.get(i) + ", ");
            for (int j = 0; j < numVertices; j++) {
                if (adjacencyMatrix[i][j] == Double.POSITIVE_INFINITY) {
                    System.out.print("INF, ");
                } else {
                    System.out.print(adjacencyMatrix[i][j] + ",   ");
                }
            }
            System.out.println();
        }
    }
}
