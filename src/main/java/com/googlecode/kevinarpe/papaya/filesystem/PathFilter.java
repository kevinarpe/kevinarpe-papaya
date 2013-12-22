package com.googlecode.kevinarpe.papaya.filesystem;

import java.io.File;

public interface PathFilter {

    boolean accept(File path, int depth);
}
