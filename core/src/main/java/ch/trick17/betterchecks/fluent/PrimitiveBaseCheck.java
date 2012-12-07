package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import ch.trick17.betterchecks.MessageType;

/**
 * The base class for all primitive value checks. It manages the state and
 * provides implementations common to these checks. The state consists of the
 * disabled flag plus the state managed by {@link BaseCheck}.
 * 
 * @author Michael Faes
 * @param <C>
 *            The type of the concrete check class. This parameter is necessary
 *            for this base class to define methods that return the check object
 *            itself (with the correct static type), which is necessary for the
 *            fluent interface of all checks.
 * @see ObjectBaseCheck
 */
public class PrimitiveBaseCheck<C extends PrimitiveBaseCheck<C>> extends
        BaseCheck<C> {
    
    /**
     * The disabled flag. It is used for property checks where the owner is null
     * and allowed to be null.
     * 
     * @see ObjectBaseCheck#intPropertyCheck(int, String)
     */
    private boolean disabled;
    
    /**
     * Resets the common state of this check class. This method must be called
     * by the subclasses' more specific <code>reset(...)</code> methods.
     */
    protected final void reset() {
        baseReset();
        disabled = false;
    }
    
    /**
     * Disables all checks that are called on this check object. This is used
     * for property checks where the owner is null and allowed to be null.
     * 
     * @return This check
     * @see ObjectBaseCheck#intPropertyCheck(int, String)
     */
    protected final C disable() {
        disabled = true;
        return me();
    }
    
    /**
     * A helper method that allows subclasses to perform simple checks
     * conveniently with a one-liner. It checks the given condition in the
     * context of this check object's current state. This means:
     * <ul>
     * <li>If this check is not {@link #disabled}, it checks if the given
     * condition is <code>true</code> or <code>false</code> respectively,
     * depending on the {@link BaseCheck#inverted inverted} flag. If the check
     * fails, an {@link IllegalArgumentException} is thrown, with a message
     * formatted using the format belonging to the given message type and the
     * given message arguments.
     * <li>Else (if this check is disabled), nothing happens.
     * <li>Then, it resets the <code>inverted</code> flag.
     * <li>Finally, it returns this check.
     * </ul>
     * 
     * @param condition
     *            The check condition
     * @param msgType
     *            The message type to be used for the exception message thrown
     *            if the check fails
     * @param msgArgs
     *            The arguments for the exception message
     * @return This check
     */
    protected final C check(final boolean condition, final MessageType msgType,
            final Object... msgArgs) {
        if(!disabled && (inverted ? condition : !condition))
            throw illegalArgumentException(msgType, inverted, msgArgs);
        inverted = false;
        return me();
    }
}
