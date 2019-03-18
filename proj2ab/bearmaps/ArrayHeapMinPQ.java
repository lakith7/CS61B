package bearmaps;

import java.util.HashMap;

public class ArrayHeapMinPQ<T> implements bearmaps.ExtrinsicMinPQ<T> {

    private PriorityNode[] minHeap;
    private HashMap<T, PriorityNode> menu;
    private int maxSize;
    private int firstOpen;

    public class PriorityNode<T> {

        private T itemHolder;
        private double priorityHolder;
        private int index;

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

        public int getIndex() {
            return index;
        }

        public void alterIndex(int indexer) {
            index = indexer;
        }

    }

    public ArrayHeapMinPQ() {
        minHeap = new PriorityNode[4];
        minHeap[0] = null;
        menu = new HashMap<>();
        maxSize = 4;
        firstOpen = 1;
    }

    public PriorityNode[] returnMinHeap() {
        return minHeap;
    }

    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        if (this.size() == 0) {
        } else if ((this.size() + 1) == maxSize) {
            resize(maxSize * 2);
        } else if (maxSize == 4) {
        } else if ((this.size()) < (0.25 * maxSize)) {
            resize(this.size() * 2);
        }
        PriorityNode holder = new PriorityNode(item, priority);
        menu.put(item, holder);
        minHeap[firstOpen] = holder;
        int temp = firstOpen;
        if (size() == 1) {

        } else {
            while (minHeap[temp / 2].getPriority() > minHeap[temp].getPriority()) {
                minHeap[temp] = minHeap[temp / 2];
                minHeap[temp / 2] = holder;
                minHeap[temp].alterIndex(temp);
                minHeap[temp / 2].alterIndex(temp / 2);
                temp = temp / 2;
                if (temp == 1) {
                    break;
                }
            }
        }
        holder.alterIndex(temp);
        firstOpen += 1;
    }

    public boolean contains(T item) {
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
        if (this.size() == 0) {
            return holder;
        }
        if ((this.size()) < (maxSize * 0.25)) {
            resize(this.size() * 2);
        }
        minHeap[1] = minHeap[firstOpen - 1];
        minHeap[firstOpen - 1] = null;
        minHeap[1].alterIndex(1);
        int tracker = 1;
        firstOpen -= 1;
        while (!isHeap(tracker)) {
            int left = tracker * 2;
            int right = tracker * 2 + 1;
            PriorityNode parentNode = minHeap[tracker];
            PriorityNode leftNode = minHeap[left];
            PriorityNode rightNode = minHeap[right];
            int leftIndex = leftNode.getIndex();
            int parentIndex = parentNode.getIndex();
            if (right >= firstOpen) {
                minHeap[tracker] = leftNode;
                minHeap[left] = parentNode;
                leftNode.alterIndex(parentIndex);
                parentNode.alterIndex(leftIndex);
                tracker = left;
            } else if (leftNode.getPriority() < rightNode.getPriority()) {
                minHeap[left] = parentNode;
                minHeap[tracker] = leftNode;
                leftNode.alterIndex(parentIndex);
                parentNode.alterIndex(leftIndex);
                tracker = left;
            } else {
                int rightIndex = rightNode.getIndex();
                minHeap[right] = parentNode;
                minHeap[tracker] = rightNode;
                rightNode.alterIndex(parentIndex);
                parentNode.alterIndex(rightIndex);
                tracker = right;
            }
        }
        return holder;
    }

    public int size() {
        return menu.size();
    }

    /* Change the index */
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new java.util.NoSuchElementException();
        }
        /* Find the item first and change the priority */
        PriorityNode actualNode = menu.get(item);
        actualNode.alterPriority(priority);
        while (!isHeap(actualNode.getIndex())) {
            int index = actualNode.getIndex();
            int left = index * 2;
            int right = index * 2 + 1;
            int parent = index/2;
            int self = index;
            if (left >= firstOpen) {
                /* Left and right node don't exist */
                swim(index);
            } else if (parent == 0) {
                /* Parent node doesn't exist */
                PriorityNode selfNode = minHeap[self];
                if (right >= firstOpen) {
                    /* Only left exists */
                    sink(index, true);
                } else if (selfNode.getPriority() > minHeap[left].getPriority()) {
                    /* Left and right exist */
                    sink(index, true);
                } else if (selfNode.getPriority() > minHeap[right].getPriority()) {
                    /* Left and right exist */
                    sink(index, false);
                }
            } else if ((parent != 0) && (right >= firstOpen)) {
                PriorityNode parentNode = minHeap[parent];
                PriorityNode selfNode = minHeap[self];
                if (parentNode.getPriority() > selfNode.getPriority()) {
                    swim(index);
                } else {
                    sink(index, true);
                }
            } else if (right < firstOpen) {
                /* Right, left, and parent node exist */
                PriorityNode parentNode = minHeap[parent];
                PriorityNode selfNode = minHeap[self];
                PriorityNode rightNode = minHeap[right];
                PriorityNode leftNode = minHeap[left];
                if (parentNode.getPriority() > selfNode.getPriority()) {
                    swim(index);
                } else if (selfNode.getPriority() > leftNode.getPriority()) {
                    sink(index, true);
                } else if (selfNode.getPriority() > rightNode.getPriority()) {
                    sink(index, false);
                }
            }
        }
    }

    private void swim(int index) {
        PriorityNode selfNode = minHeap[index];
        PriorityNode parentNode = minHeap[index/2];
        minHeap[index/2] = selfNode;
        minHeap[index] = parentNode;
        selfNode.alterIndex(index/2);
        parentNode.alterIndex(index);
    }

    private void sink(int index, boolean left) {
        if (left) {
            PriorityNode selfNode = minHeap[index];
            PriorityNode leftNode = minHeap[index * 2];
            minHeap[index * 2] = selfNode;
            minHeap[index] = leftNode;
            selfNode.alterIndex(index * 2);
            leftNode.alterIndex(index);
        } else {
            PriorityNode selfNode = minHeap[index];
            PriorityNode rightNode = minHeap[index * 2 + 1];
            minHeap[index * 2 + 1] = selfNode;
            minHeap[index] = rightNode;
            selfNode.alterIndex(index * 2 + 1);
            rightNode.alterIndex(index);
        }
    }

    private void resize(int size) {
        maxSize = size;
        PriorityNode[] temp = minHeap;
        if (size == 2) {
            minHeap = new PriorityNode[3];
            minHeap[0] = null;
            minHeap[1] = temp[1];
            minHeap[2] = temp[2];
        } else {
            minHeap = new PriorityNode[size];
            minHeap[0] = null;
            for (int i = 1; i < firstOpen; i++) {
                minHeap[i] = temp[i];
            }
        }
    }

    private boolean isHeap(int startIndex) {
        int left = startIndex * 2;
        int right = startIndex * 2 + 1;
        int grandParent = startIndex/2;
        int parent  = startIndex;

        if ((left >= firstOpen) && (right >= firstOpen) && (grandParent == 0)) {
            return true;
        }

        if ((left < firstOpen) && (grandParent > 0)) {
            PriorityNode leftNode = minHeap[startIndex * 2];
            PriorityNode grandParentNode = minHeap[startIndex/2];
            PriorityNode parentNode = minHeap[startIndex];
            return ((leftNode.getPriority() >= parentNode.getPriority()) && (grandParentNode.getPriority() <= parentNode.getPriority()));
        }
        if (left >= firstOpen) {
            PriorityNode grandParentNode = minHeap[startIndex/2];
            PriorityNode parentNode = minHeap[startIndex];
            return (grandParentNode.getPriority() <= parentNode.getPriority());
        }
        if (grandParent == 0) {
            if (right >= firstOpen) {
                PriorityNode leftNode = minHeap[startIndex * 2];
                PriorityNode parentNode = minHeap[startIndex];
                return (leftNode.getPriority() > parentNode.getPriority());
            }
            PriorityNode leftNode = minHeap[startIndex * 2];
            PriorityNode rightNode = minHeap[startIndex * 2 + 1];
            PriorityNode parentNode = minHeap[startIndex];
            return ((leftNode.getPriority() >= parentNode.getPriority()) && (rightNode.getPriority() >= parentNode.getPriority()));
        }
        PriorityNode leftNode = minHeap[startIndex * 2];
        PriorityNode rightNode = minHeap[startIndex * 2 + 1];
        PriorityNode grandParentNode = minHeap[startIndex/2];
        PriorityNode parentNode = minHeap[startIndex];
        return ((leftNode.getPriority() >= parentNode.getPriority()) && (rightNode.getPriority() >= parentNode.getPriority()) && (grandParentNode.getPriority() < parentNode.getPriority()));
    }
}
