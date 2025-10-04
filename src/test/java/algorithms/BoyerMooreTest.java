package algorithms;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Metrics;


import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class needed for testing BoyerMoore algorithm with using Junit 5
 */
class BoyerMooreTest {
    private Metrics metrics;

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
    }

    @AfterEach
    void tearDown() {
        metrics = null;
    }

    @Test
    void testEmptyArray() {
        Optional<Integer> res = BoyerMoore.findMajority(new int[0],metrics);
        assertFalse(res.isPresent());
    }

    @Test
    public void testSingleElement() {
        Optional<Integer> r = BoyerMoore.findMajority(new int[]{42}, metrics);
        assertTrue(r.isPresent());
        assertEquals(42, r.get());
    }

    @Test
    public void testHasMajority() {
        int[] a = {1,2,1,1,3,1,1};
        Optional<Integer> r = BoyerMoore.findMajority(a, metrics);
        assertTrue(r.isPresent());
        assertEquals(1, r.get());
    }

    @Test
    public void testNoMajority() {
        int[] a = {1,2,3,4,5,6};
        Optional<Integer> r = BoyerMoore.findMajority(a, metrics);
        assertFalse(r.isPresent());
    }

    @Test
    public void testAllEqual() {
        int[] arr = {7,7,7,7,7};
        Optional<Integer> r = BoyerMoore.findMajority(arr, metrics);
        assertTrue(r.isPresent());
        assertEquals(7, r.get());
    }

    @Test
    public void testEdgeCasesEvenOdd() {
        int[] a = {2,1,2,1,2,1,2}; // 2 appears 4 times out of 7
        Optional<Integer> r = BoyerMoore.findMajority(a, metrics);
        assertTrue(r.isPresent());
        assertEquals(2, r.get());

        int[] b = {2,1,2,1}; // no majority (2 appears 2 out of 4)
        r = BoyerMoore.findMajority(b, metrics);
        assertFalse(r.isPresent());
    }

    @Test
    void testOptimizedMajority() {
        int[] arr = new int[1000];
        Arrays.fill(arr, 1); // Majority element

        Optional<Integer> result = BoyerMoore.findMajority(arr, metrics);
        assertTrue(result.isPresent());
        assertEquals(1, result.get());
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> BoyerMoore.findMajority(null, metrics));
    }

    @Test
    void testLargeInput() {
        int size = 100000;
        int[] arr = new int[size];

        Arrays.fill(arr, 0, size / 2 + 1, 1); // Majority 1
        Arrays.fill(arr, size / 2 + 1, size, 2);

        Optional<Integer> result = BoyerMoore.findMajority(arr, metrics);

        assertTrue(result.isPresent());
        assertEquals(1, result.get());

        System.out.println("Large Input (size=" + size + "): Metrics = " + metrics);

        assertTrue(metrics.getArrayAccesses() <= 2 * size); // Expect less than 2n accesses
    }

    @Test
    void testNegativeNumbers() {
        int[] arr = {-1, -1, -1, -2, -2}; // -1 appears 3 out of 5
        Optional<Integer> result = BoyerMoore.findMajority(arr, metrics);

        assertTrue(result.isPresent());
        assertEquals(-1, result.get());

        System.out.println("Negative Numbers: Metrics = " + metrics);
    }
}