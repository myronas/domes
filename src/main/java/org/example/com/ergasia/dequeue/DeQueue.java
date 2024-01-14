package org.example.com.ergasia.dequeue;
import java.util.Iterator;

public interface DeQueue<E> {
    void pushFirst(E elem);
    void pushLast(E elem);
    E popFirst();
    E popLast();
    E first();
    E last();
    boolean isEmpty();
    int size();
    void clear();
    Iterator<E> iterator();
    Iterator<E> descendingIterator();
}


