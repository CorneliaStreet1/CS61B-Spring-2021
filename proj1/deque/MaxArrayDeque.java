package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    Comparator<T> c;
    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.c = c;
    }
    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for (T item : this) {
            if (this.c.compare(item, maxItem) > 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }
    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for (T item : this) {
            if (c.compare(item, maxItem) > 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Just don't wanna write...");
    }
}
