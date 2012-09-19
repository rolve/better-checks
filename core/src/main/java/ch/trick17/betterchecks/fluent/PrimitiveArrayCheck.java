package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MsgFormatId.*;
import static ch.trick17.betterchecks.MsgFormatter.formatMsg;

import java.util.Arrays;

public class PrimitiveArrayCheck extends BaseCheck<Object, PrimitiveArrayCheck> {
    
    private int argLength;
    
    protected final void reset(final Object argument, final int argumentLength) {
        reset(argument);
        argLength = argumentLength;
    }
    
    public PrimitiveArrayCheck isNotEmpty() {
        super.isNotNull();
        if(arg != null && argLength == 0)
            throw new IllegalArgumentException(formatMsg(ARG_EMPTY, argName));
        return this;
    }
    
    public PrimitiveArrayCheck hasLength(final int length) {
        super.isNotNull();
        if(arg != null && argLength != length)
            throw new IllegalArgumentException(formatMsg(ARG_LENGTH, argName,
                    length, arrayToString()));
        return this;
    }
    
    public PrimitiveArrayCheck hasLengthBetween(final int min, final int max) {
        super.isNotNull();
        if(arg != null && (argLength < min || argLength > max))
            throw new IllegalArgumentException(formatMsg(ARG_LENGTH_BETWEEN,
                    argName, min, max, arrayToString()));
        return this;
    }
    
    private String arrayToString() {
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
