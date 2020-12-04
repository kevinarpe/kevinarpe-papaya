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

import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import javax.mail.internet.MimeBodyPart;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
public enum EmailMessageAttachmentType {

    /**
     * An email attachment with a file name.  This is, by far, the most common type of email attachment.
     *
     * @see MimeBodyPart#ATTACHMENT
     * @see #INLINE
     */
    ATTACHMENT(
        MimeBodyPart.ATTACHMENT),

    /**
     * Tip: Only use this type if you really know what you are doing.  Inline attachments may surprise users!
     *
     * @see MimeBodyPart#INLINE
     * @see #ATTACHMENT
     */
    INLINE(
        MimeBodyPart.INLINE),
    ;
    public final String disposition;

    private EmailMessageAttachmentType(String disposition) {

        this.disposition = StringArgs.checkNotEmptyOrWhitespace(disposition, "disposition");
    }
}
