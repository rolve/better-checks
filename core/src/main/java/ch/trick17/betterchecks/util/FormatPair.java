package ch.trick17.betterchecks.util;

import java.util.regex.Pattern;

@GwtCompatible
public final class FormatPair {
    
    private static final Pattern POSITIVE_PATTERN = Pattern
            .compile("\\+\\((.*)\\)\\+");
    private static final Pattern NEGATIVE_PATTERN = Pattern
            .compile("-\\((.*)\\)-");
    
    private final String positive;
    private final String negative;
    
    public FormatPair(final String rawFormat) {
        final String negativeRemoved = NEGATIVE_PATTERN.matcher(rawFormat)
                .replaceAll("");
        positive = POSITIVE_PATTERN.matcher(negativeRemoved).replaceAll("$1");
        final String positiveRemoved = POSITIVE_PATTERN.matcher(rawFormat)
                .replaceAll("");
        negative = NEGATIVE_PATTERN.matcher(positiveRemoved).replaceAll("$1");
    }
    
    public String getFormat(final boolean inverted) {
        return inverted ? negative : positive;
    }
}
