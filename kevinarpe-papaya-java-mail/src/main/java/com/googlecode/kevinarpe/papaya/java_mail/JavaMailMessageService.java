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

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
public interface JavaMailMessageService {

    public static final JavaMailMessageService INSTANCE = new JavaMailMessageServiceImp();

    /**
     * Converts a Java mail message to an array of bytes suitable to write to file or attach to an email.
     *
     * @param message
     *        email message to extract raw MIME content
     *        <br>at least subject and body text must be set
     *
     * @throws Exception
     *         on error
     *
     * @see #createMimeMessageFromByteArr(Session, byte[])
     */
    byte[]
    getMimeContentAsByteArr(Message message)
    throws Exception;

    /**
     * @param session
     *        required to construct an instance of {@link MimeMessage}
     *
     * @param byteArr
     *        MIME content
     *
     * @return deserialised email message
     *
     * @throws Exception
     *         on error
     *
     * @see #getMimeContentAsByteArr(Message)
     */
    MimeMessage
    createMimeMessageFromByteArr(Session session, byte[] byteArr)
    throws Exception;
}
