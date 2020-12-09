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

import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrowerImpl;
import com.googlecode.kevinarpe.papaya.exception.ThrowableToStringServiceFactory;
import com.googlecode.kevinarpe.papaya.jooq.DatabaseConnectionShutdownHookService;
import com.googlecode.kevinarpe.papaya.jooq.JooqDatabaseConnection;
import com.googlecode.kevinarpe.papaya.jooq.JooqDatabaseQueryService;
import com.googlecode.kevinarpe.papaya.jooq.JooqDatabaseQueryServiceImp;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerService;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerServiceImpl;
import com.googlecode.kevinarpe.papaya.string.ThrowingMessageFormatterImpl;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class JooqSqliteDatabaseQueryServiceTest {

    private JooqSqliteDatabaseQueryServiceImp classUnderTest;
    private JooqDatabaseConnection dbConn;

    @BeforeMethod
    public void beforeEachTestMethod()
    throws Exception {

        MockitoAnnotations.openMocks(this);

        final LoggerService loggerService =
            new LoggerServiceImpl(
                ThrowableToStringServiceFactory.DEFAULT_IMPL,
                ThrowingMessageFormatterImpl.INSTANCE);

        final ExceptionThrower exceptionThrower = new ExceptionThrowerImpl(ThrowingMessageFormatterImpl.INSTANCE);

        final JooqDatabaseQueryService jooqDatabaseQueryService = new JooqDatabaseQueryServiceImp(exceptionThrower);

        this.classUnderTest = new JooqSqliteDatabaseQueryServiceImp(jooqDatabaseQueryService, exceptionThrower);

        final Connection dbConn = new SqliteConnectionFactoryImp(Mockito.mock(DatabaseConnectionShutdownHookService.class), loggerService)
            .newInstanceInMemory();

        this.dbConn = new SqliteJooqDatabaseConnectionFactoryImp().newInstance(dbConn);
    }

    @AfterMethod
    public void afterEachTestMethod()
    throws Exception {

        dbConn.close();
    }

    @Test
    public void getSqliteLastInsertRowId_Pass()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.execute("CREATE TABLE SAMPLE ( DIGIT INT NOT NULL )"), 0);
        Assert.assertEquals(dbConn.jooqDSLContext.execute("INSERT INTO SAMPLE VALUES ( 7 )"), 1);
        final long rowId = classUnderTest.getSqliteLastInsertRowId(dbConn);
        Assert.assertEquals(dbConn.jooqDSLContext.execute("INSERT INTO SAMPLE VALUES ( 6 )"), 1);
        final long rowId2 = classUnderTest.getSqliteLastInsertRowId(dbConn);
        Assert.assertNotEquals(rowId, rowId2);
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QInternal error: Must insert row before calling this method! [last_insert_rowid(): 0]\\E$")
    public void getSqliteLastInsertRowId_FailWhenCallBeforeInsert()
    throws Exception {

        classUnderTest.getSqliteLastInsertRowId(dbConn);
    }
}
