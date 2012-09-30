package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

public class LongCheck extends PrimitiveBaseCheck<LongCheck> {
    
    private long arg;
    
    protected final void reset(final long argument) {
        reset();
        this.arg = argument;
    }
    
    public LongCheck isPositive() {
        return check(arg > 0, ARG_POSITIVE, argName, arg);
    }
    
    public LongCheck isNegative() {
        return check(arg < 0, ARG_NEGATIVE, argName, arg);
    }
    
    public LongCheck is(final long number) {
        return check(arg == number, ARG_IS, argName, number, arg);
    }
    
    public LongCheck isGreaterThan(final long number) {
        return check(arg > number, ARG_GREATER, argName, number, arg);
    }
    
    public LongCheck isLessThan(final long number) {
        return check(arg < number, ARG_LESS, argName, number, arg);
    }
    
    public LongCheck isBetween(final long min, final long max) {
        checkValid(min <= max, "min (" + min
                + ") must be less than or equal to max (" + max + ")");
        return check(arg >= min && arg <= max, ARG_BETWEEN, argName, min, max,
                arg);
    }
}
