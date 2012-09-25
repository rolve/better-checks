package ch.trick17.betterchecks;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class ExceptionsTest {
    
    @Test
    public void testIllegalArgumentException() {
        IllegalArgumentException exception = Exceptions
                .illegalArgumentException(MessageType.ARG_EMPTY, false,
                        new Object[] {"the argument"});
        
        assertEquals(Exceptions.formatMsg(MessageType.ARG_EMPTY, false,
                "the argument"), exception.getMessage());
        
        /*
         * Test that the stack trace is cleaned up to not contain elements from
         * the better checks library
         */
        StackTraceElement topElement = exception.getStackTrace()[0];
        assertEquals(ExceptionsTest.class.getName(), topElement.getClassName());
        assertEquals(ExceptionsTest.class.getSimpleName() + ".java", topElement
                .getFileName());
        assertEquals("testIllegalArgumentException", topElement.getMethodName());
        
        /*
         * The help() method should not appear in the stack trace either as it's
         * identified as being part of the library (it doesn't end with "Test")
         */
        exception = ExceptionsTestHelper.help();
        topElement = exception.getStackTrace()[0];
        assertEquals(ExceptionsTest.class.getName(), topElement.getClassName());
        assertEquals(ExceptionsTest.class.getSimpleName() + ".java", topElement
                .getFileName());
        assertEquals("testIllegalArgumentException", topElement.getMethodName());
    }
    
    private static class ExceptionsTestHelper {
        
        private static IllegalArgumentException help() {
            return Exceptions.illegalArgumentException(MessageType.ARG_NULL,
                    false, new Object[] {"arg"});
        }
    }
    
    @Test
    public void testFormatMsg() {
        /* Not inverted */
        assertEquals("the argument must not be null", Exceptions.formatMsg(
                MessageType.ARG_NULL, false, "the argument"));
        assertEquals("your argument must not be empty", Exceptions.formatMsg(
                MessageType.ARG_EMPTY, false, Exceptions.defaultArgName()));
        assertEquals(
                "the list must have a size between 3 and 4 (value: [abc d, hello])",
                Exceptions.formatMsg(MessageType.ARG_SIZE_BETWEEN, false,
                        new StringBuilder("the list"), 3, 4, Arrays.asList(
                                "abc d", "hello")));
        
        /* Inverted */
        assertEquals("the argument must be null", Exceptions.formatMsg(
                MessageType.ARG_NULL, true, "the argument"));
        assertEquals("your argument must be empty", Exceptions.formatMsg(
                MessageType.ARG_EMPTY, true, Exceptions.defaultArgName()));
        assertEquals(
                "the list must not have a size between 3 and 4 (value: [abc d, hello])",
                Exceptions.formatMsg(MessageType.ARG_SIZE_BETWEEN, true,
                        new StringBuilder("the list"), 3, 4, Arrays.asList(
                                "abc d", "hello")));
    }
}
