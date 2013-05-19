package com.googlecode.kevinarpe.papaya.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.googlecode.kevinarpe.papaya.annotations.NotFullyTested;
import com.googlecode.kevinarpe.papaya.args.PathArgs;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.args.StringArgs;

/**
 * This is a more specific version of {@link FileNotFoundException} for resources available via
 * {@link Class#getResourceAsStream(String)}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 * @see PathArgs#checkClassResourceAsStreamExists(Class, String, String)
 */
@NotFullyTested
public class ClassResourceNotFoundException
extends IOException {

    private static final long serialVersionUID = 0;
    
    private final Class<?> _classForResource;
    private final String _resourceName;
    
    public ClassResourceNotFoundException(
            Class<?> classForResource, String resourceName, String message) {
        super(StringArgs.checkNotEmptyOrWhitespace(message, "message"));
        _classForResource = ObjectArgs.checkNotNull(classForResource, "classForResource");
        _resourceName = StringArgs.checkNotEmpty(resourceName, "resourceName");
    }

    public Class<?> getClassForResource() {
        return _classForResource;
    }

    public String getResourceName() {
        return _resourceName;
    }
}
