package ch.trick17.betterchecks;

import java.util.Arrays;

public final class Exceptions {
    
    private Exceptions() {}
    
    private static final String BASE_PACKAGE = Check.class.getPackage()
            .getName();
    
    public static IllegalArgumentException illegalArgumentException(
            final MsgFormatId formatId, final Object... msgArgs) {
        final IllegalArgumentException exception = new IllegalArgumentException(
                formatMsg(formatId, msgArgs));
        cleanUpStackTrace(exception);
        return exception;
    }
    
    public static String formatMsg(final MsgFormatId formatId,
            final Object... msgArgs) {
        final String format = Config.getConfig().getMessageFormat(formatId);
        return String.format(format, msgArgs);
    }
    
    private static void cleanUpStackTrace(final Exception exception) {
        StackTraceElement[] trace = exception.getStackTrace();
        int minIndex = 0;
        for(int i = 0; i < trace.length; i++) {
            if(removeElement(trace[i]))
                minIndex = i + 1;
            else
                break;
        }
        if(minIndex > 0)
            trace = Arrays.copyOfRange(trace, minIndex, trace.length);
        exception.setStackTrace(trace);
    }
    
    private static boolean removeElement(final StackTraceElement element) {
        final String className = element.getClassName();
        return className.startsWith(BASE_PACKAGE)
                && !className.endsWith("Test");
    }
    
    public static String defaultArgName() {
        return Config.getConfig().getDefaultArgumentName();
    }
}
