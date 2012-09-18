package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageFormatId.*;
import static ch.trick17.betterchecks.MsgFormatter.formatMsg;

public class StringCheck extends BaseCheck<String, StringCheck> {
    
    public StringCheck isNotEmpty() {
        super.isNotNull();
        if(arg != null && arg.isEmpty())
            throw new IllegalArgumentException(formatMsg(ARG_EMPTY, argName));
        return this;
    }
    
    public StringCheck isNotWhitespace() {
        super.isNotNull();
        if(arg != null && arg.trim().isEmpty())
            throw new IllegalArgumentException(formatMsg(ARG_WHITESPACE,
                    argName));
        return this;
    }
    
    public StringCheck hasLength(final int length) {
        super.isNotNull();
        if(arg != null && arg.length() != length)
            throw new IllegalArgumentException(formatMsg(ARG_LENGTH, argName,
                    length, arg));
        return this;
    }
    
    // TODO: Add hasLengthBetween(int, int)
    
    public StringCheck startsWith(final String prefix) {
        super.isNotNull();
        if(arg != null && !arg.startsWith(prefix))
            throw new IllegalArgumentException(formatMsg(ARG_STARTS, argName,
                    prefix, arg));
        return this;
    }
    
    public StringCheck endsWith(final String suffix) {
        super.isNotNull();
        if(arg != null && !arg.endsWith(suffix))
            throw new IllegalArgumentException(formatMsg(ARG_ENDS, argName,
                    suffix, arg));
        return this;
    }
    
    public StringCheck matches(final String regex) {
        super.isNotNull();
        // IMPROVE: Cache compiled patterns?
        if(arg != null && !arg.matches(regex))
            throw new IllegalArgumentException(formatMsg(ARG_MATCHES, argName,
                    regex, arg));
        return this;
    }
}
