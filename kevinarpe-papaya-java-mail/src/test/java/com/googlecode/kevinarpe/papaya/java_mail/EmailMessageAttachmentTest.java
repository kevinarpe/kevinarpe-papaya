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

import com.googlecode.kevinarpe.papaya.annotation.EmptyStringAllowed;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class EmailMessageAttachmentTest {

    @Test
    public void ctor_Pass() {

        new EmailMessageAttachment(
            EmailMessageAttachmentType.ATTACHMENT,
            "file_name.txt",
            "text/plain; charset=UTF-8",
            "blah".getBytes(StandardCharsets.UTF_8),
            IsEmptyAllowed.NO);

        new EmailMessageAttachment(
            EmailMessageAttachmentType.ATTACHMENT,
            "file_name.txt",
            "text/plain; charset=UTF-8",
            new byte[0],
            IsEmptyAllowed.YES);

        new EmailMessageAttachment(
            EmailMessageAttachmentType.INLINE,
            null,
            "text/plain; charset=UTF-8",
            "blah".getBytes(StandardCharsets.UTF_8),
            IsEmptyAllowed.NO);

        new EmailMessageAttachment(
            EmailMessageAttachmentType.INLINE,
            null,
            "text/plain; charset=UTF-8",
            new byte[0],
            IsEmptyAllowed.YES);
    }

    @DataProvider
    private static Object[][]
    _ctor_Fail_Data() {
        return new Object[][] {
            {
                EmailMessageAttachmentType.ATTACHMENT,
                null,  // not allowed
                "blah",
                IsEmptyAllowed.NO,
                NullPointerException.class
            },
            {
                EmailMessageAttachmentType.ATTACHMENT,
                "",  // not allowed
                "blah",
                IsEmptyAllowed.NO,
                IllegalArgumentException.class,
            },
            {
                EmailMessageAttachmentType.ATTACHMENT,
                "  \t  ",  // not allowed
                "blah",
                IsEmptyAllowed.NO,
                IllegalArgumentException.class,
            },
            {
                EmailMessageAttachmentType.ATTACHMENT,
                "file_name.txt",
                "",  // not allowed
                IsEmptyAllowed.NO,
                IllegalArgumentException.class,
            },
            {
                EmailMessageAttachmentType.INLINE,
                "file_name.txt",  // not allowed
                "blah",
                IsEmptyAllowed.NO,
                IllegalArgumentException.class
            },
            {
                EmailMessageAttachmentType.INLINE,
                "",  // not allowed
                "blah",
                IsEmptyAllowed.NO,
                IllegalArgumentException.class
            },
            {
                EmailMessageAttachmentType.INLINE,
                null,
                "",  // not allowed
                IsEmptyAllowed.NO,
                IllegalArgumentException.class
            },
        };
    }

    @Test(dataProvider = "_ctor_Fail_Data")
    public void ctor_Fail(EmailMessageAttachmentType attachmentType,
                          @Nullable
                          String nullableFileName,
                          @EmptyStringAllowed
                          String text,
                          IsEmptyAllowed isEmptyAllowed,
                          Class<?> exceptionClass) {

        boolean hasException = false;
        try {
            new EmailMessageAttachment(
                attachmentType,
                nullableFileName,
                "text/plain; charset=UTF-8",
                text.getBytes(StandardCharsets.UTF_8),
                isEmptyAllowed);
        }
        catch (Exception e) {
            if (exceptionClass.isInstance(e)) {
                hasException = true;
            }
            else {
                throw e;
            }
        }
        Assert.assertTrue(hasException);
    }
}
