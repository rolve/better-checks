package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import ch.trick17.betterchecks.MessageType;

public class PrimitiveBaseCheck<C extends PrimitiveBaseCheck<C>> extends
        BaseCheck<C> {
    
    private boolean disabled;
    
    /**
     * Resets all state in this check object. It can be used as if it were new.
     */
    protected final void reset() {
        baseReset();
        disabled = false;
    }
    
    /**
     * Disables all checks that are called on this check object. This is used
     * for property checks where the owner is null and allowed to be null.
     */
    protected final void disable() {
        disabled = true;
    }
    
    /**
     * Checks the given condition in the context of this check object's current
     * state. This means:
     * <ul>
     * <li>If this check is not {@link #disable()}d, and not inverted (see
     * {@link #not()}), an exception is thrown if the condition is
     * <strong>false</strong>.</li>
     * <li>Else, if this check is not disabled but inverted, an exception is
     * thrown if the condition is <strong>true</strong>.</li>
     * <li>Else (if this check is disabled), nothing happens.</li>
     * <li>In any case, the check inversion is reset after the check</li>
     * </ul>
     * 
     * @param condition
     *            The condition to be checked
     * @param msgType
     *            The {@link MessageType} for the exception message
     * @param msgArgs
     *            The arguments for the exception message
     * @return This check object
     */
    protected final C check(final boolean condition, final MessageType msgType,
            final Object... msgArgs) {
        if(!disabled && (inverted ? condition : !condition))
            throw illegalArgumentException(msgType, inverted, msgArgs);
        inverted = false;
        return me();
    }
}
