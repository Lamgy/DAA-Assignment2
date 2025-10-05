package util;

import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PlotWriter {

    public static void main(String[] args) throws IOException {
        String csvPath = "src/main/java/metrics/test-metrics.csv";
        String outputPath = "docs/plot.png";
        generatePlot(csvPath, outputPath);
    }

    public static void generatePlot(String csvPath, String outputPath) throws IOException {
        Map<String, List<Double>> inputSizes = new HashMap<>();
        Map<String, List<Double>> times = new HashMap<>();

        List<String> lines = Files.readAllLines(Paths.get(csvPath));
        lines.remove(0);

        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] parts = line.split(",");
            if (parts.length < 5) continue;

            String algorithm = parts[0].trim();
            double size = Double.parseDouble(parts[1].trim());
            double time = Double.parseDouble(parts[2].trim());

            inputSizes.computeIfAbsent(algorithm, k -> new ArrayList<>()).add(size);
            times.computeIfAbsent(algorithm, k -> new ArrayList<>()).add(time);
        }

        XYChart chart = new XYChartBuilder()
                .width(900).height(600)
                .title("Algorithm Performance Benchmark")
                .xAxisTitle("Input Size")
                .yAxisTitle("Time (ms)")
                .build();

        for (String algo : inputSizes.keySet()) {
            List<Double> xData = inputSizes.get(algo);
            List<Double> yData = times.get(algo);
            chart.addSeries(algo, xData, yData).setMarker(SeriesMarkers.CIRCLE);
        }

        BitmapEncoder.saveBitmap(chart, outputPath, BitmapEncoder.BitmapFormat.PNG);
        System.out.println("Chart saved to " + outputPath);
    }
}
