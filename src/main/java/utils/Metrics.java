package utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Metrics - collects counters for algorithm analysis.
 * Tracks comparisons, array accesses, assignments, and memory allocations.
 * comparisons: number of logical comparisons (==, <, >, etc.)
 * arrayAccesses: number of array element reads/writes
 * assignments: number of variable assignments (updates of counters, candidate variables, etc.)
 * memoryAllocations: number of memory allocations (new arrays, new objects; approximate in JVM)
 * Thread-safe: all counters use AtomicLong to allow safe increments
 */
public class Metrics {
    private final AtomicLong comparisons = new AtomicLong(0);
    private final AtomicLong arrayAccesses = new AtomicLong(0);
    private final AtomicLong assignments = new AtomicLong(0);
    private final AtomicLong memoryAllocations = new AtomicLong(0); // approximate

    // Comparisons
    public void incComparisons() { comparisons.incrementAndGet(); }
    public void addComparisons(long delta) { comparisons.addAndGet(delta); }
    public long getComparisons() { return comparisons.get(); }

    // Array accesses
    public void incArrayAccesses() { arrayAccesses.incrementAndGet(); }
    public void addArrayAccesses(long delta) { arrayAccesses.addAndGet(delta); }
    public long getArrayAccesses() { return arrayAccesses.get(); }

    // Assignments
    public void incAssignments() { assignments.incrementAndGet(); }
    public void addAssignments(long delta) { assignments.addAndGet(delta); }
    public long getAssignments() { return assignments.get(); }

    // Memory allocations
    public void incMemoryAllocations() { memoryAllocations.incrementAndGet(); }
    public void addMemoryAllocations(long delta) { memoryAllocations.addAndGet(delta); }
    public long getMemoryAllocations() { return memoryAllocations.get(); }

    @Override
    public String toString() {
        return String.format("comparisons=%d,arrayAccesses=%d,assignments=%d,memoryAllocations=%d",
                getComparisons(), getArrayAccesses(), getAssignments(), getMemoryAllocations());
    }
}
