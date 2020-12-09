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

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface SqliteConnectionFactory {

    /**
     * Creates a new raw JDBC database connection to a file.  The file may or may not exist.  A JVM shutdown hook is
     * automatically registered for the new database connection to close on shutdown.
     * <p>
     * See: DatabaseConnectionShutdownHookService in module kevinarpe-papaya-jooq
     *
     * @param filePath
     *        absolute or relative file path for SQLite database
     *        <br>If file path exists, it will open the existing SQLite database
     *        <br>If file path does not exist, it will open a new SQLite database
     *
     * @return new raw JDBC database connection associated with a file
     *
     * @throws SQLException
     *         on error
     *
     * @see #newInstanceInMemory()
     * @see SqliteJooqDatabaseConnectionFactoryImp
     */
    Connection
    newInstanceFromFile(File filePath)
    throws SQLException;

    /**
     * Creates a new raw JDBC database connection to a new SQLite in-memory database.  A JVM shutdown hook is
     * automatically registered for the new database connection to close on shutdown.
     * <p>
     * See: DatabaseConnectionShutdownHookService in module kevinarpe-papaya-jooq
     *
     * @return new raw JDBC database connection for new SQLite in-memory database
     *
     * @throws SQLException
     *         on error
     *
     * @see #newInstanceFromFile(File)
     * @see SqliteJooqDatabaseConnectionFactoryImp
     */
    Connection
    newInstanceInMemory()
    throws SQLException;
}
