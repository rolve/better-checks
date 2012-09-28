package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.defaultArgName;
import ch.trick17.betterchecks.InvalidCheckException;

public abstract class BaseCheck<C extends BaseCheck<C>> {
    
    protected String argName;
    protected boolean inverted;
    
    protected final void reset() {
        this.argName = defaultArgName();
        this.inverted = false;
    }
    
    /*
     * Modifier methods
     */
    
    public final C named(final String argumentName) {
        this.argName = argumentName;
        return me();
    }
    
    public final C not() {
        inverted = !inverted;
        return me();
    }
    
    /*
     * Implementation methods
     */
    
    @SuppressWarnings("unchecked")
    protected final C me() {
        return (C) this; // Why is a cast needed here?
    }
    
    protected static void checkValid(final boolean valid, final String message) {
        if(!valid)
            throw new InvalidCheckException(message);
    }
}
