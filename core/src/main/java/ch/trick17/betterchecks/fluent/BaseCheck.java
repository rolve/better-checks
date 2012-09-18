package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageFormatId.ARG_NULL;
import static ch.trick17.betterchecks.MsgFormatter.defaultArgName;
import static ch.trick17.betterchecks.MsgFormatter.formatMsg;

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
        return castMe();
    }
    
    public final C isNullOr() {
        nullAllowed = true;
        return castMe();
    }
    
    public final C isNotNull() {
        if(!nullAllowed && arg == null)
            throw new IllegalArgumentException(formatMsg(ARG_NULL, argName));
        return castMe();
    }
    
    @SuppressWarnings("unchecked")
    private C castMe() {
        return (C) this; // Why is a cast needed here?
    }
}
