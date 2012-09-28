package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.util.Arrays;

public final class PrimitiveArrayCheck extends
        ObjectBaseCheck<Object, PrimitiveArrayCheck> {
    
    private int argLength;
    
    protected void reset(final Object argument, final int argumentLength) {
        reset(argument);
        argLength = argumentLength;
    }
    
    public PrimitiveArrayCheck isNotEmpty() {
        return check(arg == null || argLength != 0, ARG_EMPTY, argName);
    }
    
    public PrimitiveArrayCheck hasLength(final int length) {
        return check(arg == null || argLength == length, ARG_LENGTH, argName,
                length, arrayToString(arg));
    }
    
    public PrimitiveArrayCheck hasLengthBetween(final int min, final int max) {
        return check(arg == null || (argLength >= min && argLength <= max),
                ARG_LENGTH_BETWEEN, argName, min, max, arrayToString(arg));
    }
    
    public NumberCheck hasLengthWhich() {
        return propertyCheck(NumberCheck.class,
                arg == null ? null : (Number) argLength, "length");
    }
    
    /*
     * Implementation methods
     */
    
    private static String arrayToString(final Object arg) {
        if(arg instanceof boolean[])
            return Arrays.toString((boolean[]) arg);
        if(arg instanceof byte[])
            return Arrays.toString((byte[]) arg);
        if(arg instanceof char[])
            return Arrays.toString((char[]) arg);
        if(arg instanceof double[])
            return Arrays.toString((double[]) arg);
        if(arg instanceof float[])
            return Arrays.toString((float[]) arg);
        if(arg instanceof int[])
            return Arrays.toString((int[]) arg);
        if(arg instanceof long[])
            return Arrays.toString((long[]) arg);
        if(arg instanceof short[])
            return Arrays.toString((short[]) arg);
        
        throw new RuntimeException("argument is not an primitive array: " + arg
                + " of class " + arg.getClass().getName());
    }
}
