package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MsgFormatId.ARG_NEGATIVE;
import static ch.trick17.betterchecks.MsgFormatId.ARG_POSITIVE;

import java.math.BigDecimal;

public final class NumberCheck extends BaseCheck<Number, NumberCheck> {
    
    public NumberCheck isPositive() {
        checkNull();
        if(arg instanceof BigDecimal)
            return check(((BigDecimal) arg).signum() == 1, ARG_POSITIVE,
                    argName, arg);
        else
            return check(arg.doubleValue() > 0, ARG_POSITIVE, argName, arg);
    }
    
    public NumberCheck isNegative() {
        checkNull();
        if(arg instanceof BigDecimal)
            return check(((BigDecimal) arg).signum() == -1, ARG_NEGATIVE,
                    argName, arg);
        else
            return check(arg.doubleValue() < 0, ARG_NEGATIVE, argName, arg);
    }
    
    // TODO: isBetween, isGreaterThan, isLessThan
}
