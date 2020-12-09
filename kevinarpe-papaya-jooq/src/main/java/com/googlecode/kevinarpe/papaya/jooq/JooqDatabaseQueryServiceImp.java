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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;
import com.googlecode.kevinarpe.papaya.function.count.AtMostCountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.CountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.ExactlyCountMatcher;
import org.jooq.Delete;
import org.jooq.Insert;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Update;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
@ThreadSafe
public final class JooqDatabaseQueryServiceImp
implements JooqDatabaseQueryService {

    private final ExceptionThrower exceptionThrower;

    public JooqDatabaseQueryServiceImp(ExceptionThrower exceptionThrower) {

        this.exceptionThrower = ObjectArgs.checkNotNull(exceptionThrower, "exceptionThrower");
    }

    /** {@inheritDoc} */
    @EmptyContainerAllowed
    @Blocking
    @Override
    public <TRecord extends Record, TResultQuery extends ResultQuery<TRecord>>
    Result<TRecord>
    selectRowList(JooqDatabaseConnection dbConn,
                  TResultQuery selectQuery,
                  CountMatcher rowCountMatcher)
    throws Exception {

        @Blocking
        final Result<TRecord> result = _selectRowList(dbConn, selectQuery);
        final int rowCount = result.size();
        _checkRowCount(_QueryType.SELECT, selectQuery, rowCountMatcher, rowCount);
        return result;
    }

    @EmptyContainerAllowed
    @Blocking
    private <TRecord extends Record, TResultQuery extends ResultQuery<TRecord>>
    Result<TRecord>
    _selectRowList(JooqDatabaseConnection dbConn, TResultQuery selectQuery)
    throws Exception {

        try {
            synchronized (dbConn) {
                @Blocking
                final Result<TRecord> x = selectQuery.fetch();
                return x;
            }
        }
        catch (Exception e) {

            throw exceptionThrower.throwChainedCheckedException(Exception.class,
                e,
                "Failed to execute SELECT query:"
                    + "%n%s",
                selectQuery);
        }
    }

    private void
    _checkRowCount(_QueryType queryType,
                   Query query,
                   CountMatcher rowCountMatcher,
                   final int rowCount)
    throws Exception {

        if (false == rowCountMatcher.isMatch(rowCount)) {

            throw exceptionThrower.throwCheckedException(Exception.class,
                "Expected %s row(s), but found %d row(s):"
                    + "%n%s Query:"
                    + "%n%s%n",
                rowCountMatcher, rowCount, queryType.name(), query);
        }
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public <TRecord extends Record, TResultQuery extends ResultQuery<TRecord>>
    TRecord
    selectRow(JooqDatabaseConnection dbConn, TResultQuery selectQuery)
    throws Exception {

        @Blocking
        final Result<TRecord> result = selectRowList(dbConn, selectQuery, ExactlyCountMatcher.ONE);
        final TRecord x = result.get(0);
        return x;
    }

    /** {@inheritDoc} */
    @Nullable
    @Blocking
    @Override
    public <TRecord extends Record, TResultQuery extends ResultQuery<TRecord>>
    TRecord
    trySelectRow(JooqDatabaseConnection dbConn, TResultQuery selectQuery)
    throws Exception {

        @EmptyContainerAllowed
        @Blocking
        final Result<TRecord> result = selectRowList(dbConn, selectQuery, AtMostCountMatcher.ONE);
        final int rowCount = result.size();
        switch (rowCount) {
            case 0:
                return null;
            case 1: {
                final TRecord x = result.get(0);
                return x;
            }
            default:
                throw exceptionThrower.throwCheckedException(Exception.class, "Unreachable code: %d", rowCount);
        }
    }

    /** {@inheritDoc} */
    @EmptyContainerAllowed
    @Blocking
    @Override
    public <TValue, TResultQuery extends ResultQuery<Record1<TValue>>>
    ImmutableList<TValue>
    selectValueList(JooqDatabaseConnection dbConn,
                    TResultQuery selectQuery,
                    CountMatcher rowCountMatcher)
    throws Exception {

        @Blocking
        final Result<Record1<TValue>> result = selectRowList(dbConn, selectQuery, rowCountMatcher);
        final int size = result.size();
        final ImmutableList.Builder<TValue> b = ImmutableList.builderWithExpectedSize(size);
        for (int i = 0; i < size; ++i) {

            final Record1<TValue> rec = result.get(i);
            final TValue value = rec.value1();
            b.add(value);
        }
        final ImmutableList<TValue> x = b.build();
        return x;
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public <TValue, TResultQuery extends ResultQuery<Record1<TValue>>>
    TValue
    selectValue(JooqDatabaseConnection dbConn, TResultQuery selectQuery)
    throws Exception {

        @Blocking
        final Record1<TValue> rec = selectRow(dbConn, selectQuery);
        final TValue x = rec.value1();
        return x;
    }

    /** {@inheritDoc} */
    @Nullable
    @Blocking
    @Override
    public <TValue, TResultQuery extends ResultQuery<Record1<TValue>>>
    TValue
    trySelectValue(JooqDatabaseConnection dbConn, TResultQuery selectQuery)
    throws Exception {

        @Nullable
        final Record1<TValue> rec = trySelectRow(dbConn, selectQuery);
        if (null == rec) {
            return null;
        }
        else {
            final TValue x = rec.value1();
            return x;
        }
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public void
    insertRows(JooqDatabaseConnection dbConn,
               Insert<? extends Record> insertQuery,
               CountMatcher rowCountMatcher)
    throws Exception {

        final int rowCount = _execute(dbConn, _QueryType.INSERT, insertQuery);
        _checkRowCount(_QueryType.INSERT, insertQuery, rowCountMatcher, rowCount);
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public void
    insertOneRow(JooqDatabaseConnection dbConn, Insert<? extends Record> insertQuery)
    throws Exception {

        final int rowCount = _execute(dbConn, _QueryType.INSERT, insertQuery);
        _checkRowCount(_QueryType.INSERT, insertQuery, ExactlyCountMatcher.ONE, rowCount);
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public void
    updateRows(JooqDatabaseConnection dbConn,
               Update<? extends Record> updateQuery,
               CountMatcher rowCountMatcher)
    throws Exception {

        final int rowCount = _execute(dbConn, _QueryType.UPDATE, updateQuery);
        _checkRowCount(_QueryType.UPDATE, updateQuery, rowCountMatcher, rowCount);
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public void
    updateOneRow(JooqDatabaseConnection dbConn, Update<? extends Record> updateQuery)
    throws Exception {

        updateRows(dbConn, updateQuery, ExactlyCountMatcher.ONE);
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public void
    deleteRows(JooqDatabaseConnection dbConn,
               Delete<? extends Record> deleteQuery,
               CountMatcher rowCountMatcher)
    throws Exception {

        final int rowCount = _execute(dbConn, _QueryType.DELETE, deleteQuery);
        _checkRowCount(_QueryType.DELETE, deleteQuery, rowCountMatcher, rowCount);
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public void
    deleteOneRow(JooqDatabaseConnection dbConn, Delete<? extends Record> deleteQuery)
    throws Exception {

        deleteRows(dbConn, deleteQuery, ExactlyCountMatcher.ONE);
    }

    private enum _QueryType { SELECT, INSERT, UPDATE, DELETE }

    private int
    _execute(JooqDatabaseConnection dbConn, _QueryType queryType, Query query)
    throws Exception {

        try {
            synchronized (dbConn) {
                final int x = query.execute();
                return x;
            }
        }
        catch (Exception e) {

            throw exceptionThrower.throwChainedCheckedException(Exception.class,
                e,
                "Failed to execute %s query:"
                    + "%n%s",
                queryType.name(), query);
        }
    }
}
