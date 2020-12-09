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
 * @see JerichoHtmlAttributesMatcher#not()
 */
@FullyTested
public final class NotJerichoHtmlAttributesMatcher
extends AbstractJerichoHtmlAttributesMatcher {

    /**
     * Tries to unwrap negated delegates, thus: {@code !!JerichoHtmlAttributesMatcher -> JerichoHtmlAttributesMatcher}
     *
     * @see JerichoHtmlAttributesMatcher#not()
     */
    public static JerichoHtmlAttributesMatcher
    create(JerichoHtmlAttributesMatcher delegate) {

        ObjectArgs.checkNotNull(delegate, "delegate");
        if (delegate instanceof NotJerichoHtmlAttributesMatcher) {

            final NotJerichoHtmlAttributesMatcher ncm = (NotJerichoHtmlAttributesMatcher) delegate;
            return ncm.delegate;
        }
        else {
            final NotJerichoHtmlAttributesMatcher x = new NotJerichoHtmlAttributesMatcher(delegate);
            return x;
        }
    }

    private final JerichoHtmlAttributesMatcher delegate;

    private NotJerichoHtmlAttributesMatcher(JerichoHtmlAttributesMatcher delegate) {

        this.delegate = ObjectArgs.checkNotNull(delegate, "delegate");
    }

    /** {@inheritDoc} */
    @Override
    public boolean
    isMatch(Attributes attributes) {

        final boolean x = (false == delegate.isMatch(attributes));
        return x;
    }

    @Override
    protected int
    hash() {
        final int x = Objects.hash(this.getClass(), delegate);
        return x;
    }

    @Override
    protected boolean
    isEqual(Object obj) {

        final NotJerichoHtmlAttributesMatcher other = (NotJerichoHtmlAttributesMatcher) obj;
        final boolean x = delegate.equals(other.delegate);
        return x;
    }

    @Override
    protected String
    describe() {

        final String x = "not (" + delegate.toString() + ")";
        return x;
    }
}
