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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
@FullyTested
public enum EmailMessageAddressType {

    /**
     * Required: The from address of an email message.
     *
     * @see MimeMessage#setFrom(Address)
     */
    FROM(_IsRequired.YES) {
        @Override
        public void setValue(MimeMessage m, InternetAddress inetAddr)
        throws Exception {
            m.setFrom(inetAddr);
        }
    },
    /**
     * Optional: The sender address when 'sent-on-behalf'.
     * This is used when assistants send email on behalf of another person.
     *
     * @see MimeMessage#setSender(Address)
     */
    SENDER(_IsRequired.NO) {
        @Override
        public void setValue(MimeMessage m, InternetAddress inetAddr)
        throws Exception {
            m.setSender(inetAddr);
        }
    },
    ;
    private enum _IsRequired { YES, NO }

    private EmailMessageAddressType(_IsRequired isRequired) {

        ObjectArgs.checkNotNull(isRequired, "isRequired");
        this.isRequired = _IsRequired.YES.equals(isRequired);
    }

    public final boolean isRequired;

    public abstract void
    setValue(MimeMessage m,
             InternetAddress inetAddr)
    throws Exception;
}
