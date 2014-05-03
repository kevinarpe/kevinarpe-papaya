package com.googlecode.kevinarpe.papaya.properties;

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

import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.PathArgs;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class PropertiesLoaderImpl {

    // Allow reading from classResource

    public static final Logger DEFAULT_OPTIONAL_LOGGER = null;

    private final Logger _optionalLogger;

    public PropertiesLoaderImpl() {
        this(DEFAULT_OPTIONAL_LOGGER);
    }

    public PropertiesLoaderImpl(Logger optionalLogger) {
        this._optionalLogger = optionalLogger;
    }

    public Map<String, String> loadAsMap(List<File> filePathList)
    throws PropertiesLoaderException {
        CollectionArgs.checkNotEmptyAndElementsNotNull(filePathList, "filePathList");

        LinkedHashMap<String, String> map = Maps.newLinkedHashMap();



        // Log list of files before beginning so we can fail fast.
        final int size = filePathList.size();
        for (int i = 0; i < size; ++i) {
            File filePath = filePathList.get(i);
            Properties properties = _load(i, size, filePath);
            int keyNum = 0;
            Set<String> propNameSet = properties.stringPropertyNames();
            for (String key: propNameSet) {
                ++keyNum;
                String value = properties.getProperty(key);
                _logDebug("\t[%d of %d]: '%s' -> '%s'", keyNum, propNameSet.size(), key, value);
                if (map.containsKey(key)) {
                    String oldValue = map.get(key);
                    if (oldValue.equals(value)) {
                        continue;
                    }
                    else {
                        _logDebug("\t\tOverrides old value: '%s'", oldValue);
                    }
                }
                map.put(key, value);
            }
        }

        return Collections.unmodifiableMap(map);
    }

    public Properties loadAsProperties(List<File> filePathList)
    throws PropertiesLoaderException {
        Map<String, String> map = loadAsMap(filePathList);
        Properties properties = new Properties();
        properties.putAll(map);
        return properties;
    }

    private void _logInfo(String format, Object... argArr) {
        if (null != _optionalLogger) {
            String msg = String.format(format, argArr);
            _optionalLogger.info(msg);
        }
    }

    private void _logDebug(String format, Object... argArr) {
        if (null != _optionalLogger) {
            String msg = String.format(format, argArr);
            _optionalLogger.debug(msg);
        }
    }

//    private void _logTrace(String format, Object... argArr) {
//        if (null != _optionalLogger) {
//            String msg = String.format(format, argArr);
//            _optionalLogger.trace(msg);
//        }
//    }

    private Properties _load(int index, int size, File filePath)
    throws PropertiesLoaderException {
        _logInfo("[%d of %d] Load properties from file: '%s'",
            1 + index, size, filePath.getAbsolutePath());
        _checkFileExists(index, filePath);
        FileInputStream fis = _newFileInputStream(filePath);
        Properties properties = new Properties();
        try {
            _load(properties, fis);
        }
        finally {
            _closeQuietly(fis);
        }
        return properties;
    }

    private void _checkFileExists(int i, File filePath)
        throws PropertiesLoaderException {
        try {
            PathArgs.checkFileExists(filePath, String.format("filePath[%d]", i));
        }
        catch (PathException e) {
            throw new PropertiesLoaderException(e);
        }
    }

    private FileInputStream _newFileInputStream(File filePath)
        throws PropertiesLoaderException {
        try {
            FileInputStream x = new FileInputStream(filePath);
            return x;
        }
        catch (FileNotFoundException e) {
            throw new PropertiesLoaderException(e);
        }
    }

    private void _load(Properties properties, FileInputStream fis)
        throws PropertiesLoaderException {
        try {
            properties.load(fis);
        }
        catch (IOException e) {
            _closeQuietly(fis);
            throw new PropertiesLoaderException(e);
        }
    }

    private void _closeQuietly(FileInputStream fis) {
        try {
            fis.close();
        }
        catch (IOException ignore) {
            int dummy = 1;  // debug breakpoint
        }
    }
}
