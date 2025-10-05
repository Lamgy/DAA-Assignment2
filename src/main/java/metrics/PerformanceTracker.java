package metrics;

import util.CSVWriter;
import java.io.IOException;

public class PerformanceTracker {
    public long comparisons = 0;
    public long arrayAccesses = 0;
    private double startTimeNs;
    private double endTimeNs;

    public void start() { startTimeNs = System.nanoTime(); }
    public void stop() { endTimeNs = System.nanoTime(); }
    public double elapsedMillis() { return (endTimeNs - startTimeNs) / 1_000_000; }

    public void addCompare() { comparisons++; }
    public void addAccess(int count) { arrayAccesses += count; }

    @Override
    public String toString() {
        return String.format("time=%fms, comparisons=%d, accesses=%d",
                elapsedMillis(), comparisons, arrayAccesses);
    }

    public void exportToCSV(String filePath, String algorithmName, int inputSize) {
        try (CSVWriter writer = new CSVWriter(filePath,
                "Algorithm", "InputSize", "Time(ms)", "Comparisons", "ArrayAccesses")) {

            writer.writeRow(
                    algorithmName,
                    inputSize,
                    elapsedMillis(),
                    comparisons,
                    arrayAccesses
            );

        } catch (IOException e) {
            System.err.println("[ERROR] Failed to write CSV: " + e.getMessage());
        }
    }
}
