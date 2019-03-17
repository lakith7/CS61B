import java.util.HashMap;

public class ArrayHeapMinPQ<T> implements bearmaps.ExtrinsicMinPQ<T> {

    private PriorityNode[] minHeap;
    private HashMap<T, Double> menu;
    private HashMap<T, Integer> indexer;
    private int maxSize;
    private int firstOpen;

    public class PriorityNode<T> {

        private T itemHolder;
        private double priorityHolder;

        public PriorityNode(T item, double priority) {
            itemHolder = item;
            priorityHolder = priority;
        }

        public double getPriority() {
            return priorityHolder;
        }

        public void alterPriority(double priority) {
            priorityHolder = priority;
        }

        public T getItem() {
            return itemHolder;
        }

    }

    public ArrayHeapMinPQ() {
        minHeap = new PriorityNode[4];
        minHeap[0] = null;
        menu = new HashMap<>();
        maxSize = 4;
        firstOpen = 1;
    }

    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        menu.put(item, priority);
        if ((this.size() + 1) == maxSize) {
            resize(maxSize * 2);
        }
        if ((this.size()/maxSize) < 0.25) {
            resize(this.size() * 2);
        }
        PriorityNode holder = new PriorityNode(item, priority);
        minHeap[firstOpen] = holder;
        int temp = firstOpen;
        while (!isHeap(minHeap, 1, firstOpen)) {
            minHeap[temp] = minHeap[temp/2];
            minHeap[temp/2] = holder;
            temp = temp/2;
        }
        indexer.put(item, temp);
        firstOpen += 1;
    }

    public boolean contains(T item) {
        /*May not be constant time because some say containsKey is not constant time. Just use a different data structure if that is true*/
        return menu.containsKey(item);
    }

    public T getSmallest() {
        return (T) minHeap[1].getItem();
    }

    public T removeSmallest() {
        if (minHeap.length == 0) {
            throw new java.util.NoSuchElementException();
        }
        T holder = (T) minHeap[1].getItem();
        menu.remove(holder);
        if ((this.size()/maxSize) < 0.25) {
            resize(this.size() * 2);
        }
        /* for (each item in the array)
            move one position to the left; */
        minHeap[1] = minHeap[firstOpen - 1];
        minHeap[firstOpen - 1] = null;
        int tracker = 1;
        while (!isHeap(minHeap, 1, firstOpen)) {
            int left = tracker * 2;
            int right = tracker * 2 + 1;
            PriorityNode parentNode = minHeap[tracker];
            PriorityNode leftNode = minHeap[left];
            PriorityNode rightNode = minHeap[right];
            if (leftNode.getPriority() < rightNode.getPriority()) {
                minHeap[left] = parentNode;
                minHeap[tracker] = leftNode;
                tracker = left;
            } else {
                minHeap[right] = parentNode;
                minHeap[tracker] = rightNode;
                tracker = right;
            }
        }
        firstOpen -= 1;
        return holder;
    }

    public int size() {
        return menu.size();
    }

    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new java.util.NoSuchElementException();
        }
        /* Find the item first and change the priority */
        double oldPriority = menu.get(item);
        menu.replace(item, priority);
        int index = indexer.get(item);
        minHeap[index].alterPriority(priority);
        PriorityNode newItem = minHeap[index];


        /* Put the item in the correct place according to the priority */

        /* Replace the node with the last most node in the list */

        minHeap[index] = minHeap[firstOpen - 1];

        while (!isHeap(minHeap, 1, firstOpen)) {
            int left = index * 2;
            int right = index * 2 + 1;
            PriorityNode grandParentNode = minHeap[index / 2];
            PriorityNode parentNode = minHeap[index];
            PriorityNode leftNode = minHeap[left];
            PriorityNode rightNode = minHeap[right];
            if ((parentNode.getPriority() <= leftNode.getPriority()) && (parentNode.getPriority() <= rightNode.getPriority())) {
                if (parentNode.getPriority() < grandParentNode.getPriority()) {
                    minHeap[index / 2] = parentNode;
                    minHeap[index] = grandParentNode;
                    index = index / 2;
                }
            } else if ((parentNode.getPriority() > rightNode.getPriority())) {
                minHeap[index] = rightNode;
                minHeap[index * 2 + 1] = parentNode;
                index = index * 2 + 1;
            } else if ((parentNode.getPriority() > leftNode.getPriority())) {
                minHeap[index] = leftNode;
                minHeap[index * 2] = leftNode;
                index = index * 2;
            } else if ((leftNode.getPriority()) > rightNode.getPriority()) {
                minHeap[index] = rightNode;
                minHeap[right] = parentNode;
                index = right;
            } else if ((leftNode.getPriority()) < rightNode.getPriority()) {
                minHeap[index] = leftNode;
                minHeap[left] = parentNode;
                index = left;
            }
        }

        /* use add() to add the removed node */

        add(item, priority);

    }

    private void resize(int size) {
        maxSize = size;
        PriorityNode[] temp = minHeap;
        minHeap = new PriorityNode[size];
        minHeap[0] = null;
        for (int i = 1; temp[i] != null; i++) {
            minHeap[i] = temp[i];
        }
    }

    private boolean isHeap(PriorityNode[] heap, int startIndex, int firstOpenSlot) {

        if ((startIndex * 2) >= firstOpenSlot) {
            return true;
        }
        if (((startIndex * 2) + 1) >= firstOpenSlot) {
            return true;
        }
        int left = startIndex * 2;
        int right = startIndex * 2 + 1;
        PriorityNode parent = heap[startIndex];
        PriorityNode leftChild = heap[left];
        PriorityNode rightChild = heap[right];
        if (leftChild.getPriority() < parent.getPriority()) {
            return false;
        }
        if (rightChild.getPriority() < parent.getPriority()) {
            return false;
        }
        return (isHeap(heap, left, firstOpenSlot) && isHeap(heap, right, firstOpenSlot));
    }

}
