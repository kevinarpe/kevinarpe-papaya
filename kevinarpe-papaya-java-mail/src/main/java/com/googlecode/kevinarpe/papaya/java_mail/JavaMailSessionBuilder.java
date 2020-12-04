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

import javax.mail.Session;
import java.util.Properties;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
public interface JavaMailSessionBuilder {

    /**
     * Sets the hostname of SMTP gateway.
     *
     * @param host
     *        hostname of SMTP gateway, e.g., {@code "smtp.gmail.com"}
     *        <br>must not be empty or all whitespace
     *
     * @param alwaysTrustSSL
     *        set this option if you have trouble with SSL certificates
     *
     * @return self for fluent interface / method chaining
     *
     * @throws IllegalArgumentException
     *         if {@code host} is empty or all whitespace
     *
     * @see #port(SmtpPort)
     */
    JavaMailSessionBuilder
    host(String host, AlwaysTrustSSL alwaysTrustSSL);

    /**
     * Sets a standard port number for SMTP gateway.  For non-standard ports, see: {@link #customPort(int)}.
     *
     * @return self for fluent interface / method chaining
     *
     * @see #customPort(int)
     */
    JavaMailSessionBuilder
    port(SmtpPort smtpPort);

    /**
     * Sets a standard port number for SMTP gateway.  For standard ports, see: {@link #port(SmtpPort)}.
     *
     * @param port
     *        must not be zero or negative
     *
     * @return self for fluent interface / method chaining
     *
     * @throws IllegalArgumentException
     *         if {@code port} is invalid
     *
     * @see #port(SmtpPort)
     */
    JavaMailSessionBuilder
    customPort(int port);

    /**
     * Optional: Sets the username and password to authenticate with SMTP gateway.  This is frequently required for
     * external SMTP gateways, but may not be required for internal (corporate) SMTP gateways.
     *
     * @param username
     *        must not be empty or all whitespace
     *
     * @param password
     *        must not be empty or all whitespace
     *
     * @return self for fluent interface / method chaining
     *
     * @throws IllegalArgumentException
     *         if {@code username} or {@code password} is empty or all whitespace
     */
    JavaMailSessionBuilder
    usernameAndPassword(String username, String password);

    /**
     * Optional: Directly access {@link Properties} collection used to build final {@link Session} object.
     * <p>
     * Use this method if you need to customise properties before calling {@link #build()}.
     */
    @EmptyContainerAllowed
    Properties
    properties();

    /**
     * Builds a new {@link Session} object and returns wrapper.
     *
     * @see Session#getInstance(Properties)
     */
    JavaMailSession
    build();
}
