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
public class OrJerichoHtmlAttributesMatcherTest
extends AbstractJerichoHtmlAttributesMatcherImpTest {

    @Test
    public void pass() {

        final JerichoHtmlAttributesMatcherImp matcher = JerichoHtmlAttributesMatcherImp.withEmptyValue("value");

        final JerichoHtmlAttributesMatcherImp matcher2 =
            JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                "type", "blah");

        final JerichoHtmlAttributesMatcherImp matcherb = JerichoHtmlAttributesMatcherImp.withEmptyValue("value");

        final JerichoHtmlAttributesMatcherImp matcher2b =
            JerichoHtmlAttributesMatcherImp.withNonEmptyValue(
                "type", "blah");

        final JerichoHtmlAttributesMatcher m_m2 = matcher.or(matcher2);
        final JerichoHtmlAttributesMatcher m2_m = matcher2.or(matcher);
        final JerichoHtmlAttributesMatcher mb_m2b = matcherb.or(matcher2b);
        final JerichoHtmlAttributesMatcher m2b_mb = matcher2b.or(matcherb);

        /// isMatch

        Assert.assertTrue(m_m2.isMatch(inputTextAttributes));
        Assert.assertTrue(m2_m.isMatch(inputTextAttributes));
        Assert.assertTrue(mb_m2b.isMatch(inputTextAttributes));
        Assert.assertTrue(m2b_mb.isMatch(inputTextAttributes));

        /// hashCode

        Assert.assertNotEquals(m_m2.hashCode(), m2_m.hashCode());
        Assert.assertEquals(m_m2.hashCode(), mb_m2b.hashCode());
        Assert.assertNotEquals(mb_m2b.hashCode(), m2b_mb.hashCode());
        Assert.assertEquals(m_m2.hashCode(), mb_m2b.hashCode());

        /// equals

        Assert.assertNotEquals(m_m2, m2_m);
        Assert.assertEquals(m_m2, mb_m2b);
        Assert.assertNotEquals(mb_m2b, m2b_mb);
        Assert.assertEquals(m_m2, mb_m2b);

        /// toString

        Assert.assertEquals(m_m2.toString(), "(value=\"\") or (type=\"blah\")");
        Assert.assertEquals(m2_m.toString(), "(type=\"blah\") or (value=\"\")");
        Assert.assertEquals(mb_m2b.toString(), "(value=\"\") or (type=\"blah\")");
        Assert.assertEquals(m2b_mb.toString(), "(type=\"blah\") or (value=\"\")");
    }
}
