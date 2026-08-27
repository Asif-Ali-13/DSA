/**
 * 
 * Cheat Sheet - 
 * 1. Undirected: detect cycle  -> DFS + parent
 * 2. Directed: detect cycle    -> DFS + pathVisited[]
 * 3. Find one cycle            -> DFS + parent[]
 * 4. Count nodes in one cycle  -> cycle.size()
 * 5. Store nodes in one cycle  -> List<Integer> cycle
 * 
 * 6. Directed: all nodes in any cycle   -> SCC / Tarjan
 * 7. Undirected: all nodes in any cycle ->  Bridge finding / 
 *                                    2-edge-connected components
 */

import java.util.*;

/**
 * 
 * CycleNodesDirected - This class is to find the all cycles
 * present in the graph with all the nodes that are composing 
 * that cycle. 
 * This is for the Directed graphs only. 
 * 
 * For Undirected graphs - use tarjan's algo to remove the 
 * bridges from the graph
 * 
 */
class CycleNodesDirected {
    static int timer = 0;

    static int[] tin;
    static int[] low;

    static boolean[] inStack;

    static Deque<Integer> stack;
    static List<List<Integer>> cycleNodes;

    static void dfs(
        int node, List<List<Integer>> graph
    ) {
        tin[node] = low[node] = timer++;

        stack.push(node);
        inStack[node] = true;

        for (int neighbor : graph.get(node)) {
            // Tree edge
            if (tin[neighbor] == -1) {
                dfs(neighbor, graph);

                low[node] = Math.min(
                    low[node],
                    low[neighbor]
                );
            }
            // Back edge
            else if (inStack[neighbor]) {
                low[node] = Math.min(
                    low[node],
                    tin[neighbor]
                );
            }
        }

        // node is root of SCC
        if (low[node] == tin[node]) {
            List<Integer> component = new ArrayList<>();

            int current;
            do {
                current = stack.pop();
                inStack[current] = false;
                component.add(current);
            } while (current != node);

            // SCC with > 1 node => cycle
            if (component.size() > 1) {
                cycleNodes.add(component);
            }
            // SCC with one node
            else {
                int single = component.get(0);

                // Check self-loop
                for (int neighbor : graph.get(single)) {
                    if (neighbor == single) {
                        cycleNodes.add(Arrays.asList(single));
                        break;
                    }
                }
            }
        }
    }

    static List<List<Integer>> findAllCycleNodes(
        int n, List<List<Integer>> graph
    ) {
        tin = new int[n];
        low = new int[n];
        Arrays.fill(tin, -1);

        inStack = new boolean[n];
        stack = new ArrayDeque<>();

        cycleNodes = new ArrayList<>();
        timer = 0;

        for (int i = 0; i < n; i++) {
            if (tin[i] == -1) {
                dfs(i, graph);
            }
        }
        return cycleNodes;
    }

    public static void main(String[] args) {
        int n = 8;

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Cycle 1:
        // 0 -> 1 -> 2 -> 0

        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(0);

        // Cycle 2:
        // 3 -> 4 -> 5 -> 3

        graph.get(3).add(4);
        graph.get(4).add(5);
        graph.get(5).add(3);

        // 6 have a self loop or edge
        graph.get(6).add(0);
        graph.get(6).add(6);

        // 7 is not the part of a cycle
        graph.get(7).add(0);

        List<List<Integer>> cycleNodes = 
                            findAllCycleNodes(n, graph);
        
        System.out.println("Cycle nodes: " + cycleNodes);
        System.out.println(
            "Number of cycles in the graph : " + cycleNodes.size()
        );
    }
}
