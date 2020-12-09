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
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.logging.slf4j.IncludeStackTrace;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerLevel;
import com.googlecode.kevinarpe.papaya.logging.slf4j.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Global singleton
@FullyTested
public final class DatabaseConnectionShutdownHookServiceImp
implements DatabaseConnectionShutdownHookService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionShutdownHookServiceImp.class);

    private final Runtime runtime;
    private final LoggerService loggerService;

    public DatabaseConnectionShutdownHookServiceImp(LoggerService loggerService) {

        this(Runtime.getRuntime(), loggerService);
    }

    // package-private for testing
    DatabaseConnectionShutdownHookServiceImp(Runtime runtime, LoggerService loggerService) {

        this.runtime = ObjectArgs.checkNotNull(runtime, "runtime");
        this.loggerService = ObjectArgs.checkNotNull(loggerService, "loggerService");
    }

    /** {@inheritDoc} */
    @Override
    public void
    addShutdownHook(String description, Connection dbConn) {

        StringArgs.checkNotEmptyOrWhitespace(description, "description");
        ObjectArgs.checkNotNull(dbConn, "dbConn");

        final Thread thread = createThread(description, dbConn);
        runtime.addShutdownHook(thread);
    }

    // package-private for testing
    Thread
    createThread(String description, Connection dbConn) {

        final Thread thread = new Thread(() -> _close(description, dbConn));
        thread.setName("shutdown-hook::db-conn");
        // Daemon flag does not need to be set for shutdown hooks.
        return thread;
    }

    private void
    _close(String description, Connection dbConn) {

        loggerService.formatThenLog(logger,  LoggerLevel.INFO,
            "Database [%s]: Begin shutdown hook thread for database connection...",
            description);

        _close0(description, dbConn);

        loggerService.formatThenLog(logger,  LoggerLevel.INFO,
            "Database [%s]: Database connection closed", description);
    }

    private void
    _close0(String description, Connection dbConn) {

        try {
            dbConn.close();
        }
        catch (Exception e) {

            loggerService.formatThenLogThrowable(logger,
                LoggerLevel.ERROR, IncludeStackTrace.UNIQUE_ONLY,
                e,
                "Database [%s]: %s: Failed to close database connection",
                description, LoggerService.DEFAULT_THROWABLE_MESSAGE);

            throw new IllegalStateException(e);
        }
    }
}
