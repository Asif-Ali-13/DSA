import java.util.*;

class Node {
    int vertex, parent;

    Node (int vertex, int parent) {
        this.vertex = vertex;
        this.parent = parent;
    }
}

/**
 * CycleDetection - contains the logic to detect the cycle in the graph
 */
class CycleDetection {

    // BFS

    /**
     * This is core function to detect the cycle in a component of a graph using BFS.
     * 
     * @param source - vertex from where BFS needs to be start
     * @param adj - adjacency list of graph
     * @param vis - visited array to check whether the vertex is visited or not.
     * @return true if cycle detected
     */
    private boolean detectCycleUsingBfs(
        int source, List<Integer>[]adj, boolean[] vis
    ) {
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(source, -1));
        vis[source] = true;

        while (!q.isEmpty()) {
            Node node = q.remove();

            for (int adjNode: adj[node.vertex]) {
                if (!vis[adjNode]) {
                    q.add(new Node(adjNode, node.vertex));
                    vis[adjNode] = true;
                }
                else if (adjNode != node.parent) return true;
            }
        }
        return false;
    }


    /**
     * This function checks for a cycle in graph using BFS traversal technique.
     * 
     * @param n - number of nodes/vertexs in the graph
     * @param adj - adjacency list of graph
     * @return true if the graph has a cycle
     */
    public boolean checkForCycleUsingBSF(int n, List<Integer>[] adj) {
        boolean[] vis = new boolean[n];

        for (int i=0; i<n; i++) {
            if (!vis[i]) {
                if (detectCycleUsingBfs(i, adj, vis)) {
                    return true;
                }
            }
        }
        return false;
    }

    // DFS

    /**
     * This is core function to detect the cycle in a component of a graph using DFS.
     * 
     * @param node - current node for the traversal
     * @param parent - parent of the current node
     * @param adj - adjacency list of graph
     * @param vis - visited array to check whether the vertex is visited or not.
     * @return true if cycle detected
     */
    private boolean detectCycleUsingDfs(
        int node, int parent, List<Integer>[]adj, boolean[] vis
    ) {
        vis[node] = true;
        
        for (int adjNode: adj[node]) {
            if (!vis[adjNode]) {
                if (detectCycleUsingDfs(adjNode, node, adj, vis)) {
                    return true;
                }
            }
            else if (adjNode != parent) return true;
        }
        return false;
    }

    /**
     * This function checks for a cycle in graph using DFS traversal technique.
     * 
     * @param n - number of nodes/vertexs in the graph
     * @param adj - adjacency list of graph
     * @return true if the graph has a cycle
     */
    public boolean checkForCycleUsingDFS(int n, List<Integer>[] adj) {
        boolean[] vis = new boolean[n];
        
        for (int i=0; i<n; i++) {
            if (!vis[i]) {
                if (detectCycleUsingDfs(i, -1, adj, vis)) {
                    return true;
                }
            }
        }
        return false;
    }
}