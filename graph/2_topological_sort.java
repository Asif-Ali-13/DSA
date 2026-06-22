import java.util.*;

/**
 * This class contains function to find the topologically sorted order of graph 
 * using BFS (kahn's Algorithm) and DFS traversals.
 * 
 * If cycle exists then return an empty array. 
 */
class TopoSort {
    
    // using DFS

    /**
     * Store the reverse of topological order in stack via DFS traversal
     * 
     * @param node  - current node 
     * @param adj   - adjacency list of graph
     * @param state - state of the node (0 -> unvisited, 1 -> visiting, 2 -> visited)
     * @param st    - stack to store the node in reverse Order of Topology
     */
    private boolean topoSortUsingDfs(
        int node, List<Integer>[] adj, int[] state, Stack<Integer> st
    ) {
        state[node] = 1;    // currently visiting 

        for (int adjNode: adj[node]) {
            // Back Edge found -> cycle exists
            if (state[adjNode] == 1) {
                return false;
            }

            if (state[adjNode] == 0) {
                if (!topoSortUsingDfs(adjNode, adj, state, st)) {
                    return false;
                }
            }
        }
        state[node] = 2;    // completely processed
        st.push(node);
        return true;
    }

    /**
     * Find the topological sorted order of vertices of the graph adj using DFS
     * 
     * @param adj - adjacency list of graph
     * @return      array of n nodes in topological order
     */
    public int[] findTopologicalSortUsingDfs(List<Integer>[] adj) {
        int n = adj.length;

        Stack<Integer> st = new Stack<>();
        // 0 -> unvisited, 1 -> visiting, 2 -> visited
        int[] state = new int[n];

        for (int node=0; node<n; node++) {
            if (state[node] == 0) {
                if (!topoSortUsingDfs(node, adj, state, st)) {
                    return new int[] {};    // cycle detected
                }
            }
        }

        int idx    = 0;
        int[] topo = new int[n];

        while (!st.isEmpty()) {
            topo[idx++] = st.pop();
        }
        return topo;
    }


    // using BFS

    /**
     * Find the topological sorted order of vertices of the graph adj using BFS
     * 
     * @param adj - adjacency list of graph
     * @return      array of n nodes in topological order
     */
    public int[] findTopologicalSortUsingBfs(List<Integer>[] adj) {
        int n = adj.length;

        int[] indegree = new int[n];
        for (int node=0; node<n; node++) {
            for (int adjNode: adj[node]) {
                indegree[adjNode]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for (int node=0; node<n; node++) {
            if (indegree[node] == 0) q.add(node);
        }

        int idx    = 0;
        int[] topo = new int[n];

        while (!q.isEmpty()) {
            int node = q.remove();
            topo[idx++] = node;

            for (int adjNode: adj[node]) {
                indegree[adjNode]--;
                if (indegree[adjNode] == 0) q.add(adjNode);
            }
        }
        // if the idx != n => means there would probably be a cycle 
        // return empty array in that case
        return idx == n ? topo : new int[]{};
    }
}
