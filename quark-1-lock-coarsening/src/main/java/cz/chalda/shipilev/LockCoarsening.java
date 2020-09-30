package cz.chalda.shipilev;


import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * From https://shipilev.net/jvm/anatomy-quarks/1-lock-coarsening-for-loops/
 */
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
// -XX:LoopUnrollLimit=1 ; limit loop unrolling to decrease the performance benefit of coarsening the lock of the unrolled loop
// "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintAssembly"; for getting more info but I can't see any more data with it
@Fork(value = 1, jvmArgsPrepend = {"-XX:-UseBiasedLocking", "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintAssembly"})
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LockCoarsening {

    int x;

    @Benchmark
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void test() {
        for (int c = 0; c < 1000; c++) {
            synchronized (this) {
                x += 0x42;
            }
        }
    }

}