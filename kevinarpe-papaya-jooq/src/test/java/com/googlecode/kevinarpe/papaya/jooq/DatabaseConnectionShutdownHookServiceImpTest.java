package com.googlecode.kevinarpe.papaya.jooq;

/*-
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

import com.googlecode.kevinarpe.papaya.exception.ThrowableToStringServiceFactory;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerService;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerServiceImpl;
import com.googlecode.kevinarpe.papaya.string.ThrowingMessageFormatterImpl;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class DatabaseConnectionShutdownHookServiceImpTest {

    @Mock
    private Runtime mockRuntime;
    @Mock
    private Connection mockConnection;
    private DatabaseConnectionShutdownHookServiceImp classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {

        MockitoAnnotations.openMocks(this);

        final LoggerService loggerService =
            new LoggerServiceImpl(
                ThrowableToStringServiceFactory.DEFAULT_IMPL,
                ThrowingMessageFormatterImpl.INSTANCE);

        this.classUnderTest = new DatabaseConnectionShutdownHookServiceImp(mockRuntime, loggerService);
    }

    @Test
    public void addShutdownHook_Pass() {

        classUnderTest.addShutdownHook("description", mockConnection);
        Mockito.verify(mockRuntime).addShutdownHook(Mockito.any());
    }

    @Test
    public void createThread_Pass()
    throws Exception {

        final Thread thread = classUnderTest.createThread("description", mockConnection);
        thread.start();
        thread.join();

        Mockito.verify(mockConnection).close();
    }

    @Test
    public void createThread_FailWhenConnectionCloseThrows()
    throws Exception {

        final SQLException exception = new SQLException("blah");
        Mockito.doThrow(exception).when(mockConnection).close();

        final Throwable[] throwableRef = new Throwable[] {null};
        final Thread thread = classUnderTest.createThread("description", mockConnection);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                throwableRef[0] = e;
            }
        });
        thread.start();
        thread.join();

        Assert.assertSame(throwableRef[0].getCause(), exception);
    }
}
