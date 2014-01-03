package com.googlecode.kevinarpe.papaya.comparator;

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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Comparator;

/**
 * Extends {@link Comparator} to provide case sensitivity option.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #isCaseSensitive()
 * @see #getCaseSensitive()
 * @see #setCaseSensitive(CaseSensitive)
 */
@FullyTested
public abstract class AbstractLexicographicalComparator
    <
        TValue,
        TSelf extends AbstractLexicographicalComparator<TValue, TSelf>
    >
implements Comparator<TValue> {

    /**
     * Default case sensitivity setting for empty constructor.
     *
     * @see CaseSensitive#YES
     * @see #AbstractLexicographicalComparator()
     * @see #isCaseSensitive()
     * @see #getCaseSensitive()
     * @see #setCaseSensitive(CaseSensitive)
     */
    public static final CaseSensitive DEFAULT_CASE_SENSITIVE = CaseSensitive.YES;

    private CaseSensitive caseSensitive;

    /**
     * This is a convenience method to call
     * {@link #AbstractLexicographicalComparator(CaseSensitive)} with
     * {@code caseSensitive} as {@link #DEFAULT_CASE_SENSITIVE}.
     */
    protected AbstractLexicographicalComparator() {
        this(DEFAULT_CASE_SENSITIVE);
    }

    /**
     * Calls {@link #setCaseSensitive(CaseSensitive)} with {@code caseSensitive}.
     *
     * @throws NullPointerException
     *         if {@code caseSensitive} is {@code null}
     *
     * @see #isCaseSensitive()
     * @see #getCaseSensitive()
     * @see #setCaseSensitive(CaseSensitive)
     */
    protected AbstractLexicographicalComparator(CaseSensitive caseSensitive) {
        setCaseSensitive(caseSensitive);
    }

    /**
     * @return {@code true} if {@link #getCaseSensitive()} equals {@link CaseSensitive#YES}
     *
     * @see #getCaseSensitive()
     * @see #setCaseSensitive(CaseSensitive)
     */
    public final boolean isCaseSensitive() {
        return caseSensitive == CaseSensitive.YES;
    }

    /**
     * @see #isCaseSensitive()
     * @see #setCaseSensitive(CaseSensitive)
     */
    public final CaseSensitive getCaseSensitive() {
        return caseSensitive;
    }

    /**
     * @param caseSensitive
     *        must not be {@code null}
     *
     * @return reference to {@code this}
     *
     * @throws NullPointerException
     *         if {@code caseSensitive} is {@code null}
     *
     * @see #isCaseSensitive()
     * @see #getCaseSensitive()
     */
    public final TSelf setCaseSensitive(CaseSensitive caseSensitive) {
        this.caseSensitive = ObjectArgs.checkNotNull(caseSensitive, "caseSensitive");
        @SuppressWarnings("unchecked")
        TSelf self = (TSelf) this;
        return self;
    }

    /**
     * Calls {@link String#compareTo(String)} or {@link String#compareToIgnoreCase(String)}
     * depending on the result of {@link #getCaseSensitive()}.
     *
     * @param left
     *        must not be {@code null}
     * @param right
     *        must not be {@code null}
     *
     * @return strictly normalized compare result: -1, 0, or +1
     *
     * @throws NullPointerException
     *         if {@code left} or {@code right} is {@code null}
     *
     * @see ComparatorUtils#normalizeCompareResult(int)
     * @see #isCaseSensitive()
     * @see #getCaseSensitive()
     * @see #setCaseSensitive(CaseSensitive)
     */
    protected final int compareStrings(String left, String right) {
        ObjectArgs.checkNotNull(left, "left");
        ObjectArgs.checkNotNull(right, "right");

        int result = caseSensitive.compare(left, right);
        return result;
    }

    /**
     * Equates by {@link #getCaseSensitive()}.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        // We cannot use the more beautiful 'instanceof' here because this class is generic.  Blarg.
        if (!result && null != obj && obj.getClass().equals(this.getClass())) {
            @SuppressWarnings("unchecked")
            final AbstractLexicographicalComparator<TValue, TSelf> other =
                (AbstractLexicographicalComparator<TValue, TSelf>) obj;
            result = Objects.equal(this.getCaseSensitive(), other.getCaseSensitive());
        }
        return result;
    }

    /**
     * Returns hash code of {@link #getCaseSensitive()}.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = Objects.hashCode(caseSensitive);
        return result;
    }
}
