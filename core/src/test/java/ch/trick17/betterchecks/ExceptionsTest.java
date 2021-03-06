package ch.trick17.betterchecks;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class ExceptionsTest extends CustomConfig {
    
    @Test
    public void testIllegalArgumentException() {
        IllegalArgumentException exception = Exceptions
                .illegalArgumentException(MessageType.ARG_EMPTY, false,
                        new Object[]{"arg"});
        
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, false, "arg"),
                exception.getMessage());
        
        /* Test that the stack trace is cleaned up to not contain elements from
         * the better checks library */
        StackTraceElement topElement = exception.getStackTrace()[0];
        assertEquals(ExceptionsTest.class.getName(), topElement.getClassName());
        assertEquals(ExceptionsTest.class.getSimpleName() + ".java", topElement
                .getFileName());
        assertEquals("testIllegalArgumentException", topElement.getMethodName());
        
        /* The help() method should not appear in the stack trace either as it's
         * identified as being part of the library (it doesn't end with "Test") */
        exception = ExceptionsTestHelper.help();
        topElement = exception.getStackTrace()[0];
        assertEquals(ExceptionsTest.class.getName(), topElement.getClassName());
        assertEquals(ExceptionsTest.class.getSimpleName() + ".java", topElement
                .getFileName());
        assertEquals("testIllegalArgumentException", topElement.getMethodName());
        
        /* Test with non-ch.trick17.betterchecks-class as well */
        exception = ch.trick17.helper.ExceptionsTestHelper.help();
        topElement = exception.getStackTrace()[0];
        assertEquals(ch.trick17.helper.ExceptionsTestHelper.class.getName(),
                topElement.getClassName());
        assertEquals(ch.trick17.helper.ExceptionsTestHelper.class
                .getSimpleName()
                + ".java", topElement.getFileName());
        assertEquals("help", topElement.getMethodName());
        
        /* Test disabling stack trace cleaning */
        useTestConfig();
        
        exception = Exceptions.illegalArgumentException(MessageType.ARG_EMPTY,
                false, new Object[]{"arg"});
        topElement = exception.getStackTrace()[0];
        assertEquals(Exceptions.class.getName(), topElement.getClassName());
        assertEquals(Exceptions.class.getSimpleName() + ".java", topElement
                .getFileName());
        assertEquals("illegalArgumentException", topElement.getMethodName());
    }
    
    @Test
    public void testIllegalArgumentExceptionCleanStackTracesOff() {}
    
    private static class ExceptionsTestHelper {
        
        private static IllegalArgumentException help() {
            return Exceptions.illegalArgumentException(MessageType.ARG_NULL,
                    false, new Object[]{"arg"});
        }
    }
    
    @Test
    public void testFormatMsg() {
        /* Not inverted */
        assertEquals("arg must not be null", Exceptions.formatMsg(
                MessageType.ARG_NULL, false, "arg"));
        assertEquals("the argument must not be empty", Exceptions.formatMsg(
                MessageType.ARG_EMPTY, false, Exceptions.defaultArgName()));
        assertEquals(
                "the list must have a size between 3 and 4 (value: [abc d, hello])",
                Exceptions.formatMsg(MessageType.ARG_SIZE_BETWEEN, false,
                        new StringBuilder("the list"), 3, 4, Arrays.asList(
                                "abc d", "hello")));
        
        /* Inverted */
        assertEquals("arg must be null (value: hello)", Exceptions.formatMsg(
                MessageType.ARG_NULL, true, "arg", "hello"));
        assertEquals("the argument must be empty (value: [1, 2, 3])",
                Exceptions.formatMsg(MessageType.ARG_EMPTY, true, Exceptions
                        .defaultArgName(), Arrays.asList(1, 2, 3)));
        assertEquals(
                "the list must not have a size between 3 and 4 (value: [abc d, hello])",
                Exceptions.formatMsg(MessageType.ARG_SIZE_BETWEEN, true,
                        new StringBuilder("the list"), 3, 4, Arrays.asList(
                                "abc d", "hello")));
    }
}
