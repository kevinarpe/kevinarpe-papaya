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

import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.function.ThrowingFunction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class EmailMessageAddressTypeTest {

    private MimeMessage mimeMessage;
    private InternetAddress inetAddr;

    @BeforeMethod
    public void beforeEachTestMethod()
    throws Exception {

        final Properties props = new Properties();
        final Session session = Session.getDefaultInstance(props);
        this.mimeMessage = new MimeMessage(session);

        this.inetAddr =
            new InternetAddress(
                "kevinarpe@gmail.com", "Kevin Connor ARPE", StandardCharsets.UTF_8.name());
    }

    @Test
    public void FROM_setValue_Pass()
    throws Exception {

        _pass(EmailMessageAddressType.FROM,
            (MimeMessage mm) -> {
                @Nullable
                @EmptyContainerAllowed
                final Address[] addrArr = mm.getFrom();
                if (null == addrArr) {
                    return null;
                }
                else {
                    final Address x = addrArr[0];
                    return x;
                }
            });
    }

    @Test
    public void SENDER_setValue_Pass()
    throws Exception {

        _pass(EmailMessageAddressType.SENDER, MimeMessage::getSender);
    }

    private void
    _pass(EmailMessageAddressType addressType,
          ThrowingFunction<MimeMessage, Address> getInetAddr)
    throws Exception {

        Assert.assertNull(getInetAddr.apply(mimeMessage));
        addressType.setValue(mimeMessage, inetAddr);
        Assert.assertEquals(getInetAddr.apply(mimeMessage), inetAddr);
    }
}
