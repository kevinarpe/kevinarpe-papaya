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

import com.google.common.collect.LinkedHashMultimap;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;

import javax.annotation.Nullable;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
public interface EmailMessageBuilder {

    /**
     * @return access parent Java mail {@link Session} object
     */
    JavaMailSession
    javaMailSession();

    /**
     * Sets an email address.
     * <p>
     * Required: {@link EmailMessageAddressType#FROM}
     * <p>
     * Optional: {@link EmailMessageAddressType#SENDER}
     *
     * @param nullableAddress
     *        use {@code null} to clear the address
     *
     * @return self for fluent interface / method chaining
     */
    EmailMessageBuilder
    address(EmailMessageAddressType addressType,
            @Nullable EmailMessageAddress nullableAddress);

    /**
     * Adds an email address to an address list.
     * <p>
     * Important: An email message must have at least one recipient in To:, Cc:, or Bcc: lists.
     * <p>
     * If more advanced collection operations are required for the address list,
     * see: {@link #addressSet(EmailMessageAddressListType)}.
     *
     * @return self for fluent interface / method chaining
     *
     * @see #addAllToAddressSet(EmailMessageAddressListType, Collection)
     */
    EmailMessageBuilder
    addToAddressSet(EmailMessageAddressListType addressListType,
                    EmailMessageAddress address);

    /**
     * Adds all email addresses from a collection to an address list.
     * <p>
     * Important: An email message must have at least one recipient in To:, Cc:, or Bcc: lists.
     * <p>
     * If more advanced collection operations are required for the address list,
     * see: {@link #addressSet(EmailMessageAddressListType)}.
     *
     * @return self for fluent interface / method chaining
     *
     * @throws IllegalArgumentException
     *         if argument {@code nonEmptyAddressCollectionToAdd} is empty
     *
     * @see #addToAddressSet(EmailMessageAddressListType, EmailMessageAddress)
     */
    EmailMessageBuilder
    addAllToAddressSet(EmailMessageAddressListType addressListType,
                       Collection<? extends EmailMessageAddress> nonEmptyAddressCollectionToAdd);

    /**
     * Access an address set.  Callers may add or remove addresses from the set.
     * <p>
     * Important: An email message must have at least one recipient in To:, Cc:, or Bcc: lists.
     * <p>
     * Why return {@link LinkedHashSet}?  It is an ordered unique collection of addresses.
     */
    @EmptyContainerAllowed
    LinkedHashSet<EmailMessageAddress>
    addressSet(EmailMessageAddressListType addressListType);

    /**
     * Optional: Access custom headers multi-map.  Callers may add or remove entries from the multi-map.
     */
    @EmptyContainerAllowed
    LinkedHashMultimap<String, String>
    headers();

    /**
     * Required: Sets the email subject.
     *
     * @param subject
     *        must not be empty or all whitespace
     *
     * @return self for fluent interface / method chaining
     *
     * @throws IllegalArgumentException
     *         if {@code subject} is empty or all whitespace
     */
    EmailMessageBuilder
    subject(String subject);

    /**
     * Required: Sets the email body.
     *
     * @param textMimeSubType
     *        initial release will only support plain text or HTML email body
     *
     * @param text
     *        must not be empty or all whitespace
     *
     * @return self for fluent interface / method chaining
     *
     * @throws IllegalArgumentException
     *         if {@code text} is empty or all whitespace
     */
    EmailMessageBuilder
    body(TextMimeSubType textMimeSubType, String text);

    /**
     * Optional: Creates a builder for text attachments.  Callers may reuse this builder for multiple attachments,
     * or may create a new builder for each attachment.  There are no restrictions on its usage.
     * <p>
     * For binary attachments, see: {@link #builderForBinaryAttachment()}.
     *
     * @return new builder for text attachments
     *
     * @see #builderForBinaryAttachment()
     * @see #attachmentList()
     */
    EmailMessageTextAttachmentBuilder
    builderForTextAttachment();

    /**
     * Optional: Creates a builder for binary attachments.  Callers may reuse this builder for multiple attachments,
     * or may create a new builder for each attachment.  There are no restrictions on its usage.
     * <p>
     * For text attachments, see: {@link #builderForTextAttachment()}.
     *
     * @return new builder for binary attachments
     *
     * @see #builderForTextAttachment()
     * @see #attachmentList()
     */
    EmailMessageBinaryAttachmentBuilder
    builderForBinaryAttachment();

    /**
     * Optional: Directly access list of attachments.  Callers may add or remove entries from the list.
     *
     * @see #builderForTextAttachment()
     * @see #builderForBinaryAttachment()
     */
    @EmptyContainerAllowed
    ArrayList<EmailMessageAttachment>
    attachmentList();

    /**
     * Constructs a new instance of {@link MimeMessage}.  Throws if any required values are unset.
     *
     * @throws Exception
     *         if any required value is missing
     *         <br>or if {@link MimeMessage} throws on construction
     *
     * @see JavaMailSessionBuilder
     * @see JavaMailSession#session()
     * @see JavaMailSession#sendMessage(Message)
     */
    MimeMessage
    build()
    throws Exception;
}
