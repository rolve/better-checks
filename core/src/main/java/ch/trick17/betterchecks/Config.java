package ch.trick17.betterchecks;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import ch.trick17.betterchecks.fluent.ObjectCheck;

/**
 * Provides access to the configuration of the Better Checks library.
 * <p>
 * As documented in the {@link Check} class, the library can only be configured
 * via a <code>better-checks-config.properties</code> file on the classpath.
 * Therefore, the runtime access to the configuration is read-only.
 * <p>
 * The config file is loaded when this class is initialized. To access the
 * config, use the static {@link #getConfig()} method that provides access to
 * the config singleton. To assert that the your config file has been loaded,
 * you may use the {@link #isLoadedFromConfigFile()} method.
 * 
 * @author Michael Faes
 */
public final class Config {
    
    /* Constants */
    
    /**
     * The base name of the config file for this library
     */
    public static final String CONFIG_BASE_NAME = "better-checks-config";
    
    private static final String MSG_FORMAT_SUFFIX = ".format";
    private static final String CLEAN_STACK_TRACES_ENABLED_KEY = "cleanStackTraces";
    private static final String DEFAULT_ARG_NAME_KEY = "defaultArgumentName";
    
    /* Default values */
    private static final String DEFAULT_DEFAULT_ARG_NAME = "the argument";
    private static final boolean DEFAULT_CLEAN_STRACK_TRACES_ENABLED = true;
    
    /* Static initialization and global config access */
    private static final Config config = loadConfig();
    
    /**
     * Loads the config from the {@link #CONFIG_BASE_NAME} file. If no such file
     * exists, the default values will be used for all properties. Otherwise,
     * the properties specified in the file will override the default ones.
     * 
     * @return The loaded config
     */
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
        
        theConfig.messageFormats = new HashMap<MessageType, Config.FormatPair>();
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
    
    // IMPROVE: Find a way to include default values from constants in Javadoc
    
    /**
     * Indicates whether stack trace cleaning is enabled. See the documentation
     * of {@link Check} for more information.
     * <p>
     * The default value is <code>true</code>. To override this, set the
     * <code>cleanStackTraces</code> property in the config file.
     * 
     * @return <code>true</code> if stack trace cleaning is enabled.
     */
    public boolean isCleanStackTracesEnabled() {
        return cleanStackTracesEnabled;
    }
    
    /**
     * Returns the default argument name used in the exception messages. This
     * name is used when arguments are not explicitly named using the
     * {@link ObjectCheck#named(String)} modifier.
     * <p>
     * The default value (meaning the <em>default</em> default name</em>) is
     * "the argument". To override this, set the
     * <code>defaultArgumentName</code> property in the config file.
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
    
    private static final class FormatPair {
        
        private static final Pattern POSITIVE_PATTERN = Pattern
                .compile("\\+\\((.*)\\)\\+");
        private static final Pattern NEGATIVE_PATTERN = Pattern
                .compile("-\\((.*)\\)-");
        
        private final String positive;
        private final String negative;
        
        FormatPair(final String rawFormat) {
            final String negativeRemoved = NEGATIVE_PATTERN.matcher(rawFormat)
                    .replaceAll("");
            positive = POSITIVE_PATTERN.matcher(negativeRemoved).replaceAll(
                    "$1");
            final String positiveRemoved = POSITIVE_PATTERN.matcher(rawFormat)
                    .replaceAll("");
            negative = NEGATIVE_PATTERN.matcher(positiveRemoved).replaceAll(
                    "$1");
        }
        
        String getFormat(final boolean inverted) {
            return inverted ? negative : positive;
        }
    }
}
