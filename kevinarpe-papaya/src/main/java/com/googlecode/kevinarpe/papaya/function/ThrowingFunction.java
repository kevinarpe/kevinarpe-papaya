package com.googlecode.kevinarpe.papaya.function;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FunctionalInterface
public interface ThrowingFunction<Input, Output> {

    Output apply(Input input)
    throws Exception;
}
