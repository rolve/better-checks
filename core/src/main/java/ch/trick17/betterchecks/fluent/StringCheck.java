package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.net.URL;
import java.util.Arrays;
import java.util.regex.Pattern;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.InvalidCheckException;
import ch.trick17.betterchecks.MessageType;
import ch.trick17.betterchecks.util.GwtCompatible;
import ch.trick17.betterchecks.util.GwtIncompatible;

/**
 * The check class for {@link String} arguments.
 * <p>
 * While String is a very generic data type, string arguments are often used to
 * provide a specific kind of data, for example names, URLs or even numbers.
 * Therefore, Strings have to be validated. This class provides many check
 * methods to simplify this.
 * <p>
 * The simple checks include methods like {@link #isNotEmpty()} or
 * {@link #hasLength(int)} . But there are also more powerful checks like
 * {@link #containsAll(CharSequence...) containsAll(CharSequence...)},
 * {@link #matches(String)} or even {@link #isInt()}.
 * 
 * @author Michael Faes
 */
@GwtCompatible
public final class StringCheck extends ObjectBaseCheck<String, StringCheck> {
    
    /**
     * Default constructor, for internal use only.
     */
    public StringCheck() {}
    
    /**
     * Checks that the string argument is not empty, throwing an exception
     * otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_EMPTY}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if the string argument is empty
     */
    public StringCheck isNotEmpty() {
        return check(arg == null || !arg.isEmpty(), ARG_EMPTY, argName, arg);
    }
    
    /**
     * Checks that the string argument is not empty and does not consist
     * entirely of whitespace, throwing an exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_WHITESPACE}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if the string argument is empty or consists entirely of
     *             whitespace
     */
    public StringCheck isNotWhitespace() {
        return check(arg == null || !arg.trim().isEmpty(), ARG_WHITESPACE,
                argName);
    }
    
    /**
     * Checks that the string argument has the given length, throwing an
     * exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_LENGTH}.
     * <p>
     * To create more sophisticated checks using the string size, use the
     * {@link #hasLengthWhich()} property check.
     * 
     * @param length
     *            The length the string should have
     * @return This check
     * @throws IllegalArgumentException
     *             if the string argument has a size different from the given
     *             one
     * @see #hasLengthBetween(int, int)
     */
    public StringCheck hasLength(final int length) {
        return check(arg == null || arg.length() == length, ARG_LENGTH,
                argName, length, arg);
    }
    
    /**
     * Checks that the string argument's length is between the two given
     * numbers, throwing an exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_LENGTH_BETWEEN}.
     * <p>
     * To create more sophisticated checks using the array length, use the
     * {@link #hasLengthWhich()} property check.
     * 
     * @param min
     *            The minimum length of the string
     * @param max
     *            The maximum length
     * @return This check
     * @throws IllegalArgumentException
     *             If the length of the array is <em>strictly</em> less than
     *             <code>min</code> or <em>strictly</em> greater than
     *             <code>max</code>
     */
    public StringCheck hasLengthBetween(final int min, final int max) {
        return check(arg == null
                || (arg.length() >= min && arg.length() <= max),
                ARG_LENGTH_BETWEEN, argName, min, max, arg);
    }
    
    /**
     * Creates an {@link IntCheck} property check for the length of the string
     * argument. It can be used to create advanced checks using the string
     * length. For example:
     * <p>
     * <code>Check.that(string).isNullOr().hasLengthWhich().isGreaterThan(3);</code>
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the length of this check's string set as the
     * argument and an argument name that indicates this fact. If the string
     * reference is <code>null</code> and allowed to be so (like in the above
     * example), the returned property check is completely disabled, meaning
     * none of its check methods will throw an exception. An inversion called
     * before this method is ignored and does <strong>not</strong> have any
     * effect on the check methods called on the returned property check.
     * 
     * @return A property check for the length of the string argument
     */
    public IntCheck hasLengthWhich() {
        return intPropertyCheck(arg == null ? -1 : arg.length(), "length");
    }
    
    /**
     * Checks that the string argument starts with the given string, throwing an
     * exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_STARTS}.
     * 
     * @param prefix
     *            The string the string argument of this check should start with
     * @return This check
     * @throws IllegalArgumentException
     *             if the string argument does not start with the given string
     * @see #endsWith(String)
     */
    public StringCheck startsWith(final String prefix) {
        return check(arg == null || arg.startsWith(prefix), ARG_STARTS,
                argName, prefix, arg);
    }
    
    /**
     * Checks that the string argument end with the given string, throwing an
     * exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_ENDS}.
     * 
     * @param suffix
     *            The string the string argument of this check should end with
     * @return This check
     * @throws IllegalArgumentException
     *             if the string argument does not end with the given string
     * @see #startsWith(String)
     */
    public StringCheck endsWith(final String suffix) {
        return check(arg == null || arg.endsWith(suffix), ARG_ENDS, argName,
                suffix, arg);
    }
    
    /**
     * Checks that the string argument contains the given character sequence,
     * throwing an exception otherwise. Note that this check and even more the
     * {@link #containsAny(CharSequence...) containsAny} check can be very
     * useful in their inverted form:
     * <p>
     * <code>Check.that(message).not().containsAny(swearWords);</code>
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_CONTAINS}.
     * 
     * @param sequence
     *            The sequence the string argument should contain
     * @return This check
     * @throws IllegalArgumentException
     *             if the string argument does not contain the given sequence
     * @see #containsAny(CharSequence...)
     * @see #containsAll(CharSequence...)
     */
    public StringCheck contains(final CharSequence sequence) {
        return check(arg == null || arg.contains(sequence), ARG_CONTAINS,
                argName, sequence, arg);
    }
    
    /**
     * Checks that the string argument contains at least one of the given
     * character sequences, throwing an exception otherwise. Note that if no
     * sequences are given (i.e. an empty array), this check always fails. Also
     * note that this check can be very useful in its inverted form:
     * <p>
     * <code>Check.that(message).not().containsAny(swearWords);</code>
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_CONTAINS_ANY}.
     * 
     * @param sequences
     *            The array of sequences of which the string argument should
     *            contain at least one
     * @return This check
     * @throws IllegalArgumentException
     *             if the string argument contains none of the given sequences
     * @see #containsAll(CharSequence...)
     */
    public StringCheck containsAny(final CharSequence... sequences) {
        return check(arg == null || testContainsAny(sequences),
                ARG_CONTAINS_ANY, argName, Arrays.toString(sequences), arg);
    }
    
    /**
     * Checks that the string argument contains all one of the given character
     * sequences, throwing an exception otherwise. Note that if no sequences are
     * given (i.e. an empty array), this check always succeeds.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_CONTAINS_ALL}.
     * 
     * @param sequences
     *            The sequences the string argument must contain
     * @return This check
     * @throws IllegalArgumentException
     *             if the string argument does not contain all the given
     *             sequences
     * @see #containsAny(CharSequence...)
     */
    public StringCheck containsAll(final CharSequence... sequences) {
        return check(arg == null || testContainsAll(sequences),
                ARG_CONTAINS_ALL, argName, Arrays.toString(sequences), arg);
    }
    
    /**
     * Checks that the string argument matches the given regular expression,
     * throwing an exception otherwise. For maximum performance for non-dynamic
     * patterns, consider reusing a {@link Pattern} object by passing it to the
     * {@link #matches(Pattern)} check.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_MATCHES}.
     * 
     * @param regex
     *            The regular expression the string argument must match. See
     *            {@link Pattern} for more information.
     * @return This check
     * @throws IllegalArgumentException
     *             if the string argument does not contain match the given
     *             regular expression
     * @see #matches(Pattern)
     */
    public StringCheck matches(final String regex) {
        return check(arg == null || arg.matches(regex), ARG_MATCHES, argName,
                regex, arg);
    }
    
    /**
     * Checks that the string argument matches the given compiled regular
     * expression pattern, throwing an exception otherwise. This check is
     * equivalent to the {@link #matches(String)} check, except that it is more
     * efficient if the same pattern object is used multiple times. Since
     * {@link Pattern} objects are immutable (and therefore thread-safe), it is
     * safe to pass the same pattern to multiple checks. For maximum
     * performance, consider creating a <code>static</code> pattern field for
     * each kind of <code>matches()</code> check.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_MATCHES}.
     * 
     * @param regex
     *            The regular expression the string argument must match. See
     *            {@link Pattern} for more information.
     * @return This check
     * @throws IllegalArgumentException
     *             if the string argument does not contain match the given
     *             regular expression
     * @see #matches(Pattern)
     */
    @GwtIncompatible("java.util.regex.Pattern")
    public StringCheck matches(final Pattern regex) {
        return check(arg == null || regex.matcher(arg).matches(), ARG_MATCHES,
                argName, regex, arg);
    }
    
    /**
     * Checks that the string argument is equal to the given string, throwing an
     * exception otherwise. This check method exists merely for using with a
     * property check. See {@link UrlCheck#hasHostWhich()} for example. However,
     * in conjunction with inversion (see {@link #not()}), this method can also
     * be used to exclude a single value.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_IS}.
     * 
     * @param string
     *            The string this argument must be equal to
     * @return This check
     * @throws IllegalArgumentException
     *             if the check argument is not equal to the given string
     */
    public StringCheck is(final String string) {
        return check(arg == null || arg.equals(string), ARG_IS, argName,
                string, arg);
    }
    
    /**
     * Checks that the string argument is is a valid URL, throwing an exception
     * otherwise. Note that this includes URLs with protocols other than
     * <code>http://</code>, for example <code>ftp://</code> or
     * <code>file://</code>. To specify further requirements regarding the
     * protocol, the host or the path, use the {@link #isUrlWhich()} conversion
     * check. For more information about valid URLs, see the {@link URL} class.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_URL}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if the check argument is not a valid URL
     */
    @GwtIncompatible("java.net.URL")
    public StringCheck isUrl() {
        URL url = null;
        Exception cause = null;
        try {
            url = new URL(arg);
        } catch(final Exception e) {
            cause = e;
        }
        return checkWithCause(arg == null || url != null, ARG_URL, cause,
                argName, arg);
    }
    
    /**
     * First checks that the string argument is a valid URL and then "converts"
     * this check into an {@link UrlCheck} and returns it. This can be used to
     * create checks involving properties of the URL. For example:
     * <p>
     * <code>Check.that(string).isUrlWhich().hasProtocol("http");</code>
     * <p>
     * Just like a normal check method, this method first asserts the
     * non-nullness of the argument, throwing an exception in case of a failure.
     * Otherwise, the returned check has the same argument name as this check
     * object and also behaves the same regarding the nullness of the argument.
     * <p>
     * The message type used for exceptions thrown by <em>this</em> method (not
     * the subsequent {@link UrlCheck} checks) is {@link MessageType#ARG_URL}.
     * 
     * @return An URL check for asserting properties of the URL represented by
     *         the string argument
     * @throws InvalidCheckException
     *             If this check has just been inverted using {@link #not()}.
     *             This is prohibited as it would allow unintuitive checks.
     */
    @GwtIncompatible("java.net.URL")
    public UrlCheck isUrlWhich() {
        checkConversion();
        URL url = null;
        Exception cause = null;
        try {
            url = new URL(arg);
        } catch(final Exception e) {
            cause = e;
        }
        checkWithCause(arg == null || url != null, ARG_URL, cause, argName, arg);
        final UrlCheck urlCheck = Check.that(url).named(argName);
        if(nullAllowed)
            urlCheck.isNullOr();
        return urlCheck;
    }
    
    /**
     * Checks that the string argument is is a valid <code>int</code>, throwing
     * an exception otherwise. Note that numbers outside of the <code>int</code>
     * range (given by {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE})
     * are invalid. See {@link Integer#parseInt(String)} for a description of
     * valid integer representations. To specify further requirements for the
     * <code>int</code> that the string argument should represent, use the
     * {@link #isIntWhich()} conversion check.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_INT}.
     * 
     * @return This check
     * @throws IllegalArgumentException
     *             if the check argument is not a valid <code>int</code>
     */
    public StringCheck isInt() {
        checkNull();
        Integer number = null;
        Exception cause = null;
        try {
            number = new Integer(arg);
        } catch(final Exception e) {
            cause = e;
        }
        return checkWithCause(arg == null || number != null, ARG_INT, cause,
                argName, arg);
    }
    
    /**
     * First checks that the string argument is a valid <code>int</code> and
     * then "converts" this check into an {@link IntCheck} and returns it. This
     * can be used to create checks involving properties of the <code>int</code>
     * . For example:
     * <p>
     * <code>Check.that(string).isIntWhich().isPositive();</code>
     * <p>
     * Just like a normal check method, this method first asserts the
     * non-nullness of the argument, throwing an exception in case of a failure.
     * Otherwise, the returned check has the same argument name as this check
     * object. Also, if this check has been modified to allow <code>null</code>
     * and the argument <em>is</em> <code>null</code>, the returned check is
     * completely disabled, meaning none of its check methods throw an
     * exception.
     * <p>
     * The message type used for exceptions thrown by <em>this</em> method (not
     * the subsequent {@link IntCheck} checks) is {@link MessageType#ARG_INT}.
     * 
     * @return An <code>int</code> check for asserting properties of the
     *         <code>int</code> represented by the string argument
     * @throws InvalidCheckException
     *             If this check has just been inverted using {@link #not()}.
     *             This is prohibited as it would allow unintuitive checks.
     */
    public IntCheck isIntWhich() {
        checkConversion();
        checkNull();
        Integer number = null;
        Exception cause = null;
        try {
            number = new Integer(arg);
        } catch(final Exception e) {
            cause = e;
        }
        checkWithCause(arg == null || number != null, ARG_INT, cause, argName,
                arg);
        if(number == null)
            return Check.that(-1).named(argName).disable();
        else
            return Check.that((int) number).named(argName);
    }
    
    // IMPROVE: isDouble, isDoubleWhich
    
    /* Implementation methods */
    
    private boolean testContainsAll(final CharSequence... sequences) {
        for(final CharSequence sequence : sequences) {
            if(!arg.contains(sequence))
                return false;
        }
        return true;
    }
    
    private boolean testContainsAny(final CharSequence... sequences) {
        for(final CharSequence sequence : sequences) {
            if(arg.contains(sequence))
                return true;
        }
        return false;
    }
}
