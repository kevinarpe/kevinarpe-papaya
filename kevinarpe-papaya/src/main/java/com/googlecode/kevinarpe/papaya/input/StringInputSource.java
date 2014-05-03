package com.googlecode.kevinarpe.papaya.input;

import com.google.common.base.Splitter;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Pattern;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
public final class StringInputSource
implements InputSource {

    private final String _text;
    private final StringReader _stringReader;

    public StringInputSource(String text) {
        _text = ObjectArgs.checkNotNull(text, "text");
        _stringReader = new StringReader(_text);
    }

    @Override
    public InputStream getByteStream() {
        return null;
    }

    @Override
    public Reader getCharacterStream() {
        return _stringReader;
    }

    @Override
    public String toString() {
        String fragment = Splitter.on(Pattern.compile("\r?\n")).split(_text).iterator().next();
        String x = String.format("String: '%s...'", fragment);
        return x;
    }
}
