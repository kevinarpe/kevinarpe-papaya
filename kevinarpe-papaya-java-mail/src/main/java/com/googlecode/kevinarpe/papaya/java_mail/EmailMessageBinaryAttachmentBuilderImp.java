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

import com.google.common.io.ByteStreams;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import javax.annotation.Nullable;
import javax.mail.Message;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * See: EmailMessageBuilderImpTest
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
@FullyTested
public final class EmailMessageBinaryAttachmentBuilderImp
implements EmailMessageBinaryAttachmentBuilder {

    private final EmailMessageBuilder parentBuilder;
    private final JavaMailMessageService javaMailMessageService;

    @Nullable
    private EmailMessageAttachmentType nullableAttachmentType;
    @Nullable
    private String nullableFileName;
    @Nullable
    private String nullableMimeType;

    public EmailMessageBinaryAttachmentBuilderImp(EmailMessageBuilder parentBuilder) {

        this(parentBuilder, JavaMailMessageService.INSTANCE);
    }

    // package-private for testing
    EmailMessageBinaryAttachmentBuilderImp(EmailMessageBuilder parentBuilder,
                                           JavaMailMessageService javaMailMessageService) {

        this.parentBuilder = ObjectArgs.checkNotNull(parentBuilder, "parentBuilder");
        this.javaMailMessageService = ObjectArgs.checkNotNull(javaMailMessageService, "javaMailMessageService");

        this.nullableAttachmentType = null;
        this.nullableFileName = null;
        this.nullableMimeType = null;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBuilder
    parent() {
        return parentBuilder;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBinaryAttachmentBuilder
    attachmentFileName(String attachmentFileName) {

        this.nullableAttachmentType = EmailMessageAttachmentType.ATTACHMENT;
        this.nullableFileName = StringArgs.checkNotEmptyOrWhitespace(attachmentFileName, "attachmentFileName");
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBinaryAttachmentBuilder
    inline() {

        this.nullableAttachmentType = EmailMessageAttachmentType.INLINE;
        this.nullableFileName = null;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBinaryAttachmentBuilder
    mimeType(String mimeType) {

        this.nullableMimeType = StringArgs.checkNotEmptyOrWhitespace(mimeType, "mimeType");
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBinaryAttachmentBuilder
    attachMessage(Message message)
    throws Exception {

        final byte[] byteArr = javaMailMessageService.getMimeContentAsByteArr(message);
        _attachBinaryData(byteArr, IsEmptyAllowed.NO);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBinaryAttachmentBuilder
    attachBinaryFile(File filePath, IsEmptyAllowed isEmptyAllowed)
    throws Exception {

        final FileInputStream fis = new FileInputStream(filePath);
        @EmptyContainerAllowed
        final byte[] byteArr = ByteStreams.toByteArray(fis);
        fis.close();
        _attachBinaryData(byteArr, isEmptyAllowed);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBinaryAttachmentBuilder
    attachBinaryInputStream(InputStream inputStream, IsEmptyAllowed isEmptyAllowed)
    throws Exception {

        @EmptyContainerAllowed
        final byte[] byteArr = ByteStreams.toByteArray(inputStream);
        inputStream.close();
        _attachBinaryData(byteArr, isEmptyAllowed);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBinaryAttachmentBuilder
    attachBinaryData(@EmptyContainerAllowed byte[] byteArr, IsEmptyAllowed isEmptyAllowed) {

        _attachBinaryData(byteArr, isEmptyAllowed);
        return this;
    }

    private void
    _attachBinaryData(@EmptyContainerAllowed byte[] byteArr, IsEmptyAllowed isEmptyAllowed) {

        final EmailMessageAttachment a =
            new EmailMessageAttachment(
                nullableAttachmentType, nullableFileName, nullableMimeType, byteArr, isEmptyAllowed);

        @EmptyContainerAllowed
        final ArrayList<EmailMessageAttachment> attachmentList = parentBuilder.attachmentList();
        attachmentList.add(a);
    }
}
