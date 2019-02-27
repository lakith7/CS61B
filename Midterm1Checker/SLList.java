public class SLList<T> {


    private class Node {
         T item;
         Node next;

        private Node(T i, Node n) {
            item = i;
            next = n;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private Node sentinel;
    private int size;

    /**
     * Creates an empty SLList.
     */
    public SLList() {
        sentinel = new Node(null, null);
        size = 0;
    }

    public SLList(T x) {
        sentinel = new Node(null, null);
        sentinel.next = new Node(x, null);
        size = 1;
    }

        public int size(){
            return size;
        }
        public void addFirst(T x) {
            sentinel.next = new Node(x, sentinel.next);
            size = size + 1;
        }

        public void insert (T item,int index){
            if (index < 0 || index > size()) {
                throw new IllegalArgumentException();
            }
            insert(item, index, this.sentinel.next);
        }

        private void insert (T item,int index, Node x){
            if (index == 0) {
                x.next = new Node(x.item, x.next);
                x.item = item;
            } else {
                insert(item, index - 1, x.next);
            }
        }

        public static void main(String args[]) {
            SLList<Integer> test = new SLList(5);
            test.addFirst(12);
            test.insert(8, 0);



        }

    }


