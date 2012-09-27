package ch.trick17.betterchecks;

import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

public class ConfigTest {
    
    private static final String TEST_CONFIG = "defaultArgumentName = your argument\n"
            + "ARG_POSITIVE.format = %s should -(not )-be positive\n"
            + "ARG_NEGATIVE.format = %s should -(not )-be negative";
    
    private static final File CONFIG_FILE;
    
    static {
        final String resourceName = "/" + Config.CONFIG_BASE_NAME
                + ".properties";
        try {
            CONFIG_FILE = new File(ConfigTest.class.getResource(resourceName)
                    .toURI());
        } catch(final URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Before
    public void clearConfigFile() throws IOException {
        new FileWriter(CONFIG_FILE).close();
        ResourceBundle.clearCache();
    }
    
    @SuppressWarnings("resource")
    private static void writeTestConfig() throws IOException {
        new FileWriter(CONFIG_FILE).append(TEST_CONFIG).close();
        ResourceBundle.clearCache();
    }
    
    @Test
    public void testGetDefaultArgumentName() throws IOException {
        assertEquals("the argument", Config.loadConfig()
                .getDefaultArgumentName());
        
        writeTestConfig();
        
        assertEquals("your argument", Config.loadConfig()
                .getDefaultArgumentName());
    }
    
    @Test
    public void testGetMessageFormat() throws IOException {
        Config config = Config.loadConfig();
        assertEquals("%s must be positive (value: %s)", config
                .getMessageFormat(MessageType.ARG_POSITIVE, false));
        assertEquals("%s must not be negative (value: %s)", config
                .getMessageFormat(MessageType.ARG_NEGATIVE, true));
        
        writeTestConfig();
        
        config = Config.loadConfig();
        assertEquals("%s should be positive", config.getMessageFormat(
                MessageType.ARG_POSITIVE, false));
        assertEquals("%s should not be negative", config.getMessageFormat(
                MessageType.ARG_NEGATIVE, true));
    }
}
