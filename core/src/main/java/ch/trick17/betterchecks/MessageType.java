package ch.trick17.betterchecks;

public enum MessageType {
    
    ARG_NULL("%s must +(not )+be null"),
    ARG_INSTANCE("%s must -(not )-be an instance of class %s (class: %s)"),
    ARG_CLASS("%s must -(not )-have the class %s (class: %s)"),
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
    ARG_CONTAINS("%s must -(not )-contain \"%s\" (value: %s)"),
    ARG_CONTAINS_ANY(
            "%s must -(not )-contain any of the following sequences: %s (value: %s)"),
    ARG_CONTAINS_ALL(
            "%s must -(not )-contain all of the following sequences: %s (value: %s)"),
    ARG_MATCHES("%s must -(not )-match the regex pattern \"%s\" (value: %s)"),
    ARG_URL("%s must -(not )-be a valid url (value: %s)"),
    ARG_POSITIVE("%s must -(not )-be positive (value: %s)"),
    ARG_NEGATIVE("%s must -(not )-be negative (value: %s)"),
    ARG_EQUAL("%s must -(not )-be equal to %s (value: %s)"),
    ARG_GREATER("%s must -(not )-be greater than %s (value: %s)"),
    ARG_LESS("%s must -(not )-be less than %s (value: %s)"),
    ARG_BETWEEN("%s must -(not )-be between %s and %s (value: %s)"),
    ARG_PROTOCOL("%s must -(not )-have the protocol %s (value: %s)");
    
    private final String defaultFormat;
    
    private MessageType(final String defaultFormat) {
        this.defaultFormat = defaultFormat;
    }
    
    public String getDefaultFormat() {
        return defaultFormat;
    }
}
