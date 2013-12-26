package com.googlecode.kevinarpe.papaya.filesystem.comparator;

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
