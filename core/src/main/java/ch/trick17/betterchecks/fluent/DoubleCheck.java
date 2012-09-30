package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

public class DoubleCheck extends PrimitiveBaseCheck<DoubleCheck> {
    
    private double arg;
    
    protected final void reset(final double argument) {
        reset();
        this.arg = argument;
    }
    
    public DoubleCheck isPositive() {
        return check(arg > 0, ARG_POSITIVE, argName, arg);
    }
    
    public DoubleCheck isNegative() {
        return check(arg < 0, ARG_NEGATIVE, argName, arg);
    }
    
    public DoubleCheck is(final double number) {
        return check(arg == number, ARG_IS, argName, number, arg);
    }
    
    public DoubleCheck isGreaterThan(final double number) {
        return check(arg > number, ARG_GREATER, argName, number, arg);
    }
    
    public DoubleCheck isLessThan(final double number) {
        return check(arg < number, ARG_LESS, argName, number, arg);
    }
    
    public DoubleCheck isBetween(final double min, final double max) {
        checkValid(min <= max, "min (" + min
                + ") must be less than or equal to max (" + max + ")");
        return check(arg >= min && arg <= max, ARG_BETWEEN, argName, min, max,
                arg);
    }
    
    public DoubleCheck isNotNaN() {
        return check(!Double.isNaN(arg), ARG_NAN, argName);
    }
    
    public DoubleCheck isNotInfinite() {
        return check(!Double.isInfinite(arg), ARG_INFINITE, argName, arg);
    }
}
