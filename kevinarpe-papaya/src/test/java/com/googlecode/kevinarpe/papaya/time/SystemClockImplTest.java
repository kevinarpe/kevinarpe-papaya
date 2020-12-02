package com.googlecode.kevinarpe.papaya.time;

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

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@PrepareForTest(SystemClockImpl.class)
public class SystemClockImplTest
extends PowerMockTestCase {

    @Test
    public void pass() {

        final LocalDate today = LocalDate.of(1999, 12, 31);
        final ZoneOffset zoneOffset = ZoneOffset.ofHours(8);

        final OffsetDateTime now = OffsetDateTime.of(today, LocalTime.of(9, 20), zoneOffset);
        final OffsetDateTime later = OffsetDateTime.of(today, LocalTime.of(9, 21), zoneOffset);

        PowerMockito.mockStatic(OffsetDateTime.class);
        PowerMockito.when(OffsetDateTime.now()).thenReturn(now);
        PowerMockito.when(OffsetDateTime.now(zoneOffset)).thenReturn(later);

        final SystemClockImpl classUnderTest = new SystemClockImpl();

        final OffsetDateTime actualNow = classUnderTest.offsetNow();
        Assert.assertEquals(actualNow, later);
    }
}
