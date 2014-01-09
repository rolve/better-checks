package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.util.Arrays;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.CompactChecks;
import ch.trick17.betterchecks.MessageType;

/**
 * The check class for all types of primitive arrays. There is no separate class
 * for each primitive type of array. In addition to the array argument itself, a
 * check of this class stores the length of the array, which is the only thing
 * needed for all the supported checks.
 * 
 * @author Michael Faes
 */
public final class PrimitiveArrayCheck extends
        ObjectBaseCheck<Object, PrimitiveArrayCheck> {
    
    private int argLength;
    
    /**
     * Default constructor, for internal use only.
     */
    public PrimitiveArrayCheck() {}
    
    /**
     * Resets all state of this check. This method (instead of the
     * {@link #reset(Object)} method!) must be called every time before this
     * check object is returned by one of the {@link Check} or
     * {@link CompactChecks} methods.
     * 
     * @param argument
     *            The array argument to be checked
     * @param argumentLength
     *            The length of the array. If the array is <code>null</code>,
     *            the value may be arbitrary.
     */
    protected void reset(final Object argument, final int argumentLength) {
        reset(argument);
        argLength = argumentLength;
    }
    
    /* Checks */
    
    /**
     * Checks that the array argument is not empty (meaning it has a length
     * greater than zero), throwing an exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_EMPTY}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if the array argument is empty
     */
    public PrimitiveArrayCheck isNotEmpty() {
        return check(arg == null || argLength != 0, ARG_EMPTY, argName,
                arrayToString(arg));
    }
    
    /**
     * Checks that the array argument has the given length, throwing an
     * exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_LENGTH}.
     * <p>
     * To create more sophisticated checks using the array length, use the
     * {@link #hasLengthWhich()} property check.
     * 
     * @param length
     *            The length the array should have
     * @return This check
     * @throws IllegalArgumentException
     *             if the array argument has a length different from the given
     *             one.
     * @see #hasLengthBetween(int, int)
     */
    public PrimitiveArrayCheck hasLength(final int length) {
        return check(arg == null || argLength == length, ARG_LENGTH, argName,
                length, arrayToString(arg));
    }
    
    /**
     * Checks that the array argument's length is between the two given numbers,
     * throwing an exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_LENGTH_BETWEEN}.
     * <p>
     * To create more sophisticated checks using the array length, use the
     * {@link #hasLengthWhich()} property check.
     * 
     * @param min
     *            The minimum length of the array
     * @param max
     *            The maximum length
     * @return This check
     * @throws IllegalArgumentException
     *             If the length of the array is strictly less than
     *             <code>min</code> or strictly greater than <code>max</code>.
     */
    public PrimitiveArrayCheck hasLengthBetween(final int min, final int max) {
        return check(arg == null || (argLength >= min && argLength <= max),
                ARG_LENGTH_BETWEEN, argName, min, max, arrayToString(arg));
    }
    
    /**
     * Creates an {@link IntCheck} property check for the length of the array
     * argument. It can be used to create advanced checks using the array
     * length. For example:
     * <p>
     * <code>Check.that(array).isNullOr().hasLengthWhich().isGreaterThan(3);</code>
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the length of this check's array set as the
     * argument and an argument name that indicates this fact. If the array is
     * <code>null</code> and allowed to be so (like in the above example), the
     * returned property check is completely disabled, meaning none of its check
     * methods will throw an exception. An inversion called before this method
     * is ignored and does <strong>not</strong> have any effect on the check
     * methods called on the returned property check.
     * 
     * @return A property check for the length of the array argument
     */
    public IntCheck hasLengthWhich() {
        return intPropertyCheck(arg == null ? -1 : argLength, "length");
    }
    
    /* Implementation methods */
    
    private static String arrayToString(final Object arg) {
        if(arg == null)
            return String.valueOf((Object) null);
        if(arg instanceof boolean[])
            return Arrays.toString((boolean[]) arg);
        if(arg instanceof byte[])
            return Arrays.toString((byte[]) arg);
        if(arg instanceof char[])
            return Arrays.toString((char[]) arg);
        if(arg instanceof double[])
            return Arrays.toString((double[]) arg);
        if(arg instanceof float[])
            return Arrays.toString((float[]) arg);
        if(arg instanceof int[])
            return Arrays.toString((int[]) arg);
        if(arg instanceof long[])
            return Arrays.toString((long[]) arg);
        if(arg instanceof short[])
            return Arrays.toString((short[]) arg);
        
        throw new RuntimeException("argument is not an primitive array: " + arg
                + " of class " + arg.getClass().getName());
    }
}
