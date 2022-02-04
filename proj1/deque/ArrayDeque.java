package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>,Deque<T> {
    private int First;
    private int Last;
    private int size;
    private T[] items;
    public ArrayDeque() {
        items = (T[]) new Object[8];
        First = 3;
        Last = 4;
        size = 0;
    }
    private void resize(int capacity) {
        // TODO: 2022/2/2 Resize()
        T[] newItems = (T[]) new Object[capacity];
        //resizeBigger
        if (this.items.length < capacity) {
            if (this.Last == 0) {
                System.arraycopy(this.items , 0 , newItems, 0, items.length);
                First = newItems.length - 1;
                Last = this.size();
            }
            /**
             *画个示意图，会发现不管是First越过了0，还是Last越过了size - 1,以下的规律是不变的
             * 0 到Last - 1 贴近新数组左侧(0)放，First + 1到 size - 1贴近数组右端(index = length - 1 )放
             */
            else {
                System.arraycopy(items, 0, newItems, 0, Last);
                //newItems.length - x = Size() - Last ,x = newItems.length - size() + Last
                System.arraycopy(items, First + 1, newItems, newItems.length - size() + Last, size() - Last);
                First = First + newItems.length - items.length;
            }
        }
        //ResizeSmaller
        if (this.items.length > capacity) {
            if (First < Last) {
                System.arraycopy(items, First + 1, newItems, 0, this.size());
                First = newItems.length - 1;
                Last = this.size;
            }
            else {
                /**
                 *画个示意图，会发现不管是First越过了0，还是Last越过了size - 1,以下的规律是不变的
                 * 0 到Last - 1 贴近新数组左侧(0)放，First + 1到 size - 1贴近数组右端(index = length - 1 )放
                 */
                System.arraycopy(items, 0, newItems, 0, Last);
                //newItems.length - x = Size() - Last ,x = newItems.length - size() + Last
                System.arraycopy(items, First + 1, newItems, newItems.length - size() + Last, size() - Last);
                First = First + newItems.length - items.length;
            }
        }
        items = newItems;
    }
    @Override
    public int size() {
        return this.size;
    }
    @Override
    public void addFirst(T item) {
        if ( items.length == this.size() ) {
            resize(items.length * 2);
        }
        items[First] = item;
        size ++;
        if (First == 0) {
            First = items.length - 1;
        }
        else {
            First --;
        }
    }
    @Override
    public void addLast(T item) {
        if (items.length == this.size()) {
            resize(items.length * 2);
        }
        items[Last] = item;
        size ++;
        if (Last == items.length - 1) {
            Last = 0;
        }
        else {
            Last ++;
        }
    }
    /*removed isEmpty() as required ,
    Using default method in Deque interface
    @Override
    public boolean isEmpty() {
        return (this.size() == 0);
    }*/
    @Override
    public void printDeque() {
        // TODO: 2022/2/2 After get(index)
        for (int i = 0 ; i < this.size() ; i ++ ) {
            T item = get(i);
            System.out.print(item);
            if (i == this.size() - 1) {
                System.out.println();
            }
            else {
                System.out.print(" ");
            }
        }
    }
    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        int index = this.First + 1;
        if (index == this.items.length) {
            index = 0;
        }
        T returnItem = this.items[index];
        this.items[index] = null;
        size --;
        First = index;
        double UseRate = (double) this.size() / this.items.length;
        if (UseRate < 0.25 && this.items.length >= 16) {
            resize(this.items.length / 2);
        }
        return returnItem;
    }
    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        int index = this.Last - 1;
        if (index == -1) {
            index = this.items.length - 1;
        }
        T returnItem = this.items[index];
        this.items[index] = null;
        size --;
        this.Last = index;
        double UseRate = (double) this.size() / this.items.length;
        if (UseRate < 0.25 && this.items.length >= 16) {
            resize(this.items.length / 2);
        }
        return returnItem;
    }
    @Override
    public T get(int index) {
        if (index > this.size() - 1 || index < 0) {
            return null;
        }
        int ItemCount = 0,RealIndex;
        RealIndex = this.First + 1;
        if (RealIndex == this.items.length) {
            RealIndex = 0;
        }
        while (ItemCount != index) {
            RealIndex ++;
            if (RealIndex == this.items.length) {
                RealIndex = 0;
            }
            ItemCount ++;
        }
        return this.items[RealIndex];
    }
    @Override
    public boolean equals(Object o) {
        if ( o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof ArrayDeque<?>) {
            if ((((ArrayDeque<?>) o).size() != this.size())) {
                return false;
            }
            else {
                for (int i = 0 ; i < this.size() ; i ++) {
                    if (!(((ArrayDeque<?>)o).get(i)).equals(this.get(i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T> {
        int wizPos;

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
            wizPos ++;
            return returnItem;
        }
    }
}
