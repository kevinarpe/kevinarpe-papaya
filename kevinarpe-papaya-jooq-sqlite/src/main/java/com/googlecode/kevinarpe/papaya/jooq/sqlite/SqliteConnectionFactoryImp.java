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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.jooq.DatabaseConnectionShutdownHookService;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerLevel;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Global singleton
@FullyTested
public final class SqliteConnectionFactoryImp
implements SqliteConnectionFactory {

    private static final Logger logger = LoggerFactory.getLogger(SqliteConnectionFactoryImp.class);
    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";
    private static final DecimalFormat DECIMAL_FORMAT_COMMAS = new DecimalFormat("#,###");

    private final DatabaseConnectionShutdownHookService shutdownHookService;
    private final LoggerService loggerService;

    public SqliteConnectionFactoryImp(DatabaseConnectionShutdownHookService shutdownHookService,
                                      LoggerService loggerService) {

        this.shutdownHookService = ObjectArgs.checkNotNull(shutdownHookService, "shutdownHookService");
        this.loggerService = ObjectArgs.checkNotNull(loggerService, "loggerService");
    }

    /** {@inheritDoc} */
    @Override
    public Connection
    newInstanceFromFile(File filePath)
    throws SQLException {

        final String absPathname = filePath.getAbsolutePath();
        if (filePath.exists() && filePath.isFile()) {

            final long byteCount = filePath.length();
            loggerService.formatThenLog(logger, LoggerLevel.INFO,
                "Opening existing SQLite database file: [%s] (%s bytes)",
                absPathname, DECIMAL_FORMAT_COMMAS.format(byteCount));
        }
        else {
            loggerService.formatThenLog(logger, LoggerLevel.INFO,
                "Opening new SQLite database file: [%s]", absPathname);
        }
        final String jdbcUrl = JDBC_URL_PREFIX + absPathname.replace('\\', '/');
        final Connection x = _createConnection(jdbcUrl);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public Connection
    newInstanceInMemory()
    throws SQLException {

        final String jdbcUrl = JDBC_URL_PREFIX + ":memory:";
        final Connection x = _createConnection(jdbcUrl);
        return x;
    }

    private Connection
    _createConnection(String url)
    throws SQLException {

        loggerService.formatThenLog(logger, LoggerLevel.INFO,
            "Create JDBC database connection with URL: [%s]", url);

        final Connection dbConn = DriverManager.getConnection(url);
        shutdownHookService.addShutdownHook(url, dbConn);
        return dbConn;
    }
}
