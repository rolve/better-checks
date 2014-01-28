package ch.trick17.helper;

import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MessageType;

public class ExceptionsTestHelper {
    
    public static IllegalArgumentException help() {
        return Exceptions.illegalArgumentException(MessageType.ARG_NULL, false,
                new Object[]{"arg"});
    }
    
}
