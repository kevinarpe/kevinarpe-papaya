package com.googlecode.kevinarpe.papaya.logging.slf4j;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

/**
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link SLF4JLevelLoggerUtils} will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ISLF4JLevelLoggerUtils {

    /**
     * This is a convenience method to call
     * {@link #newInstance(ILoggerFactory, SLF4JLogLevel, Class)} with the default global SLF4J
     * {@link ILoggerFactory}.
     */
    SLF4JLevelLogger newInstance(SLF4JLogLevel logLevel, Class<?> clazz);

    /**
     * Creates a new logger than logs all messages at a single level.
     *
     * @param loggerFactory
     *        usually {@link LoggerFactory#getILoggerFactory()}, but in rare cases may be different
     * @param logLevel
     *        the single level at which the new logging shall log, e.g., {@link SLF4JLogLevel#INFO}
     * @param clazz
     *        used to set the name of the new logger via {@link Class#getName()}
     *
     * @return new instance that implements inteface {@link SLF4JLevelLogger}
     *
     * @throws NullPointerException
     *         if any argument is {@code null}
     */
    SLF4JLevelLogger newInstance(
            ILoggerFactory loggerFactory, SLF4JLogLevel logLevel, Class<?> clazz);
}
