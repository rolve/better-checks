package ch.trick17.betterchecks;

/**
 * Indicates that the contract of a Check instance has been violated. This is
 * the fault of the "server" (the method that does the check) and not of the
 * client (the code that uses that method).
 * <p>
 * For easier debugging, errors of this kind are reported by a separate type of
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
    
    public InvalidCheckException() {
        super();
    }
    
    public InvalidCheckException(final String message) {
        super(message);
    }
    
    public InvalidCheckException(final Throwable cause) {
        super(cause);
    }
    
    public InvalidCheckException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
