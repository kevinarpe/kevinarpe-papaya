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

import com.googlecode.kevinarpe.papaya.function.ThrowingFunction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class EmailMessageAddressListTypeTest {

    private MimeMessage mimeMessage;
    private InternetAddress[] inetAddrArr;

    @BeforeMethod
    public void beforeEachTestMethod()
    throws Exception {

        final Properties props = new Properties();
        final Session session = Session.getDefaultInstance(props);
        this.mimeMessage = new MimeMessage(session);

        this.inetAddrArr =
            new InternetAddress[] {
                new InternetAddress(
                    "kevinarpe@gmail.com", "Kevin Connor ARPE", StandardCharsets.UTF_8.name())
            };
    }

    @Test
    public void REPLY_TO_setValue_Pass()
    throws Exception {

        _pass(EmailMessageAddressListType.REPLY_TO, MimeMessage::getReplyTo);
    }

    @Test
    public void TO_setValue_Pass()
    throws Exception {

        _pass(EmailMessageAddressListType.TO,
            (MimeMessage mm) -> mm.getRecipients(Message.RecipientType.TO));
    }

    @Test
    public void CC_setValue_Pass()
    throws Exception {

        _pass(EmailMessageAddressListType.CC,
            (MimeMessage mm) -> mm.getRecipients(Message.RecipientType.CC));
    }

    @Test
    public void BCC_setValue_Pass()
    throws Exception {

        _pass(EmailMessageAddressListType.BCC,
            (MimeMessage mm) -> mm.getRecipients(Message.RecipientType.BCC));
    }

    private void
    _pass(EmailMessageAddressListType addressListType,
          ThrowingFunction<MimeMessage, Address[]> getInetAddrArr)
    throws Exception {

        Assert.assertNull(getInetAddrArr.apply(mimeMessage));
        addressListType.setValue(mimeMessage, inetAddrArr);
        Assert.assertEquals(getInetAddrArr.apply(mimeMessage), inetAddrArr);
    }
}
