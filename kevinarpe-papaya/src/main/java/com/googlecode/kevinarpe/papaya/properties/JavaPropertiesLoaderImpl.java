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

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.input.InputSource;
import com.googlecode.kevinarpe.papaya.jdk.properties.JavaPropertiesLoader;
import com.googlecode.kevinarpe.papaya.jdk.properties.JavaPropertiesLoaderUtils;
import com.googlecode.kevinarpe.papaya.jdk.properties.JavaProperty;
import com.googlecode.kevinarpe.papaya.jdk.properties.RandomAccessList;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class JavaPropertiesLoaderImpl {

    // Allow writing?  Not sure.
    public interface JavaPropertiesLoaderPolicy { }

    public static class JavaPropertiesLoaderPolicyDuplicateKeyNotAllowed
    implements JavaPropertiesLoaderPolicy {

        public void apply(List<JavaProperty> propertyList) {
            HashMultimap<String, String> multimap = HashMultimap.create();
            List<String> errorList = Lists.newArrayList();
            final int size = propertyList.size();
            for (int index = 0; index < size; ++index) {
                JavaProperty property = propertyList.get(index);
                if (multimap.containsKey(property.getKey())) {

                }
//                if (!keySet.add(property.getKey())) {
//                    errorList.add(String.format("[%d of %d] Duplicate key"));
//                }
//                _logDebug("\t[%d of %d]: '%s' -> '%s'",
//                    1 + index, size, property.getKey(), property.getValue());
            }
        }
    }

    // Convert these to separate classes.
//    public static enum PropertiesLoaderPolicy {
//        DUPLICATE_KEY_ALLOWED,
//        DUPLICATE_KEY_NOT_ALLOWED,
//        DUPLICATE_ENTRY_ALLOWED,
//        DUPLICATE_ENTRY_NOT_ALLOWED,
//    }
    // What about empty/null keys or values?
    // What about value leading + trailing whitespace?

    public static final Logger DEFAULT_OPTIONAL_LOGGER = null;
    private static final List<JavaPropertiesLoaderPolicy> DEFAULT_POLICY_LIST = ImmutableList.of();

    private final Logger _optionalLogger;
    private final List<JavaPropertiesLoaderPolicy> _policyList;
    private final JavaPropertiesLoader _javaPropertiesLoader;

    public JavaPropertiesLoaderImpl() {
        this(DEFAULT_OPTIONAL_LOGGER);
    }

    public JavaPropertiesLoaderImpl(Logger optionalLogger) {
        this(optionalLogger, DEFAULT_POLICY_LIST, JavaPropertiesLoaderUtils.INSTANCE.getInstance());
    }

    JavaPropertiesLoaderImpl(
        Logger optionalLogger,
        List<JavaPropertiesLoaderPolicy> optionalPolicyList,
        JavaPropertiesLoader javaPropertiesLoader) {
        _optionalLogger = optionalLogger;
        _policyList = optionalPolicyList;
        _javaPropertiesLoader =
            ObjectArgs.checkNotNull(javaPropertiesLoader, "javaPropertiesLoader");
    }

    public JavaPropertiesLoaderImpl withEmptyPolicyList() {
        JavaPropertiesLoaderImpl x = withPolicyList(DEFAULT_POLICY_LIST);
        return x;
    }

    public JavaPropertiesLoaderImpl withPolicyList(List<JavaPropertiesLoaderPolicy> policyList) {
        CollectionArgs.checkElementsNotNull(policyList, "policyList");
        JavaPropertiesLoaderImpl x =
            new JavaPropertiesLoaderImpl(_optionalLogger, policyList, _javaPropertiesLoader);
        return x;
    }

    public Map<String, String> loadAsMap(List<InputSource> inputSourceList)
    throws JavaPropertiesLoaderException {
        CollectionArgs.checkNotEmptyAndElementsNotNull(inputSourceList, "inputSourceList");

        LinkedHashMap<String, String> map = Maps.newLinkedHashMap();
        final int size = inputSourceList.size();
        for (int index = 0; index < size; ++index) {
            InputSource inputSource = inputSourceList.get(index);
            _checkInputSource(index, inputSource);
            _logInfo("[%d of %d] Load properties: %s", 1 + index, size, inputSource);
            RandomAccessList<JavaProperty> propertyList = _loadPropertyList(inputSource);
            _applyPolicies(map, propertyList);
            _mergeProperties(map, propertyList);
        }
        return map;
    }

    private void _checkInputSource(int index, InputSource inputSource) {
        if (null == inputSource.getByteStream() && null == inputSource.getCharacterStream()) {
            throw new IllegalArgumentException(
                String.format(
                    "InputSource[%d] has a null byte stream and character stream: %s",
                    index, inputSource));
        }
    }

    private RandomAccessList<JavaProperty> _loadPropertyList(InputSource inputSource)
    throws JavaPropertiesLoaderException {
        RandomAccessList<JavaProperty> entryList = null;
        try {
            if (null == inputSource.getCharacterStream()) {
                entryList = _javaPropertiesLoader.load(inputSource.getByteStream());
            }
            else {
                entryList = _javaPropertiesLoader.load(inputSource.getCharacterStream());
            }
        }
        catch (IOException e) {
            throw new JavaPropertiesLoaderException(e);
        }
        finally {
            _closeQuietly(inputSource);
        }
        return entryList;
    }

    private void _applyPolicies(
            LinkedHashMap<String, String> map, RandomAccessList<JavaProperty> propertyList) {
        for (JavaPropertiesLoaderPolicy policy : _policyList) {
            // TODO: Apply policy.
        }
    }

    private void _mergeProperties(
            LinkedHashMap<String, String> map, RandomAccessList<JavaProperty> propertyList) {
        _logDebug("Loaded %d properties", propertyList.size());
        final int size = propertyList.size();
        for (int index = 0; index < size; ++index) {
            JavaProperty property = propertyList.get(index);
            _logDebug("\t[%d of %d]: '%s' -> '%s'",
                1 + index, size, property.getKey(), property.getValue());
            if (map.containsKey(property.getKey())) {
                String oldValue = map.get(property.getKey());
                if (oldValue.equals(property.getValue())) {
                    continue;
                }
                else {
                    _logDebug("\t\tOverrides old value: '%s'", oldValue);
                }
            }
            map.put(property.getKey(), property.getValue());
        }
    }

    private void _closeQuietly(InputSource inputSource) {
        try {
            if (null == inputSource.getCharacterStream()) {
                inputSource.getByteStream().close();
            }
            else {
                inputSource.getCharacterStream().close();
            }
        }
        catch (IOException ignore) {
            int dummy = 1;  // debug breakpoint
        }
    }

    public Properties loadAsProperties(List<InputSource> inputSourceList)
    throws JavaPropertiesLoaderException {
        Map<String, String> map = loadAsMap(inputSourceList);
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
}
