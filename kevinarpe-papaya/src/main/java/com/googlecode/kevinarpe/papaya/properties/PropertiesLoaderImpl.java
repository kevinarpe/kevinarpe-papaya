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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.input.InputSource;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkPropertiesLoader;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkPropertiesLoaderUtils;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;
import com.googlecode.kevinarpe.papaya.jdk.properties.RandomAccessList;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: LAST: Test & doc me
final class PropertiesLoaderImpl
implements PropertiesLoader {

    static final PropertiesLoaderPolicy DEFAULT_POLICY =
        PropertiesLoaderPolicyImpl.INSTANCE;

    private final PropertiesLoaderPolicy _optionalPolicy;
    private final JdkPropertiesLoader _jdkPropertiesLoader;
    private final Logger _logger;

    public PropertiesLoaderImpl() {
        this(JdkPropertiesLoaderUtils.INSTANCE.getInstance(), LoggerFactory.getILoggerFactory());
    }

    public PropertiesLoaderImpl(
        JdkPropertiesLoader jdkPropertiesLoader, ILoggerFactory loggerFactory) {
        this(
            DEFAULT_POLICY,
            ObjectArgs.checkNotNull(jdkPropertiesLoader, "javaPropertiesLoader"),
            _newLogger(loggerFactory));
    }

    private static Logger _newLogger(ILoggerFactory loggerFactory) {
        ObjectArgs.checkNotNull(loggerFactory, "loggerFactory");

        Logger x = loggerFactory.getLogger(PropertiesLoaderImpl.class.getName());
        return x;
    }

    private PropertiesLoaderImpl(
        PropertiesLoaderPolicy optionalPolicy,
        JdkPropertiesLoader jdkPropertiesLoader,
        Logger logger) {
        _optionalPolicy = optionalPolicy;
        _jdkPropertiesLoader =
            ObjectArgs.checkNotNull(jdkPropertiesLoader, "javaPropertiesLoader");
        _logger = logger;
    }

    @Override
    public PropertiesLoaderImpl withOptionalPolicy(PropertiesLoaderPolicy optionalPolicy) {
        PropertiesLoaderImpl x =
            new PropertiesLoaderImpl(optionalPolicy, _jdkPropertiesLoader, _logger);
        return x;
    }

    @Override
    public Properties loadAsProperties(List<InputSource> inputSourceList)
    throws PropertiesLoaderException {
        Map<String, String> map = loadAsMap(inputSourceList);
        Properties properties = new Properties();
        properties.putAll(map);
        return properties;
    }

    @Override
    public Map<String, String> loadAsMap(List<InputSource> inputSourceList)
    throws PropertiesLoaderException {
        CollectionArgs.checkNotEmptyAndElementsNotNull(inputSourceList, "inputSourceList");

        LinkedHashMap<String, String> map = Maps.newLinkedHashMap();
        final int size = inputSourceList.size();
        for (int index = 0; index < size; ++index) {
            InputSource inputSource = inputSourceList.get(index);
            _checkInputSource(index, inputSource);
            _logger.info("[%d of %d] Load properties: %s", 1 + index, size, inputSource);
            RandomAccessList<JdkProperty> propertyList = _loadPropertyList(inputSource);
            if (null != _optionalPolicy) {
                _optionalPolicy.apply(propertyList);
            }
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

    private RandomAccessList<JdkProperty> _loadPropertyList(InputSource inputSource)
    throws PropertiesLoaderException {
        try {
            if (null == inputSource.getCharacterStream()) {
                RandomAccessList<JdkProperty> x =
                    _jdkPropertiesLoader.load(inputSource.getByteStream());
                return x;
            }
            else {
                RandomAccessList<JdkProperty> x =
                    _jdkPropertiesLoader.load(inputSource.getCharacterStream());
                return x;
            }
        }
        catch (IOException e) {
            throw new PropertiesLoaderException(e);
        }
        finally {
            _closeQuietly(inputSource);
        }
    }

    private void _mergeProperties(
            LinkedHashMap<String, String> map, RandomAccessList<JdkProperty> propertyList) {
        _logger.debug("Loaded %d properties", propertyList.size());
        final int size = propertyList.size();
        for (int index = 0; index < size; ++index) {
            JdkProperty property = propertyList.get(index);
            _logger.debug("\t[%d of %d]: '%s' -> '%s'",
                1 + index, size, property.getKey(), property.getValue());
            if (map.containsKey(property.getKey())) {
                String oldValue = map.get(property.getKey());
                if (oldValue.equals(property.getValue())) {
                    continue;
                }
                else {
                    _logger.debug("\t\tOverrides old value: '%s'", oldValue);
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
}
