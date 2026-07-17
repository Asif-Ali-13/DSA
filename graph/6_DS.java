/**
 * DS - Disjoint Set data structure which is a very 
 * important topic in the entire graph topic.
 * 
 * This data structure helps in answering 
 * the following question in constant time complexity. 
 * Question - 
 * whether node A and node B are in the same component or not.
 * 
 * The DS data structure is generally used for dynamic graphs. 
 */
class DS {
    int[] parent;
    int[] rank;
    int[] size;

    public DS(int n) {
        parent = new int[n];
        rank   = new int[n];
        size   = new int[n];

        for (int i=0; i<n; i++) {
            parent[i] = i;
            rank[i]   = 0;
            size[i]   = 1;
        }
    }
    
    public int findParent(int node) {
        if (node == parent[node]) return node;
        return parent[node] = findParent(parent[node]);
    }

    public void mergeUsingSize(int u, int v) {
        int pu = findParent(u);
        int pv = findParent(v);

        if (pu == pv) return;
        else if (size[pu] < size[pv]) {
            parent[pu] = pv;
            size[pv]  += size[pu];
        }
        else {
            parent[pv] = pu;
            size[pu]  += pv;
        }
    }

    public void mergeUsingRank(int u, int v) {
        int pu = findParent(u);
        int pv = findParent(v);

        if (pu == pv) return;
        else if (rank[pu] < rank[pv]) {
            parent[pu] = pv;
        }
        else if (rank[pu] > rank[pv]) {
            parent[pv] = pu;
        }
        else {
            parent[pv] = pu;
            rank[pu] += 1;
        }
    }
}