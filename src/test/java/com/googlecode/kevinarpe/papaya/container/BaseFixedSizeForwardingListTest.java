package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.Lists;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BaseFixedSizeForwardingListTest {

    private static class BaseFixedSizeForwardingListImpl
    extends BaseFixedSizeForwardingList<String> {

        private final List<String> list = Lists.newArrayList("abc", "def", "ghi");

        protected BaseFixedSizeForwardingListImpl() {
            super("sample");
        }

        @Override
        protected List<String> delegate() {
            return list;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.add(int, TValue)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void add_Fail() {
        new BaseFixedSizeForwardingListImpl().add(99, null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.addAll(int, Collection)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAll_Fail() {
        new BaseFixedSizeForwardingListImpl().addAll(99, null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.remove(int)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_Fail() {
        new BaseFixedSizeForwardingListImpl().remove(99);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.iterator()
    //

    @Test
    public void iterator_Pass() {
        Assert.assertEquals(
            UnmodifiableIterator.class,
            new BaseFixedSizeForwardingListImpl().iterator().getClass());
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.removeAll(Collection)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeAll_Fail() {
        new BaseFixedSizeForwardingListImpl().removeAll(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.add(TValue)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void add_Fail2() {
        new BaseFixedSizeForwardingListImpl().add(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.remove(Object)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_Fail2() {
        new BaseFixedSizeForwardingListImpl().remove(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.addAll(Collection)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAll_Fail2() {
        new BaseFixedSizeForwardingListImpl().addAll(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.retainAll(Collection)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void retainAll_Fail() {
        new BaseFixedSizeForwardingListImpl().retainAll(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.clear()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void clear_Fail() {
        new BaseFixedSizeForwardingListImpl().clear();
    }

}
