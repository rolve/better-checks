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
                    ObjectArrayCheck.class, CollectionCheck.class,
                    NumberCheck.class);
    
    private static final Map<Class<?>, ThreadLocal<? extends BaseCheck<?, ?>>> objectChecks;
    
    static {
        final Map<Class<?>, ThreadLocal<? extends BaseCheck<?, ?>>> map = new HashMap<Class<?>, ThreadLocal<? extends BaseCheck<?, ?>>>();
        for(final Class<? extends BaseCheck<? extends Object, ?>> checkClass : CHECK_CLASSES)
            map.put(checkClass,
                    new NoArgConstructorThreadLocal<BaseCheck<?, ?>>(checkClass));
        objectChecks = Collections.unmodifiableMap(map);
    }
    
    public static <T, C extends BaseCheck<T, C>> C newObjectCheck(
            final Class<C> checkClass, final T argument) {
        final ThreadLocal<? extends BaseCheck<?, ?>> threadLocal = objectChecks
                .get(checkClass);
        assert threadLocal != null;
        
        @SuppressWarnings("unchecked")
        final C check = (C) threadLocal.get();
        check.reset(argument);
        return check;
    }
}
