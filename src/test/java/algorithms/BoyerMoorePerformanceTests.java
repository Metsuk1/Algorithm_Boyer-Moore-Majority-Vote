package algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.Metrics;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import static cli.CLI.generateArrayWithMajority;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BoyerMoorePerformanceTests {
    private Metrics metrics;
    private BoyerMoore boyerMoore;

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
        boyerMoore = new BoyerMoore(metrics);
    }

    // === Scalability Tests (Parameterized) ===

    @ParameterizedTest
    @MethodSource("provideScalabilitySizes")
    void testScalability(int size) {
        int[] arr = generateArrayWithMajority(size, new Random());
        long startTime = System.nanoTime();
        Optional<Integer> result = BoyerMoore.findMajority(arr, metrics);
        long endTime = System.nanoTime();
        assertTrue(result.isPresent() && result.get() == 1); // Majority is 1
        long timeNs = endTime - startTime;
        System.out.println("Size " + size + ": Time = " + timeNs / 1_000_000.0 + " ms, Metrics: " + metrics);
        // Rough time checks based on expected JMH results (~2.849 us for 100, ~294.731 us for 10000, scaled)
        if (size == 100) assertTrue(timeNs < 10_000_000); // < 10 ms
        else if (size == 1000) assertTrue(timeNs < 100_000_000); // < 100 ms
        else if (size == 10000) assertTrue(timeNs < 1_000_000_000); // < 1 sec
        else if (size == 100000) assertTrue(timeNs < 10_000_000_000L); // < 10 sec
        metrics.reset(); // Reset metrics for next iteration
    }

    // Method to provide test data
    private static Stream<Integer> provideScalabilitySizes() {
        return Stream.of(100, 1000, 10000, 100000); // Sizes 10² to 10⁵
    }

    @Test
    void testSortedDistribution() {
        int[] arr = new int[1000];
        Arrays.fill(arr, 0, 501, 1); // Majority 1
        Arrays.fill(arr, 501, 1000, 2); // Non-majority
        Optional<Integer> result = BoyerMoore.findMajority(arr, metrics);
        assertTrue(result.isPresent() && result.get() == 1);
        System.out.println("Sorted: Metrics = " + metrics);
        assertTrue(metrics.getComparisons() >= 500); // Significant comparisons
    }

    @Test
    void testReverseSortedDistribution() {
        int[] arr = new int[1000];
        Arrays.fill(arr, 0, 499, 2); // Non-majority
        Arrays.fill(arr, 499, 1000, 1); // Majority 1
        Optional<Integer> result = BoyerMoore.findMajority(arr, metrics);
        assertTrue(result.isPresent() && result.get() == 1);
        System.out.println("Reverse Sorted: Metrics = " + metrics);
    }

    @Test
    void testNearlySortedDistribution() {
        int[] arr = new int[1000];
        Arrays.fill(arr, 0, 500, 1); // Majority 1
        for (int i = 500; i < 1000; i++) arr[i] = (i % 2 == 0) ? 1 : 2; // Mostly 1 with noise
        Optional<Integer> result = BoyerMoore.findMajority(arr, metrics);
        assertTrue(result.isPresent() && result.get() == 1);
        System.out.println("Nearly Sorted: Metrics = " + metrics);
    }


}
