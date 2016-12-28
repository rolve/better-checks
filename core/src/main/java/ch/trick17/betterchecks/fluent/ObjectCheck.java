package ch.trick17.betterchecks.fluent;

import java.util.Collection;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.util.GwtCompatible;

/**
 * The most general check class, which supports all non-primitive arguments.
 * However, it should be (and <em>is</em> - if you use the {@link Check} methods
 * correctly) used only for arguments of types that do not have their own, more
 * specific check class, like {@link StringCheck} for {@link String}s or
 * {@link CollectionCheck} for {@link Collection}s.
 * <p>
 * This class provides check methods that are common to all non-primitive types
 * like {@link #isNotNull()} or {@link #hasClass(Class)}.
 * 
 * @author Michael Faes
 */
@GwtCompatible
public final class ObjectCheck extends ObjectBaseCheck<Object, ObjectCheck> {
    
    /**
     * For internal use only.
     */
    public ObjectCheck(Object arg) {
        super(arg);
    }
}
