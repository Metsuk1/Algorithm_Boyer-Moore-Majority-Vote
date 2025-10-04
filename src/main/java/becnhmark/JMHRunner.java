package becnhmark;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

public class JMHRunner {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BoyerMooreBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .warmupTime(TimeValue.valueOf("1s"))
                .measurementIterations(5)
                .measurementTime(TimeValue.valueOf("1s"))
                .forks(1)
                .jvmArgs("-Xms2g", "-Xmx2g")
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MICROSECONDS)
                .shouldDoGC(true)
                .build();

        new Runner(opt).run();
    }
}
