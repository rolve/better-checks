package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.ARG_PROTOCOL;

import java.net.URL;

public class UrlCheck extends ObjectBaseCheck<URL, UrlCheck> {
    
    public UrlCheck hasProtocol(final String protocol) {
        return check(arg == null || arg.getProtocol().equals(protocol),
                ARG_PROTOCOL, argName, protocol, arg);
    }
    
    public StringCheck hasProtocolWhich() {
        return propertyCheck(StringCheck.class, arg == null ? null : arg
                .getProtocol(), "protocol");
    }
    
    public StringCheck hasHostWhich() {
        return propertyCheck(StringCheck.class, arg == null ? null : arg
                .getHost(), "host");
    }
    
    public NumberCheck hasPortWhich() {
        return propertyCheck(NumberCheck.class,
                arg == null ? null : (Number) arg.getPort(), "port");
    }
    
    public StringCheck hasFileWhich() {
        return propertyCheck(StringCheck.class, arg == null ? null : arg
                .getFile(), "file");
    }
    
    public StringCheck hasQueryWhich() {
        return propertyCheck(StringCheck.class, arg == null ? null : arg
                .getQuery(), "query");
    }
    
    public StringCheck hasAuthorityWhich() {
        return propertyCheck(StringCheck.class, arg == null ? null : arg
                .getAuthority(), "authority");
    }
    
    public StringCheck hasPathWhich() {
        return propertyCheck(StringCheck.class, arg == null ? null : arg
                .getPath(), "path");
    }
    
    public StringCheck hasUserInfoWhich() {
        return propertyCheck(StringCheck.class, arg == null ? null : arg
                .getUserInfo(), "user info");
    }
    
    public StringCheck hasRefWhich() {
        return propertyCheck(StringCheck.class, arg == null ? null : arg
                .getRef(), "reference (anchor)");
    }
}
