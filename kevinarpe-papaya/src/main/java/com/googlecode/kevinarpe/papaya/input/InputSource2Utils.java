package com.googlecode.kevinarpe.papaya.input;

import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class InputSource2Utils
extends StatelessObject
implements IInputSource2Utils {

    public static final InputSource2Utils INSTANCE = new InputSource2Utils();

    @Override
    public void checkValid(InputSource2 inputSource, String argName) {
        ObjectArgs.checkNotNull(inputSource, argName);

        if (null == inputSource.getByteStream() && null == inputSource.getCharacterStream()) {
            throw new IllegalArgumentException(String.format(
                "Argument '%s': Both byte- and character-based streams are null; exactly one must be non-null: %s",
                argName, inputSource));
        }
        if (null != inputSource.getByteStream() && null != inputSource.getCharacterStream()) {
            throw new IllegalArgumentException(String.format(
                "Argument '%s': Both byte- and character-based streams are not null; exactly one must be non-null: %s",
                argName, inputSource));
        }
    }

    @Override
    public void checkValid(Collection<InputSource2> inputSourceCollection, String argName) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(inputSourceCollection, argName);

        final int size = inputSourceCollection.size();
        int index = 0;
        for (InputSource2 inputSource : inputSourceCollection) {
            String argName2 = String.format("%s[%d]", argName, index);
            checkValid(inputSource, argName2);
            ++index;
        }
    }

    @Override
    public void close(InputSource2 inputSource)
    throws IOException {
        checkValid(inputSource, "inputSource");

        if (null != inputSource.getByteStream()) {
            _close(inputSource.getByteStream(), "byte-based stream");
        }
        else {
            _close(inputSource.getCharacterStream(), "character-based stream");
        }
    }

    private void _close(Closeable closeable, String description)
    throws IOException {
        try {
            closeable.close();
        }
        catch (IOException e) {
            String msg = String.format("Failed to close %s", description);
            throw new IOException(msg, e);
        }
    }

    @Override
    public void closeQuietly(InputSource2 inputSource) {
        try {
            close(inputSource);
        }
        catch (IOException ignore) {
            int dummy = 1;  // debug breakpoint
        }
    }
}
