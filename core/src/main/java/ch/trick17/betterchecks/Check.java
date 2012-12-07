package ch.trick17.betterchecks;

import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import static ch.trick17.betterchecks.Exceptions.illegalStateException;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import ch.trick17.betterchecks.fluent.CollectionCheck;
import ch.trick17.betterchecks.fluent.DoubleCheck;
import ch.trick17.betterchecks.fluent.FluentChecks;
import ch.trick17.betterchecks.fluent.IntCheck;
import ch.trick17.betterchecks.fluent.LongCheck;
import ch.trick17.betterchecks.fluent.NumberCheck;
import ch.trick17.betterchecks.fluent.ObjectArrayCheck;
import ch.trick17.betterchecks.fluent.ObjectCheck;
import ch.trick17.betterchecks.fluent.PrimitiveArrayCheck;
import ch.trick17.betterchecks.fluent.StringCheck;
import ch.trick17.betterchecks.fluent.UrlCheck;

/**
 * This is the primary entry point to the Better Checks library. Its intention
 * is to provide a lightweight and concise, but powerful way for precodition
 * checking, in particular for method arguments. Argument checks are written in
 * a fluent way like this:
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
 * The check methods (such as <code>matches(...)</code> or
 * <code>hasSize(...)</code>) throw an exception if the check fails. The exact
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
 * generic {@link ObjectCheck} is returned, supporting only some basic checks
 * like {@link ObjectCheck#isNotNull()} or
 * {@link ObjectCheck#isInstanceOf(Class)} and the state-modifying methods (see
 * below).
 * <h3>Check Modification</h3>
 * <p>
 * In addition to the checking methods, the check objects provide some modifier
 * methods that affect the subsequent checks. For example all checks by default
 * also check that the argument is not <code>null</code>, throwing an exception
 * if it is. To allow <code>null</code> as an accepted value, you can prepend
 * the actual checks with <code>isNullOr()</code>, like in the second example
 * above.
 * <p>
 * It is also possible to name the arguments that you are checking. By doing
 * this, the exception messages will be more meaningful and debugging becomes
 * easier. To name an argument, prepend all checks with
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
 * <h3>Intended Use and Thread Safety</h3>
 * <p>
 * To provide optimal performance, the <code>that(...)</code> methods do not
 * create a new check object for every call. Instead, each overloaded method
 * always returns the same (but modified) object (in a given thread). Therefore,
 * you should always use those objects right after getting them by using the
 * fluent API. Never should you store a check object and use it later, not even
 * in local variables, as <em>any</em> method that is called in between may also
 * use and therefore modify it.
 * <p>
 * Thread safety is guaranteed by means of thread confinement. As each thread
 * receives its own check objects, and as long as they are not shared, the use
 * of those objects is thread safe.
 * <h3>Stack Trace Cleaning</h3>
 * <p>
 * To make the Better Checks library as unintrusive as possible, the stack
 * traces of the exceptions thrown by the checks are modified to not contain any
 * stack frames from this library. Thus, the exceptions look exactly like they
 * are being thrown by the method using the checks itself.
 * <p>
 * The idea behind this is to make it clear that the arguments (or the state
 * respectively) are illegal with respect to the client method and not in
 * respect to the methods of the Better Checks library.
 * <p>
 * If stack trace cleaning is undesired, it can be turned off in the config
 * file. See below for more information.
 * <h3>Compact Syntax</h3>
 * <p>
 * Instead of the <code>Check.that(...)</code> syntax, you can use a even more
 * compact syntax, provided by the {@link CompactChecks} class.
 * <h3>Configuration and Use in Libraries</h3>
 * <p>
 * It is intentionally not possible to configure the type of exception the
 * checking methods throw. Because of this, the Better Checks library may also
 * safely be used in libraries (as opposed to applications), without the risk
 * that the application reconfigures the behavior of the library methods,
 * possibly breaking their specification.
 * <p>
 * In general, the configuration possibilities of this library are rather
 * limited. You can customize the exception messages and disable stack trace
 * cleaning. The only way to configure those settings is via a properties file
 * on the classpath. This is also a design decision that makes it possible to
 * safely use Better Checks in libraries and, more generally, in all code that
 * potentially runs before the application's initialization, such as static
 * initializers.
 * <p>
 * The config file must be named <code>better-checks-config.properties</code>
 * and must be located in the classpath root. The config file is loaded via
 * {@link ResourceBundle}s, so localization is supported. However, it is
 * questionable to display exception messages directly to the user, so this
 * might not be commonly used.
 * <p>
 * TODO: Config file options
 * 
 * @author Michael Faes
 * @see CompactChecks
 */
public abstract class Check {
    
    /*
     * Simple checks
     */
    
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
    public static void arguments(final boolean condition, final String message) {
        if(!condition)
            throw illegalArgumentException(message);
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
    
    /*
     * Fluent argument checks
     */
    
    /**
     * Returns a generic {@link ObjectCheck} which can be use to check basic
     * properties of all objects, e.g. {@link ObjectCheck#isNotNull()} or
     * {@link ObjectCheck#isInstanceOf(Class)}.
     * 
     * @param argument
     *            The argument to check
     * @return A check object with the argument "imprinted"
     * @see ObjectCheck
     */
    public static ObjectCheck that(final Object argument) {
        return FluentChecks.getObjectCheck(ObjectCheck.class, argument);
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
        return FluentChecks.getObjectCheck(StringCheck.class, argument);
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
        return FluentChecks.getObjectCheck(ObjectArrayCheck.class, argument);
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
        return FluentChecks.getPrimitiveArrayCheck(argument,
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
        return FluentChecks.getPrimitiveArrayCheck(argument,
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
        return FluentChecks.getPrimitiveArrayCheck(argument,
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
        return FluentChecks.getPrimitiveArrayCheck(argument,
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
        return FluentChecks.getPrimitiveArrayCheck(argument,
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
        return FluentChecks.getPrimitiveArrayCheck(argument,
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
        return FluentChecks.getPrimitiveArrayCheck(argument,
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
        return FluentChecks.getPrimitiveArrayCheck(argument,
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
        return FluentChecks.<Collection<?>, CollectionCheck> getObjectCheck(
                CollectionCheck.class, argument);
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
        return FluentChecks.getObjectCheck(NumberCheck.class, argument);
    }
    
    /**
     * Returns an {@link UrlCheck} which can be use to check various properties
     * of an {@link URL}, e.g. {@link UrlCheck#hasProtocol(String)}.
     * <p>
     * Note that the typical use case is to obtain a UrlCheck via the
     * {@link StringCheck#isUrlWhich()} method which "converts" a
     * {@link StringCheck} to an UrlCheck.
     * 
     * @param argument
     *            The URL argument to check
     * @return A check object with the argument "imprinted"
     * @see UrlCheck
     */
    public static UrlCheck that(final URL argument) {
        return FluentChecks.getObjectCheck(UrlCheck.class, argument);
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
        return FluentChecks.getIntCheck(argument);
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
        return FluentChecks.getLongCheck(argument);
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
        return FluentChecks.getDoubleCheck(argument);
    }
}
