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
            newFirst = ((replacement.length - items.length) / 2) - 1;
            newLast = replacement.length - newFirst - 1;
            items = replacement;
        }
        if ((items.length % 2) == 0) {
            Glorp[] replacement = (Glorp[]) new Object[2 * items.length];
            System.arraycopy(items, 0, replacement, ((replacement.length - items.length) / 2), items.length);
            newFirst = ((replacement.length - items.length) / 2) - 1;
            newLast = replacement.length - newFirst - 1;
            items = replacement;
        }
    }

    public Glorp removeFirst() {
        newFirst += 1;
        size -= 1;
        this.resize_check();
        return items[newFirst];
    }

    public Glorp removeLast() {
        newLast -= 1;
        size -= 1;
        this.resize_check();
        return items[newLast];
    }

    public Glorp get(int index) {
        return items[newFirst + index + 1];
    }

    @JoshHug
    public ArrayDeque(ArrayDeque other) {
        items = (Glorp[]) new Object[other.items.length];
        size = 0;
        newFirst = 3;
        newLast = 4;

        for (int i = 0; i < other.items.length; i+=1) {
            addLast((Glorp) other.get(i));
        }
    }


}
