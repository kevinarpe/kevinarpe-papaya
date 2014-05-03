package com.googlecode.kevinarpe.papaya.input;

import java.io.InputStream;
import java.io.Reader;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
public interface InputSource {

    InputStream getByteStream();
    Reader getCharacterStream();
}
