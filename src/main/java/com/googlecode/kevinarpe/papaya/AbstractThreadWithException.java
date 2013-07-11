package com.googlecode.kevinarpe.papaya;

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

public abstract class AbstractThreadWithException<TException extends Exception>
extends Thread {
    
    private TException _exception;
    
    protected void setException(TException exception) {
        _exception = exception;
    }
    
    public TException getException() {
        return _exception;
    }
    
    public void rethrowCaughtException()
    throws TException {
        if (null != _exception) {
            throw _exception;
        }
    }
    
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
    
    public abstract void runWithException()
    throws TException;
}
