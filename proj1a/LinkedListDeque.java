public class LinkedListDeque<T> {

    // This class was written by Josh Hug in lecture. I merely adapted it for my program. //
    private class ThingNode {
        private T item;
        private ThingNode next;
        private ThingNode previous;

        private ThingNode(T a, ThingNode b, ThingNode p) {
            item = a;
            next = b;
            previous = p;

        }

    }

    private ThingNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new ThingNode(null, null, null);
        this.sentinel.next = this.sentinel;
        this.sentinel.previous = this.sentinel;
        size = 0;
    }

    public LinkedListDeque(T a) {
        sentinel = new ThingNode(null, null, null);
        sentinel.next = new ThingNode(a, null, null);
        sentinel.previous = sentinel.next;
        sentinel.next.previous = sentinel;
        sentinel.next.next = sentinel;
        size = 1;

    }

    public void addFirst(T a) {
        this.sentinel.next = new ThingNode(a, this.sentinel.next, this.sentinel);
        this.sentinel.next.next.previous = this.sentinel.next;
        size += 1;

    }

    public void addLast(T a) {
        this.sentinel.previous = new ThingNode(a, this.sentinel, this.sentinel.previous);
        this.sentinel.previous.previous.next = this.sentinel.previous;
        size += 1;
    }

    public boolean isEmpty() {
        if (this.sentinel.next == this.sentinel) {
            return true;
        }
        return false;
    }

    public void printDeque() {
        System.out.print(this.sentinel.next.item);
        ThingNode temp = this.sentinel.next;
        while (temp.next != sentinel) {
            System.out.print(" " + temp.next.item);
            temp = temp.next;
        }
    }

    public T removeFirst() {
        T temporary = this.sentinel.next.item;
        this.sentinel.next.next.previous = this.sentinel;
        this.sentinel.next = this.sentinel.next.next;
        if (size != 0) {
            size -= 1;
        }
        return temporary;
    }

    public T removeLast() {
        T temporary = this.sentinel.previous.item;
        this.sentinel.previous = this.sentinel.previous.previous;
        this.sentinel.previous.next = this.sentinel;
        if (size != 0) {
            size -= 1;
        }
        return temporary;

    }

    public T get(int index) {
        ThingNode holder = this.sentinel.next;
        while (index != 0) {
            holder = holder.next;
            index -= 1;
        }
        return holder.item;
    }

    public int size() {
        return this.size;
    }

    //The method below was written by Josh Hug. It is not my code.//
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new ThingNode(null, null, null);
        this.sentinel.next = this.sentinel;
        this.sentinel.previous = this.sentinel;
        size = 0;

        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }

    public T getRecursive(int index) {
        LinkedListDeque<T> temporary = new LinkedListDeque<>(this);
        if (index == 0) {
            return temporary.sentinel.next.item;
        }
        temporary.removeFirst();
        return temporary.getRecursive(index - 1);
    }
}
