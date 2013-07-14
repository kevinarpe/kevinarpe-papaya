package com.googlecode.kevinarpe.papaya;

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
 * with {@link #rethrowException()}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TException>
 *        Type of exception to be throws by {@link #runWithException()}
 *        
 * @see Thread
 * @see #run()
 * @see #runWithException()
 * @see #rethrowException()
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
     */
    public TException getException() {
        return _exception;
    }
    
    /**
     * If {@link #runWithException()} threw an exception, call this method to rethrow from a
     * different thread.
     * 
     * @throws TException
     *         exception caught by {@link #runWithException()}
     * 
     * @see #runWithException()
     */
    public void rethrowException()
    throws TException {
        if (null != _exception) {
            throw _exception;
        }
    }
    
    /**
     * Calls {@link #runWithException()} and catches any exception thrown.  Subclasses should
     * <b>not</b> override this method; instead override {@link #runWithException()}.
     * 
     * @see #runWithException()
     * @see #getException()
     * @see #rethrowException()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        try {
            runWithException();
        }
        catch (Exception e) {
            _exception = (TException) e;
        }
    }
    
    /**
     * Subclasses must override this method, which is called by {@link #run()}.
     * 
     * @throws TException
     *         on error
     * 
     * @see #getException()
     * @see #rethrowException()
     */
    public abstract void runWithException()
    throws TException;
}
