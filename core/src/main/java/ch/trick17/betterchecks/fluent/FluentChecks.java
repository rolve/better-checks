package ch.trick17.betterchecks.fluent;

import ch.trick17.betterchecks.util.NoArgConstructorThreadLocal;

public class FluentChecks {
    
    private static final ThreadLocal<ObjectCheck> objectCheck = new NoArgConstructorThreadLocal<ObjectCheck>(
            ObjectCheck.class);
    private static final ThreadLocal<StringCheck> stringCheck = new NoArgConstructorThreadLocal<StringCheck>(
            StringCheck.class);
    
    public static ObjectCheck newObjectCheck(final Object argument) {
        final ObjectCheck check = objectCheck.get();
        check.reset(argument);
        return check;
    }
    
    public static StringCheck newStringCheck(final String argument) {
        final StringCheck check = stringCheck.get();
        check.reset(argument);
        return check;
    }
    
}
