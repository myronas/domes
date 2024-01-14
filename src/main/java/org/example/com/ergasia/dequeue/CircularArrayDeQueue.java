package org.example.com.ergasia.dequeue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class CircularArrayDeQueue<E> implements DeQueue<E> {
    private E[] array;
    private int f = 0;
    private int r = 0;
    private int size = 0;
    private volatile int modCount = 0;

    // Constructor
    @SuppressWarnings("unchecked")
    public CircularArrayDeQueue(int capacity) {
        int actualCapacity = 1;
        while (actualCapacity < capacity) {
            actualCapacity *= 2;
        }
        array = (E[]) new Object[actualCapacity];
    }

    // Checks if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns the number of elements in the queue
    public int size() {
        return size;
    }

    // Inserts an element at the front of the queue
    public void pushFirst(E elem) {
        if (elem == null) throw new NullPointerException("Cannot push null element");
        if (size == array.length) resize(array.length * 2);
        f = (f - 1 + array.length) % array.length;
        array[f] = elem;
        size++;
        modCount++;
    }

    // Inserts an element at the end of the queue
    public void pushLast(E elem) {
        if (elem == null) throw new NullPointerException("Cannot push null element");
        if (size == array.length) resize(array.length *2);
        array[r] = elem;
        r = (r + 1) % array.length;
        size++;
        modCount++;
    }

    // Removes and returns the element at the front of the queue
    public E popFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        E item = array[f];
        array[f] = null;
        f = (f + 1) % array.length;
        size--;
        if (size > 0 && size == array.length / 4) resize(size);
        modCount++;
        return item;
    }

    // Removes and returns the element at the end of the queue
    public E popLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        r = (r - 1 + array.length) % array.length;
        E elem = array[r];
        array[r] = null;
        size--;
        if (size > 0 && size == array.length / 4) resize(array.length / 2); // resize down
        modCount++;
        return elem;
    }

    // Returns the first element of the queue
    public E first() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return array[f];
    }

    // Returns the last element of the queue
    public E last() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int lastIndex = (r - 1 + array.length) % array.length;
        return array[lastIndex];
    }

    // Clears the queue
    public void clear() {
        f = 0;
        r = 0;
        size = 0;
        modCount++;
    }

    // Returns an iterator over the elements of the queue in proper sequence
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = f;
            private int count = 0;
            private final int expectedModCount = modCount;

            public boolean hasNext() {
                return count < size;
            }

            public E next() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) throw new NoSuchElementException();
                E item = array[current];
                current = (current + 1) % array.length;
                count++;
                return item;
            }
        };
    }

    // Returns an iterator over the elements of the queue in reverse sequential order
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            private int current = (r - 1 + array.length) % array.length;
            private int count = 0;
            private final int expectedModCount = modCount;

            public boolean hasNext() {
                return count < size;
            }

            public E next() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) throw new NoSuchElementException();
                E item = array[current];
                current = (current - 1 + array.length) % array.length;
                count++;
                return item;
            }
        };
    }


    private void resize(int newCapacity) {
        int capacity = 1;
        while (capacity < newCapacity) {
            capacity *= 2;
        }
        @SuppressWarnings("unchecked")
        E[] newArray = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(f + i) % array.length];
        }
        array = newArray;
        f = 0;
        r = size;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int i = f;
        for (int j = 0; j < size; j++) {
            sb.append(array[i].toString());
            if (j < size - 1) {
                sb.append(", ");
            }
            i = (i + 1) % array.length;
        }
        sb.append("]");
        return sb.toString();
    }
    public int capacity(){
        return array.length;
    }

}



