package ch.trick17.betterchecks;

import java.util.Collection;

import ch.trick17.betterchecks.fluent.ObjectArrayCheck;
import ch.trick17.betterchecks.fluent.CollectionCheck;
import ch.trick17.betterchecks.fluent.ObjectCheck;
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
    
    public static CollectionCheck check(final Collection<?> argument) {
        return Check.that(argument);
    }
}
