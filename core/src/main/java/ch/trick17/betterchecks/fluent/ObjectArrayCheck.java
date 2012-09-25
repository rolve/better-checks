package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.util.Arrays;

import ch.trick17.betterchecks.Check;

public final class ObjectArrayCheck extends
        BaseCheck<Object[], ObjectArrayCheck> {
    
    private static final String LENGTH_PREFIX = "the length of ";
    
    public ObjectArrayCheck isNotEmpty() {
        return check(arg == null || arg.length != 0, ARG_EMPTY, argName);
    }
    
    public ObjectArrayCheck hasLength(final int length) {
        return check(arg == null || arg.length == length, ARG_LENGTH, argName,
                length, Arrays.toString(arg));
    }
    
    public ObjectArrayCheck hasLengthBetween(final int min, final int max) {
        return check(arg == null || (arg.length >= min && arg.length <= max),
                ARG_LENGTH_BETWEEN, argName, min, max, Arrays.toString(arg));
    }
    
    public NumberCheck hasLengthWhich() {
        checkNull();
        final NumberCheck check = Check.that(arg == null ? null : arg.length);
        if(nullAllowed)
            check.isNullOr();
        return check.named(LENGTH_PREFIX + argName);
    }
}
