package com.googlecode.kevinarpe.papaya.test;

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

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;

@NotFullyTested
public final class TestCharsetUtils {

    private static List<Charset> _nonDefaultCharsetList;
    
    /**
     * When a {@link Charset} is required for a method, such as
     * {@link String#String(byte[], Charset)}, nearly all programmers (and testers) simply use
     * {@link Charset#defaultCharset()}.  To test alternative character sets, a list of non-default
     * ones is required.  This method will traverse your JVM config, and build a list of
     * non-default {@code Charset} references.
     * <p>
     * The return values is cached.  Subsequent calls return the same immutable list.
     * 
     * @return immutable list of non-default {@link Charset} references
     * 
     * @throws IllegalStateException
     *         if zero non-default {@code Charset}s exist
     */
    @NotFullyTested
    public static List<Charset> getNonDefaultCharsetList() {
        if (null == _nonDefaultCharsetList) {
            Charset defaultCharset = Charset.defaultCharset();
            ImmutableList.Builder<Charset> builder = ImmutableList.builder();
            for (Charset cs: Charset.availableCharsets().values()) {
                if (!cs.equals(defaultCharset)) {
                    builder.add(cs);
                }
            }
            List<Charset> list = builder.build();
            if (list.isEmpty()) {
                throw new IllegalStateException(String.format(
                    "There are zero non-default Charsets available: Default = '%s'",
                    defaultCharset.name()));
            }
            _nonDefaultCharsetList = list;
        }
        return _nonDefaultCharsetList;
    }
    
    private static final Random _RANDOM = new Random();
    
    /**
     * Obtains a list of non-default {@link Charset} references and selects one at random.
     * 
     * @return reference to non-default {@link Charset}
     * 
     * @throws IllegalStateException
     *         if zero non-default {@code Charset}s exist
     * 
     * @see #getNonDefaultCharsetList()
     */
    @NotFullyTested
    public static Charset getRandomNonDefaultCharset() {
        List<Charset> list = getNonDefaultCharsetList();
        int listSize = list.size();
        int randomIndex = _RANDOM.nextInt(listSize);
        Charset cs = list.get(randomIndex);
        return cs;
    }
}
