package com.googlecode.kevinarpe.papaya;

abstract class AbstractThreadWithException<TException extends Exception>
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