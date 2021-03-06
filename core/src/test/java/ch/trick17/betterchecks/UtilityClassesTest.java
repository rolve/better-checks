package ch.trick17.betterchecks;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import net.trajano.commons.testing.UtilityClassTestUtil;

@RunWith(Parameterized.class)
public class UtilityClassesTest {
    
    @Parameters(name = "{0}")
    public static List<?> parameters() {
        return Arrays.asList(new Object[]{Check.class},
                new Object[]{CompactChecks.class},
                new Object[]{Exceptions.class});
    }
    
    public UtilityClassesTest(final Class<?> utilClass) {
        this.utilClass = utilClass;
    }
    
    private final Class<?> utilClass;
    
    @Test
    public void testUtilityClassWellDefined() {
        UtilityClassTestUtil.assertUtilityClassWellDefined(utilClass);
    }
}
