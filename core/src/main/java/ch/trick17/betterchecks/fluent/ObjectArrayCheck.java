package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.util.Arrays;

import ch.trick17.betterchecks.MessageType;

/**
 * The check class for object arrays. It supports all non-primitive arrays, not
 * just <code>Object[]</code>.
 * <p>
 * Most check methods are related to the length of the array, like
 * {@link #hasLength(int)} or {@link #hasLengthBetween(int, int)}, but there is
 * also the {@link #containsNoNull()} method.
 * 
 * @author Michael Faes
 */
public final class ObjectArrayCheck extends
        ObjectBaseCheck<Object[], ObjectArrayCheck> {
    
    /**
     * Default constructor, for internal usage only.
     */
    public ObjectArrayCheck() {}
    
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
     * @see #containsNoNull()
     */
    public ObjectArrayCheck isNotEmpty() {
        return check(arg == null || arg.length != 0, ARG_EMPTY, argName, Arrays
                .toString(arg));
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
    public ObjectArrayCheck hasLength(final int length) {
        return check(arg == null || arg.length == length, ARG_LENGTH, argName,
                length, Arrays.toString(arg));
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
     *             If the length of the array is <em>strictly</em> less than
     *             <code>min</code> or <em>strictly</em> greater than
     *             <code>max</code>.
     */
    public ObjectArrayCheck hasLengthBetween(final int min, final int max) {
        return check(arg == null || (arg.length >= min && arg.length <= max),
                ARG_LENGTH_BETWEEN, argName, min, max, Arrays.toString(arg));
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
        return intPropertyCheck(arg == null ? -1 : arg.length, "length");
    }
    
    /**
     * Checks that the array argument does not contain any <code>null</code>
     * element, throwing an exception otherwise. An empty array is considered
     * valid by this check.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_CONTAINS_NULL}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             If the array contains one or more <code>null</code> elements
     */
    public ObjectArrayCheck containsNoNull() {
        return check(arg == null || testContainsNoNull(), ARG_CONTAINS_NULL,
                argName, Arrays.toString(arg));
    }
    
    // IMPROVE: allElementsOfType
    
    /*
     * Implementation methods
     */
    
    private boolean testContainsNoNull() {
        for(final Object element : arg) {
            if(element == null)
                return false;
        }
        return true;
    }
}
