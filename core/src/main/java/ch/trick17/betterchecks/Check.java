package ch.trick17.betterchecks;

import static ch.trick17.betterchecks.MsgFormatId.ARG_EMPTY;
import static ch.trick17.betterchecks.MsgFormatId.ARG_NULL;
import static ch.trick17.betterchecks.MsgFormatter.defaultArgName;
import static ch.trick17.betterchecks.MsgFormatter.formatMsg;

import java.util.Collection;

import ch.trick17.betterchecks.fluent.CollectionCheck;
import ch.trick17.betterchecks.fluent.FluentChecks;
import ch.trick17.betterchecks.fluent.ObjectCheck;
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
        return FluentChecks.newClassCheck(ObjectCheck.class, argument);
    }
    
    public static StringCheck that(final String argument) {
        return FluentChecks.newClassCheck(StringCheck.class, argument);
    }
    
    public static CollectionCheck that(final Collection<?> argument) {
        return FluentChecks.<Collection<?>, CollectionCheck> newClassCheck(
                CollectionCheck.class, argument);
    }
}
