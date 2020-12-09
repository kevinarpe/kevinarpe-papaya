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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.jooq.DSLContext;

import javax.annotation.concurrent.NotThreadSafe;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * NotThreadSafe?  This is only a warning that programmers should be very careful with database connection handles!
 * Always assume they are not thread-safe.  As a workaround, synchronize on this object before performing any query
 * actions on the database connection.
 */
@NotThreadSafe
@FullyTested
public final class JooqDatabaseConnection
implements AutoCloseable {

    /**
     * Raw JDBC data connection wrapped by {@link #jooqDSLContext}
     * <p>
     * NotThreadSafe: Always synchronize before using this reference.
     */
    public final Connection dbConn;

    /**
     * JOOQ wrapper for raw JDBC data connection {@link #dbConn}
     * <p>
     * NotThreadSafe: Always synchronize before using this reference.
     */
    public final DSLContext jooqDSLContext;

    public JooqDatabaseConnection(Connection dbConn, DSLContext jooqDSLContext) {

        this.dbConn = ObjectArgs.checkNotNull(dbConn, "dbConn");
        this.jooqDSLContext = ObjectArgs.checkNotNull(jooqDSLContext, "jooqDSLContext");
    }

    @Override
    public void close()
    throws Exception {
        try {
            synchronized (dbConn) {
                dbConn.close();
            }
        }
        catch (SQLException e) {
            // @DebugBreakpoint
            throw e;
        }
    }
}
