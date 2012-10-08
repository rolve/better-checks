package ch.trick17.betterchecks;

/**
 * Indicates that the contract of a check object has been violated. This is the
 * fault of the "server" (the method that does the check) and not of the client
 * (the code that uses that method).
 * <p>
 * <strong>Note:</strong> For performance reasons, the validity of checks is
 * currently not checked, meaning that this exception is not yet used. However,
 * this may change in later releases of the Better Checks library.
 * <p>
 * For easier debugging, invalid check errors are reported by a separate type of
 * exception and not {@link IllegalArgumentException} as well, which could
 * easily lead to confusion. This (and the fact that this exception does not
 * extend IllegalAgrumentException) also ensures that such errors are not
 * accidentally caught by the client, which could turn an obvious bug in the
 * server to a very hard-to-find bug in the client.
 * 
 * @author Michael Faes
 */
public class InvalidCheckException extends RuntimeException {
    
    private static final long serialVersionUID = 1072035677940869197L;
    
    /**
     * Constructs a new invalid check exception with <code>null</code> as its
     * detail message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause(Throwable)}.
     */
    public InvalidCheckException() {
        super();
    }
    
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
    
    /**
     * Constructs a new invalid check exception with the specified detail
     * message and cause.
     * <p>
     * Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail message.
     * 
     * @param message
     *            the detail message (which is saved for later retrieval by the
     *            {@link #getMessage()} method).
     * @param cause
     *            the cause (which is saved for later retrieval by the
     *            {@link #getCause()} method). (A <code>null</code> value is
     *            permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     */
    public InvalidCheckException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
