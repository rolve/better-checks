package ch.trick17.betterchecks;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import ch.trick17.betterchecks.fluent.ObjectCheck;
import ch.trick17.betterchecks.util.FormatPair;
import ch.trick17.betterchecks.util.GwtCompatible;

/**
 * Provides access to the configuration of the Better Checks library.
 * <p>
 * <strong>When compiled with GWT, all configuration is disabled. Stack trace
 * cleaning is disabled and otherwise the default values are used.</strong>
 * <p>
 * The {@link #getConfig()} method provides read access to the config singleton.
 * For more information, refer to the documentation of the
 * {@link #isCleanStackTracesEnabled()}, {@link #getDefaultArgumentName()} and
 * {@link #getMessageFormat(MessageType, boolean)} methods.
 * 
 * @author Michael Faes
 */
@GwtCompatible
public final class Config {
    
    /* Constants */
    
    /**
     * The default setting for the default argument name, which is {@value} .
     * (Yes, that is a meta-default.)
     * 
     * @see #getDefaultArgumentName()
     */
    public static final String DEFAULT_DEFAULT_ARG_NAME = "the argument";
    
    /**
     * The default setting for for stack trace cleaning, which is {@value} .
     * 
     * @see #isCleanStackTracesEnabled()
     */
    public static final boolean DEFAULT_CLEAN_STRACK_TRACES_ENABLED = true;
    
    /* The config singleton */
    private static final Config config = defaultConfig();
    
    private static Config defaultConfig() {
        final Config theConfig = new Config();
        theConfig.loadedFromConfigFile = false;
        theConfig.defaultArgumentName = DEFAULT_DEFAULT_ARG_NAME;
        theConfig.cleanStackTracesEnabled = DEFAULT_CLEAN_STRACK_TRACES_ENABLED;
        
        theConfig.messageFormats = new HashMap<MessageType, FormatPair>();
        for(final MessageType msgType : MessageType.values()) {
            final String format = msgType.getDefaultFormat();
            theConfig.messageFormats.put(msgType, new FormatPair(format));
        }
        
        return theConfig;
    }
    
    /**
     * Returns the Better Checks config singleton. It is read-only, the only way
     * to configure the library is using a properties file on the classpath.
     * 
     * @return The config singleton
     * @see Config
     */
    public static Config getConfig() {
        return config;
    }
    
    /* Config object */
    
    /* Note that a Config object is *effectively* immutable. The static config
     * is therefore thread-safe after publication in the static initializer. */
    private boolean loadedFromConfigFile;
    private boolean cleanStackTracesEnabled;
    private String defaultArgumentName;
    private Map<MessageType, FormatPair> messageFormats;
    
    private Config() {}
    
    /**
     * Indicates whether a valid config file was loaded.
     * <p>
     * Note that the config file may define as many and as few properties as
     * desired. The rest of the properties are set to the default value.
     * 
     * @return <code>true</code> iff a config file was loaded.
     */
    public boolean isLoadedFromConfigFile() {
        return loadedFromConfigFile;
    }
    
    /**
     * Indicates whether stack trace cleaning is enabled. See the documentation
     * of {@link Check} for more information.
     * <p>
     * The default value is {@value #DEFAULT_CLEAN_STRACK_TRACES_ENABLED}.
     * 
     * @return <code>true</code> if stack trace cleaning is enabled.
     */
    public boolean isCleanStackTracesEnabled() {
        return cleanStackTracesEnabled;
    }
    
    /**
     * Returns the default argument name used in exception messages. This name
     * is used when arguments are not explicitly named using the
     * {@link ObjectCheck#named(String)} modifier.
     * <p>
     * The default value (meaning the <em>default</em> default name) is
     * {@value #DEFAULT_DEFAULT_ARG_NAME}.
     * 
     * @return The default argument name for exception messages
     */
    public String getDefaultArgumentName() {
        return defaultArgumentName;
    }
    
    /**
     * Returns the exception message format for the given {@link MessageType}.
     * The formats are format strings defined by the {@link Formatter} class.
     * <p>
     * Besides the message type, a second parameter affects the exception
     * message format: Whether or not the check was inverted. The messages for
     * inverted checks are also inverted, typically with an additional or
     * missing "not".
     * <p>
     * To combine two such message formats, the library internally makes use of
     * a "meta" format that encompasses both versions. This meta format is
     * simple: message parts that should only be used in the positive
     * (non-inverted) version are enclosed in <code>+(</code> and
     * <code>)+</code>. Analogically, parts for the negative (inverted) version
     * are enclosed in <code>-(</code> and <code>)-</code>. Here's an example:
     * <p>
     * <code>%s must +(not )+be null-( (value: %s))-</code>
     * <p>
     * The positive format here is <code>%s must not be null</code> and the
     * negative version is <code>%s must be null (value: %s)</code>. In both
     * cases the first <code>%s</code> will be replaced with the argument name
     * and in the negative version, the second <code>%s</code> will be replaced
     * with the actual value of the argument.
     * <p>
     * The default meta format for each message type is given by the
     * {@link MessageType#getDefaultFormat()} method. To override a specific
     * format, add a property in the config file with a key named
     * <code><em>MESSAGE_TYPE</em>.format</code> (where
     * <code><em>MESSAGE_TYPE</em></code> is the string representation of one of
     * the {@link MessageType} enum values) and a value containing the meta
     * format for that type.
     * 
     * @param type
     *            The message type
     * @param inverted
     *            If <code>true</code>, the message format is inverted to
     *            reflect an inverted check.
     * @return The message format as a {@link Formatter} format string
     */
    public String getMessageFormat(final MessageType type,
            final boolean inverted) {
        return messageFormats.get(type).getFormat(inverted);
    }
}
