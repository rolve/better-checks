package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.Exceptions.illegalArgumentException;
import static ch.trick17.betterchecks.MsgFormatId.ARG_NEGATIVE;
import static ch.trick17.betterchecks.MsgFormatId.ARG_POSITIVE;

import java.math.BigDecimal;

public class NumberCheck extends BaseCheck<Number, NumberCheck> {
    
    public NumberCheck isPositive() {
        super.isNotNull();
        if(arg != null) {
            if(arg instanceof BigDecimal) {
                if(((BigDecimal) arg).signum() != 1)
                    throw illegalArgumentException(ARG_POSITIVE, argName, arg);
            }
            else if(!(arg.doubleValue() > 0))
                throw illegalArgumentException(ARG_POSITIVE, argName, arg);
        }
        return this;
    }
    
    public NumberCheck isNegative() {
        super.isNotNull();
        if(arg != null) {
            if(arg instanceof BigDecimal) {
                if(((BigDecimal) arg).signum() != -1)
                    throw illegalArgumentException(ARG_NEGATIVE, argName, arg);
            }
            else if(!(arg.doubleValue() < 0))
                throw illegalArgumentException(ARG_NEGATIVE, argName, arg);
        }
        return this;
    }
    
    // TODO: isBetween, isGreaterThan, isLessThan, not()
}
