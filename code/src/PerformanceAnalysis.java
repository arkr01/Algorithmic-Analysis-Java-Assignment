import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.function.BiConsumer;

public class PerformanceAnalysis {

    public static Integer[][] generateArrays(Random generator, boolean sorted, boolean reverse,
            int length) {
        // Make 4 copies of array to be sorted (one for each algorithm to sort)
        Integer[][] toSort = new Integer[4][length];

        for (int i = 0; i < length; i++) {
            toSort[0][i] = generator.nextInt();
        }
        for (int j = 0; j < 4; j++) {
            toSort[j] = Arrays.copyOf(toSort[0], length);
            if (sorted && reverse) {
                Arrays.sort(toSort[j], Collections.reverseOrder());
            } else if (sorted) {
                Arrays.sort(toSort[j]);
            }
        }
        return toSort;
    }

    public static long testSort(Integer[] toSort, BiConsumer<Integer[], Boolean> algorithm) {
        long start, end;

        // Convert to milliseconds in Excel to prevent round-off error
        start = System.nanoTime();
        algorithm.accept(toSort, false);
        end = System.nanoTime();

        return end - start;
    }

    public static void main(String[] args) {
        Random generator = new Random();

        // Change these parameters per test
        Integer[][] toSort = generateArrays(generator, false, false, 5);
        
        // To handle issues with inconsistent runtimes of whichever method is called first, call
        // all methods on a random array
        Integer[][] randomTest = generateArrays(generator, false, false, 7);

        SortingAlgorithms.selectionSort(randomTest[0], false);
        SortingAlgorithms.insertionSort(randomTest[1], false);
        SortingAlgorithms.mergeSort(randomTest[2], false);
        SortingAlgorithms.quickSort(randomTest[3], false);

        long elapsed;

        elapsed = testSort(toSort[0], SortingAlgorithms::selectionSort);
        System.out.println("Selection Sort: " + elapsed);

        elapsed = testSort(toSort[1], SortingAlgorithms::insertionSort);
        System.out.println("Insertion Sort: " + elapsed);

        elapsed = testSort(toSort[2], SortingAlgorithms::mergeSort);
        System.out.println("Merge Sort: " + elapsed);

        elapsed = testSort(toSort[3], SortingAlgorithms::quickSort);
        System.out.println("Quick Sort: " + elapsed);
    }
}
