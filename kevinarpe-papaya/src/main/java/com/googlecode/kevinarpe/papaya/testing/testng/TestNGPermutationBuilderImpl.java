package com.googlecode.kevinarpe.papaya.testing.testng;

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

import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.builder.ArrayBuilder;
import com.googlecode.kevinarpe.papaya.container.builder.ArrayListFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Test me
final class TestNGPermutationBuilderImpl
implements TestNGPermutationBuilder {

    private static final ArrayListFactory<Object> _staticArrayListFactory =
        ArrayListFactory.create();

    private final ArrayList<List<Object>> _paramValueListList;
    private int nullableParamCount;

    TestNGPermutationBuilderImpl() {
        _paramValueListList = Lists.newArrayList();
    }

    @Override
    public TestNGPermutationBuilder addNullableParam(Object value) {
        ObjectArgs.checkNotNull(value, "value");

        ArrayList<Object> list = _staticArrayListFactory.copyOf(value, null);
        _paramValueListList.add(list);
        ++nullableParamCount;
        return this;
    }

    @Override
    public TestNGPermutationBuilder addParam(Object value, Object... moreValueArr) {
        ObjectArgs.checkNotNull(moreValueArr, "moreValueArr");

        List<Object> list = Lists.asList(value, moreValueArr);
        CollectionArgs.checkElementsUnique(list, "Lists.asList(value, moreValueArr)");

        _paramValueListList.add(list);
        return this;
    }

    @Override
    public Object[][] build() {
        ArrayBuilder<Object[]> arrayBuilder = ArrayBuilder.create(Object[].class);
        _coreCreateNullPermutations(0, new Object[0], arrayBuilder);
        Object[][] x = arrayBuilder.build();
        return x;
    }

    private void _coreCreateNullPermutations(
            int index, Object[] outputArr, ArrayBuilder<Object[]> arrayBuilder) {
        List<Object> paramValueList = _paramValueListList.get(index);
        List<Object[]> outputArrList = Lists.newArrayListWithCapacity(paramValueList.size());
        for (Object paramValue : paramValueList) {
            Object[] outputArr2 = ObjectArrays.concat(outputArr, paramValue);
            outputArrList.add(outputArr2);
        }
        final int size = _paramValueListList.size();
        final boolean isAllNullable = (nullableParamCount == size);
        if (index == size - 1) {
            // If all params are nullable, do not add the first output array, as all values will be
            // non-null.  This leads to a rather non-obvious condition below.
            if (!isAllNullable || !arrayBuilder.isEmpty()) {
                Object[] outputArr0 = outputArrList.get(0);
                arrayBuilder.add(outputArr0);
            }
            for (int i = 1; i < outputArrList.size(); ++i) {
                Object[] anOutputArr = outputArrList.get(i);
                arrayBuilder.add(anOutputArr);
            }
        }
        else {
            for (Object[] anOutputArr : outputArrList) {
                _coreCreateNullPermutations(1 + index, anOutputArr, arrayBuilder);
            }
        }
    }
}
