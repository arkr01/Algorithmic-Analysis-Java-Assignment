import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A circular array implementation of a double-ended queue. Let N denote the array length. Then,
 * regardless of the number of elements within the deque, the memory complexity of this
 * implementation is bounded by O(N).
 *
 * @param <T> Type of element to store within the deque.
 */
public class SimpleArrayDeque<T> implements SimpleDeque<T> {

    /** The circular array-based implementation of a deque. */
    private T[] deque;

    /** The dequeue's maximum size. */
    private int capacity;

    /** Represents the front element position. */
    private int front;

    /** Represents the rear element position. */
    private int rear;

    /** Represents the number of elements currently stored. */
    private int size;

    /** Represents the starting position of the front and rear indices to ensure all array
     * positions are able to be used
     */
    private static final int INITIAL_POSITION = -1;

    /**
     * Constructs a new array based deque with limited capacity.
     * 
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0
     */
    public SimpleArrayDeque(int capacity) throws IllegalArgumentException {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        this.capacity = capacity;
        this.deque = (T[]) new Object[this.capacity];
        this.front = INITIAL_POSITION;
        this.rear = INITIAL_POSITION;
        this.size = 0;
    }

    /**
     * Constructs a new array based deque with limited capacity, and initially populates the deque
     * with the elements of another SimpleDeque.
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0 or size of otherDeque is > capacity
     */
    public SimpleArrayDeque(int capacity, SimpleDeque<? extends T> otherDeque) 
            throws IllegalArgumentException {
        if (capacity <= 0 || otherDeque.size() > capacity) {
            throw new IllegalArgumentException("Invalid capacities");
        }
        this.capacity = capacity;
        deque = (T[]) new Object[this.capacity];

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
        return this.size() == this.capacity;
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
     * Pushes an element to the left of the deque. Clearly, as we are using a circular array, all
     * operations are either conditional checks, maths, or assignments. No elements are required
     * to be 'shifted', and hence this is a simple assignment to an empty array index. Thus, this
     * method is bounded by O(1) time complexity.
     *
     * @param e Element to push
     * @throws RuntimeException if the deque is already full
     */
    @Override
    public void pushLeft(T e) throws RuntimeException {
        if (this.isFull()) {
            throw new RuntimeException("Deque full.");
        }
        if (this.isEmpty()) {
            this.front = 0;
            this.rear = this.front;
        } else {
            // Modulus to ensure circular array implementation, the addition of this.capacity to
            // handle the case when this.front is 0, as -1 % this.capacity == -1
            this.front = (this.front - 1 + this.capacity) % this.capacity;
        }
        this.deque[this.front] = e;
        this.size++;
    }

    /**
     * Pushes an element to the right of the deque. Clearly, as we are using a circular array, all
     * operations are either conditional checks, maths, or assignments. No elements are required
     * to be 'shifted', and hence this is a simple assignment to an empty array index. Thus, this
     * method is bounded by O(1) time complexity.
     *
     * @param e Element to push
     * @throws RuntimeException if the deque is already full
     */
    @Override
    public void pushRight(T e) throws RuntimeException {
        if (this.isFull()) {
            throw new RuntimeException("Deque full.");
        }
        this.rear = (this.rear + 1) % this.capacity;
        if (this.isEmpty()) {
            this.front = this.rear;
        }
        this.deque[this.rear] = e;
        this.size++;
    }

    /**
     * Returns the element at the left of the deque, but does not remove it. Clearly, all operations
     * are simply conditional checks and array indexing. Thus, this method is bounded by O(1) time
     * complexity.
     *
     * @returns the leftmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T peekLeft() throws NoSuchElementException {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Deque empty.");
        }
        return this.deque[this.front];
    }

    /**
     * Returns the element at the right of the deque, but does not remove it. Clearly, all
     * operations are simply conditional checks and array indexing. Thus, this method is bounded
     * by O(1) time complexity.
     *
     * @returns the rightmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T peekRight() throws NoSuchElementException {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Deque empty.");
        }
        return this.deque[this.rear];
    }

    /**
     * Removes and returns the element at the left of the deque. As all operations are either
     * array indexing/assignment, maths, or calling of methods bounded by O(1) time complexity,
     * it is clear that this method must also be bounded by O(1) time complexity.
     *
     * @returns the leftmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T popLeft() throws NoSuchElementException {
        T toPop = this.peekLeft(); // throws NoSuchElementException if the deque is empty
        this.deque[this.front] = null;
        this.front = (this.front + 1) % this.capacity;
        this.size--;
        return toPop;
    }

    /**
     * Removes and returns the element at the right of the deque. As all operations are either
     * array indexing/assignment, maths, or calling of methods bounded by O(1) time complexity,
     * it is clear that this method must also be bounded by O(1) time complexity.
     *
     * @returns the rightmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T popRight() throws NoSuchElementException {
        T toPop = this.peekRight(); // throws NoSuchElementException if the deque is empty
        this.deque[this.rear] = null;

        // Modulus to ensure circular array implementation, the addition of this.capacity to
        // handle the case when this.rear is 0, as -1 % this.capacity == -1
        this.rear = (this.rear - 1 + this.capacity) % this.capacity;
        this.size--;
        return toPop;
    }

    /**
     * Returns an iterator for the deque in left to right sequence.
     *
     * The methods hasNext() and next() in the Iterator are clearly bounded by O(1) time complexity
     * as they simply index the deque.
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
         * As only a single array index was stored, it is clear that this iterator is bounded by
         * O(1) memory complexity.
         */
        Iterator<T> leftToRight = new Iterator<>() {
            /** Position of current element. */
            private int currentIndex = (front - 1 + capacity) % capacity;

            /**
             * Checks whether the deque has another element. As explained above, this method is
             * bounded by O(1) time complexity.
             *
             * @return true if we are yet to reach the deque rear, and the deque is not empty, false
             * otherwise.
             */
            @Override
            public boolean hasNext() {
                return !(isEmpty() || currentIndex == rear);
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
                    throw new NoSuchElementException("No more elements left.");
                }
                currentIndex = (currentIndex + 1) % capacity;
                return deque[currentIndex];
            }
        };
        return leftToRight;
    }

    /**
     * Returns an iterator for the deque in right to left sequence.
     *
     * The methods hasNext() and next() in the Iterator clearly run in O(1) time, as again, they
     * simply index the deque.
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
         * As only a single array index was stored, it is clear that this iterator is bounded by
         * O(1) memory complexity.
         */
        Iterator<T> rightToLeft = new Iterator<>() {
            /** Position of current element. */
            private int currentIndex = (rear + 1) % capacity;

            /**
             * Checks whether the deque has another element. As explained above, this method is
             * bounded by O(1) time complexity.
             *
             * @return true if we are yet to reach the deque front, and the deque is not empty,
             * false otherwise.
             */
            @Override
            public boolean hasNext() {
                return !(isEmpty() || currentIndex == front);
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
                    throw new NoSuchElementException("No more elements left.");
                }

                // Modulus to ensure circular array implementation, the addition of this.capacity to
                // handle the case when currentIndex is 0, as -1 % this.capacity == -1
                currentIndex = (currentIndex - 1 + capacity) % capacity;
                return deque[currentIndex];
            }
        };
        return rightToLeft;
    }
}
