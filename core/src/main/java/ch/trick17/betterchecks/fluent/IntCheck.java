package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.util.Collection;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.CompactChecks;
import ch.trick17.betterchecks.MessageType;

/**
 * The check class for <code>int</code> arguments. It can also be (and
 * <em>is</em> - if you use the {@link Check} methods properly) used for
 * <code>short</code> and <code>byte</code> arguments.
 * <p>
 * It provides methods like {@link #isPositive()} or
 * {@link #isBetween(int, int)} but also methods specific to integer numbers
 * like {@link #isValidIndex(Object[])}.
 * 
 * @author Michael Faes
 */
public class IntCheck extends PrimitiveBaseCheck<IntCheck> {
    
    private int arg;
    
    /**
     * Default constructor, for internal usage only.
     */
    public IntCheck() {}
    
    /**
     * Resets all state of this check. This method must be called every time
     * before this check object is returned by one of the {@link Check} or
     * {@link CompactChecks} methods.
     * 
     * @param argument
     *            The new argument to be checked
     */
    protected final void reset(final int argument) {
        reset();
        this.arg = argument;
    }
    
    /**
     * Checks that the <code>int</code> argument is positive, throwing an
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
    public IntCheck isPositive() {
        return check(arg > 0, ARG_POSITIVE, argName, arg);
    }
    
    /**
     * Checks that the <code>int</code> argument is negative, throwing an
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
    public IntCheck isNegative() {
        return check(arg < 0, ARG_NEGATIVE, argName, arg);
    }
    
    /**
     * Checks that the <code>int</code> argument is equal to the given number,
     * throwing an exception otherwise. This check exists merely for the use in
     * a property check. See {@link StringCheck#hasLengthWhich()} for example.
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
    public IntCheck is(final int number) {
        return check(arg == number, ARG_IS, argName, number, arg);
    }
    
    /**
     * Checks that the <code>int</code> argument is (strictly) greater than the
     * given number, throwing an exception otherwise. To create a
     * greater-than-or-equals check, you may either decrement the
     * <code>number</code> argument by one or use an inverted
     * {@link #isLessThan(int)} check:
     * <p>
     * <code>Check.that(number).not().isLessThan(1);</code>
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
    public IntCheck isGreaterThan(final int number) {
        return check(arg > number, ARG_GREATER, argName, number, arg);
    }
    
    /**
     * Checks that the <code>long</code> argument is (strictly) less than the
     * given number, throwing an exception otherwise. To create a
     * less-than-or-equals check, you may either increment the
     * <code>number</code> argument by one or use an inverted
     * {@link #isGreaterThan(int)} check:
     * <p>
     * <code>Check.that(number).not().isGreaterThan(1);</code>
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
    public IntCheck isLessThan(final int number) {
        return check(arg < number, ARG_LESS, argName, number, arg);
    }
    
    /**
     * Checks that the <code>int</code> argument is greater than or equal to
     * <code>min</code> and less than or equal to <code>max</code>, throwing an
     * exception otherwise.
     * <p>
     * To create a strict or one-sided-strict between test, you may either
     * increment or decrement the arguments of this check or use the
     * {@link #isGreaterThan(int)} and {@link #isLessThan(int)} checks, possibly
     * in conjunction with {@link #not()}.
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
    public IntCheck isBetween(final int min, final int max) {
        return check(arg >= min && arg <= max, ARG_BETWEEN, argName, min, max,
                arg);
    }
    
    /**
     * Checks that the <code>int</code> argument is a valid index in a
     * {@link Collection} or array with the given size or length, throwing an
     * exception otherwise. This means that the argument must be less than
     * <code>size</code> and not less than zero.
     * <p>
     * Two convenient methods exist: {@link #isValidIndex(Collection)},
     * {@link #isValidIndex(Object[])}.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_INDEX}.
     * 
     * @param size
     *            The size of the data structure the argument should be a valid
     *            index for
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not a valid index as defined above
     */
    public IntCheck isValidIndex(final int size) {
        return check(arg >= 0 && arg < size, ARG_INDEX, argName, size, arg);
    }
    
    /**
     * Checks that the <code>int</code> argument is a valid index for the given
     * {@link Collection}, throwing an exception otherwise. This means that the
     * argument must be less than <code>collection.size()</code> and not less
     * than zero.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_INDEX}.
     * 
     * @param collection
     *            The collection the argument should be a valid index for. Must
     *            not be <code>null</code>.
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not a valid index as defined above
     */
    public IntCheck isValidIndex(final Collection<?> collection) {
        return isValidIndex(collection.size());
    }
    
    /**
     * Checks that the <code>int</code> argument is a valid index for the given
     * array, throwing an exception otherwise. This means that the argument must
     * be less than <code>array.length</code> and not less than zero.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_INDEX}.
     * 
     * @param array
     *            The array the argument should be a valid index for. Must not
     *            be <code>null</code>.
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not a valid index as defined above
     */
    public IntCheck isValidIndex(final Object[] array) {
        return isValidIndex(array.length);
    }
}
