package com.googlecode.kevinarpe.papaya;

import java.io.IOException;

/**
 * Reduces the number of methods required for implementation by interface {@link Appendable}
 * from three to one.  Only {@link #append(CharSequence)} must be implemented by subclasses.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public abstract class AbstractSimplifiedAppendable
implements Appendable {

    /**
     * This method call {@link #append(CharSequence)}.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public Appendable append(CharSequence csq, int start, int end)
    throws IOException {
        CharSequence csq2 = csq.subSequence(start, end);
        Appendable result = append(csq2);
        return result;
    }


    /**
     * This method call {@link #append(CharSequence)}.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public Appendable append(char c)
    throws IOException {
        String x = String.valueOf(c);
        Appendable result = append(x);
        return result;
    }
}