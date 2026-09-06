/**
 * 
 * SegmentTree - class with implementation and 
 *              functions that are useful in many problems.
 */
class SegmentTree {
    int n; 
    int[] segTree;
    int[] lazy;

    public SegmentTree(int[] nums) {
        n = nums.length;
        segTree = new int[4 * n];
        lazy    = new int[4 * n];
        buildSegmentTree(0, 0, n -1, segTree, nums);
    }

    /**
     * To build the segment tree from the nums array.
     * 
     * @param i         - current index
     * @param l         - left value of node's range
     * @param r         - right value of node's range
     * @param segTree   - segment tree
     * @param nums      - nums array whose segmentTree has to be build
     */
    private void buildSegmentTree(
        int i, int l, int r, int[] segTree, int[] nums
    ) {
        if (l == r) {
            segTree[i] = nums[l];
            return;
        }

        int m = l + (r - l) / 2;
        buildSegmentTree(2 * i +1, l, m, segTree, nums);
        buildSegmentTree(2 * i +2, m +1, r, segTree, nums);

        segTree[i] = segTree[2 * i +1] + segTree[2 * i +2];
    }
    
    public void update(int index, int val) {
        updateSegmentTree(0, 0, n -1, index, val, segTree);
    }

    /**
     * To update the value at idx, TC - O(log n)
     * Also known as the point update and if there are n queries
     * then it would take TC - O(n log n)
     * 
     * @param i         - current index
     * @param l         - left value of node's range
     * @param r         - right value of node's range
     * @param idx       - idx in nums array where we need to update
     * @param val       - value to update at idx
     * @param segTree   - segment Tree
     */
    private void updateSegmentTree(
        int i, int l, int r, int idx, int val, int[] segTree
    ) {
        if (l == r) {
            segTree[i] = val;
            return;
        }
        int m = l + (r - l) / 2;

        if (idx <= m) updateSegmentTree(2 * i +1, l, m, idx, val, segTree);
        else updateSegmentTree(2 * i +2, m +1, r, idx, val, segTree);
        
        segTree[i] = segTree[2 * i +1] + segTree[2 * i +2];
    }
    
    public int sumRange(int left, int right) {
        return querySumRange(0, left, right, 0, n -1, segTree);
    }

    /**
     * To find the sum of range [start, end] inclusively
     * 
     * @param i         - current index
     * @param start     - range's start idx
     * @param end       - range's end idx
     * @param l         - left value of node's range
     * @param r         - right value of node's range
     * @param segTree   - segment Tree
     * @return            sum of the range [start, end] 
     */
    private int querySumRange(
        int i, int start, int end, int l, int r, int[] segTree
    ) {
        if (l > end || r < start) return 0;
        if (start <= l && r <= end) return segTree[i];

        int m = l + (r - l) / 2;
        return querySumRange(2 * i +1, start, end, l, m, segTree) + 
            querySumRange(2 * i +2, start, end, m +1, r, segTree);
    }

    public void updateRange(int start, int end, int val) {
        updateRangeSegmentTree(0, start, end, 0, n -1, val, segTree, lazy);
    }

    /**
     * To add the value val in range [start, end]
     * at each index in nums. 
     * Even if there are update for all n indices,
     * the TC still is O(log n) instead of O(n log n).
     * 
     * @param i         - current index
     * @param start     - range's start idx
     * @param end       - range's end idx
     * @param l         - left value of node's range
     * @param r         - right value of node's range
     * @param val       - value to be added 
     * @param segTree   - segment Tree
     * @param lazy      - lazy tree for lazy propagation
     */
    private void updateRangeSegmentTree(
        int i, int start, int end, int l, int r, 
        int val, int[] segTree, int[] lazy
    ) {
        if (lazy[i] > 0) {
            segTree[i] += (r - l +1) * val;
            
            // not a leaf node
            if (l != r) {
                lazy[2 * i +1] += val;
                lazy[2 * i +2] += val;
            }
            lazy[i] = 0;
        }

        if (l > end || r < start) return;
        
        if (l <= start && r <= end) {
            segTree[i] += (r - l +1) * val;
            
            if (l != r) {
                lazy[2 * i +1] += val;
                lazy[2 * i +2] += val;
            }
            return;
        }

        int m = l + (r - l) / 2;
        updateRangeSegmentTree(2 * i +1, start, end, l, m, val, segTree, lazy);
        updateRangeSegmentTree(2 * i +2, start, end, m +1, r, val, segTree, lazy);

        segTree[i] = segTree[2 * i +1] + segTree[2 * i +2];
    }
}
