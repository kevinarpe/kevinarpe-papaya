package com.googlecode.kevinarpe.papaya;

import java.io.IOException;

import com.googlecode.kevinarpe.papaya.annotations.NotFullyTested;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

/**
 * Extension of class {@link Thread} to allow exceptions to be thrown.  Instead of {@link #run()},
 * subclasses should override {@link #runWithException()}.  Any caught exception can be rethrown
 * with {@link #rethrowException()} or {@link #rethrowThenClearException().
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TException>
 *        Type of exception to be throws by {@link #runWithException()}, e.g., {@link IOException}
 *        
 * @see Thread
 * @see #run()
 * @see #runWithException()
 * @see #getException()
 * @see #clearException()
 * @see #rethrowException()
 * @see #rethrowThenClearException()
 */
@NotFullyTested
public abstract class AbstractThreadWithException<TException extends Exception>
extends Thread {
    
    private TException _exception;
    
    /**
     * Sets the exception reference.  Normally, there is no need to call this method directly.  The
     * method {@link #run()} catches exceptions throws by {@link #runWithException()} and sets the
     * exception reference directly.
     * 
     * @param exception
     *        reference to a subclass of {@link Exception}
     */
    protected void setException(TException exception) {
        _exception = exception;
    }
    
    /**
     * @return references to exception thrown by {@link #runWithException()}.
     *         May be {@code null}
     * 
     * @see #runWithException()
     * @see #clearException()
     * @see #rethrowException()
     * @see #rethrowThenClearException()
     */
    public TException getException() {
        return _exception;
    }
    
    /**
     * Clears any exception previously thrown by {@link #runWithException()}.
     * 
     * @see #runWithException()
     * @see #getException()
     * @see #rethrowException()
     * @see #rethrowThenClearException()
     */
    public void clearException() {
        _exception = null;
    }
    
//    /**
//     * If {@link #runWithException()} threw an exception, call this method to rethrow from a
//     * different thread.  To also clear the caught exception, see
//     * {@link #rethrowThenClearException()}.
//     * 
//     * @throws TException
//     *         exception caught by {@link #runWithException()}
//     * 
//     * @see #runWithException()
//     * @see #getException()
//     * @see #clearException()
//     * @see #rethrowThenClearException()
//     */
//    public void rethrowException()
//    throws TException {
//        if (null != _exception) {
//            throw _exception;
//        }
//    }
//    
//    /**
//     * Similar to {@link #rethrowException()}.  In addition, the exception is cleared before
//     * rethrowing.  This will prevent scenarios where a caught exception is rethrown multiple
//     * times.  Rethrowing once is likely more useful in most cases.
//     * 
//     * @throws TException
//     *         exception caught by {@link #runWithException()}
//     * 
//     * @see #runWithException()
//     * @see #getException()
//     * @see #clearException()
//     * @see #rethrowException()
//     */
//    public void rethrowThenClearException()
//    throws TException {
//        if (null != _exception) {
//            TException x = _exception;
//            _exception = null;
//            throw x;
//        }
//    }
    
    /**
     * Calls {@link #runWithException()} and catches any exception thrown.  Subclasses should
     * <b>not</b> override this method; instead override {@link #runWithException()}.
     * 
     * @see #runWithException()
     * @see #getException()
     * @see #clearException()
     * @see #rethrowException()
     * @see #rethrowThenClearException()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        try {
            runWithException();
        }
        catch (Exception e) {
            setException((TException) e);
        }
    }
    
    /**
     * Subclasses must override this method, which is called by {@link #run()}.
     * 
     * @throws TException
     *         on error
     * 
     * @see #getException()
     * @see #clearException()
     * @see #rethrowException()
     * @see #rethrowThenClearException()
     */
    public abstract void runWithException()
    throws TException;
}
