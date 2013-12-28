package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class FixedSizeListIteratorTest {

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeListIterator.of()
    //

    @Test
    public void of_PassWithModifiableIterator() {
        Assert.assertNotNull(FixedSizeListIterator.of(new ArrayList<String>().listIterator()));
    }

    @Test
    public void of_PassWithFixedSizeListIterator() {
        FixedSizeListIterator<String> iter =
            FixedSizeListIterator.of(Lists.newArrayList("abc", "def", "ghi").listIterator());
        Assert.assertEquals(iter, FixedSizeListIterator.of(iter));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void of_FailWithNull() {
        FixedSizeListIterator.of(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeIterator.add()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void add_Fail() {
        FixedSizeListIterator.of(new ArrayList<String>().listIterator()).add("abc");
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeIterator.set()
    //

    @Test
    public void set_Pass() {
        List<String> list = Lists.newArrayList("abc");
        Assert.assertEquals("abc", list.get(0));
        ListIterator<String> iter = FixedSizeListIterator.of(list.listIterator());
        Assert.assertEquals("abc", iter.next());
        iter.set("def");
        Assert.assertEquals("def", list.get(0));
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeIterator.remove()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_Fail() {
        FixedSizeListIterator.of(new ArrayList<String>().listIterator()).remove();
    }
}
