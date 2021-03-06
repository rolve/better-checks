package ch.trick17.betterchecks;

import ch.trick17.betterchecks.util.GwtCompatible;

/**
 * The MessageType enum defines the types of exception messages that are used in
 * the exceptions thrown by the various checks of the Better Checks library.
 * <p>
 * So far, the only thing associated with a message type is the message format.
 * Use {@link #getDefaultFormat()} to get the default format. It can be
 * overridden in the config file. See the documentation of {@link Config} or
 * {@link Check} for more information about configuration of the Better Checks
 * library and {@link Config#getMessageFormat(MessageType, boolean)} to learn
 * more about message formats.
 * 
 * @author Michael Faes
 */
@GwtCompatible
public enum MessageType {
    
    ARG_NULL("%s must +(not )+be null-( (value: %s))-"),
    ARG_SAME_AS("%s must -(not )-be the same as %s (value: %s)"),
    ARG_EQUAL_TO("%s must -(not )-be equal to %s (value: %s)"),
    ARG_INSTANCE_OF("%s must -(not )-be an instance of class %s (class: %s)"),
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
    ARG_CONTAINS_NULL_KEY("%s must +(not )+contain the null key (value: %s)"),
    ARG_CONTAINS_NULL_VALUES("%s must +(not )+contain any null values (value: %s)"),
    ARG_MATCHES("%s must -(not )-match the regex pattern \"%s\" (value: %s)"),
    ARG_URL("%s must -(not )-be a valid url (value: %s)"),
    ARG_INT("%s must -(not )-be a valid integer between " + Integer.MIN_VALUE
            + " and " + Integer.MAX_VALUE + " (value: %s)"),
    ARG_POSITIVE("%s must -(not )-be positive (value: %s)"),
    ARG_NEGATIVE("%s must -(not )-be negative (value: %s)"),
    ARG_IS("%s must -(not )-be equal to %s (value: %s)"),
    ARG_GREATER("%s must -(not )-be greater than %s (value: %s)"),
    ARG_LESS("%s must -(not )-be less than %s (value: %s)"),
    ARG_BETWEEN("%s must -(not )-be between %s and %s (value: %s)"),
    ARG_INDEX(
            "%s must -(not )-be a valid index for a list or array of size %s (value: %s)"),
    ARG_NAN("%s must +(not )+be NaN"),
    ARG_NUMBER("%s must -(not )-be a number (+(not )+NaN)"),
    ARG_FINITE("%s must -(not )-be finite (value: %s)"),
    ARG_INFINITE("%s must +(not )+be infinite (value: %s)"),
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
