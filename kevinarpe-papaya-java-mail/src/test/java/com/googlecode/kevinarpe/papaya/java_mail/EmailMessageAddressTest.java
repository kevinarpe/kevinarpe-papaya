package com.googlecode.kevinarpe.papaya.java_mail;

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

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.mail.internet.InternetAddress;

import static org.testng.Assert.*;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class EmailMessageAddressTest {

    @Test
    public void pass_fromEmailAddressOnly()
    throws Exception {

        final String emailAddress = "kevinarpe@gmail.com";
        final EmailMessageAddress ema = EmailMessageAddress.fromEmailAddressOnly(emailAddress);
        Assert.assertEquals(ema.emailAddress, emailAddress);
        Assert.assertNull(ema.nullableDisplayName);

        /// createInternetAddress

        final InternetAddress ia = ema.createInternetAddress();
        Assert.assertEquals(ia.getAddress(), emailAddress);
        Assert.assertNull(ia.getPersonal());

        /// hashCode

        Assert.assertNotEquals(ema.hashCode(), 0);

        /// equals

        final EmailMessageAddress ema2 = EmailMessageAddress.fromEmailAddressOnly(emailAddress);
        Assert.assertTrue(ema.equals(ema));
        Assert.assertTrue(ema.equals(ema2));
        Assert.assertFalse(ema.equals(null));
        Assert.assertFalse(ema.equals("abc"));

        /// toString

        Assert.assertEquals(ema.toString(), emailAddress);
    }

    @Test
    public void pass_fromEmailAddressAndDisplayName()
    throws Exception {

        final String emailAddress = "kevinarpe@gmail.com";
        final String displayName = "Kevin Connor ARPE";
        final EmailMessageAddress ema = EmailMessageAddress.fromEmailAddressAndDisplayName(emailAddress, displayName);
        Assert.assertEquals(ema.emailAddress, emailAddress);
        Assert.assertEquals(ema.nullableDisplayName, displayName);

        /// createInternetAddress

        final InternetAddress ia = ema.createInternetAddress();
        Assert.assertEquals(ia.getAddress(), emailAddress);
        Assert.assertEquals(ia.getPersonal(), displayName);

        /// hashCode

        Assert.assertNotEquals(ema.hashCode(), 0);

        /// equals

        final EmailMessageAddress ema2 = EmailMessageAddress.fromEmailAddressAndDisplayName(emailAddress, displayName);
        Assert.assertTrue(ema.equals(ema));
        Assert.assertTrue(ema.equals(ema2));
        Assert.assertFalse(ema.equals(null));
        Assert.assertFalse(ema.equals("abc"));

        /// toString

        Assert.assertEquals(ema.toString(), String.format("%s <%s>", displayName, emailAddress));
    }
}
