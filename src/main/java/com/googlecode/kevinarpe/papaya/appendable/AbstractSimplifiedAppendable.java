package com.googlecode.kevinarpe.papaya.appendable;

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

import java.io.IOException;

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;

/**
 * Reduces the number of methods required for implementation by interface {@link Appendable}
 * from three to one.  Only {@link #append(CharSequence)} must be implemented by subclasses.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public abstract class AbstractSimplifiedAppendable
implements Appendable {

    /**
     * This method calls {@link #append(CharSequence)}.
     * <p>
     * {@inheritDoc}
     */
    @NotFullyTested
    @Override
    public Appendable append(CharSequence csq, int start, int end)
    throws IOException {
        CharSequence csq2 = csq.subSequence(start, end);
        Appendable result = append(csq2);
        return result;
    }

    /**
     * This method calls {@link #append(CharSequence)}.
     * <p>
     * {@inheritDoc}
     */
    @NotFullyTested
    @Override
    public Appendable append(char c)
    throws IOException {
        String x = String.valueOf(c);
        Appendable result = append(x);
        return result;
    }
}
