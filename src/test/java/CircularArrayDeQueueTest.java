

import org.example.com.ergasia.dequeue.CircularArrayDeQueue;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CircularArrayDeQueueTest {

    @Test
    public void testCircularArrayDeQueue() {
        // Initialize a new queue with a large capacity to test the dynamic resizing
        CircularArrayDeQueue<Integer> queue = new CircularArrayDeQueue<>(100000);

        // Check that the new queue is empty
        assertTrue(queue.isEmpty(), "Queue should be empty initially");
        int count = 100000;
        for (int i = 0; i < count; i++) {
            queue.pushLast(i);
            assertEquals(i + 1, queue.size(), "Queue size should increment with each push");
            assertEquals(Integer.valueOf(0), queue.first(), "Front element should remain the first element pushed");
        }

        // Dequeue all elements one by one
        int current = 0;
        while (!queue.isEmpty()) {
            assertEquals(Integer.valueOf(current), queue.first(), "Front element should match the current index");
            assertEquals(Integer.valueOf(current), queue.popFirst(), "Popped element should match the current index");
            current++;
        }

        // check if queue is empty
        assertTrue(queue.isEmpty(), "Queue should be empty after all elements are popped");
    }

    // Tests pushing el to the front and popping them from the back of the queue.
    @Test
    void pushFirstAndPopLast() {
        CircularArrayDeQueue<Integer> queue = new CircularArrayDeQueue<>(5);
        queue.pushFirst(1);
        queue.pushFirst(2);
        assertEquals(1, queue.popLast());
        assertEquals(2, queue.popLast());
        assertTrue(queue.isEmpty());
    }


    @Test
    void popFromEmptyQueue() {
        CircularArrayDeQueue<Integer> queue = new CircularArrayDeQueue<>(5);
        assertThrows(NoSuchElementException.class, queue::popFirst);
        assertThrows(NoSuchElementException.class, queue::popLast);
    }

    // if size is bigger than initial capacity
    @Test
    void dynamicResizing() {
        CircularArrayDeQueue<Integer> queue = new CircularArrayDeQueue<>(2);
        queue.pushLast(1);
        queue.pushLast(2);
        // Trigger resize
        queue.pushLast(3);
        assertEquals(3, queue.size());
        assertEquals(1, queue.popFirst());
        assertEquals(2, queue.popFirst());
        assertEquals(3, queue.popFirst());
    }


    @Test
    void clearQueue() {
        CircularArrayDeQueue<Integer> queue = new CircularArrayDeQueue<>(5);
        queue.pushLast(1);
        queue.pushLast(2);
        queue.clear();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    // forward iteration elements
    @Test
    void iteratorTest() {
        CircularArrayDeQueue<Integer> queue = new CircularArrayDeQueue<>(5);
        queue.pushLast(1);
        queue.pushLast(2);
        queue.pushLast(3);

        Iterator<Integer> iterator = queue.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertFalse(iterator.hasNext());
    }

    // reverse iteration over queues' elements
    @Test
    void descendingIteratorTest() {
        CircularArrayDeQueue<Integer> queue = new CircularArrayDeQueue<>(5);
        queue.pushLast(1);
        queue.pushLast(2);
        queue.pushLast(3);

        Iterator<Integer> iterator = queue.descendingIterator();
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    // modification throw exceptions.
    @Test
    void concurrentModification() {
        CircularArrayDeQueue<Integer> queue = new CircularArrayDeQueue<>(5);
        queue.pushLast(1);
        queue.pushLast(2);

        Iterator<Integer> iterator = queue.iterator();
        queue.pushLast(3); // This should cause the iterator to throw an exception
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void pushNullElements() {
        CircularArrayDeQueue<Integer> queue = new CircularArrayDeQueue<>(5);
        assertThrows(NullPointerException.class, () -> queue.pushFirst(null));
        assertThrows(NullPointerException.class, () -> queue.pushLast(null));
    }
}

