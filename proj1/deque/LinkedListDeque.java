package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>{
    
    private Node head;
    private Node back;
    private int size;
    private class Node {
        T item;
        Node previous;
        Node next;
        public Node(T t , Node previous ,Node next) {
            item = t;
            this.next = next;
            this.previous = previous;
        }
        public T getHelper(Node n, int index) {
            if (index == 0) {
                return n.item;
            }
            return this.getHelper(n.next , index -1);
        }
    }
    private class LinkedListDequeIterator implements Iterator<T> {
        private Node p;
        public LinkedListDequeIterator() {
            p = head.next;
        }
        @Override
        public boolean hasNext() {
            return p != back;
        }
        @Override
        public T next() {
            T returnItem = p.item;
            p = p.next;
            return returnItem;
        }
    }
    /*Iterable*/
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
    /*constructor*/
    public LinkedListDeque() {
        head = new Node(null,null,null);
        back = new Node(null,null,null);
        head.next = back;
        back.previous = head;
        this.size = 0;
    }
    public void addFirst(T item) {
        Node oldFirst = head.next;
        Node NewFirst = new Node(item, head , oldFirst);
        head.next = NewFirst;
        oldFirst.previous = NewFirst;
        this.size += 1;
    }
    public void addLast(T item) {
        Node oldLast = back.previous;
        Node NewLast = new Node(item, oldLast, back);
        oldLast.next = NewLast;
        back.previous = NewLast;
        this.size += 1;
    }
    public boolean isEmpty() {
        return (this.size == 0);
    }
    public int size() {
        return this.size;
    }
    public void printDeque() {
        Node p = head.next;
        String d = "";
        while (p != back) {
            d = d + p.item;
            d = d + " ";
            p = p.next;
        }
        System.out.println(d);
    }
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        T returnItem = head.next.item;
        Node second = head.next.next;
        head.next = second;
        second.previous = head;
        this.size -= 1;
        return returnItem;
    }
    public T removeLast() {
        if (this.isEmpty()){
            return null;
        }
        T returnItem = back.previous.item;
        Node secondLast = back.previous.previous;
        back.previous = secondLast;
        secondLast.next = back;
        this.size -= 1;
        return returnItem;
    }
    /*The first node is the 0th node*/
    public T get(int index) {
        if (this.isEmpty()){
            return null;
        }
        if (index > this.size()) {
            throw new IndexOutOfBoundsException("index out of bound");
        }
        Node p = this.head.next;
        int i = 0;
        while ( (p != back) && (i < index) ) {
            p = p.next;
            i += 1;
        }
        return p.item;
    }
    public T getRecursive(int index) {
        if (this.isEmpty()) {
            return null;
        }
        if (index >= this.size()) {
            throw new IndexOutOfBoundsException("Argument index is too long");
        }
        if (index == 0) {
            return this.head.next.getHelper(this.head.next,0);
        }
        return this.head.next.next.getHelper(this.head.next.next , index -1);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinkedListDeque)) {
            return false;
        }
        LinkedListDeque<T> l = (LinkedListDeque<T>) o;
        if (l.size() != this.size()) {
            return false;
        }
        Node T = this.head.next;
        Node O = l.head.next;
        while ( (T != this.back) && (O != l.back)) {
            if (!(T.item.equals(O.item))) {
                return false;
            }
            T = T.next;
            O = O.next;
        }
        return true;
    }
}
