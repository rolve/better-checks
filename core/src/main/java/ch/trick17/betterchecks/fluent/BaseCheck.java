package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.defaultArgName;
import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.CompactChecks;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.InvalidCheckException;

/**
 * The base class for <em>all</em> checks. It manages the state common to all
 * checks: the argument name and the inverted flag. This class has two abstract
 * subclasses which all concrete checks inherit from: {@link ObjectBaseCheck}
 * and {@link PrimitiveBaseCheck}.
 * <p>
 * Because checks are reused, they must be reset using a <code>reset(...)</code>
 * method defined by the subclass before they are returned from one of the
 * {@link Check} or {@link CompactChecks} methods. These <code>reset(...)</code>
 * methods must call {@link #baseReset()}, defined by this class.
 * 
 * @author Michael Faes
 * @param <C>
 *            The type of the concrete check class. This parameter is necessary
 *            for this base class to define methods that return the check object
 *            itself (with the correct static type), which is necessary for the
 *            fluent interface of all checks.
 */
public abstract class BaseCheck<C extends BaseCheck<C>> {
    
    /**
     * The argument name. It is initialized as
     * {@link Exceptions#defaultArgName()} and can be changed using the
     * {@link #named(String)} modifier method.
     */
    protected String argName;
    
    /**
     * The inverted flag. If it is <code>true</code>, the condition of the next
     * check must be inverted. It is initialized as <code>false</code> and can
     * be set with the {@link #not()} modifier method.
     * <p>
     * Note that each check must reset this flag to <code>false</code>.
     */
    protected boolean inverted;
    
    /**
     * Resets the base state of this check. It must be called by a
     * <code>reset(...)</code> method of the subclass to make sure the base
     * state is reset before this check is returned from one of the
     * {@link Check} or {@link CompactChecks} methods.
     */
    protected final void baseReset() {
        this.argName = defaultArgName();
        this.inverted = false;
    }
    
    /* Modifier methods */
    
    /**
     * Gives the argument to be checked a name. This name is used to provide
     * more meaningful exception messages in case a check fails. This modifier
     * should be called before any of the checks. Otherwise, the default
     * argument name (given by {@link Exceptions#defaultArgName()}) is used.
     * 
     * @param argumentName
     *            The name of the argument to be checked
     * @return This check
     */
    public final C named(final String argumentName) {
        this.argName = argumentName;
        return me();
    }
    
    /**
     * Inverts the following check. For example, if the next check would require
     * that a string is not empty, inverting it would require that the string
     * <em>is</em> empty.
     * <p>
     * Note that the check inversion only affects the explicit check and
     * <strong>not the implicit null check</strong> that is done by each check
     * method of {@link ObjectBaseCheck} subclasses. The explicit
     * {@link ObjectBaseCheck#isNotNull() isNotNull()} check can also be
     * inverted this way.
     * 
     * @return This check
     */
    public final C not() {
        inverted = !inverted;
        return me();
    }
    
    /* Implementation methods */
    
    /**
     * A convenience method for subclasses to use to assert the validity of the
     * parameters of a check (<em>not</em> the argument to be checked). For
     * example, the {@link IntCheck#isBetween(int, int) isBetween(int, int)}
     * check uses this method to assert that no invalid range is used, which
     * would make passing the check infeasible.
     * <p>
     * <strong>Note:</strong> Currently, the validity of checks is not checked,
     * meaning this method is not used yet. This may change in later releases of
     * the Better Checks library.
     * 
     * @param valid
     *            The validity condition
     * @param message
     *            The exception message
     * @throws InvalidCheckException
     *             if the condition is <code>false</code>
     */
    protected static void checkValid(final boolean valid, final String message) {
        if(!valid)
            throw new InvalidCheckException(message);
    }
    
    /**
     * Simply casts this check to the <code>C</code> type parameter (which
     * should always be equal to the class of the concrete check).
     * 
     * @return This check as <code>C</code>
     */
    @SuppressWarnings("unchecked")
    protected final C me() {
        return (C) this; // Why is a cast needed here?
    }
}
