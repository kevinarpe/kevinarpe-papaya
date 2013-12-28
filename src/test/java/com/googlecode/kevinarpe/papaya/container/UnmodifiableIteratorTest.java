package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class UnmodifiableIteratorTest {

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableIterator.of()
    //

    @Test
    public void of_PassWithModifiableIterator() {
        Assert.assertNotNull(UnmodifiableIterator.of(new ArrayList<String>().iterator()));
    }

    @Test
    public void of_PassWithUnmodifiableIterator() {
        UnmodifiableIterator<String> iter =
            UnmodifiableIterator.of(Lists.newArrayList("abc", "def", "ghi").iterator());
        Assert.assertEquals(iter, UnmodifiableIterator.of(iter));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void of_FailWithNull() {
        UnmodifiableIterator.of(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableIterator.remove()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_Fail() {
        UnmodifiableIterator.of(new ArrayList<String>().iterator()).remove();
    }
}
