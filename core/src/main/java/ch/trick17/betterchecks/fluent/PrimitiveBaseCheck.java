package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import ch.trick17.betterchecks.MessageType;

public class PrimitiveBaseCheck<C extends PrimitiveBaseCheck<C>> extends
        BaseCheck<C> {
    
    protected final C check(final boolean condition,
            final MessageType formatId, final Object... msgArgs) {
        if(inverted ? condition : !condition)
            throw illegalArgumentException(formatId, inverted, msgArgs);
        inverted = false;
        return me();
    }
}
