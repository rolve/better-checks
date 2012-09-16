package ch.trick17.betterchecks;

import static ch.trick17.betterchecks.MessageFormatType.ARG_EMPTY;
import static ch.trick17.betterchecks.MessageFormatType.ARG_NULL;

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
            throw illegalArgException(ARG_NULL, defaultArgName());
    }
    
    public static void notNull(final Object arg, final String argName) {
        if(arg == null)
            throw illegalArgException(ARG_NULL, argName);
    }
    
    public static void notEmpty(final String arg) {
        if(arg == null || arg.isEmpty())
            throw illegalArgException(ARG_EMPTY, defaultArgName());
    }
    
    public static void notEmpty(final String arg, final String argName) {
        if(arg == null || arg.isEmpty())
            throw illegalArgException(ARG_EMPTY, argName);
    }
    
    /*
     * Helper methods
     */
    
    private static IllegalArgumentException illegalArgException(
            final MessageFormatType type, final Object... args) {
        final String message = formatMessage(type, args);
        return new IllegalArgumentException(message);
    }
    
    private static String formatMessage(final MessageFormatType msgType,
            final Object... args) {
        final String format = Config.getConfig().getMessageFormat(msgType);
        return String.format(format, args);
    }
    
    private static String defaultArgName() {
        return Config.getConfig().getDefaultArgumentName();
    }
}
