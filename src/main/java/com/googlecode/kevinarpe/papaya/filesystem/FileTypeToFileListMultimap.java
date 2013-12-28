package com.googlecode.kevinarpe.papaya.filesystem;

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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ForwardingListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.UnmodifiableIterator;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

// TODO: Dead class?
public class FileTypeToFileListMultimap
extends ForwardingListMultimap<FileType, File> {

    private static class _ByFileTypeIterator
    extends UnmodifiableIterator<File> {

        private final FileTypeToFileListMultimap _fileTypeToFileListMultimap;
        private final List<FileType> _fileTypeList;
        private final Iterator<FileType> _fileTypeListIter;
        private List<File> _fileList;  // for debug only
        private Iterator<File> _fileListIter;

        public _ByFileTypeIterator(
            FileTypeToFileListMultimap fileTypeToFileListMultimap,
            List<FileType> fileTypeList) {

            _fileTypeToFileListMultimap =
                ObjectArgs.checkNotNull(fileTypeToFileListMultimap, "fileTypeToFileListMultimap");
            _fileTypeList =
                CollectionArgs.checkNotEmptyAndElementsNotNull(fileTypeList, "fileTypeList");
            _fileTypeListIter = _fileTypeList.iterator();
            // We can safely call next() without calling hasNext() because we already checked
            // _fileTypeList is NOT empty.
            FileType fileType0 = _fileTypeListIter.next();
            _fileList = _fileTypeToFileListMultimap.get(fileType0);
            _fileListIter = _fileList.iterator();
        }

        @Override
        public boolean hasNext() {
            return _fileListIter.hasNext() || _fileTypeListIter.hasNext();
        }

        @Override
        public File next() {
            if (_fileListIter.hasNext()) {
                return _fileListIter.next();
            }
            while (_fileTypeListIter.hasNext()) {
                FileType fileType = _fileTypeListIter.next();
                _fileList = _fileTypeToFileListMultimap.get(fileType);
                _fileListIter = _fileList.iterator();
                if (_fileListIter.hasNext()) {
                    return _fileListIter.next();
                }
            }
            // Throws NoSuchElementException
            _fileTypeListIter.next();
            // Never reach
            return null;
        }
    }

    private final ListMultimap<FileType, File> _fileTypeToFileListMultimap;

    // TODO: Offer options to sort by (0) natural (inode), (1) name, (2) modification time, (3) file size, (4) custom, (5) multiple
    // TODO: Allow sort (1) ascending (A-Z) or (2) descending (A-Z)
    // TODO: Offer max depth option

    public FileTypeToFileListMultimap(List<File> pathList) throws IOException {
        CollectionArgs.checkElementsNotNull(pathList, "pathList");

        _fileTypeToFileListMultimap = ArrayListMultimap.create();
        for (File path : pathList) {
            FileType fileType = FileType.from(path);
            _fileTypeToFileListMultimap.put(fileType, path);
        }
    }

    @Override
    protected ListMultimap<FileType, File> delegate() {
        return _fileTypeToFileListMultimap;
    }

    public Iterator<File> iteratorByFileType(List<FileType> fileTypeList) {
        return new _ByFileTypeIterator(this, fileTypeList);
    }

//    @Override
//    public List<File> get(FileType key) {
//        ObjectArgs.checkNotNull(key, "key");
//
//        return super.get(key);
//    }

//    @Override
//    public Set<File> removeAll(Object key) {
//        ObjectArgs.checkNotNull(key, "key");
//        ObjectArgs.checkAssignableToType(key.getClass(), FileType.class, key.getClass().getName());
//
//        return super.removeAll(key);
//    }
}
