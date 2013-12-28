package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.Lists;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class FixedSizeForwardingListTest {

    private static class FixedSizeForwardingListImpl
    extends FixedSizeForwardingList<String> {

        private final List<String> list = Lists.newArrayList("abc", "def", "ghi");

        @Override
        protected List<String> delegate() {
            return list;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeForwardingList.listIterator()
    //

    @Test
    public void listIterator_Pass() {
        Assert.assertEquals(
            FixedSizeListIterator.class,
            new FixedSizeForwardingListImpl().listIterator().getClass());
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeForwardingList.listIterator(int)
    //

    @Test
    public void listIterator_Pass2() {
        Assert.assertEquals(
            FixedSizeListIterator.class,
            new FixedSizeForwardingListImpl().listIterator(1).getClass());
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeForwardingList.subList(int, int)
    //

    @Test
    public void subList_Pass() {
        List<String> sublist = new FixedSizeForwardingListImpl().subList(1, 2);
        Assert.assertTrue(sublist instanceof FixedSizeForwardingList);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void subList_FailOnRemove() {
        List<String> sublist = new FixedSizeForwardingListImpl().subList(1, 2);
        sublist.remove(0);
    }
}
