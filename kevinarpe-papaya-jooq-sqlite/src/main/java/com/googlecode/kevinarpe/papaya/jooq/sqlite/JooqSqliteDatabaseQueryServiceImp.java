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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NonBlocking;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;
import com.googlecode.kevinarpe.papaya.jooq.JooqDatabaseConnection;
import com.googlecode.kevinarpe.papaya.jooq.JooqDatabaseQueryService;
import org.jooq.Record1;
import org.jooq.ResultQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Global singleton
@FullyTested
public final class JooqSqliteDatabaseQueryServiceImp
implements JooqSqliteDatabaseQueryService {

    private final JooqDatabaseQueryService queryService;
    private final ExceptionThrower exceptionThrower;

    public JooqSqliteDatabaseQueryServiceImp(JooqDatabaseQueryService queryService,
                                             ExceptionThrower exceptionThrower) {

        this.queryService = ObjectArgs.checkNotNull(queryService, "queryService");
        this.exceptionThrower = ObjectArgs.checkNotNull(exceptionThrower, "exceptionThrower");
    }

    /** {@inheritDoc} */
    @NonBlocking
    @Override
    public long
    getSqliteLastInsertRowId(JooqDatabaseConnection dbConn)
    throws Exception {

        // Ref: https://sqlite.org/c3ref/last_insert_rowid.html
        final ResultQuery<Record1<Long>> selectQuery =
            dbConn.jooqDSLContext.select(DSL.field("last_insert_rowid()", SQLDataType.BIGINT.nullable(false)));

        @NonBlocking
        final long sqliteRowId = queryService.selectValue(dbConn, selectQuery);
        // From SQLite docs: "If no successful INSERTs into rowid tables have ever occurred on the database connection D,
        // then sqlite3_last_insert_rowid(D) returns zero."
        if (0 == sqliteRowId) {

            throw exceptionThrower.throwCheckedException(Exception.class,
                "Internal error: Must insert row before calling this method! [last_insert_rowid(): %d]", sqliteRowId);
        }
        return sqliteRowId;
    }
}
