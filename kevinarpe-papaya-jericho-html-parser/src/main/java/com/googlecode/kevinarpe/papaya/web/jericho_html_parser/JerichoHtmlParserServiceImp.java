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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.ReadOnlyContainer;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;
import com.googlecode.kevinarpe.papaya.function.count.AnyCountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.CountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.ExactlyCountMatcher;
import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Segment;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class JerichoHtmlParserServiceImp
implements JerichoHtmlParserService {

    private final ExceptionThrower exceptionThrower;

    public JerichoHtmlParserServiceImp(ExceptionThrower exceptionThrower) {

        this.exceptionThrower = ObjectArgs.checkNotNull(exceptionThrower, "exceptionThrower");
    }

    /** {@inheritDoc} */
    @EmptyContainerAllowed
    @Override
    public ImmutableList<Element>
    getElementListByTag(JerichoHtmlSource source,
                        Segment jerichoHtmlSegment,
                        HtmlElementTag htmlElementTag,
                        CountMatcher elementCountMatcher)
    throws Exception {

        final ImmutableList<Element> x =
            getElementListByTagName(source, jerichoHtmlSegment, htmlElementTag.tag, elementCountMatcher);
        return x;
    }

    /** {@inheritDoc} */
    @EmptyContainerAllowed
    @Override
    public ImmutableList<Element>
    getElementListByTagName(JerichoHtmlSource source,
                            Segment jerichoHtmlSegment,
                            String caseInsensitiveHtmlElementTag,
                            CountMatcher elementCountMatcher)
    throws Exception {

        @EmptyContainerAllowed
        @ReadOnlyContainer
        final List<Element> list = jerichoHtmlSegment.getAllElements(caseInsensitiveHtmlElementTag);
        if (false == elementCountMatcher.isMatch(list.size())) {

            throw exceptionThrower.throwCheckedException(Exception.class,
                "Expected %s HTML element(s) with tag '%s', but found %d HTML element(s)",
                elementCountMatcher, caseInsensitiveHtmlElementTag, list.size());
        }
        final ImmutableList<Element> x = ImmutableList.copyOf(list);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public Element
    getElementByTag(JerichoHtmlSource source,
                    Segment jerichoHtmlSegment,
                    HtmlElementTag htmlElementTag)
    throws Exception {

        final ImmutableList<Element> list =
            getElementListByTagName(source, jerichoHtmlSegment, htmlElementTag.tag, ExactlyCountMatcher.ONE);

        final Element x = list.get(0);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public Element
    getElementByTagName(JerichoHtmlSource source,
                        Segment jerichoHtmlSegment,
                        String caseInsensitiveHtmlElementTag)
    throws Exception {

        final ImmutableList<Element> list =
            getElementListByTagName(source, jerichoHtmlSegment, caseInsensitiveHtmlElementTag, ExactlyCountMatcher.ONE);

        final Element x = list.get(0);
        return x;
    }

    /** {@inheritDoc} */
    @EmptyContainerAllowed
    @Override
    public ImmutableList<Element>
    getElementListByTagAndAttributes(JerichoHtmlSource source,
                                     Segment jerichoHtmlSegment,
                                     HtmlElementTag htmlElementTag,
                                     CountMatcher elementCountMatcher,
                                     JerichoHtmlAttributesMatcher attributesMatcher)
    throws Exception {

        final ImmutableList<Element> list =
            getElementListByTagNameAndAttributes(
                source, jerichoHtmlSegment, htmlElementTag.tag, elementCountMatcher, attributesMatcher);

        return list;
    }

    /** {@inheritDoc} */
    @EmptyContainerAllowed
    @Override
    public ImmutableList<Element>
    getElementListByTagNameAndAttributes(JerichoHtmlSource source,
                                         Segment jerichoHtmlSegment,
                                         String caseInsensitiveHtmlElementTag,
                                         CountMatcher elementCountMatcher,
                                         JerichoHtmlAttributesMatcher attributesMatcher)
    throws Exception {

        @EmptyContainerAllowed
        final ImmutableList<Element> list =
            getElementListByTagName(source, jerichoHtmlSegment, caseInsensitiveHtmlElementTag, AnyCountMatcher.INSTANCE);

        final ImmutableList.Builder<Element> b = ImmutableList.builder();
        for (final Element element : list) {

            final Attributes attributes = element.getAttributes();
            if (attributesMatcher.isMatch(attributes)) {

                b.add(element);
            }
        }
        @EmptyContainerAllowed
        final ImmutableList<Element> filteredList = b.build();
        if (false == elementCountMatcher.isMatch(filteredList.size())) {

            throw exceptionThrower.throwCheckedException(Exception.class,
                "Expected %s HTML element(s) with tag '%s', but found %d HTML element(s)"
                    + "%n\tHTML element attributes matcher: %s",
                elementCountMatcher, caseInsensitiveHtmlElementTag, filteredList.size(), attributesMatcher);
        }
        return filteredList;
    }

    /** {@inheritDoc} */
    @Override
    public Element
    getElementByTagAndAttributes(JerichoHtmlSource source,
                                 Segment jerichoHtmlSegment,
                                 HtmlElementTag htmlElementTag,
                                 JerichoHtmlAttributesMatcher attributesMatcher)
    throws Exception {

        final ImmutableList<Element> list =
            getElementListByTagNameAndAttributes(
                source, jerichoHtmlSegment, htmlElementTag.tag, ExactlyCountMatcher.ONE, attributesMatcher);

        final Element x = list.get(0);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public Element
    getElementByTagNameAndAttributes(JerichoHtmlSource source,
                                     Segment jerichoHtmlSegment,
                                     String caseInsensitiveHtmlElementTag,
                                     JerichoHtmlAttributesMatcher attributesMatcher)
    throws Exception {

        final ImmutableList<Element> list =
            getElementListByTagNameAndAttributes(
                source, jerichoHtmlSegment, caseInsensitiveHtmlElementTag, ExactlyCountMatcher.ONE, attributesMatcher);

        final Element x = list.get(0);
        return x;
    }
}
