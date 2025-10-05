package util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVWriter implements AutoCloseable {
    private final CSVPrinter printer;

    public CSVWriter(String filePath, String... headers) throws IOException {
        Files.createDirectories(Paths.get(filePath).getParent());
        boolean append = Files.exists(Paths.get(filePath));
        printer = new CSVPrinter(
                new FileWriter(filePath, append),
                append
                        ? CSVFormat.DEFAULT
                        : CSVFormat.DEFAULT.builder().setHeader(headers).build()
        );
    }

    public void writeRow(Object... values) throws IOException {
        printer.printRecord(values);
    }

    @Override
    public void close() throws IOException {
        printer.flush();
        printer.close();
    }
}
