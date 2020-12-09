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

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class JerichoHtmlAttributesMatcherImpTest
extends AbstractJerichoHtmlAttributesMatcherImpTest {

    @Test
    public void withoutValue_Pass() {
        {
            final JerichoHtmlAttributesMatcherImp matcher = JerichoHtmlAttributesMatcherImp.withoutValue("blah");
            Assert.assertFalse(matcher.isMatch(inputCheckboxAttributes));
        }
        {
            final JerichoHtmlAttributesMatcherImp matcher = JerichoHtmlAttributesMatcherImp.withoutValue("checked");
            Assert.assertTrue(matcher.isMatch(inputCheckboxAttributes));
        }
        {
            final JerichoHtmlAttributesMatcherImp matcher = JerichoHtmlAttributesMatcherImp.withoutValue("value");
            Assert.assertFalse(matcher.isMatch(inputTextAttributes));
        }
        {
            final JerichoHtmlAttributesMatcherImp matcher = JerichoHtmlAttributesMatcherImp.withoutValue("type");
            Assert.assertFalse(matcher.isMatch(inputTextAttributes));
        }
    }

    @Test
    public void withEmptyValue_Pass() {
        {
            final JerichoHtmlAttributesMatcherImp matcher = JerichoHtmlAttributesMatcherImp.withEmptyValue("blah");
            Assert.assertFalse(matcher.isMatch(inputCheckboxAttributes));
        }
        {
            final JerichoHtmlAttributesMatcherImp matcher = JerichoHtmlAttributesMatcherImp.withEmptyValue("checked");
            Assert.assertFalse(matcher.isMatch(inputCheckboxAttributes));
        }
        {
            final JerichoHtmlAttributesMatcherImp matcher = JerichoHtmlAttributesMatcherImp.withEmptyValue("value");
            Assert.assertTrue(matcher.isMatch(inputTextAttributes));
        }
        {
            final JerichoHtmlAttributesMatcherImp matcher = JerichoHtmlAttributesMatcherImp.withEmptyValue("type");
            Assert.assertFalse(matcher.isMatch(inputTextAttributes));
        }
    }

    @Test
    public void withNonEmptyValue_Pass() {
        {
            final JerichoHtmlAttributesMatcherImp matcher =
                JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                    "blah", "value");

            Assert.assertFalse(matcher.isMatch(inputCheckboxAttributes));
        }
        {
            final JerichoHtmlAttributesMatcherImp matcher =
                JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                    "checked", "value");

            Assert.assertFalse(matcher.isMatch(inputCheckboxAttributes));
        }
        {
            final JerichoHtmlAttributesMatcherImp matcher =
                JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                    "value", "blah");

            Assert.assertFalse(matcher.isMatch(inputTextAttributes));
        }
        {
            final JerichoHtmlAttributesMatcherImp matcher =
                JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                    "type", "text");

            Assert.assertTrue(matcher.isMatch(inputTextAttributes));
        }
        {
            final JerichoHtmlAttributesMatcherImp matcher =
                JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                    "type", "text2");

            Assert.assertFalse(matcher.isMatch(inputTextAttributes));
        }
    }

    @Test
    public void hashCode_equals_Pass() {

        final JerichoHtmlAttributesMatcherImp matcher = JerichoHtmlAttributesMatcherImp.withoutValue("blah");
        final JerichoHtmlAttributesMatcherImp matcher2 = JerichoHtmlAttributesMatcherImp.withoutValue("blah");
        final JerichoHtmlAttributesMatcherImp matcher3 = JerichoHtmlAttributesMatcherImp.withoutValue("blah2");
        Assert.assertEquals(matcher.hashCode(), matcher2.hashCode());
        Assert.assertEquals(matcher, matcher);
        Assert.assertEquals(matcher, matcher2);
        Assert.assertNotEquals(matcher.hashCode(), matcher3.hashCode());
        Assert.assertNotEquals(matcher, matcher3);
        Assert.assertFalse(matcher.equals("abc"));

        final JerichoHtmlAttributesMatcherImp matcher4 = JerichoHtmlAttributesMatcherImp.withEmptyValue("blah");
        final JerichoHtmlAttributesMatcherImp matcher5 = JerichoHtmlAttributesMatcherImp.withEmptyValue("blah");
        final JerichoHtmlAttributesMatcherImp matcher6 = JerichoHtmlAttributesMatcherImp.withEmptyValue("blah2");
        Assert.assertEquals(matcher4.hashCode(), matcher5.hashCode());
        Assert.assertEquals(matcher4, matcher5);
        Assert.assertNotEquals(matcher4.hashCode(), matcher6.hashCode());
        Assert.assertNotEquals(matcher4, matcher6);

        final JerichoHtmlAttributesMatcherImp matcher7 =
            JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                "blah", "value");

        final JerichoHtmlAttributesMatcherImp matcher8 =
            JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                "blah", "value");

        final JerichoHtmlAttributesMatcherImp matcher9 =
            JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                "blah", "value2");

        Assert.assertEquals(matcher7.hashCode(), matcher8.hashCode());
        Assert.assertEquals(matcher7, matcher8);
        Assert.assertNotEquals(matcher7.hashCode(), matcher9.hashCode());
        Assert.assertNotEquals(matcher7, matcher9);

        Assert.assertNotEquals(matcher.hashCode(), matcher4.hashCode());
        Assert.assertNotEquals(matcher, matcher4);
        Assert.assertNotEquals(matcher4.hashCode(), matcher7.hashCode());
        Assert.assertNotEquals(matcher4, matcher7);
        Assert.assertNotEquals(matcher.hashCode(), matcher7.hashCode());
        Assert.assertNotEquals(matcher, matcher7);
    }

    @Test
    public void toString_Pass() {

        final JerichoHtmlAttributesMatcherImp matcher = JerichoHtmlAttributesMatcherImp.withoutValue("blah");
        Assert.assertEquals(matcher.toString(), "blah");

        final JerichoHtmlAttributesMatcherImp matcher2 = JerichoHtmlAttributesMatcherImp.withEmptyValue("blah");
        Assert.assertEquals(matcher2.toString(), "blah=\"\"");

        final JerichoHtmlAttributesMatcherImp matcher3 =
            JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                "blah", "value");
        Assert.assertEquals(matcher3.toString(), "blah=\"value\"");
    }
}
