package com.googlecode.kevinarpe.papaya.input;

import com.googlecode.kevinarpe.papaya.argument.PathArgs;
import com.googlecode.kevinarpe.papaya.exception.ClassResourceNotFoundException;

import java.io.InputStream;
import java.io.Reader;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
public final class ClassResourceInputSource
implements InputSource {

    private final Class<?> _clazz;
    private final String _pathname;
    private final InputStream _inputStream;

    public ClassResourceInputSource(Class<?> clazz, String pathname)
    throws ClassResourceNotFoundException {
        _clazz = clazz;
        _pathname = pathname;
        _inputStream = PathArgs.checkClassResourceAsStreamExists(clazz, pathname, "pathname");
    }

    @Override
    public InputStream getByteStream() {
        return _inputStream;
    }

    @Override
    public Reader getCharacterStream() {
        return null;
    }

    @Override
    public String toString() {
        String x = String.format("Class resource: %s -> '%s'", _clazz.getName(), _pathname);
        return x;
    }
}
