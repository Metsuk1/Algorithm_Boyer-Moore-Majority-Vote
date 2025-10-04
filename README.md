# Boyer-Moore Majority Vote Algorithm Implementation

This repository contains an implementation of the Boyer-Moore Majority Vote Algorithm, a linear-time algorithm for finding the majority element in an array (an element that appears more than n/2 times). The project includes a CLI interface for running benchmarks, a JMH-based benchmarking harness, and supporting utilities.

## Overview

- **Algorithm**: Boyer-Moore Majority Vote Algorithm (O(n) time complexity).
- **Language**: Java 22.
- **Build Tool**: Apache Maven.
- **Testing**: JUnit 5.
- **Benchmarking**: JMH (Java Microbenchmark Harness) 1.37.

## Usage Instructions

### Prerequisites
- **Java Development Kit (JDK)**: Version 22 or higher (e.g., installed at `C:\Program Files\Java\jdk-22`).
- **Maven**: Version 3.6.0 or higher.
- **IDE (optional)**: IntelliJ IDEA or similar for development.

- This compiles the code, runs tests, and prepares the JAR file.

### Running the CLI Benchmark
The project includes a command-line interface (`cli.CLI`) to run benchmarks with different array sizes.

1. Execute the CLI:

2. - Adjust the classpath to include all required dependencies (e.g., JMH jars from your `.m2` repository).

2. Choose an option:
- **1) Run ALL benchmarks for different sizes**: Tests sizes 100, 500, 1000, 5000, 10000 with a fixed majority element (1).
- **2) Run single size benchmark (fixed majority = 1)**: Enter a custom size (e.g., 567) to test correctness.
- **3) Run single size benchmark (random majority)**: Enter a size for a randomly chosen majority element.
- **4) Exit**: Terminates the program.

Example output for size 567:Size: 567, Time: 1.71 ms, Result: 1, Metrics: comparisons=1116,arrayAccesses=1134,assignments=602

### Running JMH Benchmarks
Use JMH to measure performance accurately.
- This executes the `BoyerMooreBenchmark` for sizes 100, 500, 1000, 5000, and 10000, outputting average time in microseconds.

Example output:Benchmark                                (size)  Mode  Cnt    Score    Error  Units
BoyerMooreBenchmark.measureFindMajority     100  avgt    5    2.849 ±  0.187  us/op
BoyerMooreBenchmark.measureFindMajority    1000  avgt    5   28.079 ±  2.658  us/op

### Notes
- Results are logged to `results.csv` by the CLI.
- Ensure JDK 22 is set as the project SDK in your IDE.
- Use `mvn -U` to force update dependencies if issues arise.

## Complexity Analysis

### Time Complexity
- **First Pass (Candidate Selection)**: O(n)
- The algorithm iterates through the array once to find a candidate for the majority element using a counter-based approach. Each element is accessed and compared at most once.
- **Second Pass (Verification)**: O(n)
- The candidate is verified by counting its occurrences. With the optimization (early exit when occurrences exceed n/2), the average case may be slightly better than O(n) for arrays with a clear majority, but the worst case remains O(n).
- **Total Time Complexity**: O(n)
- The algorithm performs two linear passes, resulting in a linear time complexity.

### Space Complexity
- **O(1)**
- The algorithm uses a constant amount of extra space (variables for candidate and count), regardless of input size. The input array is not modified beyond shuffling in the benchmark setup.

### Performance Observations
- Benchmark results (as of October 04, 2025) show linear scaling:
- Size 100: ~2.849 us/op.
- Size 10000: ~294.731 us/op.
- The slight variability (e.g., ±34.586 us/op for size 10000) is due to JVM overhead, GC, and system noise. Increasing iterations or using profiling can refine these measurements.
