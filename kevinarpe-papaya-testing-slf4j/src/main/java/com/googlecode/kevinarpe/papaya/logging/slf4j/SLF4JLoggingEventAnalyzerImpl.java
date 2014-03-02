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
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

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
public final class SLF4JLoggingEventAnalyzerImpl
implements SLF4JLoggingEventAnalyzer {

    private final List<SLF4JLoggingEvent> _loggingEventList;

    public SLF4JLoggingEventAnalyzerImpl(List<SLF4JLoggingEvent> loggingEventList) {
        CollectionArgs.checkElementsNotNull(loggingEventList, "loggingEventList");
        _loggingEventList = ImmutableList.copyOf(loggingEventList);
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventList() {
        return _loggingEventList;
    }

    // TODO: LAST: Can we make this class somewhat generic with LoggingEventAnalysisImpl?
    // TODO: Make a wrapper for log4j.LoggingEvent -> SLF4JLoggingEvent?  Necessary?
    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListIncluding(
            Predicate<SLF4JLoggingEvent> predicate) {
        ObjectArgs.checkNotNull(predicate, "predicate");

        List<SLF4JLoggingEvent> resultList = Lists.newArrayList();
        for (SLF4JLoggingEvent loggingEvent : _loggingEventList) {
            if (predicate.apply(loggingEvent)) {
                resultList.add(loggingEvent);
            }
        }
        resultList = Collections.unmodifiableList(resultList);
        return resultList;
    }

    @Override
    public <T> List<SLF4JLoggingEvent> getLoggingEventListIncluding(
            SLF4JLoggingEventAttribute attribute, T value, T... moreValueArr) {
        AnyOfLogLevel predicate = new AnyOfLogLevel(attribute, value, moreValueArr);
        List<SLF4JLoggingEvent> x = getLoggingEventListIncluding(predicate);
        return x;
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListIncluding(
            SLF4JLoggingEventAttribute attribute, Set<?> valueSet) {
        AnyOfLogLevel predicate = new AnyOfLogLevel(attribute, valueSet);
        List<SLF4JLoggingEvent> x = getLoggingEventListIncluding(predicate);
        return x;
    }

    @Override
    public <T> List<SLF4JLoggingEvent> getLoggingEventListExcluding(
            SLF4JLoggingEventAttribute attribute, T value, T... moreValueArr) {
        AnyOfLogLevel predicate = new AnyOfLogLevel(attribute, value, moreValueArr);
        Predicate<SLF4JLoggingEvent> notPredicate = Predicates.not(predicate);
        List<SLF4JLoggingEvent> x = getLoggingEventListIncluding(notPredicate);
        return x;
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListExcluding(
            SLF4JLoggingEventAttribute attribute, Set<?> valueSet) {
        AnyOfLogLevel predicate = new AnyOfLogLevel(attribute, valueSet);
        Predicate<SLF4JLoggingEvent> notPredicate = Predicates.not(predicate);
        List<SLF4JLoggingEvent> x = getLoggingEventListIncluding(notPredicate);
        return x;
    }

    static final class AnyOfLogLevel
    implements Predicate<SLF4JLoggingEvent> {

        private final SLF4JLoggingEventAttribute _attribute;
        private final Set<?> _valueSet;

        public <T> AnyOfLogLevel(
                SLF4JLoggingEventAttribute attribute, T logLevel, T... moreLogLevelArr) {
            this(attribute, _createSet(logLevel, moreLogLevelArr));
        }

        private static <T> Set<T> _createSet(T value, T... moreValueArr) {
            ObjectArgs.checkNotNull(moreValueArr, "moreValueArr");

            Set<T> set = new HashSet<T>(moreValueArr.length + 1);
            set.add(value);
            set.addAll(Arrays.asList(moreValueArr));
            return set;
        }

        public AnyOfLogLevel(SLF4JLoggingEventAttribute attribute, Set<?> valueSet) {
            _attribute = ObjectArgs.checkNotNull(attribute, "attribute");
            _valueSet = CollectionArgs.checkNotEmptyAndElementsNotNull(valueSet, "valueSet");
        }

        @Override
        public boolean apply(@Nullable SLF4JLoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Object value = _attribute.getValue(loggingEvent);
            boolean x = _valueSet.contains(value);
            return x;
        }
    }
}
