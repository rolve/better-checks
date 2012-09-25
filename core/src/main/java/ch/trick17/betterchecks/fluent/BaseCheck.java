package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.defaultArgName;
import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import static ch.trick17.betterchecks.MessageType.*;
import ch.trick17.betterchecks.MessageType;

public class BaseCheck<T, C extends BaseCheck<T, C>> {
    
    protected T arg;
    protected Class<?> argClass;
    protected String argName;
    protected boolean nullAllowed;
    private boolean inverted;
    
    protected final void reset(final T argument) {
        this.arg = argument;
        if(argument == null)
            argClass = null;
        else
            argClass = argument.getClass();
        this.argName = defaultArgName();
        this.nullAllowed = false;
        this.inverted = false;
    }
    
    /*
     * Modifier methods
     */
    
    public final C named(final String argumentName) {
        this.argName = argumentName;
        return me();
    }
    
    public final C isNullOr() {
        nullAllowed = true;
        return me();
    }
    
    public final C not() {
        inverted = !inverted;
        return me();
    }
    
    /*
     * Checks
     */
    
    public final C isNotNull() {
        if(!nullAllowed && (inverted ? arg != null : arg == null))
            throw illegalArgumentException(ARG_NULL, inverted,
                    new Object[] {argName});
        inverted = false;
        return me();
    }
    
    public final C isInstanceOf(final Class<?> type) {
        return check(arg == null || type.isAssignableFrom(arg.getClass()),
                ARG_INSTANCE, argName, type, argClass);
    }
    
    public final C hasClass(final Class<?> clazz) {
        return check(arg == null || arg.getClass() == clazz, ARG_CLASS,
                argName, clazz, argClass);
    }
    
    /*
     * Implementation methods
     */
    
    protected final C check(final boolean condition,
            final MessageType formatId, final Object... msgArgs) {
        checkNull();
        if(!(nullAllowed && arg == null) && (inverted ? condition : !condition))
            throw illegalArgumentException(formatId, inverted, msgArgs);
        inverted = false;
        return me();
    }
    
    protected final C checkWithCause(final boolean condition,
            final MessageType formatId, final Throwable cause,
            final Object... msgArgs) {
        checkNull();
        if(!(nullAllowed && arg == null) && (inverted ? condition : !condition))
            throw illegalArgumentException(formatId, inverted, msgArgs, cause);
        inverted = false;
        return me();
    }
    
    protected final void checkNull() {
        if(!nullAllowed && arg == null)
            throw illegalArgumentException(ARG_NULL, inverted,
                    new Object[] {argName});
    }
    
    @SuppressWarnings("unchecked")
    private C me() {
        return (C) this; // Why is a cast needed here?
    }
}
