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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
@FullyTested
public enum EmailMessageAddressListType {

    /**
     * Optional: Address list for Reply-To:
     *
     * @see MimeMessage#setReplyTo(Address[])
     */
    REPLY_TO() {
        @Override
        public void setValue(MimeMessage m, @Nullable @EmptyContainerAllowed InternetAddress[] addrArr)
        throws Exception {
            m.setReplyTo(addrArr);
        }
    },
    /**
     * Address list for To:
     * <p>
     * An email message must have at least one email address in To:, Cc:, or Bcc:
     */
    TO(Message.RecipientType.TO) {
        @Override
        public void setValue(MimeMessage m, @Nullable @EmptyContainerAllowed InternetAddress[] addrArr)
        throws Exception {
            m.setRecipients(Message.RecipientType.TO, addrArr);
        }
    },
    /**
     * Address list for Cc:
     * <p>
     * An email message must have at least one email address in To:, Cc:, or Bcc:
     */
    CC(Message.RecipientType.CC) {
        @Override
        public void setValue(MimeMessage m, @Nullable @EmptyContainerAllowed InternetAddress[] addrArr)
        throws Exception {
            m.setRecipients(Message.RecipientType.CC, addrArr);
        }
    },
    /**
     * Address list for Bcc:
     * <p>
     * An email message must have at least one email address in To:, Cc:, or Bcc:
     */
    BCC(Message.RecipientType.BCC) {
        @Override
        public void setValue(MimeMessage m, @Nullable @EmptyContainerAllowed InternetAddress[] addrArr)
        throws Exception {
            m.setRecipients(Message.RecipientType.BCC, addrArr);
        }
    },
    ;
    @Nullable
    public final Message.RecipientType nullableRecipientType;

    private EmailMessageAddressListType(Message.RecipientType recipientType) {

        this.nullableRecipientType = ObjectArgs.checkNotNull(recipientType, "recipientType");
    }

    private EmailMessageAddressListType() {
        this.nullableRecipientType = null;
    }

    public abstract void
    setValue(MimeMessage m, @Nullable @EmptyContainerAllowed InternetAddress[] addrArr)
    throws Exception;
}
