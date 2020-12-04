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

import com.google.common.base.Joiner;
import com.google.common.io.CharStreams;
import com.googlecode.kevinarpe.papaya.StringUtils;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.EmptyStringAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.concurrent.ThreadLocalWithReset;
import com.googlecode.kevinarpe.papaya.concurrent.ThreadLocalsWithReset;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * See: EmailMessageBuilderImpTest
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
@FullyTested
public final class EmailMessageTextAttachmentBuilderImp
implements EmailMessageTextAttachmentBuilder {

    private final EmailMessageBuilder parentBuilder;

    @Nullable
    private EmailMessageAttachmentType nullableAttachmentType;
    @Nullable
    private String nullableAttachmentFileName;
    @Nullable
    private String nullableTextMimeSubType;

    public EmailMessageTextAttachmentBuilderImp(EmailMessageBuilder parentBuilder) {

        this.parentBuilder = ObjectArgs.checkNotNull(parentBuilder, "parentBuilder");

        this.nullableAttachmentType = null;
        this.nullableAttachmentFileName = null;
        this.nullableTextMimeSubType = null;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBuilder
    parent() {
        return parentBuilder;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageTextAttachmentBuilder
    attachmentFileName(String attachmentFileName) {

        this.nullableAttachmentType = EmailMessageAttachmentType.ATTACHMENT;
        this.nullableAttachmentFileName =
            StringArgs.checkNotEmptyOrWhitespace(attachmentFileName, "attachmentFileName");
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageTextAttachmentBuilder
    inline() {

        this.nullableAttachmentType = EmailMessageAttachmentType.INLINE;
        this.nullableAttachmentFileName = null;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageTextAttachmentBuilder
    textMimeSubType(TextMimeSubType textMimeSubType) {

        ObjectArgs.checkNotNull(textMimeSubType, "textMimeType");
        this.nullableTextMimeSubType = textMimeSubType.textMimeSubType;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageTextAttachmentBuilder
    customTextMimeSubType(String customTextMimeSubType) {

        this.nullableTextMimeSubType =
            StringArgs.checkNotEmptyOrWhitespace(customTextMimeSubType, "customTextMimeSubType");
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageTextAttachmentBuilder
    attachTextFile(File filePath,
                   Charset fileCharset,
                   EmailMessageAttachmentTextNewLine newLine,
                   IsEmptyAllowed isEmptyAllowed)
    throws Exception {

        _assetAllSet();
        final FileInputStream fis = new FileInputStream(filePath);
        final InputStreamReader isr = new InputStreamReader(fis, fileCharset);
        @EmptyStringAllowed
        final String text = CharStreams.toString(isr);
        isr.close();
        _attachText(text, newLine, isEmptyAllowed);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageTextAttachmentBuilder
    attachTextInputStream(InputStream inputStream,
                          Charset inputStreamCharset,
                          EmailMessageAttachmentTextNewLine newLine,
                          IsEmptyAllowed isEmptyAllowed)
    throws Exception {

        _assetAllSet();
        final InputStreamReader isr = new InputStreamReader(inputStream, inputStreamCharset);
        @EmptyStringAllowed
        final String text = CharStreams.toString(isr);
        isr.close();
        _attachText(text, newLine, isEmptyAllowed);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageTextAttachmentBuilder
    attachText(@EmptyStringAllowed String text,
               EmailMessageAttachmentTextNewLine newLine,
               IsEmptyAllowed isEmptyAllowed) {

        _assetAllSet();
        _attachText(text, newLine, isEmptyAllowed);
        return this;
    }

    private static final ThreadLocalWithReset<ArrayList<String>> threadLocalMissingList =
        ThreadLocalsWithReset.newInstanceForArrayList();

    private void
    _assetAllSet() {

        final ArrayList<String> missingList = threadLocalMissingList.getAndReset();
        if (null == nullableAttachmentType) {

            missingList.add("attachment type");
        }
        if (EmailMessageAttachmentType.ATTACHMENT.equals(nullableAttachmentType)
            &&
            null == nullableAttachmentFileName) {

            // Unreachable code?  Me thinks yes.
            missingList.add("file name");
        }
        if (null == nullableTextMimeSubType) {

            missingList.add("MIME text sub-type");
        }
        if (missingList.size() > 0) {

            throw new IllegalStateException("Missing: " + Joiner.on(", ").join(missingList));
        }
    }

    private void
    _attachText(@EmptyStringAllowed String text,
                EmailMessageAttachmentTextNewLine newLine,
                IsEmptyAllowed isEmptyAllowed) {

        final String adjText = _adjNewLines(text, newLine, isEmptyAllowed);
        final byte[] byteArr = adjText.getBytes(EmailMessageBuilderImp.DEFAULT_CHARSET);
        // Ex: "text/plain; charset=UTF-8"
        final String mimeType =
            "text/" + nullableTextMimeSubType + "; charset=" + EmailMessageBuilderImp.DEFAULT_CHARSET.name();
        final EmailMessageAttachment a =
            new EmailMessageAttachment(nullableAttachmentType, nullableAttachmentFileName, mimeType, byteArr, isEmptyAllowed);

        @EmptyContainerAllowed
        final ArrayList<EmailMessageAttachment> attachmentList = parentBuilder.attachmentList();
        attachmentList.add(a);
    }

    private String
    _adjNewLines(@EmptyStringAllowed String text,
                 EmailMessageAttachmentTextNewLine newLine,
                 IsEmptyAllowed isEmptyAllowed) {

        if (IsEmptyAllowed.NO.equals(isEmptyAllowed)) {
            StringArgs.checkNotEmpty(text, "text");
        }
        switch (newLine) {
            case UNCHANGED: {
                return text;
            }
            case UNIX: {
                final String x = text.replace(StringUtils.WINDOWS_NEW_LINE, StringUtils.UNIX_NEW_LINE);
                return x;
            }
            case WINDOWS: {
                final String x =
                    text.replace(StringUtils.WINDOWS_NEW_LINE, StringUtils.UNIX_NEW_LINE)
                        .replace(StringUtils.UNIX_NEW_LINE, StringUtils.WINDOWS_NEW_LINE);
                return x;
            }
            default: {
                throw new IllegalStateException("Internal error: Missing switch case for "
                    + newLine.getClass().getSimpleName() + "." + newLine.name());
            }
        }
    }
}
