public class ArrayDeque<T> {
    //Some of the code is derived from lecture. That code was created by Josh Hug//
    private T[] items;
    private int size;
    private int newFirst;
    private int newLast;
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        newFirst = 3;
        newLast = 4;
    }
    public void addFirst(T a) {
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
    public void addLast(T a) {
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
    public void resizeCheck() {
        if ((items.length >= 16) && (size / items.length < 0.25)) {
            this.resizeLess();
        }
        if (size / items.length < 0.2) {
            this.resizeLess();
        }
    }
    public void resizeLess() {
        if (size == 0) {
            items = (T[]) new Object[8];
            newFirst = 3;
            newLast = 4;
        }
        if ((size % 2 == 0) && (size != 0)) {
            T[] replacement = (T[]) new Object[(2 * size)];
            int tracker = 0;
            int start = (replacement.length - size) / 2;
            int starter = start;
            while (tracker < size) {
                replacement[start] = this.get(tracker);
                tracker += 1;
                start += 1;
            }
            newFirst = starter - 1;
            newLast = newFirst + size + 1;
            items = replacement;
        }
        if ((size % 2) != 0) {
            T[] replacement = (T[]) new Object[2 * size + 1];
            int tracker = 0;
            int start = (replacement.length - size) / 2;
            int starter = start;
            while (tracker < size) {
                replacement[start] = this.get(tracker);
                tracker += 1;
                start += 1;
            }
            newFirst = starter - 1;
            newLast = newFirst + size + 1;
            items = replacement;
        }
    }
    public void resize() {
        if ((items.length % 2) == 0) {
            T[] replacement = (T[]) new Object[(2 * items.length)];
            int tracker = 0;
            int start = (replacement.length - items.length) / 2;
            while (tracker < size) {
                replacement[start] = this.get(tracker);
                tracker += 1;
                start += 1;
            }
            newFirst = (replacement.length - items.length) / 2 - 1;
            newLast = newFirst + size + 1;
            items = replacement;
        }
        if ((items.length % 2) != 0) {
            T[] replacement = (T[]) new Object[2 * items.length];
            int tracker = 0;
            int start = (replacement.length - items.length - 1) / 2 + 1;
            while (tracker < size) {
                replacement[start] = this.get(tracker);
                tracker += 1;
                start += 1;
            }
            newFirst = (replacement.length - items.length - 1) / 2;
            newLast = newFirst + size + 1;
            items = replacement;
        }
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        newFirst += 1;
        if (newFirst == items.length) {
            newFirst = 0;
        }
        size -= 1;
        T temp = items[newFirst];
        this.resizeCheck();
        return temp;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        newLast -= 1;
        if (newLast == -1) {
            newLast = (items.length - 1);
        }
        size -= 1;
        T temp = items[newLast];
        this.resizeCheck();
        return temp;
    }
    public T get(int index) {
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
        items = (T[]) new Object[other.items.length];
        size = other.size;
        newFirst = other.newFirst;
        newLast = other.newLast;
        for (int i = 0; i < other.items.length; i += 1) {
            items[i] = (T) other.items[i];
        }
    }


}
