package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import static ch.trick17.betterchecks.MsgFormatId.*;

import java.util.Collection;

public final class CollectionCheck extends
        BaseCheck<Collection<?>, CollectionCheck> {
    
    public CollectionCheck isNotEmpty() {
        super.isNotNull();
        if(arg != null && arg.isEmpty())
            throw illegalArgumentException(ARG_EMPTY, argName);
        return this;
    }
    
    public CollectionCheck hasSize(final int size) {
        super.isNotNull();
        if(arg != null && arg.size() != size)
            throw illegalArgumentException(ARG_SIZE, argName, size, arg);
        return this;
    }
    
    public CollectionCheck hasSizeBetween(final int min, final int max) {
        super.isNotNull();
        if(arg != null && (arg.size() < min || arg.size() > max))
            throw illegalArgumentException(ARG_SIZE_BETWEEN, argName, min, max,
                    arg);
        return this;
    }
}
