package ch.trick17.betterchecks;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigTest extends CustomConfig {
    
    @Test
    public void testIsLoadedFromConfigFile() {
        assertEquals(false, Config.getConfig().isLoadedFromConfigFile());
        
        useEmptyConfig();
        assertEquals(true, Config.getConfig().isLoadedFromConfigFile());
    }
    
    @Test
    public void testGetCleanStackTraces() {
        assertEquals(true, Config.getConfig().isCleanStackTracesEnabled());
        
        useEmptyConfig();
        assertEquals(true, Config.getConfig().isCleanStackTracesEnabled());
        
        useTestConfig();
        assertEquals(false, Config.getConfig().isCleanStackTracesEnabled());
    }
    
    @Test
    public void testGetDefaultArgumentName() {
        assertEquals("the argument", Config.getConfig()
                .getDefaultArgumentName());
        
        useEmptyConfig();
        assertEquals("the argument", Config.getConfig()
                .getDefaultArgumentName());
        
        useTestConfig();
        assertEquals("your argument", Config.getConfig()
                .getDefaultArgumentName());
    }
    
    @Test
    public void testGetMessageFormat() {
        assertEquals("%s must be positive (value: %s)", Config.getConfig()
                .getMessageFormat(MessageType.ARG_POSITIVE, false));
        assertEquals("%s must not be negative (value: %s)", Config.getConfig()
                .getMessageFormat(MessageType.ARG_NEGATIVE, true));
        
        useEmptyConfig();
        assertEquals("%s must be positive (value: %s)", Config.getConfig()
                .getMessageFormat(MessageType.ARG_POSITIVE, false));
        assertEquals("%s must not be negative (value: %s)", Config.getConfig()
                .getMessageFormat(MessageType.ARG_NEGATIVE, true));
        
        useTestConfig();
        assertEquals("%s should be positive", Config.getConfig()
                .getMessageFormat(MessageType.ARG_POSITIVE, false));
        assertEquals("%s should not be negative", Config.getConfig()
                .getMessageFormat(MessageType.ARG_NEGATIVE, true));
    }
}
