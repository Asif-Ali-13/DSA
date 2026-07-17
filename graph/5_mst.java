/**
 * Problem Statement: 
 * Given a weighted, undirected, and connected graph of V vertices 
 * and E edges. The task is to find the sum of weights of the 
 * edges of the Minimum Spanning Tree.
 * 
 * Sometimes it may be asked to find the MST as well, 
 * where in the MST the edge-informations will be stored 
 * in the form {u, v}(u = starting node, v = ending node).
 * 
 */

import java.util.*;

/**
 * 
 * MST
 */
class MST {

    // Prim's Algorithm 

    /**
     * 
     * Triplet - custom struct used to store the node info. 
     */
    class Triplet {
        int weight;
        int node;
        int parentNode;

        Triplet(int weight, int node, int parentNode) {
            this.weight = weight;
            this.node = node;
            this.parentNode = parentNode;
        }
    }
    
    /**
     * Prims-Algorithm is used to find the Minimum Spanning Tree
     * and its sum.
     * 
     * TC: O(E * logE)
     * SC: O(E) + O(V)
     *
     * @param V   - number of vertices
     * @param adj - adjacency list for the graph of kind
     *              {{[node, weight], [], ...}, {}, ...}
     */
    public int primsAlgo(int V, List<List<int[]>> adj) {

        PriorityQueue<Triplet> pq = new PriorityQueue<>(
            (p, q) -> Integer.compare(p.weight, q.weight)
        );

        // We can start from any node of our choice. 
        // Here we have chosen node 0.
        pq.offer(new Triplet(0, 0, -1));

        boolean[] vis = new boolean[V];

        List<int[]> mst = new ArrayList<int[]>();
        int sum = 0;

        while (!pq.isEmpty()) {
            Triplet t = pq.poll();

            if (vis[t.node]) continue;
            vis[t.node] = true;

            sum += t.weight;
            mst.add(new int[]{t.parentNode, t.node});

            for (int i=0; i<adj.get(t.node).size(); i++) {
                int adjNode = adj.get(t.node).get(i)[0];
                int weight  = adj.get(t.node).get(i)[1];

                if (!vis[adjNode]) {
                    pq.offer(new Triplet(weight, adjNode, t.node));
                }
            }
        }
        return sum;
    }


    // Kruskal's Algorithm

    /**
     * 
     * Edge - custom struct used to store the node info. 
     * and a custom comparator 
     */
    class Edge implements Comparable<Edge> {
        int srcNode;
        int dstNode;
        int weight;

        public Edge(int srcNode, int dstNode, int weight) {
            this.srcNode = srcNode;
            this.dstNode = dstNode;
            this.weight  = weight;
        }

        // comparator function used to sort 
        // edges based on their weights in ASC order
        public int compareTo(Edge compareEdge) {
            return Integer.compare(
                this.weight, compareEdge.weight
            );
        }
    }


    /**
     * Krushkal's-Algorithm is used to find the Minimum Spanning Tree
     * and its sum.
     * 
     * TC: O(E log E) + O(E · α(V)), 
     * where α(V) is the inverse Ackermann function, 
     * which is practically a constant (< 5 for all realistic inputs).
     * 
     * SC: O(V + E)
     *
     * @param V   - number of vertices
     * @param adj - adjacency list for the graph of kind
     *              {{[node, weight], [], ...}, {}, ...}
     */
    public int krushkalAlgo(int V, List<List<int[]>> adj) {

        List<Edge> edges = new ArrayList<Edge>();

        for (int node=0; node<V; node++) {
            for (int i=0; i<adj.get(node).size(); i++) {
                
                int adjNode = adj.get(node).get(i)[0];
                int weight  = adj.get(node).get(i)[1];

                edges.add(new Edge(node, adjNode, weight));
            }
        }

        // Disjoint data Structure - see 6_DS.java
        DS ds = new DS(V);

        Collections.sort(edges);
        int mstWeight = 0;

        for (int i=0; i<edges.size(); i++) {
            int u = edges.get(i).srcNode;
            int v = edges.get(i).dstNode;
            int w = edges.get(i).weight;

            if (ds.findParent(u) != ds.findParent(v)) {
                mstWeight += w;
                ds.mergeUsingSize(u, v);
            }
        }
        return mstWeight;
    }
}