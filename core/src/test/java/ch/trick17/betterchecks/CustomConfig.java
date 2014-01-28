package ch.trick17.betterchecks;

import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

import org.junit.Rule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

@PrepareForTest(Config.class)
public class CustomConfig {
    
    private static final String TEST_CONFIG = "cleanStackTraces = false\n"
            + "defaultArgumentName = your argument\n"
            + "ARG_POSITIVE.format = %s should -(not )-be positive\n"
            + "ARG_NEGATIVE.format = %s should -(not )-be negative";
    protected static final File CONFIG_FILE;
    
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
    
    public static void useTestConfig() {
        try {
            /* Write test config file */
            final Writer writer = new FileWriter(CONFIG_FILE);
            writer.append(TEST_CONFIG);
            writer.close();
            ResourceBundle.clearCache();
            
            /* Load it */
            final Config testConfig = Config.loadConfig();
            
            /* Stub it into Config class */
            spy(Config.class);
            when(Config.getConfig()).thenReturn(testConfig);
        } catch(final IOException e) {
            throw new RuntimeException(e);
        } finally {
            /* Clear config file after loading for future runs */
            try {
                new FileWriter(CONFIG_FILE).close();
            } catch(final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @Rule public PowerMockRule powerMockRule = new PowerMockRule();
}
