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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import net.htmlparser.jericho.Source;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class JerichoHtmlSource {

    /**
     * Ex: {@code "Microsoft Outlook email HTML body"}
     */
    public final String description;
    public final Source source;

    /**
     * @param description
     *        Ex: {@code "Microsoft Outlook email HTML body"}
     */
    public JerichoHtmlSource(String description, String html) {

        this.description = StringArgs.checkNotEmptyOrWhitespace(description, "description");
        StringArgs.checkNotEmptyOrWhitespace(html, "html");
        this.source = new Source(html);
        this.source.fullSequentialParse();
    }

    public String
    getHtml() {
        final String x = source.toString();
        return x;
    }

    @Override
    public String
    toString() {
        return description;
    }
}
