/**
 * 
 * Problem Statement: 
 * 
 * Given a graph of V vertices numbered from 0 to V-1. 
 * Find the shortest distances between every pair of vertices 
 * in a given edge-weighted directed graph. The graph is represented 
 * as an adjacency matrix of size n x n. Matrix[i][j] denotes 
 * the weight of the edge from i to j. 
 * If matrix[i][j]=-1, it means there is no edge from i to j.
 * 
 */

/**
 * FloydWarshall  
 * 
 * It is an algorithm used to find the shortest distance between each pair of vertices. 
 */
class FloydWarshall {

    /**
     * 
     * @param matrix    2-D matrix which represents the graph, 
     *                  matrix[i][j] denotes the edgeWeight from vertex i to j.
     *                  If matrix[i][j] = -1 means there is no direct edge between vertex i and j.
     */
    public void shortestDistance(int[][] matrix) {

        // no. of vertices in the graph
        int V = matrix.length;

        // for each intermediate node k between vertices i and j
        for (int k=0; k<V; k++) {

            for (int i=0; i<V; i++) {
                for (int j=0; j<V; j++) {

                    // if k is not an intermediate node
                    if (matrix[i][k] == -1 || matrix[k][j] == -1)
                        continue;

                    // if there is no direct edge between i and j
                    if (matrix[i][j] == -1) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                    else {
                        matrix[i][j] = Math.min(
                            matrix[i][j], matrix[i][k] + matrix[k][j]
                        );
                    }
                }
            }
        }
    }
}
