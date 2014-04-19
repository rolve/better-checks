package ch.trick17.betterchecks.fluent;

import ch.trick17.betterchecks.Check;
import ch.trick17.betterchecks.CompactChecks;
import ch.trick17.betterchecks.util.GwtCompatible;

/**
 * Manages the check objects returned by the {@link Check} and
 * {@link CompactChecks} classes. It is intended only for internal use. To
 * obtain check objects, always use the methods of the two mentioned classes.
 * <p>
 * {@link ThreadLocal}s are used to store the check objects internally.
 * Therefore, all check objects are bound to the life cycle of their thread.
 * 
 * @author Michael Faes
 */
@GwtCompatible
public final class FluentChecks {
    
    private FluentChecks() {}
    
    /**
     * Returns the check object of the given class, with the given argument
     * "imprinted" and the rest of the state reset. Only checks that are
     * subclasses of {@link ObjectCheck} and not {@link PrimitiveArrayCheck} are
     * supported. Because arguments with a primitive type or a primitive array
     * type cannot be handled with generics, they have their own methods.
     * 
     * @param checkClass
     *            The class of the desired check object
     * @param argument
     *            The argument to check
     * @return The check object of the given type for the current thread
     */
    public static <T, C extends ObjectBaseCheck<T, C>> C getObjectCheck(
            final Class<C> checkClass, final T argument) {
        final C check = getCheck(checkClass);
        check.reset(argument);
        return check;
    }
    
    /**
     * Returns the {@link PrimitiveArrayCheck} object with the given argument
     * and array length "imprinted" and the rest of the state reset.
     * 
     * @param argument
     *            The primitive array argument to check
     * @param argumentLength
     *            The length of the array
     * @return The primitive array check object for the current thread
     */
    public static PrimitiveArrayCheck getPrimitiveArrayCheck(
            final Object argument, final int argumentLength) {
        final PrimitiveArrayCheck check = getCheck(PrimitiveArrayCheck.class);
        check.reset(argument, argumentLength);
        return check;
    }
    
    /**
     * Returns the {@link IntCheck} object with the given argument "imprinted"
     * and the rest of the state reset.
     * 
     * @param argument
     *            The <code>int</code> argument to check
     * @return The <code>int</code> check object for the current thread
     */
    public static IntCheck getIntCheck(final int argument) {
        final IntCheck check = getCheck(IntCheck.class);
        check.reset(argument);
        return check;
    }
    
    /**
     * Returns the {@link LongCheck} object with the given argument "imprinted"
     * and the rest of the state reset.
     * 
     * @param argument
     *            The <code>long</code> argument to check
     * @return The <code>long</code> check object for the current thread
     */
    public static LongCheck getLongCheck(final long argument) {
        final LongCheck check = getCheck(LongCheck.class);
        check.reset(argument);
        return check;
    }
    
    /**
     * Returns the {@link DoubleCheck} object with the given argument
     * "imprinted" and the rest of the state reset.
     * 
     * @param argument
     *            The <code>double</code> argument to check
     * @return The <code>double</code> check object for the current thread
     */
    public static DoubleCheck getDoubleCheck(final double argument) {
        final DoubleCheck check = getCheck(DoubleCheck.class);
        check.reset(argument);
        return check;
    }
    
    @SuppressWarnings("unchecked")
    private static <C extends BaseCheck<C>> C getCheck(final Class<C> checkClass) {
        if(checkClass == CollectionCheck.class)
            return (C) new CollectionCheck();
        if(checkClass == DoubleCheck.class)
            return (C) new DoubleCheck();
        if(checkClass == IntCheck.class)
            return (C) new IntCheck();
        if(checkClass == LongCheck.class)
            return (C) new LongCheck();
        if(checkClass == NumberCheck.class)
            return (C) new NumberCheck();
        if(checkClass == ObjectArrayCheck.class)
            return (C) new ObjectArrayCheck();
        if(checkClass == ObjectCheck.class)
            return (C) new ObjectCheck();
        if(checkClass == PrimitiveArrayCheck.class)
            return (C) new PrimitiveArrayCheck();
        if(checkClass == StringCheck.class)
            return (C) new StringCheck();
        
        throw new AssertionError("Unhandled check class");
    }
}
