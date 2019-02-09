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
        if ((newFirst == -1) && (newLast == items.length)) {
            this.resize();
        }
        if (newFirst == -1) {
            newFirst = items.length - 1;
        }
        if (newLast == newFirst || items.length < 3) {
            this.resize();
        }
        items[newFirst] = a;
        newFirst -= 1;
        size += 1;
    }

    public void addLast(Glorp a) {
        if ((newLast == items.length) && (newFirst == -1)) {
            this.resize();
        }
        if (newLast == items.length) {
            newLast = 0;
        }
        if (newLast == newFirst) {
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
        System.out.print(get(0));
        int index = 1;
        while (index < size) {
            System.out.print(" " + get(index));
            index += 1;
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
        int tracker = 0;
        int start = 1;
        while (tracker < size) {
            replacement[start] = this.get(tracker);
            tracker += 1;
            start += 1;
        }
        newFirst = 0;
        newLast = (replacement.length - 1);
        items = replacement;
    }

    //Fix resize. Consider getting rid of all arguments and starting from scratch//
    public void resize() {
        if ((items.length % 2) == 0) {
            Glorp[] replacement = (Glorp[]) new Object[(2 * items.length)];
            int tracker = 0;
            int start = (replacement.length - items.length)/2;
            while (tracker < size) {
                replacement[start] = this.get(tracker);
                tracker += 1;
                start += 1;
            }
            newFirst = (replacement.length - items.length)/2 - 1;
            newLast = newFirst + size + 1;
            items = replacement;
        }
        if ((items.length % 2) != 0) {
            Glorp[] replacement = (Glorp[]) new Object[2 * items.length];
            int tracker = 0;
            int start = (replacement.length - items.length - 1)/2 + 1;
            while (tracker < size) {
                replacement[start] = this.get(tracker);
                tracker += 1;
                start += 1;
            }
            newFirst = (replacement.length - items.length - 1)/2;
            newLast = newFirst + size + 1;
            items = replacement;
        }
    }

    public Glorp removeFirst() {
        if (size == 0) {
            return null;
        }
        newFirst += 1;
        if (newFirst == items.length) {
            newFirst = 0;
        }
        size -= 1;
        Glorp temp = items[newFirst];
        this.resize_check();
        return temp;
    }

    public Glorp removeLast() {
        if (size == 0) {
            return null;
        }
        newLast -= 1;
        if (newLast == -1) {
            newLast = (items.length - 1);
        }
        size -= 1;
        Glorp temp = items[newLast];
        this.resize_check();
        return temp;
    }

    public Glorp get(int index) {
        if ((newFirst > newLast) || (newFirst == newLast)) {
            if ((newFirst + index + 1) > (items.length - 1)) {
                return items[index - (items.length - newFirst - 1)];
            } else {
                return items[newFirst + index + 1];
            }

        } else {
            return items[newFirst + index + 1];
        }
    }

    //The method below was written by Josh Hug. It is not my code.//
    public ArrayDeque(ArrayDeque other) {
        items = (Glorp[]) new Object[8];
        size = other.size;
        newFirst = other.newFirst;
        newLast = other.newLast;

        for (int i = 0; i < other.items.length; i+=1) {
            items[i] = (Glorp) other.items[i];
        }
    }

}
