package com.googlecode.kevinarpe.papaya.xml;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;

/**
 * Standard XPath 1.0 qualified names.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see XPathHelperUtils
 */
public enum StandardQName {

    /**
     * Maps to {@link Boolean}.
     *
     * @see XPathConstants#BOOLEAN
     */
    BOOLEAN(XPathConstants.BOOLEAN, Boolean.class),

    /**
     * Maps to {@link Double}.
     *
     * @see XPathConstants#NUMBER
     */
    NUMBER(XPathConstants.NUMBER, Double.class),

    /**
     * Maps to {@link String}.
     *
     * @see XPathConstants#STRING
     */
    STRING(XPathConstants.STRING, String.class),

    /**
     * Maps to {@link Node}.
     *
     * @see XPathConstants#NODE
     */
    NODE(XPathConstants.NODE, Node.class),

    /**
     * Maps to {@link NodeList}.
     *
     * @see XPathConstants#NODESET
     */
    NODESET(XPathConstants.NODESET, NodeList.class),
    ;

    private final QName _qname;
    private final Class<?> _valueClass;

    private StandardQName(QName qname, Class<?> valueClass) {
        _qname = ObjectArgs.checkNotNull(qname, "qname");
        _valueClass = ObjectArgs.checkNotNull(valueClass, "valueClass");
    }

    /**
     * @return qualified name, e.g., {@link XPathConstants#NODESET}
     */
    public QName getQName() {
        return _qname;
    }

    /**
     * @return class for type of qualified name, e.g., {@link NodeList}
     */
    public Class<?> getTypeClass() {
        return _valueClass;
    }

    @Override
    public String toString() {
        String x = String.format("%s.%s {%n\tgetQName(): %s,%n\tgetTypeClass(): %s%n}",
            StandardQName.class.getName(), name(), getQName(), getTypeClass());
        return x;
    }
}
