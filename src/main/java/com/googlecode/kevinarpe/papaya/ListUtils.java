package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.ArrayList;
import java.util.Arrays;

import com.googlecode.kevinarpe.papaya.annotations.NotFullyTested;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public final class ListUtils {

	// Disable default constructor
	private ListUtils() {
	}
	
	/**
	 * Creates a new {@link ArrayList} from a list of items.  Null items are allowed.
	 * 
	 * @param itemArr list of items.  May be empty.
	 * @return new modifiable list with items
	 */
	public static <T> ArrayList<T> asArrayList(T... itemArr) {
		ArrayList<T> x = new ArrayList<T>(Arrays.asList(itemArr));
		return x;
	}
}
