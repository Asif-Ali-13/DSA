import java.util.Arrays;

/**
 * This class is Implementing the Binary Lifting concept 
 * or DP on trees, to find the kth ancestor in log n time complexity
 */
class TreeAncestor {
    private final int states;
    private final int[][] ancestor;

    public TreeAncestor(int n, int[] parent) {
        int k = 0;
        while ((1 << k) <= n) k++;
        states = k;

        // (n, k) -> state stores the node value
        // of 2 ^ kth ancestor of current node n
        ancestor = new int[n][k];
        for (int[] ances: ancestor) {
            Arrays.fill(ances, -1); 
        }

        for (int node=0; node<n; node++) {
            // 2 ^ 0 = 1, first ancestor OR parent of current node 
            ancestor[node][0] = parent[node];
        }

        for (int j=1; j<k; j++) {
            for (int node=0; node<n; node++) {
                // this is the recursive relation to find the 2 ^ jth ancestor
                // 2 ^ j = 2 ^ (j -1) + 2 ^ (j -1) = 2 * 2 ^ (j -1)

                if (ancestor[node][j -1] != -1) {
                    ancestor[node][j] = ancestor[ ancestor[node][j -1] ][j -1];
                }
            }
        }
    }
    
    public int getKthAncestor(int node, int k) {
        int kthAncestor = node;

        // we can only make binary jumps till states
        for (int j=0; j<states; j++) {

            // if the jth bit is set then we can make that jump (2 ^ j)
            if ((k & (1 << j)) != 0) {
                kthAncestor = ancestor[kthAncestor][j];

                // if ancestor == -1 => reached root 
                // no more ancestors possible 
                if (kthAncestor == -1) return -1;
            }
        }
        return kthAncestor;
    }
}

