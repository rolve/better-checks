package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.defaultArgName;
import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import static ch.trick17.betterchecks.MsgFormatId.ARG_NULL;
import ch.trick17.betterchecks.MsgFormatId;

public class BaseCheck<T, C extends BaseCheck<T, C>> {
    
    protected T arg;
    protected String argName;
    protected boolean nullAllowed;
    private boolean inverted;
    
    protected final void reset(final T argument) {
        this.arg = argument;
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
            throw illegalArgumentException(ARG_NULL, inverted, argName);
        inverted = false;
        return me();
    }
    
    /*
     * Implementation methods
     */
    
    protected final C check(final boolean condition,
            final MsgFormatId formatId, final Object... msgArgs) {
        checkNull();
        if(!(nullAllowed && arg == null) && (inverted ? condition : !condition))
            throw illegalArgumentException(formatId, inverted, msgArgs);
        inverted = false;
        return me();
    }
    
    protected final void checkNull() {
        if(!nullAllowed && arg == null)
            throw illegalArgumentException(ARG_NULL, inverted, argName);
    }
    
    @SuppressWarnings("unchecked")
    private C me() {
        return (C) this; // Why is a cast needed here?
    }
}
