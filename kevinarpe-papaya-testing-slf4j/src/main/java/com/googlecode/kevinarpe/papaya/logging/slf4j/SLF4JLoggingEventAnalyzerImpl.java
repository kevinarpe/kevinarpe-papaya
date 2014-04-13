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
public final class SLF4JLoggingEventAnalyzerImpl
implements SLF4JLoggingEventAnalyzer {

    private final ImmutableList<SLF4JLoggingEvent> _loggingEventList;

    public SLF4JLoggingEventAnalyzerImpl(List<SLF4JLoggingEvent> loggingEventList) {
        CollectionArgs.checkElementsNotNull(loggingEventList, "loggingEventList");

        _loggingEventList = ImmutableList.copyOf(loggingEventList);
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventList() {
        return _loggingEventList;
    }

    // TODO: Can we make this class somewhat generic with LoggingEventAnalysisImpl?
    // TODO: Make a wrapper for log4j.LoggingEvent -> SLF4JLoggingEvent?  Necessary?
    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListIncluding(
            Predicate<SLF4JLoggingEvent> predicate) {
        ObjectArgs.checkNotNull(predicate, "predicate");

        ImmutableList.Builder<SLF4JLoggingEvent> builder = ImmutableList.builder();
        for (SLF4JLoggingEvent loggingEvent : _loggingEventList) {
            if (predicate.apply(loggingEvent)) {
                builder.add(loggingEvent);
            }
        }
        List<SLF4JLoggingEvent> list = builder.build();
        return list;
    }

    @Override
    public <TAttributeValue> List<SLF4JLoggingEvent> getLoggingEventListIncluding(
            SLF4JLoggingEventAttribute attribute,
            TAttributeValue value,
            TAttributeValue... moreValueArr) {
        AnyAttributeValuePredicate predicate =
            new AnyAttributeValuePredicate(attribute, value, moreValueArr);
        List<SLF4JLoggingEvent> x = getLoggingEventListIncluding(predicate);
        return x;
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListIncluding(
            SLF4JLoggingEventAttribute attribute, Set<?> valueSet) {
        AnyAttributeValuePredicate predicate = new AnyAttributeValuePredicate(attribute, valueSet);
        List<SLF4JLoggingEvent> x = getLoggingEventListIncluding(predicate);
        return x;
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListExcluding(
            Predicate<SLF4JLoggingEvent> predicate) {
        Predicate<SLF4JLoggingEvent> notPredicate = Predicates.not(predicate);
        List<SLF4JLoggingEvent> x = getLoggingEventListIncluding(notPredicate);
        return x;
    }

    @Override
    public <TAttributeValue> List<SLF4JLoggingEvent> getLoggingEventListExcluding(
            SLF4JLoggingEventAttribute attribute,
            TAttributeValue value,
            TAttributeValue... moreValueArr) {
        AnyAttributeValuePredicate predicate =
            new AnyAttributeValuePredicate(attribute, value, moreValueArr);
        List<SLF4JLoggingEvent> x = getLoggingEventListExcluding(predicate);
        return x;
    }

    @Override
    public List<SLF4JLoggingEvent> getLoggingEventListExcluding(
            SLF4JLoggingEventAttribute attribute, Set<?> valueSet) {
        AnyAttributeValuePredicate predicate = new AnyAttributeValuePredicate(attribute, valueSet);
        List<SLF4JLoggingEvent> x = getLoggingEventListExcluding(predicate);
        return x;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(_loggingEventList);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof SLF4JLoggingEventAnalyzerImpl) {
            final SLF4JLoggingEventAnalyzerImpl other = (SLF4JLoggingEventAnalyzerImpl) obj;
            result = Objects.equal(_loggingEventList, other._loggingEventList);
        }
        return result;
    }
}
