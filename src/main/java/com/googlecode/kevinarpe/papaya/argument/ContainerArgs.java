package com.googlecode.kevinarpe.papaya.argument;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

/**
 * Static methods to check array and {@link Collection} and array arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
final class ContainerArgs {

    // Disable default constructor
    private ContainerArgs() {
    }
    
    private static String _getContainerTypeWarning(String ref) {
        String x = StringArgs._getIdentifierWarning("Container type", ref, "containerType");
        return x;
    }
    
    static void _checkNotEmpty(
            Object container,
            String containerType,
            int size,
            String containerArgName) {
        ObjectArgs.checkNotNull(container, containerArgName);
        if (0 == size) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            throw new IllegalArgumentException(String.format(
                "%s argument '%s' is empty%s%s",
                containerType, containerArgName, w, w2));
        }
    }

    static void _checkSizeRange(
            Object container,
            String containerType,
            int size,
            int minSize,
            int maxSize,
            String containerArgName) {
        IntArgs.checkNotNegative(minSize, "minSize");
        IntArgs.checkNotNegative(maxSize, "maxSize");
        _checkSizeRangeCore(container, containerType, size, minSize, maxSize, containerArgName);
    }

    static void _checkMinSize(
            Object container,
            String containerType,
            int size,
            int minSize,
            String containerArgName) {
        IntArgs.checkNotNegative(minSize, "minSize");
        int maxSize = -1;
        _checkSizeRangeCore(container, containerType, size, minSize, maxSize, containerArgName);
    }

    static void _checkMaxSize(
            Object container,
            String containerType,
            int size,
            int maxSize,
            String containerArgName) {
        IntArgs.checkNotNegative(maxSize, "minSize");
        int minSize = -1;
        _checkSizeRangeCore(container, containerType, size, minSize, maxSize, containerArgName);
    }

    static void _checkExactSize(
            Object container,
            String containerType,
            int size,
            int exactSize,
            String containerArgName) {
        IntArgs.checkNotNegative(exactSize, "exactSize");
        _checkSizeRangeCore(
            container, containerType, size, exactSize, exactSize, containerArgName);
    }
    
    private static void _checkSizeRangeCore(
            Object container,
            String containerType,
            int size,
            int minSize,
            int maxSize,
            String containerArgName) {
        ObjectArgs.checkNotNull(container, containerArgName);
        if (-1 != minSize && -1 != maxSize && minSize > maxSize) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': minSize > maxSize: %d > %d%s%s",
                containerType, containerArgName, minSize, maxSize, w, w2));
        }
        if (-1 != minSize && size < minSize) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': size < minSize: %d < %d%s%s",
                containerType, containerArgName, size, minSize, w, w2));
        }
        if (-1 != maxSize && size > maxSize) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': size > maxSize: %d > %d%s%s",
                containerType, containerArgName, size, maxSize, w, w2));
        }
    }
    
    static void _checkAccessIndex(
            Object container,
            String containerType,
            int containerSize,
            int index,
            String containerArgName,
            String indexArgName) {
        ObjectArgs.checkNotNull(container, containerArgName);
        if (0 == containerSize) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            String w3 = StringArgs._getArgNameWarning(indexArgName, "indexArgName");
            throw new IllegalArgumentException(String.format(
                "%s '%s' is empty%s%s%s",
                containerType,
                containerArgName,
                w,
                w2,
                w3));
        }
        _checkIndexNotNegative(containerType, containerArgName, indexArgName, index);
        if (index >= containerSize) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            String w3 = StringArgs._getArgNameWarning(indexArgName, "indexArgName");
            throw new IndexOutOfBoundsException(String.format(
                "%s '%s': Index '%s' for access too large: %d >= %d%s%s%s",
                containerType,
                containerArgName,
                indexArgName,
                index,
                containerSize,
                w,
                w2,
                w3));
        }
    }
    
    static void _checkInsertIndex(
            Object container,
            String containerType,
            int containerSize,
            int index,
            String containerArgName,
            String indexArgName) {
        ObjectArgs.checkNotNull(container, containerArgName);
        _checkIndexNotNegative(containerType, containerArgName, indexArgName, index);
        if (index > containerSize) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            String w3 = StringArgs._getArgNameWarning(indexArgName, "indexArgName");
            throw new IndexOutOfBoundsException(String.format(
                "%s '%s': Index '%s' for insert too large: %d > %d%s%s%s",
                containerType,
                containerArgName,
                indexArgName,
                index,
                containerSize,
                w,
                w2,
                w3));
        }
    }

    static void _checkIndexNotNegative(
            String containerType, String containerArgName, String indexArgName, int index) {
        if (index < 0) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            String w3 = StringArgs._getArgNameWarning(indexArgName, "indexArgName");
            throw new IndexOutOfBoundsException(String.format(
                "%s '%s': Index '%s' is negative: %d%s%s%s",
                containerType,
                containerArgName,
                indexArgName,
                index,
                w,
                w2,
                w3));
        }
    }
    
    static void _checkIndexAndCount(
            Object container,
            String containerType,
            int containerSize,
            int index,
            int count,
            String containerArgName,
            String indexArgName,
            String countArgName) {
        _checkAccessIndex(
            container, containerType, containerSize, index, containerArgName, indexArgName);
        if (count < 0) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            String w3 = StringArgs._getArgNameWarning(countArgName, "countArgName");
          throw new IllegalArgumentException(String.format(
                "%s '%s': Count '%s' is negative: %d%s%s%s",
                containerType,
                containerArgName,
                countArgName,
                count,
                w,
                w2,
                w3));
        }
        if (index + count > containerSize) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            String w3 = StringArgs._getArgNameWarning(indexArgName, "indexArgName");
            String w4 = StringArgs._getArgNameWarning(countArgName, "countArgName");
            throw new IndexOutOfBoundsException(String.format(
                "%s '%s': Index '%s' and count '%s' too large: %d + %d > %d%s%s%s%s",
                containerType,
                containerArgName,
                indexArgName,
                countArgName,
                index,
                count,
                containerSize,
                w,
                w2,
                w3,
                w4));
        }
    }
    
    static void _checkFromAndToIndices(
            Object container,
            String containerType,
            int containerSize,
            int fromIndex,
            int toIndex,
            String containerArgName,
            String fromIndexArgName,
            String toIndexArgName) {
        _checkAccessIndex(
            container,
            containerType,
            containerSize,
            fromIndex,
            containerArgName,
            fromIndexArgName);
        _checkIndexNotNegative(containerType, containerArgName, toIndexArgName, toIndex);
        if (toIndex > containerSize) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            String w3 = StringArgs._getArgNameWarning(toIndexArgName, "toIndexArgName");
            throw new IndexOutOfBoundsException(String.format(
                "%s '%s': Index '%s' too large: %d > %d%s%s%s",
                containerType,
                containerArgName,
                toIndexArgName,
                toIndex,
                containerSize,
                w,
                w2,
                w3));
        }
        if (fromIndex > toIndex) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            String w3 = StringArgs._getArgNameWarning(toIndexArgName, "toIndexArgName");
            String w4 = StringArgs._getArgNameWarning(fromIndexArgName, "fromIndexArgName");
            throw new IndexOutOfBoundsException(String.format(
                "%s '%s': Index '%s' larger than index '%s': %d > %d%s%s%s%s",
                containerType,
                containerArgName,
                fromIndexArgName,
                toIndexArgName,
                fromIndex,
                toIndex,
                w,
                w2,
                w3,
                w4));
        }
    }
    
    static <THaystack, TNeedle extends THaystack> void _checkContains(
            Collection<THaystack> ref,
            String containerType,
            TNeedle value,
            String containerArgName) {
        ObjectArgs.checkNotNull(ref, containerArgName);
        
        if (ref.isEmpty()) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            throw new IllegalArgumentException(String.format(
                "%s '%s' is empty%s%s",
                containerType,
                containerArgName,
                w,
                w2));
        }
        
        if (!ref.contains(value)) {
            String w = _getContainerTypeWarning(containerType);
            String w2 = StringArgs._getArgNameWarning(containerArgName, "containerArgName");
            throw new IllegalArgumentException(String.format(
                "%s '%s': Value %s not valid%nValid value(s): %s%s%s",
                containerType,
                containerArgName,
                (null == value ? "null" : value),
                Joiner.on(", ").useForNull("null").join(ref),
                w,
                w2));
        }
    }
    
    static <TValue> void _checkElementsNotNull(
            Iterable<TValue> ref, String containerType, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        int count = 0;
        for (TValue item: ref) {
            if (null == item) {
                String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new NullPointerException(String.format(
                    "%s argument '%s': Item at index %d is null%s",
                    containerType, argName, count, w));
            }
            ++count;
        }
    }

    static <TValue> void _checkElementsUnique(
            Collection<TValue> ref, String containerType, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        if (ref instanceof Set) {
            return;
        }
        final int size = ref.size();
        if (0 == size) {
            return;
        }
        HashSet<TValue> uniqSet = Sets.newHashSetWithExpectedSize(size);
        HashSet<TValue> dupSet = Sets.newLinkedHashSet();
        for (TValue value : ref) {
            if (!uniqSet.add(value)) {
                dupSet.add(value);
            }
        }
        if (!dupSet.isEmpty()) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': %d duplicate entries found: %s%nCollection: %s%s",
                containerType,
                argName,
                dupSet.size(),
                _joinIterable(dupSet),
                _joinIterable(ref),
                w));
        }
    }

    static <TValue> String _joinIterable(Iterable<TValue> iter) {
        StringBuilder sb = new StringBuilder();
        for (TValue value : iter) {
            if (0 != sb.length()) {
                sb.append(", ");
            }
            if (null == value) {
                sb.append("null");
            }
            else {
                String x = "'" + value + "'";
                sb.append(x);
            }
        }
        String sbs = sb.toString();
        return sbs;
    }
}
