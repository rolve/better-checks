package ch.trick17.betterchecks.fluent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MessageType;

public class UrlCheckTest {
    
    @Test
    @SuppressWarnings("null")
    public void testHasProtocol() throws MalformedURLException {
        Check.that(new URL("http://example.com")).hasProtocol("http");
        Check.that(new URL("ftp://example.com")).hasProtocol("ftp");
        
        Exception thrown = null;
        try {
            Check.that(new URL("http://example.com")).hasProtocol("ftp");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_PROTOCOL, false,
                Exceptions.defaultArgName(), "ftp", "http://example.com"),
                thrown.getMessage());
        
        thrown = null;
        try {
            Check.that((URL) null).hasProtocol("http");
        } catch(final Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(Exceptions.formatMsg(MessageType.ARG_NULL, false,
                Exceptions.defaultArgName()), thrown.getMessage());
    }
    
    @Test
    public void testHasProtocolWhich() throws MalformedURLException {
        final URL url = new URL(
                "http://rolve@example.com:8080/the-path/?the-query#the-anchor");
        Check.that(url).hasProtocolWhich().isEqualTo("http");
        Check.that(url).hasHostWhich().isEqualTo("example.com");
        Check.that(url).hasPortWhich().isEqualTo(8080);
        Check.that(url).hasFileWhich().isEqualTo("/the-path/?the-query");
        Check.that(url).hasQueryWhich().isEqualTo("the-query");
        Check.that(url).hasAuthorityWhich().isEqualTo("rolve@example.com:8080");
        Check.that(url).hasPathWhich().isEqualTo("/the-path/");
        Check.that(url).hasUserInfoWhich().isEqualTo("rolve");
        Check.that(url).hasRefWhich().isEqualTo("the-anchor");
    }
}
