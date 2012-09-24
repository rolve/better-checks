package ch.trick17.betterchecks;

import java.util.Arrays;

public abstract class Exceptions {
    
    private static final String BASE_PACKAGE = Check.class.getPackage()
            .getName();
    
    public static IllegalArgumentException illegalArgumentException(
            final MsgFormatId formatId, final boolean inverted,
            final Object... msgArgs) {
        final IllegalArgumentException exception = new IllegalArgumentException(
                formatMsg(formatId, inverted, msgArgs));
        cleanUpStackTrace(exception);
        return exception;
    }
    
    public static String formatMsg(final MsgFormatId formatId,
            final boolean inverted, final Object... msgArgs) {
        final String format = Config.getConfig().getMessageFormat(formatId,
                inverted);
        return String.format(format, msgArgs);
    }
    
    public static String defaultArgName() {
        return Config.getConfig().getDefaultArgumentName();
    }
    
    /*
     * Implementation methods
     */
    
    private static void cleanUpStackTrace(final Exception exception) {
        StackTraceElement[] trace = exception.getStackTrace();
        int minIndex = 0;
        for(int i = 0; i < trace.length; i++) {
            if(isBetterChecksElement(trace[i]))
                minIndex = i + 1;
            else
                break;
        }
        if(minIndex > 0)
            trace = Arrays.copyOfRange(trace, minIndex, trace.length);
        exception.setStackTrace(trace);
    }
    
    private static boolean isBetterChecksElement(final StackTraceElement element) {
        final String className = element.getClassName();
        return className.startsWith(BASE_PACKAGE)
                && !className.endsWith("Test");
    }
}
