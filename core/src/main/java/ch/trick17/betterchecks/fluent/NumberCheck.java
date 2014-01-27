package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.MessageType;

/**
 * The check class for {@link Number} arguments. It should be (and <em>is</em> -
 * if you use the {@link Check} methods correctly) used only for number
 * arguments of types that do not correspond to one of the primitive types (
 * <code>byte, short, int, long, float, double</code>). These types have their
 * own check classes ({@link IntCheck} for example) with additional checks.
 * <p>
 * This class provides check methods like {@link #isPositive()},
 * {@link #isGreaterThan(Number)} or {@link #isBetween(Number, Number)}.
 * <p>
 * <strong>Attention:</strong> As there is no way to compare two arbitrary
 * Number instances in general, there is no guarantee that the implementations
 * of this class' check methods handle instances of custom Number subclasses
 * correctly. However, you can expect them to handle all Number classes provided
 * by the JDK (including {@link BigInteger} and {@link BigDecimal}) correctly.
 * For custom subclasses, read the following implementation notes.
 * <p>
 * Checks involving a comparison between two arbitrary Number instances first
 * convert both numbers to {@link BigDecimal} and then compare the two objects
 * using {@link BigDecimal#compareTo(BigDecimal) compareTo}. The conversion has
 * special cases for most of the JDK classes and a default implementation
 * covering the rest. This implementation uses the number's
 * {@link Number#toString() toString} method to construct a BigDecimal. So to
 * make sure your custom subclasses are handled correctly, ensure that their
 * String representation can be parsed by the
 * {@link BigDecimal#BigDecimal(String) BigDecimal(String)} constructor.
 * <p>
 * Checks that do not involve two arbitrary Numbers, e.g. {@link #isPositive()}
 * have different implementations. Read their documentations' implementation
 * notes for more information.
 * 
 * @author Michael Faes
 */
public final class NumberCheck extends ObjectBaseCheck<Number, NumberCheck> {
    
    /**
     * Default constructor, for internal use only.
     */
    public NumberCheck() {}
    
    /**
     * Checks that the argument is positive, throwing an exception otherwise.
     * Note that zero is not a valid number. Often, you want to check that a
     * number is non-negative. Use the inverted {@link #isNegative()} check for
     * that:
     * <p>
     * <code>Check.that(number).not().isNegative();</code>
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_POSITIVE}.
     * <p>
     * <strong>Implementation note:</strong> This check uses the
     * {@link BigDecimal#signum() signum} method for instances of
     * {@link BigDecimal} and {@link Number#doubleValue() doubleValue} for all
     * other objects.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not positive
     * @see #isNegative()
     */
    public NumberCheck isPositive() {
        checkNull();
        if(arg instanceof BigDecimal)
            return check(((BigDecimal) arg).signum() == 1, ARG_POSITIVE,
                    argName, arg);
        else
            return check(arg.doubleValue() > 0, ARG_POSITIVE, argName, arg);
    }
    
    /**
     * Checks that the argument is negative, throwing an exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_NEGATIVE}.
     * <p>
     * <strong>Implementation note:</strong> This check uses the
     * {@link BigDecimal#signum() signum} method for instances of
     * {@link BigDecimal} and {@link Number#doubleValue() doubleValue} for all
     * other objects.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not negative
     * @see #isPositive()
     */
    public NumberCheck isNegative() {
        checkNull();
        if(arg instanceof BigDecimal)
            return check(((BigDecimal) arg).signum() == -1, ARG_NEGATIVE,
                    argName, arg);
        else
            return check(arg.doubleValue() < 0, ARG_NEGATIVE, argName, arg);
    }
    
    /**
     * Checks that the argument is equal (equivalent) to the given
     * {@link Number}, throwing an exception otherwise. Note that the definition
     * of equality is different from the one that is usually associated with
     * {@link Number#equals(Object) equals}. This check may also consider two
     * Numbers to be equal if they have different classes. For example, while
     * <p>
     * <code>new Double(0).equals(new Integer(0))</code>
     * <p>
     * is <code>false</code>, this method considers the two numbers to be equal.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_IS}.
     * <p>
     * <strong>Implementation note:</strong> This check uses the conversion
     * described in the {@link NumberCheck} documentation. Read this before
     * using this check with custom Number subclasses.
     * 
     * @param number
     *            The number this argument must be equal to
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not equal to the given number
     */
    public NumberCheck is(final Number number) {
        return check(arg == null || compare(arg, number) == 0, ARG_IS, argName,
                number, arg);
    }
    
    /**
     * Checks that the argument is (strictly) greater than the given
     * {@link Number}, throwing an exception otherwise. To create a
     * greater-than-or-equals check, you may use an inverted
     * {@link #isLessThan(Number)} check:
     * <p>
     * <code>Check.that(number).not().isLessThan(1.0);</code>
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_GREATER}.
     * <p>
     * <strong>Implementation note:</strong> This check uses the conversion
     * described in the {@link NumberCheck} documentation. Read this before
     * using this check with custom Number subclasses.
     * 
     * @param number
     *            The number this argument must be greater than
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not greater than the given number
     */
    public NumberCheck isGreaterThan(final Number number) {
        return check(arg == null || compare(arg, number) > 0, ARG_GREATER,
                argName, number, arg);
    }
    
    /**
     * Checks that the argument is (strictly) less than the given {@link Number}
     * , throwing an exception otherwise. To create a less-than-or-equals check,
     * you may use an inverted {@link #isGreaterThan(Number)} check:
     * <p>
     * <code>Check.that(number).not().isGreaterThan(1.0);</code>
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_LESS}.
     * <p>
     * <strong>Implementation note:</strong> This check uses the conversion
     * described in the {@link NumberCheck} documentation. Read this before
     * using this check with custom Number subclasses.
     * 
     * @param number
     *            The number this argument must be less than
     * @return This check
     * @throws IllegalArgumentException
     *             if argument is not less than the given number
     */
    public NumberCheck isLessThan(final Number number) {
        return check(arg == null || compare(arg, number) < 0, ARG_LESS,
                argName, number, arg);
    }
    
    /**
     * Checks that the {@link Number} argument is greater than or equal to
     * <code>min</code> and less than or equal to <code>max</code>, throwing an
     * exception otherwise.
     * <p>
     * To create a strict or one-sided-strict between test, you may use the
     * {@link #isGreaterThan(Number)} and {@link #isLessThan(Number)} checks,
     * possibly in conjunction with {@link #not()}.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_BETWEEN}.
     * <p>
     * <strong>Implementation note:</strong> This check uses the conversion
     * described in the {@link NumberCheck} documentation. Read this before
     * using this check with custom Number subclasses.
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
    public NumberCheck isBetween(final Number min, final Number max) {
        return check(arg == null
                || (compare(arg, min) >= 0 && compare(arg, max) <= 0),
                ARG_BETWEEN, argName, min, max, arg);
    }
    
    /* Implementation methods */
    
    private static int compare(final Number x, final Number y) {
        return toBigDecimal(x).compareTo(toBigDecimal(y));
    }
    
    private static BigDecimal toBigDecimal(final Number number) {
        if(number instanceof BigDecimal)
            return (BigDecimal) number;
        if(number instanceof BigInteger)
            return new BigDecimal((BigInteger) number);
        if(number instanceof Byte || number instanceof Short
                || number instanceof Integer || number instanceof Long)
            return new BigDecimal(number.longValue());
        if(number instanceof Float || number instanceof Double)
            return new BigDecimal(number.doubleValue());
        
        try {
            return new BigDecimal(number.toString());
        } catch(final NumberFormatException e) {
            throw new RuntimeException("The given number (\"" + number
                    + "\" of class " + number.getClass().getName()
                    + ") does not have a parsable string representation", e);
        }
    }
}
