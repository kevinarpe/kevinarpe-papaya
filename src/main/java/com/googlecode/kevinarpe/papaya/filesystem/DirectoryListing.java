package com.googlecode.kevinarpe.papaya.filesystem;

import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.PathArgs;
import com.googlecode.kevinarpe.papaya.exception.PathException;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class DirectoryListing {

    public static final Class<? extends List> DEFAULT_LIST_CLASS = ArrayList.class;

    private final File _dirPath;
    private List<File> _childPathList;

    public DirectoryListing(File dirPath)
    throws PathException {
        this(dirPath, DEFAULT_LIST_CLASS);
    }

    public DirectoryListing(File dirPath, Class<? extends List> listClass)
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
                throw new PathException(
                    PathException.PathExceptionReason.PATH_IS_FILE, dirPath, null, msg);
            }
            // Exists + Directory...
            if (!dirPath.canExecute()) {
                String msg = String.format(
                    "Failed to list files for path (execute permission not set): '%s'",
                    dirPath.getAbsolutePath());
                throw new PathException(
                    PathException.PathExceptionReason.PATH_IS_NON_EXECUTABLE_DIRECTORY,
                    dirPath, null, msg);
            }
            String msg = String.format(
                "Failed to list files for path (unknown error): '%s'",
                dirPath.getAbsolutePath());
            throw new PathException(PathException.PathExceptionReason.UNKNOWN, dirPath, null, msg);
        }

        _childPathList = _newInstance(listClass);
        if (0 != childPathArr.length) {
            _childPathList.addAll(Arrays.asList(childPathArr));
        }
    }

    public DirectoryListing(DirectoryListing other) {
        this(
            other,
            (null == other ? null : other._childPathList.getClass()),
            123);
    }

    public DirectoryListing(DirectoryListing other, Class<? extends List> listClass) {
        this(
            other,
            ObjectArgs.checkNotNull(listClass, "listClass"),
            123);
    }

    private DirectoryListing(DirectoryListing other, Class<? extends List> listClass, int dummy) {
        ObjectArgs.checkNotNull(other, "other");

        _dirPath = other._dirPath;
        _childPathList = _newInstance(listClass);
        _childPathList.addAll(other._childPathList);
    }

    private static List<File> _newInstance(Class<? extends List> listClass) {
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

    public File getDirPath() {
        return _dirPath;
    }

    public List<File> getChildPathList() {
        List<File> list = _newInstance(_childPathList.getClass());
        list.addAll(_childPathList);
        return list;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(_dirPath, _childPathList);
        result = 31 * result + _childPathList.getClass().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof DirectoryListing) {
            final DirectoryListing other = (DirectoryListing) obj;
            result = Objects.equal(this._dirPath, other._dirPath)
                && _classEquals(this._childPathList, other._childPathList)
                && Objects.equal(this._childPathList, other._childPathList);
        }
        return result;
    }

    private static boolean _classEquals(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (null == a || null == b) {
            return false;
        }
        boolean x = a.getClass().equals(b.getClass());
        return x;
    }

    public DirectoryListing sort(Comparator<File> fileComparator) {
        ObjectArgs.checkNotNull(fileComparator, "fileComparator");

        @SuppressWarnings("unchecked")
        List<Comparator<File>> fileComparatorList = Arrays.asList(fileComparator);
        return sort(fileComparatorList);
    }

    public DirectoryListing sort(List<Comparator<File>> fileComparatorList) {
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

    public DirectoryListing filter(FileFilter fileFilter) {
        ObjectArgs.checkNotNull(fileFilter, "fileFilter");

        return filter(Arrays.asList(fileFilter));
    }

    public DirectoryListing filter(List<FileFilter> fileFilterList) {
        CollectionArgs.checkElementsNotNull(fileFilterList, "fileFilterList");

        if (!fileFilterList.isEmpty() && !_childPathList.isEmpty()) {
            if (_childPathList instanceof RandomAccess) {
                for (FileFilter fileFilter : fileFilterList) {
                    _childPathList = _filterRandomAccessList(_childPathList, fileFilter);
                }
            }
            else {  // instanceof SequentialAccess or LinkedList
                for (FileFilter fileFilter : fileFilterList) {
                    _filterSequentialAccessList(_childPathList, fileFilter);
                }
            }
        }
        return this;
    }

    private static List<File> _filterRandomAccessList(
            List<File> childPathList, FileFilter fileFilter) {
        List<File> newChildPathList = _newInstance(childPathList.getClass());
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

    private static void _filterSequentialAccessList(
            List<File> childPathList, FileFilter fileFilter) {
        ListIterator<File> iter = childPathList.listIterator();
        while (iter.hasNext()) {
            File childPath = iter.next();
            if (!fileFilter.accept(childPath)) {
                iter.remove();
            }
        }
    }
}
