package ch.trick17.betterchecks;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public final class Config {
    
    /*
     * Constants
     */
    public static final String CONFIG_BASE_NAME = "better-checks-config";
    private static final String MSG_FORMAT_SUFFIX = ".format";
    private static final String CLEAN_STACK_TRACES_ENABLED_KEY = "cleanStackTraces";
    private static final String DEFAULT_ARG_NAME_KEY = "defaultArgumentName";
    
    /*
     * Default values
     */
    private static final String DEFAULT_DEFAULT_ARG_NAME = "the argument";
    private static final boolean DEFAULT_CLEAN_STRACK_TRACES_ENABLED = true;
    
    /*
     * Static initialization and global config access
     */
    private static final Config config = loadConfig();
    
    protected static Config loadConfig() {
        final Config theConfig = new Config();
        
        ResourceBundle bundle;
        try {
            bundle = ResourceBundle.getBundle(CONFIG_BASE_NAME);
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
    
    public static Config getConfig() {
        return config;
    }
    
    /*
     * Config object
     */
    
    /*
     * Note that a Config object is *effectively* immutable. The static config
     * is therefore thread-safe after publication in the static initializer.
     */
    private boolean cleanStackTracesEnabled;
    private String defaultArgumentName;
    private Map<MessageType, FormatPair> messageFormats;
    
    private Config() {}
    
    public boolean isCleanStackTracesEnabled() {
        return cleanStackTracesEnabled;
    }
    
    public String getDefaultArgumentName() {
        return defaultArgumentName;
    }
    
    public String getMessageFormat(final MessageType type,
            final boolean inverted) {
        return messageFormats.get(type).getFormat(inverted);
    }
    
    private static class FormatPair {
        
        private static final Pattern POSITIVE_PATTERN = Pattern
                .compile("\\+\\((.*)\\)\\+");
        private static final Pattern NEGATIVE_PATTERN = Pattern
                .compile("-\\((.*)\\)-");
        
        private final String positive;
        private final String negative;
        
        public FormatPair(final String rawFormat) {
            final String negativeRemoved = NEGATIVE_PATTERN.matcher(rawFormat)
                    .replaceAll("");
            positive = POSITIVE_PATTERN.matcher(negativeRemoved).replaceAll(
                    "$1");
            final String positiveRemoved = POSITIVE_PATTERN.matcher(rawFormat)
                    .replaceAll("");
            negative = NEGATIVE_PATTERN.matcher(positiveRemoved).replaceAll(
                    "$1");
        }
        
        public String getFormat(final boolean inverted) {
            return inverted ? negative : positive;
        }
    }
}
