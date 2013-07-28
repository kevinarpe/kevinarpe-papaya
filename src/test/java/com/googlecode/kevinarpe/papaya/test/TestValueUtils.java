package com.googlecode.kevinarpe.papaya.test;

import java.util.Collection;

import com.google.common.base.Joiner;
import com.googlecode.kevinarpe.papaya.StringUtils;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;

public final class TestValueUtils {

    // Disable default constructor
    private TestValueUtils() {
    }
    
    @NotFullyTested
    public static <T> T findFirstUnusedValue(
            Collection<T> availableValueCollection, Collection<T> usedValueCollection) {
        
        CollectionArgs.checkNotEmptyAndElementsNotNull(
            availableValueCollection, "availableValueCollection");
        
        CollectionArgs.checkNotEmptyAndElementsNotNull(
            usedValueCollection, "usedValueCollection");
        
        for (T usedValue: usedValueCollection) {
            if (!availableValueCollection.contains(usedValue)) {
                String msg = String.format(
                    "All used values must also appear in available list, but at least one value does not:"
                    + "%nValue: %s"
                    + "%nAvailable: %s"
                    + "%nUsed: %s",
                    usedValue,
                    joinCollection(availableValueCollection),
                    joinCollection(usedValueCollection));
                throw new IllegalArgumentException(msg);
            }
        }
        for (T availableValue: availableValueCollection) {
            if (!usedValueCollection.contains(availableValue)) {
                return availableValue;
            }
        }
        String msg = String.format(
            "Failed to find first unused value:"
            + "%nAvailable: %s"
            + "%nUsed: %s",
            joinCollection(availableValueCollection),
            joinCollection(usedValueCollection));
        throw new IllegalArgumentException(msg);
    }
    
    private static <T> String joinCollection(Collection<T> c) {
        String x = "<" + Joiner.on(" >< ").join(c) + ">";
        x = x.replace(StringUtils.NEW_LINE, " ");
        return x;
    }
}
