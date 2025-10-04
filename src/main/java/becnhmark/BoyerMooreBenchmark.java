package becnhmark;

import algorithms.BoyerMoore;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import utils.Metrics;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static cli.CLI.generateArrayWithRandomMajority;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1, jvmArgs = {"-Xms2g", "-Xmx2g"}) // enough memory for arrays
public class BoyerMooreBenchmark {

    @Param({"100", "500", "1000", "5000", "10000"})
    private int size;

    private int[] arr;
    private Metrics metrics;

    @Setup
    public void setUp() {
        Random random = new Random();
        metrics = new Metrics();
        arr = generateArrayWithRandomMajority(size, random);
    }

    @Benchmark
    public void measureFindMajority(Blackhole blackhole) {
        Optional<Integer> result = BoyerMoore.findMajority(arr, metrics);
        blackhole.consume(result);
    }

    @TearDown
    public void tearDown() {
        metrics.reset(); // reset metrics after each start
    }
}
