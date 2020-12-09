package com.googlecode.kevinarpe.papaya.web.jericho_html_parser;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.function.count.AbstractCountMatcher;
import net.htmlparser.jericho.Attributes;

import javax.annotation.Nullable;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see AbstractCountMatcher
 */
@FullyTested
public interface JerichoHtmlAttributesMatcher {

    public static final JerichoHtmlAttributesMatcher ANY =
        (Attributes attributes) -> true;

    /**
     * Tests if HTML tag attributes are a match, e.g.,
     * <ul>
     *     <li>if the attribute name {@code "type"} exists and value is {@code "checkbox"}</li>
     *     <li>if the attribute name {@code "value"} exists and value is {@code "" (empty string)}</li>
     *     <li>if the attribute name {@code "checked"} exists and value does not exist</li>
     * </ul>
     *
     * @param attributes
     *        attributes to match
     *
     * @return {@code true} if {@code attributes} matches
     *
     * @see #and(JerichoHtmlAttributesMatcher)
     * @see #or(JerichoHtmlAttributesMatcher)
     * @see #not()
     */
    boolean isMatch(Attributes attributes);

    /** {@inheritDoc} */
    @Override
    boolean equals(@Nullable Object obj);

    int hashCode();

    /** {@inheritDoc} */
    @Override
    String toString();

    /**
     * @return new instance of {@link JerichoHtmlAttributesMatcher} that combines {@code this} and {@code other}
     *         using logical-AND ({@code &&}).
     *
     * @see AndJerichoHtmlAttributesMatcher
     */
    default JerichoHtmlAttributesMatcher
    and(JerichoHtmlAttributesMatcher other) {

        final AndJerichoHtmlAttributesMatcher x = new AndJerichoHtmlAttributesMatcher(this, other);
        return x;
    }

    /**
     * @return new instance of {@link JerichoHtmlAttributesMatcher} that combines {@code this} and {@code other}
     *         using logical-OR ({@code ||}).
     *
     * @see OrJerichoHtmlAttributesMatcher
     */
    default JerichoHtmlAttributesMatcher
    or(JerichoHtmlAttributesMatcher other) {

        final OrJerichoHtmlAttributesMatcher x = new OrJerichoHtmlAttributesMatcher(this, other);
        return x;
    }

    /**
     * @return {@link JerichoHtmlAttributesMatcher} using {@code this} and logical-NOT ({@code !}).
     *
     * @see NotJerichoHtmlAttributesMatcher#create(JerichoHtmlAttributesMatcher)
     */
    default JerichoHtmlAttributesMatcher
    not() {

        final JerichoHtmlAttributesMatcher x = NotJerichoHtmlAttributesMatcher.create(this);
        return x;
    }
}
