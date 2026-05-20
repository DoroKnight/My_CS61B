import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ArrayDeque61B<T> implements Deque61B<T>{
    private T items[];
    private int currSize;
    private int maxSize;
    public int beginIndex;
    private int endIndex;

    @SuppressWarnings("unchecked")
    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        currSize = 0;
        maxSize = 8;
        beginIndex = endIndex = 0;
    }

    @SuppressWarnings("unchecked")
    private T[] doubleSpace() {
        T[] newItems = (T[]) new Object[2 * maxSize];
        for (int i = 0; i < currSize; i++) {
            int index = (beginIndex + i) % maxSize;
            newItems[i] = items[index];
        }
        maxSize *= 2;
        beginIndex = 0;
        endIndex = currSize - 1;
        return newItems;
    }

    @SuppressWarnings("unchecked")
    private T[] reduceSpace() {
        T[] newItems = (T[]) new Object[maxSize / 2];
        for (int i = 0; i < currSize; i++) {
            int index = (beginIndex + i) % maxSize;
            newItems[i] = items[index];
        }
        maxSize /= 2;
        beginIndex = 0;
        endIndex = currSize - 1;
        return newItems;
    }

    private boolean isFull() {
        return currSize == maxSize;
    }

    private boolean isTooSmall() {
        if (currSize <= maxSize / 4) return true;
        else return false;
    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if (isFull()) items = doubleSpace();
        if (currSize == 0) items[beginIndex] = x;
        else {
            beginIndex -= 1;
            if (beginIndex < 0) beginIndex += maxSize;
            items[beginIndex] = x;
        }
        currSize += 1;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (isFull()) items = doubleSpace();
        if (currSize == 0) items[endIndex] = x;
        else {
            endIndex += 1;
            if (endIndex >= maxSize) endIndex %= maxSize;
            items[endIndex] = x;
        }
        currSize += 1;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < currSize; i++) {
            returnList.add(items[(beginIndex + i) % maxSize]);
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return currSize == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return currSize;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) return null;
        T returnValue = items[beginIndex];
        if (size() == 1) items[beginIndex] = null;
        else {
            beginIndex += 1;
            if (beginIndex == maxSize) beginIndex %= maxSize;
        }
        currSize -= 1;
        return returnValue;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) return null;
        T returnValue = items[endIndex];
        if (size() == 1) items[endIndex] = null;
        else {
            endIndex -= 1;
            if (endIndex < 0) endIndex += maxSize;
        }
        currSize -= 1;
        return returnValue;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if ( index < 0 || index >= maxSize ) return null;
        int returnIndex = beginIndex + index;
        if (returnIndex >= maxSize) returnIndex %= maxSize;
        return items[returnIndex];
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    private class dequeIterator implements Iterator<T> {
        private int pos;

        public dequeIterator() { pos = 0; }

        @Override
        public boolean hasNext() {
            return (pos < currSize);
        }

        @Override
        public T next() {
            T returnThing = items[(beginIndex + pos) % maxSize];
            pos += 1;
            return returnThing;
        }
    }

    public Iterator<T> iterator() { return new dequeIterator(); }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Deque61B)) return false;

        ArrayDeque61B<T> o = (ArrayDeque61B<T>) other;
        if (this.size() != o.size()) return false;
        for (int i = 0; i < currSize; i++) {
            if (!this.items[(this.beginIndex + i) % this.maxSize].equals(o.items[(o.beginIndex + i) % o.maxSize]))
                return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder returnString = new StringBuilder("[");
        for (int i = 0; i < currSize; i++) {
            returnString.append(items[beginIndex + i]);
            if (i != currSize - 1) returnString.append(", ");
        }
        returnString.append("]");
        return returnString.toString();
    }
}
