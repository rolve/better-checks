package ch.trick17.betterchecks;

import java.net.URL;
import java.util.Collection;

import ch.trick17.betterchecks.fluent.CollectionCheck;
import ch.trick17.betterchecks.fluent.NumberCheck;
import ch.trick17.betterchecks.fluent.ObjectArrayCheck;
import ch.trick17.betterchecks.fluent.ObjectCheck;
import ch.trick17.betterchecks.fluent.PrimitiveArrayCheck;
import ch.trick17.betterchecks.fluent.StringCheck;
import ch.trick17.betterchecks.fluent.UrlCheck;

/**
 * This class provides the same functionality as the {@link Check} class, but
 * with even more compact syntax.
 * <p>
 * To use it, statically import the {@link CompactChecks#check(Object)} method
 * and write your checks like this:
 * <p>
 * <code>check(<em>argument</em>).<em>check1</em>().<em>check2</em>()<em>...</em>;</code>
 * <p>
 * Examples:
 * <p>
 * <code>check(name).matches("hello .*!").hasLenghtBetween(0, 20);</code><br>
 * <code>check(list).isNullOr().hasSize(0);</code><br>
 * <code>check(args).named("arguments").isNotEmpty();</code>
 * <p>
 * <strong>Note:</strong> For use case and thread safety issues, check the
 * documentation of the {@link Check} class.
 * 
 * @author Michael Faes
 */
public abstract class CompactChecks {
    
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
    
    public static UrlCheck check(final URL argument) {
        return Check.that(argument);
    }
}
