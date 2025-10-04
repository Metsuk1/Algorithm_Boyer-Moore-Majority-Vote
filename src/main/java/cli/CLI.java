package cli;


import algorithms.BoyerMoore;
import utils.CSVLogger;
import utils.Metrics;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

/**
 * CLI interface for benchmarking the Boyer-Moore Majority Vote Algorithm.
 */
public class CLI {
    private static final String FILE_NAME = "results.csv";
    private static final int[] SIZES = {100, 500, 1000, 5000, 10000};
    private static final String ALGORITHM_NAME = "Boyer_Moore";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Boyer-Moore Benchmark CLI ---");
        System.out.println("1) Run ALL benchmarks for different sizes (fixed majority = 1)");
        System.out.println("2) Run single size benchmark (fixed majority = 1 for correctness)");
        System.out.println("3) Run single size benchmark (random majority)");
        System.out.println("4) Exit");
        System.out.print("Choose option: ");

        int choice = scanner.nextInt();

        try (CSVLogger logger = new CSVLogger(FILE_NAME, false)) { // false для перезаписи файла
            switch (choice) {
                case 1 -> benchmarkAll(logger);
                case 2 -> {
                    System.out.print("Enter array size (e.g., 100, 500, etc.): ");
                    int size = scanner.nextInt();
                    benchmarkSingleSizeWithFixedMajority(size, logger);
                }
                case 3 -> {
                    System.out.print("Enter array size (e.g., 100, 500, etc.): ");
                    int size = scanner.nextInt();
                    benchmarkSingleSizeWithRandomMajority(size, logger);
                }
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
        System.out.println("Benchmarks finished, results written to " + FILE_NAME);
    }

    /**
     * Runs benchmarks for all predefined sizes with a fixed majority element (1).
     */
    public static void benchmarkAll(CSVLogger logger) throws IOException {
        for (int size : SIZES) {
            benchmarkSingleSizeWithFixedMajority(size, logger);
        }
        System.out.println("CSV results written to " + FILE_NAME);
    }

    /**
     * Runs a benchmark for a single size with a fixed majority element (1).
     */
    public static void benchmarkSingleSizeWithFixedMajority(int size, CSVLogger logger) throws IOException {
        Random random = new Random();
        Metrics metrics = new Metrics();
        int[] arr = generateArrayWithMajority(size, random);

        long startTime = System.nanoTime();
        Optional<Integer> result = BoyerMoore.findMajority(arr, metrics);
        long endTime = System.nanoTime();
        long elapsedNanos = endTime - startTime;

        logger.logResult(ALGORITHM_NAME, size, elapsedNanos, metrics);

        double timeMs = elapsedNanos / 1e6;
        String resultStr = result.map(Object::toString).orElse("No majority");
        System.out.printf("Size: %d, Time: %.2f ms, Result: %s, Metrics: %s%n",
                size, timeMs, resultStr, metrics.toString());
    }

    /**
     * Runs a benchmark for a single size with a random majority element.
     */
    public static void benchmarkSingleSizeWithRandomMajority(int size, CSVLogger logger) throws IOException {
        Random random = new Random();
        Metrics metrics = new Metrics();
        int[] arr = generateArrayWithRandomMajority(size, random);

        long startTime = System.nanoTime();
        Optional<Integer> result = BoyerMoore.findMajority(arr, metrics);
        long endTime = System.nanoTime();
        long elapsedNanos = endTime - startTime;

        logger.logResult(ALGORITHM_NAME, size, elapsedNanos, metrics);

        double timeMs = elapsedNanos / 1e6;
        String resultStr = result.map(Object::toString).orElse("No majority");
        System.out.printf("Size: %d, Time: %.2f ms, Result: %s, Metrics: %s%n",
                size, timeMs, resultStr, metrics.toString());
    }

    /**
     * Generates an array with a fixed majority element (1).
     */
    private static int[] generateArrayWithMajority(int size, Random random) {
        int majorityElement = 1; // fixed value for correctnes testing
        int[] arr = new int[size];
        for (int i = 0; i < size / 2 + 1; i++) {
            arr[i] = majorityElement;
        }
        for (int i = size / 2 + 1; i < size; i++) {
            arr[i] = random.nextInt(100);
        }
        for (int i = size - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }

    /**
     * Generates an array with a random majority element.
     */
    private static int[] generateArrayWithRandomMajority(int size, Random random) {
        int majorityElement = random.nextInt(100); // Random element 0-99
        int[] arr = new int[size];
        for (int i = 0; i < size / 2 + 1; i++) {
            arr[i] = majorityElement;
        }
        for (int i = size / 2 + 1; i < size; i++) {
            arr[i] = random.nextInt(100);
        }
        for (int i = size - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }
}