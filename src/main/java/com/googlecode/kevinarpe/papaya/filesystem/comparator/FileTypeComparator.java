package com.googlecode.kevinarpe.papaya.filesystem.comparator;

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

import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.filesystem.FileType;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class FileTypeComparator
implements Comparator<File> {

    private final Map<FileType, Integer> _fileTypeToIndexMap;

    public FileTypeComparator(List<FileType> fileTypeList) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(fileTypeList, "fileTypeList");

        _fileTypeToIndexMap = Maps.newHashMap();
        for (int i = 0; i < fileTypeList.size(); ++i) {
            _fileTypeToIndexMap.put(fileTypeList.get(i), i);
        }
    }

    @Override
    public int compare(File path1, File path2) {
        FileType fileType1 = checkFileType(path1);
        FileType fileType2 = checkFileType(path2);
        int fileTypeIndex1 = _fileTypeToIndexMap.get(fileType1);
        int fileTypeIndex2 = _fileTypeToIndexMap.get(fileType2);
        int result = Integer.compare(fileTypeIndex1, fileTypeIndex2);
        return result;
    }

    private FileType checkFileType(File path) {
        FileType fileType = getFileType(path);
        if (!_fileTypeToIndexMap.containsKey(fileType)) {
            throw new IllegalArgumentException(String.format(
                "Unknown %s %s for path: '%s'",
                FileType.class.getSimpleName(), fileType.name(), path.getAbsolutePath()));
        }
        return fileType;
    }

    private FileType getFileType(File path) {
        try {
            return FileType.from(path);
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (this == obj ||
            (obj instanceof FileTypeComparator &&
                this._fileTypeToIndexMap.equals(((FileTypeComparator) obj)._fileTypeToIndexMap)));
    }

    // TODO: Hashcode?
}
