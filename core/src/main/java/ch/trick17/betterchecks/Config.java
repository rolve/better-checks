package ch.trick17.betterchecks;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import ch.trick17.betterchecks.fluent.ObjectCheck;
import ch.trick17.betterchecks.util.FormatPair;
import ch.trick17.betterchecks.util.GwtCompatible;
import ch.trick17.betterchecks.util.GwtIncompatible;

/**
 * Provides access to the configuration of the Better Checks library.
 * <p>
 * As documented in the {@link Check} class, the library can only be configured
 * via a config file on the classpath. The file must be named
 * <code>{@value #CONFIG_BASE_NAME}.properties</code> (without the quotes, of
 * course!) and must be located in the classpath root. The config file is loaded
 * via {@link ResourceBundle}s, so localization is supported. However, it is
 * questionable to display exception messages directly to the user, so this
 * might not be commonly used.
 * <p>
 * Here is what a config file might look like:
 * 
 * <pre>
 * cleanStackTraces = false
 * defaultArgumentName = le argument
 * ARG_POSITIVE.format = %s should -(not )-be positive
 * ARG_NEGATIVE.format = %s should -(not )-be negative
 * </pre>
 * 
 * The {@link #getConfig()} method provides read access to the config singleton.
 * To assert that the your config file has been loaded, you may use the
 * {@link #isLoadedFromConfigFile()} method.
 * <p>
 * For more information, refer to the documentation of the
 * {@link #isCleanStackTracesEnabled()}, {@link #getDefaultArgumentName()} and
 * {@link #getMessageFormat(MessageType, boolean)} methods.
 * 
 * @author Michael Faes
 */
@GwtCompatible
public final class Config {
    
    /* Constants */
    
    /** The base name of the config file for this library: <code>{@value}</code> */
    @GwtIncompatible("Configuration not supported with GWT") public static final String CONFIG_BASE_NAME = "better-checks-config";
    
    /**
     * The key for disabling stack trace cleaning in the config file:
     * <code>{@value}</code> (without quotes).
     * 
     * @see #isCleanStackTracesEnabled()
     */
    @GwtIncompatible("Configuration not supported with GWT") public static final String CLEAN_STACK_TRACES_ENABLED_KEY = "cleanStackTraces";
    
    /**
     * The key for setting the default argument name in the config file:
     * <code>{@value}</code> (without quotes).
     * 
     * @see #getDefaultArgumentName()
     */
    @GwtIncompatible("Configuration not supported with GWT") public static final String DEFAULT_ARG_NAME_KEY = "defaultArgumentName";
    
    private static final String MSG_FORMAT_SUFFIX = ".format";
    
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
    private static final Config config = loadConfig();
    
    /**
     * Loads the config from the {@link #CONFIG_BASE_NAME} file. If no such file
     * exists, the default values will be used for all properties. Otherwise,
     * the properties specified in the file will override the default ones.
     * 
     * @return The loaded config
     */
    @GwtIncompatible("Configuration not supported with GWT")
    static Config loadConfig() {
        final Config theConfig = new Config();
        
        ResourceBundle bundle;
        try {
            bundle = ResourceBundle.getBundle(CONFIG_BASE_NAME);
            theConfig.loadedFromConfigFile = true;
        } catch(final MissingResourceException e) {
            bundle = null;
        }
        
        theConfig.defaultArgumentName = getFromBundle(bundle,
                DEFAULT_ARG_NAME_KEY, DEFAULT_DEFAULT_ARG_NAME);
        theConfig.cleanStackTracesEnabled = getFromBundle(bundle,
                CLEAN_STACK_TRACES_ENABLED_KEY,
                DEFAULT_CLEAN_STRACK_TRACES_ENABLED);
        
        theConfig.messageFormats = new HashMap<MessageType, FormatPair>();
        for(final MessageType msgType : MessageType.values()) {
            final String key = msgType.name() + MSG_FORMAT_SUFFIX;
            final String format = getFromBundle(bundle, key, msgType
                    .getDefaultFormat());
            theConfig.messageFormats.put(msgType, new FormatPair(format));
        }
        
        return theConfig;
    }
    
    private static String getFromBundle(final ResourceBundle bundle,
            final String key, final String defaultValue) {
        if(bundle == null)
            return defaultValue;
        else
            try {
                return bundle.getString(key);
            } catch(final MissingResourceException e) {
                return defaultValue;
            }
    }
    
    private static boolean getFromBundle(final ResourceBundle bundle,
            final String key, final boolean defaultValue) {
        if(bundle == null)
            return defaultValue;
        else
            try {
                return Boolean.parseBoolean(bundle.getString(key));
            } catch(final MissingResourceException e) {
                return defaultValue;
            }
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
     * The default value is {@value #DEFAULT_CLEAN_STRACK_TRACES_ENABLED}. To
     * override this, set the
     * <code>{@value #CLEAN_STACK_TRACES_ENABLED_KEY}</code> property in the
     * config file.
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
     * The default value (meaning the <em>default</em> default name</em>) is
     * {@value #DEFAULT_DEFAULT_ARG_NAME}. To override this, set the
     * <code>{@value #DEFAULT_ARG_NAME_KEY}</code> property in the config file.
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
