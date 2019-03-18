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
        if ((this.size()) < (maxSize * 0.25)) {
            resize(this.size() * 2);
        }
        minHeap[1] = minHeap[firstOpen - 1];
        minHeap[firstOpen - 1] = null;
        minHeap[1].alterIndex(1);
        int tracker = 1;
        firstOpen -= 1;
        while (!isHeap(minHeap, 1, firstOpen)) {
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
        int index = actualNode.getIndex();
        actualNode.alterPriority(priority);
        sinkOrSwim(index);


        /* Remove the node from the map */

        /* Put the item in the correct place according to the priority */

        /* What if there is no Grandparent Node or Children Node or are only one of those?*/
        /* Sink/swim the node until it's in the right position */
        /* while (!isHeap(minHeap, 1, firstOpen)) {

            int left = index * 2;
            int right = index * 2 + 1;
            int grandParent = index / 2;
            if (right >= firstOpen) {
                PriorityNode parentNode = minHeap[index];
                PriorityNode leftNode = minHeap[left];
                int leftIndex = leftNode.getIndex();
                int parentIndex = parentNode.getIndex();
                minHeap[index] = leftNode;
                minHeap[left] = parentNode;
                leftNode.alterIndex(parentIndex);
                parentNode.alterIndex(leftIndex);
                index = left;
            } else {
                PriorityNode parentNode = minHeap[index];
                PriorityNode leftNode = minHeap[left];
                PriorityNode rightNode = minHeap[right];
                int rightIndex = rightNode.getIndex();
                int leftIndex = leftNode.getIndex();
                int parentIndex = parentNode.getIndex();
                if ((parentNode.getPriority() <= leftNode.getPriority()) && (parentNode.getPriority() <= rightNode.getPriority())) {
                    PriorityNode grandParentNode = minHeap[index / 2];
                    int grandParentIndex = grandParentNode.getIndex();
                    if (parentNode.getPriority() < grandParentNode.getPriority()) {
                        minHeap[index / 2] = parentNode;
                        minHeap[index] = grandParentNode;
                        parentNode.alterIndex(grandParentIndex);
                        grandParentNode.alterIndex(parentIndex);
                        index = index / 2;
                    }
                } else if ((parentNode.getPriority() > rightNode.getPriority())) {
                    minHeap[index] = rightNode;
                    minHeap[index * 2 + 1] = parentNode;
                    rightNode.alterIndex(parentIndex);
                    parentNode.alterIndex(rightIndex);
                    index = index * 2 + 1;
                } else if ((parentNode.getPriority() > leftNode.getPriority())) {
                    minHeap[index] = leftNode;
                    minHeap[index * 2] = parentNode;
                    leftNode.alterIndex(parentIndex);
                    parentNode.alterIndex(leftIndex);
                    index = index * 2;
                } else if ((leftNode.getPriority()) > rightNode.getPriority()) {
                    minHeap[index] = rightNode;
                    minHeap[right] = parentNode;
                    rightNode.alterIndex(parentIndex);
                    parentNode.alterIndex(rightIndex);
                    index = right;
                } else if ((leftNode.getPriority()) < rightNode.getPriority()) {
                    minHeap[index] = leftNode;
                    minHeap[left] = parentNode;
                    leftNode.alterIndex(parentIndex);
                    parentNode.alterIndex(leftIndex);
                    index = left;
                }
            }
        } */

        /* consider writing a sink and swim helper method, which is basically just the while loop */

    }

    private void resize(int size) {
        maxSize = size;
        PriorityNode[] temp = minHeap;
        minHeap = new PriorityNode[size];
        minHeap[0] = null;
        for (int i = 1; i < firstOpen; i++) {
            minHeap[i] = temp[i];
        }
    }

    private boolean isHeap(PriorityNode[] heap, int startIndex, int firstOpenSlot) {

        if ((startIndex * 2) >= firstOpenSlot) {
            return true;
        }
        if (((startIndex * 2) + 1) >= firstOpenSlot) {
            if (heap[startIndex * 2].getPriority() < heap[startIndex].getPriority()) {
                return false;
            }
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

    /* This helper method situates an item in the correct place in a heap given the index of the item. Should work for ANY situation. */
    private void sinkOrSwim(int index) {

        /*
        PriorityNode parentNode = minHeap[index];
        PriorityNode leftNode = minHeap[left];
        PriorityNode rightNode = minHeap[right];
        PriorityNode grandParentNode = minHeap[grandParent];
        int rightIndex = rightNode.getIndex();
        int leftIndex = leftNode.getIndex();
        int parentIndex = parentNode.getIndex();
        int grandParentIndex = grandParentNode.getIndex();
        */
        /* While loop exits once the heap we input is actually a heap. Parent node is the node we are focusing on
        * There should be a specific ordering to the situations below. Also be aware that sometimes there is null pointer exceptions
        * because there is no grandparent node (for example) but we try to get its priority.
        * Int Index is the index of the node in the faulty heap that we are trying to fix.
        * */
        while (!isHeap(minHeap, 1, firstOpen)) {
            int left = index * 2;
            int right = index * 2 + 1;
            int parent = index;
            int grandParent = index / 2;

            /* Both left and right nodes don't exist. */
            if (left >= firstOpen) {
                PriorityNode grandParentNode = minHeap[grandParent];
                PriorityNode parentNode = minHeap[index];
                int parentIndex = parentNode.getIndex();
                int grandParentIndex = grandParentNode.getIndex();
                minHeap[grandParent] = parentNode;
                minHeap[parent] = grandParentNode;
                index = grandParent;
                grandParentNode.alterIndex(parentIndex);
                parentNode.alterIndex(grandParentIndex);
                /* Situation 0: No left or right node. Only grandparent node.
                 * Solution: If grandparent node is greater than the parent node, switch the nodes. (Must be the case)
                 * */
            /* Right node and grandparent doesn't exist */
            } else if ((right >= firstOpen) && (grandParent == 0)) {
                PriorityNode parentNode = minHeap[index];
                PriorityNode leftNode = minHeap[left];
                int leftIndex = leftNode.getIndex();
                int parentIndex = parentNode.getIndex();
                minHeap[left] = parentNode;
                minHeap[parent] = leftNode;
                index = left;
                leftNode.alterIndex(parentIndex);
                parentNode.alterIndex(leftIndex);
                /* Situation 1: No grandparent node and no right node. This means that the left node must be smaller than the parent
                 * Solution: Switch left node and parent node
                 * */
            /* Right node doesn't exist. Grandparent and left do*/
            } else if (right >= firstOpen) {
                PriorityNode parentNode = minHeap[index];
                PriorityNode leftNode = minHeap[left];
                PriorityNode grandParentNode = minHeap[grandParent];
                int leftIndex = leftNode.getIndex();
                int parentIndex = parentNode.getIndex();
                int grandParentIndex = grandParentNode.getIndex();
                if (grandParentNode.getPriority() > parentNode.getPriority()) {
                    minHeap[parent] = grandParentNode;
                    minHeap[grandParent] = parentNode;
                    index = grandParent;
                    parentNode.alterIndex(grandParentIndex);
                    grandParentNode.alterIndex(parentIndex);
                } else if (leftNode.getPriority() < parentNode.getPriority()) {
                    minHeap[left] = parentNode;
                    minHeap[parent] = leftNode;
                    index = left;
                    parentNode.alterIndex(leftIndex);
                    leftNode.alterIndex(parentIndex);
                }
                /* Situation 3: No right node.
                 * Solution: If the left node is less than the parent node, switch them. Otherwise, don't do anything.
                 * */
            /* No grandparent node */
            } else if (grandParent == 0) {
                PriorityNode parentNode = minHeap[index];
                PriorityNode leftNode = minHeap[left];
                PriorityNode rightNode = minHeap[right];
                int rightIndex = rightNode.getIndex();
                int leftIndex = leftNode.getIndex();
                int parentIndex = parentNode.getIndex();

                /* Situation 4: Parent node is greater than both of the children.
                 * Solution: Switch the parent node with the left node if the left node is less than the right node. Switch
                 * the parent node with the right node if the right node is less than the left node. Switch the parent node
                 * with the left node if the left node is equal to the right node.
                 * */
                if ((parentNode.getPriority() > leftNode.getPriority()) && (parentNode.getPriority() > rightNode.getPriority())) {
                    if (leftNode.getPriority() < rightNode.getPriority()) {
                        minHeap[left] = parentNode;
                        minHeap[parent] = leftNode;
                        index = left;
                        parentNode.alterIndex(leftIndex);
                        leftNode.alterIndex(parentIndex);
                    } else if (rightNode.getPriority() < leftNode.getPriority()) {
                        minHeap[right] = parentNode;
                        minHeap[parent] = rightNode;
                        index = right;
                        parentNode.alterIndex(rightIndex);
                        rightNode.alterIndex(parentIndex);
                    }

                /* Situation 5: Parent node is greater than the left node only. The only part is already implemented
                through the order we implemented.
                * Solution: Switch the left node and the parent node
                * */
                } else if (parentNode.getPriority() > leftNode.getPriority()) {
                    minHeap[left] = parentNode;
                    minHeap[parent] = leftNode;
                    index = left;
                    parentNode.alterIndex(leftIndex);
                    leftNode.alterIndex(parentIndex);
                } else if (parentNode.getPriority() > rightNode.getPriority()) {
                    minHeap[right] = parentNode;
                    minHeap[parent] = rightNode;
                    index = right;
                    parentNode.alterIndex(rightIndex);
                    rightNode.alterIndex(parentIndex);
                }





                /* Situation 6: Parent node is greater than the right node only.
                 * Solution: Switch the parent node and the right node.
                 * */
            /* Every node exists */
            } else {
                PriorityNode parentNode = minHeap[index];
                PriorityNode leftNode = minHeap[left];
                PriorityNode rightNode = minHeap[right];
                PriorityNode grandParentNode = minHeap[grandParent];
                int rightIndex = rightNode.getIndex();
                int leftIndex = leftNode.getIndex();
                int parentIndex = parentNode.getIndex();
                int grandParentIndex = grandParentNode.getIndex();


                /* Situation 2: Grandparent node is greater than the parent node.
                 * Solution: Switch Grandparent node and parent node
                 * */
                if (grandParentNode.getPriority() > parentNode.getPriority()) {
                    minHeap[parent] = grandParentNode;
                    minHeap[grandParent] = parentNode;
                    index = grandParent;
                    parentNode.alterIndex(grandParentIndex);
                    grandParentNode.alterIndex(parentIndex);

                    /* Situation 4: Parent node is greater than both of the children.
                     * Solution: Switch the parent node with the left node if the left node is less than the right node. Switch
                     * the parent node with the right node if the right node is less than the left node. Switch the parent node
                     * with the left node if the left node is equal to the right node.
                     * */
                } else if ((parentNode.getPriority() > leftNode.getPriority()) && (parentNode.getPriority() > rightNode.getPriority())) {
                    if (leftNode.getPriority() < rightNode.getPriority()) {
                        minHeap[left] = parentNode;
                        minHeap[parent] = leftNode;
                        index = left;
                        parentNode.alterIndex(leftIndex);
                        leftNode.alterIndex(parentIndex);
                    } else if (rightNode.getPriority() < leftNode.getPriority()) {
                        minHeap[right] = parentNode;
                        minHeap[parent] = rightNode;
                        index = right;
                        parentNode.alterIndex(rightIndex);
                        rightNode.alterIndex(parentIndex);
                    }
                                    /* Situation 5: Parent node is greater than the left node only. The only part is already implemented
            through the order we implemented.
            * Solution: Switch the left node and the parent node
            * */
                } else if (parentNode.getPriority() > leftNode.getPriority()) {
                    minHeap[left] = parentNode;
                    minHeap[parent] = leftNode;
                    index = left;
                    parentNode.alterIndex(leftIndex);
                    leftNode.alterIndex(parentIndex);

                    /* Situation 6: Parent node is greater than the right node only.
                     * Solution: Switch the parent node and the right node.
                     * */
                } else if (parentNode.getPriority() > rightNode.getPriority()) {
                    minHeap[right] = parentNode;
                    minHeap[parent] = rightNode;
                    index = right;
                    parentNode.alterIndex(rightIndex);
                    rightNode.alterIndex(parentIndex);
                }
            }
        }
    }
}
