package com.googlecode.kevinarpe.papaya.filesystem;

import java.util.Iterator;

public interface PathIterator<TValue>
extends Iterator<TValue> {

//    boolean hasPrevious();
//    File previous();

    int depth();
}
