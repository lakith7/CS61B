import java.util.Set;


/** I referenced the princeton source code for BSTMap to help me. The link I used is below.
 * @source <a href="https://algs4.cs.princeton.edu/32bst/BST.java.html">https://algs4.cs.princeton.edu/32bst/BST.java.html</a>
 */
public class BSTMap<K extends Comparable <K>, V> implements Map61B<K, V> {

    public Node root;

    public class Node {

        public Node left;
        public Node right;
        public int size;
        public K key;
        public V value;

        public Node(K key, V value, int size) {
            this.size = size;
            this.key = key;
            this.value = value;
        }

    }

    public BSTMap() {
        root = new Node(null, null, 0);
    }

    public void clear() {
        root.left = null;
        root.right = null;
        root.size = 0;
        root.key = null;
        root.value = null;
    }

    public boolean containsKey(K key) {
        if (get(key) == null) {
            return false;
        }
        return true;
    }

    public V get(K key) {
        Node helper = root;
        int x = 0;
        while (x == 0) {
            if (helper == null) {
                return null;
            }
            if (key.compareTo(helper.key) < 0) {
                helper = helper.left;
            } else if (key.compareTo(helper.key) > 0) {
                helper = helper.right;
            } else {
                return helper.value;
            }
        }
        return null;
    }

    private int size(Node n) {
        if (n == null) {
            return 0;
        }
        return n.size + size(n.left) + size(n.right);
    }

    public int size() {
        if (root == null) {
            return 0;
        }
        return size(root);
    }

    public void put(K key, V value) {
        Node helper = root;
        int x = 0;
        if (root.key == null) {
            root.key = key;
            root.value = value;
            root.size += 1;
        } else {
            while (x == 0) {
                if (key.compareTo(helper.key) < 0) {
                    if (helper.left == null) {
                        helper.left = new Node(key, value, 1);
                        x = 1;
                    }
                    helper = helper.left;
                } else if (key.compareTo(helper.key) > 0) {
                    if (helper.right == null) {
                        helper.right = new Node(key, value, 1);
                        x = 1;
                    }
                    helper = helper.right;
                } else {
                    helper.value = value;
                    x = 1;
                    helper.size = helper.left.size + helper.right.size + 1;
                }
            }
        }
    }

    public void printInOrder() {
    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public java.util.Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
