package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.defaultArgName;
import static org.openjdk.jmh.annotations.Mode.Throughput;
import static org.openjdk.jmh.annotations.Scope.Thread;

import java.util.HashMap;
import java.util.Map;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import ch.trick17.betterchecks.util.SimpleThreadLocal;

@BenchmarkMode(Throughput)
@Fork(1)
@State(Thread)
public class ReuseChecksBenchmark {
    
    /**
     * Simple class for benchmarking, so that the benchmark is independent of the "production" code.
     */
    public static class SomeCheck {
        Object arg;
        String argName;
        
        private void reset(Object newArg) {
            this.arg = newArg;
            this.argName = defaultArgName();
        }
    }
    
    private final Map<Class<?>, ThreadLocal<?>> checks = new HashMap<Class<?>, ThreadLocal<?>>();
    private ThreadLocal<SomeCheck> check;
    
    @Setup
    public void setup() {
        checks.clear();
        checks.put(SomeCheck.class, new SimpleThreadLocal<SomeCheck>(SomeCheck.class));
        
        check = new SimpleThreadLocal<SomeCheck>(SomeCheck.class);
    }
    
    @Benchmark
    public SomeCheck fromThreadLocalMap() {
        SomeCheck result = (SomeCheck) checks.get(SomeCheck.class).get();
        result.reset(null);
        return result;
    }
    
    @Benchmark
    public SomeCheck fromThreadLocal() {
        SomeCheck result = check.get();
        result.reset(null);
        return result;
    }
    
    @Benchmark
    public SomeCheck createNew() {
        SomeCheck result = new SomeCheck();
        result.reset(null);
        return result;
    }
    
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(ReuseChecksBenchmark.class.getSimpleName())
                .warmupIterations(5).measurementIterations(20).build();
        new Runner(options).run();
    }
}
