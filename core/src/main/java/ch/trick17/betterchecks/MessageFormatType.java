package ch.trick17.betterchecks;

public enum MessageFormatType {
    
    ARG_NULL("%s must not be null"),
    ARG_EMPTY("%s must not be null or empty");
    
    private final String defaultFormat;
    
    private MessageFormatType(final String defaultFormat) {
        this.defaultFormat = defaultFormat;
    }
    
    public String getDefaultFormat() {
        return defaultFormat;
    }
}
