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
public class NotJerichoHtmlAttributesMatcherTest
extends AbstractJerichoHtmlAttributesMatcherImpTest {

    @Test
    public void pass() {

        final JerichoHtmlAttributesMatcherImp matcher =
            JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                "type", "text");

        final JerichoHtmlAttributesMatcherImp matcherb =
            JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                "type", "text");

        final JerichoHtmlAttributesMatcher not_m = matcher.not();
        final JerichoHtmlAttributesMatcher not_not_m = not_m.not();
        final JerichoHtmlAttributesMatcher not_mb = matcherb.not();

        Assert.assertSame(matcher, not_not_m);

        /// isMatch

        Assert.assertFalse(not_m.isMatch(inputTextAttributes));
        Assert.assertTrue(not_not_m.isMatch(inputTextAttributes));
        Assert.assertFalse(not_mb.isMatch(inputTextAttributes));

        /// hashCode

        Assert.assertNotEquals(matcher.hashCode(), not_m.hashCode());
        Assert.assertEquals(not_m.hashCode(), not_m.hashCode());
        Assert.assertEquals(not_m.hashCode(), not_mb.hashCode());
        Assert.assertNotEquals(not_m.hashCode(), not_not_m.hashCode());
        Assert.assertEquals(not_not_m.hashCode(), not_not_m.hashCode());

        /// equals

        Assert.assertNotEquals(matcher, not_m);
        Assert.assertEquals(not_m, not_m);
        Assert.assertEquals(not_m, not_mb);
        Assert.assertNotEquals(not_m, not_not_m);
        Assert.assertEquals(not_not_m, not_not_m);

        /// toString

        Assert.assertEquals(not_m.toString(), "not (type=\"text\")");
        Assert.assertEquals(not_not_m.toString(), "type=\"text\"");
        Assert.assertEquals(not_m.toString(), not_mb.toString());
    }
}
