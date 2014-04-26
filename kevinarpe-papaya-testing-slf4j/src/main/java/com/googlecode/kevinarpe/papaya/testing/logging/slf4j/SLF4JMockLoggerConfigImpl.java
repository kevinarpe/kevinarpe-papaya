package com.googlecode.kevinarpe.papaya.testing.logging.slf4j;

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

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.slf4j.Marker;

import java.util.HashMap;
import java.util.Map;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
@FullyTested
final class SLF4JMockLoggerConfigImpl
implements SLF4JMockLoggerConfig {

    public static final Marker DEFAULT_MARKER = SLF4JMarkerNone.INSTANCE;
    public static final boolean DEFAULT_IS_ENABLED = true;

    private static final ImmutableMap<SLF4JLogLevel, Boolean> DEFAULT_LOG_LEVEL_TO_IS_ENABLED_MAP;

    static {
        ImmutableMap.Builder<SLF4JLogLevel, Boolean> builder = ImmutableMap.builder();
        SLF4JLogLevel[] logLevelArr = SLF4JLogLevel.values();
        for (SLF4JLogLevel logLevel : logLevelArr) {
            builder.put(logLevel, DEFAULT_IS_ENABLED);
        }
        DEFAULT_LOG_LEVEL_TO_IS_ENABLED_MAP = builder.build();
    }

    private final Map<Marker, Map<SLF4JLogLevel, Boolean>> _marker_To_logLevelToIsEnabledMap_Map;

    public SLF4JMockLoggerConfigImpl() {
        _marker_To_logLevelToIsEnabledMap_Map = Maps.newHashMap();
        _marker_To_logLevelToIsEnabledMap_Map.put(
            SLF4JMarkerNone.INSTANCE,
            new HashMap<SLF4JLogLevel, Boolean>(DEFAULT_LOG_LEVEL_TO_IS_ENABLED_MAP));
    }

    public SLF4JMockLoggerConfigImpl(SLF4JMockLoggerConfigImpl other) {
        ObjectArgs.checkNotNull(other, "other");

        _marker_To_logLevelToIsEnabledMap_Map = Maps.newHashMap();
        for (Map.Entry<Marker, Map<SLF4JLogLevel, Boolean>> entry
                : other._marker_To_logLevelToIsEnabledMap_Map.entrySet()) {
            Marker marker = entry.getKey();
            Map<SLF4JLogLevel, Boolean> map = entry.getValue();
            _marker_To_logLevelToIsEnabledMap_Map.put(
                marker, new HashMap<SLF4JLogLevel, Boolean>(map));
        }
    }

    @Override
    public boolean isEnabled(SLF4JLogLevel logLevel) {
        boolean x = isEnabled(DEFAULT_MARKER, logLevel);
        return x;
    }

    @Override
    public boolean isEnabled(Marker marker, SLF4JLogLevel logLevel) {
        ObjectArgs.checkNotNull(marker, "marker");
        ObjectArgs.checkNotNull(logLevel, "logLevel");

        Map<SLF4JLogLevel, Boolean> map = _marker_To_logLevelToIsEnabledMap_Map.get(marker);
        if (null == map) {
            return DEFAULT_IS_ENABLED;
        }
        boolean x = map.get(logLevel);
        return x;
    }

    @Override
    public boolean setEnabled(SLF4JLogLevel logLevel, boolean isEnabled) {
        boolean x = setEnabled(DEFAULT_MARKER, logLevel, isEnabled);
        return x;
    }

    @Override
    public boolean setEnabled(Marker marker, SLF4JLogLevel logLevel, boolean isEnabled) {
        ObjectArgs.checkNotNull(marker, "marker");
        ObjectArgs.checkNotNull(logLevel, "logLevel");

        Map<SLF4JLogLevel, Boolean> map = _marker_To_logLevelToIsEnabledMap_Map.get(marker);
        if (null == map) {
            map = new HashMap<SLF4JLogLevel, Boolean>(DEFAULT_LOG_LEVEL_TO_IS_ENABLED_MAP);
            _marker_To_logLevelToIsEnabledMap_Map.put(marker, map);
        }
        boolean last = map.put(logLevel, isEnabled);
        return last;
    }

    @Override
    public SLF4JMockLoggerConfigImpl copy() {
        SLF4JMockLoggerConfigImpl x = new SLF4JMockLoggerConfigImpl(this);
        return x;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(_marker_To_logLevelToIsEnabledMap_Map);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof SLF4JMockLoggerConfigImpl) {
            final SLF4JMockLoggerConfigImpl other = (SLF4JMockLoggerConfigImpl) obj;
            result =
                Objects.equal(
                    _marker_To_logLevelToIsEnabledMap_Map,
                    other._marker_To_logLevelToIsEnabledMap_Map);
        }
        return result;
    }
}
