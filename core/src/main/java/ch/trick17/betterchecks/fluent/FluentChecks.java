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
            .asList(ObjectCheck.class, StringCheck.class, CollectionCheck.class);
    
    private static final Map<Class<?>, ThreadLocal<? extends BaseCheck<?, ?>>> classChecks;
    
    static {
        final Map<Class<?>, ThreadLocal<? extends BaseCheck<?, ?>>> map = new HashMap<Class<?>, ThreadLocal<? extends BaseCheck<?, ?>>>();
        for(final Class<? extends BaseCheck<? extends Object, ?>> checkClass : CHECK_CLASSES)
            map.put(checkClass,
                    new NoArgConstructorThreadLocal<BaseCheck<?, ?>>(checkClass));
        classChecks = Collections.unmodifiableMap(map);
    }
    
    public static <T, C extends BaseCheck<T, C>> C newClassCheck(
            final Class<C> checkClass, final T argument) {
        @SuppressWarnings("unchecked")
        final C check = (C) classChecks.get(checkClass).get();
        check.reset(argument);
        return check;
    }
}
