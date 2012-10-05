package ch.trick17.betterchecks;

/**
 * The MessageType enum define the types of exception messages that are used in
 * the exceptions thrown by the various checks of the Better Checks library.
 * <p>
 * Each message type has a default message format associated with it. Use
 * {@link #getDefaultFormat()} to get it. This default format can be overridden
 * in the config file. See the documentation of {@link Config} or {@link Check}
 * for more information about configuration of the Better Checks library and
 * {@link Config#getMessageFormat(MessageType, boolean)} to learn more about the
 * message formats.
 * 
 * @author Michael Faes
 */
public enum MessageType {
    
    ARG_NULL("%s must +(not )+be null-( (value: %s))-"),
    ARG_INSTANCE("%s must -(not )-be an instance of class %s (class: %s)"),
    ARG_CLASS("%s must -(not )-have the class %s (class: %s)"),
    ARG_EMPTY("%s must +(not )+be empty-( (value: %s))-"),
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
    ARG_CONTAINS_NULL("%s must +(not )+contain any null elements (value: %s)"),
    ARG_MATCHES("%s must -(not )-match the regex pattern \"%s\" (value: %s)"),
    ARG_URL("%s must -(not )-be a valid url (value: %s)"),
    ARG_POSITIVE("%s must -(not )-be positive (value: %s)"),
    ARG_NEGATIVE("%s must -(not )-be negative (value: %s)"),
    ARG_IS("%s must -(not )-be equal to %s (value: %s)"),
    ARG_GREATER("%s must -(not )-be greater than %s (value: %s)"),
    ARG_LESS("%s must -(not )-be less than %s (value: %s)"),
    ARG_BETWEEN("%s must -(not )-be between %s and %s (value: %s)"),
    ARG_INDEX(
            "%s must -(not )-be a valid index for a list or array of size %s (value: %s)"),
    ARG_NAN("%s must +(not )+be NaN"),
    ARG_INFINITE("%s must +(not )+be infinite (value: $s)"),
    ARG_PROTOCOL("%s must -(not )-have the protocol %s (value: %s)");
    
    private final String defaultFormat;
    
    private MessageType(final String defaultFormat) {
        this.defaultFormat = defaultFormat;
    }
    
    /**
     * Returns the default message format for this message type. It is a custom
     * "meta" format that encompasses both the positive and the negative
     * (inverted) version of the format. See
     * {@link Config#getMessageFormat(MessageType, boolean)} for more
     * information.
     * 
     * @return The default "meta" message format for this type of exception
     *         message
     */
    public String getDefaultFormat() {
        return defaultFormat;
    }
}
