package com.googlecode.kevinarpe.papaya.jooq.sqlite;

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

import com.googlecode.kevinarpe.papaya.PathUtils;
import com.googlecode.kevinarpe.papaya.exception.ThrowableToStringServiceFactory;
import com.googlecode.kevinarpe.papaya.jooq.DatabaseConnectionShutdownHookService;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerService;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerServiceImpl;
import com.googlecode.kevinarpe.papaya.string.ThrowingMessageFormatterImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SqliteConnectionFactoryImpTest {

    @Mock
    private DatabaseConnectionShutdownHookService mockShutdownHookService;
    private SqliteConnectionFactoryImp classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {

        MockitoAnnotations.openMocks(this);

        final LoggerService loggerService =
            new LoggerServiceImpl(
                ThrowableToStringServiceFactory.DEFAULT_IMPL,
                ThrowingMessageFormatterImpl.INSTANCE);

        this.classUnderTest = new SqliteConnectionFactoryImp(mockShutdownHookService, loggerService);
    }

    @Test
    public void newInstanceFromFile_Pass()
    throws Exception {

        final File filePath = File.createTempFile("newInstanceFromFile_PassWhenNewFile", ".sqlite");
        PathUtils.removeFile(filePath);
        {
            Assert.assertEquals(filePath.length(), 0);
            final Connection dbConn = classUnderTest.newInstanceFromFile(filePath);
            final Statement statement = dbConn.createStatement();
            // false means update count only -- no result object
            Assert.assertFalse(statement.execute("CREATE TABLE SAMPLE ( DIGIT INT NOT NULL )"));
            dbConn.close();
            Assert.assertTrue(filePath.length() > 0,
                String.format("filePath.length() > 0: %d > 0", filePath.length()));
        }
        {
            final long length0 = filePath.length();
            final Connection dbConn = classUnderTest.newInstanceFromFile(filePath);
            final Statement statement = dbConn.createStatement();
            statement.execute("CREATE TABLE SAMPLE2 ( DIGIT INT NOT NULL )");
            dbConn.close();
            Assert.assertTrue(filePath.length() > length0,
                String.format("filePath.length() > length0: %d > %d", filePath.length(), length0));
        }
        PathUtils.removeFile(filePath);
    }

    @Test
    public void newInstanceInMemory_Pass()
    throws Exception {

        final Connection dbConn = classUnderTest.newInstanceInMemory();
        final Statement statement = dbConn.createStatement();
        // false means update count only -- no result object
        Assert.assertFalse(statement.execute("CREATE TABLE SAMPLE ( DIGIT INT NOT NULL )"));
        dbConn.close();
    }
}
