package com.googlecode.kevinarpe.papaya.testing.logging;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.Lists2;

import java.util.Collections;
import java.util.Set;

/**
 * Predicate to match logging events by attribute and allowed values.  Specifically designed for
 * use with {@link CapturingLogger}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see CapturingLogger
 */
@FullyTested
public final class AnyLoggingEventAttributeValuePredicate<TLoggingEvent>
implements Predicate<TLoggingEvent> {

    private final ILoggingEventAttribute<TLoggingEvent> _attribute;
    private final Set<Object> _valueSet;

    /**
     * Creates a new predicate from a logging event attribute and a series of allowed values.
     *
     * @param attribute
     *        logging event attribute
     * @param value
     *        any value to match.  May be {@code null}
     * @param moreValueArr
     *        more values to match.  May contain {@code null}
     *
     * @throws NullPointerException
     *         if {@code attribute} or {@code moreValueArr} is {@code null}
     */
    public AnyLoggingEventAttributeValuePredicate(
            ILoggingEventAttribute<TLoggingEvent> attribute,
            Object value,
            Object... moreValueArr) {
        _attribute = ObjectArgs.checkNotNull(attribute, "attribute");
        _valueSet =
            Sets.<Object>newHashSet(
                Lists2.newUnmodifiableListFromOneOrMoreValues(value, moreValueArr));
    }

    /**
     * Creates a new predicate from a logging event attribute and a set of allowed values.
     *
     * @param attribute
     *        logging event attribute
     * @param valueSet
     *        values to match.  May contain {@code null}
     *
     * @throws NullPointerException
     *         if {@code attribute} or {@code valueSet} is {@code null}
     */
    public AnyLoggingEventAttributeValuePredicate(
            ILoggingEventAttribute<TLoggingEvent> attribute, Set<?> valueSet) {
        _attribute = ObjectArgs.checkNotNull(attribute, "attribute");
        _valueSet = Sets.<Object>newHashSet(CollectionArgs.checkNotEmpty(valueSet, "valueSet"));
    }

    /**
     * @return logging event attribute to perform the matching
     */
    public ILoggingEventAttribute getAttribute() {
        return _attribute;
    }

    /**
     * @return unmodifiable set of allowed values
     *
     * @see Collections#unmodifiableSet(Set)
     */
    public Set<Object> getValueSet() {
        return Collections.unmodifiableSet(_valueSet);
    }

    /**
     * Given a logging event, retrieve an attribute value and compare to allowed values.
     * <hr>
     * Inherited docs:
     * <br>
     * {@inheritDoc}
     *
     * @throws NullPointerException
     *         if {@code loggingEvent} is {@code null}
     *
     * @see ILoggingEventAttribute#getValue(Object)
     */
    @Override
    public boolean apply(TLoggingEvent loggingEvent) {
        ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

        Object value = _attribute.getValue(loggingEvent);
        boolean x = _valueSet.contains(value);
        return x;
    }
}
