package com.googlecode.kevinarpe.papaya.annotation;

/*
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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates the item is <b>not</b> considered fully tested.  Some tests may exist,
 * but they are not considered complete.  In the longer view, not fully tested items should move
 * towards being fully tested.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see FullyTested
 */
@Retention(RetentionPolicy.CLASS)
@Target({
    ElementType.PACKAGE,
    ElementType.TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.METHOD})
@Documented
public @interface NotFullyTested {
}
