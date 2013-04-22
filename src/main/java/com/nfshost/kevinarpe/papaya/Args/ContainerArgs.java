/**
 * Copyright 2013 Kevin Connor ARPE
 * 
 * This file is part of Papaya.
 * 
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nfshost.kevinarpe.papaya.Args;

final class ContainerArgs {

    static void _staticCheckSizeRange(
            Object container,
            String containerType,
            int size,
            int minSize,
            int maxSize,
            String containerArgName) {
        ObjectArgs.staticCheckNotNull(container, containerArgName);
        if (-1 != minSize && -1 != maxSize && minSize > maxSize) {
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': 'minSize' > 'maxSize': %d > %d",
                containerType, containerArgName, minSize, maxSize));
        }
        if (-1 != minSize && size < minSize) {
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': size < 'minSize': %d < %d",
                containerType, containerArgName, size, minSize));
        }
        if (-1 != maxSize && size > maxSize) {
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': size > 'maxSize': %d > %d",
                containerType, containerArgName, size, maxSize));
        }
    }
    
    static void _staticCheckAccessIndex(
            Object container,
            String containerType,
            int containerSize,
            int index,
            String containerArgName,
            String indexArgName) {
        ObjectArgs.staticCheckNotNull(container, containerArgName);
        ObjectArgs.staticCheckNotNull(indexArgName, "indexArgName");
        _staticCheckIndexNotNegative(containerType, containerArgName, indexArgName, index);
        if (index >= containerSize) {
            throw new IndexOutOfBoundsException(String.format(
                    "%s '%s': Index '%s' for access too large: %d >= %d",
                    containerType,
                    containerArgName,
                    indexArgName,
                    index,
                    containerSize));
        }
    }
    
    static void _staticCheckInsertIndex(
            Object container,
            String containerType,
            int containerSize,
            int index,
            String containerArgName,
            String indexArgName) {
        ObjectArgs.staticCheckNotNull(container, containerArgName);
        ObjectArgs.staticCheckNotNull(indexArgName, "indexArgName");
        _staticCheckIndexNotNegative(containerType, containerArgName, indexArgName, index);
        if (index > containerSize) {
            throw new IndexOutOfBoundsException(String.format(
                    "%s '%s': Index '%s' for insert too large: %d > %d",
                    containerType,
                    containerArgName,
                    indexArgName,
                    index,
                    containerSize));
        }
    }

    static void _staticCheckIndexNotNegative(
            String containerType, String containerArgName, String indexArgName, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format(
                "%s '%s': Index '%s' is negative: %d",
                containerType,
                containerArgName,
                indexArgName,
                index));
        }
    }
    
    static void _staticCheckIndexAndCount(
            Object container,
            String containerType,
            int containerSize,
            int index,
            int count,
            String containerArgName,
            String indexArgName,
            String countArgName) {
        ObjectArgs.staticCheckNotNull(container, containerArgName);
        ObjectArgs.staticCheckNotNull(indexArgName, "indexArgName");
        _staticCheckIndexNotNegative(containerType, containerArgName, indexArgName, index);
        IntArgs.staticCheckNotNegative(count, countArgName);
        if (index >= containerSize) {
            throw new IndexOutOfBoundsException(String.format(
                    "%s '%s': Index '%s' too large: %d >= %d",
                    containerType,
                    containerArgName,
                    indexArgName,
                    index,
                    containerSize));
        }
        if (index + count > containerSize) {
            throw new IndexOutOfBoundsException(String.format(
                    "%s '%s': Index '%s' and count '%s' too large: %d + %d > %d",
                    containerType,
                    containerArgName,
                    indexArgName,
                    countArgName,
                    index,
                    count,
                    containerSize));
        }
    }
}
