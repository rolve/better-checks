package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;
import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.CompactChecks;
import ch.trick17.betterchecks.MessageType;

/**
 * The check class for <code>double</code> arguments. It can also be (and
 * <em>is</em> - if you use the {@link Check} methods correctly) used for
 * <code>float</code> arguments.
 * <p>
 * It provides methods like {@link #isPositive()} or
 * {@link #isBetween(double, double)} but also methods specific to floating
 * numbers like {@link #isNotInfinite()} and {@link #isNotNaN()}.
 * 
 * @author Michael Faes
 */
public final class DoubleCheck extends PrimitiveBaseCheck<DoubleCheck> {
    
    private double arg;
    
    /**
     * Default constructor, for internal use only.
     */
    public DoubleCheck() {}
    
    /**
     * Resets all state of this check. This method must be called every time
     * before this check object is returned by one of the {@link Check} or
     * {@link CompactChecks} methods.
     * 
     * @param argument
     *            The new argument to be checked
     */
    protected void reset(final double argument) {
        reset();
        this.arg = argument;
    }
    
    /**
     * Checks that the <code>double</code> argument is positive, throwing an
     * exception otherwise. Note that zero is not a valid number. Often, you
     * want to check that a number is non-negative. Use the inverted
     * {@link #isNegative()} check for that:
     * <p>
     * <code>Check.that(number).not().isNegative();</code>
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_POSITIVE}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not positive
     * @see #isNegative()
     */
    public DoubleCheck isPositive() {
        return check(arg > 0, ARG_POSITIVE, argName, arg);
    }
    
    /**
     * Checks that the <code>double</code> argument is negative, throwing an
     * exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_NEGATIVE}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not negative
     * @see #isPositive()
     */
    public DoubleCheck isNegative() {
        return check(arg < 0, ARG_NEGATIVE, argName, arg);
    }
    
    /**
     * Checks that the <code>double</code> argument is equal to the given
     * number, throwing an exception otherwise. This check exists merely for
     * reasons of symmetry to the {@link IntCheck} class which can be used as a
     * property check. See {@link StringCheck#hasLengthWhich()} for example.
     * However, in conjunction with inversion (see {@link #not()}), this method
     * can also be used to exclude a single value.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_IS}.
     * 
     * @param number
     *            The number this argument must be equal to
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not equal to the given number
     */
    public DoubleCheck is(final double number) {
        return check(arg == number, ARG_IS, argName, number, arg);
    }
    
    /**
     * Checks that the <code>double</code> argument is (strictly) greater than
     * the given number, throwing an exception otherwise. To create a
     * greater-than-or-equals check, you may use an inverted
     * {@link #isLessThan(double)} check:
     * <p>
     * <code>Check.that(number).not().isLessThan(1.0);</code>
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_GREATER}.
     * 
     * @param number
     *            The number this argument must be greater than
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not greater than the given number
     */
    public DoubleCheck isGreaterThan(final double number) {
        return check(arg > number, ARG_GREATER, argName, number, arg);
    }
    
    /**
     * Checks that the <code>double</code> argument is (strictly) less than the
     * given number, throwing an exception otherwise. To create a
     * less-than-or-equals check, you may use an inverted
     * {@link #isGreaterThan(double)} check:
     * <p>
     * <code>Check.that(number).not().isGreaterThan(1.0);</code>
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_LESS}.
     * 
     * @param number
     *            The number this argument must be less than
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not less than the given number
     */
    public DoubleCheck isLessThan(final double number) {
        return check(arg < number, ARG_LESS, argName, number, arg);
    }
    
    /**
     * Checks that the <code>double</code> argument is greater than or equal to
     * <code>min</code> and less than or equal to <code>max</code>, throwing an
     * exception otherwise.
     * <p>
     * To create a strict or one-sided-strict between test, you may use the
     * {@link #isGreaterThan(double)} and {@link #isLessThan(double)} checks,
     * possibly in conjunction with {@link #not()}.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_BETWEEN}.
     * 
     * @param min
     *            The minimum number
     * @param max
     *            The maximum number
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not between the two given numbers as defined
     *             above
     */
    public DoubleCheck isBetween(final double min, final double max) {
        return check(arg >= min && arg <= max, ARG_BETWEEN, argName, min, max,
                arg);
    }
    
    /**
     * Checks that the <code>double</code> argument is not NaN (not a number,
     * see {@link Double#NaN}), throwing an exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_NAN}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is NaN
     */
    public DoubleCheck isNotNaN() {
        return check(!Double.isNaN(arg), ARG_NAN, argName);
    }
    
    /**
     * Checks that the <code>double</code> argument is not infinite (positive or
     * negative), throwing an exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_INFINITE}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is infinite
     */
    public DoubleCheck isNotInfinite() {
        return check(!Double.isInfinite(arg), ARG_INFINITE, argName, arg);
    }
}
