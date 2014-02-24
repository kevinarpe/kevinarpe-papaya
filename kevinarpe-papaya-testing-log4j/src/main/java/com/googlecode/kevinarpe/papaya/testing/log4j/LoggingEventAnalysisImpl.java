package com.googlecode.kevinarpe.papaya.testing.log4j;

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
import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
@NotFullyTested
final class LoggingEventAnalysisImpl
implements LoggingEventAnalysis {

    private final List<LoggingEvent> _loggingEventList;

    public LoggingEventAnalysisImpl(List<LoggingEvent> loggingEventList) {
        CollectionArgs.checkElementsNotNull(loggingEventList, "loggingEventList");
        _loggingEventList = ImmutableList.copyOf(loggingEventList);
    }

    @Override
    public List<LoggingEvent> getLoggingEventList() {
        return _loggingEventList;
    }

    // TODO: Can we match String.format()-[Nihongo]no-format to output?
    // TODO: Can we build a simple system to both log and use to test?
    // ^^^ Ask yourself: What exactly is a formatted log event?
    // ^^^ String, Object[]

    @Override
    public List<LoggingEvent> getLoggingEventListByPredicate(
        Predicate<LoggingEvent> predicate) {
        ObjectArgs.checkNotNull(predicate, "predicate");

        List<LoggingEvent> resultList = Lists.newArrayList();
        for (LoggingEvent loggingEvent : _loggingEventList) {
            Level level = loggingEvent.getLevel();
            if (predicate.apply(loggingEvent)) {
                resultList.add(loggingEvent);
            }
        }
        resultList = Collections.unmodifiableList(resultList);
        return resultList;
    }

    @Override
    public List<LoggingEvent> getLoggingEventListIncluding(Level level, Level... moreLevelArr) {
        AnyOfLogLevel predicate = new AnyOfLogLevel(level, moreLevelArr);
        List<LoggingEvent> x = getLoggingEventListByPredicate(predicate);
        return x;
    }

    @Override
    public List<LoggingEvent> getLoggingEventListIncluding(Set<Level> levelSet) {
        AnyOfLogLevel predicate = new AnyOfLogLevel(levelSet);
        List<LoggingEvent> x = getLoggingEventListByPredicate(predicate);
        return x;
    }

    private static final class AnyOfLogLevel
    implements Predicate<LoggingEvent> {

        private final Set<Level> _levelSet;

        public AnyOfLogLevel(Level level, Level... moreLevelArr) {
            this(_createLevelSet(level, moreLevelArr));
        }

        private static Set<Level> _createLevelSet(Level level, Level... moreLevelArr) {
            ObjectArgs.checkNotNull(moreLevelArr, "moreLevelArr");

            Set<Level> levelSet = new HashSet<Level>(moreLevelArr.length + 1);
            levelSet.add(level);
            levelSet.addAll(Arrays.asList(moreLevelArr));
            return levelSet;
        }

        public AnyOfLogLevel(Set<Level> levelSet) {
            _levelSet = CollectionArgs.checkNotEmptyAndElementsNotNull(levelSet, "levelSet");
        }

        @Override
        public boolean apply(@Nullable LoggingEvent input) {
            Level level = input.getLevel();
            boolean x = _levelSet.contains(level);
            return x;
        }
    }
}
