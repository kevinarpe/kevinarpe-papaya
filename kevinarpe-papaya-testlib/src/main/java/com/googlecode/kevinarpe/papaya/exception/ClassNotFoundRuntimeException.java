package com.googlecode.kevinarpe.papaya.exception;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ClassNotFoundRuntimeException
extends RuntimeException {

    public ClassNotFoundRuntimeException() {
        super();
    }

    public ClassNotFoundRuntimeException(String message) {
        super(message);
    }

    public ClassNotFoundRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassNotFoundRuntimeException(Throwable cause) {
        super(cause);
    }
}
