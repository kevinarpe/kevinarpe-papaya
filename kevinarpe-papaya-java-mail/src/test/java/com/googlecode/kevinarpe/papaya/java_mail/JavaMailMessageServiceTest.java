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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class JavaMailMessageServiceTest {

    private MimeMessage mimeMessage;

    @BeforeMethod
    public void beforeEachTestMethod()
    throws Exception {

        final Properties props = new Properties();
        final Session session = Session.getDefaultInstance(props);
        this.mimeMessage = new MimeMessage(session);
    }

    @Test
    public void getMimeContentAsByteArr_Pass()
    throws Exception {

        mimeMessage.setSubject("sample subject");
        mimeMessage.setText("body text");
        final byte[] byteArr = JavaMailMessageService.INSTANCE.getMimeContentAsByteArr(mimeMessage);
        Assert.assertTrue(byteArr.length > 0);
    }

    @Test(expectedExceptions = IOException.class, expectedExceptionsMessageRegExp = "^No MimeMessage content$")
    public void getMimeContentAsByteArr_FailWhenEmptyMimeMessage()
    throws Exception {

        JavaMailMessageService.INSTANCE.getMimeContentAsByteArr(mimeMessage);
    }
}
