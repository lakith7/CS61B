public class LinkedListDeque<Hello> {

    // This class was written by Josh Hug in lecture. I merely adapted it for my program. //
    public class ThingNode {
        public Hello item;
        public ThingNode next;
        public ThingNode previous;

        public ThingNode(Hello a, ThingNode b, ThingNode p) {
            item = a;
            next = b;
            previous = p;

        }

    }

    public ThingNode sentinel;
    public int size;

    public LinkedListDeque() {
        sentinel = new ThingNode(null, null, null);
        this.sentinel.next = this.sentinel;
        this.sentinel.previous = this.sentinel;
        size = 0;
    }

    public LinkedListDeque(Hello a) {
        sentinel = new ThingNode(null, null, null);
        sentinel.next = new ThingNode(a, null, null);
        sentinel.previous = sentinel.next;
        sentinel.next.previous = sentinel;
        sentinel.next.next = sentinel;
        size = 1;

    }

    public void addFirst(Hello a) {
        this.sentinel.next = new ThingNode(a, this.sentinel.next, this.sentinel);
        this.sentinel.next.next.previous = this.sentinel.next;
        size += 1;

    }

    public void addLast(Hello a) {
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

    //Might not work because assigning temp to sentinel and then changing temp might change sentinel too//
    public void printDeque() {
        System.out.print(this.sentinel.next.item);
        ThingNode temp = this.sentinel.next;
        while (temp.next != sentinel) {
            System.out.print(" " + temp.next.item);
            temp = temp.next;
        }
    }

    public Hello removeFirst() {
        Hello temporary = this.sentinel.next.item;
        this.sentinel.next.next.previous = this.sentinel;
        this.sentinel.next = this.sentinel.next.next;
        size -= 1;
        return temporary;
    }

    public Hello removeLast() {
        Hello temporary = this.sentinel.previous.item;
        this.sentinel.previous = this.sentinel.previous.previous;
        this.sentinel.previous.next = this.sentinel;
        size -= 1;
        return temporary;

    }

    public Hello get(int index) {
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


    public LinkedListDeque copier(LinkedListDeque other) {
        ThingNode copy = other.sentinel;
        LinkedListDeque<Hello> holder = new LinkedListDeque<>();
        while (copy.next != other.sentinel) {
            holder.addLast(copy.next.item);
            copy = copy.next;
        }
        return holder;
    }

    //The method below was written by Josh Hug. It is not my code.//
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new ThingNode(null, null, null);
        this.sentinel.next = this.sentinel;
        this.sentinel.previous = this.sentinel;
        size = 0;

        for (int i = 0; i < other.size(); i += 1) {
            addLast((Hello) other.get(i));
        }
    }

    public Hello getRecursive(int index) {
        LinkedListDeque<Hello> temporary = copier(this);
        if (index == 0) {
            return temporary.sentinel.next.item;
        }
        temporary.removeFirst();
        return temporary.getRecursive(index - 1);
    }

}
