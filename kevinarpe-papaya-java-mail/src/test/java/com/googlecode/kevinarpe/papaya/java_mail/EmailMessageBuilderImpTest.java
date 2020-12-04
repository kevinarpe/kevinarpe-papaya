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

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.subethamail.smtp.server.SMTPServer;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class EmailMessageBuilderImpTest {

    private Wiser wiser;

    @BeforeMethod
    public void beforeEachTestMethod() {

        this.wiser = Wiser.create(SMTPServer.port(0));
        this.wiser.start();
        final int port = this.wiser.getServer().getPortAllocated();
        System.out.printf("SMTP server is now listening on port: %d%n", port);
    }

    @AfterMethod
    public void afterEachTestMethod() {

        this.wiser.stop();
        System.out.printf("SMTP server is now stopped%n");
    }

    @Test(
        expectedExceptions = IllegalStateException.class,
        expectedExceptionsMessageRegExp = "^Missing: attachment type, MIME text sub-type$")
    public void EmailMessageTextAttachmentBuilderImp_attach_FailWhenUnset()
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.NO, UsernameAndPassword.NO);

        javaMailSession.emailMessageBuilder()
            .builderForTextAttachment()
            .attachText("blah", EmailMessageAttachmentTextNewLine.UNCHANGED, IsEmptyAllowed.NO);
    }

    @Test(
        expectedExceptions = IllegalStateException.class,
        expectedExceptionsMessageRegExp = "^Missing EmailMessageAddressType\\.FROM$")
    public void failWhenMissingFrom()
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.YES, UsernameAndPassword.NO);

        javaMailSession.emailMessageBuilder().build();
    }

    @Test(
        expectedExceptions = IllegalStateException.class,
        expectedExceptionsMessageRegExp = "^Failed to find any recipient addresses \\(To:, Cc:, Bcc:\\)$")
    public void failWhenZeroRecipients()
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.NO, UsernameAndPassword.NO);

        javaMailSession.emailMessageBuilder()
            .address(EmailMessageAddressType.FROM, EmailMessageAddress.fromEmailAddressOnly("blah@gmail.com"))
            .build();
    }

    @Test(
        expectedExceptions = IllegalStateException.class,
        expectedExceptionsMessageRegExp = "^Missing subject$")
    public void failWhenMissingSubject()
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.YES, UsernameAndPassword.NO);

        final EmailMessageBuilder b =
            javaMailSession.emailMessageBuilder()
                .address(EmailMessageAddressType.FROM, EmailMessageAddress.fromEmailAddressOnly("blah@gmail.com"));

        b.addressSet(EmailMessageAddressListType.TO).add(EmailMessageAddress.fromEmailAddressOnly("blimey@gmail.com"));
        b.build();
    }

    @Test(
        expectedExceptions = IllegalStateException.class,
        expectedExceptionsMessageRegExp = "^Missing body text$")
    public void failWhenMissingBodyText()
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.NO, UsernameAndPassword.NO);

        final EmailMessageBuilder b =
            javaMailSession.emailMessageBuilder()
                .address(EmailMessageAddressType.FROM, EmailMessageAddress.fromEmailAddressOnly("blah@gmail.com"));

        b.addressSet(EmailMessageAddressListType.TO).add(EmailMessageAddress.fromEmailAddressOnly("blimey@gmail.com"));
        b.subject("subject");
        b.build();
    }

    @Test
    public void passWhenZeroAttachments()
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.YES, UsernameAndPassword.NO);

        final EmailMessageAddress ema = EmailMessageAddress.fromEmailAddressOnly("blah@gmail.com");
        final EmailMessageBuilder b =
            javaMailSession.emailMessageBuilder()
                .address(EmailMessageAddressType.FROM, ema);

        b.addressSet(EmailMessageAddressListType.TO).add(ema);

        b.subject("subject");
        b.body(TextMimeSubType.PLAIN, "body");

        final _Data data = _sendAndReceiveEmail(b);

        Assert.assertEquals(data.inMimeMessage.getFrom()[0].toString(), ema.emailAddress);
        Assert.assertEquals(data.inMimeMessage.getRecipients(Message.RecipientType.TO)[0].toString(), ema.emailAddress);
        Assert.assertEquals(data.inMimeMessage.getSubject(), "subject");
        Assert.assertEquals(((MimeMultipart) data.inMimeMessage.getContent()).getBodyPart(0).getContent(),
            "body");
    }

    @Test
    public void insecure_Pass()
    throws Exception {

        _pass(UsernameAndPassword.NO);
    }

    @Test
    public void secure_Pass()
    throws Exception {

        _pass(UsernameAndPassword.YES);
    }

    private void
    _pass(UsernameAndPassword usernameAndPassword)
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.NO, usernameAndPassword);

        final EmailMessageAddress fromEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("from@gmail.com");
        final EmailMessageAddress toEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("to@gmail.com");
//        final EmailMessageAddress ccEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("cc@gmail.com");
//        final EmailMessageAddress bccEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("bcc@gmail.com");

        final EmailMessageBuilder b =
            javaMailSession.emailMessageBuilder()
                .subject("sample subject")
                .body(TextMimeSubType.PLAIN, "body text")
                .address(EmailMessageAddressType.FROM, fromEmailMessageAddress);

        b.addressSet(EmailMessageAddressListType.TO)
            .add(toEmailMessageAddress);
        // These will generated unnecessary extra emails!
//
//        b.addressSet(EmailMessageAddressListType.CC)
//            .add(ccEmailMessageAddress);
//
//        b.addressSet(EmailMessageAddressListType.BCC)
//            .add(bccEmailMessageAddress);

        b.builderForTextAttachment()
            .attachmentFileName("log.txt")
            .textMimeSubType(TextMimeSubType.PLAIN)
            .attachText(String.format("line 1%nline 2%nline 3%n"), EmailMessageAttachmentTextNewLine.WINDOWS, IsEmptyAllowed.NO);

        b.builderForTextAttachment()
            .inline()
            .textMimeSubType(TextMimeSubType.PLAIN)
            .attachText("line4\r\nline5",
                // probably useless, as inline text *appears* to always arrive with Windows new-lines: \r\n
                EmailMessageAttachmentTextNewLine.UNIX,
                IsEmptyAllowed.NO);

        b.builderForBinaryAttachment()
            .attachmentFileName("data.bin")
            .mimeType("application/octet-stream")
            .attachBinaryData("binary data".getBytes(StandardCharsets.UTF_8), IsEmptyAllowed.NO);

        b.builderForBinaryAttachment()
            .inline()
            .mimeType("application/octet-stream")
            .attachBinaryData("binary data2".getBytes(StandardCharsets.UTF_8), IsEmptyAllowed.NO);

        final _Data data = _sendAndReceiveEmail(b);

        Assert.assertEquals(data.inMimeMessage.getFrom(), data.outMimeMessage.getFrom());
        Assert.assertEquals(
            data.inMimeMessage.getRecipients(Message.RecipientType.TO),
            data.outMimeMessage.getRecipients(Message.RecipientType.TO));

        Assert.assertEquals(data.inMimeMessage.getSubject(), data.outMimeMessage.getSubject());

        final MimeMultipart mimeMultipart = (MimeMultipart) data.outMimeMessage.getContent();
        final MimeMultipart mimeMultipart2 = (MimeMultipart) data.inMimeMessage.getContent();
        Assert.assertEquals(mimeMultipart2.getCount(), 5);
        Assert.assertEquals(mimeMultipart2.getCount(), mimeMultipart.getCount());

        final BodyPart bodyPart = mimeMultipart.getBodyPart(0);
        final BodyPart bodyPart2 = mimeMultipart2.getBodyPart(0);

        Assert.assertEquals(bodyPart2.getDisposition(), bodyPart.getDisposition());
        Assert.assertEquals(bodyPart2.getFileName(), bodyPart.getFileName());
        Assert.assertEquals(bodyPart2.getContentType(), bodyPart.getContentType());
        Assert.assertEquals((String) bodyPart2.getContent(), "body text");
        Assert.assertEquals((String) bodyPart2.getContent(), (String) bodyPart.getContent());

        final BodyPart textAttachmentBodyPart = mimeMultipart.getBodyPart(1);
        final BodyPart textAttachmentBodyPart2 = mimeMultipart2.getBodyPart(1);

        Assert.assertEquals(textAttachmentBodyPart2.getDisposition(), MimeBodyPart.ATTACHMENT);
        Assert.assertEquals(textAttachmentBodyPart2.getDisposition(), textAttachmentBodyPart.getDisposition());
        Assert.assertEquals(textAttachmentBodyPart2.getFileName(), "log.txt");
        Assert.assertEquals(textAttachmentBodyPart2.getFileName(), textAttachmentBodyPart.getFileName());
        Assert.assertEquals(textAttachmentBodyPart2.getContentType(), "text/plain; charset=UTF-8; name=log.txt");
        Assert.assertEquals(textAttachmentBodyPart2.getContentType(), textAttachmentBodyPart.getContentType());
        Assert.assertEquals((String) textAttachmentBodyPart2.getContent(), "line 1\r\nline 2\r\nline 3\r\n");
        Assert.assertEquals((String) textAttachmentBodyPart2.getContent(), (String) textAttachmentBodyPart.getContent());

        final BodyPart inlineTextBodyPart = mimeMultipart.getBodyPart(2);
        final BodyPart inlineTextBodyPart2 = mimeMultipart2.getBodyPart(2);

        Assert.assertEquals(inlineTextBodyPart2.getDisposition(), MimeBodyPart.INLINE);
        Assert.assertEquals(inlineTextBodyPart2.getDisposition(), inlineTextBodyPart.getDisposition());
        Assert.assertNull(inlineTextBodyPart2.getFileName());
        Assert.assertEquals(inlineTextBodyPart2.getFileName(), inlineTextBodyPart.getFileName());
        Assert.assertEquals(inlineTextBodyPart2.getContentType(), "text/plain; charset=UTF-8");
        Assert.assertEquals(inlineTextBodyPart2.getContentType(), inlineTextBodyPart.getContentType());
        Assert.assertEquals((String) inlineTextBodyPart.getContent(), "line4\nline5");
        // The "UNIX" new-lines conversion appears to have no effect upon INLINE text.
        Assert.assertEquals((String) inlineTextBodyPart2.getContent(), "line4\r\nline5");

        final BodyPart binaryAttachmentBodyPart = mimeMultipart.getBodyPart(3);
        final BodyPart binaryAttachmentBodyPart2 = mimeMultipart2.getBodyPart(3);

        Assert.assertEquals(binaryAttachmentBodyPart2.getDisposition(), MimeBodyPart.ATTACHMENT);
        Assert.assertEquals(binaryAttachmentBodyPart2.getDisposition(), binaryAttachmentBodyPart.getDisposition());
        Assert.assertEquals(binaryAttachmentBodyPart2.getFileName(), "data.bin");
        Assert.assertEquals(binaryAttachmentBodyPart2.getFileName(), binaryAttachmentBodyPart.getFileName());
        Assert.assertEquals(binaryAttachmentBodyPart2.getContentType(), "application/octet-stream; name=data.bin");
        Assert.assertEquals(binaryAttachmentBodyPart2.getContentType(), binaryAttachmentBodyPart.getContentType());
        {
            final InputStream is = (InputStream) binaryAttachmentBodyPart2.getContent();
            final byte[] byteArr = ByteStreams.toByteArray(is);
            final String s = new String(byteArr, StandardCharsets.UTF_8);
            Assert.assertEquals(s, "binary data");
        }

        final BodyPart inlineBinaryBodyPart = mimeMultipart.getBodyPart(4);
        final BodyPart inlineBinaryBodyPart2 = mimeMultipart2.getBodyPart(4);

        Assert.assertEquals(inlineBinaryBodyPart2.getDisposition(), MimeBodyPart.INLINE);
        Assert.assertEquals(inlineBinaryBodyPart2.getDisposition(), inlineBinaryBodyPart.getDisposition());
        Assert.assertNull(inlineBinaryBodyPart2.getFileName());
        Assert.assertEquals(inlineBinaryBodyPart2.getFileName(), inlineBinaryBodyPart.getFileName());
        Assert.assertEquals(inlineBinaryBodyPart2.getContentType(), "application/octet-stream");
        Assert.assertEquals(inlineBinaryBodyPart2.getContentType(), inlineBinaryBodyPart.getContentType());
        {
            final InputStream is = (InputStream) inlineBinaryBodyPart2.getContent();
            final byte[] byteArr = ByteStreams.toByteArray(is);
            final String s = new String(byteArr, StandardCharsets.UTF_8);
            Assert.assertEquals(s, "binary data2");
        }
    }

    private enum UsernameAndPassword { YES, NO }

    private JavaMailSession
    _createJavaMailSession(AlwaysTrustSSL alwaysTrustSSL,
                           UsernameAndPassword usernameAndPassword) {

        final int port = this.wiser.getServer().getPortAllocated();

        System.out.printf("%s.%s: SMTP server port: %d%n",
            usernameAndPassword.getClass().getSimpleName(), usernameAndPassword.name(), port);

        final JavaMailSessionBuilder javaMailSessionBuilder =
            JavaMailSessionBuilderFactory.INSTANCE.newInstance()
                .host("localhost", alwaysTrustSSL)
                // We need to call this *once* for code coverage! :)
                .port(SmtpPort.MODERN_SECURE)
                .customPort(port);

        if (UsernameAndPassword.YES.equals(usernameAndPassword)) {

            javaMailSessionBuilder.usernameAndPassword("username", "password");
        }
        Assert.assertFalse(javaMailSessionBuilder.properties().isEmpty());
        final JavaMailSession x = javaMailSessionBuilder.build();
        return x;
    }

    private static final class _Data {

        public final MimeMessage outMimeMessage;
        public final byte[] outMimeMessageByteArr;
        public final String outMimeMessageStr;
        public final WiserMessage inWiserMessage;
        public final MimeMessage inMimeMessage;
        public final byte[] inMimeMessageByteArr;
        public final String inMimeMessageStr;

        private _Data(MimeMessage outMimeMessage, WiserMessage inWiserMessage)
        throws Exception {

            this.outMimeMessage = ObjectArgs.checkNotNull(outMimeMessage, "outMimeMessage");
            this.outMimeMessageByteArr = JavaMailMessageService.INSTANCE.getMimeContentAsByteArr(outMimeMessage);
            this.outMimeMessageStr = new String(outMimeMessageByteArr, StandardCharsets.UTF_8);
            this.inWiserMessage = ObjectArgs.checkNotNull(inWiserMessage, "inWiserMessage");
            this.inMimeMessageByteArr = inWiserMessage.getData();
            this.inMimeMessageStr = new String(inMimeMessageByteArr, StandardCharsets.UTF_8);
            this.inMimeMessage =
                JavaMailMessageService.INSTANCE.createMimeMessageFromByteArr(
                    outMimeMessage.getSession(), inMimeMessageByteArr);
        }
    }

    private _Data
    _sendAndReceiveEmail(EmailMessageBuilder b)
    throws Exception {

        final MimeMessage mimeMessage = b.build();

        b.javaMailSession().sendMessage(mimeMessage);

        @EmptyContainerAllowed
        final List<WiserMessage> wiserMessageList = wiser.getMessages();
        Assert.assertEquals(wiserMessageList.size(), 1);  // One message for each entry in To:, Cc:, Bcc:

        final WiserMessage wiserMessage = wiserMessageList.get(0);
        final _Data x = new _Data(mimeMessage, wiserMessage);
        return x;
    }

    @Test
    public void EmailMessageTextAttachmentBuilderImp_attachTextFile_Pass()
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.YES, UsernameAndPassword.NO);

        final EmailMessageAddress fromEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("from@gmail.com");
        final EmailMessageAddress toEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("to@gmail.com");

        final String subject = "sample subject";
        final String bodyText = "body text";

        final EmailMessageBuilder b =
            javaMailSession.emailMessageBuilder()
                .subject(subject)
                .body(TextMimeSubType.PLAIN, bodyText)
                .address(EmailMessageAddressType.FROM, fromEmailMessageAddress);

        b.addressSet(EmailMessageAddressListType.TO)
            .add(toEmailMessageAddress);

        b.headers().put("X-Random", "12345");

        final File filePath =
            File.createTempFile("EmailMessageTextAttachmentBuilderImp_attachTextFile_Pass", ".txt");

        filePath.deleteOnExit();

        final File filePath2 =
            File.createTempFile("EmailMessageTextAttachmentBuilderImp_attachTextFile_Pass2", ".txt");

        filePath2.deleteOnExit();

        final File filePath3 =
            File.createTempFile("EmailMessageTextAttachmentBuilderImp_attachTextFile_Pass3", ".txt");

        filePath3.deleteOnExit();

        final String text = String.format("line 1%nline 2%nline 3%n");
        Files.asCharSink(filePath, StandardCharsets.UTF_8).write(text);

        final String attachmentFileName = "log.txt";

        b.builderForTextAttachment()
            .attachmentFileName(attachmentFileName)
            .textMimeSubType(TextMimeSubType.PLAIN)
            .attachTextFile(
                filePath, StandardCharsets.UTF_8, EmailMessageAttachmentTextNewLine.WINDOWS, IsEmptyAllowed.NO);

        final String html = String.format("<html>%n...%n</html>%n");
        Files.asCharSink(filePath2, StandardCharsets.UTF_8).write(html);

        b.builderForTextAttachment()
            .inline()
            .textMimeSubType(TextMimeSubType.HTML)
            .attachTextFile(
                filePath2, StandardCharsets.UTF_8, EmailMessageAttachmentTextNewLine.UNCHANGED, IsEmptyAllowed.NO);

        Files.asCharSink(filePath3, StandardCharsets.UTF_8).write("");

        b.builderForTextAttachment()
            .inline()
            .textMimeSubType(TextMimeSubType.HTML)
            .attachTextFile(
                filePath3, StandardCharsets.UTF_8, EmailMessageAttachmentTextNewLine.UNCHANGED, IsEmptyAllowed.YES);

        final _Data data = _sendAndReceiveEmail(b);

        Assert.assertEquals(data.inMimeMessage.getHeader("X-Random"), new String[]{ "12345" });

        _assertTextAttachment(
            data, 1,
            EmailMessageAttachmentType.ATTACHMENT, attachmentFileName,
            "text/plain; charset=UTF-8", "line 1\r\nline 2\r\nline 3\r\n");

        _assertTextAttachment(
            data, 2,
            EmailMessageAttachmentType.INLINE, null,
            "text/html; charset=UTF-8", "<html>\r\n...\r\n</html>\r\n");

        _assertTextAttachment(
            data, 3,
            EmailMessageAttachmentType.INLINE, null,
            "text/html; charset=UTF-8", "");
    }

    @Test
    public void EmailMessageTextAttachmentBuilderImp_attachTextInputStream_Pass()
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.NO, UsernameAndPassword.YES);

        final EmailMessageAddress fromEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("from@gmail.com");
        final EmailMessageAddress ccEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("cc@gmail.com");

        final String subject = "sample subject";
        final String bodyText = "body text";

        final EmailMessageBuilder b =
            javaMailSession.emailMessageBuilder()
                .subject(subject)
                .body(TextMimeSubType.PLAIN, bodyText)
                .address(EmailMessageAddressType.FROM, fromEmailMessageAddress);

        b.addressSet(EmailMessageAddressListType.CC)
            .add(ccEmailMessageAddress);

        final File filePath =
            File.createTempFile("EmailMessageTextAttachmentBuilderImp_attachTextFile_Pass", ".txt");

        filePath.deleteOnExit();

        final File filePath2 =
            File.createTempFile("EmailMessageTextAttachmentBuilderImp_attachTextFile_Pass2", ".txt");

        filePath2.deleteOnExit();

        final File filePath3 =
            File.createTempFile("EmailMessageTextAttachmentBuilderImp_attachTextFile_Pass3", ".txt");

        filePath3.deleteOnExit();

        final String text = String.format("line 1%nline 2%nline 3%n");
        Files.asCharSink(filePath, StandardCharsets.UTF_8).write(text);

        final String attachmentFileName = "log.txt";

        final EmailMessageTextAttachmentBuilder b2 =
            b.builderForTextAttachment()
                .attachmentFileName(attachmentFileName)
                .customTextMimeSubType(TextMimeSubType.PLAIN.textMimeSubType)
                .attachTextInputStream(
                    new FileInputStream(filePath),
                    StandardCharsets.UTF_8, EmailMessageAttachmentTextNewLine.WINDOWS, IsEmptyAllowed.NO);

        Assert.assertSame(b2.parent(), b);

        final String html = String.format("<html>%n...%n</html>%n");
        Files.asCharSink(filePath2, StandardCharsets.UTF_8).write(html);

        b.builderForTextAttachment()
            .inline()
            .textMimeSubType(TextMimeSubType.HTML)
            .attachTextInputStream(
                new FileInputStream(filePath2),
                StandardCharsets.UTF_8, EmailMessageAttachmentTextNewLine.UNCHANGED, IsEmptyAllowed.NO);

        Files.asCharSink(filePath3, StandardCharsets.UTF_8).write("");

        b.builderForTextAttachment()
            .inline()
            .textMimeSubType(TextMimeSubType.HTML)
            .attachTextInputStream(
                new FileInputStream(filePath3),
                StandardCharsets.UTF_8, EmailMessageAttachmentTextNewLine.UNCHANGED, IsEmptyAllowed.YES);

        final _Data data = _sendAndReceiveEmail(b);

        _assertTextAttachment(
            data, 1,
            EmailMessageAttachmentType.ATTACHMENT, attachmentFileName,
            "text/plain; charset=UTF-8", "line 1\r\nline 2\r\nline 3\r\n");

        _assertTextAttachment(
            data, 2,
            EmailMessageAttachmentType.INLINE, null,
            "text/html; charset=UTF-8", "<html>\r\n...\r\n</html>\r\n");

        _assertTextAttachment(
            data, 3,
            EmailMessageAttachmentType.INLINE, null,
            "text/html; charset=UTF-8", "");
    }

    @Test
    public void EmailMessageBinaryAttachmentBuilderImp_attachMessage_Pass()
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.YES, UsernameAndPassword.NO);

        final EmailMessageAddress fromEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("from@gmail.com");
        final EmailMessageAddress bccEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("bcc@gmail.com");

        final String subject = "sample subject";
        final String bodyText = "body text";

        final EmailMessageBuilder b =
            javaMailSession.emailMessageBuilder()
                .subject(subject)
                .body(TextMimeSubType.PLAIN, bodyText)
                .address(EmailMessageAddressType.FROM, fromEmailMessageAddress);

        b.addressSet(EmailMessageAddressListType.BCC)
            .add(bccEmailMessageAddress);

        final MimeMessage attachedMimeMessage = new MimeMessage(javaMailSession.session());
        attachedMimeMessage.setSubject("another subject");
        attachedMimeMessage.setText("body text");
        final String attachedMimeMessageStr =
            new String(
                JavaMailMessageService.INSTANCE.getMimeContentAsByteArr(attachedMimeMessage), StandardCharsets.UTF_8);

        final String attachmentFileName = "convo.eml";

        b.builderForBinaryAttachment()
            .attachmentFileName(attachmentFileName)
            .mimeType("application/octet-stream")
            .attachMessage(attachedMimeMessage);

        final MimeMessage inlineMimeMessage = new MimeMessage(javaMailSession.session());
        inlineMimeMessage.setSubject("another subject2");
        inlineMimeMessage.setText("inline body text", StandardCharsets.UTF_8.name(), "plain");
        final String inlineMimeMessageStr =
            new String(
                JavaMailMessageService.INSTANCE.getMimeContentAsByteArr(inlineMimeMessage), StandardCharsets.UTF_8);

        b.builderForBinaryAttachment()
            .inline()
            .mimeType("application/octet-stream")
            .attachMessage(inlineMimeMessage);

        final _Data data = _sendAndReceiveEmail(b);

        _assertBinaryAttachment(
            data, 1,
            EmailMessageAttachmentType.ATTACHMENT, attachmentFileName,
            "application/octet-stream", attachedMimeMessageStr);

        _assertBinaryAttachment(
            data, 2,
            EmailMessageAttachmentType.INLINE, null,
            "application/octet-stream", inlineMimeMessageStr);
    }

    @Test
    public void EmailMessageBinaryAttachmentBuilderImp_attachBinaryFile_Pass()
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.NO, UsernameAndPassword.YES);

        final EmailMessageAddress fromEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("from@gmail.com");
        final EmailMessageAddress toEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("to@gmail.com");

        final String subject = "sample subject";
        final String bodyText = "body text";

        final EmailMessageBuilder b =
            javaMailSession.emailMessageBuilder()
                .subject(subject)
                .body(TextMimeSubType.PLAIN, bodyText)
                .address(EmailMessageAddressType.FROM, fromEmailMessageAddress);

        b.addressSet(EmailMessageAddressListType.TO)
            .add(toEmailMessageAddress);

        final MimeMessage attachedMimeMessage = new MimeMessage(javaMailSession.session());
        attachedMimeMessage.setSubject("another subject");
        attachedMimeMessage.setText("body text");
        final byte[] attachedMimeMessageByteArr =
            JavaMailMessageService.INSTANCE.getMimeContentAsByteArr(attachedMimeMessage);

        final String attachedMimeMessageStr = new String(attachedMimeMessageByteArr, StandardCharsets.UTF_8);

        final File filePath =
            File.createTempFile("EmailMessageBinaryAttachmentBuilderImp_attachBinaryFile_Pass", ".bin");

        filePath.deleteOnExit();

        final File filePath2 =
            File.createTempFile("EmailMessageBinaryAttachmentBuilderImp_attachBinaryFile_Pass2", ".bin");

        filePath2.deleteOnExit();

        final File filePath3 =
            File.createTempFile("EmailMessageBinaryAttachmentBuilderImp_attachBinaryFile_Pass3", ".bin");

        filePath3.deleteOnExit();

        Files.asByteSink(filePath).write(attachedMimeMessageByteArr);

        final String attachmentFileName = "convo.eml";

        final EmailMessageBinaryAttachmentBuilder b2 =
            b.builderForBinaryAttachment()
                .attachmentFileName(attachmentFileName)
                .mimeType("application/octet-stream")
                .attachBinaryFile(filePath, IsEmptyAllowed.NO);

        Assert.assertSame(b2.parent(), b);

        final MimeMessage inlineMimeMessage = new MimeMessage(javaMailSession.session());
        inlineMimeMessage.setSubject("another subject2");
        inlineMimeMessage.setText("inline body text", StandardCharsets.UTF_8.name(), "plain");
        final byte[] inlineMimeMessageByteArr =
            JavaMailMessageService.INSTANCE.getMimeContentAsByteArr(inlineMimeMessage);

        final String inlineMimeMessageStr = new String(inlineMimeMessageByteArr, StandardCharsets.UTF_8);

        Files.asByteSink(filePath2).write(inlineMimeMessageByteArr);

        b.builderForBinaryAttachment()
            .inline()
            .mimeType("application/octet-stream")
            .attachBinaryFile(filePath2, IsEmptyAllowed.NO);

        Files.asByteSink(filePath3).write(new byte[0]);

        b.builderForBinaryAttachment()
            .inline()
            .mimeType("application/octet-stream")
            .attachBinaryFile(filePath3, IsEmptyAllowed.YES);

        final _Data data = _sendAndReceiveEmail(b);

        _assertBinaryAttachment(
            data, 1,
            EmailMessageAttachmentType.ATTACHMENT, attachmentFileName,
            "application/octet-stream", attachedMimeMessageStr);

        _assertBinaryAttachment(
            data, 2,
            EmailMessageAttachmentType.INLINE, null,
            "application/octet-stream", inlineMimeMessageStr);

        _assertBinaryAttachment(
            data, 3,
            EmailMessageAttachmentType.INLINE, null,
            "application/octet-stream", "");
    }

    @Test
    public void EmailMessageBinaryAttachmentBuilderImp_attachBinaryInputStream_Pass()
    throws Exception {

        final JavaMailSession javaMailSession = _createJavaMailSession(AlwaysTrustSSL.YES, UsernameAndPassword.YES);

        final EmailMessageAddress fromEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("from@gmail.com");
        final EmailMessageAddress toEmailMessageAddress = EmailMessageAddress.fromEmailAddressOnly("to@gmail.com");

        final String subject = "sample subject";
        final String bodyText = "body text";

        final EmailMessageBuilder b =
            javaMailSession.emailMessageBuilder()
                .subject(subject)
                .body(TextMimeSubType.PLAIN, bodyText)
                .address(EmailMessageAddressType.FROM, fromEmailMessageAddress);

        b.addressSet(EmailMessageAddressListType.TO)
            .add(toEmailMessageAddress);

        final MimeMessage attachedMimeMessage = new MimeMessage(javaMailSession.session());
        attachedMimeMessage.setSubject("another subject");
        attachedMimeMessage.setText("body text");
        final byte[] attachedMimeMessageByteArr =
            JavaMailMessageService.INSTANCE.getMimeContentAsByteArr(attachedMimeMessage);

        final String attachedMimeMessageStr = new String(attachedMimeMessageByteArr, StandardCharsets.UTF_8);

        final File filePath =
            File.createTempFile("EmailMessageBinaryAttachmentBuilderImp_attachBinaryInputStream_Pass", ".bin");

        filePath.deleteOnExit();

        final File filePath2 =
            File.createTempFile("EmailMessageBinaryAttachmentBuilderImp_attachBinaryInputStream_Pass2", ".bin");

        filePath2.deleteOnExit();

        final File filePath3 =
            File.createTempFile("EmailMessageBinaryAttachmentBuilderImp_attachBinaryInputStream_Pass3", ".bin");

        filePath3.deleteOnExit();

        Files.asByteSink(filePath).write(attachedMimeMessageByteArr);

        final String attachmentFileName = "convo.eml";

        final EmailMessageBinaryAttachmentBuilder b2 =
            b.builderForBinaryAttachment()
                .attachmentFileName(attachmentFileName)
                .mimeType("application/octet-stream")
                .attachBinaryInputStream(new FileInputStream(filePath), IsEmptyAllowed.NO);

        Assert.assertSame(b2.parent(), b);

        final MimeMessage inlineMimeMessage = new MimeMessage(javaMailSession.session());
        inlineMimeMessage.setSubject("another subject2");
        inlineMimeMessage.setText("inline body text", StandardCharsets.UTF_8.name(), "plain");
        final byte[] inlineMimeMessageByteArr =
            JavaMailMessageService.INSTANCE.getMimeContentAsByteArr(inlineMimeMessage);

        final String inlineMimeMessageStr = new String(inlineMimeMessageByteArr, StandardCharsets.UTF_8);

        Files.asByteSink(filePath2).write(inlineMimeMessageByteArr);

        b.builderForBinaryAttachment()
            .inline()
            .mimeType("application/octet-stream")
            .attachBinaryInputStream(new FileInputStream(filePath2), IsEmptyAllowed.NO);

        Files.asByteSink(filePath3).write(new byte[0]);

        b.builderForBinaryAttachment()
            .inline()
            .mimeType("application/octet-stream")
            .attachBinaryInputStream(new FileInputStream(filePath3), IsEmptyAllowed.YES);

        final _Data data = _sendAndReceiveEmail(b);

        _assertBinaryAttachment(
            data, 1,
            EmailMessageAttachmentType.ATTACHMENT, attachmentFileName,
            "application/octet-stream", attachedMimeMessageStr);

        _assertBinaryAttachment(
            data, 2,
            EmailMessageAttachmentType.INLINE, null,
            "application/octet-stream", inlineMimeMessageStr);

        _assertBinaryAttachment(
            data, 3,
            EmailMessageAttachmentType.INLINE, null,
            "application/octet-stream", "");
    }

    private void
    _assertTextAttachment(_Data data,
                          final int bodyPartIndex,
                          EmailMessageAttachmentType attachment,
                          @Nullable
                          String nullableAttachmentFileName,
                          String mimeType,
                          String text)
    throws Exception {

        final MimeMultipart outMimeMultipart = (MimeMultipart) data.outMimeMessage.getContent();
        final MimeMultipart inMimeMultipart = (MimeMultipart) data.inMimeMessage.getContent();

        final BodyPart outTextAttachmentBodyPart = outMimeMultipart.getBodyPart(bodyPartIndex);
        final BodyPart inTextAttachmentBodyPart = inMimeMultipart.getBodyPart(bodyPartIndex);

        Assert.assertEquals(inTextAttachmentBodyPart.getDisposition(), attachment.disposition);
        Assert.assertEquals(inTextAttachmentBodyPart.getDisposition(), outTextAttachmentBodyPart.getDisposition());
        if (null == nullableAttachmentFileName) {
            Assert.assertNull(inTextAttachmentBodyPart.getFileName());
        }
        else {
            Assert.assertEquals(inTextAttachmentBodyPart.getFileName(), nullableAttachmentFileName);
        }
        Assert.assertEquals(inTextAttachmentBodyPart.getFileName(), outTextAttachmentBodyPart.getFileName());
        final String contentType = mimeType + (null == nullableAttachmentFileName ? "" : "; name=" + nullableAttachmentFileName);
        Assert.assertEquals(inTextAttachmentBodyPart.getContentType(), contentType);
        Assert.assertEquals(inTextAttachmentBodyPart.getContentType(), outTextAttachmentBodyPart.getContentType());
        Assert.assertEquals((String) inTextAttachmentBodyPart.getContent(), text);
    }

    private void
    _assertBinaryAttachment(_Data data,
                            final int bodyPartIndex,
                            EmailMessageAttachmentType attachment,
                            @Nullable
                            String nullableAttachmentFileName,
                            String mimeType,
                            String text)
    throws Exception {

        final MimeMultipart outMimeMultipart = (MimeMultipart) data.outMimeMessage.getContent();
        final MimeMultipart inMimeMultipart = (MimeMultipart) data.inMimeMessage.getContent();

        final BodyPart outTextAttachmentBodyPart = outMimeMultipart.getBodyPart(bodyPartIndex);
        final BodyPart inTextAttachmentBodyPart = inMimeMultipart.getBodyPart(bodyPartIndex);

        Assert.assertEquals(inTextAttachmentBodyPart.getDisposition(), attachment.disposition);
        Assert.assertEquals(inTextAttachmentBodyPart.getDisposition(), outTextAttachmentBodyPart.getDisposition());
        if (null == nullableAttachmentFileName) {
            Assert.assertNull(inTextAttachmentBodyPart.getFileName());
        }
        else {
            Assert.assertEquals(inTextAttachmentBodyPart.getFileName(), nullableAttachmentFileName);
        }
        Assert.assertEquals(inTextAttachmentBodyPart.getFileName(), outTextAttachmentBodyPart.getFileName());
        final String contentType = mimeType + (null == nullableAttachmentFileName ? "" : "; name=" + nullableAttachmentFileName);
        Assert.assertEquals(inTextAttachmentBodyPart.getContentType(), contentType);
        Assert.assertEquals(inTextAttachmentBodyPart.getContentType(), outTextAttachmentBodyPart.getContentType());
        {
            final InputStream is = (InputStream) inTextAttachmentBodyPart.getContent();
            final byte[] byteArr = ByteStreams.toByteArray(is);
            final String s = new String(byteArr, StandardCharsets.UTF_8);
            Assert.assertEquals(s, text);
        }
    }
}
