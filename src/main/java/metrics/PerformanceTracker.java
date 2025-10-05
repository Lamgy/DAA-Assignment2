package metrics;

public class PerformanceTracker {
    public long comparisons = 0;
    public long arrayAccesses = 0;
    private long startTimeNs;
    private long endTimeNs;

    public void start() { startTimeNs = System.nanoTime(); }
    public void stop() { endTimeNs = System.nanoTime(); }
    public long elapsedMillis() { return (endTimeNs - startTimeNs) / 1_000_000; }

    public void addCompare() { comparisons++; }
    public void addAccess(int count) { arrayAccesses += count; }

    @Override
    public String toString() {
        return String.format("time=%dms, comparisons=%d, accesses=%d",
                elapsedMillis(), comparisons, arrayAccesses);
    }
}
