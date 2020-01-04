package com.googlecode.kevinarpe.papaya.test;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

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
