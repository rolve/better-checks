package ch.trick17.betterchecks;

public enum MsgFormatId {
    
    ARG_NULL("%s must +(not )+be null"),
    ARG_EMPTY("%s must +(not )+be empty"),
    ARG_WHITESPACE("%s must +(not )+consist of whitespace only"),
    ARG_LENGTH("%s must -(not )-have a length of %d (value: %s)"),
    ARG_SIZE("%s must -(not )-have a size of %d (value: %s)"),
    ARG_LENGTH_BETWEEN(
            "%s must -(not )-have a length between %d and %d (value: %s)"),
    ARG_SIZE_BETWEEN(
            "%s must -(not )-have a size between %d and %d (value: %s)"),
    ARG_STARTS("%s must -(not )-start with \"%s\" (value: %s)"),
    ARG_ENDS("%s must -(not )-end with \"%s\" (value: %s)"),
    ARG_MATCHES("%s must -(not )-match the regex pattern \"%s\" (value: %s)"),
    ARG_POSITIVE("%s must -(not )-be positive (value: %s)"),
    ARG_NEGATIVE("%s must -(not )-be negative (value: %s)");
    
    private final String defaultFormat;
    
    private MsgFormatId(final String defaultFormat) {
        this.defaultFormat = defaultFormat;
    }
    
    public String getDefaultFormat() {
        return defaultFormat;
    }
}
