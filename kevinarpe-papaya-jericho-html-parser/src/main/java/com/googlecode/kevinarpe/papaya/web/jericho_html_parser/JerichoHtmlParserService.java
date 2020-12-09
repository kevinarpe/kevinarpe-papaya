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
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.function.count.AnyCountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.CountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.ExactlyCountMatcher;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Segment;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface JerichoHtmlParserService {

    /**
     * This is a convenience method to call
     * {@link #getElementListByTagName(JerichoHtmlSource, Segment, String, CountMatcher)}
     * <br>where {@code caseInsensitiveHtmlElementTag} is {@link HtmlElementTag#tag}.
     */
    @EmptyContainerAllowed
    ImmutableList<Element>
    getElementListByTag(JerichoHtmlSource source,
                        Segment jerichoHtmlSegment,
                        HtmlElementTag htmlElementTag,
                        CountMatcher elementCountMatcher)
    throws Exception;

    /**
     * Finds all child elements of {@code jerichoHtmlSegment} by matching HTML element tag.
     * <p>
     * This method calls {@link Segment#getAllElements(String)}, then checks number of matched elements.
     *
     * @param source
     *        parent of {@code jerichoHtmlSegment}
     *
     * @param jerichoHtmlSegment
     *        HTML element to start search
     *        <br>Note: {@link JerichoHtmlSource#source} has type {@link Segment}
     *
     * @param caseInsensitiveHtmlElementTag
     *        case insensitive HTML element tag, e.g., {@code "input" or "a" or "table"}
     *
     * @param elementCountMatcher
     *        used to verify number of matched HTML elements
     *        <br>Example: If {@link AnyCountMatcher#INSTANCE} is used, then return result may be any empty list.
     *
     * @return zero or more matching child HTML elements of {@code jerichoHtmlSegment}
     *
     * @throws Exception
     *         if number of matched HTML elements does not match {@code elementCountMatcher}
     *
     * @see Segment#getAllElements(String)
     */
    @EmptyContainerAllowed
    ImmutableList<Element>
    getElementListByTagName(JerichoHtmlSource source,
                            Segment jerichoHtmlSegment,
                            String caseInsensitiveHtmlElementTag,
                            CountMatcher elementCountMatcher)
    throws Exception;

    /**
     * This is a convenience method to call
     * {@link #getElementListByTagName(JerichoHtmlSource, Segment, String, CountMatcher)}
     * <br>where {@code caseInsensitiveHtmlElementTag} is {@link HtmlElementTag#tag}
     * <br>and {@code elementCountMatcher} is {@link ExactlyCountMatcher#ONE}.
     */
    Element
    getElementByTag(JerichoHtmlSource source,
                    Segment jerichoHtmlSegment,
                    HtmlElementTag htmlElementTag)
    throws Exception;

    /**
     * This is a convenience method to call
     * {@link #getElementListByTagName(JerichoHtmlSource, Segment, String, CountMatcher)}
     * <br>where {@code elementCountMatcher} is {@link ExactlyCountMatcher#ONE}.
     */
    Element
    getElementByTagName(JerichoHtmlSource source,
                        Segment jerichoHtmlSegment,
                        String caseInsensitiveHtmlElementTag)
    throws Exception;

    /**
     * This is a convenience method to call
     * {@link #getElementListByTagNameAndAttributes(JerichoHtmlSource, Segment, String, CountMatcher, JerichoHtmlAttributesMatcher)}
     * <br>where {@code caseInsensitiveHtmlElementTag} is {@link HtmlElementTag#tag}.
     */
    @EmptyContainerAllowed
    ImmutableList<Element>
    getElementListByTagAndAttributes(JerichoHtmlSource source,
                                     Segment jerichoHtmlSegment,
                                     HtmlElementTag htmlElementTag,
                                     CountMatcher elementCountMatcher,
                                     JerichoHtmlAttributesMatcher attributesMatcher)
    throws Exception;

    /**
     * Finds all child elements of {@code jerichoHtmlSegment} by matching HTML element tag and HTML element attributes.
     * <p>
     * This method calls {@link Segment#getAllElements(String)}, then filters results by HTML element attributes.
     * Finally, then number of matched elements is checked.
     *
     * @param source
     *        parent of {@code jerichoHtmlSegment}
     *
     * @param jerichoHtmlSegment
     *        HTML element to start search
     *        <br>Note: {@link JerichoHtmlSource#source} has type {@link Segment}
     *
     * @param caseInsensitiveHtmlElementTag
     *        case insensitive HTML element tag, e.g., {@code "input" or "a" or "table"}
     *
     * @param elementCountMatcher
     *        matches the number of found HTML elements
     *        <br>Important: This is applied after filtered results by {@code attributesMatcher}.  Thus, it is possible
     *        to match many HTML elements by {@code caseInsensitiveHtmlElementTag}, then further filter the result.
     *        <br>Example: If {@link AnyCountMatcher#INSTANCE} is used, then return result may be any empty list.
     *
     * @param attributesMatcher
     *        matches HTML element attributes
     *        <br>Example: If {@link JerichoHtmlAttributesMatcher#ANY} is used, then return result may be any empty list.
     *
     * @return zero or more matching child HTML elements of {@code jerichoHtmlSegment}
     *
     * @throws Exception
     *         if number of matched HTML elements does not match {@code elementCountMatcher}
     */
    @EmptyContainerAllowed
    ImmutableList<Element>
    getElementListByTagNameAndAttributes(JerichoHtmlSource source,
                                         Segment jerichoHtmlSegment,
                                         String caseInsensitiveHtmlElementTag,
                                         CountMatcher elementCountMatcher,
                                         JerichoHtmlAttributesMatcher attributesMatcher)
    throws Exception;

    /**
     * This is a convenience method to call
     * {@link #getElementListByTagNameAndAttributes(JerichoHtmlSource, Segment, String, CountMatcher, JerichoHtmlAttributesMatcher)}
     * <br>where {@code caseInsensitiveHtmlElementTag} is {@link HtmlElementTag#tag}
     * <br>and {@code elementCountMatcher} is {@link ExactlyCountMatcher#ONE}.
     */
    Element
    getElementByTagAndAttributes(JerichoHtmlSource source,
                                 Segment jerichoHtmlSegment,
                                 HtmlElementTag htmlElementTag,
                                 JerichoHtmlAttributesMatcher attributesMatcher)
    throws Exception;

    /**
     * This is a convenience method to call
     * {@link #getElementListByTagNameAndAttributes(JerichoHtmlSource, Segment, String, CountMatcher, JerichoHtmlAttributesMatcher)}
     * <br>where {@code elementCountMatcher} is {@link ExactlyCountMatcher#ONE}.
     */
    Element
    getElementByTagNameAndAttributes(JerichoHtmlSource source,
                                     Segment jerichoHtmlSegment,
                                     String caseInsensitiveHtmlElementTag,
                                     JerichoHtmlAttributesMatcher attributesMatcher)
    throws Exception;
}
