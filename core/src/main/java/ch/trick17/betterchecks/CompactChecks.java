package ch.trick17.betterchecks;

import java.util.Collection;

import ch.trick17.betterchecks.fluent.CollectionCheck;
import ch.trick17.betterchecks.fluent.NumberCheck;
import ch.trick17.betterchecks.fluent.ObjectArrayCheck;
import ch.trick17.betterchecks.fluent.ObjectCheck;
import ch.trick17.betterchecks.fluent.PrimitiveArrayCheck;
import ch.trick17.betterchecks.fluent.StringCheck;

public class CompactChecks {
    
    public static ObjectCheck check(final Object argument) {
        return Check.that(argument);
    }
    
    public static StringCheck check(final String argument) {
        return Check.that(argument);
    }
    
    public static ObjectArrayCheck check(final Object[] argument) {
        return Check.that(argument);
    }
    
    public static PrimitiveArrayCheck check(final boolean[] argument) {
        return Check.that(argument);
    }
    
    public static PrimitiveArrayCheck check(final byte[] argument) {
        return Check.that(argument);
    }
    
    public static PrimitiveArrayCheck check(final char[] argument) {
        return Check.that(argument);
    }
    
    public static PrimitiveArrayCheck check(final double[] argument) {
        return Check.that(argument);
    }
    
    public static PrimitiveArrayCheck check(final float[] argument) {
        return Check.that(argument);
    }
    
    public static PrimitiveArrayCheck check(final int[] argument) {
        return Check.that(argument);
    }
    
    public static PrimitiveArrayCheck check(final long[] argument) {
        return Check.that(argument);
    }
    
    public static PrimitiveArrayCheck check(final short[] argument) {
        return Check.that(argument);
    }
    
    public static CollectionCheck check(final Collection<?> argument) {
        return Check.that(argument);
    }
    
    public static NumberCheck check(final Number argument) {
        return Check.that(argument);
    }
}
