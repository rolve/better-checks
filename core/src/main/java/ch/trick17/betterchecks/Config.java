package ch.trick17.betterchecks;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class Config {
    
    /*
     * Default values
     */
    private static final String DEFAULT_DEFAULT_ARG_NAME = "argument";
    
    /*
     * Static initialization and global config access
     */
    private static final Config config;
    
    static {
        final URL configUrl = Config.class
                .getResource("/better-checks.properties");
        if(configUrl == null)
            config = createDefaultConfig();
        else
            config = loadConfig(configUrl);
    }
    
    private static Config createDefaultConfig() {
        final Config defaultConfig = new Config();
        defaultConfig.defaultArgumentName = DEFAULT_DEFAULT_ARG_NAME;
        
        final HashMap<MessageType, FormatPair> formats = new HashMap<MessageType, FormatPair>();
        for(final MessageType type : MessageType.values())
            formats.put(type, new FormatPair(type.getDefaultFormat()));
        defaultConfig.messageFormats = Collections.unmodifiableMap(formats);
        
        return defaultConfig;
    }
    
    private static Config loadConfig(final URL configUrl) {
        // TODO
        return null;
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
    private String defaultArgumentName;
    private Map<MessageType, FormatPair> messageFormats;
    
    private Config() {}
    
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
