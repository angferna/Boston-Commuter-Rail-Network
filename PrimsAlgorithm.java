import java.util.ArrayList;

public class PrimsAlgorithm {

    static int minKey(double[] key, Boolean mstSet[], int V) {
        double min = Integer.MAX_VALUE;
        int min_index = -1;
    
        for (int v = 0; v < V; v++) {
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        }
    
        // Return V if no minimum key is found
        return min_index == -1 ? V : min_index;
    }
    

    static void primMST(double[][] adjacencyMatrix, int V)
    {
        int parent[] = new int[V];
        double key[] = new double[V];
        Boolean mstSet[] = new Boolean[V];
 
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
 
        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet, V);
            mstSet[u] = true;
            for (int v = 0; v < V; v++)
                if (adjacencyMatrix[u][v] != 0 && mstSet[v] == false
                    && adjacencyMatrix[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = adjacencyMatrix[u][v];
                }
        }

        printMatrix(parent, adjacencyMatrix,V);
    }

    static void primMST(double[][] adjacencyMatrix, int V, ArrayList<String> labelsInOrder) {
        int parent[] = new int[V];
        double key[] = new double[V];
        Boolean mstSet[] = new Boolean[V];
    
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
    
        key[0] = 0;
        parent[0] = -1;
    
        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet, V);
     
            // Check if minimum key found
            if (u != V) {
                mstSet[u] = true;
    
                for (int v = 0; v < V; v++) {
                    if (adjacencyMatrix[u][v] != 0 && mstSet[v] == false
                            && adjacencyMatrix[u][v] < key[v]) {
                        parent[v] = u;
                        key[v] = adjacencyMatrix[u][v];
                    }
                }
            }
        }
        //printMatrix(parent, adjacencyMatrix, V);
        printMatrix(parent, adjacencyMatrix, V, labelsInOrder);

    }
    
    private static void printMatrix(int[] parent, double[][] adjacencyMatrix, int V) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++)
            System.out.println(parent[i] + " - " + i + "\t"
                               + adjacencyMatrix[i][parent[i]]);
    }

    private static void printMatrix(int[] parent, double[][] adjacencyMatrix, int V, ArrayList<String> labelsInOrder) {
        System.out.println("Edge \tWeight");
        double sum = 0.0;
        for (int i = 1; i < V; i++) {
            if (adjacencyMatrix[i][parent[i]] != Double.POSITIVE_INFINITY) {
                String source = labelsInOrder.get(parent[i]);
                String destination = labelsInOrder.get(i);
                System.out.println(source + " - " + destination + "\t" + adjacencyMatrix[i][parent[i]]);
                sum  += adjacencyMatrix[i][parent[i]];
            }
        }
        System.out.println("Minimum Cost: "+ sum);
    }
    
}