package com.googlecode.kevinarpe.papaya.args;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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
            throw new IndexOutOfBoundsException(String.format(
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
}
