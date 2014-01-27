package ch.trick17.betterchecks.fluent;

import java.util.Collection;

import ch.trick17.betterchecks.Check;

/**
 * The most general check class, which supports all non-primitive arguments.
 * However, it should be (and <em>is</em> - if you use the {@link Check} methods
 * correctly) used only for arguments of types that do not have their own, more
 * specific check class, like {@link StringCheck} for {@link String}s or
 * {@link CollectionCheck} for {@link Collection}s.
 * <p>
 * This class provides check methods that are common to all non-primitive types
 * like {@link #isNotNull()}, {@link #isInstanceOf(Class)} or
 * {@link #hasClass(Class)}.
 * 
 * @author Michael Faes
 */
public final class ObjectCheck extends ObjectBaseCheck<Object, ObjectCheck> {
    
    /**
     * Default constructor, for internal use only.
     */
    public ObjectCheck() {}
}
