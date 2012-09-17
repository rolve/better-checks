package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageFormatId.*;
import static ch.trick17.betterchecks.MsgFormatter.formatMsg;

public class StringCheck extends ObjectCheck {
    
    /*
     * State methods
     */
    
    @Override
    public StringCheck named(final String argumentName) {
        super.named(argumentName);
        return this;
    }
    
    @Override
    public StringCheck isNullOr() {
        super.isNullOr();
        return this;
    }
    
    /*
     * Checks
     */
    
    @Override
    public StringCheck isNotNull() {
        super.isNotNull();
        return this;
    }
    
    public StringCheck isNotEmpty() {
        super.isNotNull();
        if(arg != null && ((String) arg).isEmpty())
            throw new IllegalArgumentException(formatMsg(ARG_EMPTY, argName));
        return this;
    }
    
    public StringCheck isNotWhitespace() {
        super.isNotNull();
        if(arg != null && ((String) arg).trim().isEmpty())
            throw new IllegalArgumentException(formatMsg(ARG_WHITESPACE,
                    argName));
        return this;
    }
    
    public StringCheck hasLength(final int length) {
        super.isNotNull();
        if(arg != null && ((String) arg).length() != length)
            throw new IllegalArgumentException(formatMsg(ARG_LENGTH, argName,
                    length, arg));
        return this;
    }
    
    // TODO: Add hasLengthBetween(int, int)
    
    public StringCheck startsWith(final String prefix) {
        super.isNotNull();
        if(arg != null && !((String) arg).startsWith(prefix))
            throw new IllegalArgumentException(formatMsg(ARG_STARTS, argName,
                    prefix, arg));
        return this;
    }
    
    public StringCheck endsWith(final String suffix) {
        super.isNotNull();
        if(arg != null && !((String) arg).endsWith(suffix))
            throw new IllegalArgumentException(formatMsg(ARG_ENDS, argName,
                    suffix, arg));
        return this;
    }
    
    public StringCheck matches(final String regex) {
        super.isNotNull();
        // IMPROVE: Cache compiled patterns?
        if(arg != null && !((String) arg).matches(regex))
            throw new IllegalArgumentException(formatMsg(ARG_MATCHES, argName,
                    regex, arg));
        return this;
    }
}
