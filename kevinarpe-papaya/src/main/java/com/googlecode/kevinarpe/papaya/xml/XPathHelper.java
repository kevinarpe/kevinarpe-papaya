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

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;

/**
 * Helper methods to compile, evaluate, and cast the result of XPath expressions.
 * <p/>
 * Unfortunately, the interfaces provided by Java are weak because they return null in many cases
 * or do not correctly define pre- or post-conditions.  You may consider this interface an
 * improvement over the standard Java classes and methods.
 * <p/>
 * To instantiate an instance of this class, see {@link XPathHelperUtils}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #evaluate(String, Node, StandardQName, String)
 * @see #tryEvaluate(String, Node, StandardQName, String)
 * @see XPathHelperUtils
 */
public interface XPathHelper {

    /**
     * Compiles, evaluates, and casts the result of an XPath expression.
     *
     * @param xpath
     *        See <a href="http://www.w3schools.com/xpath/xpath_examples.asp">example</a>
     * @param node
     *        Root node to search.  May be {@link Document} reference.
     * @param qname
     *        Expected result type
     * @param xpathDescription
     *        Human-readable description of XPath, e.g., number of shared in index composition
     *
     * @return result of XPath expression.
     *         Never null and always can be cast to expected result type
     *
     * @throws NullPointerException
     *         if {@code xpath}, {@code node}, {@code qname}, or {@code xpathDescription} is
     *         {@code null}
     * @throws XPathHelperException
     * <ul>
     *     <li>if {@code xpath} fails to compile</li>
     *     <li>if evaluation result is {@code null}</li>
     *     <li>if evaluation result cannot be cast to expected result type</li>
     * </ul>
     *
     * @see #tryEvaluate(String, Node, StandardQName, String)
     */
    Object evaluate(String xpath, Node node, StandardQName qname, String xpathDescription)
    throws XPathHelperException;

    /**
     * This is a convenience method to call {@link #evaluate(String, Node, StandardQName, String)}
     * but returns {@code null} if exception thrown.
     */
    @Nullable Object tryEvaluate(
            String xpath, Node node, StandardQName qname, String xpathDescription);

    /**
     * This is a convenience method to call {@link #evaluate(String, Node, StandardQName, String)}
     * and cast the result to {@link Boolean}.
     */
    Boolean evaluateAsBoolean(String xpath, Node node, String xpathDescription)
    throws XPathHelperException;

    /**
     * This is a convenience method to call {@link #evaluateAsBoolean(String, Node, String)}
     * but returns {@code null} if exception thrown.
     */
    @Nullable Boolean tryEvaluateAsBoolean(String xpath, Node node, String xpathDescription);

    /**
     * This is a convenience method to call {@link #evaluate(String, Node, StandardQName, String)}
     * and cast the result to {@link Double}.
     */
    Double evaluateAsDouble(String xpath, Node node, String xpathDescription)
    throws XPathHelperException;

    /**
     * This is a convenience method to call {@link #evaluateAsBoolean(String, Node, String)}
     * but returns {@code null} if exception thrown.
     */
    @Nullable
    Double tryEvaluateAsDouble(
        String xpath, Node node, String xpathDescription);

    /**
     * This is a convenience method to call {@link #evaluate(String, Node, StandardQName, String)}
     * and cast the result to {@link String}.
     */
    String evaluateAsString(String xpath, Node node, String xpathDescription)
    throws XPathHelperException;

    /**
     * This is a convenience method to call {@link #evaluateAsString(String, Node, String)}
     * but returns {@code null} if exception thrown.
     */
    @Nullable
    String tryEvaluateAsString(String xpath, Node node, String xpathDescription);

    /**
     * This is a convenience method to call {@link #evaluate(String, Node, StandardQName, String)}
     * and cast the result to {@link Node}.
     */
    Node evaluateAsNode(String xpath, Node node, String xpathDescription)
    throws XPathHelperException;

    /**
     * This is a convenience method to call {@link #evaluateAsNode(String, Node, String)}
     * but returns {@code null} if exception thrown.
     */
    @Nullable
    Node tryEvaluateAsNode(String xpath, Node node, String xpathDescription);

    /**
     * This is a convenience method to call {@link #evaluate(String, Node, StandardQName, String)}
     * and cast the result to {@link NodeList}.
     */
    NodeList evaluateAsNodeList(String xpath, Node node, String xpathDescription)
    throws XPathHelperException;

    /**
     * This is a convenience method to call {@link #evaluateAsNodeList(String, Node, String)}
     * but returns {@code null} if exception thrown.
     */
    @Nullable
    NodeList tryEvaluateAsNodeList(String xpath, Node node, String xpathDescription);
}
