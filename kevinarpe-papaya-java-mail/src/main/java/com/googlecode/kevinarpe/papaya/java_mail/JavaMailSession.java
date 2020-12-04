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

import javax.mail.Message;
import javax.mail.Session;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JavaMailSessionBuilderFactory#INSTANCE
 */
public interface JavaMailSession {

    /**
     * @return new builder to create email messages
     */
    EmailMessageBuilder
    emailMessageBuilder();

    /**
     * Directly access the underlying {@link Session} object.  Usually, this is not necessary, as most send scenarios
     * are handled correctly via {@link #sendMessage(Message)}.
     */
    Session session();

    /**
     * Connects to SMTP gateway, then sends message.  Both of these steps may be throttled and will block.
     *
     * @throws Exception
     *         on connect or send error
     */
    @Blocking
    void sendMessage(Message message)
    throws Exception;
}
