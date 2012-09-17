package ch.trick17.betterchecks;

public enum MessageFormatId {
    
    ARG_NULL("%s must not be null"),
    ARG_EMPTY("%s must not be empty"),
    ARG_WHITESPACE("%s must not consist of whitespace only"),
    ARG_LENGTH("%s must have a length of %d (value: %s)"),
    ARG_STARTS("%s must start with \"%s\" (value: %s)"),
    ARG_ENDS("%s must end with \"%s\" (value: %s)"),
    ARG_MATCHES("%s must match the regex pattern \"%s\" (value: %s)");
    
    private final String defaultFormat;
    
    private MessageFormatId(final String defaultFormat) {
        this.defaultFormat = defaultFormat;
    }
    
    public String getDefaultFormat() {
        return defaultFormat;
    }
}
