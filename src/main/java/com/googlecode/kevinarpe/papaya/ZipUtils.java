package com.googlecode.kevinarpe.papaya;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.google.common.primitives.Bytes;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.PathArgs;

/**
 * Static methods for handling compressed files and streams.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ZipUtils {

    // Disable default constructor
    private ZipUtils() {
    }

    /**
     * Tricky: We cannot use byte here because range is -128 to 127.
     * Instead, we intentionally use int to match return type from InputStream.read().
     * Ref: http://www.onicos.com/staff/iz/formats/gzip.html
     */
    public static final List<Byte> GZIP_MAGIC_BYTE_LIST = Bytes.asList((byte) 0x1f, (byte) 0x8b);
    
    /**
     * Tests if a file has GZIP compression format.
     * 
     * @param path
     *        file path to test
     * 
     * @return {@code true} if file has GZIP format
     * 
     * @throws NullPointerException
     *         if {@code path} is {@code null}
     * @throws IOException
     * <ul>
     *   <li>if {@code path} does not exist</li>
     *   <li>if {@code path} exists, but is not a file</li>
     *   <li>if any other I/O error occurs</li>
     * </ul>
     * 
     * @see #createUnzipInputStream(File)
     * @see #unzipInputStream(InputStream)
     */
    @NotFullyTested
    public static boolean isGZipFile(File path)
    throws IOException {
        PathArgs.checkFileExists(path, "path");
        FileInputStream fin = new FileInputStream(path);
        
        try {
            for (final int expected: GZIP_MAGIC_BYTE_LIST) {
                final int actual = InputStreamUtils.checkedReadNextByte(fin);
                if (-1 == actual || actual != expected) {
                    return false;
                }
            }
            return true;
        }
        finally {
            try {
                fin.close();
            }
            catch (IOException e) {
                // Intentionally ignore
                @SuppressWarnings("unused")
                int dummy = 1;
            }
        }
    }
    
    /**
     * Creates a new {@link InputStream} for a file, guaranteeing the resuls will be decompressed.
     * If the file is not compressed, an instance of {@link FileInputStream} is returned.
     * Currently, only GZIP format is supported.  Other formats may be added in the future, such as
     * BZIP2 and XZ.
     * 
     * @param path
     *        path to file to open for reading
     * 
     * @return new stream handle
     * 
     * @throws NullPointerException
     *         if {@code path} is {@code null}
     * @throws IOException
     * <ul>
     *   <li>if {@code path} does not exist</li>
     *   <li>if {@code path} exists, but is not a file</li>
     *   <li>if any other I/O error occurs</li>
     * </ul>
     * 
     * @see FileInputStream
     * @see GZIPInputStream
     * @see #isGZipFile(File)
     */
    @NotFullyTested
    public static InputStream createUnzipInputStream(File path)
    throws IOException {
        PathArgs.checkFileExists(path, "path");
        
        InputStream in = new FileInputStream(path);
        InputStream in2 = unzipInputStream(in);
        return in2;
    }
    
    /**
     * Creates a new {@link InputStream} from an existing one, guaranteeing the resuls will be
     * decompressed.  If the stream is not compressed, a wrapped version of the stream is returned.
     * Currently, only GZIP format is supported.  Other formats may be added in the future, such as
     * BZIP2 and XZ.
     * <p>
     * This method expects the input to be set to the first byte of the {@code InputStream}.
     * Attempts to call this method with an {@code InputStream} set to any other byte, but the
     * first, will result in undefined behaviour.
     * 
     * @param in
     *        input stream to decompress
     * 
     * @return new stream handle (set to the first byte)
     * 
     * @throws NullPointerException
     *         if {@code in} is {@code null}
     * @throws IOException
     *         if any I/O error occurs
     * 
     * @see #createUnzipInputStream(File)
     * @see #isGZipFile(File)
     */
    @NotFullyTested
    public static InputStream unzipInputStream(InputStream in)
    throws IOException {
        ObjectArgs.checkNotNull(in, "in");
        
        int size = GZIP_MAGIC_BYTE_LIST.size();
        PushbackInputStream pbin = new PushbackInputStream(in, size);
        byte[] byteArr = new byte[size];
        int byteCount = in.read(byteArr);
        if (-1 == byteCount) {
            return pbin;
        }
        pbin.unread(byteArr, 0, byteCount);
        if (byteCount < size) {
            return pbin;
        }
        for (int i = 0; i < size; ++i) {
            final byte expected = GZIP_MAGIC_BYTE_LIST.get(i);
            final byte actual = byteArr[i];
            if (expected != actual) {
                return pbin;
            }
        }
        GZIPInputStream gzpbin = new GZIPInputStream(pbin);
        return gzpbin;
    }
}
