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
import java.io.File;
import java.io.InputStream;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
public interface EmailMessageBinaryAttachmentBuilder {

    /**
     * Access the parent email message builder
     */
    EmailMessageBuilder
    parent();

    /**
     * This is the most common attachment type.
     * <p>
     * Sets the attachment type to {@link EmailMessageAttachmentType#ATTACHMENT} and sets the attachment file name.
     *
     * @param attachmentFileName
     *        only a file name -- does not include a parent path
     *        <br>Ex: {@code "binary_data.bin"}
     *
     * @return self for fluent interface / method chaining
     *
     * @see #inline()
     */
    EmailMessageBinaryAttachmentBuilder
    attachmentFileName(String attachmentFileName);

    /**
     * RARE: Sets the attachment type to {@link EmailMessageAttachmentType#INLINE} and clears the attachment file name.
     *
     * @return self for fluent interface / method chaining
     *
     * @see #attachmentFileName(String)
     */
    EmailMessageBinaryAttachmentBuilder
    inline();

    /**
     * Sets the MIME type.  A good default for binary attachments is {@code "application/octet-stream"}.
     * <p>
     * See: https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
     *
     * @return self for fluent interface / method chaining
     */
    EmailMessageBinaryAttachmentBuilder
    mimeType(String mimeType);

    /**
     * Converts a MIME message to binary data, then adds new attachment to the parent builder.
     *
     * @param message
     *        MIME message to attach
     *
     * @return self for fluent interface / method chaining
     *
     * @throws Exception
     *         on I/O exception
     *
     * @see JavaMailMessageService#getMimeContentAsByteArr(Message)
     * @see #attachBinaryFile(File, IsEmptyAllowed)
     * @see #attachBinaryInputStream(InputStream, IsEmptyAllowed)
     * @see #attachBinaryData(byte[], IsEmptyAllowed)
     */
    EmailMessageBinaryAttachmentBuilder
    attachMessage(Message message)
    throws Exception;

    /**
     * Immediately reads the contents of the file as binary data, then adds new attachment to the parent builder.
     *
     * @param filePath
     *        path to file with text
     *
     * @param isEmptyAllowed
     *        normally, use {@link IsEmptyAllowed#NO}
     *
     * @return self for fluent interface / method chaining
     *
     * @throws Exception
     *         on I/O exception
     *         <br>if {@code isEmptyAllowed} is {@link IsEmptyAllowed#NO} and the file is empty
     *
     * @see #attachMessage(Message)
     * @see #attachBinaryInputStream(InputStream, IsEmptyAllowed)
     * @see #attachBinaryData(byte[], IsEmptyAllowed)
     */
    EmailMessageBinaryAttachmentBuilder
    attachBinaryFile(File filePath, IsEmptyAllowed isEmptyAllowed)
    throws Exception;

    /**
     * Immediately reads the contents of the {@code InputStream} as binary data,
     * then adds new attachment to the parent builder.
     *
     * @param inputStream
     *        unlike most Java APIs, the input stream is closed after reading.  This is the source or many hidden bugs!
     *        <br>See: {@link InputStream#close()}
     *
     * @param isEmptyAllowed
     *        normally, use {@link IsEmptyAllowed#NO}
     *
     * @return self for fluent interface / method chaining
     *
     * @throws Exception
     *         on I/O exception
     *         <br>if {@code isEmptyAllowed} is {@link IsEmptyAllowed#NO} and {@code inputStream} is empty
     *
     * @see #attachMessage(Message)
     * @see #attachBinaryFile(File, IsEmptyAllowed)
     * @see #attachBinaryData(byte[], IsEmptyAllowed)
     */
    EmailMessageBinaryAttachmentBuilder
    attachBinaryInputStream(InputStream inputStream, IsEmptyAllowed isEmptyAllowed)
    throws Exception;

    /**
     * Adds new attachment to the parent builder.
     *
     * @param byteArr
     *        byte array for attachment
     *
     * @param isEmptyAllowed
     *        normally, use {@link IsEmptyAllowed#NO}
     *
     * @return self for fluent interface / method chaining
     *
     * @throws IllegalArgumentException
     *         if {@code isEmptyAllowed} is {@link IsEmptyAllowed#NO} and {@code byteArr} is empty
     *
     * @see #attachMessage(Message)
     * @see #attachBinaryFile(File, IsEmptyAllowed)
     * @see #attachBinaryInputStream(InputStream, IsEmptyAllowed)
     */
    EmailMessageBinaryAttachmentBuilder
    attachBinaryData(byte[] byteArr, IsEmptyAllowed isEmptyAllowed);
}
