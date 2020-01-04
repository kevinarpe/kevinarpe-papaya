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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class XPathExpressionFactoryImplTest {

    private static final String XPATH = "XPATH";
    private static final String DESCRIPTION = "DESCRIPTION";

    private XPath mockXPathExpressionCompiler;
    private XPathExpression mockXPathExpression;

    private XPathExpressionFactoryImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockXPathExpressionCompiler = mock(XPath.class);
        classUnderTest = new XPathExpressionFactoryImpl(mockXPathExpressionCompiler);
    }

    @Test(expectedExceptions = XPathHelperException.class)
    public void getInstance_FailWhenCompile()
    throws XPathExpressionException, XPathHelperException {
        XPathExpressionException exception = new XPathExpressionException("exception");
        when(mockXPathExpressionCompiler.compile(XPATH)).thenThrow(exception);
        try {
            classUnderTest.getInstance(XPATH, DESCRIPTION);
        }
        catch (XPathHelperException e) {
            assertSame(e.getCause(), exception);
            assertTrue(e.getMessage().contains(XPATH));
            assertTrue(e.getMessage().contains(DESCRIPTION));
            throw e;
        }
    }

    @Test
    public void getInstance_Pass()
    throws XPathHelperException, XPathExpressionException {
        when(mockXPathExpressionCompiler.compile(XPATH)).thenReturn(mockXPathExpression);
        XPathExpression xpe = classUnderTest.getInstance(XPATH, DESCRIPTION);
        assertSame(xpe, mockXPathExpression);
        // Confirm the original compile result is cached.
        assertSame(classUnderTest.getInstance(XPATH, DESCRIPTION), xpe);
    }
}
