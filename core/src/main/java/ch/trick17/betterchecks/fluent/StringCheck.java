package ch.trick17.betterchecks.fluent;

import static ch.trick17.betterchecks.MessageType.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import ch.trick17.betterchecks.Check;

public final class StringCheck extends ObjectBaseCheck<String, StringCheck> {
    
    public StringCheck isNotEmpty() {
        return check(arg == null || !arg.isEmpty(), ARG_EMPTY, argName);
    }
    
    public StringCheck isNotWhitespace() {
        return check(arg == null || !arg.trim().isEmpty(), ARG_WHITESPACE,
                argName);
    }
    
    public StringCheck hasLength(final int length) {
        return check(arg == null || arg.length() == length, ARG_LENGTH,
                argName, length, arg);
    }
    
    public StringCheck hasLengthBetween(final int min, final int max) {
        return check(arg == null
                || (arg.length() >= min && arg.length() <= max),
                ARG_LENGTH_BETWEEN, argName, min, max, arg);
    }
    
    public IntCheck hasLengthWhich() {
        return intPropertyCheck(arg == null ? -1 : arg.length(), "length");
    }
    
    public StringCheck startsWith(final String prefix) {
        return check(arg == null || arg.startsWith(prefix), ARG_STARTS,
                argName, prefix, arg);
    }
    
    public StringCheck endsWith(final String suffix) {
        return check(arg == null || arg.endsWith(suffix), ARG_ENDS, argName,
                suffix, arg);
    }
    
    public StringCheck contains(final CharSequence sequence) {
        return check(arg == null || arg.contains(sequence), ARG_CONTAINS,
                argName, sequence, arg);
    }
    
    public StringCheck containsAny(final CharSequence... sequences) {
        return check(arg == null || testContainsAny(sequences),
                ARG_CONTAINS_ANY, argName, Arrays.toString(sequences), arg);
    }
    
    public StringCheck containsAll(final CharSequence... sequences) {
        return check(arg == null || testContainsAll(sequences),
                ARG_CONTAINS_ALL, argName, Arrays.toString(sequences), arg);
    }
    
    public StringCheck matches(final String regex) {
        // IMPROVE: Cache compiled patterns?
        return check(arg == null || arg.matches(regex), ARG_MATCHES, argName,
                regex, arg);
    }
    
    public StringCheck isEqualTo(final String string) {
        return check(arg == null || arg.equals(string), ARG_EQUAL, argName,
                string, arg);
    }
    
    public StringCheck isUrl() {
        checkNull();
        URL url = null;
        Exception cause = null;
        try {
            url = new URL(arg);
        } catch(final MalformedURLException e) {
            cause = e;
        }
        return checkWithCause(arg == null || url != null, ARG_URL, cause,
                argName, arg);
    }
    
    public UrlCheck isUrlWhich() {
        checkNull();
        URL url = null;
        Exception cause = null;
        try {
            url = new URL(arg);
        } catch(final MalformedURLException e) {
            cause = e;
        }
        checkWithCause(arg == null || url != null, ARG_URL, cause, argName, arg);
        return Check.that(url).named(argName);
    }
    
    /*
     * Implementation methods
     */
    
    private boolean testContainsAll(final CharSequence... sequences) {
        for(final CharSequence sequence : sequences) {
            if(!arg.contains(sequence))
                return false;
        }
        return true;
    }
    
    private boolean testContainsAny(final CharSequence... sequences) {
        for(final CharSequence sequence : sequences) {
            if(arg.contains(sequence))
                return true;
        }
        return false;
    }
}
