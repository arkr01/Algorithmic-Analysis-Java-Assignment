/**
 * A set of sorting algorithm implementations.
 */
public class SortingAlgorithms {
    /**
     * Sorts the given array using the selection sort algorithm.
     * This should modify the array in-place.
     *
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void selectionSort(T[] input, boolean reversed) {
        for (int current = 0; current < input.length; current++) {

            // Index of minimum (or maximum - if reversed flag is set) array
            // element that is yet to be sorted
            int minMax = current;

            for (int next = current + 1; next < input.length; next++) {
                // Find either the maximum or minimum element position based on reversed flag
                if ((reversed && (input[minMax]).compareTo(input[next]) <= 0) || (!reversed &&
                        (input[minMax]).compareTo(input[next]) >= 0)) {
                    minMax = next;
                }
            }
            // Swap min/max element of interest if it isn't already set
            if (minMax != current) {
                T tempSwap = input[minMax];
                input[minMax] = input[current];
                input[current] = tempSwap;
            }
        }
    }

    /**
     * Sorts the given array using the insertion sort algorithm.
     * This should modify the array in-place.
     *
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void insertionSort(T[] input, boolean reversed) {
        for (int current = 0; current < input.length; current++) {
            T toInsert = input[current];
            int insertPosition = current - 1;

            // Either find smaller or larger element based on reversed flag and 'shift' array
            // elements left respectively
            while (insertPosition >= 0 &&
                    ((((input[insertPosition]).compareTo(toInsert) >= 0) && !reversed) ||
                    ((input[insertPosition]).compareTo(toInsert) <= 0 && reversed))) {
                input[insertPosition + 1] = input[insertPosition];
                insertPosition--;
            }
            input[insertPosition + 1] = toInsert;
        }
    }
    
    /**
     * Sorts the given array using the merge sort algorithm.
     * This should modify the array in-place.
     * 
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void mergeSort(T[] input, boolean reversed) {
        mergeSort(input, 0, input.length - 1, reversed);
    }

    /**
     * Recursively partitions given array into halves, sorts each half, and merges halves.
     *
     * @param input The given array of comparable objects
     * @param left The index of the left-most element
     * @param right The index of the right-most element
     * @param reversed Whether the array should be sorted in reverse order
     * @param <T> Array element type to be sorted.
     */
    private static <T extends Comparable> void mergeSort(T[] input, int left, int right,
            boolean reversed) {
        if (left < right) {
            int midPoint = (left + right) / 2;
            mergeSort(input, left, midPoint, reversed);
            mergeSort(input, midPoint + 1, right, reversed);
            merge(input, left, midPoint, right, reversed);
        }
    }

    /**
     * Takes partitions of the given array and merges them together in ascending order (descending
     * if reversed flag is set).
     *
     * @param input The given array of comparable objects
     * @param left The index of the left-most element
     * @param midPoint The middle index of the given array
     * @param right The index of the right-most element
     * @param reversed Whether the array should be sorted in reverse order
     * @param <T> Array element type to be sorted.
     */
    private static <T extends Comparable> void merge(T[] input, int left, int midPoint, int right,
            boolean reversed) {
        int firstHalfLength = midPoint - left + 1;
        int secondHalfLength = right - midPoint;
        
        // Copy first and second half of input array
        T[] firstHalf = (T[]) new Comparable[firstHalfLength];
        T[] secondHalf = (T[]) new Comparable[secondHalfLength];
        for (int position = left; position < right + 1; position++) {
            if (position - left < firstHalfLength) {
                firstHalf[position - left] = input[position];
            } else {
                secondHalf[position - firstHalfLength - left] = input[position];
            }
        }

        int firstPosition = 0;
        int secondPosition = 0;
        int inputPosition = left;

        // Compare each half and re-order input array accordingly
        while (firstPosition < firstHalfLength && secondPosition < secondHalfLength) {
            if (((firstHalf[firstPosition]).compareTo(secondHalf[secondPosition]) <= 0 &&
                    !reversed) ||
                    ((firstHalf[firstPosition]).compareTo(secondHalf[secondPosition]) >= 0 &&
                    reversed)) {
                input[inputPosition++] = firstHalf[firstPosition++];
            } else {
                input[inputPosition++] = secondHalf[secondPosition++];
            }
        }
        
        // Restore any remaining elements
        while (firstPosition < firstHalfLength) {
            input[inputPosition++] = firstHalf[firstPosition++];
        }
        while (secondPosition < secondHalfLength) {
            input[inputPosition++] = secondHalf[secondPosition++];
        }
    }
    
    /**
     * Sorts the given array using the quick sort algorithm.
     * This should modify the array in-place.
     * 
     * You should use the value at the middle of the input array (i.e. floor
     * (n/2)) as the pivot at each step.
     * 
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void quickSort(T[] input, boolean reversed) {
        quickSort(input, 0, input.length - 1, reversed);
    }

    /**
     * Recursively partitions the given array using the median element as a pivot and sorts said
     * partitions.
     *
     * @param input The given array of comparable objects
     * @param left The left-most array index of interest
     * @param right The right-most array index of interest
     * @param reversed Whether the array is to be sorted in reverse order
     * @param <T> Array element type to be sorted.
     */
    private static <T extends Comparable> void quickSort(T[] input, int left, int right,
            boolean reversed) {
        if (left >= right) {
            return;
        }
        int newBound = partition(input, left, right, reversed);
        quickSort(input, left, newBound, reversed);
        quickSort(input, newBound + 1, right, reversed);
    }

    /**
     * Partitions the array via the median element of the provided left and right indices.
     *
     * @param input The given array to partition
     * @param left The left-most array index element to focus on
     * @param right The right-most array index element to focus on
     * @param reversed Whether the array is to be partitioned into ascending or descending
     *                 sub-arrays
     * @param <T> Array element type to be sorted.
     * @return The left/right position of the subarray to focus on
     */
    private static <T extends Comparable> int partition(T[] input, int left, int right,
            boolean reversed) {
        int midPoint = (left + right) / 2;
        T pivot = input[midPoint];
        int currentLeft = left - 1;
        int currentRight = right + 1;

        while (true) {
            // If pivot has been swapped, obtain new pivot and start over (pivot must always be
            // midpoint)
            if (pivot != input[midPoint]) {
                pivot = input[midPoint];
                currentLeft = left;
                currentRight = right;
            }
            // Find first left and right positions out of order (with respect to the pivot)
            do {
                (currentRight)--;
            } while ((((input[currentRight]).compareTo(pivot) > 0 &&
                    !reversed) || ((input[currentRight]).compareTo(pivot) < 0 && reversed)));

            do {
                (currentLeft)++;
            } while ((((input[currentLeft]).compareTo(pivot) < 0 &&
                    !reversed) || ((input[currentLeft]).compareTo(pivot) > 0 && reversed)));

            // Swap to partition array based on the current median element (i.e. pivot)
            if (currentLeft < currentRight) {
                T tempSwap = input[currentLeft];
                input[currentLeft] = input[currentRight];
                input[currentRight] = tempSwap;
            } else {
                // currentRight (i.e. right) will only be less than or equal to currentLeft (i.e.
                // left) when all elements are partitioned around midpoint pivot
                return currentRight;
            }
        }
    }
}
