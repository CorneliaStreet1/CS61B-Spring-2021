package deque;
import java.util.Iterator;

/**
 * @author jiangyiqing
 */
public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private class Node {
        private T item;
        private Node next;
        private Node previous;
        public Node(T item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }
    //should NOT be LinkedListDequeIterator<T> implements Iterator<T>
    //NO need for type para
    private class LinkedListDequeIterator implements Iterator<T> {
        // TODO: 2022/2/2 hasNext() & next()
        private Node wizPos;
        public LinkedListDequeIterator() {
            wizPos = head.next;
        }
        public boolean hasNext() {
            return wizPos != tail;
        }
        public T next() {
            T returnItem = wizPos.item;
            wizPos = wizPos.next;
            return returnItem;
        }
    }
    private Node head;
    private Node tail;
    private int size;
    public LinkedListDeque() {
        head = new Node(null, null, null);
        tail = new Node(null, null, null);
        head.next = tail;
        tail.previous = head;
        this.size = 0;
    }
    @Override
    public void addFirst(T item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("Can not add null value");
        }
        Node oldFirst = head.next;
        Node NewFirst = new Node(item, oldFirst, head);
        oldFirst.previous = NewFirst;
        head.next = NewFirst;
        size ++;
    }
    @Override
    public void addLast(T item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("Can not add null value");
        }
        Node oldLast = tail.previous;
        Node NewLast = new Node(item, tail, oldLast);
        oldLast.next = NewLast;
        tail.previous = NewLast;
        size ++;
    }
    /*removed isEmpty() as required ,
    Using default method in Deque interface
    @Override
    public boolean isEmpty() {
        return ( this.size == 0 );
    }*/
    @Override
    public int size() {
        return this.size;
    }
    @Override
    public void printDeque() {
        Node p = this.head.next;
        while (p != this.tail) {
            System.out.print(p.item);
            if (p == tail.previous) {
                System.out.println();
            }
            else {
                System.out.print(" ");
            }
            p = p.next;
        }
    }
    @Override
    public T removeFirst() {
        T returnItem = null;
        if (!this.isEmpty()) {
            returnItem = this.head.next.item;
            Node removeItem = this.head.next;
            this.head.next = removeItem.next;
            removeItem.next.previous = head;
            size --;
        }
        return returnItem;
    }
    @Override
    public T removeLast() {
        T returnItem = null;
        if (!this.isEmpty()) {
            returnItem = this.tail.previous.item;
            Node removeItem = this.tail.previous;
            this.tail.previous = removeItem.previous;
            removeItem.previous.next = this.tail;
            size --;
        }
        return returnItem;
    }

    /**
     * @param index 0 is the front, 1 is the next item, and so forth.
     * @return Gets the item at the given index
     */
    @Override
    public T get(int index) {
        if (this.isEmpty()) {
            return null;
        }
        if (index >= this.size() || index < 0) {
            return null;
        }
        else {
            Node p = this.head.next;
            for (int i = 0; i != index; i++) {
                p = p.next;
            }
            return p.item;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof LinkedListDeque<?> ) {
            LinkedListDeque<?> L = (LinkedListDeque<?>) o;
            if (L.size() != this.size()) {
                return false;
            }
            LinkedListDeque<?>.Node Lo = L.head.next;
            Node This = this.head.next;
            for (int i = 0 ; i < this.size() ; i++) {
                if (!This.item.equals(Lo.item)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param index Same as get, but uses recursion.
     * @return Same as get, but uses recursion.
     */
    public T getRecursive(int index) {
        if (this.isEmpty()) {
            return null;
        }
        else {
            return this.getRecursiveHelper(index , this.head.next);
        }
    }

    /**
     * A helper method for the method above
     * @param index 0 is the head.Next
     * @param n a node that is either a head nor a tail
     * @return the item in pos index
     */
    private T getRecursiveHelper(int index , Node n) {
        if (index == 0) {
            return n.item;
        }
        else {
            //the index-th item in the list starting with node n
            //is the index - 1-th item in the list starting with node n.next
            return getRecursiveHelper(index - 1, n.next);
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0 ; i < this.size(); i ++) {
            sb.append(this.get(i));
            if (i != size() - 1 ) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    public static <E> LinkedListDeque<E> of(E... items) {
        LinkedListDeque<E> returnItem = new LinkedListDeque<>();
        for (E e : items) {
            returnItem.addLast(e);
        }
        return returnItem;
    }
}
