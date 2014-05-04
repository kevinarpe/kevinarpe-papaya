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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.input.IInputSource2Utils;
import com.googlecode.kevinarpe.papaya.input.InputSource2;
import com.googlecode.kevinarpe.papaya.input.InputSource2Utils;
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

    private final PropertiesLoaderPolicy _optionalPolicy;
    private final JdkPropertiesLoader _jdkPropertiesLoader;
    private final IInputSource2Utils _inputSource2Utils;
    private final PropertiesMerger _propertiesMerger;
    private final Logger _logger;

    public PropertiesLoaderImpl() {
        this(_newLogger(LoggerFactory.getILoggerFactory()));
    }

    private PropertiesLoaderImpl(Logger logger) {
        this(
            PropertiesLoaderUtils.DEFAULT_POLICY,
            JdkPropertiesLoaderUtils.INSTANCE.getInstance(),
            InputSource2Utils.INSTANCE,
            new PropertiesMergerImpl(logger),
            logger);
    }

    PropertiesLoaderImpl(
            JdkPropertiesLoader jdkPropertiesLoader,
            IInputSource2Utils inputSource2Utils,
            PropertiesMerger propertiesMerger,
            ILoggerFactory loggerFactory) {
        this(
            PropertiesLoaderUtils.DEFAULT_POLICY,
            ObjectArgs.checkNotNull(jdkPropertiesLoader, "javaPropertiesLoader"),
            ObjectArgs.checkNotNull(inputSource2Utils, "inputSource2Utils"),
            ObjectArgs.checkNotNull(propertiesMerger, "propertiesMerger"),
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
            IInputSource2Utils inputSource2Utils,
            PropertiesMerger propertiesMerger,
            Logger logger) {
        _optionalPolicy = optionalPolicy;
        _jdkPropertiesLoader = jdkPropertiesLoader;
        _inputSource2Utils = inputSource2Utils;
        _propertiesMerger = propertiesMerger;
        _logger = logger;
    }

    @Override
    public PropertiesLoaderPolicy withOptionalPolicy() {
        return _optionalPolicy;
    }

    @Override
    public PropertiesLoaderImpl withOptionalPolicy(PropertiesLoaderPolicy optionalPolicy) {
        PropertiesLoaderImpl x =
            new PropertiesLoaderImpl(
                optionalPolicy,
                _jdkPropertiesLoader,
                _inputSource2Utils,
                _propertiesMerger,
                _logger);
        return x;
    }

    @Override
    public Properties loadAsProperties(List<InputSource2> inputSourceList)
    throws PropertiesLoaderException {
        Map<String, String> map = loadAsMap(inputSourceList);
        Properties properties = new Properties();
        properties.putAll(map);
        return properties;
    }

    @Override
    public Map<String, String> loadAsMap(List<InputSource2> inputSourceList)
    throws PropertiesLoaderException {
        _inputSource2Utils.checkValid(inputSourceList, "inputSourceList");

        LinkedHashMap<String, String> map = Maps.newLinkedHashMap();
        final int size = inputSourceList.size();
        for (int index = 0; index < size; ++index) {
            InputSource2 inputSource = inputSourceList.get(index);
            _logger.info("[%d of %d] Load properties: %s", 1 + index, size, inputSource);
            RandomAccessList<JdkProperty> propertyList = _loadPropertyList(inputSource);
            if (null != _optionalPolicy) {
                _optionalPolicy.apply(propertyList);
            }
            _propertiesMerger.merge(map, propertyList);
        }
        return map;
    }

    private RandomAccessList<JdkProperty> _loadPropertyList(InputSource2 inputSource)
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
            _inputSource2Utils.closeQuietly(inputSource);
        }
    }
}
