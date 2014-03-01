package com.googlecode.kevinarpe.papaya.logging.slf4j;

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

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class LoggingEventAnalyzerImpl
implements LoggingEventAnalyzer {

    private final List<SLF4JLoggingEvent> _loggingEventList;

    public LoggingEventAnalyzerImpl(List<SLF4JLoggingEvent> loggingEventList) {
        CollectionArgs.checkElementsNotNull(loggingEventList, "loggingEventList");
        _loggingEventList = ImmutableList.copyOf(loggingEventList);
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventList() {
        return _loggingEventList;
    }

    // TODO: LAST: Can we make this class somewhat generic with LoggingEventAnalysisImpl?
    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListIncluding(
            Predicate<SLF4JLoggingEvent> predicate) {
        return null;
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListIncluding(
            SLF4JLogLevel level, SLF4JLogLevel... moreSLF4JLogLevelArr) {
        return null;
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListIncluding(Set<SLF4JLogLevel> levelSet) {
        return null;
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListExcluding(
            SLF4JLogLevel level, SLF4JLogLevel... moreLevelArr) {
        return null;
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListExcluding(Set<SLF4JLogLevel> levelSet) {
        return null;
    }

    static final class AnyOfLogLevel
    implements Predicate<SLF4JLoggingEvent> {

        private final Set<SLF4JLogLevel> _logLevelSet;

        public AnyOfLogLevel(SLF4JLogLevel logLevel, SLF4JLogLevel... moreLogLevelArr) {
            this(_createLogLevelSet(logLevel, moreLogLevelArr));
        }

        private static Set<SLF4JLogLevel> _createLogLevelSet(
                SLF4JLogLevel logLevel, SLF4JLogLevel... moreLevelArr) {
            ObjectArgs.checkNotNull(moreLevelArr, "moreLevelArr");

            Set<SLF4JLogLevel> levelSet = new HashSet<SLF4JLogLevel>(moreLevelArr.length + 1);
            levelSet.add(logLevel);
            levelSet.addAll(Arrays.asList(moreLevelArr));
            return levelSet;
        }

        public AnyOfLogLevel(Set<SLF4JLogLevel> levelSet) {
            _logLevelSet = CollectionArgs.checkNotEmptyAndElementsNotNull(levelSet, "levelSet");
        }

        @Override
        public boolean apply(@Nullable SLF4JLoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            SLF4JLogLevel level = loggingEvent.getLevel();
            boolean x = _logLevelSet.contains(level);
            return x;
        }
    }
}
