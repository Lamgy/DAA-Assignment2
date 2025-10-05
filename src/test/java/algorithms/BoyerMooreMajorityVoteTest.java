package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BoyerMooreMajorityVoteTest {

    private static final String CSV_PATH = "src/main/java/metrics/test-metrics.csv";

    private int runAndTrack(int[] arr, String label) {
        PerformanceTracker tracker = new PerformanceTracker();
        int result = BoyerMooreMajorityVote.findMajorityElement(arr, tracker);
        tracker.exportToCSV(CSV_PATH, label, arr.length);
        return result;
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        assertEquals(-1, runAndTrack(arr, "Empty Array"));
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        assertEquals(42, runAndTrack(arr, "Single Element"));
    }

    @Test
    void testAllDuplicates() {
        int[] arr = {5, 5, 5, 5, 5};
        assertEquals(5, runAndTrack(arr, "All Duplicates"));
    }

    @Test
    void testNoMajority() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        assertEquals(-1, runAndTrack(arr, "No Majority"));
    }

    @Test
    void testSortedInput() {
        int[] arr = {1, 1, 1, 1, 2, 3, 4};
        assertEquals(1, runAndTrack(arr, "Sorted Input"));
    }

    @Test
    void testReverseSortedInput() {
        int[] arr = {4, 3, 2, 1, 1, 1, 1};
        assertEquals(1, runAndTrack(arr, "Reverse Sorted Input"));
    }

    @Test
    void testNearlySortedInput() {
        int[] arr = {1, 1, 2, 1, 3, 1};
        assertEquals(1, runAndTrack(arr, "Nearly Sorted Input"));
    }

    @Test
    void testRandomInputs() {
        Random rand = new Random(42);
        for (int i = 0; i < 5; i++) {
            int size = 100 + rand.nextInt(1000);
            int[] arr = rand.ints(size, 0, 5).toArray();
            int result = runAndTrack(arr, "Random Input");

            boolean exists = Arrays.stream(arr).anyMatch(x -> x == result);
            assertTrue(exists || result == -1);
        }
    }

    @Test
    void testScalability() {
        int[] sizes = {100, 1000, 10_000, 100_000};
        Random rand = new Random();

        for (int n : sizes) {
            int[] arr = rand.ints(n, 0, 1000).toArray();
            int result = runAndTrack(arr, "Scalability");
            assertTrue(result == -1 || Arrays.stream(arr).anyMatch(x -> x == result));
        }
    }

    @Test
    void testCrossValidation() {
        int[] arr = {3, 3, 4, 2, 4, 4, 2, 4, 4};
        int customResult = runAndTrack(arr, "Cross Validation");

        int expected = Arrays.stream(arr)
                .filter(x -> Arrays.stream(arr).filter(y -> y == x).count() > arr.length / 2)
                .findFirst()
                .orElse(-1);

        assertEquals(expected, customResult);
    }
}
