package ch.trick17.betterchecks;

import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import static ch.trick17.betterchecks.Exceptions.illegalStateException;

import java.util.Collection;

import ch.trick17.betterchecks.fluent.CollectionCheck;
import ch.trick17.betterchecks.fluent.DoubleCheck;
import ch.trick17.betterchecks.fluent.IntCheck;
import ch.trick17.betterchecks.fluent.LongCheck;
import ch.trick17.betterchecks.fluent.NumberCheck;
import ch.trick17.betterchecks.fluent.ObjectArrayCheck;
import ch.trick17.betterchecks.fluent.ObjectCheck;
import ch.trick17.betterchecks.fluent.PrimitiveArrayCheck;
import ch.trick17.betterchecks.fluent.StringCheck;
import ch.trick17.betterchecks.util.GwtCompatible;

/**
 * The primary entry point to the Better Checks library. Argument checks are
 * written like this:
 * <p>
 * <code>Check.that(<em>argument</em>).<em>check1</em>().<em>check2</em>()<em>...</em>;</code>
 * <p>
 * Examples:
 * <p>
 * <code>Check.that(greeting).matches("hello .*!").hasLenghtBetween(0, 20);</code>
 * <br>
 * <code>Check.that(list).isNullOr().hasSize(0);</code><br>
 * <code>Check.that(names).named("list of names").isNotEmpty();</code>
 * <p>
 * The check methods, such as <code>matches(...)</code> or
 * <code>hasSize(...)</code>, throw an exception if the check fails. The exact
 * type of exception depends on the kind of check that is called but in most
 * cases it is {@link IllegalArgumentException}.
 * <h3>Check Objects</h3>
 * <p>
 * You check the properties of your <code><em>argument</em></code> by calling
 * <code>Check.that(<em>argument</em>)</code> and then appending the required
 * checks. The <code>that(...)</code> method returns a check object that matches
 * the (static) type of <code><em>argument</em></code>. This is done with method
 * overloading. The returned check object has the <code><em>argument</em></code>
 * "imprinted" to perform the checks that are called subsequently.
 * <p>
 * For instance, if you pass a {@link String}, you will get a
 * {@link StringCheck} object, with methods like
 * {@link StringCheck#isNotEmpty()}, {@link StringCheck#hasLength(int)} or
 * {@link StringCheck#matches(String)}.
 * <p>
 * If there is no specific check class that matches the passed argument, a
 * generic {@link ObjectCheck} is returned, providing basic checks like
 * {@link ObjectCheck#isNotNull()} or {@link ObjectCheck#hasClass(Class)} and
 * the state-modifying methods (see below).
 * <h3>Check Modification</h3>
 * <p>
 * In addition to the checking methods, the check objects provide a few modifier
 * methods that affect subsequent checks. For example, all checks by default
 * also check that the argument is not <code>null</code>, throwing an exception
 * if it is. To allow <code>null</code> as an accepted value, you can prepend
 * your other checks with <code>isNullOr()</code>, like in the second example
 * above.
 * <p>
 * It is also possible to name the arguments that you are checking. Doing so
 * will make the exception messages more meaningful and debugging easier. To
 * name an argument, prepend all checks with
 * <code>named("<em>argument name</em>")</code>, just like in the third example
 * at the top.
 * <p>
 * Finally, each check can be inverted by prepending <code>.not()</code>. Here
 * is an example:
 * <p>
 * <code>Check.that(message).not().containsAny(badWords);</code>
 * <p>
 * Note that the <code>not()</code> only inverts the actual check and not the
 * null check. In the above example this would mean that message must still be
 * non-null. So null check inversion with <code>isNullOr()</code> is completely
 * independent of check inversion with <code>not()</code>.
 * <h3>Stack Trace Cleaning</h3>
 * <p>
 * To make the Better Checks library as unintrusive as possible, the stack
 * traces of the exceptions thrown by the checks are modified to not contain any
 * stack frames from this library. Thus, the exceptions look exactly like they
 * are being thrown by the method using the checks itself.
 * <p>
 * The idea behind this is to make it clear that the arguments (or the state
 * respectively) are illegal with respect to the client method and not with
 * respect to the methods of the Better Checks library.
 * <p>
 * If stack trace cleaning is undesired, it can be turned off in the config
 * file. See below for more information.
 * <h3>Compact and Simple Checks</h3>
 * <p>
 * Instead of the <code>Check.that(...)</code> syntax, you can use an even more
 * compact syntax, provided by the {@link CompactChecks} class. Also, in
 * addition to the fluent checks, there are also a few simple check methods,
 * like {@link #arguments(boolean, String)} and {@link #state(boolean, String)}.
 * <h3>Configuration and Use in Libraries</h3>
 * <p>
 * It is intentionally not possible to configure the type of exception the
 * checking methods throw. Because of this, the Better Checks library may also
 * safely be used in libraries (as opposed to applications), without the risk
 * that the application reconfigures the behavior of the library methods,
 * possibly breaking their specification.
 * <p>
 * The configuration possibilities of this library are rather limited. You can
 * customize the exception messages and disable stack trace cleaning. The only
 * way to configure those settings is via a properties file on the classpath.
 * This is also a design decision that makes it possible to safely use Better
 * Checks in libraries and, more generally, in all code that potentially runs
 * before the application's initialization, such as static initializers.
 * <p>
 * See the documentation of the {@link Config} class for more information.
 * 
 * @author Michael Faes
 * @see CompactChecks
 */
@GwtCompatible
public final class Check {
    
    private Check() {}
    
    /* Simple checks */
    
    /**
     * A simple argument check that throws an {@link IllegalArgumentException}
     * with the given message if the given condition is <code>false</code>.
     * <p>
     * Use this as a fallback option if you have to check something that the
     * fluent checks don't provide or if you have to check two arguments
     * together. Otherwise the fluent checks will probably be more readable and
     * concise as you don't have to provide the exception message.
     * 
     * @param condition
     *            The condition that must hold for the method arguments
     * @param message
     *            The exception message
     * @throws IllegalArgumentException
     *             if the condition is <code>false</code>
     * @see Check#state(boolean, String)
     */
    public static void args(final boolean condition, final String message) {
        if(!condition)
            throw illegalArgumentException(message);
    }
    
    /**
     * A simple argument check that throws an {@link IllegalArgumentException}
     * with the given message if the given condition is <code>false</code>.
     * <p>
     * Use this as a fallback option if you have to check something that the
     * fluent checks don't provide or if you have to check two arguments
     * together. Otherwise the fluent checks will probably be more readable and
     * concise as you don't have to provide the exception message.
     * 
     * @param condition
     *            The condition that must hold for the method arguments
     * @param message
     *            The exception message
     * @throws IllegalArgumentException
     *             if the condition is <code>false</code>
     * @see Check#state(boolean, String)
     * @deprecated Use the shorter {@link #args(boolean, String)} instead
     */
    @Deprecated
    public static void arguments(final boolean condition, final String message) {
        args(condition, message);
    }
    
    /**
     * A simple check that throws an {@link IllegalStateException} with the
     * given message if the given condition is <code>false</code>.
     * <p>
     * In contrast to most other checks, this check is for checking the state of
     * your object (instead of the method arguments) at the beginning of a
     * method invocation.
     * 
     * @param condition
     *            The state condition that must hold at the beginning of the
     *            method invocation
     * @param message
     *            The exception message
     * @throws IllegalStateException
     *             if the condition is <code>false</code>
     * @see #arguments(boolean, String)
     */
    public static void state(final boolean condition, final String message) {
        if(!condition)
            throw illegalStateException(message);
    }
    
    /* Fluent argument checks */
    
    /**
     * Returns a generic {@link ObjectCheck} which can be use to check basic
     * properties of all objects, e.g. {@link ObjectCheck#isNotNull()} or
     * {@link ObjectCheck#hasClass(Class)}.
     * 
     * @param argument
     *            The argument to check
     * @return A check object with the argument "imprinted"
     * @see ObjectCheck
     */
    public static ObjectCheck that(final Object argument) {
        return new ObjectCheck(argument);
    }
    
    /**
     * Returns a {@link StringCheck} which can be use to check various
     * properties of a {@link String}, e.g. {@link StringCheck#hasLength(int)}
     * or {@link StringCheck#contains(CharSequence)}.
     * 
     * @param argument
     *            The String argument to check
     * @return A check object with the argument "imprinted"
     * @see StringCheck
     */
    public static StringCheck that(final String argument) {
        return new StringCheck(argument);
    }
    
    /**
     * Returns a {@link ObjectArrayCheck} which can be use to check various
     * properties of an object array (<code>Object[]</code>), e.g.
     * {@link ObjectArrayCheck#hasLength(int)} or
     * {@link ObjectArrayCheck#containsNoNull()}.
     * <p>
     * Note that since arrays are covariant in Java, any type of array (e.g.
     * <code>String[]</code> or <code>Date[]</code>) is accepted by this method.
     * The only exception are arrays of primitive types (e.g. <code>int[]</code>
     * ), which have their own <code>that(...)</code> methods.
     * 
     * @param argument
     *            The object array argument to check
     * @return A check object with the argument "imprinted"
     * @see ObjectArrayCheck
     */
    public static ObjectArrayCheck that(final Object[] argument) {
        return new ObjectArrayCheck(argument);
    }
    
    /**
     * Returns a {@link PrimitiveArrayCheck} which can be use to check various
     * properties of a primitive array (e.g. <code>int[]</code>), e.g.
     * {@link PrimitiveArrayCheck#hasLength(int)} or
     * {@link PrimitiveArrayCheck#isNotEmpty()}.
     * 
     * @param argument
     *            The <code>boolean[]</code> array argument to check
     * @return A check object with the argument "imprinted"
     * @see PrimitiveArrayCheck
     */
    public static PrimitiveArrayCheck that(final boolean[] argument) {
        return new PrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    /**
     * Returns a {@link PrimitiveArrayCheck} which can be use to check various
     * properties of a primitive array (e.g. <code>int[]</code>), e.g.
     * {@link PrimitiveArrayCheck#hasLength(int)} or
     * {@link PrimitiveArrayCheck#isNotEmpty()}.
     * 
     * @param argument
     *            The <code>byte[]</code> array argument to check
     * @return A check object with the argument "imprinted"
     * @see PrimitiveArrayCheck
     */
    public static PrimitiveArrayCheck that(final byte[] argument) {
        return new PrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    /**
     * Returns a {@link PrimitiveArrayCheck} which can be use to check various
     * properties of a primitive array (e.g. <code>int[]</code>), e.g.
     * {@link PrimitiveArrayCheck#hasLength(int)} or
     * {@link PrimitiveArrayCheck#isNotEmpty()}.
     * 
     * @param argument
     *            The <code>char[]</code> array argument to check
     * @return A check object with the argument "imprinted"
     * @see PrimitiveArrayCheck
     */
    public static PrimitiveArrayCheck that(final char[] argument) {
        return new PrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    /**
     * Returns a {@link PrimitiveArrayCheck} which can be use to check various
     * properties of a primitive array (e.g. <code>int[]</code>), e.g.
     * {@link PrimitiveArrayCheck#hasLength(int)} or
     * {@link PrimitiveArrayCheck#isNotEmpty()}.
     * 
     * @param argument
     *            The <code>double[]</code> array argument to check
     * @return A check object with the argument "imprinted"
     * @see PrimitiveArrayCheck
     */
    public static PrimitiveArrayCheck that(final double[] argument) {
        return new PrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    /**
     * Returns a {@link PrimitiveArrayCheck} which can be use to check various
     * properties of a primitive array (e.g. <code>int[]</code>), e.g.
     * {@link PrimitiveArrayCheck#hasLength(int)} or
     * {@link PrimitiveArrayCheck#isNotEmpty()}.
     * 
     * @param argument
     *            The <code>float[]</code> array argument to check
     * @return A check object with the argument "imprinted"
     * @see PrimitiveArrayCheck
     */
    public static PrimitiveArrayCheck that(final float[] argument) {
        return new PrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    /**
     * Returns a {@link PrimitiveArrayCheck} which can be use to check various
     * properties of a primitive array (e.g. <code>int[]</code>), e.g.
     * {@link PrimitiveArrayCheck#hasLength(int)} or
     * {@link PrimitiveArrayCheck#isNotEmpty()}.
     * 
     * @param argument
     *            The <code>int[]</code> array argument to check
     * @return A check object with the argument "imprinted"
     * @see PrimitiveArrayCheck
     */
    public static PrimitiveArrayCheck that(final int[] argument) {
        return new PrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    /**
     * Returns a {@link PrimitiveArrayCheck} which can be use to check various
     * properties of a primitive array (e.g. <code>int[]</code>), e.g.
     * {@link PrimitiveArrayCheck#hasLength(int)} or
     * {@link PrimitiveArrayCheck#isNotEmpty()}.
     * 
     * @param argument
     *            The <code>long[]</code> array argument to check
     * @return A check object with the argument "imprinted"
     * @see PrimitiveArrayCheck
     */
    public static PrimitiveArrayCheck that(final long[] argument) {
        return new PrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    /**
     * Returns a {@link PrimitiveArrayCheck} which can be use to check various
     * properties of a primitive array (e.g. <code>int[]</code>), e.g.
     * {@link PrimitiveArrayCheck#hasLength(int)} or
     * {@link PrimitiveArrayCheck#isNotEmpty()}.
     * 
     * @param argument
     *            The <code>short[]</code> array argument to check
     * @return A check object with the argument "imprinted"
     * @see PrimitiveArrayCheck
     */
    public static PrimitiveArrayCheck that(final short[] argument) {
        return new PrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    /**
     * Returns a {@link CollectionCheck} which can be use to check various
     * properties of a {@link Collection}, e.g.
     * {@link CollectionCheck#hasSize(int)} or
     * {@link CollectionCheck#containsNoNull()}.
     * 
     * @param argument
     *            The Collection argument to check
     * @return A check object with the argument "imprinted"
     * @see CollectionCheck
     */
    public static CollectionCheck that(final Collection<?> argument) {
        return new CollectionCheck(argument);
    }
    
    // IMPROVE: Create MapCheck
    
    /**
     * Returns a {@link NumberCheck} which can be use to check various
     * properties of a {@link Number}, e.g. {@link NumberCheck#isPositive()} or
     * {@link NumberCheck#isBetween(Number, Number)}.
     * <p>
     * Note that this method is (should be) only used for numbers other than the
     * ones corresponding to the primitive type numbers (
     * <code>byte, short, int, long, float, double</code>). Those types have
     * their own <code>that(...)</code> method and check classes with specific
     * features. Also, they do not require any boxing or unboxing.
     * 
     * @param argument
     *            The Number argument to check
     * @return A check object with the argument "imprinted"
     * @see NumberCheck
     */
    public static NumberCheck that(final Number argument) {
        return new NumberCheck(argument);
    }
    
    /**
     * Returns an {@link IntCheck} which can be use to check various properties
     * of an <code>int</code>, e.g. {@link IntCheck#isPositive()},
     * {@link IntCheck#isBetween(int, int)} or
     * {@link IntCheck#isValidIndex(Object[])}.
     * <p>
     * This method is (should be) also used for <code>short</code> and
     * <code>byte</code> arguments as there is no (need for a) separate check
     * class for them.
     * 
     * @param argument
     *            The <code>int</code> argument to check
     * @return A check object with the argument "imprinted"
     * @see IntCheck
     */
    public static IntCheck that(final int argument) {
        return new IntCheck(argument);
    }
    
    /**
     * Returns a {@link LongCheck} which can be use to check various properties
     * of a <code>long</code>, e.g. {@link LongCheck#isPositive()} or
     * {@link LongCheck#isBetween(long, long)}.
     * 
     * @param argument
     *            The <code>long</code> argument to check
     * @return A check object with the argument "imprinted"
     * @see LongCheck
     */
    public static LongCheck that(final long argument) {
        return new LongCheck(argument);
    }
    
    /**
     * Returns a {@link DoubleCheck} which can be use to check various
     * properties of an <code>double</code>, e.g.
     * {@link DoubleCheck#isPositive()},
     * {@link DoubleCheck#isBetween(double, double)} or
     * {@link DoubleCheck#isNotNaN()}.
     * <p>
     * This method is (should be) also used for <code>float</code> arguments as
     * there is no (need for a) separate check class for them.
     * 
     * @param argument
     *            The <code>double</code> or <code>float</code> argument to
     *            check
     * @return A check object with the argument "imprinted"
     * @see DoubleCheck
     */
    public static DoubleCheck that(final double argument) {
        return new DoubleCheck(argument);
    }
}
