package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageFormatId.ARG_NULL;
import static ch.trick17.betterchecks.MsgFormatter.defaultArgName;
import static ch.trick17.betterchecks.MsgFormatter.formatMsg;

public class ObjectCheck {
    
    protected Object arg;
    protected String argName;
    protected boolean nullAllowed;
    
    /*
     * Internal methods
     */
    
    protected void reset(final Object argumentName) {
        this.arg = argumentName;
        this.argName = defaultArgName();
        this.nullAllowed = false;
    }
    
    /*
     * State methods
     */
    
    public ObjectCheck named(final String argumentName) {
        this.argName = argumentName;
        return this;
    }
    
    public ObjectCheck isNullOr() {
        nullAllowed = true;
        return this;
    }
    
    /*
     * Checks
     */
    
    public ObjectCheck isNotNull() {
        if(!nullAllowed && arg == null)
            throw new IllegalArgumentException(formatMsg(ARG_NULL, argName));
        return this;
    }
}
