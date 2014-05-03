package com.googlecode.kevinarpe.papaya.input;

import com.googlecode.kevinarpe.papaya.argument.PathArgs;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.exception.PathExceptionReason;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
public final class FileInputSource
implements InputSource {

    private final File _filePath;
    private final InputStreamReader _inputStreamReader;

    public FileInputSource(File filePath)
    throws PathException {
        this(filePath, new InputStreamReader(_newFileInputStream(filePath)));
    }

    public FileInputSource(File filePath, Charset charset)
    throws PathException {
        this(filePath, new InputStreamReader(_newFileInputStream(filePath), charset));
    }

    public FileInputSource(File filePath, CharsetDecoder charsetDecoder)
    throws PathException {
        this(filePath, new InputStreamReader(_newFileInputStream(filePath), charsetDecoder));
    }

    public FileInputSource(File filePath, String charsetName)
    throws PathException, UnsupportedEncodingException {
        this(filePath, new InputStreamReader(_newFileInputStream(filePath), charsetName));
    }

    public FileInputSource(File filePath, InputStreamReader inputStreamReader)
    throws PathException {
        _filePath = filePath;
        _inputStreamReader = inputStreamReader;
    }

    private static FileInputStream _newFileInputStream(File filePath)
    throws PathException {
        PathArgs.checkFileExists(filePath, "filePath");
        try {
            FileInputStream x = new FileInputStream(filePath);
            return x;
        }
        catch (FileNotFoundException e) {
            String msg = String.format("Failed to open file: '%s'", filePath.getAbsolutePath());
            throw new PathException(PathExceptionReason.UNKNOWN, filePath, (File) null, msg, e);
        }
    }

    @Override
    public InputStream getByteStream() {
        return null;
    }

    @Override
    public Reader getCharacterStream() {
        return _inputStreamReader;
    }

    @Override
    public String toString() {
        String x = String.format("File: '%s'", _filePath.getAbsolutePath());
        return x;
    }
}
