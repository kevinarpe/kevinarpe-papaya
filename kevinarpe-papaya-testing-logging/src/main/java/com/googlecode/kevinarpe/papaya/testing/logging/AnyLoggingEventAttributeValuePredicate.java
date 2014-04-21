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

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.Lists2;

import java.util.Collections;
import java.util.Set;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
@FullyTested
public final class AnyLoggingEventAttributeValuePredicate<TLoggingEvent>
implements Predicate<TLoggingEvent> {

    private final ILoggingEventAttribute<TLoggingEvent> _attribute;
    private final Set<Object> _valueSet;

    public <TAttributeValue> AnyLoggingEventAttributeValuePredicate(
            ILoggingEventAttribute<TLoggingEvent> attribute,
            TAttributeValue value,
            TAttributeValue... moreValueArr) {
        _attribute = ObjectArgs.checkNotNull(attribute, "attribute");
        _valueSet =
            Sets.<Object>newHashSet(
                Lists2.newUnmodifiableListFromOneOrMoreValues(value, moreValueArr));
    }

    public AnyLoggingEventAttributeValuePredicate(
            ILoggingEventAttribute<TLoggingEvent> attribute, Set<?> valueSet) {
        _attribute = ObjectArgs.checkNotNull(attribute, "attribute");
        _valueSet = Sets.<Object>newHashSet(CollectionArgs.checkNotEmpty(valueSet, "valueSet"));
    }

    public ILoggingEventAttribute getAttribute() {
        return _attribute;
    }

    public Set<Object> getValueSet() {
        return Collections.unmodifiableSet(_valueSet);
    }

    @Override
    public boolean apply(TLoggingEvent loggingEvent) {
        ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

        Object value = _attribute.getValue(loggingEvent);
        boolean x = _valueSet.contains(value);
        return x;
    }
}
