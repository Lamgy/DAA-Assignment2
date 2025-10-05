package cli;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;
import util.CSVWriter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BenchmarkRunner {

    private static final int[] DEFAULT_SIZES = {1000, 10_000, 100_000, 1_000_000};
    private static final String[] DEFAULT_DISTRIBUTIONS = {
            "random", "sorted", "reverse-sorted", "nearly-sorted"
    };

    public static void main(String[] args) throws IOException {
        List<Integer> sizes = parseSizes(args);
        List<String> distributions = parseDistributions(args);

        String path = "src/main/java/metrics/test-metrics.csv";
        try (CSVWriter writer = new CSVWriter(path, "size", "distribution", "time_ms", "comparisons", "accesses")) {
            for (int size : sizes) {
                for (String dist : distributions) {
                    int[] arr = generateArray(size, dist);
                    PerformanceTracker tracker = new PerformanceTracker();

                    BoyerMooreMajorityVote.findMajorityElement(arr, tracker);
                    writer.writeRow(size, dist, tracker.elapsedMillis(), tracker.comparisons, tracker.arrayAccesses);

                    System.out.printf("size=%d, dist=%s, %s%n", size, dist, tracker);
                }
            }
        }

        System.out.println("\nBenchmark completed. Results saved to: src/main/java/metrics/test-metrics.csv");
    }

    private static List<Integer> parseSizes(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--sizes")) {
                String list = arg.substring("--sizes".length()).replace("=", "").trim();
                return Arrays.stream(list.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            }
        }
        return Arrays.stream(DEFAULT_SIZES).boxed().collect(Collectors.toList());
    }

    private static List<String> parseDistributions(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--dists")) {
                String list = arg.substring("--dists".length()).replace("=", "").trim();
                return Arrays.stream(list.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
            }
        }
        return Arrays.asList(DEFAULT_DISTRIBUTIONS);
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
