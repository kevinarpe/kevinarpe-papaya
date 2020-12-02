package com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.util;

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
import com.googlecode.kevinarpe.papaya.annotation.DebugBreakpoint;
import com.googlecode.kevinarpe.papaya.annotation.EmptyStringAllowed;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;
import com.googlecode.kevinarpe.papaya.function.ThrowingConsumer;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Multiple global singletons
public final class TestHttpServerImp
implements TestHttpServer {

    private final ExceptionThrower exceptionThrower;
    @Nullable
    private ServerSocket nullableServerSocket;

    public TestHttpServerImp(ExceptionThrower exceptionThrower) {

        this.exceptionThrower = ObjectArgs.checkNotNull(exceptionThrower, "exceptionThrower");
        this.nullableServerSocket = null;
    }

    @Override
    public final ServerSocket
    bindWithAutoAllocPort()
    throws Exception {

        if (null != nullableServerSocket) {

            throw exceptionThrower.throwRuntimeException(IllegalStateException.class,
                "Already bound to port %d", nullableServerSocket.getLocalPort());
        }
        try {
            nullableServerSocket = new ServerSocket(0);
        }
        catch (Exception e) {
            throw exceptionThrower.throwChainedCheckedException(Exception.class,
                e,
                "Failed to bind to an auto-allocated port (zero)");
        }
        return nullableServerSocket;
    }

    @Blocking
    @Override
    public final void
    accept(ThrowingConsumer<Socket> afterConnectCallback)
    throws Exception {

        if (null == nullableServerSocket) {

            throw exceptionThrower.throwRuntimeException(IllegalStateException.class, "Must bind to port first");
        }
        @Blocking
        final Socket socket = nullableServerSocket.accept();
        // @Blocking
        afterConnectCallback.accept(socket);
        try {
            socket.close();
        }
        catch (Exception e) {
            throw exceptionThrower.throwChainedCheckedException(Exception.class,
                e,
                "Failed to close socket bound to port %d", nullableServerSocket.getLocalPort());
        }
    }

    public static void
    staticReadGetRequest(Socket socket)
    throws Exception {

        // Intentional: Do not use
        final InputStream is = socket.getInputStream();
        final InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        final BufferedReader br = new BufferedReader(isr);

        while (true) {
            @Nullable
            @EmptyStringAllowed
            final String line = br.readLine();
            if (null == line) {
                break;
            }
            System.out.printf("%s%n", line);
            if (line.isEmpty()) {
                break;
            }
        }
        @DebugBreakpoint
        int dummy = 1;
    }

    public static void
    staticWriteHtmlResponse(Socket socket,
                            String html)
    throws Exception {

        final String headers = ""
            + "HTTP/1.1 200 OK\r\n"
            + "Content-Type: text/html; charset=utf-8\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: " + html.getBytes(StandardCharsets.UTF_8).length + "\r\n"
            + "\r\n";

        final String full = headers + html;
        final byte[] outputByteArr = full.getBytes(StandardCharsets.UTF_8);

        try (final OutputStream os = socket.getOutputStream()) {

            // @Blocking
            os.write(outputByteArr);
        }
        @DebugBreakpoint
        int dummy = 1;
    }

    @Override
    public final void
    close()
    throws Exception {

        if (null != nullableServerSocket) {

            nullableServerSocket.close();
            nullableServerSocket = null;
        }
    }
}
