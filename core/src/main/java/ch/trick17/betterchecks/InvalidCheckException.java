package ch.trick17.betterchecks;

import ch.trick17.betterchecks.util.GwtCompatible;

/**
 * Indicates that the contract of a check object has been violated. This is the
 * fault of the "server" (the method that does the check) and not of the client
 * (the code that uses that method).
 * <p>
 * <strong>Note:</strong> The validity of checks is only checked in few cases,
 * so you should not rely on the Better Checks library to report erroneous uses
 * of checks. Instead, keep your checks simple and refer to the documentation of
 * each check method for precise information on what the method does.
 * <p>
 * For easier debugging, invalid check errors are reported by a separate type of
 * exception instead of {@link IllegalArgumentException}, which could easily
 * lead to confusion. This (and the fact that this exception does not extend
 * IllegalArgumentException) also ensures that such errors are not accidentally
 * caught by the client, which could turn an obvious bug in the server into a
 * very-hard-to-find bug in the client.
 * 
 * @author Michael Faes
 */
@GwtCompatible
public class InvalidCheckException extends RuntimeException {
    
    private static final long serialVersionUID = 1072035677940869197L;
    
    /**
     * Constructs a new invalid check exception with the specified detail
     * message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause(Throwable)}.
     * 
     * @param message
     *            the detail message. The detail message is saved for later
     *            retrieval by the {@link #getMessage()} method.
     */
    public InvalidCheckException(final String message) {
        super(message);
    }
}
