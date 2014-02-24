package com.googlecode.kevinarpe.papaya.testing.log4j;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.base.Predicate;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import java.util.List;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface LoggingEventAnalysis {
    List<LoggingEvent> getLoggingEventList();

    List<LoggingEvent> getLoggingEventListByPredicate(
        Predicate<LoggingEvent> predicate);

    List<LoggingEvent> getLoggingEventListIncluding(Level level, Level... moreLevelArr);

    List<LoggingEvent> getLoggingEventListIncluding(Set<Level> levelSet);
}
