package benchmarks;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;
import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class BoyerMooreBenchmark {

    @Param({"100", "1000", "10000"})
    private int size;

    private int[] data;

    @Setup(Level.Trial)
    public void setup() {
        data = new int[size];
        Random rand = new Random(42);
        for (int i = 0; i < size; i++) {
            data[i] = rand.nextInt(10);
        }
    }

    @Benchmark
    public int testBoyerMoore() {
        PerformanceTracker tracker = new PerformanceTracker();
        return BoyerMooreMajorityVote.findMajorityElement(data, tracker);
    }
}
