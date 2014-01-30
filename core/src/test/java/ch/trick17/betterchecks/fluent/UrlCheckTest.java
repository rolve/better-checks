package ch.trick17.betterchecks.fluent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.Exceptions;
import ch.trick17.betterchecks.MessageType;

public class UrlCheckTest {
    
    @Test
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
    public void testHasEverythingWhich() throws MalformedURLException {
        final URL url = new URL(
                "http://rolve@example.com:8080/the-path/?the-query#the-anchor");
        Check.that(url).hasProtocolWhich().is("http");
        Check.that(url).hasHostWhich().is("example.com");
        Check.that(url).hasPortWhich().is(8080);
        Check.that(url).hasFileWhich().is("/the-path/?the-query");
        Check.that(url).hasQueryWhich().is("the-query");
        Check.that(url).hasAuthorityWhich().is("rolve@example.com:8080");
        Check.that(url).hasPathWhich().is("/the-path/");
        Check.that(url).hasUserInfoWhich().is("rolve");
        Check.that(url).hasRefWhich().is("the-anchor");
    }
    
    @Test
    public void testNullHasEverythingWhich() {
        final URL url = null;
        Check.that(url).isNullOr().hasProtocolWhich().is("http");
        Check.that(url).isNullOr().hasHostWhich().is("example.com");
        Check.that(url).isNullOr().hasPortWhich().is(8080);
        Check.that(url).isNullOr().hasFileWhich().is("/the-path/?the-query");
        Check.that(url).isNullOr().hasQueryWhich().is("the-query");
        Check.that(url).isNullOr().hasAuthorityWhich().is(
                "rolve@example.com:8080");
        Check.that(url).isNullOr().hasPathWhich().is("/the-path/");
        Check.that(url).isNullOr().hasUserInfoWhich().is("rolve");
        Check.that(url).isNullOr().hasRefWhich().is("the-anchor");
    }
}
