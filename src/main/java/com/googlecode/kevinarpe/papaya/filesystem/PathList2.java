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

    public static final Class<? extends List> DEFAULT_LIST_CLASS = ArrayList.class;

    private final File _dirPath;
    private List<File> _childPathList;

    public PathList2(File dirPath)
    throws PathException {
        this(dirPath, DEFAULT_LIST_CLASS);
    }

    public PathList2(File dirPath, Class<? extends List> listClass)
    throws PathException {
        _dirPath = PathArgs.checkDirectoryExists(dirPath, "dirPath");
        ObjectArgs.checkNotNull(listClass, "listClass");

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

        _childPathList = newFileListInstance(listClass);
        if (0 != childPathArr.length) {
            _childPathList.addAll(Arrays.asList(childPathArr));
        }
    }

    public PathList2(PathList2 other) {
        this(
            other,
            (null == other ? null : other._childPathList.getClass()),
            123);
    }

    // TODO: If we allow class override, how to handle equals()?
    // TODO: Do we need to override equals() and hashCode()?
    public PathList2(PathList2 other, Class<? extends List> listClass) {
        this(
            other,
            ObjectArgs.checkNotNull(listClass, "listClass"),
            123);
    }

    private PathList2(PathList2 other, Class<? extends List> listClass, int dummy) {
        ObjectArgs.checkNotNull(other, "other");

        _dirPath = other._dirPath;
        _childPathList = newFileListInstance(listClass);
        _childPathList.addAll(other._childPathList);
    }

    // TODO: Move to ContainerUtils?  Maybe create a static method to new() and copy() [via addAll()]?
    private static List<File> newFileListInstance(Class<? extends List> listClass) {
        try {
            @SuppressWarnings("unchecked")
            List<File> list = listClass.newInstance();
            return list;
        }
        catch (Exception e) {
            String msg = String.format(
                "Failed to create new instance of list class %s", listClass.getName());
            throw new IllegalArgumentException(msg, e);
        }
    }

    @Override
    protected List<File> delegate() {
        return _childPathList;
    }

    Class<? extends List> getDelegateClass() {
        // Exists for testing.
        return delegate().getClass();
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
        CollectionArgs.checkElementsNotNull(fileFilterList, "fileFilterList");

        if (!fileFilterList.isEmpty() && !_childPathList.isEmpty()) {
            if (_childPathList instanceof RandomAccess) {
                for (FileFilter fileFilter : fileFilterList) {
                    _childPathList = coreFilter(fileFilter, _childPathList);
                }
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
        return this;
    }

    private List<File> coreFilter(FileFilter fileFilter, List<File> childPathList) {
        List<File> newChildPathList = newFileListInstance(_childPathList.getClass());
        for (File childPath : childPathList) {
            if (fileFilter.accept(childPath)) {
                newChildPathList.add(childPath);
            }
        }
        if (newChildPathList.size() == childPathList.size()) {
            return childPathList;
        }
        return newChildPathList;
    }
}
