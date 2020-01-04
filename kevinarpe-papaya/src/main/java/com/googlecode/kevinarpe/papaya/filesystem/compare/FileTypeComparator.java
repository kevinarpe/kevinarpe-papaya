package com.googlecode.kevinarpe.papaya.filesystem.compare;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.google.common.primitives.Ints;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.filesystem.FileType;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Compares two {@link File} references using a list of {@link FileType}s to determine order.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #compare(File, File)
 * @see FileType
 */
@FullyTested
public final class FileTypeComparator
implements Comparator<File> {

    /**
     * Since the number of file types is very limited in Java 6, this static final members exists
     * as a convenience to sort directories first, and non-directories last.
     * <ul>
     *     <li>First: {@link FileType#DIRECTORY}</li>
     *     <li>Second: {@link FileType#NORMAL_FILE}</li>
     * </ul>
     *
     * @see #NORMAL_FILES_FIRST
     */
    public static final FileTypeComparator DIRECTORIES_FIRST =
        new FileTypeComparator(Arrays.asList(FileType.DIRECTORY, FileType.NORMAL_FILE));

    /**
     * Since the number of file types is very limited in Java 6, this static final members exists
     * as a convenience to sort directories first, and non-directories last.
     * <ul>
     *     <li>First: {@link FileType#NORMAL_FILE}</li>
     *     <li>Second: {@link FileType#DIRECTORY}</li>
     * </ul>
     *
     * @see #DIRECTORIES_FIRST
     */
    public static final FileTypeComparator NORMAL_FILES_FIRST =
        new FileTypeComparator(Arrays.asList(FileType.NORMAL_FILE, FileType.DIRECTORY));

    private final Map<FileType, Integer> _fileTypeToIndexMap;

    /**
     * Creates a new {@link Comparator} from a list of {@link FileType} references.  Callers may
     * want to use {@link Arrays#asList(Object[])} to build a {@link List}.  The first value in the
     * param list has the high priority and will sort first.  The last value has the lowest priority
     * and will sort last.
     * <p>
     * Example: {@code Arrays.asList(FileType.NORMAL_FILE, FileType.DIRECTORY)} will sort all
     * regular files before directories.
     *
     * @param fileTypeList
     *        list of {@link FileType}s.  Must not be empty, contain {@code null}s, or contain
     *        duplicate values.
     *
     * @see Arrays#asList(Object[])
     * @see FileType
     */
    public FileTypeComparator(List<FileType> fileTypeList) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(fileTypeList, "fileTypeList");
        CollectionArgs.checkElementsUnique(fileTypeList, "fileTypeList");

        _fileTypeToIndexMap = Maps.newHashMapWithExpectedSize(fileTypeList.size());
        for (int i = 0; i < fileTypeList.size(); ++i) {
            FileType fileType = fileTypeList.get(i);
            _fileTypeToIndexMap.put(fileType, i);
        }
    }

    /**
     * Compares the priority of {@link FileType} for paths.  This priority is determined by the
     * list provided to the constructor.
     * <hr>
     * {@inheritDoc}
     *
     * @param path1
     *        must not be {@code null}
     * @param path2
     *        must not be {@code null}
     *
     * @return
     * <ul>
     *     <li>-1 if {@code path1} is less than {@code path2}</li>
     *     <li>0 if {@code path1} is equal to {@code path2}</li>
     *     <li>+1 if {@code path1} is greater than {@code path2}</li>
     * </ul>
     *
     * @throws NullPointerException
     *         if {@code path1} or {@code path2} is {@code null}
     * @throws IllegalArgumentException
     *         if {@link FileType} of {@code path1} or {@code path2} is not found in the internal
     *         priority map
     */
    @Override
    public int compare(File path1, File path2) {
        ObjectArgs.checkNotNull(path1, "path1");
        ObjectArgs.checkNotNull(path2, "path2");

        FileType fileType1 = checkFileType(path1);
        FileType fileType2 = checkFileType(path2);
        int fileTypeIndex1 = _fileTypeToIndexMap.get(fileType1);
        int fileTypeIndex2 = _fileTypeToIndexMap.get(fileType2);
        int result = Ints.compare(fileTypeIndex1, fileTypeIndex2);
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

    /**
     * Equates by the priority map of {@link FileType}s.  This priority is determined by the list
     * provided to the constructor.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof FileTypeComparator) {
            final FileTypeComparator other = (FileTypeComparator) obj;
            result = this._fileTypeToIndexMap.equals(other._fileTypeToIndexMap);
        }
        return result;
    }

    /**
     * Returns hash code of priority map of {@link FileType}s.  This priority is determined by the
     * list provided to the constructor.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return _fileTypeToIndexMap.hashCode();
    }
}
