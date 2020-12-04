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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import javax.annotation.Nullable;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
@FullyTested
public final class EmailMessageAttachment {

    public final EmailMessageAttachmentType attachmentType;

    /**
     * Never {@code null} when {@link #attachmentType} is {@link EmailMessageAttachmentType#ATTACHMENT}.
     * <p>
     * Always {@code null} when {@link #attachmentType} is {@link EmailMessageAttachmentType#INLINE}.
     * <p>
     * Ex(Text): {@code "log.txt"}
     * <p>
     * Ex(Binary): {@code "binary_data.bin"}
     */
    @Nullable
    public String nullableFileName;

    /**
     * Ex(Text): {@code "text/plain; charset=utf-8"}
     * <p>
     * Ex(Binary): {@code "application/octet-stream"}
     */
    public final String mimeType;

    /**
     * Data for attachment
     */
    @EmptyContainerAllowed
    public final byte[] byteArr;

    /**
     * Instead of direct access, API users should use {@link EmailMessageBuilder#builderForTextAttachment()}
     * or  {@link EmailMessageBuilder#builderForBinaryAttachment()}.
     *
     * @param attachmentType
     *        most users will expect {@link EmailMessageAttachmentType#ATTACHMENT}
     *
     * @param nullableFileName
     *        when {@code attachmentType} is {@link EmailMessageAttachmentType#ATTACHMENT},
     *        must not be empty or all whitespace
     *        <br>when {@code attachmentType} is {@link EmailMessageAttachmentType#INLINE}, always {@code null}
     *
     * @param mimeType
     *        MIME type for attachment
     *        <br>See: https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
     *
     * @param byteArr
     *        array of bytes for the attachment
     *        <br>if {@code isEmptyAllowed} is {@link IsEmptyAllowed#NO}, this array must not be empty.
     *
     * @param isEmptyAllowed
     *        normally, this is {@link IsEmptyAllowed#NO}
     *
     * @throws IllegalArgumentException
     *         if {@code attachmentType} is {@link EmailMessageAttachmentType#ATTACHMENT}
     *         and {@code nullableFileName} is empty or all whitespace
     *         <br>if {@code mimeType} is empty or all whitespace
     *         <br>if {@code isEmptyAllowed} is {@link IsEmptyAllowed#NO} and {@code byteArr} is empty
     */
    public EmailMessageAttachment(EmailMessageAttachmentType attachmentType,
                                  @Nullable
                                  String nullableFileName,
                                  String mimeType,
                                  @EmptyContainerAllowed
                                  byte[] byteArr,
                                  IsEmptyAllowed isEmptyAllowed) {

        _checkAttachmentTypeAndFileName(attachmentType, nullableFileName);
        this.attachmentType = attachmentType;
        this.nullableFileName = nullableFileName;
        this.mimeType = StringArgs.checkNotEmptyOrWhitespace(mimeType, "mimeType");
        ObjectArgs.checkNotNull(isEmptyAllowed, "isEmptyAllowed");
        if (IsEmptyAllowed.NO.equals(isEmptyAllowed)) {
            ArrayArgs.checkNotEmpty(byteArr, "byteArr");
        }
        this.byteArr = ObjectArgs.checkNotNull(byteArr, "byteArr");
    }

    private static void
    _checkAttachmentTypeAndFileName(EmailMessageAttachmentType attachmentType,
                                    @Nullable String nullableFileName) {

        ObjectArgs.checkNotNull(attachmentType, "attachmentType");
        switch (attachmentType) {

            case ATTACHMENT: {
                StringArgs.checkNotEmptyOrWhitespace(nullableFileName, "nullableFileName");
                break;
            }
            case INLINE: {
                ObjectArgs.checkIsNull(nullableFileName, "nullableFileName");
                break;
            }
            // Note(Testing): This case is impossible to test.  :)
            default: {
                throw new IllegalStateException("Internal error: Missing switch case for "
                    + attachmentType.getClass().getSimpleName() + "." + attachmentType.name());
            }
        }
    }
}
