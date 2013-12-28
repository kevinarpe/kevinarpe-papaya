package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class UnmodifiableListIteratorTest {

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableListIterator.of()
    //

    @Test
    public void of_PassWithModifiableIterator() {
        Assert.assertNotNull(UnmodifiableListIterator.of(new ArrayList<String>().listIterator()));
    }

    @Test
    public void of_PassWithUnmodifiableListIterator() {
        UnmodifiableListIterator<String> iter =
            UnmodifiableListIterator.of(Lists.newArrayList("abc", "def", "ghi").listIterator());
        Assert.assertEquals(iter, UnmodifiableListIterator.of(iter));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void of_FailWithNull() {
        UnmodifiableListIterator.of(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableIterator.add()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void add_Fail() {
        UnmodifiableListIterator.of(new ArrayList<String>().listIterator()).add("abc");
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableIterator.set()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void set_Fail() {
        UnmodifiableListIterator.of(new ArrayList<String>().listIterator()).set("abc");
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableIterator.remove()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_Fail() {
        UnmodifiableListIterator.of(new ArrayList<String>().listIterator()).remove();
    }
}
