package org.example;

import org.example.com.ergasia.dequeue.CircularArrayDeQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        CircularArrayDeQueue<Integer> deque = new CircularArrayDeQueue<>(6);

        // adding elements
        deque.pushLast(1);
        deque.pushLast(2);
        deque.pushLast(3);
        System.out.println("After pushLast: " + deque);

        deque.pushFirst(0);
        System.out.println("After pushFirst: " + deque);

        //removing elements
        deque.popFirst();
        deque.popLast();
        System.out.println("After popFirst and popLast: " + deque);

        //resizing by adding elements
        for (int i = 4; i <= 10; i++) {
            deque.pushLast(i);
        }
        System.out.println("After adding more elements: " + deque);

        //resizing by removing elements
        while (!deque.isEmpty()) {
            deque.popFirst();
            if (deque.size() <= deque.capacity() / 4) {
                System.out.println("Resizing down: " + deque);
            }
        }
        try {
            deque.popFirst();
        } catch (NoSuchElementException e) {
            System.out.println("Can't pop from an empty queue.");
        }

        try {
            deque.pushFirst(null);
        } catch (NullPointerException e) {
            System.out.println("Can't add null element ");

        }
    }

}

