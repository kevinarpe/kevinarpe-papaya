package com.googlecode.kevinarpe.papaya;

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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
 * subclasses should override {@link #runWithException()}.  Any caught exception can be retrieved
 * with {@link #getException()} and cleared with {@link #clearException()}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Thread
 * @see #run()
 * @see #runWithException()
 * @see #getException()
 * @see #clearException()
 */
@NotFullyTested
// TODO: Re-write with delegates.  Use IntelliJ to generate the delegates!
public abstract class AbstractThreadWithException
extends Thread {
    
    private Exception _exception;
    
    /**
     * Sets the exception reference.  Normally, there is no need to call this method directly.  The
     * method {@link #run()} catches exceptions throws by {@link #runWithException()} and sets the
     * exception reference directly.  This method exists only for subclasses if they need to
     * control the setting of caught exceptions differently than the standard implementation in
     * this class.
     * 
     * @param exception
     *        reference to a subclass of {@link Exception}
     */
    protected void setException(Exception exception) {
        _exception = exception;
    }
    
    /**
     * Retrieves the caught exception from {@link #runWithException()}.  If this exception is
     * rethrown, it is best to wrap in another exception.  Without a wrapper, it may be confusing
     * to catching code the understand the source of the error, as the callstack will not correct
     * show the link between threads.
     * <p>
     * After rethrowing the exception, it may be prudent to clear it via {@link #clearException()},
     * else the same exception may be rethrown multiple times.  However, the option is left to the
     * developer.  There may be rare cases where it is necessary or valuable to rethrow the same
     * exception more than once.
     * 
     * @return references to exception thrown by {@link #runWithException()}.
     *         May be {@code null}
     * 
     * @see #runWithException()
     * @see #clearException()
     */
    public Exception getException() {
        return _exception;
    }
    
    /**
     * Clears any exception previously thrown by {@link #runWithException()}.
     * 
     * @see #runWithException()
     * @see #getException()
     */
    public void clearException() {
        _exception = null;
    }
    
    /**
     * Calls {@link #runWithException()} and catches any exception thrown.  Subclasses should
     * <b>not</b> override this method; instead override {@link #runWithException()}.
     * 
     * @see #runWithException()
     * @see #getException()
     * @see #clearException()
     */
    @Override
    public void run() {
        try {
            runWithException();
        }
        catch (Exception e) {
            setException(e);
        }
    }
    
    /**
     * Subclasses must override this method, which is called by {@link #run()}.  Implementations
     * are welcome to throw exceptions of type {@code TException}.
     * 
     * @throws Exception
     *         on error
     * 
     * @see #getException()
     * @see #clearException()
     */
    public abstract void runWithException()
    throws Exception;
}
