package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.util.Collection;

public class IntCheck extends PrimitiveBaseCheck<IntCheck> {
    
    private int arg;
    
    protected final void reset(final int argument) {
        reset();
        this.arg = argument;
    }
    
    public IntCheck isPositive() {
        return check(arg > 0, ARG_POSITIVE, argName, arg);
    }
    
    public IntCheck isNegative() {
        return check(arg < 0, ARG_NEGATIVE, argName, arg);
    }
    
    public IntCheck isEqualTo(final int number) {
        return check(arg == number, ARG_EQUAL, argName, number, arg);
    }
    
    public IntCheck isGreaterThan(final int number) {
        return check(arg > number, ARG_GREATER, argName, number, arg);
    }
    
    public IntCheck isLessThan(final int number) {
        return check(arg < number, ARG_LESS, argName, number, arg);
    }
    
    public IntCheck isBetween(final int min, final int max) {
        checkValid(min <= max, "min (" + min
                + ") must be less than or equal to max (" + max + ")");
        return check(arg >= min && arg <= max, ARG_BETWEEN, argName, min, max,
                arg);
    }
    
    public IntCheck isValidIndex(final int listSize) {
        checkValid(listSize > 0, "listSize must be greater than 0 (value: "
                + listSize + ")");
        return check(arg >= 0 && arg < listSize, ARG_INDEX, argName, listSize,
                arg);
    }
    
    public IntCheck isValidIndex(final Collection<?> collection) {
        return isValidIndex(collection.size());
    }
    
    public IntCheck isValidIndex(final Object[] array) {
        return isValidIndex(array.length);
    }
}
