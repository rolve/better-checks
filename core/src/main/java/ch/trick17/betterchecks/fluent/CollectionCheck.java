package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.util.Collection;

import ch.trick17.betterchecks.Check;

public final class CollectionCheck extends
        BaseCheck<Collection<?>, CollectionCheck> {
    
    private static final String SIZE_PREFIX = "the size of ";
    
    public CollectionCheck isNotEmpty() {
        return check(arg == null || !arg.isEmpty(), ARG_EMPTY, argName);
    }
    
    public CollectionCheck hasSize(final int size) {
        return check(arg == null || arg.size() == size, ARG_SIZE, argName,
                size, arg);
    }
    
    public CollectionCheck hasSizeBetween(final int min, final int max) {
        return check(arg == null || (arg.size() >= min && arg.size() <= max),
                ARG_SIZE_BETWEEN, argName, min, max, arg);
    }
    
    public NumberCheck hasSizeWhich() {
        checkNull();
        final NumberCheck check = Check.that(arg == null ? null : arg.size());
        if(nullAllowed)
            check.isNullOr();
        return check.named(SIZE_PREFIX + argName);
    }
}
