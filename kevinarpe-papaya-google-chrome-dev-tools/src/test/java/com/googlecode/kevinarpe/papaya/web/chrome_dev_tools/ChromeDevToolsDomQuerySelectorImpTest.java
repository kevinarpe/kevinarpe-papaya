package com.googlecode.kevinarpe.papaya.web.chrome_dev_tools;

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

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.annotation.Blocking;
import com.googlecode.kevinarpe.papaya.annotation.DebugBreakpoint;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrowerImpl;
import com.googlecode.kevinarpe.papaya.function.ThrowingConsumer;
import com.googlecode.kevinarpe.papaya.function.count.AtLeastCountMatcher;
import com.googlecode.kevinarpe.papaya.function.count.ExactlyCountMatcher;
import com.googlecode.kevinarpe.papaya.string.ThrowingMessageFormatterImpl;
import com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.util.TestHttpServer;
import com.googlecode.kevinarpe.papaya.web.chrome_dev_tools.util.TestHttpServerImp;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.annotation.concurrent.GuardedBy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ChromeDevToolsDomQuerySelectorImpTest
extends ChromeDevToolsTest {

    private static final String INPUT_TEXT_ID = "input_text";
    private static final String BUTTON_ID = "button";

    private static final String RESPONSE_HTML = String.format(""
            + "<!DOCTYPE html>%n"
            + "<html lang=\"en\">%n"
            + "<head>%n"
            + "    <meta charset=\"utf-8\">%n"
            + "    <title>Sample Web Page</title>%n"
            + "</head>%n"
            + "<body style=\"font-family: monospace\">%n"
            + "Input: <input id=\"%s\" type=\"text\" size=\"64\" autofocus=\"true\" style=\"font-family: monospace\">%n"
            + "<br><button id=\"%s\" type=\"button\">Click Me</button>%n"
            + "</body>%n"
            + "</html>%n",
        INPUT_TEXT_ID,
        BUTTON_ID);

    private TestHttpServer testHttpServer;
    private ServerSocket serverSocket;
    private String testHttpServerUrl;
    private Thread testHttpServerAcceptThread;
    private final Object lock = new Object();
    @GuardedBy("lock")
    private boolean isAcceptDone;

    @BeforeMethod
    public void beforeEachTestMethod2()
    throws Exception {

        final ExceptionThrower exceptionThrower = new ExceptionThrowerImpl(ThrowingMessageFormatterImpl.INSTANCE);
        this.testHttpServer = new TestHttpServerImp(exceptionThrower);
        this.serverSocket = testHttpServer.bindWithAutoAllocPort();

        this.testHttpServerUrl = String.format("http://localhost:%d/", serverSocket.getLocalPort());
        System.out.printf("Say hello here: %s%n", this.testHttpServerUrl);

        this.testHttpServerAcceptThread =
            new Thread(
                () -> _testHttpServerAccept(),
                "testHttpServerAcceptThread");

        this.testHttpServerAcceptThread.start();
        this.isAcceptDone = false;
    }

    @AfterMethod
    public void afterEachTestMethod2()
    throws Exception {

        testHttpServer.close();
        System.out.printf("Joining thread [%s]...%n", testHttpServerAcceptThread.getName());
        testHttpServerAcceptThread.join();
        System.out.printf("Done%n");
    }

    @Blocking
    private void
    _testHttpServerAccept() {

        try {
            // @Blocking
            testHttpServer.accept(new ThrowingConsumer<Socket>() {
                @Override
                public void accept(Socket socket)
                throws Exception {

                    TestHttpServerImp.staticReadGetRequest(socket);
                    TestHttpServerImp.staticWriteHtmlResponse(socket, RESPONSE_HTML);
                    synchronized (lock) {
                        isAcceptDone = true;
                        lock.notify();
                    }
                }
            });
        }
        catch (Exception e) {
            // Note: On intentionally failing tests below, exceptions may be caught here
            // ... but there are safe to ignore. :)
            throw new RuntimeException(e);
        }
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^Parent node is unset$")
    public void failWhenParentNodeIsUnset()
    throws Exception {

        appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
            .expectedCount(ExactlyCountMatcher.ONE)
            .querySelectorAll("blah");
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^Expected count is unset$")
    public void failWhenExpectedCountIsUnset()
    throws Exception {

        appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
            .parentNodeIsDocument()
            .querySelectorAll("blah");
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^expectedCountMatcher is already set: .*$")
    public void failWhenExpectedCountIsNotOneButQuerySelectedExactlyOne()
    throws Exception {

        appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
            .parentNodeIsDocument()
            .expectedCount(AtLeastCountMatcher.ONE)
            .querySelectorExactlyOne("blah");
    }

    @Test(
        expectedExceptions = Exception.class,
        expectedExceptionsMessageRegExp = "^.*Expected exactly 7 node\\(s\\), but found 1 node\\(s\\)$")
    public void failWhenExpectedCountDoesNotMatch()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
            .parentNodeIsDocument()
            .expectedCount(new ExactlyCountMatcher(7))  // Correct count is one (1)
            .querySelectorAll("#" + INPUT_TEXT_ID);
    }

    @Test
    public void parentNodeIsDocument_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        // Ex: " !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~"
        final String inputText = _createInputText();

        final String outputText =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .awaitQuerySelectorExactlyOneThenRun(
                    "#" + INPUT_TEXT_ID, appContext().basicFiveSecondRetryStrategyFactory,
                    (ChromeDevToolsDomNode n) -> n.focus())
                .sendKeys(inputText)
                .evaluateJavaScriptExpressionWithDollarZero(
                    "$0.value",
                    ChromeDevToolsJavaScriptRemoteObjectType.STRING,
                    IsNullResultAllowed.NO);

        Assert.assertEquals(outputText, inputText);
    }

    @Blocking
    private void
    _awaitAcceptDone()
    throws Exception {

        chrome().chromeTab0.getPage().navigate(testHttpServerUrl);

        synchronized (lock) {
            while (false == isAcceptDone) {
                // @Blocking
                lock.wait();
                @DebugBreakpoint
                int dummy = 1;
            }
            @DebugBreakpoint
            int dummy = 1;
        }
        // Intentional: Find the HTMLInputElement and focus right away.  Why?
        // We need to be sure the page is fully rendered before we start testing.
        appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
            .parentNodeIsDocument()
            .awaitQuerySelectorExactlyOneThenRun(
                "#" + INPUT_TEXT_ID, appContext().basicFiveSecondRetryStrategyFactory,
                (ChromeDevToolsDomNode n) -> n.focus());

        _assertDocumentOuterHTML();
    }

    /*
RESPONSE_HTML:
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Sample Web Page</title>
</head>
<body style="font-family: monospace">
Input: <input id="input_text" type="text" size="64" autofocus="true" style="font-family: monospace">
<br><button id="button" type="button">Click Me</button>
</body>
</html>
--------------------------------
html:
<!DOCTYPE html><html lang="en"><head>
    <meta charset="utf-8">
    <title>Sample Web Page</title>
</head>
<body style="font-family: monospace">
Input: <input id="input_text" type="text" size="64" autofocus="true" style="font-family: monospace">
<br><button id="button" type="button">Click Me</button>


</body></html>
     */
    private void _assertDocumentOuterHTML()
    throws Exception {

        final String html = chrome().chromeTab0.getDocumentOuterHTML();
        // See comment above to understand why we need to remove all whitespace before assertEquals().
        final Pattern whitespacePattern = Pattern.compile("\\s");
        final String html2 = whitespacePattern.matcher(html).replaceAll("");
        final String expectedHtml = whitespacePattern.matcher(RESPONSE_HTML).replaceAll("");
        Assert.assertEquals(html2, expectedHtml);
    }

    private String
    _createInputText() {

        final StringBuilder sb = new StringBuilder();
        for (char ch = (char) 32; ch <= (char) 126; ++ch) {

            sb.append(ch);
        }
        final String x = sb.toString();
        // Ex: " !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~"
        return x;
    }

    @Test
    public void parentNodeIsBody_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final int bodyNodeId = _awaitBodyNodeId();

        // Ex: " !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~"
        final String inputText = _createInputText();

        final String outputText =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeId(bodyNodeId)
                .querySelectorExactlyOne("#" + INPUT_TEXT_ID)
                .sendKeys(inputText)
                .evaluateJavaScriptExpressionWithDollarZero(
                    "$0.value",
                    ChromeDevToolsJavaScriptRemoteObjectType.STRING,
                    IsNullResultAllowed.NO);

        Assert.assertEquals(outputText, inputText);
    }

    @Test
    public void querySelectorExactlyOne_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final ChromeDevToolsDomNode domNode =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .querySelectorExactlyOne("#" + INPUT_TEXT_ID);

        Assert.assertNotNull(domNode);
    }

    @Test
    public void awaitQuerySelectorExactlyOne_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final ChromeDevToolsDomNode domNode =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorExactlyOne("#" + INPUT_TEXT_ID,
                    appContext().basicFiveSecondRetryStrategyFactory);

        Assert.assertNotNull(domNode);
    }

    @Test
    public void awaitQuerySelectorExactlyOneThenRun_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final ChromeDevToolsDomNode domNode =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorExactlyOneThenRun("#" + INPUT_TEXT_ID,
                    appContext().basicFiveSecondRetryStrategyFactory,
                    (ChromeDevToolsDomNode n) -> n.focus());

        Assert.assertNotNull(domNode);
    }

    @Test
    public void awaitQuerySelectorExactlyOneThenRun_PassOnRetry()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final int[] indexRef = {-1};

        final ChromeDevToolsDomNode domNode =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorExactlyOneThenRun("#" + INPUT_TEXT_ID,
                    appContext().basicFiveSecondRetryStrategyFactory,

                    (ChromeDevToolsDomNode n) -> {

                        ++(indexRef[0]);

                        switch (indexRef[0]) {
                            case 0: {
                                throw new Exception(ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);
                            }
                            case 1: {
                                n.focus();
                                break;
                            }
                            default: {
                                throw new IllegalStateException("Internal error: Missing switch case: " + indexRef[0]);
                            }
                        }
                    });

        Assert.assertNotNull(domNode);
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^always fail$")
    public void awaitQuerySelectorExactlyOneThenRun_FailWhenRun()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
            .parentNodeIsDocument()
            .expectedCount(ExactlyCountMatcher.ONE)
            .awaitQuerySelectorExactlyOneThenRun("#" + INPUT_TEXT_ID,
                appContext().basicFiveSecondRetryStrategyFactory,

                (ChromeDevToolsDomNode n) -> {
                    throw new Exception("always fail");
                });
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^always fail$")
    public void awaitQuerySelectorExactlyOneThenRun_FailOnRetry()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final int[] indexRef = {-1};

        try {
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorExactlyOneThenRun("#" + INPUT_TEXT_ID,
                    appContext().basicFiveSecondRetryStrategyFactory,

                    (ChromeDevToolsDomNode n) -> {

                        ++(indexRef[0]);

                        switch (indexRef[0]) {
                            case 0: {
                                throw new Exception(ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);
                            }
                            case 1: {
                                throw new Exception("always fail");
                            }
                            default: {
                                throw new IllegalStateException("Internal error: Missing switch case: " + indexRef[0]);
                            }
                        }
                    });
        }
        catch (Exception e) {

            @EmptyContainerAllowed
            final Throwable[] suppressedArr = e.getSuppressed();
            Assert.assertEquals(suppressedArr.length, 1);
            Assert.assertEquals(suppressedArr[0].getMessage(),
                ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);

            throw e;
        }
    }

    @Test
    public void awaitQuerySelectorExactlyOneThenCall_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final String outerHtml =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorExactlyOneThenCall("#" + INPUT_TEXT_ID,
                    appContext().basicFiveSecondRetryStrategyFactory,
                    (ChromeDevToolsDomNode n) -> n.getOuterHTML());

        Assert.assertEquals(outerHtml,
            "<input id=\"input_text\" type=\"text\" size=\"64\" autofocus=\"true\" style=\"font-family: monospace\">");
    }

    @Test
    public void awaitQuerySelectorExactlyOneThenCall_PassOnRetry()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final int[] indexRef = {-1};

        final String outerHtml =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorExactlyOneThenCall("#" + INPUT_TEXT_ID,
                    appContext().basicFiveSecondRetryStrategyFactory,

                    (ChromeDevToolsDomNode n) -> {

                        ++(indexRef[0]);

                        switch (indexRef[0]) {
                            case 0: {
                                throw new Exception(ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);
                            }
                            case 1: {
                                final String z = n.getOuterHTML();
                                return z;
                            }
                            default: {
                                throw new IllegalStateException("Internal error: Missing switch case: " + indexRef[0]);
                            }
                        }
                    });

        Assert.assertEquals(outerHtml,
            "<input id=\"input_text\" type=\"text\" size=\"64\" autofocus=\"true\" style=\"font-family: monospace\">");
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^always fail$")
    public void awaitQuerySelectorExactlyOneThenCall_FailWhenCall()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
            .parentNodeIsDocument()
            .expectedCount(ExactlyCountMatcher.ONE)
            .awaitQuerySelectorExactlyOneThenCall("#" + INPUT_TEXT_ID,
                appContext().basicFiveSecondRetryStrategyFactory,

                (ChromeDevToolsDomNode n) -> {
                    throw new Exception("always fail");
                });
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^always fail$")
    public void awaitQuerySelectorExactlyOneThenCall_FailOnRetry()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final int[] indexRef = {-1};

        try {
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorExactlyOneThenCall("#" + INPUT_TEXT_ID,
                    appContext().basicFiveSecondRetryStrategyFactory,

                    (ChromeDevToolsDomNode n) -> {

                        ++(indexRef[0]);

                        switch (indexRef[0]) {
                            case 0: {
                                throw new Exception(ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);
                            }
                            case 1: {
                                throw new Exception("always fail");
                            }
                            default: {
                                throw new IllegalStateException("Internal error: Missing switch case: " + indexRef[0]);
                            }
                        }
                    });
        }
        catch (Exception e) {

            @EmptyContainerAllowed
            final Throwable[] suppressedArr = e.getSuppressed();
            Assert.assertEquals(suppressedArr.length, 1);
            Assert.assertEquals(suppressedArr[0].getMessage(),
                ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);

            throw e;
        }
    }

    @Test
    public void querySelectorAll_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        @Blocking
        final int bodyNodeId = _awaitBodyNodeId();

        final ImmutableList<ChromeDevToolsDomNode> domNodeList =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeId(bodyNodeId)
                .expectedCount(ExactlyCountMatcher.ONE)
                .querySelectorAll("#" + INPUT_TEXT_ID);

        Assert.assertEquals(domNodeList.size(), 1);
    }

    @Blocking
    private int
    _awaitBodyNodeId()
    throws Exception {

        final ChromeDevToolsDomNode bodyDomNode =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .awaitQuerySelectorExactlyOne("body", appContext().basicFiveSecondRetryStrategyFactory);

        Assert.assertSame(bodyDomNode.chromeTab(), chrome().chromeTab0);
        @Blocking
        final int x = bodyDomNode.nodeId();
        return x;
    }

    @Test
    public void awaitQuerySelectorAll_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final ImmutableList<ChromeDevToolsDomNode> domNodeList =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorAll("#" + INPUT_TEXT_ID, appContext().basicFiveSecondRetryStrategyFactory);

        Assert.assertEquals(domNodeList.size(), 1);
    }

    @Test
    public void querySelectorByIndex_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        @Blocking
        final int bodyNodeId = _awaitBodyNodeId();

        final ChromeDevToolsDomNode domNode =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeId(bodyNodeId)
                .expectedCount(ExactlyCountMatcher.ONE)
                .querySelectorByIndex("#" + INPUT_TEXT_ID, 0);

        Assert.assertNotNull(domNode);
    }

    @Test
    public void awaitQuerySelectorByIndex_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final ChromeDevToolsDomNode domNode =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorByIndex(
        "#" + INPUT_TEXT_ID, 0, appContext().basicFiveSecondRetryStrategyFactory);

        Assert.assertNotNull(domNode);
    }

    @Test
    public void awaitQuerySelectorByIndexThenRun_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final ChromeDevToolsDomNode domNode =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorByIndexThenRun(
                    "#" + INPUT_TEXT_ID, 0, appContext().basicFiveSecondRetryStrategyFactory,
                    (ChromeDevToolsDomNode n) -> n.focus());

        // Dummy: I need to assert *something* here...
        Assert.assertNotNull(domNode);

        domNode.awaitFocus(appContext().basicFiveSecondRetryStrategyFactory);
        domNode.click();
    }

    @Test
    public void awaitQuerySelectorByIndexThenRun_PassOnRetry()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final int[] indexRef = {-1};

        final ChromeDevToolsDomNode domNode =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorByIndexThenRun(
                    "#" + INPUT_TEXT_ID, 0, appContext().basicFiveSecondRetryStrategyFactory,

                    (ChromeDevToolsDomNode n) -> {

                        ++(indexRef[0]);

                        switch (indexRef[0]) {
                            case 0: {
                                throw new Exception(ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);
                            }
                            case 1: {
                                n.focus();
                                break;
                            }
                            default: {
                                throw new IllegalStateException("Internal error: Missing switch case: " + indexRef[0]);
                            }
                        }
                    });

        Assert.assertNotNull(domNode);
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^always fail$")
    public void awaitQuerySelectorByIndexThenRun_FailWhenRun()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
            .parentNodeIsDocument()
            .expectedCount(ExactlyCountMatcher.ONE)
            .awaitQuerySelectorByIndexThenRun(
                "#" + INPUT_TEXT_ID, 0, appContext().basicFiveSecondRetryStrategyFactory,

                (ChromeDevToolsDomNode n) -> {
                    throw new Exception("always fail");
                });
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^always fail$")
    public void awaitQuerySelectorByIndexThenRun_FailOnRetry()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final int[] indexRef = {-1};

        try {
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorByIndexThenRun(
                    "#" + INPUT_TEXT_ID, 0, appContext().basicFiveSecondRetryStrategyFactory,

                    (ChromeDevToolsDomNode n) -> {

                        ++(indexRef[0]);

                        switch (indexRef[0]) {
                            case 0: {
                                throw new Exception(ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);
                            }
                            case 1: {
                                throw new Exception("always fail");
                            }
                            default: {
                                throw new IllegalStateException("Internal error: Missing switch case: " + indexRef[0]);
                            }
                        }
                    });
        }
        catch (Exception e) {

            @EmptyContainerAllowed
            final Throwable[] suppressedArr = e.getSuppressed();
            Assert.assertEquals(suppressedArr.length, 1);
            Assert.assertEquals(suppressedArr[0].getMessage(),
                ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);

            throw e;
        }
    }

    @Test
    public void awaitQuerySelectorByIndexThenCall_Pass()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final String outerHtml =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorByIndexThenCall(
                    "#" + INPUT_TEXT_ID, 0, appContext().basicFiveSecondRetryStrategyFactory,
                    (ChromeDevToolsDomNode n) -> n.getOuterHTML());

        Assert.assertEquals(outerHtml,
            "<input id=\"input_text\" type=\"text\" size=\"64\" autofocus=\"true\" style=\"font-family: monospace\">");
    }

    @Test
    public void awaitQuerySelectorByIndexThenCall_PassOnRetry()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final int[] indexRef = {-1};

        final String outerHtml =
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorByIndexThenCall(
                    "#" + INPUT_TEXT_ID, 0, appContext().basicFiveSecondRetryStrategyFactory,

                    (ChromeDevToolsDomNode n) -> {

                        ++(indexRef[0]);

                        switch (indexRef[0]) {
                            case 0: {
                                throw new Exception(ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);
                            }
                            case 1: {
                                final String z = n.getOuterHTML();
                                return z;
                            }
                            default: {
                                throw new IllegalStateException("Internal error: Missing switch case: " + indexRef[0]);
                            }
                        }
                    });

        Assert.assertEquals(outerHtml,
            "<input id=\"input_text\" type=\"text\" size=\"64\" autofocus=\"true\" style=\"font-family: monospace\">");
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^always fail$")
    public void awaitQuerySelectorByIndexThenCall_FailWhenCall()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
            .parentNodeIsDocument()
            .expectedCount(ExactlyCountMatcher.ONE)
            .awaitQuerySelectorByIndexThenCall(
                "#" + INPUT_TEXT_ID, 0, appContext().basicFiveSecondRetryStrategyFactory,

                (ChromeDevToolsDomNode n) -> {
                    throw new Exception("always fail");
                });
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^always fail$")
    public void awaitQuerySelectorByIndexThenCall_FailOnRetry()
    throws Exception {

        // @Blocking
        _awaitAcceptDone();

        final int[] indexRef = {-1};

        try {
            appContext().chromeDevToolsDomQuerySelectorFactory.newInstance(chrome().chromeTab0)
                .parentNodeIsDocument()
                .expectedCount(ExactlyCountMatcher.ONE)
                .awaitQuerySelectorByIndexThenCall(
                    "#" + INPUT_TEXT_ID, 0, appContext().basicFiveSecondRetryStrategyFactory,

                    (ChromeDevToolsDomNode n) -> {

                        ++(indexRef[0]);

                        switch (indexRef[0]) {
                            case 0: {
                                throw new Exception(ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);
                            }
                            case 1: {
                                throw new Exception("always fail");
                            }
                            default: {
                                throw new IllegalStateException("Internal error: Missing switch case: " + indexRef[0]);
                            }
                        }
                    });
        }
        catch (Exception e) {

            @EmptyContainerAllowed
            final Throwable[] suppressedArr = e.getSuppressed();
            Assert.assertEquals(suppressedArr.length, 1);
            Assert.assertEquals(suppressedArr[0].getMessage(),
                ChromeDevToolsDomQuerySelectorImp.COULD_NOT_FIND_NODE_WITH_GIVEN_ID);

            throw e;
        }
    }
}
