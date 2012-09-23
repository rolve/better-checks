package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import static ch.trick17.betterchecks.MsgFormatId.*;

public final class StringCheck extends BaseCheck<String, StringCheck> {
    
    public StringCheck isNotEmpty() {
        super.isNotNull();
        if(arg != null && arg.isEmpty())
            throw illegalArgumentException(ARG_EMPTY, argName);
        return this;
    }
    
    public StringCheck isNotWhitespace() {
        super.isNotNull();
        if(arg != null && arg.trim().isEmpty())
            throw illegalArgumentException(ARG_WHITESPACE, argName);
        return this;
    }
    
    public StringCheck hasLength(final int length) {
        super.isNotNull();
        if(arg != null && arg.length() != length)
            throw illegalArgumentException(ARG_LENGTH, argName, length, arg);
        return this;
    }
    
    public StringCheck hasLengthBetween(final int min, final int max) {
        super.isNotNull();
        if(arg != null && (arg.length() < min || arg.length() > max))
            throw illegalArgumentException(ARG_LENGTH_BETWEEN, argName, min,
                    max, arg);
        return this;
    }
    
    public StringCheck startsWith(final String prefix) {
        super.isNotNull();
        if(arg != null && !arg.startsWith(prefix))
            throw illegalArgumentException(ARG_STARTS, argName, prefix, arg);
        return this;
    }
    
    public StringCheck endsWith(final String suffix) {
        super.isNotNull();
        if(arg != null && !arg.endsWith(suffix))
            throw illegalArgumentException(ARG_ENDS, argName, suffix, arg);
        return this;
    }
    
    public StringCheck matches(final String regex) {
        super.isNotNull();
        // IMPROVE: Cache compiled patterns?
        if(arg != null && !arg.matches(regex))
            throw illegalArgumentException(ARG_MATCHES, argName, regex, arg);
        return this;
    }
}
