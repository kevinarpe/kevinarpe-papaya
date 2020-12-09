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

import com.googlecode.kevinarpe.papaya.annotation.NonBlocking;
import com.googlecode.kevinarpe.papaya.jooq.JooqDatabaseConnection;
import com.googlecode.kevinarpe.papaya.jooq.JooqDatabaseQueryService;
import org.jooq.Insert;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JooqDatabaseQueryService
 */
public interface JooqSqliteDatabaseQueryService {

    /**
     * SQLite has a hidden column in each table called {@code "rowid"}.  After a row insert, the row ID can be queried.
     * <p>
     * NonBlocking?  Yes, this function never blocks, as long as {@code dbConn} is not locked by another thread.
     *
     * @param dbConn
     *        database connection
     *
     * @return row ID of last insert
     *
     * @throws Exception
     *         if database I/O error
     *         <br>if last insert row ID is zero (insert must be done first!)
     *
     * @see JooqDatabaseQueryService#insertOneRow(JooqDatabaseConnection, Insert)
     */
    @NonBlocking
    long getSqliteLastInsertRowId(JooqDatabaseConnection dbConn)
    throws Exception;
}
