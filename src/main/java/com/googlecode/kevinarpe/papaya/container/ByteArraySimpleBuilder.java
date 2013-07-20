package com.googlecode.kevinarpe.papaya.container;

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

import java.io.InputStream;
import java.util.Arrays;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.IntArgs;

/**
 * Very simple class to build a byte array: {@code byte[]}.  Use this class when reading bytes from
 * an {@link InputStream}.
 * <p>
 * This class is intentionally very, very basic.  In the future, it may grow if necessary.
 * <p>
 * As of now, methods {@link Object#equals(Object)} and {@link Object#hashCode()} are not
 * implemented in this class.  These methods use the default implementation from {@link Object}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see InputStream
 */
@FullyTested
public class ByteArraySimpleBuilder {
        
    private byte[] _byteArr;
    private int _usedLength;
    
    /**
     * Constructs a new builder.  The capacity will be automatically increased later as necessary.
     * 
     * @param initialCapacity
     * <ul>
     *   <li>initial number of bytes of capacity, e.g., 8196 (2^13)</li>
     *   <li>must be positive (smallest allowed is one)</li>
     * </ul>
     * 
     * @throws IllegalArgumentException
     *         if {@code initialCapacity} is not positive
     * 
     * @see #length()
     * @see #append(byte[], int, int)
     * @see #toArray()
     */
    public ByteArraySimpleBuilder(int initialCapacity) {
        IntArgs.checkPositive(initialCapacity, "initialCapacity");
        
        _byteArr = new byte[initialCapacity];
        _usedLength = 0;
    }
    
//    public int capacity() {
//        return _byteArr.length;
//    }
    
    /**
     * This value is always less than or equal to capacity.
     * 
     * @return number of bytes appended to this builder
     */
    public int length() {
        return _usedLength;
    }
    
//    public ByteArrayBuilder setLength(int newLength) {
//        IntArgs.checkNotNegative(newLength, "newLength");
//        
//        if (newLength > _usedLength) {
//            ensureCapacity(newLength);
//        }
//        _usedLength = newLength;
//        return this;
//    }
//    
//    public ByteArrayBuilder clear() {
//        _usedLength = 0;
//        return this;
//    }
//
//    public ByteArrayBuilder append(Byte oneByte) {
//        ObjectArgs.checkNotNull(oneByte, "oneByte");
//        
//        byte b = oneByte.byteValue();
//        append(b);
//        return this;
//    }
    
    /**
     * Appends a single byte to this builder.
     * 
     * @param oneByte
     *        data to append
     * 
     * @return reference to {@code this}
     * 
     * @see #append(byte[])
     * @see #append(byte[], int, int)
     */
    public ByteArraySimpleBuilder append(byte oneByte) {
        ensureCapacity(_usedLength + 1);
        _byteArr[_usedLength] = oneByte;
        ++_usedLength;
        return this;
    }
    
    /**
     * This is a convenience method to call {@link #append(byte[], int, int)} where params
     * {@code offset} is zero and {@code length} is {@code byteArr.length}.
     * <p>
     * This method allows parameter {@code byteArr} to be empty.
     */
    public ByteArraySimpleBuilder append(byte[] byteArr) {
        int len = (null == byteArr ? -1 : byteArr.length);
        if (0 != len) {
            append(byteArr, 0, len);
        }
        return this;
    }
    
    /**
     * Appends a segment of an array of bytes to this builder.
     * 
     * @param byteArr
     *        data to append.  Must <b>not</b> be empty, e.g., {@code new byte[0]}
     * @param offset
     * <ul>
     *   <li>Index offset to append bytes from parameter {@code byteArr}</li>
     *   <li>Valid range: Zero to {@code byteArr.length - 1}</li>
     * </ul>
     * @param length
     * <ul>
     *   <li>Number of bytes to append from parameter {@code byteArr}</li>
     *   <li>Valid range: Zero to {@code byteArr.length - offset}</li>
     * </ul>
     * 
     * @return reference to {@code this}
     * 
     * @see #append(byte)
     * @see #append(byte[], int, int)
     * 
     * @throws NullPointerException
     *         if {@code byteArr} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code byteArr} is empty</li>
     *   <li>if {@code offset < 0}</li>
     *   <li>if {@code length < 0}</li>
     * </ul>
     * @throws IndexOutOfBoundsException
     * <ul>
     *   <li>if {@code offset >= byteArr.length}</li>
     *   <li>if {@code offset + length > byteArr.length}</li>
     * </ul>
     */
    public ByteArraySimpleBuilder append(byte[] byteArr, int offset, int length) {
        ArrayArgs.checkIndexAndCount(byteArr, offset, length, "byteArr", "offset", "length");
        if (0 != byteArr.length && 0 != length) {
            ensureCapacity(_usedLength + length);
            System.arraycopy(byteArr, offset, _byteArr, _usedLength, length);
            _usedLength += length;
        }
        return this;
    }
    
    /**
     * Ensures the internal byte array has sufficient capacity for additional data to be appended.
     * The growth algorithm is very basic: It doubles the current capacity continuously, until it
     * exceeds {@code newCapacity}.
     * 
     * @param newCapacity
     *        Minimum number of bytes required for internal storage buffer
     */
    protected void ensureCapacity(int newCapacity) {
        if (_byteArr.length < newCapacity) {
            int capacity = _byteArr.length;
            do {
                capacity *= 2;
            }
            while (capacity < newCapacity);
            _byteArr = Arrays.copyOf(_byteArr, capacity);
        }
    }
    
    /**
     * Copies the internal buffer to a new array of data.  Only the used area of the buffer is
     * copied to the new array.
     * 
     * @return array of appended data
     */
    public byte[] toArray() {
        byte[] x = Arrays.copyOf(_byteArr, _usedLength);
        return x;
    }
    
//    public byte[] toArray(int offset, int length) {
//        ArrayArgs.checkIndexAndCount(_byteArr, offset, length, "byteArr", "offset", "length");
//        
//        byte[] x = Arrays.copyOfRange(_byteArr, offset, offset + length);
//        return x;
//    }
    
//    public ByteArrayBuilder copy(byte[] byteArr, int offset, int length) {
//        ArrayArgs.checkIndexAndCount(byteArr, offset, length, "byteArr", "offset", "length");
//        if (length > _usedLength) {
//            throw new IllegalArgumentException(String.format(
//                "Argument 'length': Larger than number of available bytes: %d > %d",
//                length, _usedLength));
//        }
//        
//        System.arraycopy(_byteArr, 0, byteArr, offset, length);
//        return this;
//    }
}
