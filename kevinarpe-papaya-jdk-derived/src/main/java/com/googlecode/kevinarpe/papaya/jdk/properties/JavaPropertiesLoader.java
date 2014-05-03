package com.googlecode.kevinarpe.papaya.jdk.properties;

/*
 * #%L
 * This file is part of Papaya (JDK Derived Classes).
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya (JDK Derived Classes) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only as
 * published by the Free Software Foundation.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in the LICENSE
 * file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Special notice for this module of Papaya:
 * Classes in this module may contain significant portions that are originally
 * part of the JDK source base.  In such cases, prominent notices appear before
 * these blocks of source code.
 * #L%
 */

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Loader for Java properties.
 * <p>
 * The provided implementation is a derivative of original class {@link Properties} and methods
 * related to loading of Java properties files.  Original source code:
 * <a href="http://hg.openjdk.java.net/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/java/util/Properties.java"
 * >Properties.java</a>
 * <p>
 * To access an implementation, see {@link JavaPropertiesLoaderUtils#getInstance()}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaPropertiesLoaderUtils
 * @see Properties
 */
public interface JavaPropertiesLoader {

    /**
     * This is a derivative of the original {@link Properties#load(Reader)}.  The result is a list
     * of map entries, instead of a {@link Hashtable}.  As a result, more careful analysis and
     * checking of the loaded entries is possible.
     *
     * @param characterStream
     *        stream of characters to read.
     *        To read from a file, use {@link FileReader}.
     *        To read from a string use {@link StringReader}.
     *
     * @return mutable list of mutable entries.  Order of entries follows input exactly.
     *         Duplicate keys and key-value pairs may exist.
     *
     * @throws NullPointerException
     *         if {@code characterStream} is null
     * @throws IOException
     *         if an error occurred when reading the stream
     * @throws IllegalArgumentException
     *         if a malformed Unicode escape appears in the input
     *
     * @see Properties#load(Reader)
     * @see #load(InputStream)
     */
    RandomAccessList<JavaProperty> load(Reader characterStream)
    throws IOException;

    /**
     * This is a derivative of the original {@link Properties#load(Reader)}.  The result is a list
     * of map entries, instead of a {@link Hashtable}.  As a result, more careful analysis and
     * checking of the loaded entries is possible.
     *
     * @param byteStream
     *        stream of bytes to read.
     *        To read from a file, use {@link FileInputStream}
     *
     * @return mutable list of mutable entries.  Order of entries follows input exactly.
     *         Duplicate keys and key-value pairs may exist.
     *
     * @throws NullPointerException
     *         if {@code byteStream} is null
     * @throws IOException
     *         if an error occurred when reading the stream
     * @throws IllegalArgumentException
     *         if a malformed Unicode escape appears in the input
     *
     * @see Properties#load(InputStream)
     * @see #load(Reader)
     */
    RandomAccessList<JavaProperty> load(InputStream byteStream)
    throws IOException;
}
