package com.googlecode.kevinarpe.papaya.filesystem;

import com.google.common.collect.ForwardingList;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.PathArgs;
import com.googlecode.kevinarpe.papaya.exception.PathException;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class PathList2
extends ForwardingList<File> {

    private final File _dirPath;
    private final List<File> _childPathList;

    public PathList2(File dirPath)
    throws PathException {
        this(dirPath, ArrayList.class);
    }

    public PathList2(File dirPath, Class<? extends List<File>> clazz)
    throws PathException {
        _dirPath = PathArgs.checkDirectoryExists(dirPath, "dirPath");
        ObjectArgs.checkNotNull(clazz, "clazz");

        File[] childPathArr = dirPath.listFiles();
        if (null == childPathArr) {
            if (!dirPath.exists()) {
                String msg = String.format(
                    "Failed to list files for path (does not exist): '%s'",
                    dirPath.getAbsolutePath());
                throw new PathException(
                    PathException.PathExceptionReason.PATH_DOES_NOT_EXIST, dirPath, null, msg);
            }
            if (dirPath.isFile()) {
                String msg = String.format(
                    "Failed to list files for path (exists as file, not directory): '%s'",
                    dirPath.getAbsolutePath());
                throw new PathException(PathException.PathExceptionReason.PATH_IS_FILE, dirPath, null, msg);
            }
            // Exists + Directory...
            if (!dirPath.canExecute()) {
                String msg = String.format(
                    "Failed to list files for path (execute permission not set): '%s'",
                    dirPath.getAbsolutePath());
                throw new PathException(
                    PathException.PathExceptionReason.PATH_IS_NON_EXECUTABLE_DIRECTORY, dirPath, null, msg);
            }
            String msg = String.format(
                "Failed to list files for path (unknown error): '%s'",
                dirPath.getAbsolutePath());
            throw new PathException(PathException.PathExceptionReason.UNKNOWN, dirPath, null, msg);
        }

        _childPathList = newFileListInstance(clazz);
        if (0 != childPathArr.length) {
            _childPathList.addAll(Arrays.asList(childPathArr));
        }
    }

    public PathList2(PathList2 other) {
        ObjectArgs.checkNotNull(other, "other");

        _dirPath = other._dirPath;
        Class<? extends List<File>> clazz = other._childPathList.getClass();
        _childPathList = newFileListInstance(clazz);
        _childPathList.addAll(other._childPathList);
    }

    private static List<File> newFileListInstance(Class<? extends List<File>> clazz) {
        try {
            return clazz.newInstance();
        }
        catch (Exception e) {
            String msg = String.format("Failed to create new instance of list class %s", clazz.getName());
            throw new IllegalArgumentException(msg, e);
        }
    }

    @Override
    protected List<File> delegate() {
        return _childPathList;
    }

    public File getDirPath() {
        return _dirPath;
    }

    public PathList2 sort(Comparator<File> fileComparator) {
        ObjectArgs.checkNotNull(fileComparator, "fileComparator");

        @SuppressWarnings("unchecked")
        List<Comparator<File>> fileComparatorList = Arrays.asList(fileComparator);
        return sort(fileComparatorList);
    }

    public PathList2 sort(List<Comparator<File>> fileComparatorList) {
        CollectionArgs.checkElementsNotNull(fileComparatorList, "fileComparatorList");

        if (!fileComparatorList.isEmpty() && !_childPathList.isEmpty()) {
            // Ugly.  Move to helper?
            final int size = _childPathList.size();
            File[] childPathArr = new File[size];
            childPathArr = _childPathList.toArray(childPathArr);

            for (Comparator<File> fileComparator : fileComparatorList) {
                Arrays.sort(childPathArr, fileComparator);
            }

            // Ugly.  Move to helper?
            ListIterator<File> iter = _childPathList.listIterator();
            for (int i = 0; i < childPathArr.length; ++i) {
                iter.next();
                File childPath = childPathArr[i];
                iter.set(childPath);
            }
        }
        return this;
    }

    public PathList2 filter(FileFilter fileFilter) {
        ObjectArgs.checkNotNull(fileFilter, "fileFilter");

        return filter(Arrays.asList(fileFilter));
    }

    public PathList2 filter(List<FileFilter> fileFilterList) {
        ObjectArgs.checkNotNull(fileFilterList, "fileFilterList");

        if (!fileFilterList.isEmpty() && !_childPathList.isEmpty()) {

            if (_childPathList instanceof RandomAccess) {
                // TODO: Shifing algorithm?  Might be useless.  Use a cheaper one initially.
//                final int size = _childPathList.size();
//                File[] childPathArr = new File[size];
//                childPathArr = _childPathList.toArray(childPathArr);
//
//                int newIter = 0;
//                for (int oldIter = 0; oldIter < size; ++oldIter) {
//                    File childPath = _childPathList.get(oldIter);
//
//                }
            }
            else {  // instanceof SequentialAccess or LinkedList
                for (FileFilter fileFilter : fileFilterList) {
                    ListIterator<File> iter = _childPathList.listIterator();
                    while (iter.hasNext()) {
                        File childPath = iter.next();
                        if (!fileFilter.accept(childPath)) {
                            iter.remove();
                        }
                    }
                }
            }
        }

//        File[] newChildPathArr = new File[_childPathArrAsFixedSizeList.size()];
//        File[] childPathArr = _childPathArrAsFixedSizeList.getArrayRef();
//        final int size = childPathArr.length;
//        int newChildPathArrIter = 0;
//        for (int childPathArrIter = 0; childPathArrIter < size; ++childPathArrIter) {
//            File file = childPathArr[childPathArrIter];
//            if (fileFilterList.accept(file)) {
//                newChildPathArr[newChildPathArrIter] = file;
//                ++newChildPathArrIter;
//            }
//        }
//        if (newChildPathArrIter < size) {
//            File[] newChildPathArr2 = new File[newChildPathArrIter];
//            System.arraycopy(newChildPathArr, 0, newChildPathArr2, 0, newChildPathArr2.length);
//            _childPathArrAsFixedSizeList = ArrayAsFixedSizeList.referenceTo(newChildPathArr2);
//        }
        // else: we 'accept' 100% of paths via 'fileFilterList': array is unchanged
        return this;
    }
}
