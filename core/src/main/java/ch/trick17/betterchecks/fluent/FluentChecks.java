package ch.trick17.betterchecks.fluent;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.trick17.betterchecks.util.NoArgConstructorThreadLocal;

public class FluentChecks {
    
    @SuppressWarnings("unchecked")
    private static final List<Class<? extends BaseCheck<? extends Object, ?>>> CHECK_CLASSES = Arrays
            .asList(ObjectCheck.class, StringCheck.class,
                    ObjectArrayCheck.class, PrimitiveArrayCheck.class,
                    CollectionCheck.class, NumberCheck.class, UrlCheck.class);
    
    private static final Map<Class<?>, ThreadLocal<? extends BaseCheck<?, ?>>> objectChecks;
    
    static {
        final Map<Class<?>, ThreadLocal<? extends BaseCheck<?, ?>>> map = new HashMap<Class<?>, ThreadLocal<? extends BaseCheck<?, ?>>>();
        for(final Class<? extends BaseCheck<? extends Object, ?>> checkClass : CHECK_CLASSES)
            map.put(checkClass,
                    new NoArgConstructorThreadLocal<BaseCheck<?, ?>>(checkClass));
        objectChecks = Collections.unmodifiableMap(map);
    }
    
    public static <T, C extends BaseCheck<T, C>> C getObjectCheck(
            final Class<C> checkClass, final T argument) {
        final C check = getCheck(checkClass);
        check.reset(argument);
        return check;
    }
    
    public static PrimitiveArrayCheck getPrimitiveArrayCheck(
            final Object argument, final int argumentLength) {
        final PrimitiveArrayCheck check = getCheck(PrimitiveArrayCheck.class);
        check.reset(argument, argumentLength);
        return check;
    }
    
    @SuppressWarnings("unchecked")
    private static <T, C extends BaseCheck<T, C>> C getCheck(
            final Class<C> checkClass) {
        final ThreadLocal<? extends BaseCheck<?, ?>> threadLocal = objectChecks
                .get(checkClass);
        assert threadLocal != null;
        return (C) threadLocal.get();
    }
}
