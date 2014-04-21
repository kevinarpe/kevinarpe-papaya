package com.googlecode.kevinarpe.papaya.testing.logging;

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
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.List;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class LoggingEventAnalyzerImpl
    <
        TLoggingEvent,
        TLoggingEventAttribute extends ILoggingEventAttribute<TLoggingEvent>
    >
implements LoggingEventAnalyzer<TLoggingEvent, TLoggingEventAttribute> {

    private final ImmutableList<TLoggingEvent> _loggingEventList;

    public LoggingEventAnalyzerImpl(List<TLoggingEvent> loggingEventList) {
        CollectionArgs.checkElementsNotNull(loggingEventList, "loggingEventList");

        _loggingEventList = ImmutableList.copyOf(loggingEventList);
    }

    @Override
    public final List<TLoggingEvent> getLoggingEventList() {
        return _loggingEventList;
    }

    @Override
    public final List<TLoggingEvent> getLoggingEventListIncluding(
            Predicate<TLoggingEvent> predicate) {
        ObjectArgs.checkNotNull(predicate, "predicate");

        ImmutableList.Builder<TLoggingEvent> builder = ImmutableList.builder();
        for (TLoggingEvent loggingEvent : _loggingEventList) {
            if (predicate.apply(loggingEvent)) {
                builder.add(loggingEvent);
            }
        }
        List<TLoggingEvent> list = builder.build();
        return list;
    }

    @Override
    public final <TAttributeValue> List<TLoggingEvent> getLoggingEventListIncluding(
            TLoggingEventAttribute attribute,
            TAttributeValue value,
            TAttributeValue... moreValueArr) {
        AnyLoggingEventAttributeValuePredicate<TLoggingEvent> predicate =
            new AnyLoggingEventAttributeValuePredicate<TLoggingEvent>(attribute, value, moreValueArr);
        List<TLoggingEvent> x = getLoggingEventListIncluding(predicate);
        return x;
    }

    @Override
    public final List<TLoggingEvent> getLoggingEventListIncluding(
            TLoggingEventAttribute attribute, Set<?> valueSet) {
        AnyLoggingEventAttributeValuePredicate<TLoggingEvent> predicate =
            new AnyLoggingEventAttributeValuePredicate<TLoggingEvent>(attribute, valueSet);
        List<TLoggingEvent> x = getLoggingEventListIncluding(predicate);
        return x;
    }

    @Override
    public final List<TLoggingEvent> getLoggingEventListExcluding(
            Predicate<TLoggingEvent> predicate) {
        Predicate<TLoggingEvent> notPredicate = Predicates.not(predicate);
        List<TLoggingEvent> x = getLoggingEventListIncluding(notPredicate);
        return x;
    }

    @Override
    public final <TAttributeValue> List<TLoggingEvent> getLoggingEventListExcluding(
            TLoggingEventAttribute attribute,
            TAttributeValue value,
            TAttributeValue... moreValueArr) {
        AnyLoggingEventAttributeValuePredicate<TLoggingEvent> predicate =
            new AnyLoggingEventAttributeValuePredicate<TLoggingEvent>(attribute, value, moreValueArr);
        List<TLoggingEvent> x = getLoggingEventListExcluding(predicate);
        return x;
    }

    @Override
    public final List<TLoggingEvent> getLoggingEventListExcluding(
            TLoggingEventAttribute attribute, Set<?> valueSet) {
        AnyLoggingEventAttributeValuePredicate<TLoggingEvent> predicate =
            new AnyLoggingEventAttributeValuePredicate<TLoggingEvent>(attribute, valueSet);
        List<TLoggingEvent> x = getLoggingEventListExcluding(predicate);
        return x;
    }

    @Override
    public final int hashCode() {
        int result = Objects.hashCode(_loggingEventList);
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof LoggingEventAnalyzerImpl) {
            final LoggingEventAnalyzerImpl other = (LoggingEventAnalyzerImpl) obj;
            result = Objects.equal(_loggingEventList, other._loggingEventList);
        }
        return result;
    }
}
