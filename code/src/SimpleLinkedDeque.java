import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A doubly-linked list implementation of a double-ended queue. Let n denote the number of list
 * nodes within the deque. Then, as each list node is bounded by O(1) memory complexity (see the
 * ListNode class Javadoc for further details), this implementation's memory complexity is clearly
 * bounded by O(n).
 *
 * @param <T> Type of element to store within the deque.
 */
public class SimpleLinkedDeque<T> implements SimpleDeque<T> {
    /**
     * Represents a node in a doubly-linked list. As only a single element, along with two
     * references are stored, it is clear that this data structure is bounded by O(1) memory
     * complexity.
     *
     * @param <T> The type of element to be stored
     */
    private class ListNode<T> {
        /** Denotes the element stored by the list node. */
        T element;

        /** Provides a reference to the next list node in the doubly linked list. */
        ListNode<T> next;

        /** Provides a reference to the previous list node in the doubly linked list. */
        ListNode<T> previous;

        /**
         * Constructs a new list node with the given element.
         *
         * @param element the element to store within the node
         */
        private ListNode(T element) {
            this.element = element;
            this.next = null;
            this.previous = null;
        }
    }

    /** The first node in the deque. */
    private ListNode<T> head;

    /** The last node in the deque. */
    private ListNode<T> tail;

    /** The maximum number of elements allowed to be stored within the deque. */
    private int capacity;

    /** The current number of elements stored within the deque. */
    private int size;

    /** Used to represent a deque of unlimited capacity. */
    private static final int UNLIMITED = -1;

    /**
     * Constructs a new linked list based deque with unlimited capacity.
     */
    public SimpleLinkedDeque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.capacity = UNLIMITED;
    }

    /**
     * Constructs a new linked list based deque with limited capacity.
     *
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0
     */
    public SimpleLinkedDeque(int capacity) throws IllegalArgumentException {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Invalid capacity");
        }
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.capacity = capacity;
    }

    /**
     * Constructs a new linked list based deque with unlimited capacity, and initially 
     * populates the deque with the elements of another SimpleDeque.
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @requires otherDeque != null
     */
    public SimpleLinkedDeque(SimpleDeque<? extends T> otherDeque) {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.capacity = UNLIMITED;
        Iterator<? extends T> reverse = otherDeque.reverseIterator();
        while (reverse.hasNext()) {
            this.pushLeft(reverse.next());
        }
    }
    
    /**
     * Constructs a new linked list based deque with limited capacity, and initially 
     * populates the deque with the elements of another SimpleDeque.
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0 or size of otherDeque is > capacity
     */
    public SimpleLinkedDeque(int capacity, SimpleDeque<? extends T> otherDeque)
            throws IllegalArgumentException {
        if (capacity <= 0 || otherDeque.size() > capacity) {
            throw new IllegalArgumentException("Invalid capacities");
        }
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.capacity = capacity;
        Iterator<? extends T> reverse = otherDeque.reverseIterator();
        while (reverse.hasNext()) {
            this.pushLeft(reverse.next());
        }
    }

    /**
     * Checks whether the deque is empty. This method is clearly bounded by O(1) time complexity
     * time as all that occurs is a simple conditional check between the return value of another
     * method also bounded by O(1) time complexity, and the constant 0.
     *
     * @return true if the deque is empty, otherwise false.
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the deque is full. This method is clearly bounded by O(1) time complexity
     * as all that occurs is a simple conditional check between the return value of another
     * method also bounded by O(1) time complexity, and the value of this.capacity.
     *
     * @return true if the deque is full, otherwise false.
     */
    @Override
    public boolean isFull() {
        // UNLIMITED is negative and hence will never be equal to the size, hence this edge case
        // is already handled by the choice of sentinel value for unlimited deque size
        return this.capacity == this.size();
    }

    /**
     * Returns the size of the deque. Clearly, as all that occurs is a simple return of a variable,
     * this method is bounded by O(1) time complexity.
     *
     * @return The number of elements stored in the deque.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Pushes an element to the left of the deque. Clearly, as we are using a doubly-linked list,
     * all operations are either conditional checks, maths, or assignments. No elements are required
     * to be 'shifted'. Thus, this method is bounded by O(1) time complexity.
     *
     * @param e Element to push
     * @throws RuntimeException if the deque is already full
     */
    @Override
    public void pushLeft(T e) throws RuntimeException {
        if (this.isFull()) {
            throw new RuntimeException("Deque full.");
        }
        ListNode<T> toAdd = new ListNode<>(e);

        if (this.isEmpty()) {
            this.tail = toAdd; // Both head and tail point to this element
        } else {
            this.head.previous = toAdd;
            toAdd.next = this.head;
        }
        this.head = toAdd;
        this.size++;
    }

    /**
     * Pushes an element to the right of the deque. Clearly, as we are using a doubly-linked list,
     * all operations are either conditional checks, maths, or assignments. No elements are required
     * to be 'shifted'. Thus, this method is bounded by O(1) time complexity.
     *
     * @param e Element to push
     * @throws RuntimeException if the deque is already full
     */
    @Override
    public void pushRight(T e) throws RuntimeException {
        if (this.isFull()) {
            throw new RuntimeException("Deque full.");
        }
        ListNode<T> toAdd = new ListNode<>(e);

        if (this.isEmpty()) {
            this.head = toAdd; // Both head and tail point to this element
        } else {
            this.tail.next = toAdd;
            toAdd.previous = this.tail;
        }
        this.tail = toAdd;
        this.size++;
    }

    /**
     * Returns the element at the left of the deque, but does not remove it. Clearly, the only
     * operations are a conditional check and a return statement. Thus, this method is bounded by
     * O(1) time complexity.
     *
     * @returns the leftmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T peekLeft() throws NoSuchElementException {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }
        return this.head.element;
    }

    /**
     * Returns the element at the right  of the deque, but does not remove it. Clearly, the only
     * operations are a conditional check and a return statement. Thus, this method is bounded by
     * O(1) time complexity.
     *
     * @returns the rightmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T peekRight() throws NoSuchElementException {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }
        return this.tail.element;
    }

    /**
     * Removes and returns the element at the left of the deque. As all operations are either
     * conditional checking/assignment, maths, or calling of methods bounded by O(1) time
     * complexity, it is clear that this method must also be bounded by O(1) time complexity.
     *
     * @returns the leftmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T popLeft() throws NoSuchElementException {
        T toPop = this.peekLeft();
        if (this.size() == 1) {
            // Deque is now empty.
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.next;
            this.head.previous = null;
        }
        this.size--;
        return toPop;
    }

    /**
     * Removes and returns the element at the right of the deque. As all operations are either
     * conditional checking/assignment, maths, or calling of methods bounded by O(1) time
     * complexity, it is clear that this method must also be bounded by O(1) time complexity.
     *
     * @returns the rightmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T popRight() throws NoSuchElementException {
        T toPop = this.peekRight();
        if (this.size() == 1) {
            // Deque is now empty.
            this.head = null;
            this.tail = null;
        } else {
            this.tail = this.tail.previous;
            this.tail.next = null;
        }
        this.size--;
        return toPop;
    }

    /**
     * Returns an iterator for the deque in left to right sequence.
     *
     * The methods hasNext() and next() in the Iterator are clearly bounded by O(1) time complexity
     * as they simply perform conditional checks and access list node parameters.
     *
     * The remove() method in the iterator has not been implemented.
     *
     * We have assumed that the elements in the deque will never change while the iterator is being
     * used.
     *
     * @returns an iterator over the elements in in order from leftmost to rightmost.
     */
    @Override
    public Iterator<T> iterator() {
        /**
         * As only a single list node reference was stored, it is clear that this iterator is
         * bounded by O(1) memory complexity.
         */
        Iterator<T> leftToRight = new Iterator<>() {
            /** Position of current node. */
            private ListNode<T> currentNode = null;

            /**
             * Checks whether the deque has another element. As explained above, this method is
             * bounded by O(1) time complexity.
             *
             * @return true if we are yet to reach the deque tail, and the deque is not empty,
             * false otherwise.
             */
            @Override
            public boolean hasNext() {
                return !(isEmpty() || currentNode == tail);
            }

            /**
             * Provides the next element in the deque. As explained above, this method is bounded by
             * O(1) time complexity.
             *
             * @throws NoSuchElementException if no more elements are left
             *
             * @return the next element in the deque.
             */
            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException("No elements left");
                } else if (currentNode == null) {
                    currentNode = head;
                } else {
                    currentNode = currentNode.next;
                }
                return currentNode.element;
            }
        };
        return leftToRight;
    }

    /**
     * Returns an iterator for the deque in right to left sequence.
     *
     * The methods hasNext() and next() in the Iterator are clearly bounded by O(1) time complexity
     * as they simply perform conditional checks and access list node parameters.
     *
     * The remove() method in the iterator has not been implemented.
     *
     * We have assumed that the elements in the deque will never change while the iterator is
     * being used.
     *
     * @returns an iterator over the elements in in order from rightmost to leftmost.
     */
    @Override
    public Iterator<T> reverseIterator() {
        /**
         * As only a single list node reference was stored, it is clear that this iterator is
         * bounded by O(1) memory complexity.
         */
        Iterator<T> rightToLeft = new Iterator<>() {
            /** Position of current node. */
            private ListNode<T> currentNode = null;

            /**
             * Checks whether the deque has another element. As explained above, this method is
             * bounded by O(1) time complexity.
             *
             * @return true if we are yet to reach the deque head, and the deque is not empty,
             * false otherwise.
             */
            @Override
            public boolean hasNext() {
                return !(isEmpty() || currentNode == head);
            }

            /**
             * Provides the next element in the deque. As explained above, this method is bounded by
             * O(1) time complexity.
             *
             * @throws NoSuchElementException if no more elements are left
             *
             * @return the next element in the deque.
             */
            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException("No elements left");
                } else if (currentNode == null) {
                    currentNode = tail;
                } else {
                    currentNode = currentNode.previous;
                }
                return currentNode.element;
            }
        };
        return rightToLeft;
    }
}
