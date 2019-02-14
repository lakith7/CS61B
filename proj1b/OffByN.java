public class OffByN implements CharacterComparator {
    private int offset;

    public OffByN(int N) {
        this.offset = N;
    }

    public boolean equalChars(char x, char y) {
        int holder = x - y;
        if (holder == offset) {
            return true;
        }

        if (holder == (-1 * offset)) {
            return true;
        }

        return false;
    }

}
