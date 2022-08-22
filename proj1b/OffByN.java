public class OffByN implements CharacterComparator {

    private int n;
    public OffByN (int N) {
        n = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return x - y == n || y - x == n;
    }
}
