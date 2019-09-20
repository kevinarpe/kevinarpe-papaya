package com.googlecode.kevinarpe.papaya.function;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FunctionalInterface
public interface ThrowingRunnable {

    void run()
    throws Exception;
}
