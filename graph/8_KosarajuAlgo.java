/**
 * Problem Statement: 
 * 
 * Given a Directed Graph with V vertices 
 * (Numbered from 0 to V-1) and E edges, Find the 
 * number of strongly connected components in the graph..
 * 
 * In a directed graph, strongly connected components (SCCs) 
 * are subsets of nodes where every node is reachable from 
 * every other node within the same subset. 
 */

import java.util.*;

/**
 * KosarajuAlgo
 * 
 * TC - O(V + E)
 * SC - O(V + E)
 */
class KosarajuAlgo {

    /**
     * 
     * @param V     no. of vertices in the graph
     * @param adj   adjacency list of graph
     * @return      no. of strongly connected components
     */
    public int kosaraju(int V, List<Integer>[] adj) {
        
        boolean[] vis = new boolean[V];
        Stack<Integer> st = new Stack<>();

        for (int node=0; node<V; node++) {
            if (!vis[node]) {
                dfs(node, adj, vis, st);
            }
        }

        List<Integer>[] adjT = new ArrayList[V];
        for (int i=0; i<V; i++) {
            adjT[i] = new ArrayList<>();
        }
        Arrays.fill(vis, false);

        for (int node=0; node<V; node++) {
            for (int adjNode: adj[node]) {
                adjT[adjNode].add(node);
            }
        }

        int scc = 0;
        while (!st.isEmpty()) {
            int node = st.pop();

            if (!vis[node]) {
                scc++;
                dfsT(node, adjT, vis);
            }
        }
        return scc;
    }

    /**
     * 
     * @param node  current traversing node
     * @param adj   adjacency list of graph
     * @param vis   visited array
     * @param st    stack to store the nodes 
     *              in finished Time order
     */
    private void dfs(
        int node, List<Integer>[] adj, 
        boolean[] vis, Stack<Integer> st
    ) {
        vis[node] = true;

        for (int adjNode: adj[node]) {
            if (!vis[adjNode]) {
                dfs(adjNode, adj, vis, st);
            }
        }
        st.push(node);
    }

    /**
     * 
     * @param node  current traversing node
     * @param adjT  adjacency list of Transposed graph
     * @param vis   visited array
     */
    private void dfsT(
        int node, List<Integer>[] adjT, boolean[] vis
    ) {
        vis[node] = true;

        for (int adjNode: adjT[node]) {
            if (!vis[adjNode]) {
                dfsT(adjNode, adjT, vis);
            }
        }
    }
}
