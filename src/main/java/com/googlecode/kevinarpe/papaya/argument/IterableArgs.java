package com.googlecode.kevinarpe.papaya.argument;

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;

public final class IterableArgs {

    // Disable default constructor
    private IterableArgs() {
    }

    /**
     * Tests if a {@link Iterable} reference is not null and each element is not null.  An empty
     * iterable will pass this test.
     * 
     * @param ref
     *        an iterable reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated iterable reference
     *
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see ArrayArgs#checkElementsNotNull(Object[], String)
     * @see CollectionArgs#checkElementsNotNull(Object[], String)
     *
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     */
    @NotFullyTested
    public static <TValue, TIterable extends Iterable<TValue>>
    TIterable checkElementsNotNull(TIterable ref, String argName) {
        ContainerArgs._checkElementsNotNull(ref, "Iterable", argName);
        return ref;
    }
}
