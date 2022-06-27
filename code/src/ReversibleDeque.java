import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Wrapper for a SimpleDeque. Only modification is the addition of a reverse() method, which only
 * requires temporary variables to store single elements. Thus, the memory complexity of this
 * implementation is bounded by the memory complexity of the internal deque. Let X denote the
 * asymptotic upper bound of the memory complexity of the internal deque. (e.g. for
 * SimpleArrayDeque, X == N, where N is the length of the circular array). Thus, we may say that
 * this implementation is bounded by O(X) memory complexity.
 * @param <T> Type of element to store within the deque.
 */
public class ReversibleDeque<T> implements SimpleDeque<T> {
    /** The internal deque. */
    private SimpleDeque<T> data;

    /**
     * Constructs a new reversible deque, using the given data deque to store
     * elements.
     * The data deque must not be used externally once this ReversibleDeque
     * is created.
     * @param data a deque to store elements in.
     */
    public ReversibleDeque(SimpleDeque<T> data) {
        this.data = data;
    }

    /**
     * Reverses the internal deque elements. All elements at the right are now at the left and
     * vice versa.
     *
     * Observe that all primitive operations are simply method calls and conditional checks.
     * Furthermore, all method calls are bounded by O(1) time complexity. Thus, the time complexity
     * of this method is dependent on the number of recursive calls it must make. On each recursive
     * call, two elements are removed from the deque. Thus, if we let n denote the size of the
     * deque, it follows that floor(n / 2) + 1 recursive calls are made, and each call contains only
     * constant operations. Thus, this method is bounded by O(n) time complexity.
     */
    public void reverse() {
        if (this.size() < 2) {
            return;
        }
        T oldFront = this.popLeft();
        T oldRear = this.popRight();
        this.reverse();
        this.pushLeft(oldRear);
        this.pushRight(oldFront);
    }

    /**
     * Returns the size of the internal deque. All current implementations of SimpleDeque.size() are
     * bounded by O(1) time complexity. Hence, unless a new SimpleDeque implementation is introduced
     * with greater time complexity, this method is also bounded by O(1) time complexity.
     *
     * @return The number of elements stored in the deque.
     */
    @Override
    public int size() {
        return this.data.size();
    }

    /**
     * Checks whether the deque is empty. All current implementations of SimpleDeque.isEmpty() are
     * bounded by O(1) time complexity. Hence, unless a new SimpleDeque implementation is introduced
     * with greater time complexity, this method is also bounded by O(1) time complexity.
     *
     * @return true if the deque is empty, otherwise false.
     */
    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    /**
     * Checks whether the deque is full. All current implementations of SimpleDeque.isFull() are
     * bounded by O(1) time complexity. Hence, unless a new SimpleDeque implementation is introduced
     * with greater time complexity, this method is also bounded by O(1) time complexity.
     *
     * @return true if the deque is full, otherwise false.
     */
    @Override
    public boolean isFull() {
        return this.data.isFull();
    }

    /**
     * Pushes an element to the left of the deque. All current implementations of
     * SimpleDeque.pushLeft() are bounded by O(1) time complexity. Hence, unless a new SimpleDeque
     * implementation is introduced with greater time complexity, this method is also bounded by
     * O(1) time complexity.
     *
     * @param e Element to push
     * @throws RuntimeException if the deque is already full
     */
    @Override
    public void pushLeft(T e) throws RuntimeException {
        this.data.pushLeft(e);
    }

    /**
     * Pushes an element to the right of the deque. All current implementations of
     * SimpleDeque.pushRight() are bounded by O(1) time complexity. Hence, unless a new SimpleDeque
     * implementation is introduced with greater time complexity, this method is also bounded by
     * O(1) time complexity.
     *
     * @param e Element to push
     * @throws RuntimeException if the deque is already full
     */
    @Override
    public void pushRight(T e) throws RuntimeException {
        this.data.pushRight(e);
    }

    /**
     * Returns the element at the left of the deque, but does not remove it. All current
     * implementations of SimpleDeque.peekLeft() are bounded by O(1) time complexity. Hence, unless
     * a new SimpleDeque implementation is introduced with greater time complexity, this method is
     * also bounded by O(1) time complexity.
     *
     * @returns the leftmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T peekLeft() throws NoSuchElementException {
        return this.data.peekLeft();
    }

    /**
     * Returns the element at the right of the deque, but does not remove it. All current
     * implementations of SimpleDeque.peekRight() are bounded by O(1) time complexity. Hence, unless
     * a new SimpleDeque implementation is introduced with greater time complexity, this method is
     * also bounded by O(1) time complexity.
     *
     * @returns the rightmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T peekRight() throws NoSuchElementException {
        return this.data.peekRight();
    }

    /**
     * Removes and returns the element at the left of the deque. All current implementations of
     * SimpleDeque.popLeft() are bounded by O(1) time complexity. Hence, unless a new SimpleDeque
     * implementation is introduced with greater time complexity, this method is also bounded by
     * O(1) time complexity.
     *
     * @returns the leftmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T popLeft() throws NoSuchElementException {
        return this.data.popLeft();
    }

    /**
     * Removes and returns the element at the right of the deque. All current implementations of
     * SimpleDeque.popLeft() are bounded by O(1) time complexity. Hence, unless a new SimpleDeque
     * implementation is introduced with greater time complexity, this method is also bounded by
     * O(1) time complexity.
     *
     * @returns the rightmost element
     * @throws NoSuchElementException if the deque is empty
     */
    @Override
    public T popRight() throws NoSuchElementException {
        return this.data.popRight();
    }

    /**
     * Returns an iterator for the deque in left to right sequence.
     *
     * The methods hasNext() and next() in the Iterator are clearly bounded by O(1) time complexity
     * as all current implementations of SimpleDeque are bounded by O(1) time complexity. Hence,
     * this method's time complexity will only change if a new implementation of SimpleDeque
     * is introduced with greater time complexity.
     *
     * The same applies for the memory complexity of the Iterator itself, which is bounded by O(1)
     * at this given stage, assuming no new implementations with greater memory complexity are
     * introduced.
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
        return this.data.iterator();
    }

    /**
     * Returns an iterator for the deque in right to left sequence.
     *
     * The methods hasNext() and next() in the Iterator are clearly bounded by O(1) time complexity
     * as all current implementations of SimpleDeque are bounded by O(1) time complexity. Hence,
     * this method's time complexity will only change if a new implementation of SimpleDeque
     * is introduced with greater time complexity.
     *
     * The same applies for the memory complexity of the Iterator itself, which is bounded by O(1)
     * at this given stage, assuming no new implementations with greater memory complexity are
     * introduced.
     *
     * The remove() method in the iterator has not been implemented.
     *
     * We have assumed that the elements in the deque will never change while the iterator is being
     * used.
     *
     * @returns an iterator over the elements in in order from rightmost to leftmost.
     */
    @Override
    public Iterator<T> reverseIterator() {
        return this.data.reverseIterator();
    }
}
