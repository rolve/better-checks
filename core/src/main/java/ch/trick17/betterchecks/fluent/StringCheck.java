package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

public final class StringCheck extends BaseCheck<String, StringCheck> {
    
    public StringCheck isNotEmpty() {
        return check(arg == null || !arg.isEmpty(), ARG_EMPTY, argName);
    }
    
    public StringCheck isNotWhitespace() {
        return check(arg == null || !arg.trim().isEmpty(), ARG_WHITESPACE,
                argName);
    }
    
    public StringCheck hasLength(final int length) {
        return check(arg == null || arg.length() == length, ARG_LENGTH,
                argName, length, arg);
    }
    
    public StringCheck hasLengthBetween(final int min, final int max) {
        return check(arg == null
                || (arg.length() >= min && arg.length() <= max),
                ARG_LENGTH_BETWEEN, argName, min, max, arg);
    }
    
    public StringCheck startsWith(final String prefix) {
        return check(arg == null || arg.startsWith(prefix), ARG_STARTS,
                argName, prefix, arg);
    }
    
    public StringCheck endsWith(final String suffix) {
        return check(arg == null || arg.endsWith(suffix), ARG_ENDS, argName,
                suffix, arg);
    }
    
    public StringCheck matches(final String regex) {
        // IMPROVE: Cache compiled patterns?
        return check(arg == null || arg.matches(regex), ARG_MATCHES, argName,
                regex, arg);
    }
}
