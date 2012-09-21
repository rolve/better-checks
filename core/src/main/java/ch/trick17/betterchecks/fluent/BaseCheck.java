package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.defaultArgName;
import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import static ch.trick17.betterchecks.MsgFormatId.ARG_NULL;

public class BaseCheck<T, C extends BaseCheck<T, C>> {
    
    protected T arg;
    protected String argName;
    protected boolean nullAllowed;
    
    protected final void reset(final T argument) {
        this.arg = argument;
        this.argName = defaultArgName();
        this.nullAllowed = false;
    }
    
    public final C named(final String argumentName) {
        this.argName = argumentName;
        return me();
    }
    
    public final C isNullOr() {
        nullAllowed = true;
        return me();
    }
    
    public final C isNotNull() {
        if(!nullAllowed && arg == null)
            throw illegalArgumentException(ARG_NULL, argName);
        return me();
    }
    
    @SuppressWarnings("unchecked")
    private C me() {
        return (C) this; // Why is a cast needed here?
    }
}
