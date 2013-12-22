package com.googlecode.kevinarpe.papaya.filesystem.comparator;

import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.filesystem.FileType;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FileTypeComparator
implements Comparator<File> {

    private final Map<FileType, Integer> fileTypeToIndexMap;

    public FileTypeComparator(List<FileType> fileTypeList) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(fileTypeList, "fileTypeList");

        fileTypeToIndexMap = Maps.newHashMap();
        for (int i = 0; i < fileTypeList.size(); ++i) {
            fileTypeToIndexMap.put(fileTypeList.get(i), i);
        }
    }

    @Override
    public int compare(File path1, File path2) {
        FileType fileType1 = checkFileType(path1);
        FileType fileType2 = checkFileType(path2);
        return Integer.compare(
            fileTypeToIndexMap.get(fileType1), fileTypeToIndexMap.get(fileType2));
    }

    private FileType checkFileType(File path) {
        FileType fileType = getFileType(path);
        if (!fileTypeToIndexMap.containsKey(fileType)) {
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
}
