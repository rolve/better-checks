package ch.trick17.betterchecks;

import java.util.Arrays;

/**
 * Helper class that provides several methods for creating exceptions and
 * formatting exception messages. It is intended only for internal and testing
 * use and provides convenient access to all exception-related configuration
 * aspects (which is actually <em>all</em> the configuration aspects so far) so
 * that the other classes do not need to depend on the {@link Config} class
 * directly.
 * <p>
 * The <code>...Exception(...)</code> methods all return exceptions of the type
 * indicated by the method name with their stack traces cleaned up. This feature
 * is described in the documentation of the {@link Check} class.
 * <p>
 * The stack trace cleaning is implemented by removing from the top of the stack
 * trace all stack frames belonging to methods that are part of the Better
 * Checks library. A method is defined to be part of the Better Checks library
 * iff it belongs to a class that is located in the base package of the library
 * (<code>ch.trick17.betterchecks</code>) or one of its subpackages.
 * Exceptionally, for testing purposes, classes ending in "Test" are not
 * considered to be part of the library.
 * 
 * @author Michael Faes
 */
public abstract class Exceptions {
    
    private static final String BASE_PACKAGE = Check.class.getPackage()
            .getName();
    
    /**
     * Returns an {@link IllegalArgumentException} with the given exception
     * message and a cleaned-up stack trace (if enabled).
     * 
     * @param message
     *            The exception message
     * @return such an exception
     */
    public static IllegalArgumentException illegalArgumentException(
            final String message) {
        final IllegalArgumentException exception = new IllegalArgumentException(
                message);
        cleanUpStackTrace(exception);
        return exception;
    }
    
    /**
     * Returns an {@link IllegalArgumentException} with the given exception
     * message, the given cause and a cleaned-up stack trace (if enabled).
     * 
     * @param message
     *            The exception message
     * @param cause
     *            The cause of the exception
     * @return such an exception
     */
    public static IllegalArgumentException illegalArgumentException(
            final String message, final Throwable cause) {
        final IllegalArgumentException exception = new IllegalArgumentException(
                message, cause);
        cleanUpStackTrace(exception);
        return exception;
    }
    
    /**
     * Returns an {@link IllegalArgumentException} with a message formatted
     * using the format corresponding to the given message type and the given
     * message arguments. If <code>inverted</code> is <code>true</code>, the
     * inverted message format will be used. Also, the stack trace of the
     * exception will be cleaned (if enabled).
     * <p>
     * See {@link Config#getMessageFormat(MessageType, boolean)} for more
     * information about exception message formats.
     * 
     * @param msgType
     *            The message type indicating the message format to use
     * @param inverted
     *            If <code>true</code>, the inverted format is used
     * @param msgArgs
     *            The message arguments
     * @return such an exception
     */
    public static IllegalArgumentException illegalArgumentException(
            final MessageType msgType, final boolean inverted,
            final Object[] msgArgs) {
        final IllegalArgumentException exception = new IllegalArgumentException(
                formatMsg(msgType, inverted, msgArgs));
        cleanUpStackTrace(exception);
        return exception;
    }
    
    /**
     * Returns an {@link IllegalArgumentException} with a message formatted
     * using the format corresponding to the given message type and the given
     * message arguments. If <code>inverted</code> is <code>true</code>, the
     * inverted message format will be used. Also, the cause of the message is
     * set to the one given and the stack trace of the exception will be cleaned
     * (if enabled).
     * <p>
     * See {@link Config#getMessageFormat(MessageType, boolean)} for more
     * information about exception message formats.
     * 
     * @param msgType
     *            The message type indicating the message format to use
     * @param inverted
     *            If <code>true</code>, the inverted format is used
     * @param msgArgs
     *            The message arguments
     * @param cause
     *            The cause for the exception
     * @return such an exception
     */
    public static IllegalArgumentException illegalArgumentException(
            final MessageType msgType, final boolean inverted,
            final Object[] msgArgs, final Throwable cause) {
        final IllegalArgumentException exception = new IllegalArgumentException(
                formatMsg(msgType, inverted, msgArgs), cause);
        cleanUpStackTrace(exception);
        return exception;
    }
    
    /**
     * Returns an {@link IllegalStateException} with the given exception message
     * and a cleaned-up stack trace (if enabled).
     * 
     * @param message
     *            The exception message
     * @return such an exception
     */
    public static IllegalStateException illegalStateException(
            final String message) {
        final IllegalStateException exception = new IllegalStateException(
                message);
        cleanUpStackTrace(exception);
        return exception;
    }
    
    /**
     * Formats an exception message using the format corresponding to the given
     * message type and the given message arguments. If <code>inverted</code> is
     * <code>true</code>, the inverted message format will be used.
     * 
     * @param msgType
     *            The message type indicating the message format to use
     * @param inverted
     *            If <code>true</code>, the inverted format is used
     * @param msgArgs
     *            The message arguments
     * @return The formatted exception message
     */
    public static String formatMsg(final MessageType msgType,
            final boolean inverted, final Object... msgArgs) {
        final String format = Config.getConfig().getMessageFormat(msgType,
                inverted);
        return String.format(format, msgArgs);
    }
    
    /**
     * A convenience method that returns the default argument name from the
     * configuration.
     * 
     * @return {@link Config#getDefaultArgumentName()}
     */
    public static String defaultArgName() {
        return Config.getConfig().getDefaultArgumentName();
    }
    
    /*
     * Implementation methods
     */
    
    private static void cleanUpStackTrace(final Exception exception) {
        if(Config.getConfig().isCleanStackTracesEnabled()) {
            StackTraceElement[] trace = exception.getStackTrace();
            int minIndex = 0;
            for(int i = 0; i < trace.length; i++) {
                if(isBetterChecksElement(trace[i]))
                    minIndex = i + 1;
                else
                    break;
            }
            if(minIndex > 0)
                trace = Arrays.copyOfRange(trace, minIndex, trace.length);
            exception.setStackTrace(trace);
        }
    }
    
    private static boolean isBetterChecksElement(final StackTraceElement element) {
        final String className = element.getClassName();
        return className.startsWith(BASE_PACKAGE)
                && !className.endsWith("Test");
    }
}
