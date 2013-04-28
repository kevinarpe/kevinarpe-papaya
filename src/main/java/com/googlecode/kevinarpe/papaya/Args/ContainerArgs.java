package com.googlecode.kevinarpe.papaya.Args;

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
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
final class ContainerArgs {

    static void _checkSizeRange(
            Object container,
            String containerType,
            int size,
            int minSize,
            int maxSize,
            String containerArgName) {
        ObjectArgs.checkNotNull(container, containerArgName);
        if (-1 != minSize && -1 != maxSize && minSize > maxSize) {
            StringArgs.checkNotEmptyOrWhitespace(containerType, "containerType");
            StringArgs._checkArgNameValid(containerArgName, "containerArgName");
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': 'minSize' > 'maxSize': %d > %d",
                containerType, containerArgName, minSize, maxSize));
        }
        if (-1 != minSize && size < minSize) {
            StringArgs.checkNotEmptyOrWhitespace(containerType, "containerType");
            StringArgs._checkArgNameValid(containerArgName, "containerArgName");
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': size < 'minSize': %d < %d",
                containerType, containerArgName, size, minSize));
        }
        if (-1 != maxSize && size > maxSize) {
            StringArgs.checkNotEmptyOrWhitespace(containerType, "containerType");
            StringArgs._checkArgNameValid(containerArgName, "containerArgName");
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': size > 'maxSize': %d > %d",
                containerType, containerArgName, size, maxSize));
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
        ObjectArgs.checkNotNull(indexArgName, "indexArgName");
        _checkIndexNotNegative(containerType, containerArgName, indexArgName, index);
        if (index >= containerSize) {
            StringArgs.checkNotEmptyOrWhitespace(containerType, "containerType");
            StringArgs._checkArgNameValid(containerArgName, "containerArgName");
            StringArgs._checkArgNameValid(indexArgName, "indexArgName");
            throw new IndexOutOfBoundsException(String.format(
                    "%s '%s': Index '%s' for access too large: %d >= %d",
                    containerType,
                    containerArgName,
                    indexArgName,
                    index,
                    containerSize));
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
        ObjectArgs.checkNotNull(indexArgName, "indexArgName");
        _checkIndexNotNegative(containerType, containerArgName, indexArgName, index);
        if (index > containerSize) {
            StringArgs.checkNotEmptyOrWhitespace(containerType, "containerType");
            StringArgs._checkArgNameValid(containerArgName, "containerArgName");
            StringArgs._checkArgNameValid(indexArgName, "indexArgName");
            throw new IndexOutOfBoundsException(String.format(
                    "%s '%s': Index '%s' for insert too large: %d > %d",
                    containerType,
                    containerArgName,
                    indexArgName,
                    index,
                    containerSize));
        }
    }

    static void _checkIndexNotNegative(
            String containerType, String containerArgName, String indexArgName, int index) {
        if (index < 0) {
            StringArgs.checkNotEmptyOrWhitespace(containerType, "containerType");
            StringArgs._checkArgNameValid(containerArgName, "containerArgName");
            StringArgs._checkArgNameValid(indexArgName, "indexArgName");
            throw new IndexOutOfBoundsException(String.format(
                "%s '%s': Index '%s' is negative: %d",
                containerType,
                containerArgName,
                indexArgName,
                index));
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
        ObjectArgs.checkNotNull(container, containerArgName);
        _checkIndexNotNegative(containerType, containerArgName, indexArgName, index);
        IntArgs.checkNotNegative(count, countArgName);
        if (index >= containerSize) {
            StringArgs.checkNotEmptyOrWhitespace(containerType, "containerType");
            StringArgs._checkArgNameValid(containerArgName, "containerArgName");
            StringArgs._checkArgNameValid(indexArgName, "indexArgName");
            throw new IndexOutOfBoundsException(String.format(
                    "%s '%s': Index '%s' too large: %d >= %d",
                    containerType,
                    containerArgName,
                    indexArgName,
                    index,
                    containerSize));
        }
        if (index + count > containerSize) {
            StringArgs.checkNotEmptyOrWhitespace(containerType, "containerType");
            StringArgs._checkArgNameValid(containerArgName, "containerArgName");
            StringArgs._checkArgNameValid(indexArgName, "indexArgName");
            StringArgs._checkArgNameValid(countArgName, "countArgName");
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
