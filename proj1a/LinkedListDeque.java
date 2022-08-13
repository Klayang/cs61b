public class LinkedListDeque<T> {
    private static class TNode<T> {
        private T item;
        private TNode pre;
        private TNode next;
        public TNode(T value) {
            item = value;
            pre = null;
            next = null;
        }
    }
    private int size;
    private TNode<T> sentinel;
    public LinkedListDeque() {
        sentinel = new TNode<>(null);
        size = 0;
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
    }
    public void addFirst(T item) {
        TNode<T> node = new TNode<>(item);
        node.next = sentinel.next;
        node.pre = sentinel;
        sentinel.next.pre = node;
        sentinel.next = node;
        size++;
    }
    public void addLast(T item) {
        TNode<T> node = new TNode<>(item);
        node.next = sentinel;
        node.pre = sentinel.pre;
        sentinel.pre.next = node;
        sentinel.pre = node;
        size++;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        TNode n = sentinel.next;
        for (int i = 0; i < size; ++i) {
            System.out.print(n.item);
            System.out.print(' ');
            n = n.next;
        }
    }
    public T removeFirst() {
        if (size == 0){
            return null;
        }
        T valueOfFirst = (T) sentinel.next.item;
        sentinel.next.next.pre = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return valueOfFirst;
    }
    public T removeLast() {
        if (size == 0) return null;
        T valueOfLast = (T) sentinel.pre.item;
        sentinel.pre.pre.next = sentinel;
        sentinel.pre = sentinel.pre.pre;
        size--;
        return valueOfLast;
    }
    public T get(int index) {
        TNode<T> n = sentinel;
        for (int i = 0; i <= index; ++i) {
            n = n.next;
        }
        return n.item;
    }
    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }
    private T getRecursive(int index, TNode node) {
        if (index == 0) {
            return (T) node.item;
        }
        return getRecursive(index - 1, node.next);
    }
}
