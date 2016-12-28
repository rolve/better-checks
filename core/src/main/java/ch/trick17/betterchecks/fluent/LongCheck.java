package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import ch.trick17.betterchecks.MessageType;
import ch.trick17.betterchecks.util.GwtCompatible;

/**
 * The check class for <code>long</code> arguments.
 * <p>
 * It provides methods like {@link #isPositive()}, {@link #isGreaterThan(long)}
 * or {@link #isBetween(long, long)}.
 * 
 * @author Michael Faes
 */
@GwtCompatible
public final class LongCheck extends PrimitiveBaseCheck<LongCheck> {
    
    private final long arg;
    
    /**
     * For internal use only.
     */
    public LongCheck(long arg) {
        this.arg = arg;
    }
    
    /**
     * Checks that the <code>long</code> argument is positive, throwing an
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
    public LongCheck isPositive() {
        return check(arg > 0, ARG_POSITIVE, argName, arg);
    }
    
    /**
     * Checks that the <code>long</code> argument is negative, throwing an
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
    public LongCheck isNegative() {
        return check(arg < 0, ARG_NEGATIVE, argName, arg);
    }
    
    /**
     * Checks that the <code>long</code> argument is equal to the given number,
     * throwing an exception otherwise. This check exists merely for reasons of
     * symmetry to the {@link IntCheck} class which can be used as a property
     * check. See {@link StringCheck#hasLengthWhich()} for example. However, in
     * conjunction with inversion (see {@link #not()}), this method can also be
     * used to exclude a single value.
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
    public LongCheck is(final long number) {
        return check(arg == number, ARG_IS, argName, number, arg);
    }
    
    /**
     * Checks that the <code>long</code> argument is (strictly) greater than the
     * given number, throwing an exception otherwise. To create a
     * greater-than-or-equals check, you may either decrement the
     * <code>number</code> argument by one or use an inverted
     * {@link #isLessThan(long)} check:
     * <p>
     * <code>Check.that(number).not().isLessThan(1L);</code>
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
    public LongCheck isGreaterThan(final long number) {
        return check(arg > number, ARG_GREATER, argName, number, arg);
    }
    
    /**
     * Checks that the <code>long</code> argument is (strictly) less than the
     * given number, throwing an exception otherwise. To create a
     * less-than-or-equals check, you may either increment the
     * <code>number</code> argument by one or use an inverted
     * {@link #isGreaterThan(long)} check:
     * <p>
     * <code>Check.that(number).not().isGreaterThan(1L);</code>
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
    public LongCheck isLessThan(final long number) {
        return check(arg < number, ARG_LESS, argName, number, arg);
    }
    
    /**
     * Checks that the <code>long</code> argument is greater than or equal to
     * <code>min</code> and less than or equal to <code>max</code>, throwing an
     * exception otherwise.
     * <p>
     * To create a strict or one-sided-strict between test, you may either
     * increment or decrement the arguments of this check or use the
     * {@link #isGreaterThan(long)} and {@link #isLessThan(long)} checks,
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
    public LongCheck isBetween(final long min, final long max) {
        return check(arg >= min && arg <= max, ARG_BETWEEN, argName, min, max,
                arg);
    }
}
