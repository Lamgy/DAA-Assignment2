package algorithms;

import metrics.PerformanceTracker;
import java.util.Objects;

public class BoyerMooreMajorityVote {

    public static int findMajorityElement(int[] arr, PerformanceTracker t) {
        Objects.requireNonNull(arr);
        if (arr.length == 0) return -1;
        if (arr.length == 1) return arr[0];
        if (t == null) t = new PerformanceTracker();
        t.start();
        int candidate = 0;
        int count = 0;

        for (int value : arr) {
            t.addAccess(1);
            if (count == 0) {
                candidate = value;
                count = 1;
            }
            else {
                t.addCompare();
                if (value == candidate) count++;
                else count--;
            }
            if (count > arr.length / 2) {
                t.stop();
                return candidate;
            }
        }

        int freq = 0;
        for (int v : arr) {
            t.addAccess(1);
            t.addCompare();
            if (v == candidate) freq++;
        }
        t.stop();
        return (freq > arr.length / 2) ? candidate : -1;
    }

    public static int findMajorityElement(int[] arr) {
        return findMajorityElement(arr, new PerformanceTracker());
    }
}
