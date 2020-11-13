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
 * From https://shipilev.net/jvm/anatomy-quarks/6-new-object-stages/
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3, jvmArgsPrepend =
    {"-XX:-UseBiasedLocking", "-XX:-TieredCompilation", "-XX:+UseParallelGC", "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintAssembly", "-XX:PrintAssemblyOptions=intel"})
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class UserInit {

    @Benchmark
    public Object init() {
        return new Init(42);
    }

    @Benchmark
    public Object initLeaky() {
        return new InitLeaky(42);
    }

    static class Init {
        private int x;
        public Init(int x) {
            this.x = x;
        }
    }

    static class InitLeaky {
        private int x;
        public InitLeaky(int x) {
            doSomething();
            this.x = x;
        }

        @CompilerControl(CompilerControl.Mode.DONT_INLINE)
        void doSomething() {
            // intentionally left blank
        }
    }
}