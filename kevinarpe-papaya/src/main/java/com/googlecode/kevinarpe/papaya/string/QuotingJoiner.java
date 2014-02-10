package com.googlecode.kevinarpe.papaya.string;

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.Lists2;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: LAST
@NotFullyTested
public final class QuotingJoiner {

    // TODO: Also add transform function?  Or create class: TransformingQuotingJoiner?

    public static final boolean DEFAULT_SKIP_NULLS = false;
    public static final String DEFAULT_NULL_TEXT = null;
    public static final String DEFAULT_PREFIX_TEXT = "";
    public static final String DEFAULT_SUFFIX_TEXT = "";
    public static final String DEFAULT_EMPTY_TEXT = "";
    public static final String DEFAULT_PAIR_PREFIX_TEXT = "";
    public static final String DEFAULT_PAIR_SUFFIX_TEXT = "";

    private final String _separator;
    private final boolean _skipNulls;
    private final String _nullText;
    private final String _prefix;
    private final String _suffix;
    private final String _emptyText;

    private QuotingJoiner(String separator) {
        this(
            separator,
            DEFAULT_SKIP_NULLS,
            DEFAULT_NULL_TEXT,
            DEFAULT_PREFIX_TEXT,
            DEFAULT_SUFFIX_TEXT,
            DEFAULT_EMPTY_TEXT);
    }

    private QuotingJoiner(
            String separator,
            boolean skipNulls,
            String nullText,
            String prefix,
            String suffix,
            String emptyText) {
        this._separator = separator;
        this._skipNulls = skipNulls;
        this._nullText = nullText;
        this._prefix = prefix;
        this._suffix = suffix;
        this._emptyText = emptyText;
    }

    public static QuotingJoiner on(String separator) {
        ObjectArgs.checkNotNull(separator, "separator");

        QuotingJoiner x = new QuotingJoiner(separator);
        return x;
    }

    public static QuotingJoiner on(char separator) {
        String separatorString = String.valueOf(separator);

        QuotingJoiner x = new QuotingJoiner(separatorString);
        return x;
    }

    // on vs use vs with
    // TOOD: Fix name!
    public QuotingJoiner withSeparator(String separator) {
        ObjectArgs.checkNotNull(separator, "separator");

        QuotingJoiner x =
            new QuotingJoiner(
                separator, _skipNulls, _nullText, _prefix, _suffix, _emptyText);
        return x;
    }

    public String withSeparator() {
        return _separator;
    }

    public QuotingJoiner withPrefix(String prefix) {
        ObjectArgs.checkNotNull(prefix, "prefix");

        QuotingJoiner x =
            new QuotingJoiner(_separator, _skipNulls, _nullText, prefix, _suffix, _emptyText);
        return x;
    }

    public String withPrefix() {
        return _suffix;
    }

    public QuotingJoiner withSuffix(String suffix) {
        ObjectArgs.checkNotNull(suffix, "suffix");

        QuotingJoiner x =
            new QuotingJoiner(_separator, _skipNulls, _nullText, _prefix, suffix, _emptyText);
        return x;
    }

    public String withSuffix() {
        return _suffix;
    }

    @CheckReturnValue
    public QuotingJoiner useForEmpty(String emptyText) {
        ObjectArgs.checkNotNull(emptyText, "emptyText");

        QuotingJoiner x =
            new QuotingJoiner(
                _separator, _skipNulls, _nullText, _prefix, _suffix, emptyText);
        return x;
    }

    public String useForEmpty() {
        return _emptyText;
    }

    @CheckReturnValue
    public QuotingJoiner useForNull(String nullText) {
        ObjectArgs.checkNotNull(nullText, "nullText");

        QuotingJoiner x =
            new QuotingJoiner(
                _separator, _skipNulls, _nullText, _prefix, _suffix, _emptyText);
        return x;
    }

    public String useForNull() {
        return _nullText;
    }

    @CheckReturnValue
    public QuotingJoiner skipNulls(boolean flag) {
        QuotingJoiner x =
            new QuotingJoiner(
                _separator, _skipNulls, _nullText, _prefix, _suffix, _emptyText);
        return x;
    }

    public boolean skipNulls() {
        return _skipNulls;
    }

    public <TAppendable extends Appendable>
    TAppendable appendTo(
            TAppendable appendable,
            @Nullable Object value1,
            @Nullable Object value2,
            Object... valueArr)
    throws IOException {
        ObjectArgs.checkNotNull(valueArr, "valueArr");  // TODO: Possible?

        List<Object> list = Lists2.newUnmodifiableListFromTwoOrMore(value1, value2, valueArr);
        appendTo(appendable, list);
        return appendable;
    }

    public <TAppendable extends Appendable>
    TAppendable appendTo(TAppendable appendable, Object[] partArr)
    throws IOException {
        ObjectArgs.checkNotNull(partArr, "partArr");

        if (0 == partArr.length) {
            appendable.append(_emptyText);
        }
        else {
            Iterable<Object> partIterable = Arrays.asList(partArr);
            appendTo(appendable, partIterable);
        }
        return appendable;
    }

    public <TAppendable extends Appendable>
    TAppendable appendTo(TAppendable appendable, Iterable<?> partIterable)
    throws IOException {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        appendTo(appendable, partIter);
        return appendable;
    }

    public <TAppendable extends Appendable>
    TAppendable appendTo(TAppendable appendable, Iterator<?> partIter)
    throws IOException {
        ObjectArgs.checkNotNull(appendable, "appendable");
        ObjectArgs.checkNotNull(partIter, "partIter");

        if (partIter.hasNext()) {
            _appendNext(appendable, partIter);
            while (partIter.hasNext()) {
                appendable.append(_separator);
                _appendNext(appendable, partIter);
            }
        }
        else {
            appendable.append(_emptyText);
        }
        return appendable;
    }

    public StringBuilder appendTo(
            StringBuilder builder,
            @Nullable Object value1,
            @Nullable Object value2,
            Object... valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");  // TODO: Possible?

        List<Object> list = Lists2.newUnmodifiableListFromTwoOrMore(value1, value2, valueArr);
        appendTo(builder, list);
        return builder;
    }

    public StringBuilder appendTo(StringBuilder builder, Object[] partArr) {
        ObjectArgs.checkNotNull(partArr, "partArr");

        if (0 == partArr.length) {
            return builder;
        }
        Iterable<Object> partIterable = Arrays.asList(partArr);
        appendTo(builder, partIterable);
        return builder;
    }

    public StringBuilder appendTo(StringBuilder builder, Iterable<?> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        appendTo(builder, partIter);
        return builder;
    }

    public StringBuilder appendTo(StringBuilder builder, Iterator<?> partIter) {
        try {
            appendTo((Appendable) builder, partIter);
        }
        catch (IOException e) {
            throw new Error("Unexpected exception", e);
        }
        return builder;
    }

    public String join(@Nullable Object value1, @Nullable Object value2, Object... valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");  // TODO: Possible?

        List<Object> list = Lists2.newUnmodifiableListFromTwoOrMore(value1, value2, valueArr);
        String x = join(list);
        return x;
    }

    public String join(Object[] partArr) {
        ObjectArgs.checkNotNull(partArr, "partArr");

        if (0 == partArr.length) {
            return _emptyText;
        }
        Iterable<Object> partIterable = Arrays.asList(partArr);
        String x = join(partIterable);
        return x;
    }

    public String join(Iterable<?> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        String x = join(partIter);
        return x;
    }

    public String join(Iterator<?> partIter) {
        ObjectArgs.checkNotNull(partIter, "partIter");

        StringBuilder sb = new StringBuilder();
        appendTo(sb, partIter);
        String sbs = sb.toString();
        return sbs;
    }

    @CheckReturnValue
    public MapJoiner withKeyValueSeparator(String keyValueSeparator) {
        ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator");

        MapJoiner x = new MapJoiner(this, keyValueSeparator);
        return x;
    }

    public static class MapJoiner {

        private final QuotingJoiner _quotingJoiner;
        private final String _keyValueSeparator;
        // TODO: Maybe change to keyPrefix, keySuffix, valuePrefix, valueSuffix?
        private final String _pairPrefix;
        private final String _pairSuffix;

        private MapJoiner(QuotingJoiner quotingJoiner, String keyValueSeparator) {
            this(
                quotingJoiner,
                keyValueSeparator,
                DEFAULT_PAIR_PREFIX_TEXT,
                DEFAULT_PAIR_SUFFIX_TEXT);
        }

        private MapJoiner(
                QuotingJoiner quotingJoiner,
                String keyValueSeparator,
                String pairprefix,
                String pairsuffix) {
            _quotingJoiner = quotingJoiner;
            _keyValueSeparator = keyValueSeparator;
            _pairPrefix = pairprefix;
            _pairSuffix = pairsuffix;
        }

        public MapJoiner withPairPrefix(String pairPrefix) {
            ObjectArgs.checkNotNull(pairPrefix, "pairPrefix");

            MapJoiner x =
                new MapJoiner(_quotingJoiner, _keyValueSeparator, pairPrefix, _pairSuffix);
            return x;
        }

        public String withPairPrefix() {
            return _pairPrefix;
        }

        public MapJoiner withPairSuffix(String pairSuffix) {
            ObjectArgs.checkNotNull(pairSuffix, "pairSuffix");

            MapJoiner x =
                new MapJoiner(_quotingJoiner, _keyValueSeparator, pairSuffix, _pairSuffix);
            return x;
        }

        public String withPairSuffix() {
            return _pairSuffix;
        }

        public <TAppendable extends Appendable>
        TAppendable appendTo(TAppendable appendable, Iterator<? extends Map.Entry<?, ?>> partIter)
        throws IOException {
            ObjectArgs.checkNotNull(appendable, "appendable");
            ObjectArgs.checkNotNull(partIter, "partIter");

            if (partIter.hasNext()) {
                _appendNext(appendable, partIter);
                while (partIter.hasNext()) {
                    appendable.append(_quotingJoiner.withSeparator());
                    _appendNext(appendable, partIter);
                }
            }
            return appendable;
        }

        private <TAppendable extends Appendable>
        void _appendNext(TAppendable appendable, Iterator<? extends Map.Entry<?, ?>> partIter)
        throws IOException {
            Map.Entry<?, ?> entry = partIter.next();
            Object key = entry.getKey();
            String keyString = _quotingJoiner._toString(key);
            Object value = entry.getValue();
            String valueString = _quotingJoiner._toString(value);
            appendable.append(_pairPrefix);
            appendable.append(keyString);
            appendable.append(_keyValueSeparator);
            appendable.append(valueString);
            appendable.append(_pairSuffix);
        }

        // TODO: Finish append/join/etc


        public QuotingJoiner withSeparator(String separator) {
            return _quotingJoiner.withSeparator(separator);
        }

        public String withSeparator() {
            return _quotingJoiner.withSeparator();
        }

        public QuotingJoiner withPrefix(String prefix) {
            return _quotingJoiner.withPrefix(prefix);
        }

        public String withPrefix() {
            return _quotingJoiner.withPrefix();
        }

        public QuotingJoiner withSuffix(String suffix) {
            return _quotingJoiner.withSuffix(suffix);
        }

        public String withSuffix() {
            return _quotingJoiner.withSuffix();
        }

        @CheckReturnValue
        public QuotingJoiner useForEmpty(String emptyText) {
            return _quotingJoiner.useForEmpty(emptyText);
        }

        public String useForEmpty() {
            return _quotingJoiner.useForEmpty();
        }

        @CheckReturnValue
        public QuotingJoiner useForNull(String nullText) {
            return _quotingJoiner.useForNull(nullText);
        }

        public String useForNull() {
            return _quotingJoiner.useForNull();
        }

        @CheckReturnValue
        public QuotingJoiner skipNulls(boolean flag) {
            return _quotingJoiner.skipNulls(flag);
        }

        public boolean skipNulls() {
            return _quotingJoiner.skipNulls();
        }
    }

    private <TAppendable extends Appendable>
    void _appendNext(TAppendable appendable, Iterator<?> partIter)
    throws IOException {
        Object value = partIter.next();
        String valueString = _toString(value);
        appendable.append(valueString);
    }

    private String _toString(Object value) {
        if (null == value) {
            if (null == _nullText) {
                throw new NullPointerException("Failed to convert null value to text");
            }
            return _nullText;
        }
        String x = value.toString();
        x = _prefix + x + _suffix;
        return x;
    }
}
