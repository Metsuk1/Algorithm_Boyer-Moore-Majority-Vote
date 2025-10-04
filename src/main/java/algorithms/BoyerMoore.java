package algorithms;

import utils.Metrics;

import java.util.Optional;

/**
 * BoyerMoore — baseline implementation of the Boyer–Moore majority vote algorithm.
 *
 * Behavior:
 *  - First pass: single-pass candidate selection (O(n) time, O(1) extra space).
 *    Uses a candidate value and a counter. The counter increases when the current
 *    element equals the candidate, otherwise decreases; when it reaches zero, candidate resets.
 *  - Second pass: verification (counts how many times candidate appears).
 *    Required because the single-pass candidate is only *a candidate*; it is not guaranteed
 *    to be a majority unless verified.
 *
 * Complexity:
 *  - Time: O(n) (two linear passes)
 *  - Space: O(1) extra (few local variables)
 *
 * Metrics integration:
 *  - arrayAccesses incremented each time we read arr[i]
 *  - comparisons incremented when we compare values (candidate == v and verification comparisons)
 *  - assignments incremented for candidate/count updates and resets
 */
public class BoyerMoore {
    private final Metrics metrics;

    private BoyerMoore(Metrics metrics) {
        this.metrics = metrics;
    }

    /**
     * Finds the majority element (element that appears > n/2 times) if one exists.
     *
     * @param arr input array (must not be null)
     * @param metrics   metrics collector; if null a new Metrics instance will be created and used
     * @return Optional.of(majority) if majority exists, otherwise Optional.empty()
     * @throws IllegalArgumentException if arr is null
     */
    public static Optional<Integer> findMajority(int[] arr, Metrics metrics){
        if(arr == null){
            throw new IllegalArgumentException("Input array must not be null");
        }
        // ensure metrics object exists to avoid NPEs
        if(metrics == null){
            metrics = new Metrics();
        }

        //Edge cases
        if (arr.length == 0) {
            return Optional.empty();
        }
        if(arr.length == 1){
            metrics.incAssignments();//assign candidate
            metrics.incArrayAccesses();//read arr[0]
            return Optional.of(arr[0]);
        }

        //first pass - single-pass candidate selection
        int candidate = -1,count = 0;
        boolean hasCandidate = false;

        for(int i = 0; i < arr.length; i++){
            metrics.incArrayAccesses();
            int v = arr[i];
            metrics.incAssignments(); // v assigned from arr[i]

            if(!hasCandidate){
                // assign candidate = v; count = 1
                candidate = v;
                hasCandidate = true;
                count = 1;
                metrics.incAssignments(); // assign candidate
                metrics.incAssignments(); // hasCandidate
                metrics.incAssignments(); // assign count
                continue;
            }

            // compare candidate with current value
            metrics.incComparisons();
            if(v == candidate){
                count++;
                metrics.incAssignments();//count increment
            }else{
                count--;
                metrics.incAssignments();// count decrement

                if(count == 0){
                    // reset candidate on next iteration
                    hasCandidate = false;
                    metrics.incAssignments();//hasCandidate assign
                }
            }
        }

        if(!hasCandidate){
            // No candidate found so no majority
            return Optional.empty();
        }

        //Second pass - verification
        int occurrences  = 0;
        int threshold = arr.length / 2;
        for(int i = 0; i < arr.length; i++){
            metrics.incArrayAccesses();// we need to accesses each element in the array
            metrics.incComparisons(); // comparing arr[i] to candidate

            if(arr[i] == candidate){
                occurrences++;
                metrics.incAssignments();
                if (occurrences > threshold) {
                    metrics.incComparisons(); // occurrences > threshold
                    // Early exit if majority is confirmed
                    break;
                }
            }
        }

        if(occurrences > arr.length/2){
            return Optional.of(candidate);
        }else{
            return Optional.empty();
        }
    }


}
