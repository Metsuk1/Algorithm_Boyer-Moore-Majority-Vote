package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * CSVLogger - utility for writing algorithm metrics into a CSV file.
 * Automatically writes a header if the file is new or overwritten.
 */
public class CSVLogger implements AutoCloseable {
    private final FileWriter writer;


    /**
     * Creates a new CSV logger.
     *
     * @param fileName path to CSV file
     * @param append   true = append mode, false = overwrite
     */
    public CSVLogger(String fileName, boolean append) throws IOException {
        File file = new File(fileName);
        boolean exists = file.exists();
        writer = new FileWriter(file, append);
        // Write header only once for a new file
        if (!exists || !append) {
            writer.write("Algorithm_Name,n,timeMs,comparisons,arrayAccesses,assignments,allocations,maxDepth\n");
        }
    }

    /**
     * Logs one experiment result as a row in the CSV file.
     *
     * @param algorithmName algorithm identifier
     * @param n             input size
     * @param elapsedNanos  execution time in nanoseconds
     * @param m             collected metrics
     */
    public void logResult(String algorithmName,  int n, long elapsedNanos, Metrics m) throws IOException {
        double timeMs = elapsedNanos / 1e6;
        writer.write(algorithmName + "," +
                n + "," +
                timeMs + "," +
                m.getComparisons() + "," +
                m.getArrayAccesses() + "," +
                m.getAssignments() + "," +
                m.getMemoryAllocations() + "," + "\n");
        writer.flush();
    }


    /** Close the file writer (use with try-with-resources). */
    @Override
    public void close() throws IOException {
        writer.close();
    }
}
