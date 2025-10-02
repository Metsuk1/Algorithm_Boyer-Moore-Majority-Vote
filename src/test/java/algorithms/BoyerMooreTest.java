package algorithms;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Metrics;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        Metrics m = new Metrics();
        Optional<Integer> r = BoyerMoore.findMajority(new int[]{42}, m);
        assertTrue(r.isPresent());
        assertEquals(42, r.get());
    }

    @Test
    public void testHasMajority() {
        Metrics m = new Metrics();
        int[] a = {1,2,1,1,3,1,1};
        Optional<Integer> r = BoyerMoore.findMajority(a, m);
        assertTrue(r.isPresent());
        assertEquals(1, r.get());
    }

    @Test
    public void testNoMajority() {
        Metrics m = new Metrics();
        int[] a = {1,2,3,4,5,6};
        Optional<Integer> r = BoyerMoore.findMajority(a, m);
        assertFalse(r.isPresent());
    }

    @Test
    public void testAllEqual() {
        Metrics m = new Metrics();
        int[] a = {7,7,7,7,7};
        Optional<Integer> r = BoyerMoore.findMajority(a, m);
        assertTrue(r.isPresent());
        assertEquals(7, r.get());
    }

    @Test
    public void testEdgeCasesEvenOdd() {
        Metrics m = new Metrics();
        int[] a = {2,1,2,1,2,1,2}; // 2 appears 4 times out of 7
        Optional<Integer> r = BoyerMoore.findMajority(a, m);
        assertTrue(r.isPresent());
        assertEquals(2, r.get());

        int[] b = {2,1,2,1}; // no majority (2 appears 2 out of 4)
        r = BoyerMoore.findMajority(b, m);
        assertFalse(r.isPresent());
    }
}