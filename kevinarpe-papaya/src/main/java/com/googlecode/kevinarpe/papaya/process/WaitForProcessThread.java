package com.googlecode.kevinarpe.papaya.process;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.AbstractThreadWithException;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

/**
 * Used by {@link Process2} to wait (with a timeout period) for a child process to terminate.
 * Normally, there is no need to use this class directly, or subclass it.  The class
 * {@code Process2} uses this class internally.
 * <p>
 * Since there is no timeout available for {@link Process#waitFor()}, this simple thread class was
 * written to wait for the child process to terminate in a different thread.  The main thread then
 * attempts to {@link Thread#join(long)} with a timeout.  Why is this so hard?  Something feels
 * lacking in the original JDK API for child process management.
 * <p>
 * It is possible to customize the behavior of this class through subclassing and overriding
 * {@link Process2#createWaitForProcessThread()}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Process2
 * @see #getOptExitValue()
 */
@FullyTested
public class WaitForProcessThread
extends AbstractThreadWithException {
    
    protected final Process _process;
    protected Integer _optExitValue;
    
    /**
     * @param process
     *        reference to a child process handle
     * 
     * @throws NullPointerException
     *         if {@code process} is {@code null}
     */
    public WaitForProcessThread(Process process) {
        _process = ObjectArgs.checkNotNull(process, "process");
    }
    
    /**
     * Retrieves the exit value of the child process.  Default value is {@code null}.  If the
     * result is not {@code null}, the child process terminated and its exit value was reaped.
     * 
     * @return optional exit value of child process.  May be {@code null}.
     */
    public Integer getOptExitValue() {
        return _optExitValue;
    }
    
    /**
     * Wait indefinitely for the child process to terminate.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void runWithException()
    throws InterruptedException {
        _optExitValue = _process.waitFor();
    }
}
