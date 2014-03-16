package com.googlecode.kevinarpe.papaya.logging.slf4j.mock;

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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class SLF4JMockLoggerFactoryImpl
implements SLF4JMockLoggerFactory {

//    public static final void main(String[] argArr) {
//        Logger logger = LoggerFactory.getLogger(SLF4JMockLoggerFactoryImpl.class);
//        try {
//            a();
//        }
//        catch (Exception e) {
//            logger.info("message", e);
//        }
//    }
//
//    private static void a() {
//        b();
//    }
//
//    private static void b() {
//        c();
//    }
//
//    private static void c() {
//        throw new NullPointerException("exception");
//    }

    private final SLF4JMockLoggerConfigImpl _config;
    private final Map<String, SLF4JMockLoggerImpl> _nameToLoggerMap;

    public SLF4JMockLoggerFactoryImpl() {
        _config = new SLF4JMockLoggerConfigImpl();
        _nameToLoggerMap = Maps.newHashMap();
    }

    public SLF4JMockLoggerFactoryImpl(SLF4JMockLoggerFactoryImpl other) {
        ObjectArgs.checkNotNull(other, "other");

        _config = new SLF4JMockLoggerConfigImpl(other._config);
        _nameToLoggerMap = new HashMap<String, SLF4JMockLoggerImpl>(other._nameToLoggerMap);
    }

    @Override
    public SLF4JMockLoggerConfig getConfig() {
        return _config;
    }

    @Override
    public SLF4JMockLoggerImpl getLogger(String name) {
        SLF4JMockLoggerImpl logger = _nameToLoggerMap.get(name);
        if (null == logger) {
            // Replace with factory?
            logger = new SLF4JMockLoggerImpl(name, _config);
            _nameToLoggerMap.put(name, logger);
        }
        return logger;
    }
}