package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.util.Arrays;

public final class ObjectArrayCheck extends
        ObjectBaseCheck<Object[], ObjectArrayCheck> {
    
    public ObjectArrayCheck isNotEmpty() {
        return check(arg == null || arg.length != 0, ARG_EMPTY, argName, Arrays.toString(arg));
    }
    
    public ObjectArrayCheck hasLength(final int length) {
        return check(arg == null || arg.length == length, ARG_LENGTH, argName,
                length, Arrays.toString(arg));
    }
    
    public ObjectArrayCheck hasLengthBetween(final int min, final int max) {
        return check(arg == null || (arg.length >= min && arg.length <= max),
                ARG_LENGTH_BETWEEN, argName, min, max, Arrays.toString(arg));
    }
    
    public IntCheck hasLengthWhich() {
        return intPropertyCheck(arg == null ? -1 : arg.length, "length");
    }
}
