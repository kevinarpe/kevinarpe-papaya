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

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
public interface EmailMessageTextAttachmentBuilder {

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
     *        <br>Ex: {@code "log.txt"}
     *
     * @return self for fluent interface / method chaining
     *
     * @see #inline()
     */
    EmailMessageTextAttachmentBuilder
    attachmentFileName(String attachmentFileName);

    /**
     * RARE: Sets the attachment type to {@link EmailMessageAttachmentType#INLINE} and clears the attachment file name.
     *
     * @return self for fluent interface / method chaining
     *
     * @see #attachmentFileName(String)
     */
    EmailMessageTextAttachmentBuilder
    inline();

    /**
     * Sets the text MIME sub-type to a common value -- text or plain.
     * <p>
     * For custom types, see: {@link #customTextMimeSubType(String)}.
     *
     * @return self for fluent interface / method chaining
     *
     * @see #customTextMimeSubType(String)
     */
    EmailMessageTextAttachmentBuilder
    textMimeSubType(TextMimeSubType textMimeSubType);

    /**
     * RARE: Sets the text MIME sub-type to a custom value.
     * <p>
     * For common types, see {@link #textMimeSubType(TextMimeSubType)}.
     *
     * @return self for fluent interface / method chaining
     *
     * @see #textMimeSubType(TextMimeSubType)
     */
    EmailMessageTextAttachmentBuilder
    customTextMimeSubType(String customTextMimeSubType);

    /**
     * Immediately reads the contents of the file as text, converts text new-lines (if necessary),
     * then adds new attachment to the parent builder.
     * <p>
     * Implementation note: Regardless of {@code fileCharset}, the text is always re-encoded using
     * {@link StandardCharsets#UTF_8} before attachment.  Why?  In 2020, everything should be UTF-8!
     *
     * @param filePath
     *        path to file with text
     *
     * @param fileCharset
     *        charset used to read text from {@code filePath}
     *
     * @param newLine
     *        for internal emails at large corporations, {@link EmailMessageAttachmentTextNewLine#WINDOWS} is probably
     *        most friendly towards Microsoft Windows and Microsoft Outlook users
     *        <br>When {@link #inline()} is used, this option may be irrelevant.  Multiple tests showed the new-lines
     *        for inline text on received emails to <b>always</b> be Microsoft Windows new-lines.
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
     * @see #attachTextInputStream(InputStream, Charset, EmailMessageAttachmentTextNewLine, IsEmptyAllowed)
     * @see #attachText(String, EmailMessageAttachmentTextNewLine, IsEmptyAllowed)
     */
    EmailMessageTextAttachmentBuilder
    attachTextFile(File filePath,
                   Charset fileCharset,
                   EmailMessageAttachmentTextNewLine newLine,
                   IsEmptyAllowed isEmptyAllowed)
    throws Exception;

    /**
     * Immediately reads the contents of the {@code InputStream} as text, converts text new-lines (if necessary),
     * then adds new attachment to the parent builder.
     * <p>
     * Implementation note: Regardless of {@code fileCharset}, the text is always re-encoded using
     * {@link StandardCharsets#UTF_8} before attachment.  Why?  In 2020, everything should be UTF-8!
     *
     * @param inputStream
     *        unlike most Java APIs, the input stream is closed after reading.  This is the source or many hidden bugs!
     *        <br>See: {@link InputStream#close()}
     *
     * @param inputStreamCharset
     *        charset used to read text from {@code inputStream}
     *
     * @param newLine
     *        for internal emails at large corporations, {@link EmailMessageAttachmentTextNewLine#WINDOWS} is probably
     *        most friendly towards Microsoft Windows and Microsoft Outlook users
     *        <br>When {@link #inline()} is used, this option may be irrelevant.  Multiple tests showed the new-lines
     *        for inline text on received emails to <b>always</b> be Microsoft Windows new-lines.
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
     * @see #attachTextFile(File, Charset, EmailMessageAttachmentTextNewLine, IsEmptyAllowed)
     * @see #attachText(String, EmailMessageAttachmentTextNewLine, IsEmptyAllowed)
     */
    EmailMessageTextAttachmentBuilder
    attachTextInputStream(InputStream inputStream,
                          Charset inputStreamCharset,
                          EmailMessageAttachmentTextNewLine newLine,
                          IsEmptyAllowed isEmptyAllowed)
    throws Exception;

    /**
     * Converts text new-lines (if necessary), then adds new attachment to the parent builder.
     * <p>
     * Implementation note: Regardless of {@code fileCharset}, the text is always re-encoded using
     * {@link StandardCharsets#UTF_8} before attachment.  Why?  In 2020, everything should be UTF-8!
     *
     * @param text
     *        text for attachment
     *
     * @param newLine
     *        for internal emails at large corporations, {@link EmailMessageAttachmentTextNewLine#WINDOWS} is probably
     *        most friendly towards Microsoft Windows and Microsoft Outlook users
     *        <br>When {@link #inline()} is used, this option may be irrelevant.  Multiple tests showed the new-lines
     *        for inline text on received emails to <b>always</b> be Microsoft Windows new-lines.
     *
     * @param isEmptyAllowed
     *        normally, use {@link IsEmptyAllowed#NO}
     *
     * @return self for fluent interface / method chaining
     *
     * @throws IllegalArgumentException
     *         if {@code isEmptyAllowed} is {@link IsEmptyAllowed#NO} and {@code text} is empty
     *
     * @see #attachTextFile(File, Charset, EmailMessageAttachmentTextNewLine, IsEmptyAllowed)
     * @see #attachTextInputStream(InputStream, Charset, EmailMessageAttachmentTextNewLine, IsEmptyAllowed)
     */
    EmailMessageTextAttachmentBuilder
    attachText(@EmptyStringAllowed String text,
               EmailMessageAttachmentTextNewLine newLine,
               IsEmptyAllowed isEmptyAllowed);
}
