package com.googlecode.kevinarpe.papaya.xml.xpath;

/*
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

import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import javax.xml.xpath.XPath;

/**
 * Methods to create new instances that implement interface {@link XPathHelper}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see StatelessObject
 * @see IXPathHelperUtils
 * @see #INSTANCE
 */
public final class XPathHelperUtils
extends StatelessObject
implements IXPathHelperUtils {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final XPathHelperUtils INSTANCE = new XPathHelperUtils();

    /**
     * {@inheritDoc}
     */
    @Override
    public XPathHelper newInstance(XPath xpathExpressionCompiler) {
        XPathExpressionFactoryImpl factory =
            new XPathExpressionFactoryImpl(xpathExpressionCompiler);
        XPathHelperImpl x =new XPathHelperImpl(factory, XPathExpressionEvaluatorImpl.INSTANCE);
        return x;
    }
}
