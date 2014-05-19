package com.googlecode.kevinarpe.papaya.xml.xpath;

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

import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class XPathExpressionFactoryImpl
implements XPathExpressionFactory {

    private final XPath _xpathExpressionCompiler;
    private final HashMap<String, XPathExpression> _xpathToXPathExpressionMap;

    XPathExpressionFactoryImpl(XPath xpathExpressionCompiler) {
        _xpathExpressionCompiler =
            ObjectArgs.checkNotNull(xpathExpressionCompiler, "xpathExpressionCompiler");
        _xpathToXPathExpressionMap = Maps.newHashMap();
    }

    @Override
    public XPathExpression getInstance(String xpath, String description)
    throws XPathHelperException {
        StringArgs.checkNotEmptyOrWhitespace(xpath, "xpath");
        StringArgs.checkNotEmptyOrWhitespace(description, "description");

        XPathExpression xpe = _xpathToXPathExpressionMap.get(xpath);
        if (null == xpe) {
            xpe = _compile(xpath, description);
            _xpathToXPathExpressionMap.put(xpath, xpe);
        }
        return xpe;
    }

    private XPathExpression _compile(String xpath, String description)
    throws XPathHelperException {
        try {
            XPathExpression xpe = _xpathExpressionCompiler.compile(xpath);
            return xpe;
        }
        catch (XPathExpressionException e) {
            String msg = String.format(
                "XPath for '%s': Failed to compile: '%s'", description, xpath);
            throw new XPathHelperException(msg, e);
        }
    }
}
