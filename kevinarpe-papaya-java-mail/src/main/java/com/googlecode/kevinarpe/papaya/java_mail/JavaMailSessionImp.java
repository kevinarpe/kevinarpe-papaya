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

import com.googlecode.kevinarpe.papaya.annotation.Blocking;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
public final class JavaMailSessionImp
implements JavaMailSession {

    private final Session session;
    private final EmailMessageBuilderFactory emailMessageBuilderFactory;

    public JavaMailSessionImp(Session session) {
        this(session, EmailMessageBuilderFactory.INSTANCE);
    }

    // package-private for testing
    JavaMailSessionImp(Session session,
                       EmailMessageBuilderFactory emailMessageBuilderFactory) {

        this.session = ObjectArgs.checkNotNull(session, "session");

        this.emailMessageBuilderFactory =
            ObjectArgs.checkNotNull(emailMessageBuilderFactory, "emailMessageBuilderFactory");
    }

    /** {@inheritDoc} */
    @Override
    public EmailMessageBuilder
    emailMessageBuilder() {

        final EmailMessageBuilder x = emailMessageBuilderFactory.newInstance(this);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public Session
    session() {
        return session;
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public void
    sendMessage(Message message)
    throws Exception {

        final Properties properties = session.getProperties();
        @Nullable
        final String username = properties.getProperty(JavaMailSessionBuilderImp.MAIL_SMTP_USER);
        if (null == username) {
            // @Blocking
            Transport.send(message);
        }
        else {
            // Ref: https://stackoverflow.com/a/47452/257299
            final String host = properties.getProperty(JavaMailSessionBuilderImp.MAIL_SMTP_HOST);
            final String password = properties.getProperty(JavaMailSessionBuilderImp.MAIL_SMTP_PASSWORD);
            final Transport transport = session.getTransport("smtp");
            // If sending via Google GMail, this method may throw if not using app password.
            // javax.mail.AuthenticationFailedException: 534-5.7.9 Application-specific password required. Learn more at
            // 534 5.7.9  https://support.google.com/mail/?p=InvalidSecondFactor v17sm1621393pga.58 - gsmtp
            // @Blocking
            transport.connect(host, username, password);
            // @Blocking
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
    }
}
