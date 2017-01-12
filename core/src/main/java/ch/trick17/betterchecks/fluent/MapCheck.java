package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.util.Map;

import ch.trick17.betterchecks.MessageType;
import ch.trick17.betterchecks.util.GwtCompatible;

/**
 * The check class for all {@link Map} types.
 * <p>
 * Most check methods are related to the size of the map, like {@link #hasSize(int)} or
 * {@link #hasSizeBetween(int, int)}, but there is also the {@link #containsNoNullKey()} method.
 * 
 * @author Michael Faes
 */
@GwtCompatible
public final class MapCheck extends ObjectBaseCheck<Map<?, ?>, MapCheck> {
    
    /**
     * For internal use only.
     */
    public MapCheck(Map<?, ?> arg) {
        super(arg);
    }
    
    /**
     * Checks that the map argument is not empty (meaning it has a size greater than zero), throwing
     * an exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is {@link MessageType#ARG_EMPTY}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if the map argument is empty
     */
    public MapCheck isNotEmpty() {
        return check(arg == null || !arg.isEmpty(), ARG_EMPTY, argName, arg);
    }
    
    /**
     * Checks that the map argument has the given size, throwing an exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is {@link MessageType#ARG_SIZE}.
     * <p>
     * To create more sophisticated checks using the map size, use the {@link #hasSizeWhich()}
     * property check.
     * 
     * @param size
     *            The size the map should have
     * @return This check
     * @throws IllegalArgumentException
     *             if the map argument has a size different from the given one
     * @see #hasSizeBetween(int, int)
     */
    public MapCheck hasSize(final int size) {
        return check(arg == null || arg.size() == size, ARG_SIZE, argName,
                size, arg);
    }
    
    /**
     * Checks that the map argument's size is between the two given numbers, throwing an exception
     * otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_SIZE_BETWEEN}.
     * <p>
     * To create more sophisticated checks using the map size, use the {@link #hasSizeWhich()}
     * property check.
     * 
     * @param min
     *            The minimum size of the map
     * @param max
     *            The maximum size
     * @return This check
     * @throws IllegalArgumentException
     *             If the size of the map is <em>strictly</em> less than <code>min</code> or
     *             <em>strictly</em> greater than <code>max</code>
     */
    public MapCheck hasSizeBetween(final int min, final int max) {
        return check(arg == null || (arg.size() >= min && arg.size() <= max),
                ARG_SIZE_BETWEEN, argName, min, max, arg);
    }
    
    /**
     * Creates an {@link IntCheck} property check for the size of the map argument. It can be used
     * to create advanced checks using the map size. For example:
     * <p>
     * <code>Check.that(map).isNullOr().hasSizeWhich().isGreaterThan(3);</code>
     * <p>
     * Just like a check method, this method first asserts the non-nullness of the argument,
     * throwing an exception in case of a failure.
     * <p>
     * The returned check has the size of this check's map set as the argument and an argument name
     * that indicates this fact. If the map is <code>null</code> and allowed to be so (like in the
     * above example), the returned property check is completely disabled, meaning none of its check
     * methods will throw an exception. An inversion called before this method is ignored and does
     * <strong>not</strong> have any effect on the check methods called on the returned property
     * check.
     * 
     * @return A property check for the size of the map argument
     */
    public IntCheck hasSizeWhich() {
        return intPropertyCheck(arg == null ? -1 : arg.size(), "size");
    }
    
    /**
     * Checks that the map argument does not contain the <code>null</code> key, throwing an
     * exception otherwise. An empty map is considered valid by this check.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_CONTAINS_NULL_KEY}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             If the map contains the <code>null</code> key
     */
    public MapCheck containsNoNullKey() {
        return check(arg == null || !arg.keySet().contains(null), ARG_CONTAINS_NULL_KEY, argName,
                arg);
    }
    
    /**
     * Checks that the map argument does not contain any <code>null</code> values, throwing an
     * exception otherwise. An empty map is considered valid by this check.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_CONTAINS_NULL_VALUES}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             If the map contains one or more <code>null</code> values
     */
    public MapCheck containsNoNullValues() {
        return check(arg == null || !arg.values().contains(null), ARG_CONTAINS_NULL_VALUES, argName,
                arg);
    }
    
    /**
     * Checks that the map argument does not contain any <code>null</code> elements, i.e., key or
     * values, throwing an exception otherwise. An empty map is considered valid by this check.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_CONTAINS_NULL}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             If the map contains one or more <code>null</code> elements
     */
    public MapCheck containsNoNull() {
        return check(arg == null || (!arg.keySet().contains(null) && !arg.values().contains(null)),
                ARG_CONTAINS_NULL, argName, arg);
    }
    
    // IMPROVE: allKeysOfType, allValuesOfType
}
