package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private final double loiteringRatio = 0.25;
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextLast = 3;
        nextFirst = 4;
    }
    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;//作为index ，第wizPOS个元素
        public ArrayDequeIterator() {
            wizPos = 0;
        }
        @Override
        public boolean hasNext() {
            return wizPos < size;
        }
        @Override
        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }
    private void resizeBigger() {
        T[] a = (T[]) new Object[2 * this.size];
        if (nextLast == 0) {//这种情况下旧的数组里的内容恰好是逻辑和物理上都是后插的元素在前插的元素的后面
            System.arraycopy(items, 0, a, 0, size);
            nextLast = size;
            nextFirst = items.length - 1;
        }
        //否则就是NextFirst或者NextLast其中之一越过了临界点，此时分成两部分来迁移
        //这种情况画个了图来示意
        else {
            System.arraycopy(items, 0, a, 0, nextLast);
            System.arraycopy(items, nextLast, a, nextLast + size, size - nextLast);
            nextFirst += size;
        }
        this.items = a;
    }
    private void resizeSmaller() {
        T[] newItems = (T[]) new Object[this.size / 2];
        if (nextFirst < nextLast) {//情况1，NextFirst没有越过0，或NextLast没有越过items.length(这两种情况不可能同时发生)
            System.arraycopy(items, nextFirst + 1, newItems, 0, size);
        } else {//NextFirst或NextLast其中之一越过了临界线
            //复制从NextFirst到items末尾的那一部分A，这一部分属于逻辑上靠前的那一部分
            System.arraycopy(items, nextFirst + 1, newItems, 0, items.length - nextFirst - 1);
            //复制从items[0]到items[nextFirst]的那一部分，并在新数组中把这部分放到部分A后面
            System.arraycopy(items, 0, newItems, items.length - nextFirst - 1, nextLast);
        }
        this.nextFirst = newItems.length -1;
        this.nextLast = this.size;
        this.items = newItems;
    }
    public void addFirst(T item) {
        if (this.size == this.items.length) {
            this.resizeBigger();
        }
        items[nextFirst] = item;
        size ++;
        nextFirst --;
        if (nextFirst < 0) {
            nextFirst = this.items.length -1;
        }
    }
    public void addLast(T item) {
        if (this.size == this.items.length) {
            resizeBigger();
        }
        items[nextLast] = item;
        size ++;
        nextLast ++;
        if (nextLast == items.length -1) {
            nextLast = 0;
        }
    }
    public boolean isEmpty() {
        return this.size == 0;
    }
    public int size() {
        return this.size;
    }
    public void printDeque() {
        StringBuilder SB = new StringBuilder("[");
        for (int i = 0 ; i < this.size() - 1 ; i ++) {
            SB.append(this.get(i).toString());
            SB.append(",");
        }
        SB.append(this.get(this.size() - 1).toString());
        SB.append("]");
        System.out.println(SB.toString());
    }
    public T removeFirst() {
        if (this.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        if (this == null) {
            throw new IllegalArgumentException("Null List");
        }
        T returnItem = this.items[nextFirst +1];
        this.items[nextFirst +1] = null;
        nextFirst += 1;
        if (nextFirst >= items.length) {
            nextFirst = 0;
        }
        this.size --;
        if (((double) size / items.length <= this.loiteringRatio) && (items.length >= 16)) {
            resizeSmaller();
        }
        return returnItem;
    }
    public T removeLast() {
        if (this.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        if (this == null) {
            throw new IllegalArgumentException("Null List");
        }
        T returnItem = this.items[nextLast -1];
        items[nextLast -1] = null;
        nextLast --;
        if (nextLast < 0) {
            nextLast = items.length - 1;
        }
        size --;
        if (((double) size / items.length <= this.loiteringRatio) && (items.length >= 16)) {
            resizeSmaller();
        }
            return returnItem;
    }
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index Out of Bound");
        }
        int location = nextFirst + 1 + index;
        if (location >= items.length) {
            //location = index  - (length -1 - nextFirst)
            location = location - items.length;
        }
        return items[location];
    }
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof ArrayDeque)) {
            return false;
        }
        ArrayDeque<T> a = (ArrayDeque<T>) o;
        if (a.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < this.size; i++) {
            if (!(this.get(i).equals(a.get(i)))) {
                return false;
            }
        }
        return true;
    }
    /*EXTRA VIDEO CODE
    @Override
    public String toString() {
        List<String> listOfItems = new ArrayList<>();
        for (T x : this) {
            listOfItems.add(x.toString());
        }
        return "{" + String.join(", ", listOfItems) + "}";
    }
*/
    //EXTRA VIDEO CODE
    public static <Glerp> ArrayDeque<Glerp> of(Glerp... stuff) {
        ArrayDeque<Glerp> returnDeque = new ArrayDeque<Glerp>();
        for (Glerp x : stuff) {
            returnDeque.addLast(x);
        }
        return returnDeque;
    }
}
