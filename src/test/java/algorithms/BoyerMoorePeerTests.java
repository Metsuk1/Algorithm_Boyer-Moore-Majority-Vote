package algorithms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Metrics;

import java.util.Optional;
import java.util.Random;

import static cli.CLI.generateArrayWithMajority;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoyerMoorePeerTests {
    private Metrics metrics;
    private BoyerMoore boyerMoore;

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
        boyerMoore = new BoyerMoore(metrics);
    }

    // Integration Testing
    @Test
    void testIntegrationCompileAndRun() {
        int[] arr = {1, 1, 1, 2, 2}; // Majority 1 (3 out of 5 > 2.5)
        Optional<Integer> result = boyerMoore.findMajority(arr,metrics);

        assertTrue(result.isPresent() && result.get() == 1);
        System.out.println("Integration Test: Metrics = " + metrics);

        assertTrue(metrics.getArrayAccesses() >= 5); // Minimum expected accesses
    }

    @Test
    void testEdgeCasesIntegration() {
        // Null input
        assertThrows(IllegalArgumentException.class, () -> boyerMoore.findMajority(null,metrics));
        // Empty array
        Optional<Integer> emptyResult = boyerMoore.findMajority(new int[0],metrics);
        assertTrue(emptyResult.isEmpty());

        // No majority (e.g., all distinct)
        int[] noMajority = {1, 2, 3, 4};
        Optional<Integer> noMajorityResult = boyerMoore.findMajority(noMajority,metrics);

        assertTrue(noMajorityResult.isEmpty());

        System.out.println("Edge Cases: Metrics = " + metrics);
    }

    // Benchmark Reproduction
    @Test
    void testBenchmarkReproduction100() {
        int[] arr = generateArrayWithMajority(100, new Random(12345));
        // Warm-up run to allow JIT optimization
        for (int i = 0; i < 5; i++) boyerMoore.findMajority(arr,metrics);
        // Measure average over multiple runs
        long totalTimeNs = 0;

        int runs = 10;
        for (int i = 0; i < runs; i++) {

            long startTime = System.nanoTime();
            Optional<Integer> result = boyerMoore.findMajority(arr, metrics);
            long endTime = System.nanoTime();

            assertTrue(result.isPresent() && result.get() == 1);
            totalTimeNs += (endTime - startTime);

            metrics.reset(); // Reset metrics for next run
        }
        long avgTimeUs = (totalTimeNs / runs) / 1_000; // Average in microseconds
        System.out.println("Size 100: Average Time = " + avgTimeUs + " us, Metrics: " + metrics);
        assertTrue(avgTimeUs > 2 && avgTimeUs < 100); // range (2-100 us)
    }
}
