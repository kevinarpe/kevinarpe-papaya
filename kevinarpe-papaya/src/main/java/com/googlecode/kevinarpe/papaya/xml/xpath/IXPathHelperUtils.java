package com.googlecode.kevinarpe.papaya.xml.xpath;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import javax.xml.xpath.XPath;

/**
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link XPathHelperUtils} will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface IXPathHelperUtils {

    /**
     * Creates a new XPath helper.
     *
     * @param xpathExpressionCompiler
     *        instance of {@link XPath}.
     *        <br>Example: {@code XPathFactory.newXPath()}
     *
     * @return new instance that implements {@link XPathHelper}
     *
     * @throws NullPointerException
     *         if {@code xpathExpressionCompiler} is {@code null}
     */
    XPathHelper newInstance(XPath xpathExpressionCompiler);
}
