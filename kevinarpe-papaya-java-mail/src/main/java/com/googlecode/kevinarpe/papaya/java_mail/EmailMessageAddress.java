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
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import javax.annotation.Nullable;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Set;

/**
 * Both {@link #hashCode()} and {@link #equals(Object)} are implemented,
 * so these objects are safe to use with {@link Set}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
@FullyTested
public final class EmailMessageAddress {

    /**
     * Constructs a new email address without a display name, e.g., "username@example.com".
     *
     * @param emailAddress
     *        Ex: {@code "username@example.com"}
     *        <br>must not be empty or all whitespace
     *
     * @throws IllegalArgumentException
     *         if {@code emailAddress} is empty or all whitespace
     *
     * @see #fromEmailAddressAndDisplayName(String, String)
     * @see #createInternetAddress()
     */
    public static EmailMessageAddress
    fromEmailAddressOnly(String emailAddress) {

        final String nullableDisplayName = null;
        final EmailMessageAddress x = new EmailMessageAddress(emailAddress, nullableDisplayName);
        return x;
    }

    /**
     * Constructs a new email address with a display name, e.g., {@code "First Middle LAST <username@example.com>"}.
     *
     * @param emailAddress
     *        Ex: {@code "username@example.com"}
     *        <br>must not be empty or all whitespace
     *
     * @param displayName
     *        Ex: {@code "First Middle LAST"}
     *        <br>must not be empty or all whitespace
     *
     * @throws IllegalArgumentException
     *         if {@code emailAddress} or {@code displayName} is empty or all whitespace
     *
     * @see #fromEmailAddressAndDisplayName(String, String)
     * @see #createInternetAddress()
     */
    public static EmailMessageAddress
    fromEmailAddressAndDisplayName(String emailAddress, String displayName) {

        final EmailMessageAddress x = new EmailMessageAddress(emailAddress, displayName);
        return x;
    }

    /** Ex: {@code "kevinarpe@gmail.com"} */
    public final String emailAddress;
    /**
     * Never empty or all whitespace
     * <p>
     * Ex: {@code "Kevin Connor ARPE"}
     */
    @Nullable
    public final String nullableDisplayName;

    private EmailMessageAddress(String emailAddress, @Nullable String nullableDisplayName) {

        this.emailAddress = StringArgs.checkNotEmptyOrWhitespace(emailAddress, "emailAddress");
        if (null != nullableDisplayName) {
            StringArgs.checkNotEmptyOrWhitespace(nullableDisplayName, "nullableDisplayName");
        }
        this.nullableDisplayName = nullableDisplayName;
    }

    /**
     * Creates an instance of {@link InternetAddress} for use with {@link MimeMessage}.
     *
     * @throws Exception
     *         if email address is invalid or display name cannot be correctly encoded
     */
    public InternetAddress
    createInternetAddress()
    throws Exception {

        final InternetAddress x =
            new InternetAddress(emailAddress, nullableDisplayName, EmailMessageBuilderImp.DEFAULT_CHARSET.name());
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public int
    hashCode() {

        final int x = Objects.hash(emailAddress, nullableDisplayName);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public boolean
    equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }
        // Note: Class.isInstance() works correctly when 'obj' is null, but use 'null == obj' to be explicit!
        if (null == obj || false == this.getClass().isInstance(obj)) {
            return false;
        }
        final EmailMessageAddress other = this.getClass().cast(obj);

        final boolean x =
            this.emailAddress.equals(other.emailAddress)
                && Objects.equals(this.nullableDisplayName, other.nullableDisplayName);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public String
    toString() {

        if (null == nullableDisplayName) {
            return emailAddress;
        }
        else {
            final String x = nullableDisplayName + " <" + emailAddress + ">";
            return x;
        }
    }
}
