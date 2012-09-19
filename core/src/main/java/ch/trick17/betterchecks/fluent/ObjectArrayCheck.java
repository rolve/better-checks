package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MsgFormatId.*;
import static ch.trick17.betterchecks.MsgFormatter.formatMsg;

import java.util.Arrays;

public class ObjectArrayCheck extends BaseCheck<Object[], ObjectArrayCheck> {
    
    public ObjectArrayCheck isNotEmpty() {
        super.isNotNull();
        if(arg != null && arg.length == 0)
            throw new IllegalArgumentException(formatMsg(ARG_EMPTY, argName));
        return this;
    }
    
    public ObjectArrayCheck hasLength(final int length) {
        super.isNotNull();
        if(arg != null && arg.length != length)
            throw new IllegalArgumentException(formatMsg(ARG_LENGTH, argName,
                    length, Arrays.toString(arg)));
        return this;
    }
    
    public ObjectArrayCheck hasLengthBetween(final int min, final int max) {
        super.isNotNull();
        if(arg != null && (arg.length < min || arg.length > max))
            throw new IllegalArgumentException(formatMsg(ARG_LENGTH_BETWEEN,
                    argName, min, max, Arrays.toString(arg)));
        return this;
    }
}
