package cz.chalda.shipilev;


import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * From https://shipilev.net/jvm/anatomy-quarks/7-initialization-costs/
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3,
        jvmArgsPrepend = {"-XX:+UseParallelOldGC", "-Xmx20g", "-Xms20g", "-Xmn18g"})
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class UA {
    @Param({"1", "10", "100", "1000", "10000", "100000"})
    int size;

    @Benchmark
    public byte[] java() {
        return new byte[size];
    }
}