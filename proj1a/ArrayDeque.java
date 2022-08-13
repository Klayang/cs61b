
public class ArrayDeque<T>{
    T[] items;
    private int size;
    private int head;
    public ArrayDeque(){
        size = 0;
        head = 0;
        items = (T[])new Object[8];
    }
    private void resize(int capacity){
        T[] newItems = (T[])new Object[capacity];

        int firstHalf = items.length - head;
        int secondHalf = size - firstHalf;
        int newHead = head + newItems.length - items.length;

        System.arraycopy(items, head, newItems, newHead, firstHalf);
        System.arraycopy(items, 0, newItems, 0, secondHalf);

        items = newItems;
        head = newHead;
    }
    public void addFirst(T item){
        head = (head + items.length - 1) % items.length;
        items[head] = item;
        size++;
        if (size == items.length) resize(size * 2);
    }
    public void addLast(T item){
        items[(head + size) % items.length] = item;
        size++;
        if (size == items.length) resize(size * 2);
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        int p = head;
        for (int i = 0; i < size; ++i) {
            System.out.print(items[p]);
            System.out.print(' ');
            p = (p + 1) % items.length;
        }
    }
    public T removeFirst(){
        T res = items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        size--;
        if (size < items.length / 4 && size < 10) resize(items.length / 2);
        return res;
    }
    public T removeLast(){
        T res = items[(head + size - 1) % items.length];
        items[(head + size - 1) % items.length] = null;
        size--;
        if (size < items.length / 4 && size < 10) resize(items.length / 2);
        return res;
    }
    public T get(int index){
        return items[(head + index) % items.length];
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        arrayDeque.addLast(1);
        for (int i = 100; i > 1; --i)
            arrayDeque.addFirst(i);
        arrayDeque.removeFirst();
        arrayDeque.removeLast();
        arrayDeque.printDeque();
    }
}
