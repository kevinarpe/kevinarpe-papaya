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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

public class XPathExpressionEvaluatorImplTest {

    private static final String XPATH = "XPATH";
    private static final String DESCRIPTION = "DESCRIPTION";

    private XPathExpression mockXPathExpression;
    private Node mockNode;
    private NodeList mockNodeList;

    private XPathExpressionEvaluatorImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockXPathExpression = mock(XPathExpression.class);
        mockNode = mock(Node.class);
        mockNodeList = mock(NodeList.class);

        classUnderTest = new XPathExpressionEvaluatorImpl();
    }

    @Test(expectedExceptions = XPathHelperException.class)
    public void evaluate_FailWhenXPathExpressionEvaluateThrowsException()
    throws XPathHelperException, XPathExpressionException {
        XPathExpressionException exception = new XPathExpressionException("exception");
        when(mockXPathExpression.evaluate(mockNode, StandardQName.NODESET.getQName()))
            .thenThrow(exception);
        try {
            classUnderTest.evaluate(
                mockXPathExpression, mockNode, StandardQName.NODESET, XPATH, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e.getCause(), exception);
            throw e;
        }
    }

    @Test(expectedExceptions = XPathHelperException.class)
    public void evaluate_FailWhenXPathExpressionEvaluateReturnsNull()
    throws XPathHelperException, XPathExpressionException {
        when(mockXPathExpression.evaluate(mockNode, StandardQName.NODESET.getQName()))
            .thenReturn(null);  // Pedantic
        classUnderTest.evaluate(
            mockXPathExpression, mockNode, StandardQName.NODESET, XPATH, DESCRIPTION);
    }

    @Test(expectedExceptions = XPathHelperException.class)
    public void evaluate_FailWhenXPathExpressionEvaluateReturnsWrongType()
    throws XPathHelperException, XPathExpressionException {
        when(mockXPathExpression.evaluate(mockNode, StandardQName.NODESET.getQName()))
            .thenReturn("abc");  // String
        try {
            classUnderTest.evaluate(
                mockXPathExpression, mockNode, StandardQName.NODESET, XPATH, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e.getCause().getClass(), ClassCastException.class);
            throw e;
        }
    }

    @Test
    public void evaluate_Pass()
    throws XPathHelperException, XPathExpressionException {
        when(mockXPathExpression.evaluate(mockNode, StandardQName.NODESET.getQName()))
            .thenReturn(mockNodeList);  // Pedantic
        assertSame(
            classUnderTest.evaluate(
                mockXPathExpression, mockNode, StandardQName.NODESET, XPATH, DESCRIPTION),
            mockNodeList);
    }
}
