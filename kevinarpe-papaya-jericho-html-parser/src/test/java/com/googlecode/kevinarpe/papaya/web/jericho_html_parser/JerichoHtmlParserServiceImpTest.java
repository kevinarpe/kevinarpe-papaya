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

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrowerImpl;
import com.googlecode.kevinarpe.papaya.function.count.ExactlyCountMatcher;
import com.googlecode.kevinarpe.papaya.string.ThrowingMessageFormatterImpl;
import net.htmlparser.jericho.Element;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class JerichoHtmlParserServiceImpTest
extends AbstractJerichoHtmlAttributesMatcherImpTest {

    private JerichoHtmlParserServiceImp classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod2() {

        final ExceptionThrower exceptionThrower = new ExceptionThrowerImpl(ThrowingMessageFormatterImpl.INSTANCE);
        this.classUnderTest = new JerichoHtmlParserServiceImp(exceptionThrower);
    }

    /// getElementListByTag

    @Test
    public void getElementListByTag_Pass()
    throws Exception {

        final ImmutableList<Element> elementList =
            classUnderTest.getElementListByTag(
                jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.INPUT, new ExactlyCountMatcher(2));

        Assert.assertEquals(elementList.size(), 2);
        Assert.assertTrue(elementList.get(0).getName().equalsIgnoreCase(HtmlElementTag.INPUT.tag));
        Assert.assertTrue(elementList.get(1).getName().equalsIgnoreCase(HtmlElementTag.INPUT.tag));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 3 HTML element(s) with tag 'input', but found 2 HTML element(s)\\E$")
    public void getElementListByTag_FailWhenRowCountNotMatch()
    throws Exception {

        classUnderTest.getElementListByTag(
            jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.INPUT, new ExactlyCountMatcher(3));
    }

    /// getElementListByTagName

    @Test
    public void getElementListByTagName_Pass()
    throws Exception {

        final ImmutableList<Element> elementList =
            classUnderTest.getElementListByTagName(
                jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.INPUT.tag, new ExactlyCountMatcher(2));

        Assert.assertEquals(elementList.size(), 2);
        Assert.assertTrue(elementList.get(0).getName().equalsIgnoreCase(HtmlElementTag.INPUT.tag));
        Assert.assertTrue(elementList.get(1).getName().equalsIgnoreCase(HtmlElementTag.INPUT.tag));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 3 HTML element(s) with tag 'input', but found 2 HTML element(s)\\E$")
    public void getElementListByTagName_FailWhenRowCountNotMatch()
    throws Exception {

        classUnderTest.getElementListByTagName(
            jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.INPUT.tag, new ExactlyCountMatcher(3));
    }

    /// getElementByTag

    @Test
    public void getElementByTag_Pass()
    throws Exception {

        final Element element =
            classUnderTest.getElementByTag(jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.BUTTON);

        Assert.assertTrue(element.getName().equalsIgnoreCase(HtmlElementTag.BUTTON.tag));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 1 HTML element(s) with tag 'input', but found 2 HTML element(s)\\E$")
    public void getElementByTag_FailWhenRowCountNotMatch()
    throws Exception {

        classUnderTest.getElementByTag(
            jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.INPUT);
    }

    /// getElementByTagName

    @Test
    public void getElementByTagName_Pass()
    throws Exception {

        final Element element =
            classUnderTest.getElementByTagName(jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.BUTTON.tag);

        Assert.assertTrue(element.getName().equalsIgnoreCase(HtmlElementTag.BUTTON.tag));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 1 HTML element(s) with tag 'input', but found 2 HTML element(s)\\E$")
    public void getElementByTagName_FailWhenRowCountNotMatch()
    throws Exception {

        classUnderTest.getElementByTagName(
            jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.INPUT.tag);
    }

    /// getElementListByTagAndAttributes

    @Test
    public void getElementListByTagAndAttributes_Pass()
    throws Exception {

        final ImmutableList<Element> elementList =
            classUnderTest.getElementListByTagAndAttributes(
                jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.BUTTON, ExactlyCountMatcher.ONE,
                JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                    "type", "button"));

        Assert.assertEquals(elementList.size(), 1);
        Assert.assertTrue(elementList.get(0).getName().equalsIgnoreCase(HtmlElementTag.BUTTON.tag));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 2 HTML element(s) with tag 'button', but found 1 HTML element(s)\\E.*$")
    public void getElementListByTagAndAttributes_FailWhenRowCountNotMatch()
    throws Exception {

        classUnderTest.getElementListByTagAndAttributes(
            jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.BUTTON, new ExactlyCountMatcher(2),
            JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                "type", "button"));
    }

    /// getElementListByTagNameAndAttributes

    @Test
    public void getElementListByTagNameAndAttributes_Pass()
    throws Exception {

        final ImmutableList<Element> elementList =
            classUnderTest.getElementListByTagNameAndAttributes(
                jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.BUTTON.tag, ExactlyCountMatcher.ONE,
                JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                    "type", "button"));

        Assert.assertEquals(elementList.size(), 1);
        Assert.assertTrue(elementList.get(0).getName().equalsIgnoreCase(HtmlElementTag.BUTTON.tag));
    }

    @Test
    public void getElementListByTagNameAndAttributes_PassWhenMultipleMatchingTagsButFilteredWithAttributes()
    throws Exception {

        final ImmutableList<Element> elementList =
            classUnderTest.getElementListByTagNameAndAttributes(
                jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.INPUT.tag, ExactlyCountMatcher.ONE,
                JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                    "type", "checkbox"));

        Assert.assertEquals(elementList.size(), 1);
        Assert.assertTrue(elementList.get(0).getName().equalsIgnoreCase(HtmlElementTag.INPUT.tag));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 2 HTML element(s) with tag 'button', but found 1 HTML element(s)\\E.*$")
    public void getElementListByTagNameAndAttributes_FailWhenRowCountNotMatch()
    throws Exception {

        classUnderTest.getElementListByTagNameAndAttributes(
            jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.BUTTON.tag, new ExactlyCountMatcher(2),
            JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                "type", "button"));
    }

    /// getElementByTagAndAttributes

    @Test
    public void getElementByTagAndAttributes_Pass()
    throws Exception {

        final Element element =
            classUnderTest.getElementByTagAndAttributes(
                jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.BUTTON,
                JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                    "type", "button"));

        Assert.assertTrue(element.getName().equalsIgnoreCase(HtmlElementTag.BUTTON.tag));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 1 HTML element(s) with tag 'input', but found 2 HTML element(s)\\E.*$")
    public void getElementByTagAndAttributes_FailWhenRowCountNotMatch()
    throws Exception {

        classUnderTest.getElementByTagAndAttributes(
            jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.INPUT, JerichoHtmlAttributesMatcherImp.ANY);
    }

    /// getElementByTagNameAndAttributes

    @Test
    public void getElementByTagNameAndAttributes_Pass()
    throws Exception {

        final Element element =
            classUnderTest.getElementByTagNameAndAttributes(
                jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.BUTTON.tag,
                JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                    "type", "button"));

        Assert.assertTrue(element.getName().equalsIgnoreCase(HtmlElementTag.BUTTON.tag));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 1 HTML element(s) with tag 'input', but found 2 HTML element(s)\\E.*$")
    public void getElementByTagNameAndAttributes_FailWhenRowCountNotMatch()
    throws Exception {

        classUnderTest.getElementByTagNameAndAttributes(
            jerichoHtmlSource, jerichoHtmlSource.source, HtmlElementTag.INPUT.tag, JerichoHtmlAttributesMatcherImp.ANY);
    }
}
