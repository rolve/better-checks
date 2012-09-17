package ch.trick17.betterchecks;

public class MsgFormatter {
    
    public static String formatMsg(final MessageFormatId formatId,
            final Object... args) {
        final String format = Config.getConfig().getMessageFormat(formatId);
        return String.format(format, args);
    }
    
    public static String defaultArgName() {
        return Config.getConfig().getDefaultArgumentName();
    }
}
