package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.defaultArgName;
import static org.openjdk.jmh.annotations.Mode.Throughput;
import static org.openjdk.jmh.annotations.Scope.Thread;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    
    private static class SimpleThreadLocal<T> extends ThreadLocal<T> {
        
        private Constructor<? extends T> constructor;
        
        public SimpleThreadLocal(final Class<? extends T> clazz) {
            try {
                constructor = clazz.getConstructor();
            } catch(final NoSuchMethodException e) {
                final String msg = "Class " + clazz.getName()
                        + " does not have a public no-arg constructor";
                throw new IllegalArgumentException(msg, e);
            }
        }
        
        @Override
        protected T initialValue() {
            try {
                return constructor.newInstance();
            } catch(final IllegalAccessException e) {
                throw new AssertionError(e); // Not possible
            } catch(final InvocationTargetException e) {
                final String msg = "Could not create initial value for ThreadLocal with class: "
                        + constructor.getDeclaringClass().getName();
                throw new RuntimeException(msg, e);
            } catch(final InstantiationException e) {
                final String msg = "Could not create initial value for ThreadLocal with class: "
                        + constructor.getDeclaringClass().getName();
                throw new RuntimeException(msg, e);
            }
        }
    }
}
