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
import com.googlecode.kevinarpe.papaya.annotation.Blocking;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.function.count.AtMostCountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.CountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.ExactlyCountMatcher;
import org.jooq.Delete;
import org.jooq.Insert;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Update;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * ThreadSafe?  All implementations expected to be thread-safe by calling synchronize while executing queries.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * See JooqSqliteDatabaseQueryService in module kevinarpe-papaya-jooq-sqlite.
 */
// Scope: Global singleton
@ThreadSafe
public interface JooqDatabaseQueryService {

    /**
     * Selects rows, then checks result row count.
     *
     * @param dbConn
     *        database connection
     *
     * @param selectQuery
     *        query to select rows
     *
     * @param rowCountMatcher
     *        matcher for result row count
     *
     * @return result of select query
     *         <br>empty is possible if allowed by {@code rowCountMatcher}
     *
     * @throws Exception
     *         if database I/O error or query syntax error
     *         <br>if result row count does not match {@code rowCountMatcher}
     *
     * @see #selectRow(JooqDatabaseConnection, ResultQuery)
     * @see #trySelectRow(JooqDatabaseConnection, ResultQuery)
     * @see #selectValueList(JooqDatabaseConnection, ResultQuery, CountMatcher)
     */
    @EmptyContainerAllowed
    @Blocking
    <TRecord extends Record, TResultQuery extends ResultQuery<TRecord>>
    Result<TRecord>
    selectRowList(JooqDatabaseConnection dbConn,
                  TResultQuery selectQuery,
                  CountMatcher rowCountMatcher)
    throws Exception;

    /**
     * This is a convenience method to call {@link #selectRowList(JooqDatabaseConnection, ResultQuery, CountMatcher)}
     * <br>where {@code rowCountMatcher} is {@link ExactlyCountMatcher#ONE}.
     *
     * @see #selectRowList(JooqDatabaseConnection, ResultQuery, CountMatcher)
     * @see #selectValue(JooqDatabaseConnection, ResultQuery)
     */
    @Blocking
    <TRecord extends Record, TResultQuery extends ResultQuery<TRecord>>
    TRecord
    selectRow(JooqDatabaseConnection dbConn, TResultQuery selectQuery)
    throws Exception;

    /**
     * This is a convenience method to call {@link #selectRowList(JooqDatabaseConnection, ResultQuery, CountMatcher)}
     * <br>where {@code rowCountMatcher} is {@link AtMostCountMatcher#ONE}.
     *
     * @return
     * <ul>
     *     <li>if zero rows are found, return {@code null}</li>
     *     <li>if one row is found, return the row</li>
     *     <li>if more than one row is found, throw an exception</li>
     * </ul>
     *
     * @see #selectRowList(JooqDatabaseConnection, ResultQuery, CountMatcher)
     * @see #selectRow(JooqDatabaseConnection, ResultQuery)
     * @see #trySelectValue(JooqDatabaseConnection, ResultQuery)
     */
    @Nullable
    @Blocking
    <TRecord extends Record, TResultQuery extends ResultQuery<TRecord>>
    TRecord
    trySelectRow(JooqDatabaseConnection dbConn, TResultQuery selectQuery)
    throws Exception;

    /**
     * This is a convenience method to call {@link #selectRowList(JooqDatabaseConnection, ResultQuery, CountMatcher)}
     * <br>with a query that selects only a single column.  The result is converted to an {@link ImmutableList}.
     *
     * @see #selectValue(JooqDatabaseConnection, ResultQuery)
     */
    @EmptyContainerAllowed
    @Blocking
    <TValue, TResultQuery extends ResultQuery<Record1<TValue>>>
    ImmutableList<TValue>
    selectValueList(JooqDatabaseConnection dbConn,
                    TResultQuery selectQuery,
                    CountMatcher rowCountMatcher)
    throws Exception;

    /**
     * This is a convenience method to call {@link #selectRow(JooqDatabaseConnection, ResultQuery)}
     * <br>with a query that selects only a single column.
     *
     * @see #trySelectValue(JooqDatabaseConnection, ResultQuery)
     */
    @Blocking
    <TValue, TResultQuery extends ResultQuery<Record1<TValue>>>
    TValue
    selectValue(JooqDatabaseConnection dbConn, TResultQuery selectQuery)
    throws Exception;

    /**
     * This is a convenience method to call {@link #trySelectRow(JooqDatabaseConnection, ResultQuery)}
     * <br>with a query that selects only a single column.
     *
     * @see #selectValue(JooqDatabaseConnection, ResultQuery)
     */
    @Nullable
    @Blocking
    <TValue, TResultQuery extends ResultQuery<Record1<TValue>>>
    TValue
    trySelectValue(JooqDatabaseConnection dbConn, TResultQuery selectQuery)
    throws Exception;

    /**
     * Inserts rows, then checks row count.
     * <p>
     * See: JooqSqliteDatabaseQueryService.getSqliteLastInsertRowId(JooqDatabaseConnection)
     * in module kevinarpe-papaya-jooq-sqlite
     *
     * @param dbConn
     *        database connection
     *
     * @param insertQuery
     *        query to insert rows
     *
     * @param rowCountMatcher
     *        matcher for row count
     *
     * @throws Exception
     *         if database I/O error or query syntax error
     *         <br>if row count does not match {@code rowCountMatcher}
     *
     * @see #insertOneRow(JooqDatabaseConnection, Insert)
     */
    @Blocking
    void insertRows(JooqDatabaseConnection dbConn,
                    Insert<? extends Record> insertQuery,
                    CountMatcher rowCountMatcher)
    throws Exception;

    /**
     * This is a convenience method to call {@link #insertRows(JooqDatabaseConnection, Insert, CountMatcher)}
     * <br>where {@code rowCountMatcher} is {@link ExactlyCountMatcher#ONE}.
     */
    @Blocking
    void insertOneRow(JooqDatabaseConnection dbConn, Insert<? extends Record> insertQuery)
    throws Exception;

    /**
     * Updates rows, then checks row count.
     *
     * @param dbConn
     *        database connection
     *
     * @param updateQuery
     *        query to update rows
     *
     * @param rowCountMatcher
     *        matcher for row count
     *
     * @throws Exception
     *         if database I/O error or query syntax error
     *         <br>if row count does not match {@code rowCountMatcher}
     *
     * @see #updateOneRow(JooqDatabaseConnection, Update)
     */
    @Blocking
    void updateRows(JooqDatabaseConnection dbConn,
                    Update<? extends Record> updateQuery,
                    CountMatcher rowCountMatcher)
    throws Exception;

    /**
     * This is a convenience method to call {@link #updateRows(JooqDatabaseConnection, Update, CountMatcher)}
     * <br>where {@code rowCountMatcher} is {@link ExactlyCountMatcher#ONE}.
     */
    @Blocking
    void updateOneRow(JooqDatabaseConnection dbConn, Update<? extends Record> updateQuery)
    throws Exception;

    /**
     * Deletes rows, then checks row count.
     *
     * @param dbConn
     *        database connection
     *
     * @param deleteQuery
     *        query to delete rows
     *
     * @param rowCountMatcher
     *        matcher for row count
     *
     * @throws Exception
     *         if database I/O error or query syntax error
     *         <br>if row count does not match {@code rowCountMatcher}
     *
     * @see #deleteOneRow(JooqDatabaseConnection, Delete)
     */
    @Blocking
    void deleteRows(JooqDatabaseConnection dbConn,
                    Delete<? extends Record> deleteQuery,
                    CountMatcher rowCountMatcher)
    throws Exception;

    /**
     * This is a convenience method to call {@link #deleteRows(JooqDatabaseConnection, Delete, CountMatcher)}
     * <br>where {@code rowCountMatcher} is {@link ExactlyCountMatcher#ONE}.
     */
    @Blocking
    void deleteOneRow(JooqDatabaseConnection dbConn, Delete<? extends Record> deleteQuery)
    throws Exception;
}
