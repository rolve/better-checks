package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MsgFormatId.*;
import static ch.trick17.betterchecks.MsgFormatter.formatMsg;

import java.util.Collection;

public class CollectionCheck extends BaseCheck<Collection<?>, CollectionCheck> {
    
    public CollectionCheck isNotEmpty() {
        super.isNotNull();
        if(arg != null && arg.isEmpty())
            throw new IllegalArgumentException(formatMsg(ARG_EMPTY, argName));
        return this;
    }
    
    public CollectionCheck hasSize(final int size) {
        super.isNotNull();
        if(arg != null && arg.size() != size)
            throw new IllegalArgumentException(formatMsg(ARG_SIZE, argName,
                    size, arg));
        return this;
    }
    
    public CollectionCheck hasSizeBetween(final int min, final int max) {
        super.isNotNull();
        if(arg != null && (arg.size() < min || arg.size() > max))
            throw new IllegalArgumentException(formatMsg(ARG_SIZE_BETWEEN,
                    argName, min, max, arg));
        return this;
    }
}
