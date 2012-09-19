package ch.trick17.betterchecks;

import static ch.trick17.betterchecks.MsgFormatId.ARG_EMPTY;
import static ch.trick17.betterchecks.MsgFormatId.ARG_NULL;
import static ch.trick17.betterchecks.MsgFormatter.defaultArgName;
import static ch.trick17.betterchecks.MsgFormatter.formatMsg;

import java.util.Collection;

import ch.trick17.betterchecks.fluent.CollectionCheck;
import ch.trick17.betterchecks.fluent.FluentChecks;
import ch.trick17.betterchecks.fluent.NumberCheck;
import ch.trick17.betterchecks.fluent.ObjectArrayCheck;
import ch.trick17.betterchecks.fluent.ObjectCheck;
import ch.trick17.betterchecks.fluent.PrimitiveArrayCheck;
import ch.trick17.betterchecks.fluent.StringCheck;

public class Check {
    
    private Check() {}
    
    /*
     * Simple checks
     */
    
    public static void that(final boolean condition, final String message) {
        if(!condition)
            throw new IllegalArgumentException(message);
    }
    
    public static void notNull(final Object arg) {
        if(arg == null)
            throw new IllegalArgumentException(formatMsg(ARG_NULL,
                    defaultArgName()));
    }
    
    public static void notNull(final Object arg, final String argName) {
        if(arg == null)
            throw new IllegalArgumentException(formatMsg(ARG_NULL, argName));
    }
    
    public static void notEmpty(final String arg) {
        if(arg == null || arg.isEmpty())
            throw new IllegalArgumentException(formatMsg(ARG_EMPTY,
                    defaultArgName()));
    }
    
    public static void notEmpty(final String arg, final String argName) {
        if(arg == null || arg.isEmpty()) {
            throw new IllegalArgumentException(formatMsg(ARG_EMPTY, argName));
        }
    }
    
    /*
     * Fluent checks
     */
    
    public static ObjectCheck that(final Object argument) {
        return FluentChecks.getObjectCheck(ObjectCheck.class, argument);
    }
    
    public static StringCheck that(final String argument) {
        return FluentChecks.getObjectCheck(StringCheck.class, argument);
    }
    
    public static ObjectArrayCheck that(final Object[] argument) {
        return FluentChecks.getObjectCheck(ObjectArrayCheck.class, argument);
    }
    
    public static PrimitiveArrayCheck that(final boolean[] argument) {
        return FluentChecks.getPrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    public static PrimitiveArrayCheck that(final byte[] argument) {
        return FluentChecks.getPrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    public static PrimitiveArrayCheck that(final char[] argument) {
        return FluentChecks.getPrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    public static PrimitiveArrayCheck that(final double[] argument) {
        return FluentChecks.getPrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    public static PrimitiveArrayCheck that(final float[] argument) {
        return FluentChecks.getPrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    public static PrimitiveArrayCheck that(final int[] argument) {
        return FluentChecks.getPrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    public static PrimitiveArrayCheck that(final long[] argument) {
        return FluentChecks.getPrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    public static PrimitiveArrayCheck that(final short[] argument) {
        return FluentChecks.getPrimitiveArrayCheck(argument,
                argument != null ? argument.length : -1);
    }
    
    public static CollectionCheck that(final Collection<?> argument) {
        return FluentChecks.<Collection<?>, CollectionCheck> getObjectCheck(
                CollectionCheck.class, argument);
    }
    
    public static NumberCheck that(final Number argument) {
        return FluentChecks.getObjectCheck(NumberCheck.class, argument);
    }
    
}
