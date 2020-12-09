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

import net.htmlparser.jericho.Attributes;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public abstract class AbstractJerichoHtmlAttributesMatcherImpTest {

    private static final String INPUT_TEXT_ID = "input_text";
    private static final String CHECKED_INPUT_CHECKBOX_ID = "checked_input_checkbox";
    private static final String BUTTON_ID = "button";

    private static final String HTML = String.format(""
            + "<!DOCTYPE html>%n"
            + "<html lang=\"en\">%n"
            + "<head>%n"
            + "    <meta charset=\"utf-8\">%n"
            + "    <title>Sample Web Page</title>%n"
            + "</head>%n"
            + "<body style=\"font-family: monospace\">%n"
            + "Text Input: <input id=\"%s\" type=\"text\" value=\"\" size=\"64\" autofocus=\"true\" style=\"font-family: monospace\">%n"
            + "<br>Checkbox: <input id=\"%s\" type=\"checkbox\" checked>%n"
            + "<br><button id=\"%s\" type=\"button\">Click Me</button>%n"
            + "</body>%n"
            + "</html>%n",
        INPUT_TEXT_ID,
        CHECKED_INPUT_CHECKBOX_ID,
        BUTTON_ID);

    protected JerichoHtmlSource jerichoHtmlSource;
    protected Attributes inputTextAttributes;
    protected Attributes inputCheckboxAttributes;

    @BeforeMethod
    public void beforeEachTestMethod() {

        this.jerichoHtmlSource = new JerichoHtmlSource("description", HTML);
        Assert.assertEquals(jerichoHtmlSource.getHtml(), HTML);
        Assert.assertEquals(jerichoHtmlSource.toString(), "description");
        this.inputTextAttributes = jerichoHtmlSource.source.getAllElements("input").get(0).getAttributes();
        Assert.assertTrue(JerichoHtmlAttributesMatcher.ANY.isMatch(inputTextAttributes));
        this.inputCheckboxAttributes = jerichoHtmlSource.source.getAllElements("input").get(1).getAttributes();
    }
}
