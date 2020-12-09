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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import net.htmlparser.jericho.Attributes;

import java.util.Objects;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JerichoHtmlAttributesMatcher#or(JerichoHtmlAttributesMatcher)
 */
@FullyTested
public final class OrJerichoHtmlAttributesMatcher
extends AbstractJerichoHtmlAttributesMatcher {

    private final JerichoHtmlAttributesMatcher left;
    private final JerichoHtmlAttributesMatcher right;

    /**
     * @see JerichoHtmlAttributesMatcher#or(JerichoHtmlAttributesMatcher)
     */
    public OrJerichoHtmlAttributesMatcher(JerichoHtmlAttributesMatcher left,
                                          JerichoHtmlAttributesMatcher right) {

        this.left = ObjectArgs.checkNotNull(left, "left");
        this.right = ObjectArgs.checkNotNull(right, "right");
    }

    /** {@inheritDoc} */
    @Override
    public boolean
    isMatch(Attributes attributes) {

        final boolean x = left.isMatch(attributes) || right.isMatch(attributes);
        return x;
    }

    @Override
    protected int
    hash() {
        final int x = Objects.hash(this.getClass(), left, right);
        return x;
    }

    @Override
    protected boolean
    isEqual(Object obj) {

        final OrJerichoHtmlAttributesMatcher other = (OrJerichoHtmlAttributesMatcher) obj;
        final boolean x = left.equals(other.left) && right.equals(other.right);
        return x;
    }

    @Override
    protected String
    describe() {

        final String x = "(" + left.toString() + ") or (" + right.toString() + ")";
        return x;
    }
}
