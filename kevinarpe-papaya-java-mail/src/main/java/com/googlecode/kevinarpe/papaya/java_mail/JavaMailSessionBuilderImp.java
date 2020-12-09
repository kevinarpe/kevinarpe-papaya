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
import com.googlecode.kevinarpe.papaya.argument.IntArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import javax.annotation.Nullable;
import javax.mail.Session;
import java.util.Properties;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * See: EmailMessageBuilderImpTest
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
@FullyTested
public final class JavaMailSessionBuilderImp
implements JavaMailSessionBuilder {

    // Ref: https://javaee.github.io/javamail/docs/api/com/sun/mail/smtp/package-summary.html
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_USER = "mail.smtp.user";
    public static final String MAIL_SMTP_PASSWORD = "mail.smtp.password";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

    private final Properties properties;

    public JavaMailSessionBuilderImp() {

        this.properties = new Properties();
        // In 2020, what kind of SMTP server does *not* require this setting!!??
        this.properties.put(MAIL_SMTP_STARTTLS_ENABLE, "true");
    }

    /** {@inheritDoc} */
    @Override
    public JavaMailSessionBuilder
    host(String host, AlwaysTrustSSL alwaysTrustSSL) {

        StringArgs.checkNotEmptyOrWhitespace(host, "host");
        ObjectArgs.checkNotNull(alwaysTrustSSL, "alwaysTrustSSL");

        properties.setProperty(MAIL_SMTP_HOST, host);

        if (AlwaysTrustSSL.YES.equals(alwaysTrustSSL)) {
            properties.setProperty(MAIL_SMTP_SSL_TRUST, host);
        }
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public JavaMailSessionBuilder
    port(SmtpPort smtpPort) {

        ObjectArgs.checkNotNull(smtpPort, "smtpPort");
        properties.setProperty(MAIL_SMTP_PORT, String.valueOf(smtpPort.port));
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public JavaMailSessionBuilder
    customPort(final int port) {

        IntArgs.checkMinValue(port, 1, "port");
        properties.setProperty(MAIL_SMTP_PORT, String.valueOf(port));
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public JavaMailSessionBuilder
    usernameAndPassword(String username, String password) {

        StringArgs.checkNotEmptyOrWhitespace(username, "username");
        StringArgs.checkNotEmptyOrWhitespace(password, "password");
        properties.setProperty(MAIL_SMTP_USER, username);
        properties.setProperty(MAIL_SMTP_PASSWORD, password);
        properties.setProperty(MAIL_SMTP_AUTH, "true");
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Properties
    properties() {
        return properties;
    }

    /** {@inheritDoc} */
    @Override
    public JavaMailSession
    build() {

        _assertSet(MAIL_SMTP_HOST, "host");
        _assertSet(MAIL_SMTP_PORT, "port");

        final Session session = Session.getInstance(properties);
        final JavaMailSession x = JavaMailSessionFactory.INSTANCE.newInstance(session);
        return x;
    }

    private void
    _assertSet(String propertyName, String attributeName) {

        @Nullable
        final String value = properties.getProperty(propertyName);
        if (null == value) {
            throw new IllegalStateException("Attribute is unset: " + attributeName);
        }
    }
}
