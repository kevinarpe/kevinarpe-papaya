package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.Lists;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UnmodifiableForwardingListTest {

    private static class UnmodifiableForwardingListImpl
    extends UnmodifiableForwardingList<String> {

        private final List<String> list = Lists.newArrayList("abc", "def", "ghi");

        @Override
        protected List<String> delegate() {
            return list;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableForwardingList.listIterator()
    //

    @Test
    public void listIterator_Pass() {
        Assert.assertEquals(
            UnmodifiableListIterator.class,
            new UnmodifiableForwardingListImpl().listIterator().getClass());
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableForwardingList.listIterator(int)
    //

    @Test
    public void listIterator_Pass2() {
        Assert.assertEquals(
            UnmodifiableListIterator.class,
            new UnmodifiableForwardingListImpl().listIterator(1).getClass());
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableForwardingList.set(int, TValue)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void set_Fail() {
        new UnmodifiableForwardingListImpl().set(99, null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableForwardingList.subList(int, int)
    //

    @Test
    public void subList_Pass() {
        List<String> sublist = new UnmodifiableForwardingListImpl().subList(1, 2);
        Assert.assertTrue(sublist instanceof UnmodifiableForwardingList);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void subList_FailOnRemove() {
        List<String> sublist = new UnmodifiableForwardingListImpl().subList(1, 2);
        sublist.remove(0);
    }
}
