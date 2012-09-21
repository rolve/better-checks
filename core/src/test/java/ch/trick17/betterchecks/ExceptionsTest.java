package ch.trick17.betterchecks;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class ExceptionsTest {
    
    @Test
    public void testIllegalArgumentException() {
        IllegalArgumentException exception = Exceptions
                .illegalArgumentException(MsgFormatId.ARG_EMPTY, "the argument");
        
        assertEquals(Exceptions
                .formatMsg(MsgFormatId.ARG_EMPTY, "the argument"), exception
                .getMessage());
        
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
            return Exceptions.illegalArgumentException(MsgFormatId.ARG_NULL,
                    "arg");
        }
    }
    
    @Test
    public void testFormatMsg() {
        /* Just a few examples */
        assertEquals("the argument must not be null", Exceptions.formatMsg(
                MsgFormatId.ARG_NULL, "the argument"));
        assertEquals("argument must not be empty", Exceptions.formatMsg(
                MsgFormatId.ARG_EMPTY, Exceptions.defaultArgName()));
        assertEquals(
                "the list must have a size between 3 and 4 (value: [abc d, hello])",
                Exceptions.formatMsg(MsgFormatId.ARG_SIZE_BETWEEN,
                        new StringBuilder("the list"), 3, 4, Arrays.asList(
                                "abc d", "hello")));
    }
    
}
