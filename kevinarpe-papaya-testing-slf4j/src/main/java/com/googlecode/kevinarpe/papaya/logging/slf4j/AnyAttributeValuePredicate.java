package com.googlecode.kevinarpe.papaya.logging.slf4j;

import com.google.common.base.Predicate;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
@NotFullyTested
final class AnyAttributeValuePredicate
implements Predicate<SLF4JLoggingEvent> {

    // TODO: LAST: Test me

    private final SLF4JLoggingEventAttribute _attribute;
    private final Set<?> _valueSet;

    public <TAttributeValue> AnyAttributeValuePredicate(
            SLF4JLoggingEventAttribute attribute,
            TAttributeValue value,
            TAttributeValue... moreValueArr) {
        this(attribute, _createSet(value, moreValueArr));
    }

    private static <T> Set<T> _createSet(T value, T... moreValueArr) {
        ObjectArgs.checkNotNull(moreValueArr, "moreValueArr");

        Set<T> set = new LinkedHashSet<T>(moreValueArr.length + 1);
        set.add(value);
        set.addAll(Arrays.asList(moreValueArr));
        return set;
    }

    public AnyAttributeValuePredicate(SLF4JLoggingEventAttribute attribute, Set<?> valueSet) {
        _attribute = ObjectArgs.checkNotNull(attribute, "attribute");
        _valueSet = CollectionArgs.checkNotEmpty(valueSet, "valueSet");
    }

    @Override
    public boolean apply(SLF4JLoggingEvent loggingEvent) {
        ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

        Object value = loggingEvent.getAttributeValue(_attribute);
        boolean x = _valueSet.contains(value);
        return x;
    }
}
