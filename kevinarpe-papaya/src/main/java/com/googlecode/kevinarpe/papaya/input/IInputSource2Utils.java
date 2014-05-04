package com.googlecode.kevinarpe.papaya.input;

import java.io.IOException;
import java.util.Collection;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface IInputSource2Utils {

    void checkValid(InputSource2 inputSource, String argName);

    void checkValid(Collection<InputSource2> inputSourceCollection, String argName);

    void close(InputSource2 inputSource)
    throws IOException;

    void closeQuietly(InputSource2 inputSource);
}
