package ch.trick17.betterchecks;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Config {
    
    /*
     * Default values
     */
    private static final String DEFAULT_DEFAULT_ARG_NAME = "arg";
    
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
        
        final HashMap<MsgFormatId, String> formats = new HashMap<MsgFormatId, String>();
        for(final MsgFormatId type : MsgFormatId.values())
            formats.put(type, type.getDefaultFormat());
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
    private Map<MsgFormatId, String> messageFormats;
    
    private Config() {}
    
    public String getDefaultArgumentName() {
        return defaultArgumentName;
    }
    
    public String getMessageFormat(final MsgFormatId type) {
        return messageFormats.get(type);
    }
}
