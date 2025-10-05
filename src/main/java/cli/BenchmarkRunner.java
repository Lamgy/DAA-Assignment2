package cli;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;
import util.CSVWriter;

import java.io.IOException;
import java.util.Random;

public class BenchmarkRunner {

    private static final int[] SIZES = {100, 1_000, 10_000, 100_000};
    private static final String[] DISTRIBUTIONS = {"random", "sorted", "reverse-sorted", "nearly-sorted"};

    public static void main(String[] args) throws IOException {
        String path = "src/main/java/metrics/test-metrics.csv";

        try (CSVWriter writer = new CSVWriter(path, "Distribution", "size", "time_ms", "comparisons", "accesses")) {
            for (int size : SIZES) {
                for (String dist : DISTRIBUTIONS) {
                    int[] arr = generateArray(size, dist);

                    PerformanceTracker tracker = new PerformanceTracker();
                    BoyerMooreMajorityVote.findMajorityElement(arr, tracker);

                    writer.writeRow(dist, size, tracker.elapsedMillis(), tracker.comparisons, tracker.arrayAccesses);

                    System.out.printf("Done: size=%d, dist=%s, %s%n", size, dist, tracker);
                }
            }
        }
        System.out.println("Benchmark completed. Results saved to: src/main/java/metrics/test-metrics.csv");
    }

    private static int[] generateArray(int size, String distribution) {
        Random random = new Random();
        int[] arr = new int[size];

        switch (distribution) {
            case "random" -> {
                for (int i = 0; i < size; i++)
                    arr[i] = random.nextInt(5);
            }
            case "sorted" -> {
                for (int i = 0; i < size; i++)
                    arr[i] = i;
            }
            case "reverse-sorted" -> {
                for (int i = 0; i < size; i++)
                    arr[i] = size - i;
            }
            case "nearly-sorted" -> {
                for (int i = 0; i < size; i++)
                    arr[i] = i;
                for (int i = 0; i < size / 10; i++) {
                    int a = random.nextInt(size);
                    int b = random.nextInt(size);
                    int temp = arr[a];
                    arr[a] = arr[b];
                    arr[b] = temp;
                }
            }
            default -> throw new IllegalArgumentException("Unknown distribution: " + distribution);
        }

        return arr;
    }
}
