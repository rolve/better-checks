package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.ARG_PROTOCOL;

import java.net.URL;

public class UrlCheck extends ObjectBaseCheck<URL, UrlCheck> {
    
    /**
     * Default constructor, for internal use only.
     */
    public UrlCheck() {}
    
    public UrlCheck hasProtocol(final String protocol) {
        return check(arg == null || arg.getProtocol().equals(protocol),
                ARG_PROTOCOL, argName, protocol, arg);
    }
    
    public StringCheck hasProtocolWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getProtocol(), "protocol");
    }
    
    public StringCheck hasHostWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getHost(), "host");
    }
    
    public IntCheck hasPortWhich() {
        return intPropertyCheck(arg == null ? -1 : arg.getPort(), "port");
    }
    
    public StringCheck hasFileWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getFile(), "file");
    }
    
    public StringCheck hasQueryWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getQuery(), "query");
    }
    
    public StringCheck hasAuthorityWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getAuthority(), "authority");
    }
    
    public StringCheck hasPathWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getPath(), "path");
    }
    
    public StringCheck hasUserInfoWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getUserInfo(), "user info");
    }
    
    public StringCheck hasRefWhich() {
        return objectPropertyCheck(StringCheck.class, arg == null ? null : arg
                .getRef(), "reference (anchor)");
    }
}
