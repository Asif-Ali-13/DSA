/**
 * Problem Statement: 
 * 
 * There are n servers numbered from 0 to n - 1 connected by undirected 
 * server-to-server connections forming a network where 
 * connections[i] = [ai, bi] represents a connection between servers 
 * ai and bi. 
 * 
 * Any server can reach other servers directly or indirectly 
 * through the network. A critical connection is a connection that, 
 * if removed, will make some servers unable to reach some other servers. 
 * Return all critical connections in the network in any order.
 */

import java.util.*;

/**
 * TarjansAlgo - algo to find the bridge edges
 * 
 * TC - O(V+2E)
 * SC - O(V+2E) + O(3V)
 */
class TarjansAlgo {

    // global timer 
    private int timer = 1;
    
    /**
     * 
     * @param node      current node being travered
     * @param parent    parent of the current node
     * @param vis       visited array to chech whether 
     *                  the node is visited or not
     * @param adj       adjacency list - graph
     * @param tin       time of insertion 
     * @param low       lowest time of insertion taken 
     *                  from all adjacent nodes except 
     *                  the parent node
     * @param bridges   List to collect all the bridge edges
     */
    private void dfs(
        int node, int parent, boolean[] vis, 
        List<Integer>[] adj, int[] tin, int[] low, 
        List<List<Integer>> bridges
    ) {
        vis[node] = true;
        tin[node] = low[node] = timer++;

        for (int adjNode: adj[node]) {
            if (adjNode == parent) continue;

            if (!vis[adjNode]) {
                dfs(adjNode, node, vis, adj, tin, low, bridges);

                low[node] = Math.min(low[node], low[adjNode]);
                if (low[adjNode] > tin[node]) {
                    bridges.add(Arrays.asList(adjNode, node));
                }
            }
            else {
                low[node] = Math.min(low[node], low[adjNode]);
            }
        }
    }

    /**
     * 
     * @param n             no. of vertices in the graph
     * @param connections   2D array of edges 
     * @return              List of bridge edges
     */
    public List<List<Integer>> criticalConnections(
        int n, int[][] connections
    ) {
        List<Integer>[] adj = new ArrayList[n];
        for (int i=0; i<n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] conn: connections) {
            int u = conn[0];
            int v = conn[1];

            adj[u].add(v);
            adj[v].add(u);
        }

        boolean[] vis = new boolean[n];
        int[] tin = new int[n];     // time of insertion
        int[] low = new int[n];     // lowest time of insertion

        List<List<Integer>> bridges = new ArrayList<>();
        dfs(0, -1, vis, adj, tin, low, bridges);
        return bridges;
    }
}
