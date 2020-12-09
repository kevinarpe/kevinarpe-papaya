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

import com.googlecode.kevinarpe.papaya.annotation.EmptyStringAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Attributes;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class JerichoHtmlAttributesMatcherImp
extends AbstractJerichoHtmlAttributesMatcher {

    /**
     * Matches an HTML attribute <u>without</u> a value,
     * e.g., attribute {@code "checked"} from {@code <input type="checkbox" checked>}
     *
     * @param caseInsensitiveAttrName
     *        case-insensitive HTML attribute name, e.g., {@code "checked"}
     */
    public static JerichoHtmlAttributesMatcherImp
    withoutValue(String caseInsensitiveAttrName) {

        final JerichoHtmlAttributesMatcherImp x =
            new JerichoHtmlAttributesMatcherImp(caseInsensitiveAttrName, null);
        return x;
    }

    /**
     * Matches an HTML attribute with an <u>empty</u> value,
     * e.g., attribute {@code "value"} from {@code <input type="text" value="">}
     *
     * @param caseInsensitiveAttrName
     *        case-insensitive HTML attribute name, e.g., {@code "text"}
     */
    public static JerichoHtmlAttributesMatcherImp
    withEmptyValue(String caseInsensitiveAttrName) {

        final JerichoHtmlAttributesMatcherImp x =
            new JerichoHtmlAttributesMatcherImp(caseInsensitiveAttrName, "");
        return x;
    }

    /**
     * Matches an HTML attribute with a <u>non-empty</u> value,
     * e.g., attribute {@code "type"} from {@code <button type="button">}
     *
     * @param caseInsensitiveAttrName
     *        case-insensitive HTML attribute name, e.g., {@code "type"}
     */
    public static JerichoHtmlAttributesMatcherImp
    withNonEmptyValue(String caseInsensitiveAttrName, String caseInsensitiveAttrValue) {

        StringArgs.checkNotEmptyOrWhitespace(caseInsensitiveAttrValue, "caseInsensitiveAttrValue");

        final JerichoHtmlAttributesMatcherImp x =
            new JerichoHtmlAttributesMatcherImp(caseInsensitiveAttrName, caseInsensitiveAttrValue);
        return x;
    }

    private final String caseInsensitiveAttrName;
    @Nullable
    @EmptyStringAllowed
    private final String nullableCaseInsensitiveAttrValue;

    private JerichoHtmlAttributesMatcherImp(String caseInsensitiveAttrName,
                                            @Nullable
                                            @EmptyStringAllowed
                                            String nullableCaseInsensitiveAttrValue) {

        this.caseInsensitiveAttrName =
            StringArgs.checkNotEmptyOrWhitespace(caseInsensitiveAttrName, "caseInsensitiveAttrName");

        this.nullableCaseInsensitiveAttrValue = nullableCaseInsensitiveAttrValue;
   }

   /** {@inheritDoc} */
    @Override
    public boolean
    isMatch(Attributes attributes) {

        @Nullable
        final Attribute attribute = attributes.get(caseInsensitiveAttrName);
        if (null == attribute) {
            return false;
        }
        if (null == nullableCaseInsensitiveAttrValue) {

            final boolean x = (false == attribute.hasValue());
            return x;
        }
        @Nullable
        @EmptyStringAllowed
        final String value = attribute.getValue();
        if (null == value) {
            return false;
        }
        final boolean x = value.equalsIgnoreCase(nullableCaseInsensitiveAttrValue);
        return x;
    }

    @Override
    protected int
    hash() {

        final int x = Objects.hash(caseInsensitiveAttrName, nullableCaseInsensitiveAttrValue,
            // We need this to workaround null and "" have same hash code -> zero!
            (null == nullableCaseInsensitiveAttrValue) ? Boolean.TRUE : Boolean.FALSE);
        return x;
    }

    @Override
    protected boolean
    isEqual(Object obj) {

        final JerichoHtmlAttributesMatcherImp other = (JerichoHtmlAttributesMatcherImp) obj;
        final boolean x =
            this.caseInsensitiveAttrName.equals(other.caseInsensitiveAttrName)
                && Objects.equals(this.nullableCaseInsensitiveAttrValue, other.nullableCaseInsensitiveAttrValue);
        return x;
    }

    @Override
    protected String
    describe() {

        if (null == nullableCaseInsensitiveAttrValue) {
            return caseInsensitiveAttrName;
        }
        else {
            final String x = caseInsensitiveAttrName + "=\"" + nullableCaseInsensitiveAttrValue + "\"";
            return x;
        }
    }
}
