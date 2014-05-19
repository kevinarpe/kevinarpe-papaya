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

import com.googlecode.kevinarpe.papaya.xml.xpath.StandardQName;
import com.googlecode.kevinarpe.papaya.xml.xpath.XPathExpressionEvaluator;
import com.googlecode.kevinarpe.papaya.xml.xpath.XPathExpressionFactory;
import com.googlecode.kevinarpe.papaya.xml.xpath.XPathHelperException;
import com.googlecode.kevinarpe.papaya.xml.xpath.XPathHelperImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpression;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class XPathHelperImplTest {

    private static final String XPATH = "XPATH";
    private static final String DESCRIPTION = "DESCRIPTION";

    private XPathExpressionFactory mockXPathExpressionFactory;
    private XPathExpressionEvaluator mockXPathExpressionEvaluator;
    private Node mockNode;
    private Node mockNode2;
    private NodeList mockNodeList;
    private XPathExpression mockXPathExpression;

    private XPathHelperException exception;

    private XPathHelperImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockXPathExpressionFactory = mock(XPathExpressionFactory.class);
        mockXPathExpressionEvaluator = mock(XPathExpressionEvaluator.class);
        mockNode = mock(Node.class);
        mockNode2 = mock(Node.class);
        mockNodeList = mock(NodeList.class);
        mockXPathExpression = mock(XPathExpression.class);

        exception = new XPathHelperException("exception");

        classUnderTest =
            new XPathHelperImpl(mockXPathExpressionFactory, mockXPathExpressionEvaluator);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.evaluate()
    //

    @Test
    public void evaluate_FailWhenXPathExpressionCompile()
    throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        try {
            classUnderTest.evaluate(XPATH, mockNode, StandardQName.STRING, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluate_FailWhenXPathExpressionEvaluate()
        throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        try {
            classUnderTest.evaluate(XPATH, mockNode, StandardQName.STRING, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluate_Pass()
    throws XPathHelperException {
        final Object result = new Object();
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(mockXPathExpression, StandardQName.STRING, result);
        assertSame(
            classUnderTest.evaluate(XPATH, mockNode, StandardQName.STRING, DESCRIPTION),
            result);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.tryEvaluate()
    //

    @Test
    public void tryEvaluate_FailWhenXPathExpressionCompile()
    throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        assertNull(classUnderTest.tryEvaluate(XPATH, mockNode, StandardQName.STRING, DESCRIPTION));
    }

    @Test
    public void tryEvaluate_FailWhenXPathExpressionEvaluate()
    throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        assertNull(classUnderTest.tryEvaluate(XPATH, mockNode, StandardQName.STRING, DESCRIPTION));
    }

    @Test
    public void tryEvaluate_Pass()
    throws XPathHelperException {
        final Object result = new Object();
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(mockXPathExpression, StandardQName.STRING, result);
        assertSame(
            classUnderTest.tryEvaluate(XPATH, mockNode, StandardQName.STRING, DESCRIPTION),
            result);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.evaluateAsBoolean()
    //

    @Test
    public void evaluateAsBoolean_FailWhenXPathExpressionCompile()
    throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        try {
            classUnderTest.evaluateAsBoolean(XPATH, mockNode, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluateAsBoolean_FailWhenXPathExpressionEvaluate()
    throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        try {
            classUnderTest.evaluateAsBoolean(XPATH, mockNode, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluateAsBoolean_Pass()
    throws XPathHelperException {
        final Boolean result = Boolean.TRUE;
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(mockXPathExpression, StandardQName.BOOLEAN, result);
        assertSame(
            classUnderTest.evaluateAsBoolean(XPATH, mockNode, DESCRIPTION),
            result);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.tryEvaluateAsBoolean()
    //

    @Test
    public void tryEvaluateAsBoolean_FailWhenXPathExpressionCompile()
    throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        assertNull(classUnderTest.tryEvaluateAsBoolean(XPATH, mockNode, DESCRIPTION));
    }

    @Test
    public void tryEvaluateAsBoolean_FailWhenXPathExpressionEvaluate()
    throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        assertNull(classUnderTest.tryEvaluateAsBoolean(XPATH, mockNode, DESCRIPTION));
    }

    @Test
    public void tryEvaluateAsBoolean_Pass()
    throws XPathHelperException {
        final Boolean result = Boolean.TRUE;
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(mockXPathExpression, StandardQName.BOOLEAN, result);
        assertSame(
            classUnderTest.tryEvaluateAsBoolean(XPATH, mockNode, DESCRIPTION),
            result);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.evaluateAsDouble()
    //

    @Test
    public void evaluateAsDouble_FailWhenXPathExpressionCompile()
    throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        try {
            classUnderTest.evaluateAsDouble(XPATH, mockNode, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluateAsDouble_FailWhenXPathExpressionEvaluate()
    throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        try {
            classUnderTest.evaluateAsDouble(XPATH, mockNode, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluateAsDouble_Pass()
    throws XPathHelperException {
        final Double result = 0.1234d;
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(mockXPathExpression, StandardQName.NUMBER, result);
        assertSame(
            classUnderTest.evaluateAsDouble(XPATH, mockNode, DESCRIPTION),
            result);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.tryEvaluateAsDouble()
    //

    @Test
    public void tryEvaluateAsDouble_FailWhenXPathExpressionCompile()
    throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        assertNull(classUnderTest.tryEvaluateAsDouble(XPATH, mockNode, DESCRIPTION));
    }

    @Test
    public void tryEvaluateAsDouble_FailWhenXPathExpressionEvaluate()
    throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        assertNull(classUnderTest.tryEvaluateAsDouble(XPATH, mockNode, DESCRIPTION));
    }

    @Test
    public void tryEvaluateAsDouble_Pass()
    throws XPathHelperException {
        final Double result = 0.1234d;
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(mockXPathExpression, StandardQName.NUMBER, result);
        assertSame(
            classUnderTest.tryEvaluateAsDouble(XPATH, mockNode, DESCRIPTION),
            result);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.evaluateAsString()
    //

    @Test
    public void evaluateAsString_FailWhenXPathExpressionCompile()
    throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        try {
            classUnderTest.evaluateAsString(XPATH, mockNode, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluateAsString_FailWhenXPathExpressionEvaluate()
    throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        try {
            classUnderTest.evaluateAsString(XPATH, mockNode, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluateAsString_Pass()
    throws XPathHelperException {
        final String result = "result";
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(mockXPathExpression, StandardQName.STRING, result);
        assertSame(
            classUnderTest.evaluateAsString(XPATH, mockNode, DESCRIPTION),
            result);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.tryEvaluateAsString()
    //

    @Test
    public void tryEvaluateAsString_FailWhenXPathExpressionCompile()
    throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        assertNull(classUnderTest.tryEvaluateAsString(XPATH, mockNode, DESCRIPTION));
    }

    @Test
    public void tryEvaluateAsString_FailWhenXPathExpressionEvaluate()
    throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        assertNull(classUnderTest.tryEvaluateAsString(XPATH, mockNode, DESCRIPTION));
    }

    @Test
    public void tryEvaluateAsString_Pass()
    throws XPathHelperException {
        final String result = "result";
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(mockXPathExpression, StandardQName.STRING, result);
        assertSame(
            classUnderTest.tryEvaluateAsString(XPATH, mockNode, DESCRIPTION),
            result);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.evaluateAsNode()
    //

    @Test
    public void evaluateAsNode_FailWhenXPathExpressionCompile()
    throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        try {
            classUnderTest.evaluateAsNode(XPATH, mockNode, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluateAsNode_FailWhenXPathExpressionEvaluate()
    throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        try {
            classUnderTest.evaluateAsNode(XPATH, mockNode, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluateAsNode_Pass()
    throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(mockXPathExpression, StandardQName.NODE, mockNode2);
        assertSame(
            classUnderTest.evaluateAsNode(XPATH, mockNode, DESCRIPTION),
            mockNode2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.tryEvaluateAsNode()
    //

    @Test
    public void tryEvaluateAsNode_FailWhenXPathExpressionCompile()
        throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        assertNull(classUnderTest.tryEvaluateAsNode(XPATH, mockNode, DESCRIPTION));
    }

    @Test
    public void tryEvaluateAsNode_FailWhenXPathExpressionEvaluate()
        throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        assertNull(classUnderTest.tryEvaluateAsNode(XPATH, mockNode, DESCRIPTION));
    }

    @Test
    public void tryEvaluateAsNode_Pass()
    throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(mockXPathExpression, StandardQName.NODE, mockNode2);
        assertSame(
            classUnderTest.tryEvaluateAsNode(XPATH, mockNode, DESCRIPTION),
            mockNode2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.evaluateAsNodeList()
    //

    @Test
    public void evaluateAsNodeList_FailWhenXPathExpressionCompile()
        throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        try {
            classUnderTest.evaluateAsNodeList(XPATH, mockNode, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluateAsNodeList_FailWhenXPathExpressionEvaluate()
        throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        try {
            classUnderTest.evaluateAsNodeList(XPATH, mockNode, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e, exception);
        }
    }

    @Test
    public void evaluateAsNodeList_Pass()
        throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(
            mockXPathExpression, StandardQName.NODESET, mockNodeList);
        assertSame(
            classUnderTest.evaluateAsNodeList(XPATH, mockNode, DESCRIPTION),
            mockNodeList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // XPathHelperImpl.tryEvaluateAsNodeList()
    //

    @Test
    public void tryEvaluateAsNodeList_FailWhenXPathExpressionCompile()
        throws XPathHelperException {
        _whenXPathExpressionCompileFailure(exception);
        assertNull(classUnderTest.tryEvaluateAsNodeList(XPATH, mockNode, DESCRIPTION));
    }

    @Test
    public void tryEvaluateAsNodeList_FailWhenXPathExpressionEvaluate()
        throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationFailure(mockXPathExpression, exception);
        assertNull(classUnderTest.tryEvaluateAsNodeList(XPATH, mockNode, DESCRIPTION));
    }

    @Test
    public void tryEvaluateAsNodeList_Pass()
        throws XPathHelperException {
        _whenXPathExpressionCompileSuccess(mockXPathExpression);
        _whenXPathExpressionEvaluationSuccess(
            mockXPathExpression, StandardQName.NODESET, mockNodeList);
        assertSame(
            classUnderTest.tryEvaluateAsNodeList(XPATH, mockNode, DESCRIPTION),
            mockNodeList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private void _whenXPathExpressionCompileFailure(XPathHelperException e)
    throws XPathHelperException {
        when(mockXPathExpressionFactory.getInstance(XPATH, DESCRIPTION)).thenThrow(e);
    }

    private void _whenXPathExpressionCompileSuccess(XPathExpression xpe)
    throws XPathHelperException {
        when(mockXPathExpressionFactory.getInstance(XPATH, DESCRIPTION)).thenReturn(xpe);
    }

    private void _whenXPathExpressionEvaluationFailure(
            XPathExpression xpe, XPathHelperException e)
    throws XPathHelperException {
        when(
            mockXPathExpressionEvaluator.evaluate(
                xpe, mockNode, StandardQName.STRING, XPATH, DESCRIPTION))
            .thenThrow(e);
    }

    private void _whenXPathExpressionEvaluationSuccess(
        XPathExpression xpe, StandardQName qname, Object result)
    throws XPathHelperException {
        when(
            mockXPathExpressionEvaluator.evaluate(
                xpe, mockNode, qname, XPATH, DESCRIPTION))
            .thenReturn(result);
    }
}
