package com.googlecode.kevinarpe.papaya.logging.slf4j;

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

import com.google.common.collect.Iterators;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;
import org.slf4j.Marker;

import java.util.Iterator;

/**
 * Replacement marker for {@code null}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class SLF4JMarkerNone
extends StatelessObject
implements Marker {

    /**
     * The constructor is private so this is the only accessible instance of this class.
     */
    public static final SLF4JMarkerNone INSTANCE = new SLF4JMarkerNone();

    /**
     * This marker always has an empty string as its name.
     *
     * @see #getName()
     */
    public static final String NAME = "";

    private SLF4JMarkerNone() {
        // Empty
    }

    /**
     * Always returns {@link #NAME}.
     * <hr/>
     * Inherited docs:
     * <br/>
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Always does nothing.
     * <hr/>
     * Inherited docs:
     * <br/>
     * {@inheritDoc}
     */
    @Override
    public void add(Marker reference) {
        // Do nothing
    }

    /**
     * Always does nothing and returns {@code false}.
     * <hr/>
     * Inherited docs:
     * <br/>
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Marker reference) {
        return false;  // reference could not be found and removed
    }

    /**
     * Always does nothing and returns {@code false}.
     * <hr/>
     * Inherited docs:
     * <br/>
     * {@inheritDoc}
     */
    @Deprecated
    @Override
    public boolean hasChildren() {
        return false;
    }

    /**
     * Always does nothing and returns {@code false}.
     * <hr/>
     * Inherited docs:
     * <br/>
     * {@inheritDoc}
     */
    @Override
    public boolean hasReferences() {
        return false;
    }

    /**
     * Always returns an empty iterator.
     * <hr/>
     * Inherited docs:
     * <br/>
     * {@inheritDoc}
     */
    @Override
    public Iterator iterator() {
        Iterator x = Iterators.emptyIterator();
        return x;
    }

    /**
     * Always does nothing and returns {@code false}.
     * <hr/>
     * Inherited docs:
     * <br/>
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Marker other) {
        return false;
    }

    /**
     * Always does nothing and returns {@code false}.
     * <hr/>
     * Inherited docs:
     * <br/>
     * {@inheritDoc}
     */
    @Override
    public boolean contains(String name) {
        return false;
    }
}
