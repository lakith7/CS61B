import java.util.Set;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.HashSet;

public class MyHashMap<K, V> implements Map61B<K, V> {

    public int initialSizeHolder;
    public double loadFactorHolder;
    public LinkedList[] arrayHolder;
    public int sizeHolder;
    public HashSet<K> keySet;

    public class Node<K, V> {
        public K keyHolder;
        public V valueHolder;

        public Node(K key, V value) {
            keyHolder = key;
            valueHolder = value;
        }

        public K getKey() {
            return keyHolder;
        }

        public V getValue() {
            return valueHolder;
        }
    }

    public MyHashMap() {
        initialSizeHolder = 16;
        loadFactorHolder = 0.75;
        arrayHolder = new LinkedList[initialSizeHolder];
        sizeHolder = 0;
        keySet = new HashSet<>();
        for (int i = 0; i < initialSizeHolder; i++) {
            arrayHolder[i] = new LinkedList();
        }
    }

    public MyHashMap(int initialSize) {
        initialSizeHolder = initialSize;
        loadFactorHolder = 0.75;
        arrayHolder = new LinkedList[initialSizeHolder];
        sizeHolder = 0;
        keySet = new HashSet<>();
        for (int i = 0; i < initialSizeHolder; i++) {
            arrayHolder[i] = new LinkedList();
        }
    }

    public MyHashMap(int initialSize, double loadFactor) {
        initialSizeHolder = initialSize;
        loadFactorHolder = loadFactor;
        arrayHolder = new LinkedList[initialSizeHolder];
        sizeHolder = 0;
        keySet = new HashSet<>();
        for (int i = 0; i < initialSizeHolder; i++) {
            arrayHolder[i] = new LinkedList();
        }
    }

    public void clear() {
        sizeHolder = 0;
        initialSizeHolder = 16;
        loadFactorHolder = 0.75;
        arrayHolder = new LinkedList[initialSizeHolder];
        keySet = new HashSet<>();
    }

    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    public V get(K key) {
        if (!keySet.contains(key)) {
            return null;
        }

        for (int i = 0; i < arrayHolder.length; i++) {
            for (int y = 0; y < arrayHolder[i].size(); y++) {
                Node holder = (Node) arrayHolder[i].get(y);
                if (holder.getKey().equals(key)) {
                    return (V) holder.getValue();
                }
            }
        }
        return null;
    }

    public int size() {
        return sizeHolder;
    }

    public void resize(int size) {
        LinkedList[] temp = arrayHolder;
        arrayHolder = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            arrayHolder[i] = new LinkedList();
        }
        for (int i = 0; i < temp.length; i++) {
            for (int y = 0; y < temp[i].size(); y++) {
                Node holder = (Node) temp[i].get(y);
                K key = (K) holder.getKey();
                int hashCode = key.hashCode();
                int place = Math.floorMod(hashCode, size);
                arrayHolder[place].add(holder);
            }
        }
        initialSizeHolder = size;
    }

    public void put(K key, V value) {
        for (int i = 0; i < initialSizeHolder; i++) {
            if (arrayHolder[i] == null) {

            } else if ((arrayHolder[i].size() / initialSizeHolder) > loadFactorHolder) {
                resize(initialSizeHolder * 2);
            }
        }
        sizeHolder += 1;
        Node holder = new Node(key, value);
        int hashCode = key.hashCode();
        int place = Math.floorMod(hashCode, initialSizeHolder);
        arrayHolder[place].add(holder);
        keySet.add(key);
    }

    public Set<K> keySet() {
        return keySet;
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator() {
        return null;
    }

}
