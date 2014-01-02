package com.googlecode.kevinarpe.papaya.filesystem.comparator;

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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.comparator.AbstractLexicographicalComparator;
import com.googlecode.kevinarpe.papaya.comparator.CaseSensitive;

import java.io.File;

public final class FileNameLexicographicalComparator
extends AbstractLexicographicalComparator<File, FileNameLexicographicalComparator> {

    public FileNameLexicographicalComparator() {
        super();
    }

    public FileNameLexicographicalComparator(CaseSensitive caseSensitive) {
        super(caseSensitive);
    }

    @Override
    public int compare(File path1, File path2) {
        ObjectArgs.checkNotNull(path1, "path1");
        ObjectArgs.checkNotNull(path2, "path2");

        final String fileName1 = path1.getName();
        final String fileName2 = path2.getName();
        int result = compareStrings(fileName1, fileName2);
        return result;
    }
}
