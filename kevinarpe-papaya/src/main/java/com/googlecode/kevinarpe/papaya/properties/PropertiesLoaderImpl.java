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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.builder.MapBuilder;
import com.googlecode.kevinarpe.papaya.container.builder.MapFactory;
import com.googlecode.kevinarpe.papaya.container.builder.PropertiesBuilder;
import com.googlecode.kevinarpe.papaya.input.IInputSource2Utils;
import com.googlecode.kevinarpe.papaya.input.InputSource2;
import com.googlecode.kevinarpe.papaya.input.InputSource2Utils;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkPropertiesLoaderUtils;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;
import com.googlecode.kevinarpe.papaya.jdk.properties.RandomAccessList;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class PropertiesLoaderImpl
implements PropertiesLoader {

    private final PropertiesLoaderPolicy _policy;
    private final JdkPropertiesLoaderHelper _jdkPropertiesLoaderHelper;
    private final IInputSource2Utils _inputSource2Utils;
    private final PropertiesMerger _propertiesMerger;
    private final Logger _logger;

    public PropertiesLoaderImpl() {
        this(_newLogger(LoggerFactory.getILoggerFactory()));
    }

    private PropertiesLoaderImpl(Logger logger) {
        this(
            PropertiesLoaderUtils.DEFAULT_POLICY,
            new JdkPropertiesLoaderHelperImpl(
                JdkPropertiesLoaderUtils.INSTANCE.getInstance(),
                InputSource2Utils.INSTANCE),
            InputSource2Utils.INSTANCE,
            new PropertiesMergerImpl(logger),
            logger);
    }

    PropertiesLoaderImpl(
            JdkPropertiesLoaderHelper jdkPropertiesLoaderHelper,
            IInputSource2Utils inputSource2Utils,
            PropertiesMerger propertiesMerger,
            ILoggerFactory loggerFactory) {
        this(
            PropertiesLoaderUtils.DEFAULT_POLICY,
            ObjectArgs.checkNotNull(jdkPropertiesLoaderHelper, "jdkPropertiesLoaderHelper"),
            ObjectArgs.checkNotNull(inputSource2Utils, "inputSource2Utils"),
            ObjectArgs.checkNotNull(propertiesMerger, "propertiesMerger"),
            _newLogger(loggerFactory));
    }

    private static Logger _newLogger(ILoggerFactory loggerFactory) {
        ObjectArgs.checkNotNull(loggerFactory, "loggerFactory");

        Logger x = loggerFactory.getLogger(PropertiesLoaderImpl.class.getName());
        return x;
    }

    PropertiesLoaderImpl(
            PropertiesLoaderPolicy policy,
            JdkPropertiesLoaderHelper jdkPropertiesLoaderHelper,
            IInputSource2Utils inputSource2Utils,
            PropertiesMerger propertiesMerger,
            Logger logger) {
        _policy = policy;
        _jdkPropertiesLoaderHelper = jdkPropertiesLoaderHelper;
        _inputSource2Utils = inputSource2Utils;
        _propertiesMerger = propertiesMerger;
        _logger = logger;
    }

    /** {@inheritDoc} */
    @Override
    public PropertiesLoaderPolicy withPolicy() {
        return _policy;
    }

    /** {@inheritDoc} */
    @Override
    public PropertiesLoaderImpl withPolicy(PropertiesLoaderPolicy policy) {
        PropertiesLoaderImpl x =
            new PropertiesLoaderImpl(
                ObjectArgs.checkNotNull(policy, "policy"),
                _jdkPropertiesLoaderHelper,
                _inputSource2Utils,
                _propertiesMerger,
                _logger);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public Properties load(List<? extends InputSource2> inputSourceList)
    throws PropertiesLoaderException {
        PropertiesBuilder builder = PropertiesBuilder.create();
        _load(inputSourceList, builder);
        Properties prop = builder.build();
        return prop;
    }

    /** {@inheritDoc} */
    @Override
    public
    <
        TMap extends Map<String, String>,
        TMapBuilder extends MapBuilder<String, String, TMap>,
        TMapBuilderFactory extends MapFactory<String, String, TMap, TMapBuilder>
    >
    TMap load(
            List<? extends InputSource2> inputSourceList, TMapBuilderFactory mapBuilderFactory)
    throws PropertiesLoaderException {
        TMapBuilder mapBuilder = mapBuilderFactory.builder();
        _load(inputSourceList, mapBuilder);
        TMap map = mapBuilder.build();
        return map;
    }

    private void _load(
            List<? extends InputSource2> inputSourceList, Map<? super String, ? super String> map)
    throws PropertiesLoaderException {
        _inputSource2Utils.checkValid(inputSourceList, "inputSourceList");

        final int size = inputSourceList.size();
        for (int index = 0; index < size; ++index) {
            InputSource2 inputSource = inputSourceList.get(index);
            _logger.info("[%d of %d] Load properties: %s", 1 + index, size, inputSource);
            RandomAccessList<JdkProperty> propertyList =
                _jdkPropertiesLoaderHelper.loadPropertyList(inputSource);
            _policy.apply(propertyList);
            _propertiesMerger.merge(map, propertyList);
        }
    }
}
