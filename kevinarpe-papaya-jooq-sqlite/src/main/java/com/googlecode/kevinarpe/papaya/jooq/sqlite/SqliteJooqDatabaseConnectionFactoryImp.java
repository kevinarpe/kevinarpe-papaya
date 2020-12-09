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
import com.googlecode.kevinarpe.papaya.jooq.JooqDatabaseConnection;
import com.googlecode.kevinarpe.papaya.jooq.JooqDatabaseConnectionFactory;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Global singleton
@FullyTested
public final class SqliteJooqDatabaseConnectionFactoryImp
implements JooqDatabaseConnectionFactory {

    public SqliteJooqDatabaseConnectionFactoryImp() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public JooqDatabaseConnection
    newInstance(Connection dbConn) {

        final DSLContext dslContext = _createDSLContext(dbConn);
        final JooqDatabaseConnection x = new JooqDatabaseConnection(dbConn, dslContext);
        return x;
    }

    private DSLContext
    _createDSLContext(Connection dbConn) {

        final Settings settings = new Settings();

        final Configuration config =
            new DefaultConfiguration()
                .set(dbConn)
                .set(SQLDialect.SQLITE)
                .set(settings);

        final DSLContext x = DSL.using(config);
        return x;
    }
}
