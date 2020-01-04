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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class XPathExpressionEvaluatorImpl
implements XPathExpressionEvaluator {

    static final XPathExpressionEvaluatorImpl INSTANCE = new XPathExpressionEvaluatorImpl();

    @Override
    public Object evaluate(
            XPathExpression xpe,
            Node node,
            StandardQName qname,
            String xpath,
            String xpathDescription)
    throws XPathHelperException {
        ObjectArgs.checkNotNull(xpe, "xpe");
        ObjectArgs.checkNotNull(node, "node");
        ObjectArgs.checkNotNull(qname, "qname");
        StringArgs.checkNotEmptyOrWhitespace(xpath, "xpath");
        StringArgs.checkNotEmptyOrWhitespace(xpathDescription, "xpathDescription");

        Object x = _evaluate(xpe, node, qname, xpath, xpathDescription);
        x = _castResult(qname, xpath, xpathDescription, x);
        return x;
    }

    private Object _castResult(
            StandardQName qname, String xpath, String xpathDescription, Object result)
    throws XPathHelperException {
        if (null == result) {
            throw new XPathHelperException(
                String.format("XPath for '%s': Evaluation result is null for type '%s': '%s'",
                    xpathDescription, qname.name(), xpath));
        }
        try {
            Object castResult = qname.getTypeClass().cast(result);
            return castResult;
        }
        catch (ClassCastException e) {
            String msg =
                String.format(
                    "XPath for '%s': Failed to cast result from type %s to type %s: '%s'",
                    xpathDescription,
                    result.getClass().getName(),
                    qname.getTypeClass().getName(),
                    xpath);
            throw new XPathHelperException(msg, e);
        }
    }

    private Object _evaluate(
            XPathExpression xpe,
            Node node,
            StandardQName qname,
            String xpath,
            String xpathDescription)
    throws XPathHelperException {
        try {
            Object x = xpe.evaluate(node, qname.getQName());
            return x;
        }
        catch (XPathExpressionException e) {
            String msg = String.format("XPath for '%s': Failed to evaluate as type %s: '%s'",
                xpathDescription, qname.name(), xpath);
            throw new XPathHelperException(msg, e);
        }
    }
}
