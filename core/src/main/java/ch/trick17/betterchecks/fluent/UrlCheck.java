package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.ARG_PROTOCOL;

import java.net.URL;

import ch.trick17.betterchecks.MessageType;

/**
 * The check class for {@link URL} arguments.
 * <p>
 * It provides a quick method to assert a certain protocol (
 * {@link #hasProtocol(String)}) and other than that provides property checks
 * for all properties, including host, file, port, query, etc.
 * 
 * @author Michael Faes
 */
public class UrlCheck extends ObjectBaseCheck<URL, UrlCheck> {
    
    /**
     * Default constructor, for internal use only.
     */
    public UrlCheck() {}
    
    /**
     * Checks that the {@link URL} argument has the given protocol, throwing an
     * exception otherwise.
     * <p>
     * The message type used for exceptions thrown by this method is
     * {@link MessageType#ARG_PROTOCOL}.
     * <p>
     * To create more sophisticated checks using the URL protocol, use the
     * {@link #hasProtocolWhich()} property check.
     * 
     * @param protocol
     *            The protocol the URL should have
     * @return This check
     * @throws IllegalArgumentException
     *             if the URL argument has a protocol different from the given
     *             one
     */
    public UrlCheck hasProtocol(final String protocol) {
        return check(arg == null || arg.getProtocol().equals(protocol),
                ARG_PROTOCOL, argName, protocol, arg);
    }
    
    /**
     * Creates an {@link StringCheck} property check for the protocol of the
     * {@link URL} argument. It can be used to create advanced checks using the
     * protocol. For example:
     * <p>
     * <code>Check.that(url).hasProtocolWhich().matches("https?");</code>
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the protocol of this check's URL set as the
     * argument and an argument name that indicates this fact. If the URL
     * reference is <code>null</code> and allowed to be so (like in the above
     * example), the returned property check is completely disabled, meaning
     * none of its check methods will throw an exception. An inversion called
     * before this method is ignored and does <strong>not</strong> have any
     * effect on the check methods called on the returned property check.
     * 
     * @return A property check for the protocol of the URL argument
     */
    public StringCheck hasProtocolWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getProtocol(), "protocol");
    }
    
    /**
     * Creates an {@link StringCheck} property check for the host of the
     * {@link URL} argument. It can be used to create checks using the host. For
     * example:
     * <p>
     * <code>Check.that(url).hasHostWhich().endsWith(".ch");</code>
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the host of this check's URL set as the argument
     * and an argument name that indicates this fact. If the URL reference is
     * <code>null</code> and allowed to be so, the returned property check is
     * completely disabled, meaning none of its check methods will throw an
     * exception. An inversion called before this method is ignored and does
     * <strong>not</strong> have any effect on the check methods called on the
     * returned property check.
     * 
     * @return A property check for the host of the URL argument
     */
    public StringCheck hasHostWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getHost(), "host");
    }
    
    /**
     * Creates an {@link IntCheck} property check for the port of the
     * {@link URL} argument. It can be used to create checks using the port. For
     * example:
     * <p>
     * <code>Check.that(url).hasPortWhich().is(80);</code>
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the port of this check's URL set as the argument
     * and an argument name that indicates this fact. If the URL reference is
     * <code>null</code> and allowed to be so, the returned property check is
     * completely disabled, meaning none of its check methods will throw an
     * exception. An inversion called before this method is ignored and does
     * <strong>not</strong> have any effect on the check methods called on the
     * returned property check.
     * 
     * @return A property check for the port of the URL argument
     * @see URL#getPort()
     */
    public IntCheck hasPortWhich() {
        return intPropertyCheck(arg == null ? -1 : arg.getPort(), "port");
    }
    
    /**
     * Creates an {@link StringCheck} property check for the file of the
     * {@link URL} argument.
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the file of this check's URL set as the argument
     * and an argument name that indicates this fact. If the URL reference is
     * <code>null</code> and allowed to be so, the returned property check is
     * completely disabled, meaning none of its check methods will throw an
     * exception. An inversion called before this method is ignored and does
     * <strong>not</strong> have any effect on the check methods called on the
     * returned property check.
     * 
     * @return A property check for the file of the URL argument
     * @see URL#getFile()
     */
    public StringCheck hasFileWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getFile(), "file");
    }
    
    /**
     * Creates an {@link StringCheck} property check for the query of the
     * {@link URL} argument.
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the query of this check's URL set as the argument
     * and an argument name that indicates this fact. If the URL reference is
     * <code>null</code> and allowed to be so, the returned property check is
     * completely disabled, meaning none of its check methods will throw an
     * exception. An inversion called before this method is ignored and does
     * <strong>not</strong> have any effect on the check methods called on the
     * returned property check.
     * 
     * @return A property check for the query of the URL argument
     * @see URL#getQuery()
     */
    public StringCheck hasQueryWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getQuery(), "query");
    }
    
    /**
     * Creates an {@link StringCheck} property check for the authority of the
     * {@link URL} argument.
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the authority of this check's URL set as the
     * argument and an argument name that indicates this fact. If the URL
     * reference is <code>null</code> and allowed to be so, the returned
     * property check is completely disabled, meaning none of its check methods
     * will throw an exception. An inversion called before this method is
     * ignored and does <strong>not</strong> have any effect on the check
     * methods called on the returned property check.
     * 
     * @return A property check for the authority of the URL argument
     * @see URL#getAuthority()
     */
    public StringCheck hasAuthorityWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getAuthority(), "authority");
    }
    
    /**
     * Creates an {@link StringCheck} property check for the path of the
     * {@link URL} argument.
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the path of this check's URL set as the argument
     * and an argument name that indicates this fact. If the URL reference is
     * <code>null</code> and allowed to be so, the returned property check is
     * completely disabled, meaning none of its check methods will throw an
     * exception. An inversion called before this method is ignored and does
     * <strong>not</strong> have any effect on the check methods called on the
     * returned property check.
     * 
     * @return A property check for the path of the URL argument
     * @see URL#getPath()
     */
    public StringCheck hasPathWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getPath(), "path");
    }
    
    /**
     * Creates an {@link StringCheck} property check for the user info of the
     * {@link URL} argument.
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the user info of this check's URL set as the
     * argument and an argument name that indicates this fact. If the URL
     * reference is <code>null</code> and allowed to be so, the returned
     * property check is completely disabled, meaning none of its check methods
     * will throw an exception. An inversion called before this method is
     * ignored and does <strong>not</strong> have any effect on the check
     * methods called on the returned property check.
     * 
     * @return A property check for the user info of the URL argument
     * @see URL#getUserInfo()
     */
    public StringCheck hasUserInfoWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getUserInfo(), "user info");
    }
    
    /**
     * Creates an {@link StringCheck} property check for the reference (or the
     * anchor) of the {@link URL} argument.
     * <p>
     * Just like a check method, this method first asserts the non-nullness of
     * the argument, throwing an exception in case of a failure.
     * <p>
     * The returned check has the reference of this check's URL set as the
     * argument and an argument name that indicates this fact. If the URL
     * reference is <code>null</code> and allowed to be so, the returned
     * property check is completely disabled, meaning none of its check methods
     * will throw an exception. An inversion called before this method is
     * ignored and does <strong>not</strong> have any effect on the check
     * methods called on the returned property check.
     * 
     * @return A property check for the reference of the URL argument
     * @see URL#getRef()
     */
    public StringCheck hasRefWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getRef(), "reference (anchor)");
    }
}
