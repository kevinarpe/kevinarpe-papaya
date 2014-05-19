package com.googlecode.kevinarpe.papaya.xml;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import javax.xml.xpath.XPathExpression;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class XPathHelperImpl
implements XPathHelper {

    private final XPathExpressionFactory _xpathExpressionFactory;
    private final XPathExpressionEvaluator _xpathExpressionEvaluator;

    XPathHelperImpl(
            XPathExpressionFactory xpathExpressionFactory,
            XPathExpressionEvaluator xpathExpressionEvaluator) {
        _xpathExpressionFactory =
            ObjectArgs.checkNotNull(xpathExpressionFactory, "xpathExpressionFactory");
        _xpathExpressionEvaluator =
            ObjectArgs.checkNotNull(xpathExpressionEvaluator, "xpathExpressionEvaluator");
    }

    @Override
    public Object evaluate(String xpath, Node node, StandardQName qname, String xpathDescription)
    throws XPathHelperException {
        XPathExpression xpe = _xpathExpressionFactory.getInstance(xpath, xpathDescription);
        Object result =
            _xpathExpressionEvaluator.evaluate(xpe, node, qname, xpath, xpathDescription);
        return result;
    }

    @Override
    public @Nullable Object tryEvaluate(
            String xpath, Node node, StandardQName qname, String xpathDescription) {
        try {
            Object x = evaluate(xpath, node, qname, xpathDescription);
            return x;
        }
        catch (XPathHelperException e) {
            return null;
        }
    }

    @Override
    public Boolean evaluateAsBoolean(String xpath, Node node, String xpathDescription)
    throws XPathHelperException {
        Boolean x = (Boolean) evaluate(xpath, node, StandardQName.BOOLEAN, xpathDescription);
        return x;
    }

    /**
     * This is a convenience method to call {@link #evaluateAsBoolean(String, Node, String)}
     * but returns {@code null} if exception thrown.
     */
    @Override
    public @Nullable Boolean tryEvaluateAsBoolean(
            String xpath, Node node, String xpathDescription) {
        try {
            Boolean x = (Boolean) evaluate(xpath, node, StandardQName.BOOLEAN, xpathDescription);
            return x;
        }
        catch (XPathHelperException e) {
            return null;
        }
    }

    /**
     * This is a convenience method to call {@link #evaluate(String, Node, StandardQName, String)}
     * and cast the result to {@link Double}.
     */
    @Override
    public Double evaluateAsDouble(String xpath, Node node, String xpathDescription)
        throws XPathHelperException {
        Double x = (Double) evaluate(xpath, node, StandardQName.NUMBER, xpathDescription);
        return x;
    }

    /**
     * This is a convenience method to call {@link #evaluateAsBoolean(String, Node, String)}
     * but returns {@code null} if exception thrown.
     */
    @Override
    public @Nullable Double tryEvaluateAsDouble(String xpath, Node node, String xpathDescription) {
        try {
            Double x = (Double) evaluate(xpath, node, StandardQName.NUMBER, xpathDescription);
            return x;
        }
        catch (XPathHelperException e) {
            return null;
        }
    }

    /**
     * This is a convenience method to call {@link #evaluate(String, Node, StandardQName, String)}
     * and cast the result to {@link String}.
     */
    @Override
    public String evaluateAsString(String xpath, Node node, String xpathDescription)
        throws XPathHelperException {
        String x = (String) evaluate(xpath, node, StandardQName.STRING, xpathDescription);
        return x;
    }

    /**
     * This is a convenience method to call {@link #evaluateAsString(String, Node, String)}
     * but returns {@code null} if exception thrown.
     */
    @Override
    public @Nullable String tryEvaluateAsString(String xpath, Node node, String xpathDescription) {
        try {
            String x = (String) evaluate(xpath, node, StandardQName.STRING, xpathDescription);
            return x;
        }
        catch (XPathHelperException e) {
            return null;
        }
    }

    /**
     * This is a convenience method to call {@link #evaluate(String, Node, StandardQName, String)}
     * and cast the result to {@link Node}.
     */
    @Override
    public Node evaluateAsNode(String xpath, Node node, String xpathDescription)
        throws XPathHelperException {
        Node x = (Node) evaluate(xpath, node, StandardQName.NODE, xpathDescription);
        return x;
    }

    /**
     * This is a convenience method to call {@link #evaluateAsNode(String, Node, String)}
     * but returns {@code null} if exception thrown.
     */
    @Override
    public @Nullable Node tryEvaluateAsNode(String xpath, Node node, String xpathDescription) {
        try {
            Node x = (Node) evaluate(xpath, node, StandardQName.NODE, xpathDescription);
            return x;
        }
        catch (XPathHelperException e) {
            return null;
        }
    }

    /**
     * This is a convenience method to call {@link #evaluate(String, Node, StandardQName, String)}
     * and cast the result to {@link NodeList}.
     */
    @Override
    public NodeList evaluateAsNodeList(String xpath, Node node, String xpathDescription)
        throws XPathHelperException {
        NodeList x = (NodeList) evaluate(xpath, node, StandardQName.NODESET, xpathDescription);
        return x;
    }

    /**
     * This is a convenience method to call {@link #evaluateAsNodeList(String, Node, String)}
     * but returns {@code null} if exception thrown.
     */
    @Override
    public @Nullable NodeList tryEvaluateAsNodeList(
        String xpath, Node node, String xpathDescription) {
        try {
            NodeList x = (NodeList) evaluate(xpath, node, StandardQName.NODESET, xpathDescription);
            return x;
        }
        catch (XPathHelperException e) {
            return null;
        }
    }
}
