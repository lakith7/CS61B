public class UnionFind {

    private int[] holder;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        holder = new int[n];
        int index = 0;
        while (index < n) {
            holder[index] = index;
            index += 1;
        }
    }


    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex > holder.length + 1 || vertex < 0) {
            throw new IllegalArgumentException("vertex is not a valid index");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        if (holder[v1] == v1) {
            return 1;
        }
        if (holder[v1] < 0) {
            return holder[v1] * -1;
        } else {
            return holder[find(v1)] * -1;
        }
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        if (v1 < 0) {
            return v1;
        }
        return holder[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (sizeOf(v1) == sizeOf(v2)) {
            if (sizeOf(v1) == 1) {
                holder[v2] = -2;
                holder[v1] = v2;
            } else {
                int root1 = find(v1);
                int root2 = find(v2);
                holder[root2] += holder[root1];
                holder[root1] = root2;
            }
        } else if (sizeOf(v1) < sizeOf(v2)) {
            int root1 = find(v1);
            int root2 = find(v2);
            holder[root2] -= sizeOf(root1);
            holder[root1] = v2;
        } else if (sizeOf(v1) > sizeOf(v2)) {
            int root1 = find(v2);
            int root2 = find(v1);
            holder[root2] -= sizeOf(root1);
            holder[root1] = v1;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        if (holder[vertex] == vertex) {
            return vertex;
        }
        int parent1 = vertex;
        int parent2 = vertex;
        while (parent1 > 0) {
            parent2 = parent1;
            parent1 = parent(parent1);
        }
        return parent1;
    }
}
