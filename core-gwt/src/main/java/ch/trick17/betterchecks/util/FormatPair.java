package ch.trick17.betterchecks.util;

@GwtCompatible
public final class FormatPair {
    
    private static final String POSITIVE_PATTERN = "\\+\\((.*)\\)\\+";
    private static final String NEGATIVE_PATTERN = "-\\((.*)\\)-";
    
    private final String positive;
    private final String negative;
    
    public FormatPair(final String rawFormat) {
        positive = rawFormat.replaceAll(NEGATIVE_PATTERN, "").replaceAll(
                POSITIVE_PATTERN, "$1");
        negative = rawFormat.replaceAll(POSITIVE_PATTERN, "").replaceAll(
                NEGATIVE_PATTERN, "$1");
    }
    
    public String getFormat(final boolean inverted) {
        return inverted ? negative : positive;
    }
}
