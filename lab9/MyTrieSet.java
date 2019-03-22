import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class MyTrieSet implements TrieSet61B {

    private Node root;

    private class Node {
        private char value;
        private HashMap<Character, Node> map;
        /* isKey denotes the blue node, or the last node for the word */
        private boolean isKey;
        private Node(char x, boolean y) {
            isKey = y;
            value = x;
            map = new HashMap<Character, Node>();
        }
        private boolean color() {
            return isKey;
        }
        private void addToMap() {

        }
        private void mapClear() {
            map.clear();
        }
    }

    public MyTrieSet() {
        root = new Node('\u0000', false);
    }

    public void clear() {
        root.mapClear();
    }

    public boolean contains(String key) {
        Node start = root;
        for (int i = 0, n = key.length(); i < n; i ++) {
            char c = key.charAt(i);
            if (!start.map.containsKey(c)) {
                return false;
            }
            /* start changes to the value of c, which is a Node */
            start = start.map.get(c);
        }
        /* Does contains refer to whether the string is in the trie or whether the end of the string is a blue node */
        return start.color();
    }


    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    public List<String> keysWithPrefix(String prefix) {
        List<String> holder = new ArrayList<String>();
        Node start = root;
        /* Finds the node associated with the last char of the string. If the string isn't in the trie, it returns an
         * empty list */
        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (!start.map.containsKey(c)) {
                return holder;
            }
            start = start.map.get(c);
        }
        for (int y = 0; y < start.map.size(); y++) {

        }
        return holder;
    }


    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

}
