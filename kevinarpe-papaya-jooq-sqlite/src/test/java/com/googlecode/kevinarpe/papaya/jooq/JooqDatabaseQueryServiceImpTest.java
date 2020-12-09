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

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrowerImpl;
import com.googlecode.kevinarpe.papaya.exception.ThrowableToStringServiceFactory;
import com.googlecode.kevinarpe.papaya.function.count.AnyCountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.ExactlyCountMatcher;
import com.googlecode.kevinarpe.papaya.jooq.sqlite.SqliteConnectionFactoryImp;
import com.googlecode.kevinarpe.papaya.jooq.sqlite.SqliteJooqDatabaseConnectionFactoryImp;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerService;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerServiceImpl;
import com.googlecode.kevinarpe.papaya.string.ThrowingMessageFormatterImpl;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import java.sql.Connection;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class JooqDatabaseQueryServiceImpTest {

    public static final Name TABLE_NAME = DSL.name("SAMPLE");

    public static final Table<Record> TABLE = DSL.table(TABLE_NAME);

    public static final Field<Long> COLUMN_DIGIT =
        DSL.field(DSL.name(TABLE.getName(), "DIGIT"),
            SQLDataType.BIGINT.nullable(false));

    private JooqDatabaseQueryServiceImp classUnderTest;
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

        this.classUnderTest = new JooqDatabaseQueryServiceImp(exceptionThrower);

        final Connection dbConn = new SqliteConnectionFactoryImp(Mockito.mock(DatabaseConnectionShutdownHookService.class), loggerService)
            .newInstanceInMemory();

        this.dbConn = new SqliteJooqDatabaseConnectionFactoryImp().newInstance(dbConn);
    }

    @AfterMethod
    public void afterEachTestMethod()
    throws Exception {

        dbConn.close();
    }

    /////////////////////////////////////////////
    /// selectRowList
    ///

    @Test
    public void selectRowList_Pass()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);
        final Result<Record1<Long>> result =
            classUnderTest.selectRowList(
                dbConn, dbConn.jooqDSLContext.select(COLUMN_DIGIT).from(TABLE), new ExactlyCountMatcher(2));
        Assert.assertEquals((long) result.get(0).value1(), 7L);
        Assert.assertEquals((long) result.get(1).value1(), 6L);
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QFailed to execute SELECT query:\\E.*$")
    public void selectRowList_FailWhenBadQuery()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);
        try {
            classUnderTest.selectRowList(
                dbConn, dbConn.jooqDSLContext.resultQuery("blah"), AnyCountMatcher.INSTANCE);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getCause().getClass(), DataAccessException.class);
            throw e;
        }
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 3 row(s), but found 2 row(s):\\E.*$")
    public void selectRowList_FailWhenRowCountNotMatch()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.selectRowList(
            dbConn, dbConn.jooqDSLContext.select(COLUMN_DIGIT).from(TABLE), new ExactlyCountMatcher(3));
    }

    /////////////////////////////////////////////
    /// selectRow
    ///

    @Test
    public void selectRow_Pass()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        final Record1<Long> rec =
            classUnderTest.selectRow(dbConn,
                dbConn.jooqDSLContext.select(COLUMN_DIGIT).from(TABLE).where(COLUMN_DIGIT.eq(7L)));

        Assert.assertEquals((long) rec.value1(), 7L);
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 1 row(s), but found 2 row(s):\\E.*$")
    public void selectRow_FailWhenRowCountNotMatch()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.selectRow(dbConn, dbConn.jooqDSLContext.select(COLUMN_DIGIT).from(TABLE));
    }

    /////////////////////////////////////////////
    /// trySelectRow
    ///

    @Test
    public void trySelectRow_PassWhenExists()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        @Nullable
        final Record1<Long> rec =
            classUnderTest.trySelectRow(dbConn,
                dbConn.jooqDSLContext.select(COLUMN_DIGIT).from(TABLE).where(COLUMN_DIGIT.eq(7L)));

        Assert.assertNotNull(rec);
        Assert.assertEquals((long) rec.value1(), 7L);
    }

    @Test
    public void trySelectRow_PassWhenNotExists()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        @Nullable
        final Record1<Long> rec =
            classUnderTest.trySelectRow(dbConn,
                dbConn.jooqDSLContext.select(COLUMN_DIGIT).from(TABLE).where(COLUMN_DIGIT.eq(5L)));

        Assert.assertNull(rec);
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected at most 1 row(s), but found 2 row(s):\\E.*$")
    public void trySelectRow_FailWhenTooManyRows()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.trySelectRow(dbConn, dbConn.jooqDSLContext.select(COLUMN_DIGIT).from(TABLE));
    }

    /////////////////////////////////////////////
    /// selectValueList
    ///

    @Test
    public void selectValueList_Pass()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        final ImmutableList<Long> valueList =
            classUnderTest.selectValueList(
                dbConn, dbConn.jooqDSLContext.select(COLUMN_DIGIT).from(TABLE), new ExactlyCountMatcher(2));

        Assert.assertEquals(valueList, ImmutableList.of(7L, 6L));
    }

    /////////////////////////////////////////////
    /// selectValue
    ///

    @Test
    public void selectValue_Pass()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        final Long value =
            classUnderTest.selectValue(dbConn,
                dbConn.jooqDSLContext.select(COLUMN_DIGIT).from(TABLE).where(COLUMN_DIGIT.eq(7L)));

        Assert.assertEquals((long) value, 7L);
    }

    /////////////////////////////////////////////
    /// trySelectValue
    ///

    @Test
    public void trySelectValue_PassWhenExists()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        @Nullable
        final Long value =
            classUnderTest.trySelectValue(dbConn,
                dbConn.jooqDSLContext.select(COLUMN_DIGIT).from(TABLE).where(COLUMN_DIGIT.eq(7L)));

        Assert.assertNotNull(value);
        Assert.assertEquals((long) value, 7L);
    }

    @Test
    public void trySelectValue_PassWhenNotExists()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        @Nullable
        final Long value =
            classUnderTest.trySelectValue(dbConn,
                dbConn.jooqDSLContext.select(COLUMN_DIGIT).from(TABLE).where(COLUMN_DIGIT.eq(5L)));

        Assert.assertNull(value);
    }

    /////////////////////////////////////////////
    /// insertRows
    ///

    @Test
    public void insertRows_Pass()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);

        classUnderTest.insertRows(dbConn,
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L),
            new ExactlyCountMatcher(2));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 3 row(s), but found 2 row(s):\\E.*$")
    public void insertRows_FailWhenRowCountNotMatch()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.insertRows(dbConn,
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L),
            new ExactlyCountMatcher(3));
    }

    /////////////////////////////////////////////
    /// insertOneRow
    ///

    @Test
    public void insertOneRow_Pass()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);

        classUnderTest.insertOneRow(dbConn, dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 1 row(s), but found 2 row(s):\\E.*$")
    public void insertOneRow_FailWhenRowCountNotMatch()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);

        classUnderTest.insertOneRow(dbConn,
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QFailed to execute INSERT query:\\E.*$")
    public void insertOneRow_FailWhenBadQuery()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        try {
            classUnderTest.insertOneRow(dbConn,
                dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(ImmutableList.of("abc")));
        }
        catch (Exception e) {
            Assert.assertEquals(e.getCause().getClass(), DataAccessException.class);
            throw e;
        }
    }

    /////////////////////////////////////////////
    /// updateRows
    ///

    @Test
    public void updateRows_Pass()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.updateRows(
            dbConn, dbConn.jooqDSLContext.update(TABLE).set(COLUMN_DIGIT, 5L), new ExactlyCountMatcher(2));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 3 row(s), but found 2 row(s):\\E.*$")
    public void updateRows_FailWhenRowCountNotMatch()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.updateRows(
            dbConn, dbConn.jooqDSLContext.update(TABLE).set(COLUMN_DIGIT, 5L), new ExactlyCountMatcher(3));
    }

    /////////////////////////////////////////////
    /// updateOneRow
    ///

    @Test
    public void updateOneRow_Pass()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.updateOneRow(dbConn,
            dbConn.jooqDSLContext.update(TABLE).set(COLUMN_DIGIT, 5L).where(COLUMN_DIGIT.eq(7L)));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 1 row(s), but found 2 row(s):\\E.*$")
    public void updateOneRow_FailWhenRowCountNotMatch()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.updateOneRow(dbConn, dbConn.jooqDSLContext.update(TABLE).set(COLUMN_DIGIT, 5L));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QFailed to execute UPDATE query:\\E.*$")
    public void updateOneRow_FailWhenBadQuery()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);
        try {
            classUnderTest.updateOneRow(dbConn,
                dbConn.jooqDSLContext.update(TABLE).set(COLUMN_DIGIT, 5L).where("blah"));
        }
        catch (Exception e) {
            Assert.assertEquals(e.getCause().getClass(), DataAccessException.class);
            throw e;
        }
    }

    /////////////////////////////////////////////
    /// deleteRows
    ///

    @Test
    public void deleteRows_Pass()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.deleteRows(dbConn, dbConn.jooqDSLContext.deleteFrom(TABLE), new ExactlyCountMatcher(2));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 3 row(s), but found 2 row(s):\\E.*$")
    public void deleteRows_FailWhenRowCountNotMatch()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.deleteRows(dbConn, dbConn.jooqDSLContext.deleteFrom(TABLE), new ExactlyCountMatcher(3));
    }

    /////////////////////////////////////////////
    /// deleteOneRow
    ///

    @Test
    public void deleteOneRow_Pass()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.deleteOneRow(dbConn, dbConn.jooqDSLContext.deleteFrom(TABLE).where(COLUMN_DIGIT.eq(7L)));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QExpected exactly 1 row(s), but found 2 row(s):\\E.*$")
    public void deleteOneRow_FailWhenRowCountNotMatch()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);

        classUnderTest.deleteOneRow(dbConn, dbConn.jooqDSLContext.deleteFrom(TABLE));
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^\\QFailed to execute DELETE query:\\E.*$")
    public void deleteOneRow_FailWhenBadQuery()
    throws Exception {

        Assert.assertEquals(dbConn.jooqDSLContext.createTable(TABLE).column(COLUMN_DIGIT).execute(), 0);
        Assert.assertEquals(
            dbConn.jooqDSLContext.insertInto(TABLE).columns(COLUMN_DIGIT).values(7L).values(6L).execute(), 2);
        try {
            classUnderTest.deleteOneRow(dbConn, dbConn.jooqDSLContext.deleteFrom(TABLE).where("blah"));
        }
        catch (Exception e) {
            Assert.assertEquals(e.getCause().getClass(), DataAccessException.class);
            throw e;
        }
    }
}
