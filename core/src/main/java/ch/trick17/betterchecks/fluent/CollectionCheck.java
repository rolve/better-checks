package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.util.Collection;

import ch.trick17.betterchecks.MessageType;

/**
 * The check class for all {@link Collection} types. It provides a functionality
 * similar to the {@link ObjectArrayCheck} class.
 * <p>
 * Most check methods are related to the size of the collection, like
 * {@link #hasSize(int)} or {@link #hasSizeBetween(int, int)}, but there is also
 * the {@link #containsNoNull()} method.
 * 
 * @author Michael Faes
 */
public final class CollectionCheck extends
        ObjectBaseCheck<Collection<?>, CollectionCheck> {
    
    /**
     * Default constructor, for internal use only.
     */
    public CollectionCheck() {}
    
    /**
     * Checks that the collection argument is not empty (meaning it has a size
     * greater than zero), throwing an exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_EMPTY}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if the collection argument is empty
     * @see #containsNoNull()
     */
    public CollectionCheck isNotEmpty() {
        return check(arg == null || !arg.isEmpty(), ARG_EMPTY, argName, arg);
    }
    
    /**
     * Checks that the collection argument has the given size, throwing an
     * exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_SIZE}.
     * <p>
     * To create more sophisticated checks using the collection size, use the
     * {@link #hasSizeWhich()} property check.
     * 
     * @param size
     *            The size the collection should have
     * @return This check
     * @throws IllegalArgumentException
     *             if the collection argument has a size different from the
     *             given one
     * @see #hasSizeBetween(int, int)
     */
    public CollectionCheck hasSize(final int size) {
        return check(arg == null || arg.size() == size, ARG_SIZE, argName,
                size, arg);
    }
    
    /**
     * Checks that the collection argument's size is between the two given
     * numbers, throwing an exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_SIZE_BETWEEN}.
     * <p>
     * To create more sophisticated checks using the collection size, use the
     * {@link #hasSizeWhich()} property check.
     * 
     * @param min
     *            The minimum size of the collection
     * @param max
     *            The maximum size
     * @return This check
     * @throws IllegalArgumentException
     *             If the size of the collection is <em>strictly</em> less than
     *             <code>min</code> or <em>strictly</em> greater than
     *             <code>max</code>
     */
    public CollectionCheck hasSizeBetween(final int min, final int max) {
        return check(arg == null || (arg.size() >= min && arg.size() <= max),
                ARG_SIZE_BETWEEN, argName, min, max, arg);
    }
    
    /**
     * Creates an {@link IntCheck} property check for the size of the collection
     * argument. It can be used to create advanced checks using the collection
     * size. For example:
     * <p>
     * <code>Check.that(list).isNullOr().hasSizeWhich().isGreaterThan(3);</code>
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the size of this check's collection set as the
     * argument and an argument name that indicates this fact. If the collection
     * is <code>null</code> and allowed to be so (like in the above example),
     * the returned property check is completely disabled, meaning none of its
     * check methods will throw an exception. An inversion called before this
     * method is ignored and does <strong>not</strong> have any effect on the
     * check methods called on the returned property check.
     * 
     * @return A property check for the size of the collection argument
     */
    public IntCheck hasSizeWhich() {
        return intPropertyCheck(arg == null ? -1 : arg.size(), "size");
    }
    
    /**
     * Checks that the collection argument does not contain any
     * <code>null</code> element, throwing an exception otherwise. An empty
     * collection is considered valid by this check.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_CONTAINS_NULL}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             If the collection contains one or more <code>null</code>
     *             elements
     */
    public CollectionCheck containsNoNull() {
        return check(arg == null || testContainsNoNull(), ARG_CONTAINS_NULL,
                argName, arg);
    }
    
    // IMPROVE: allElementsOfType
    
    /* Implementation methods */
    
    private boolean testContainsNoNull() {
        for(final Object element : arg) {
            if(element == null)
                return false;
        }
        return true;
    }
}
