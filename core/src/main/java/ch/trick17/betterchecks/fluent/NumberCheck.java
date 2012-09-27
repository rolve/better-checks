package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class NumberCheck extends ObjectBaseCheck<Number, NumberCheck> {
    
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
    
    public NumberCheck isEqualTo(final Number number) {
        return check(arg == null || compare(arg, number) == 0, ARG_EQUAL,
                argName, number, arg);
    }
    
    public NumberCheck isGreaterThan(final Number number) {
        return check(arg == null || compare(arg, number) > 0, ARG_GREATER,
                argName, number, arg);
    }
    
    public NumberCheck isLessThan(final Number number) {
        return check(arg == null || compare(arg, number) < 0, ARG_LESS,
                argName, number, arg);
    }
    
    public NumberCheck isBetween(final Number min, final Number max) {
        return check(arg == null
                || (compare(arg, min) >= 0 && compare(arg, max) <= 0),
                ARG_BETWEEN, argName, min, max, arg);
    }
    
    /*
     * Implementation methods
     */
    
    private static int compare(final Number x, final Number y) {
        final double dx = x.doubleValue();
        final double dy = y.doubleValue();
        if(Double.isInfinite(dx) || Double.isInfinite(dy))
            return Double.compare(dx, dy);
        else
            return toBigDecimal(x).compareTo(toBigDecimal(y));
    }
    
    private static BigDecimal toBigDecimal(final Number number) {
        if(number instanceof BigDecimal)
            return (BigDecimal) number;
        if(number instanceof BigInteger)
            return new BigDecimal((BigInteger) number);
        if(number instanceof Byte || number instanceof Short
                || number instanceof Integer || number instanceof Long)
            return new BigDecimal(number.longValue());
        if(number instanceof Float || number instanceof Double)
            return new BigDecimal(number.doubleValue());
        
        try {
            return new BigDecimal(number.toString());
        } catch(final NumberFormatException e) {
            throw new RuntimeException("The given number (" + number
                    + " of class " + number.getClass().getName()
                    + ") does not have a parsable string representation", e);
        }
    }
}
