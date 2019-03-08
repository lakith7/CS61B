public class BookendIterator<T> implements Iterator<T> {

    Iterator<T> L;

    public BookendIterator(Iterator<T> input) {
        Iterator<T> L = input;
    }

    public boolean hasNext() {
        if (L.hasnext()) {
            return true;
        }
        return false;

    }


}
