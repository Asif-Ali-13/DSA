/**
 * Problem Statement: 
 * Given a weighted, directed and connected graph of V vertices and E edges, 
 * Find the shortest distance of all the vertices from the source vertex S. 
 * 
 * Note: If the Graph contains a negative cycle 
 * then return an array consisting of only -1.
 * 
 * It works even when there are negative edge weights and 
 * can detect negative cycles (unlike Dijkstra).
 * 
 * Where Dijkstra Fails: 
 * When there are negative edges or a negative cycle 
 * because it may loop forever or give incorrect results.
 * 
 * Negative Cycle: 
 * A cycle where the total path weight is negative, 
 * causing the distance to decrease endlessly.
 * For undirected graphs, treat each edge as two directed edges.
 */

import java.util.*;

/**
 * BellmanFord - Algorithm to the shortest distance to all vertices from the source. 
 *              It can handle the NEGATIVE cycles as well where DIJKSTRA fails. 
 */
class BellmanFord {
    final static int INF = (int)1e9;

    /**
     * 
     * @param source initial vertex from where the distance is calculated 
     * @param edges  edges of the graph - {startVertex, endVertex, edgeWeight}
     * @param V      no. of vertices in the graph
     * @return       int array filled with the shortest distance 
     *               to reach the vertex from source
     *               OR [-1] if graph contains negative cycle
     */
    static int[] bellman(int source, List<Integer>[] edges, int V) {
        
        int[] dist = new int[V];
        Arrays.fill(dist, INF);

        // Relax the edges for V -1 times
        for (int i=0; i<V -1; i++) {
            for (List<Integer> edge: edges) {

                int u = edge.get(0);    // startVertex
                int v = edge.get(1);    // endVertex
                int w = edge.get(2);    // edgeWeight

                // Relaxation step
                if (dist[u] != INF && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        for (List<Integer> edge: edges) {
            int u = edge.get(0);
            int v = edge.get(1);
            int w = edge.get(2);

            // detects the NEGATIVE cycle
            if (dist[u] != INF && dist[u] + w < dist[v]) {
                return new int[]{-1};   // detected 
            }
        }

        return dist;
    }
}  