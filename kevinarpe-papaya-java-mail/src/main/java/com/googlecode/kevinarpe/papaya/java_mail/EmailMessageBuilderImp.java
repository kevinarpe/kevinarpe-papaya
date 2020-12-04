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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.container.AreNullValuesAllowed;
import com.googlecode.kevinarpe.papaya.container.FullEnumMap;
import com.googlecode.kevinarpe.papaya.container.ImmutableFullEnumMap;

import javax.activation.DataHandler;
import javax.annotation.Nullable;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
public final class EmailMessageBuilderImp
implements EmailMessageBuilder {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final JavaMailSession javaMailSession;
    private final EmailMessageTextAttachmentBuilderFactory textAttachmentBuilderFactory;
    private final EmailMessageBinaryAttachmentBuilderFactory binaryAttachmentBuilderFactory;

    private Charset charset;
    private final FullEnumMap<EmailMessageAddressType, Optional<EmailMessageAddress>> addressTypeMap;
    private final ImmutableFullEnumMap<EmailMessageAddressListType, LinkedHashSet<EmailMessageAddress>>
        addressListTypeMap;
    @Nullable
    @EmptyContainerAllowed
    private LinkedHashMultimap<String, String> nullableHeaderLinkedHashMultimap;
    @Nullable
    private String nullableSubject;
    @Nullable
    private TextMimeSubType nullableBodyTextMimeSubType;
    @Nullable
    private String nullableBodyText;
    @Nullable
    @EmptyContainerAllowed
    private ArrayList<EmailMessageAttachment> nullableAttachmentList;

    public EmailMessageBuilderImp(JavaMailSession javaMailSession) {
        this(
            javaMailSession,
            EmailMessageTextAttachmentBuilderFactory.INSTANCE,
            EmailMessageBinaryAttachmentBuilderFactory.INSTANCE);
    }

    // package-private for testing
    EmailMessageBuilderImp(JavaMailSession javaMailSession,
                           EmailMessageTextAttachmentBuilderFactory textAttachmentBuilderFactory,
                           EmailMessageBinaryAttachmentBuilderFactory binaryAttachmentBuilderFactory) {

        this.javaMailSession = ObjectArgs.checkNotNull(javaMailSession, "javaMailSession");

        this.textAttachmentBuilderFactory =
            ObjectArgs.checkNotNull(textAttachmentBuilderFactory, "textAttachmentBuilderFactory");

        this.binaryAttachmentBuilderFactory =
            ObjectArgs.checkNotNull(binaryAttachmentBuilderFactory, "binaryAttachmentBuilderFactory");

        this.charset = DEFAULT_CHARSET;

        this.addressTypeMap =
            FullEnumMap.ofKeys(EmailMessageAddressType.class, AreNullValuesAllowed.NO, any -> Optional.empty());

        this.addressListTypeMap =
            ImmutableFullEnumMap.ofKeys(EmailMessageAddressListType.class, any -> new LinkedHashSet<>());

        this.nullableHeaderLinkedHashMultimap = null;
        this.nullableSubject = null;
        this.nullableBodyTextMimeSubType = null;
        this.nullableBodyText = null;
        this.nullableAttachmentList = null;
    }

    /** {@inheritDoc} */
    @Override
    public JavaMailSession
    javaMailSession() {
        return javaMailSession;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBuilder
    address(EmailMessageAddressType addressType,
            @Nullable EmailMessageAddress nullableAddress) {

        addressTypeMap.put(addressType, Optional.ofNullable(nullableAddress));
        return this;
    }

    /** {@inheritDoc} */
    @EmptyContainerAllowed
    @Override
    public LinkedHashSet<EmailMessageAddress>
    addressSet(EmailMessageAddressListType addressListType) {

        @EmptyContainerAllowed
        final LinkedHashSet<EmailMessageAddress> x = addressListTypeMap.getByEnum(addressListType);
        return x;
    }

    /** {@inheritDoc} */
    @EmptyContainerAllowed
    @Override
    public LinkedHashMultimap<String, String>
    headers() {

        if (null == nullableHeaderLinkedHashMultimap) {
            nullableHeaderLinkedHashMultimap = LinkedHashMultimap.create();
        }
        return nullableHeaderLinkedHashMultimap;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBuilder
    subject(String subject) {

        this.nullableSubject = StringArgs.checkNotEmptyOrWhitespace(subject, "subject");
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBuilder
    body(TextMimeSubType textMimeSubType,
         String text) {

        this.nullableBodyTextMimeSubType = ObjectArgs.checkNotNull(textMimeSubType, "textMimeType");
        this.nullableBodyText = StringArgs.checkNotEmptyOrWhitespace(text, "text");
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageTextAttachmentBuilder
    builderForTextAttachment() {

        final EmailMessageTextAttachmentBuilder x = textAttachmentBuilderFactory.newInstance(this);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBinaryAttachmentBuilder
    builderForBinaryAttachment() {

        final EmailMessageBinaryAttachmentBuilder x = binaryAttachmentBuilderFactory.newInstance(this);
        return x;
    }

    /** {@inheritDoc} */
    @EmptyContainerAllowed
    @Override
    public ArrayList<EmailMessageAttachment>
    attachmentList() {

        if (null == nullableAttachmentList) {
            nullableAttachmentList = new ArrayList<>();
        }
        return nullableAttachmentList;
    }

    /** {@inheritDoc} */
    @Override
    public MimeMessage
    build()
    throws Exception {

        final Session session = javaMailSession.session();
        final MimeMessage m = new MimeMessage(session);

        _setAddresses(m);
        _setAddresses2(m);
        _setHeaders(m);
        _setSubject(m);

        final MimeMultipart mimeMultipart = new MimeMultipart();

        _setBody(mimeMultipart);
        _addAttachments(mimeMultipart);

        m.setContent(mimeMultipart);
        return m;
    }

    private void
    _setAddresses(MimeMessage m)
    throws Exception {

        _assertHasRequiredAddress();
        for (final EmailMessageAddressType addressType : EmailMessageAddressType.values()) {

            final Optional<EmailMessageAddress> optionalAddress = addressTypeMap.getByEnum(addressType);
            if (optionalAddress.isPresent()) {

                final EmailMessageAddress addr = optionalAddress.get();
                final InternetAddress inetAddr = addr.createInternetAddress();
                addressType.setValue(m, inetAddr);
            }
        }
    }

    private void
    _assertHasRequiredAddress() {

        for (final EmailMessageAddressType addressType : EmailMessageAddressType.values()) {

            if (addressType.isRequired) {

                final Optional<EmailMessageAddress> optionalAddress = addressTypeMap.getByEnum(addressType);
                if (false == optionalAddress.isPresent()) {

                    throw new IllegalStateException(
                        "Missing " + EmailMessageAddressType.class.getSimpleName() + "." + addressType.name());
                }
            }
        }
    }

    private void
    _setAddresses2(MimeMessage m)
    throws Exception {

        _assertRecipientExists();
        for (final EmailMessageAddressListType addressListType : EmailMessageAddressListType.values()) {

            @EmptyContainerAllowed
            final LinkedHashSet<EmailMessageAddress> set = addressListTypeMap.getByEnum(addressListType);
            if (set.size() > 0) {

                final InternetAddress[] inetAddrArr = _toInetAddrArr(set);
                addressListType.setValue(m, inetAddrArr);
            }
        }
    }

    private void
    _assertRecipientExists() {

        for (final EmailMessageAddressListType addressListType : EmailMessageAddressListType.values()) {

            if (null != addressListType.nullableRecipientType) {

                @EmptyContainerAllowed
                final LinkedHashSet<EmailMessageAddress> addressSet = addressListTypeMap.getByEnum(addressListType);
                if (addressSet.size() > 0) {
                    return;
                }
            }
        }
        throw new IllegalStateException("Failed to find any recipient addresses (To:, Cc:, Bcc:)");
    }

    private InternetAddress[]
    _toInetAddrArr(LinkedHashSet<EmailMessageAddress> set)
    throws Exception {

        final InternetAddress[] inetAddrArr = new InternetAddress[set.size()];
        int i = 0;
        for (final EmailMessageAddress addr : set) {

            final InternetAddress inetAddr = addr.createInternetAddress();
            inetAddrArr[i] = inetAddr;
            ++i;
        }
        return inetAddrArr;
    }

    private void
    _setSubject(MimeMessage m)
    throws Exception {

        if (null == nullableSubject) {
            throw new IllegalStateException("Missing subject");
        }
        m.setSubject(nullableSubject, charset.name());
    }

    private void
    _setHeaders(MimeMessage m)
    throws Exception {

        if (null == nullableHeaderLinkedHashMultimap || nullableHeaderLinkedHashMultimap.isEmpty()) {
            return;
        }
        for (final Map.Entry<String, String> entry : nullableHeaderLinkedHashMultimap.entries()) {

            m.addHeader(entry.getKey(), entry.getValue());
        }
    }

    private void
    _setBody(MimeMultipart mimeMultipart)
    throws Exception {

        if (null == nullableBodyTextMimeSubType || null == nullableBodyText) {
            throw new IllegalStateException("Missing body text");
        }
        final MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setText(nullableBodyText, charset.name(), nullableBodyTextMimeSubType.textMimeSubType);
        mimeMultipart.addBodyPart(mimeBodyPart);
    }

    private void
    _addAttachments(MimeMultipart mimeMultipart)
    throws Exception {

        if (null == nullableAttachmentList || nullableAttachmentList.isEmpty()) {
            return;
        }
        for (final EmailMessageAttachment attachment : nullableAttachmentList) {

            final MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setDisposition(attachment.attachmentType.disposition);

            if (null != attachment.nullableFileName) {

                mimeBodyPart.setFileName(attachment.nullableFileName);
            }
            final ByteArrayDataSource ds = new ByteArrayDataSource(attachment.byteArr, attachment.mimeType);
            final DataHandler dh = new DataHandler(ds);
            mimeBodyPart.setDataHandler(dh);
            mimeMultipart.addBodyPart(mimeBodyPart);
        }
    }
}
