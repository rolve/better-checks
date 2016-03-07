package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import static ch.trick17.betterchecks.MessageType.*;
import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.CompactChecks;
import ch.trick17.betterchecks.MessageType;
import ch.trick17.betterchecks.util.GwtCompatible;
import ch.trick17.betterchecks.util.GwtIncompatible;

/**
 * The base class for all object checks (as opposed to primitive value checks).
 * It manages the state and provides implementations common to all these checks.
 * The state consists of the argument, the argument class and the null-allowed
 * flag plus the state managed by {@link BaseCheck}.
 * 
 * @author Michael Faes
 * @param <T>
 *            The argument type, defined by the concrete check classes
 * @param <C>
 *            The type of the concrete check class. This parameter is necessary
 *            for this base class to define methods that return the check object
 *            itself (with the correct static type), which is necessary for the
 *            fluent interface of all checks.
 * @see PrimitiveBaseCheck
 */
@GwtCompatible
public abstract class ObjectBaseCheck<T, C extends ObjectBaseCheck<T, C>>
        extends BaseCheck<C> {
    
    /**
     * The argument that is currently checked. It is initialized by the
     * {@link #reset(Object)} method.
     */
    protected T arg;
    
    /**
     * The class of the argument. Note that this may also be a subclass of the
     * <code>C</code> type parameter (as opposed to exactly <code>C</code>). It
     * is initialized by the {@link #reset(Object)} method and used for the
     * {@link #hasClass(Class)} check.
     */
    protected Class<?> argClass;
    
    /**
     * This flag indicates that <code>null</code> is a valid value for the
     * argument of this check. It is initialized to <code>false</code> and may
     * be set using the {@link #isNullOr()} modifier.
     * <p>
     * All checks must respect this flag. If the
     * {@link #check(boolean, MessageType, Object...)} helper method is used,
     * the condition passed to it must always start with "
     * <code>arg == null ||</code>" to not cause a {@link NullPointerException}.
     */
    protected boolean nullAllowed;
    
    /**
     * Resets all state of this check. This method must be called every time
     * before this check object is returned by one of the {@link Check} or
     * {@link CompactChecks} methods.
     * 
     * @param argument
     *            The argument to be checked
     */
    protected final void reset(final T argument) {
        baseReset();
        this.arg = argument;
        if(argument == null)
            argClass = null;
        else
            argClass = argument.getClass();
        this.nullAllowed = false;
    }
    
    /* Modifier methods */
    
    /**
     * Allow the argument to be <code>null</code>. This method modifies all
     * subsequent checks of this check object to not perform the implicit not-
     * <code>null</code> check that is done by default. It should be called
     * before any of the check methods because the checks are performed
     * immediately when a check method is called.
     * 
     * @return This check
     */
    public final C isNullOr() {
        nullAllowed = true;
        return me();
    }
    
    /* Checks */
    
    /**
     * Checks that the argument is not <code>null</code>, throwing an exception
     * otherwise. This check is sensible to both the {@link #isNotNull()} and
     * the {@link #not()} modifier. If the former is called before this one,
     * this check is redundant. The later however, changes this check to
     * <em>only</em> accept <code>null</code> as a value.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_NULL}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public final C isNotNull() {
        if(!nullAllowed && (inverted ? arg != null : arg == null))
            throw illegalArgumentException(ARG_NULL, inverted, new Object[]{
                    argName, arg});
        inverted = false;
        return me();
    }

    /**
     * Checks that the argument is the same instance as the given object,
     * throwing an exception otherwise. This check probably only makes sense
     * if used together with {@link #not()}.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_SAME_AS}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if the argument is not the same as the given object
     */
    public final C isSameAs(Object object) {
        return check(arg == null || arg == object, ARG_SAME_AS, argName, object, arg);
    }

    /**
     * Checks that the argument is {@linkplain Object#equals(Object) equal to} the
     * given object, throwing an exception otherwise. 
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_EQUAL_TO}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if the argument is not equal to the given object
     */
    public final C isEqualTo(Object object) {
        return check(arg == null || arg.equals(object), ARG_EQUAL_TO, argName, object, arg);
    }
    
    // IMPROVE: isOneOf, isEqualToOneOf
    
    /**
     * Checks that the argument is an instance of the given type, throwing an
     * exception otherwise. Note that the argument's class may also be a
     * subclass of the given type. To check the exact class, use the
     * {@link #hasClass(Class)} method.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_INSTANCE_OF}.
     * 
     * @param type
     *            The type the argument should be an instance of
     * @return This check
     * @throws IllegalArgumentException
     *             if the argument is not an instance of the given type
     */
    @GwtIncompatible("Class.isAssignableFrom")
    public final C isInstanceOf(final Class<?> type) {
        return check(arg == null || type.isAssignableFrom(arg.getClass()),
                ARG_INSTANCE_OF, argName, type, argClass);
    }
    
    /**
     * Checks that the argument has the given class, throwing an exception
     * otherwise. Note that this is different from the
     * {@link #isInstanceOf(Class)} check.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_CLASS}.
     * 
     * @param clazz
     *            The class the argument should have
     * @return This check
     * @throws IllegalArgumentException
     *             if the argument does not have the given class
     */
    public final C hasClass(final Class<?> clazz) {
        return check(arg == null || arg.getClass() == clazz, ARG_CLASS,
                argName, clazz, argClass);
    }
    
    // IMPROVE: hasClassWhich
    
    /* Implementation methods */
    
    /**
     * A helper method that allows subclasses to perform simple checks
     * conveniently with a one-liner. It checks the given condition in the
     * context of this check object's current state. This means:
     * <ul>
     * <li>First, it performs the ({@link #isNullOr() isNullOr}-aware)
     * <code>null</code>-check
     * <li>Then, it checks if the given condition is <code>true</code> or
     * <code>false</code> respectively, depending on the
     * {@link BaseCheck#inverted inverted} flag. If the check fails, an
     * {@link IllegalArgumentException} is thrown, with a message formatted
     * using the format belonging to the given message type and the given
     * message arguments.
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
        checkNull();
        if(!(nullAllowed && arg == null) && (inverted ? condition : !condition))
            throw illegalArgumentException(msgType, inverted, msgArgs);
        inverted = false;
        return me();
    }
    
    /**
     * The same as {@link #check(boolean, MessageType, Object...)} but with the
     * possibility to define a cause for the exception that is thrown if the
     * check fails.
     * 
     * @param condition
     *            The check condition
     * @param msgType
     *            The message type to be used for the exception message thrown
     *            if the check fails
     * @param cause
     *            The cause for the exception
     * @param msgArgs
     *            The arguments for the exception message
     * @return This check
     */
    protected final C checkWithCause(final boolean condition,
            final MessageType msgType, final Throwable cause,
            final Object... msgArgs) {
        checkNull();
        if(!(nullAllowed && arg == null) && (inverted ? condition : !condition))
            throw illegalArgumentException(msgType, inverted, msgArgs, cause);
        inverted = false;
        return me();
    }
    
    /**
     * Performs the implicit <code>null</code> check that should be done before
     * each check. It takes the {@link #isNullOr()} modifier into account.
     * <p>
     * This method can be used by check methods if the check can not be
     * implemented using a {@link #check(boolean, MessageType, Object...)}
     * one-liner.
     */
    protected final void checkNull() {
        if(!nullAllowed && arg == null)
            throw illegalArgumentException(ARG_NULL, nullAllowed,
                    new Object[]{argName});
    }
    
    /**
     * Creates an object property check for this argument. An example of an
     * object property check is {@link UrlCheck#hasHostWhich()}. It can be used
     * to write something like this:
     * <p>
     * <code>Check.that(url).hasHostWhich().endsWith(".ch");</code>
     * <p>
     * This method can only be used to create property checks for object
     * properties (as opposed to primitive properties). The
     * {@link #intPropertyCheck(int, String)} method can be used to create
     * checks for <code>int</code> checks. Other primitive types are not
     * supported (or needed) yet.
     * 
     * @param checkClass
     *            The class of the property check
     * @param property
     *            The property to be checked
     * @param propertyName
     *            The name of the property. This is used to provide meaningful
     *            exception messages for property checks
     * @return A check of the given class for the given property
     */
    protected final <T1, C1 extends ObjectBaseCheck<T1, C1>> C1 objectPropertyCheck(
            final Class<C1> checkClass, final T1 property,
            final String propertyName) {
        checkNull();
        final C1 check = FluentChecks.getObjectCheck(checkClass, property);
        if(nullAllowed)
            check.isNullOr();
        return check.named("the " + propertyName + " of " + argName);
    }
    
    /**
     * Creates an <code>int</code> property check for this argument. An example
     * of an <code>int</code> property check is
     * {@link StringCheck#hasLengthWhich()}. It can be used to write something
     * like this:
     * <p>
     * <code>Check.that(name).hasLengthWhich().isGreaterThan(10);</code>
     * 
     * @param property
     *            The <code>int</code> property to be checked
     * @param propertyName
     *            The name of the property. This is used to provide meaningful
     *            exception messages for property checks
     * @return A check for the given property
     */
    protected final IntCheck intPropertyCheck(final int property,
            final String propertyName) {
        checkNull();
        final IntCheck check = FluentChecks.getIntCheck(property);
        if(nullAllowed && arg == null)
            check.disable();
        return check.named("the " + propertyName + " of " + argName);
    }
}
