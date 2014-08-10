package com.googlecode.kevinarpe.papaya.container.builder;

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

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.Collection;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class MinimalistCollectionBuilderFactoryImpl<TValue, TCollection extends Collection<TValue>>
implements MinimalistCollectionBuilderFactory<TValue, TCollection> {

    private final Constructor<TCollection> _constructor;
    private final @Nullable Constructor<TCollection> _constructorInt;

    public MinimalistCollectionBuilderFactoryImpl(Class<? super TCollection> collectionClass) {
        ObjectArgs.checkNotNull(collectionClass, "collectionClass");

        try {
            @SuppressWarnings("unchecked")
            Constructor<TCollection> x =
                (Constructor<TCollection>) collectionClass.getConstructor();
            _constructor = x;
        }
        catch (NoSuchMethodException e) {
            String msg = String.format("Internal error: Failed to find constructor: %s()",
                collectionClass.getSimpleName());
            throw new IllegalStateException(msg, e);
        }
        Constructor<TCollection> constructorInt = null;
        try {
            @SuppressWarnings("unchecked")
            Constructor<TCollection> x =
                (Constructor<TCollection>) collectionClass.getConstructor(int.class);
            constructorInt = x;
        }
        catch (NoSuchMethodException e) {
            int dummy = 1;  // debug breakpoint
        }
        _constructorInt = constructorInt;
    }

    @Override
    public final MinimalistCollectionBuilderImpl<TValue, TCollection> newInstance() {
        TCollection x = _newInstance(_constructor);
        MinimalistCollectionBuilderImpl<TValue, TCollection> y =
            new MinimalistCollectionBuilderImpl<TValue, TCollection>(x);
        return y;
    }

    @Override
    public final MinimalistCollectionBuilderImpl<TValue, TCollection>
    newInstanceWithCapacity(int initialCapacity) {
        TCollection x = _newInstance(initialCapacity);
        MinimalistCollectionBuilderImpl<TValue, TCollection> y =
            new MinimalistCollectionBuilderImpl<TValue, TCollection>(x);
        return y;
    }

    private TCollection _newInstance(Constructor<TCollection> ctor, Object... argArr) {
        try {
            TCollection x = ctor.newInstance(argArr);
            return x;
        }
        catch (Exception e) {
            String msg = String.format("Internal error: Failed to create instance of class %s",
                _constructor.getClass().getSimpleName());
            throw new IllegalStateException(msg, e);
        }
    }

    private TCollection _newInstance(int initialCapacity) {
        if (null != _constructorInt) {
            TCollection x = _newInstance(_constructorInt, initialCapacity);
            return x;
        }
        else {
            TCollection x = _newInstance(_constructor);
            return x;
        }
    }
}
