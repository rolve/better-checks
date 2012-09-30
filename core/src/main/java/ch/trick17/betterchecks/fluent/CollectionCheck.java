package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.util.Collection;

public final class CollectionCheck extends
        ObjectBaseCheck<Collection<?>, CollectionCheck> {
    
    public CollectionCheck isNotEmpty() {
        return check(arg == null || !arg.isEmpty(), ARG_EMPTY, argName, arg);
    }
    
    public CollectionCheck hasSize(final int size) {
        return check(arg == null || arg.size() == size, ARG_SIZE, argName,
                size, arg);
    }
    
    public CollectionCheck hasSizeBetween(final int min, final int max) {
        return check(arg == null || (arg.size() >= min && arg.size() <= max),
                ARG_SIZE_BETWEEN, argName, min, max, arg);
    }
    
    public IntCheck hasSizeWhich() {
        return intPropertyCheck(arg == null ? -1 : arg.size(), "size");
    }
}
