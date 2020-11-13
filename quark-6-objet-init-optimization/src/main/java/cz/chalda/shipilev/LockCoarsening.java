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
// -XX:-UseBiasedLocking ; switching off the optimization to not adjust the behaviour of testing
// "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintAssembly"; for getting assembly code, otherwise the -prof only shows more details on average times
// -XX:PrintAssemblyOptions=intel ; ' It annoys me every time I use -XX:+PrintAssembly with Hotspot and have to read the horrible AT&T syntax' -> https://stackoverflow.com/questions/9337670/hotspot7-hsdis-printassembly-intel-syntax
@Fork(value = 1, jvmArgsPrepend = {"-XX:-UseBiasedLocking", "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintAssembly", "-XX:PrintAssemblyOptions=intel"})
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