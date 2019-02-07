public class ArrayDeque<Glorp> {
    //Some of the code is derived from lecture. That code was created by Josh Hug//
    public Glorp[] items;
    public int size;
    public int newFirst;
    public int newLast;

    public ArrayDeque() {
        items = (Glorp[]) new Object[8];
        size = 0;
        newFirst = 3;
        newLast = 4;
    }

    public void addFirst(Glorp a) {
        if ((newFirst - 1) == -1) {
            this.resize();
        }
        items[newFirst] = a;
        newFirst -= 1;
        size += 1;
    }

    public void addLast(Glorp a) {
        if ((newLast + 1) == items.length) {
            this.resize();
        }
        items[newLast] = a;
        newLast += 1;
        size += 1;
    }

    public boolean isEmpty() {
        if (items[newFirst + 1] == null) {
            return true;
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        System.out.print(items[newFirst + 1]);
        int index = newFirst + 2;
        while (index < newLast) {
            System.out.print(" " + items[index]);
        }
    }

    public void resize_check() {
        if ((items.length >= 16) && (size/items.length < 0.25)) {
            this.resize_less();
        }

        if (size/items.length < 0.2) {
            this.resize_less();
        }

    }

    public void resize_less() {
        Glorp[] replacement = (Glorp []) new Object[size+2];
        System.arraycopy(items, (newFirst + 1), replacement, 1, size);
        newFirst = 0;
        newLast = (replacement.length - 1);
        items = replacement;
    }

    public void resize() {
        if ((items.length % 2) != 0) {
            Glorp[] replacement = (Glorp[]) new Object[(2 * items.length) + 1];
            System.arraycopy(items, 0, replacement, ((replacement.length - items.length) / 2), items.length);
            newFirst = (replacement.length - items.length) - 1;
            newLast = newFirst + size + 1;
            items = replacement;
        }
        if ((items.length % 2) == 0) {
            Glorp[] replacement = (Glorp[]) new Object[2 * items.length];
            System.arraycopy(items, 0, replacement, ((replacement.length - items.length) / 2), items.length);
            newFirst = (replacement.length - items.length) - 1;
            newLast = newFirst + size + 1;
            items = replacement;
        }
    }

    public Glorp removeFirst() {
        newFirst += 1;
        size -= 1;
        Glorp temp = items[newFirst];
        this.resize_check();
        return temp;
    }

    public Glorp removeLast() {
        newLast -= 1;
        size -= 1;
        Glorp temp = items[newLast];
        this.resize_check();
        return temp;
    }

    public Glorp get(int index) {
        return items[newFirst + index + 1];
    }

    //The method below was written by Josh Hug. It is not my code.//
    public ArrayDeque(ArrayDeque other) {
        items = (Glorp[]) new Object[other.items.length];
        size = 0;
        newFirst = 3;
        newLast = 4;

        for (int i = 0; i < other.items.length; i+=1) {
            addLast((Glorp) other.get(i));
        }
    }

    public static void main(String[] args) {
        ArrayDeque test = new ArrayDeque<Integer> ();
        test.addLast(0);
        test.addLast(1);
        test.addLast(2);
        test.addLast(3);
        test.addLast(4);
        test.addLast(5);
        test.addLast(6);
        test.addLast(7);
        System.out.println(test.removeFirst());
        System.out.print(test.removeLast());
    }


}
