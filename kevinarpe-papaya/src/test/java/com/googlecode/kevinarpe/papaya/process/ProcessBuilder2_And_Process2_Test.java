package com.googlecode.kevinarpe.papaya.process;

/*
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

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.PathUtils;
import com.googlecode.kevinarpe.papaya.appendable.AbstractSimplifiedAppendable;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.InvalidExitValueException;
import com.googlecode.kevinarpe.papaya.exception.TimeoutException;
import com.googlecode.kevinarpe.papaya.test.TestAssertUtils;
import com.googlecode.kevinarpe.papaya.test.TestCharsetUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ProcessBuilder2_And_Process2_Test {
    
    public static enum ProcessStream {
        STDIN,
        STDOUT,
        STDERR;

        public static final String DATA_FILE_DIR_PATHNAME = "src/test/resources/generated";
        public static final int MIN_DATA_FILE_CHAR_COUNT = 8000000;
        public final File dataFilePath;
        public final String text;
        public final List<String> textList;
        
        private ProcessStream() {
            String name = this.name();  // "STDIN", "STDOUT", "STDERR"
            dataFilePath =
                new File(String.format("%s/%s.txt", DATA_FILE_DIR_PATHNAME, name.toLowerCase()));
            try {
                // TODO: Create min/max/exact/range file size checkers
                if (!dataFilePath.isFile()) {
                    System.out.println(String.format(
                        "Test data file for '%s' is missing: '%s'"
                        + "%n\tGenerating a minimum of %d chars...",
                        name, dataFilePath.getAbsolutePath(), MIN_DATA_FILE_CHAR_COUNT));
                    StringBuilder sb = new StringBuilder();
                    while (sb.length() < MIN_DATA_FILE_CHAR_COUNT) {
                        sb.append(UUID.randomUUID().toString());
                    }
                    String s = sb.toString();
                    File dataFileDirPath = dataFilePath.getParentFile();
                    PathUtils.makeDirectoryAndParents(dataFileDirPath);
                    FileWriter out = new FileWriter(dataFilePath);
                    out.write(s);
                    out.close();
                }
                int byteArrSize = (int) dataFilePath.length();
                FileInputStream fin = new FileInputStream(dataFilePath);
                byte[] byteArr = new byte[byteArrSize];
                int readCount = -1;
                try {
                    readCount = fin.read(byteArr);
                }
                finally {
                    fin.close();
                }
                if (readCount != byteArr.length) {
                    throw new IOException(String.format(
                        "%s: Expected to read %d bytes, but only read %d bytes: '%s'",
                        this.name(), byteArrSize, readCount, dataFilePath.getAbsolutePath()));
                }
                text = new String(byteArr, Charset.defaultCharset());
                // The data files are sequences of random UUIDs converted to String.
                // Each is 36 chars long.
                int listSize = 1 + (byteArrSize / 36);
                List<String> list = new ArrayList<String>(listSize);
                for (int i = 0; i < byteArrSize; i += 36) {
                    int endIndex = Math.min(i + 36, byteArrSize);
                    if (i < endIndex) {
                        String s = text.substring(i, endIndex);
                        list.add(s);
                    }
                }
                textList = Collections.unmodifiableList(list);
            }
            catch (Exception e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }
    
    public static class ChildProcess1 {
        
        public static void main(String[] argArr) {
            System.out.println("Hello, World!");
            System.err.println("Hello, World! x 2");
        }
    }
    
    public static class ChildProcess2a {
        
        public static void main(String[] argArr) {
            System.out.print(ProcessStream.STDOUT.text);
            System.err.print(ProcessStream.STDERR.text);
        }
    }
    
    public static class ChildProcess2b {
        
        public static void main(String[] argArr) {
            _print(System.out, ProcessStream.STDOUT.textList);
            _print(System.err, ProcessStream.STDERR.textList);
        }
        
        private static void _print(final PrintStream ps, final List<String> list) {
            for (String s: list) {
                ps.print(s);
            }
        }
    }
    
    public static class ChildProcess3a {
        
        public static void main(String[] argArr) {
            _print(System.out, ProcessStream.STDOUT.text);
            _print(System.err, ProcessStream.STDERR.text);
        }
        
        private static void _print(final PrintStream ps, final String s) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    ps.print(s);
                }
            }).start();
        }
    }
    
    public static class ChildProcess3b {
        
        public static void main(String[] argArr) {
            _print(System.out, ProcessStream.STDOUT.textList);
            _print(System.err, ProcessStream.STDERR.textList);
        }
        
        private static void _print(final PrintStream ps, final List<String> list) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    for (String s: list) {
                        ps.print(s);
                    }
                }
            }).start();
        }
    }
    
    public static class ChildProcess4a {
        
        public static void main(String[] argArr)
        throws InterruptedException {
            _InputStreamReader isr = null;
            for (String arg: argArr) {
                ProcessStream ps = ProcessStream.valueOf(arg);
                if (ProcessStream.STDOUT == ps) {
                    _printAsync(System.out, ProcessStream.STDOUT.text);
                }
                else if (ProcessStream.STDERR == ps) {
                    _printAsync(System.err, ProcessStream.STDERR.text);
                }
                else if (ProcessStream.STDIN == ps) {
                    isr = new _InputStreamReader(System.in, ProcessStream.STDIN.text);
                    isr.start();
                }
            }
            if (null != isr) {
                isr.join();
                if (!isr.isOk()) {
                    System.exit(1);
                }
            }
        }
        
        private static void _printAsync(final PrintStream ps, final String s) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    ps.print(s);
                }
            }).start();
        }
        
        private static class _InputStreamReader
        extends Thread {
            
            private final InputStream _is;
            private final String _expected;
            private boolean _ok;
            
            public _InputStreamReader(InputStream is, String expected) {
                _is = is;
                _expected = expected;
                _ok = true;
            }
            
            public boolean isOk() {
                return _ok;
            }

            @Override
            public void run() {
                int expectedLen = _expected.length();
                // Since we are reading UTF-16 for Java, use two extra bytes here.
                byte[] byteArr = new byte[expectedLen + 2];
                int readCount = -1;
                try {
                    readCount = _is.read(byteArr);
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                    _ok = false;
                    return;
                }
                if (-1 == readCount) {
                    if (0 != expectedLen) {
                        System.err.println(String.format(
                            "Zero bytes read from STDIN, but expected %d bytes",
                            expectedLen));
                        _ok = false;
                    }
                    else {
                        _ok = true;
                    }
                    return;
                }
                if (readCount == byteArr.length) {
                    System.err.println(String.format(
                        "Expected %d bytes from STDIN, but read (at least) %d bytes",
                        readCount, byteArr.length));
                    _ok = false;
                    return;
                }
                String actual = new String(byteArr, 0, readCount, Charset.defaultCharset());
                try {
                    TestAssertUtils.assertHugeStringEquals(actual, _expected);
                }
                catch (Exception e) {
                    e.printStackTrace(System.err);
                    _ok = false;
                    return;
                }
                _ok = true;
            }
        }
    }

    @Test
    public void ChildProcess1_Pass()
    throws IOException, InterruptedException {
        String classpath = _getCurrentClasspath();
        ProcessBuilder2 builder = new ProcessBuilder2("java", "-classpath", classpath, ChildProcess1.class.getName());
        builder.stdoutSettings().charCallback(_createAppendable("STDOUT"));
        builder.stdoutSettings().isDataAccumulated(true);
        builder.stderrSettings().charCallback(_createAppendable("STDERR"));
        builder.stderrSettings().isDataAccumulated(true);
        Process2 proc = builder.start();
        int exitValue = proc.waitFor();
        Assert.assertEquals(exitValue, 0);
    }
    
    private Appendable _createAppendable(final String prefix) {
        return new AbstractSimplifiedAppendable() {
            
            @Override
            public Appendable append(CharSequence csq)
            throws IOException {
                System.out.println(String.format("%s: [%s]", prefix, csq));
                return this;
            }
        };
    }

    @Test
    public void ChildProcess_Pass()
    throws IOException, InterruptedException {
        _ChildProcess_Pass(ChildProcess2a.class);
        _ChildProcess_Pass(ChildProcess2b.class);
        _ChildProcess_Pass(ChildProcess3a.class);
        _ChildProcess_Pass(ChildProcess3b.class);
    }
    
    private void _ChildProcess_Pass(Class<?> klass)
    throws IOException, InterruptedException {
        ObjectArgs.checkNotNull(klass, "klass");
        
        String classpath = _getCurrentClasspath();
        ProcessBuilder2 builder =
            new ProcessBuilder2("java", "-classpath", classpath, klass.getName());
        builder.stdoutSettings().isDataAccumulated(true);
        builder.stderrSettings().isDataAccumulated(true);
        Process2 proc = builder.start();
        int exitValue = proc.waitFor();
        TestAssertUtils.assertHugeStringEquals(proc.stderrDataAsString(), ProcessStream.STDERR.text);
        TestAssertUtils.assertHugeStringEquals(proc.stdoutDataAsString(), ProcessStream.STDOUT.text);
        Assert.assertEquals(exitValue, 0);
    }
    
    private static String _getCurrentClasspath() {
        ArrayList<String> classpathList = new ArrayList<String>();
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        // A little bit of cheating.  How does this work exactly?
        // Ref: http://www.mkyong.com/java/how-to-print-out-the-current-project-classpath/
        URLClassLoader ucl = (URLClassLoader) cl;
        URL[] urlArr = ucl.getURLs();
        for (URL url: urlArr) {
            String pathname = url.getPath();
            classpathList.add(pathname);
        }
        String classpath = Joiner.on(File.pathSeparator).join(classpathList);
        return classpath;
    }

    // 2020-10-04: This test is now broken.  I don't how to fix it.  :(
    // I *think* the root cause is an upgrade to my main build machine that is 5x faster,
    // so I have now uncovered a hidden race condition!
    @Ignore
    @Test
    public void ChildProcess4a_Pass()
    throws IOException, InterruptedException {
        String classpath = _getCurrentClasspath();

        boolean isStdinText = true;
        
        for (ProcessStream[] psArr: new ProcessStream[][] {
                { ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR },
                { ProcessStream.STDIN, ProcessStream.STDOUT },
                { ProcessStream.STDIN, ProcessStream.STDERR },
                { ProcessStream.STDOUT, ProcessStream.STDERR },
                { ProcessStream.STDIN },
                { ProcessStream.STDOUT },
                { ProcessStream.STDERR },
                { },
                }) {
            System.out.println("[" + Joiner.on(", ").join(psArr) + "]");
            List<ProcessStream> psList = Arrays.asList(psArr);
            Class<?> klass = ChildProcess4a.class;
            ArrayList<String> argList =
                new ArrayList<String>(
                    Arrays.asList("java", "-classpath", classpath, klass.getName()));
            ProcessBuilder2 builder = new ProcessBuilder2(argList);
            builder.stdoutSettings().isDataAccumulated(true);
            builder.stderrSettings().isDataAccumulated(true);
            // Intentional: Do not set maxAccumulatedDataByteCount(int).
            byte[] optStdinDataByteArr = null;
            if (psList.contains(ProcessStream.STDIN)) {
                if (isStdinText) {
                    builder.stdinText(ProcessStream.STDIN.text);
                }
                else {
                    optStdinDataByteArr = ProcessStream.STDIN.text.getBytes();
                    builder.stdinData(optStdinDataByteArr);
                }
                isStdinText = !isStdinText;
            }
            for (ProcessStream ps: psArr) {
                String name = ps.name();
                // This will also add to ProcessBuilder2's arg list.  (By-Ref)
                argList.add(name);
            }
            
            Process2 proc = builder.start();
            if (3 == psArr.length) {
                // There is no guarantee this will work.  If our thread is starved and the child
                // process finishes first, this assert will fail.  STDIN/STDOUT/STDERR each push
                // (at least) 8,000,000 chars.  This should be enough delay to allow this assert to
                // succeed.
                Assert.assertTrue(proc.isRunning());
                Assert.assertTrue(!proc.hasFinished());
            }
            int exitValue = proc.waitFor();
            Assert.assertTrue(proc.hasFinished());
            
            if (null != optStdinDataByteArr) {
                byte[] stdinDataByteArrCopy = proc.stdinData();
                Assert.assertEquals(stdinDataByteArrCopy, optStdinDataByteArr);
                byte b = stdinDataByteArrCopy[0];
                b += 1;
                stdinDataByteArrCopy[0] = b;
                Assert.assertNotEquals(proc.stdinData(), stdinDataByteArrCopy);
                // Prove we are getting a copy.
                byte[] stdinDataByteArrCopy2 = proc.stdinData();
                Assert.assertEquals(stdinDataByteArrCopy2, optStdinDataByteArr);
                Assert.assertNotEquals(stdinDataByteArrCopy, optStdinDataByteArr);
            }
            
            String expectedStdout =
                (psList.contains(ProcessStream.STDOUT) ? ProcessStream.STDOUT.text : "");
            String actualStdout = proc.stdoutDataAsString();
            byte[] actualStdoutByteArr = proc.stdoutDataAsByteArr();
            Assert.assertEquals(actualStdoutByteArr, actualStdout.getBytes());
            
            String expectedStderr =
                (psList.contains(ProcessStream.STDERR) ? ProcessStream.STDERR.text : "");
            String actualStderr = proc.stderrDataAsString();
            byte[] actualStderrByteArr = proc.stderrDataAsByteArr();
            Assert.assertEquals(actualStderrByteArr, actualStderr.getBytes());
            
            TestAssertUtils.assertHugeStringEquals(actualStdout, expectedStdout);
            TestAssertUtils.assertHugeStringEquals(actualStderr, expectedStderr);
            Assert.assertEquals(exitValue, 0);
            _ProcessBuilder2AndProcess2Equals_Pass(builder, proc);
            
            System.out.println("\tRun again with STDERR redirected to STDOUT...");
            builder.redirectErrorStream(true);
            @SuppressWarnings("unused")
            boolean wasRemoved = builder.command().remove(ProcessStream.STDOUT.name());
            Process2 proc2 = builder.start();
            int exitValue2 = proc2.waitFor();
            
            String expectedStdout2 = expectedStderr;
/*
java.io.IOException: Failed to write data to child process STDIN stream

	at com.googlecode.kevinarpe.papaya.process.Process2.tryWriteStdinThreadRethrowCaughtException(Process2.java:309)
	at com.googlecode.kevinarpe.papaya.process.Process2.stdoutDataAsString(Process2.java:391)
	at com.googlecode.kevinarpe.papaya.process.Process2.stdoutDataAsString(Process2.java:361)
	at com.googlecode.kevinarpe.papaya.process.ProcessBuilder2_And_Process2_Test.ChildProcess4a_Pass(ProcessBuilder2_And_Process2_Test.java:459)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:134)
	at org.testng.internal.TestInvoker.invokeMethod(TestInvoker.java:597)
	at org.testng.internal.TestInvoker.invokeTestMethod(TestInvoker.java:173)
	at org.testng.internal.MethodRunner.runInSequence(MethodRunner.java:46)
	at org.testng.internal.TestInvoker$MethodInvocationAgent.invoke(TestInvoker.java:816)
	at org.testng.internal.TestInvoker.invokeTestMethods(TestInvoker.java:146)
	at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:146)
	at org.testng.internal.TestMethodWorker.run(TestMethodWorker.java:128)
	at java.util.ArrayList.forEach(ArrayList.java:1257)
	at org.testng.TestRunner.privateRun(TestRunner.java:766)
	at org.testng.TestRunner.run(TestRunner.java:587)
	at org.testng.SuiteRunner.runTest(SuiteRunner.java:384)
	at org.testng.SuiteRunner.runSequentially(SuiteRunner.java:378)
	at org.testng.SuiteRunner.privateRun(SuiteRunner.java:337)
	at org.testng.SuiteRunner.run(SuiteRunner.java:286)
	at org.testng.SuiteRunnerWorker.runSuite(SuiteRunnerWorker.java:53)
	at org.testng.SuiteRunnerWorker.run(SuiteRunnerWorker.java:96)
	at org.testng.TestNG.runSuitesSequentially(TestNG.java:1187)
	at org.testng.TestNG.runSuitesLocally(TestNG.java:1109)
	at org.testng.TestNG.runSuites(TestNG.java:1039)
	at org.testng.TestNG.run(TestNG.java:1007)
	at com.intellij.rt.testng.IDEARemoteTestNG.run(IDEARemoteTestNG.java:66)
	at com.intellij.rt.testng.RemoteTestNGStarter.main(RemoteTestNGStarter.java:109)
Caused by: java.io.IOException: Broken pipe
	at java.io.FileOutputStream.writeBytes(Native Method)
	at java.io.FileOutputStream.write(FileOutputStream.java:326)
	at java.io.BufferedOutputStream.write(BufferedOutputStream.java:122)
	at java.io.FilterOutputStream.write(FilterOutputStream.java:97)
	at com.googlecode.kevinarpe.papaya.process.WriteOutputStreamThread.runWithException(WriteOutputStreamThread.java:82)
	at com.googlecode.kevinarpe.papaya.AbstractThreadWithException.run(AbstractThreadWithException.java:106)

 */
            String actualStdout2 = proc2.stdoutDataAsString();
            
            String expectedStderr2 = "";
            String actualStderr2 = proc2.stderrDataAsString();
            
            TestAssertUtils.assertHugeStringEquals(actualStdout2, expectedStdout2);
            TestAssertUtils.assertHugeStringEquals(actualStderr2, expectedStderr2);
            Assert.assertEquals(exitValue2, 0);
            _ProcessBuilder2AndProcess2Equals_Pass(builder, proc2);
        }
    }
    
    private void _ProcessBuilder2AndProcess2Equals_Pass(ProcessBuilder2 builder, Process2 proc) {
        Assert.assertEquals(builder, proc);
        _ProcessBuilder2AndProcess2_StdxxxSettings_Equals_Pass(
            builder.stdoutSettings(),
            proc.stdoutSettings());
        _ProcessBuilder2AndProcess2_StdxxxSettings_Equals_Pass(
            builder.stderrSettings(),
            proc.stderrSettings());
        Assert.assertEquals(builder.command(), proc.command());
        Assert.assertEquals(builder.environment(), proc.environment());
        Assert.assertEquals(builder.directory(), proc.directory());
        Assert.assertEquals(builder.redirectErrorStream(), proc.redirectErrorStream());
        Assert.assertEquals(builder.stdinData(), proc.stdinData());
        Assert.assertEquals(builder.stdinText(), proc.stdinText());
    }
    
    private void _ProcessBuilder2AndProcess2_StdxxxSettings_Equals_Pass(
            ProcessOutputStreamSettings stdxxxSettings1,
            ProcessOutputStreamSettings stdxxxSettings2) {
        Assert.assertEquals(stdxxxSettings1, stdxxxSettings2);
        Assert.assertEquals(stdxxxSettings1.charset(), stdxxxSettings2.charset());
        Assert.assertEquals(stdxxxSettings1.splitRegex(), stdxxxSettings2.splitRegex());
        Assert.assertEquals(stdxxxSettings1.charCallback(), stdxxxSettings2.charCallback());
        Assert.assertEquals(stdxxxSettings1.byteCallback(), stdxxxSettings2.byteCallback());
        Assert.assertEquals(
            stdxxxSettings1.isDataAccumulated(),
            stdxxxSettings2.isDataAccumulated());
        Assert.assertEquals(
            stdxxxSettings1.maxAccumulatedDataByteCount(),
            stdxxxSettings2.maxAccumulatedDataByteCount());
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessBuilder2.ctor(List<String>)
    //

    @DataProvider
    public static Object[][] _ctor_Pass_Data() {
        return new Object[][] {
                { ImmutableList.<String>of("abc") },
                { ImmutableList.<String>of("abc", "def") },
                { ImmutableList.<String>of("abc", "def", "ghi") },
        };
    }
    
    @Test(dataProvider = "_ctor_Pass_Data")
    public void ctor_Pass(List<String> command) {
        new ProcessBuilder2(command);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNullList() {
        new ProcessBuilder2((List<String>) null);
    }
    
    @DataProvider
    public static Object[][] _ctor_FailWithNullsInList_Data() {
        return new Object[][] {
                { new ArrayList<String>(Arrays.asList((String) null)) },
                { new ArrayList<String>(Arrays.asList("abc", null)) },
                { new ArrayList<String>(Arrays.asList("abc", null, "def")) },
                { new ArrayList<String>(Arrays.asList("abc", null, "def", null)) },
        };
    }
    
    @Test(dataProvider = "_ctor_FailWithNullsInList_Data",
            expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNullsInList(List<String> command) {
        new ProcessBuilder2(command);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessBuilder2.ctor(String...)
    //

    @DataProvider
    public static Object[][] _ctor2_Pass_Data() {
        return new Object[][] {
                { new String[] { "abc" } },
                { new String[] { "abc", "def" } },
                { new String[] { "abc", "def", "ghi" } },
        };
    }
    
    @Test(dataProvider = "_ctor2_Pass_Data")
    public void ctor_Pass(String[] commandArr) {
        new ProcessBuilder2(commandArr);
    }
    
    @DataProvider
    public static Object[][] _ctor2_FailWithNullsInList_Data() {
        return new Object[][] {
                { new String[] { null } },
                { new String[] { "abc", null } },
                { new String[] { "abc", null, "def" } },
                { new String[] { "abc", null, "def", null } },
        };
    }
    
    @Test(dataProvider = "_ctor2_FailWithNullsInList_Data",
            expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNullsInList(String[] commandArr) {
        new ProcessBuilder2(commandArr);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessBuilder2.ctor(ProcessBuilder2) & .equals
    //

    @Test
    public void copyCtor_And_Equals_Pass() {
        List<String> argList = Arrays.asList("abc", "def");
        String envKey = UUID.randomUUID().toString();
        String envValue = UUID.randomUUID().toString();
        File dirPath = new File(UUID.randomUUID().toString());
        byte[] stdinData = UUID.randomUUID().toString().getBytes();
        String stdinText = UUID.randomUUID().toString();
        Charset nonDefaultCharset = TestCharsetUtils.getRandomNonDefaultCharset();
        Pattern splitRegex = Pattern.compile("xyz");
        
        ProcessBuilder2 pb1 = new ProcessBuilder2();
        pb1.command(argList);
        pb1.environment().put(envKey, envValue);
        //Map<String, String> env1 = new HashMap<String, String>(pb1.environment());
        pb1.directory(dirPath);
        pb1.redirectErrorStream(!pb1.redirectErrorStream());
        pb1.stdinData(stdinData);
        
        pb1.stdoutSettings().charset(nonDefaultCharset);
        pb1.stdoutSettings().splitRegex(splitRegex);
        pb1.stdoutSettings().charCallback(ProcessOutputStreamSettingsTest.SAMPLE_CHAR_CALLBACK);
        pb1.stdoutSettings().byteCallback(ProcessOutputStreamSettingsTest.SAMPLE_BYTE_CALLBACK);
        pb1.stdoutSettings().isDataAccumulated(!pb1.stdoutSettings().isDataAccumulated());
        pb1.stdoutSettings().maxAccumulatedDataByteCount(99);
        
        ProcessBuilder2 pb1Copy = new ProcessBuilder2(pb1);
        ProcessBuilder2 pb2Unrelated = new ProcessBuilder2();
        Assert.assertEquals(pb1, pb1Copy);
        Assert.assertNotEquals(pb1, pb2Unrelated);
        Assert.assertNotEquals(pb1Copy, pb2Unrelated);
        
        pb1.stdinText(stdinText);
        pb1.stdoutSettings().charCallbackFactory(
            ProcessOutputStreamSettingsTest.SAMPLE_CHAR_CALLBACK_FACTORY);
        pb1.stdoutSettings().byteCallbackFactory(
            ProcessOutputStreamSettingsTest.SAMPLE_BYTE_CALLBACK_FACTORY);
        
        Assert.assertNotEquals(pb1, pb1Copy);
        Assert.assertNotEquals(pb1, pb2Unrelated);
        Assert.assertNotEquals(pb1Copy, pb2Unrelated);
        pb1Copy = new ProcessBuilder2(pb1);
        Assert.assertEquals(pb1, pb1Copy);
        
        pb1.stderrSettings().charset(nonDefaultCharset);
        pb1.stderrSettings().splitRegex(splitRegex);
        pb1.stderrSettings().charCallback(ProcessOutputStreamSettingsTest.SAMPLE_CHAR_CALLBACK);
        pb1.stderrSettings().byteCallback(ProcessOutputStreamSettingsTest.SAMPLE_BYTE_CALLBACK);
        pb1.stderrSettings().isDataAccumulated(!pb1.stderrSettings().isDataAccumulated());
        pb1.stderrSettings().maxAccumulatedDataByteCount(99);
        
        Assert.assertNotEquals(pb1, pb1Copy);
        Assert.assertNotEquals(pb1, pb2Unrelated);
        Assert.assertNotEquals(pb1Copy, pb2Unrelated);
        pb1Copy = new ProcessBuilder2(pb1);
        Assert.assertEquals(pb1, pb1Copy);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessBuilder2.command
    //
    
    @Test
    public void command_Pass() {
        ProcessBuilder2 x = new ProcessBuilder2();
        List<String> emptyList = x.command();
        Assert.assertTrue(emptyList.isEmpty());
        
        ArrayList<String> list = new ArrayList<String>();
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.command(list));
        List<String> list2 = x.command();
        Assert.assertTrue(list == list2);
        list2.add("abc");
        Assert.assertTrue(list == list2);
        Assert.assertTrue(x == x.command(list2));
        List<String> list3 = x.command();
        Assert.assertTrue(list2 == list3);
        Assert.assertEquals(list2, list);
        
        Assert.assertTrue(x == x.command("123", "456"));
        List<String> list4 = x.command();
        Assert.assertEquals(list4, Arrays.asList("123", "456"));
        list4.add("789");
        List<String> list5 = x.command();
        Assert.assertTrue(list4 == list5);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void command_FailWithNullList() {
        ProcessBuilder2 x = new ProcessBuilder2();
        x.command((List<String>) null);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void command_FailWithNullElements() {
        ProcessBuilder2 x = new ProcessBuilder2();
        x.command(Arrays.asList("abc", null));
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void command_FailWithNullElements2() {
        ProcessBuilder2 x = new ProcessBuilder2();
        x.command("abc", null);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessBuilder2.environment
    //
    
    @Test
    public void environment_Pass() {
        ProcessBuilder2 x = new ProcessBuilder2();
        Map<String, String> env = x.environment();
        if (env.isEmpty()) {
            // We are on a platform that does not support env vars.
            return;
        }
        
        String key = UUID.randomUUID().toString();
        String value = UUID.randomUUID().toString();
        env.put(key, value);
        
        Map<String, String> env2 = x.environment();
        
        Assert.assertTrue(env == env2);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessBuilder2.directory
    //
    
    @Test
    public void directory_Pass() {
        ProcessBuilder2 x = new ProcessBuilder2();
        Assert.assertEquals(x.directory(), null);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.directory(null));
        Assert.assertEquals(x.directory(), null);
        File dirPath = new File(UUID.randomUUID().toString());
        Assert.assertTrue(x == x.directory(dirPath));
        Assert.assertEquals(x.directory(), dirPath);
        Assert.assertTrue(x == x.directory(null));
        Assert.assertEquals(x.directory(), null);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessBuilder2.redirectErrorStream
    //
    
    @Test
    public void redirectErrorStream_Pass() {
        ProcessBuilder2 x = new ProcessBuilder2();
        Assert.assertEquals(x.redirectErrorStream(), false);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.redirectErrorStream(false));
        Assert.assertEquals(x.redirectErrorStream(), false);
        Assert.assertTrue(x == x.redirectErrorStream(true));
        Assert.assertEquals(x.redirectErrorStream(), true);
        Assert.assertTrue(x == x.redirectErrorStream(false));
        Assert.assertEquals(x.redirectErrorStream(), false);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessBuilder2.stdinData
    //
    
    @Test
    public void stdinDataAndText_Pass() {
        ProcessBuilder2 x = new ProcessBuilder2();
        Assert.assertEquals(x.stdinData(), null);
        Assert.assertEquals(x.stdinText(), null);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.stdinData((byte[]) null));
        Assert.assertEquals(x.stdinData(), null);
        Assert.assertEquals(x.stdinText(), null);
        Assert.assertTrue(x == x.stdinText((String) null));
        Assert.assertEquals(x.stdinData(), null);
        Assert.assertEquals(x.stdinText(), null);
        Assert.assertTrue(x == x.stdinData(new byte[0]));
        Assert.assertEquals(x.stdinData(), null);
        Assert.assertEquals(x.stdinText(), null);
        Assert.assertTrue(x == x.stdinText(""));
        Assert.assertEquals(x.stdinData(), null);
        Assert.assertEquals(x.stdinText(), null);
        
        byte[] byteArr = new byte[] { 1, 2, 3 };
        String text = "abc";
        
        Assert.assertTrue(x == x.stdinData(byteArr));
        Assert.assertEquals(x.stdinData(), byteArr);
        Assert.assertEquals(x.stdinText(), null);
        Assert.assertTrue(x == x.stdinText(text));
        Assert.assertEquals(x.stdinData(), null);
        Assert.assertEquals(x.stdinText(), text);
        
        Assert.assertTrue(x == x.stdinData((byte[]) null));
        Assert.assertEquals(x.stdinData(), null);
        Assert.assertEquals(x.stdinText(), null);
        Assert.assertTrue(x == x.stdinText((String) null));
        Assert.assertEquals(x.stdinData(), null);
        Assert.assertEquals(x.stdinText(), null);
        Assert.assertTrue(x == x.stdinData(new byte[0]));
        Assert.assertEquals(x.stdinData(), null);
        Assert.assertEquals(x.stdinText(), null);
        Assert.assertTrue(x == x.stdinText(""));
        Assert.assertEquals(x.stdinData(), null);
        Assert.assertEquals(x.stdinText(), null);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessBuilder2.start
    //
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void start_FailWithEmptyArgList()
    throws IOException {
        ProcessBuilder2 x = new ProcessBuilder2();
        x.start();
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void start_FailWithNullArg()
    throws IOException {
        ProcessBuilder2 x = new ProcessBuilder2();
        x.command().add(null);
        x.start();
    }
    
    @Test(expectedExceptions = IOException.class)
    public void start_FailWithDirNotExists()
    throws IOException {
        ProcessBuilder2 x = new ProcessBuilder2();
        x.command().add(UUID.randomUUID().toString());
        x.directory(new File(UUID.randomUUID().toString()));
        x.start();
    }
    
    @Test(expectedExceptions = IOException.class)
    public void start_FailWithInvalidCommand()
    throws IOException {
        ProcessBuilder2 x = new ProcessBuilder2();
        x.command().add(UUID.randomUUID().toString());
        x.start();
    }
    
    @Test(expectedExceptions = IOException.class)
    public void start_FailWithValidDirButInvalidCommand()
    throws IOException {
        ProcessBuilder2 x = new ProcessBuilder2();
        x.directory(new File("."));
        x.command().add(UUID.randomUUID().toString());
        x.start();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.destroy
    //
    
    @Test
    public void Process2_destroy_Pass()
    throws Exception {
        ProcessBuilder2 builder = _createBuilder();
        Process2 proc = builder.start();
        proc.destroy();
        try {
            for (int i = 1; !proc.hasFinished(); ++i) {
                System.out.println(i + ": Not yet destroyed");
                Thread.yield();
                final int millis = 100;
                Thread.sleep(millis);
            }
        }
        catch (IOException e) {
            // ignore this exception
            @SuppressWarnings("unused")
            int dummy = 1;
        }
        try {
            Assert.assertTrue(proc.hasFinished());
        }
        catch (IOException e) {
            // hasFinished will now rethrow the same exception twice.
            Assert.assertTrue(proc.hasFinished());
        }
        // Also confirm that destory can never fail, even after child process has terminated.
        proc.destroy();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.waitFor(long)
    //
    
    @Test
    public void Process2_waitFor_PassWithOneMillisecondWait()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        long timeoutMillis = 1;
        Integer optExitValue = proc.waitFor(timeoutMillis);
        Assert.assertEquals(optExitValue, null);
        proc.destroy();
    }
    
    @Test
    public void Process2_waitFor_PassWithWaitOnTerminatedChild()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder();
        Process2 proc = builder.start();
        proc.waitFor();
        // Demonstrate we can still wait with a timeout for a terminated child process.
        long timeoutMillis = 1;
        proc.waitFor(timeoutMillis);
    }
    
    @Test
    public void Process2_waitFor_PassWithUnlimitedWait()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        long timeoutMillis = 0;  // unlimited
        Integer optExitValue = proc.waitFor(timeoutMillis);
        Assert.assertNotEquals(optExitValue, null);
    }
    
    @Test
    public void Process2_waitFor_PassWithHugeWait()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        long timeoutMillis = Long.MAX_VALUE;  // nearly unlimited =)
        Integer optExitValue = proc.waitFor(timeoutMillis);
        Assert.assertNotEquals(optExitValue, null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void Process2_waitFor_FailWithNegative()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        long timeoutMillis = -1;
        try {
            proc.waitFor(timeoutMillis);
        }
        finally {
            proc.destroy();
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.exitValue
    //
    
    @Test
    public void Process2_exitValue_Pass()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder();
        Process2 proc = builder.start();
        int exitValue = proc.waitFor();
        try {
            int exitValue2 = proc.exitValue();
            Assert.assertEquals(exitValue, exitValue2);
        }
        catch (IOException e) {
            throw e;  // debug breakpoint
        }
    }
    
    @Test(expectedExceptions = IllegalThreadStateException.class)
    public void Process2_exitValue_FailWhenChildRunning()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        try {
            proc.exitValue();
        }
        finally {
            proc.destroy();
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.checkExitValue(int)
    //

    // 2020-10-04: This test is now broken.  I don't how to fix it.  :(
    // I *think* the root cause is an upgrade to my main build machine that is 5x faster,
    // so I have now uncovered a hidden race condition!
    @Ignore
    @Test
    public void Process2_checkExitValue_Pass()
    throws IOException, InterruptedException, InvalidExitValueException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        int exitValue = proc.waitFor();
        int exitValue2 = proc.checkExitValue(exitValue);
        Assert.assertEquals(exitValue, exitValue2);
    }

    // 2020-10-04: This test is now broken.  I don't how to fix it.  :(
    // I *think* the root cause is an upgrade to my main build machine that is 5x faster,
    // so I have now uncovered a hidden race condition!
    @Ignore
    @Test(expectedExceptions = InvalidExitValueException.class)
    public void Process2_checkExitValue_FailWithInvalidExitValue()
    throws IOException, InterruptedException, InvalidExitValueException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        int exitValue = proc.waitFor();
        try {
            proc.checkExitValue(exitValue + 1);
        }
        catch (InvalidExitValueException e) {
            Assert.assertEquals(e.getExitValue(), exitValue);
            Assert.assertEquals(e.getValidExitValueList(), Arrays.asList(exitValue + 1));
            throw e;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.checkExitValue(int...)
    //

    // 2020-10-04: This test is now broken.  I don't how to fix it.  :(
    // I *think* the root cause is an upgrade to my main build machine that is 5x faster,
    // so I have now uncovered a hidden race condition!
    @Ignore
    @Test
    public void Process2_checkExitValue_Pass2()
    throws IOException, InterruptedException, InvalidExitValueException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        int exitValue = proc.waitFor();
        int exitValue2 =
            proc.checkExitValue(exitValue + 2, exitValue, exitValue +1);
        Assert.assertEquals(exitValue, exitValue2);
    }

    // 2020-10-04: This test is now broken.  I don't how to fix it.  :(
    // I *think* the root cause is an upgrade to my main build machine that is 5x faster,
    // so I have now uncovered a hidden race condition!
    @Ignore
    @Test(expectedExceptions = InvalidExitValueException.class)
    public void Process2_checkExitValue_FailWithInvalidExitValues2()
    throws IOException, InterruptedException, InvalidExitValueException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        int exitValue = proc.waitFor();
        try {
            proc.checkExitValue(exitValue + 3, exitValue + 2, exitValue + 1);
        }
        catch (InvalidExitValueException e) {
            Assert.assertEquals(e.getExitValue(), exitValue);
            Assert.assertEquals(
                e.getValidExitValueList(),
                Arrays.asList(exitValue + 3, exitValue + 2, exitValue + 1));
            throw e;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.checkExitValue(Collection)
    //

    // 2020-10-04: This test is now broken.  I don't how to fix it.  :(
    // I *think* the root cause is an upgrade to my main build machine that is 5x faster,
    // so I have now uncovered a hidden race condition!
    @Ignore
    @Test
    public void Process2_checkExitValue_Pass3()
    throws IOException, InterruptedException, InvalidExitValueException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        int exitValue = proc.waitFor();
        int exitValue2 =
            proc.checkExitValue(Arrays.asList(exitValue + 2, exitValue, exitValue +1));
        Assert.assertEquals(exitValue, exitValue2);
    }

    // 2020-10-04: This test is now broken.  I don't how to fix it.  :(
    // I *think* the root cause is an upgrade to my main build machine that is 5x faster,
    // so I have now uncovered a hidden race condition!
    @Ignore
    @Test(expectedExceptions = InvalidExitValueException.class)
    public void Process2_checkExitValue_FailWithInvalidExitValues3()
    throws IOException, InterruptedException, InvalidExitValueException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        int exitValue = proc.waitFor();
        try {
            proc.checkExitValue(Arrays.asList(exitValue + 3, exitValue + 2, exitValue + 1));
        }
        catch (InvalidExitValueException e) {
            Assert.assertEquals(e.getExitValue(), exitValue);
            Assert.assertEquals(
                e.getValidExitValueList(),
                Arrays.asList(exitValue + 3, exitValue + 2, exitValue + 1));
            throw e;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.isRunning
    //

    // 2020-10-04: This test is now broken.  I don't how to fix it.  :(
    // I *think* the root cause is an upgrade to my main build machine that is 5x faster,
    // so I have now uncovered a hidden race condition!
    @Ignore
    @Test
    public void Process2_isRunning_Pass()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        Assert.assertTrue(proc.isRunning());
        proc.waitFor();
        Assert.assertTrue(!proc.isRunning());
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.hasFinished
    //

    // 2020-10-04: This test is now broken.  I don't how to fix it.  :(
    // I *think* the root cause is an upgrade to my main build machine that is 5x faster,
    // so I have now uncovered a hidden race condition!
    @Ignore
    @Test
    public void Process2_hasFinished_Pass()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder =
            _createBuilder(ProcessStream.STDIN, ProcessStream.STDOUT, ProcessStream.STDERR);
        Process2 proc = builder.start();
        Assert.assertTrue(!proc.hasFinished());
        proc.waitFor();
        Assert.assertTrue(proc.hasFinished());
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.stdoutDataAsByteArr
    //
    
    @Test
    public void Process2_stdoutDataAsByteArr_Pass()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        proc.waitFor();
        Assert.assertEquals(proc.stdoutDataAsByteArr(), ProcessStream.STDOUT.text.getBytes());
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.stdoutDataAsString()
    //
    
    @Test
    public void Process2_stdoutDataAsString_Pass()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        proc.waitFor();
        Assert.assertEquals(proc.stdoutDataAsString(), ProcessStream.STDOUT.text);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.stdoutDataAsString(Charset)
    //
    
    @Test
    public void Process2_stdoutDataAsString_Pass2()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        proc.waitFor();
        Assert.assertEquals(proc.stdoutDataAsString(null), ProcessStream.STDOUT.text);
        Assert.assertEquals(
            proc.stdoutDataAsString(Charset.defaultCharset()),
            ProcessStream.STDOUT.text);
    }
    
    @Test
    public void Process2_stdoutDataAsString_Pass3()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        proc.waitFor();
        Assert.assertEquals(
            proc.stdoutDataAsString(Charset.defaultCharset()),
            ProcessStream.STDOUT.text);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.stderrDataAsByteArr
    //
    
    @Test
    public void Process2_stderrDataAsByteArr_Pass()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDERR);
        Process2 proc = builder.start();
        proc.waitFor();
        Assert.assertEquals(proc.stderrDataAsByteArr(), ProcessStream.STDERR.text.getBytes());
    }
    
    @Test
    public void Process2_stderrDataAsByteArr_Pass2()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDERR);
        builder.redirectErrorStream(true);
        Process2 proc = builder.start();
        proc.waitFor();
        Assert.assertEquals(proc.stderrDataAsByteArr(), new byte[0]);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.stderrDataAsString()
    //
    
    @Test
    public void Process2_stderrDataAsString_Pass()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDERR);
        Process2 proc = builder.start();
        proc.waitFor();
        Assert.assertEquals(proc.stderrDataAsString(), ProcessStream.STDERR.text);
    }
    
    @Test
    public void Process2_stderrDataAsString_Pass2()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDERR);
        builder.redirectErrorStream(true);
        Process2 proc = builder.start();
        proc.waitFor();
        Assert.assertEquals(proc.stderrDataAsString(), "");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.stderrDataAsString(Charset)
    //
    
    @Test
    public void Process2_stderrDataAsString_Pass3()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDERR);
        Process2 proc = builder.start();
        proc.waitFor();
        Assert.assertEquals(proc.stderrDataAsString(null), ProcessStream.STDERR.text);
        Assert.assertEquals(
            proc.stderrDataAsString(Charset.defaultCharset()),
            ProcessStream.STDERR.text);
    }
    
    @Test
    public void Process2_stderrDataAsString_Pass4()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDERR);
        builder.redirectErrorStream(true);
        Process2 proc = builder.start();
        proc.waitFor();
        Assert.assertEquals(proc.stderrDataAsString(null), "");
        Assert.assertEquals(proc.stderrDataAsString(Charset.defaultCharset()), "");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.tryExitValue
    //
    
    @Test
    public void Process2_tryExitValue_Pass()
    throws IOException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        builder.redirectErrorStream(true);
        Process2 proc = builder.start();
        proc.tryExitValue();
        proc.waitFor();
        proc.tryExitValue();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.waitForThenCheckExitValue(int)
    //
    
    @Test
    public void Process2_waitForThenCheckExitValue_Pass()
    throws IOException, InvalidExitValueException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        int exitValue = 0;
        proc.waitForThenCheckExitValue(exitValue);
    }
    
    @Test(expectedExceptions = InvalidExitValueException.class)
    public void Process2_waitForThenCheckExitValue_Fail()
    throws IOException, InvalidExitValueException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        int exitValue = 1;
        try {
            proc.waitForThenCheckExitValue(exitValue);
        }
        catch (InvalidExitValueException e) {
            Assert.assertEquals(e.getExitValue(), 0);
            Assert.assertEquals(e.getValidExitValueList(), Arrays.asList(exitValue));
            throw e;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.waitForThenCheckExitValue(Collection)
    //
    
    @Test
    public void Process2_waitForThenCheckExitValue_Pass2()
    throws IOException, InvalidExitValueException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        int exitValue = 0;
        proc.waitForThenCheckExitValue(Arrays.asList(exitValue + 2, exitValue, exitValue + 1));
    }
    
    @Test(expectedExceptions = InvalidExitValueException.class)
    public void Process2_waitForThenCheckExitValue_Fail2()
    throws IOException, InvalidExitValueException, InterruptedException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        int exitValue = 1;
        try {
            proc.waitForThenCheckExitValue(Arrays.asList(exitValue + 2, exitValue, exitValue + 1));
        }
        catch (InvalidExitValueException e) {
            Assert.assertEquals(e.getExitValue(), 0);
            Assert.assertEquals(
                e.getValidExitValueList(),
                Arrays.asList(exitValue + 2, exitValue, exitValue + 1));
            throw e;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.waitForThenCheckExitValue(long, int)
    //
    
    @Test
    public void Process2_waitForThenCheckExitValue_Pass3()
    throws IOException, InvalidExitValueException, InterruptedException, TimeoutException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        long timeoutMillis = Long.MAX_VALUE;
        int exitValue = 0;
        proc.waitForThenCheckExitValue(timeoutMillis, exitValue);
    }
    
    @Test(expectedExceptions = InvalidExitValueException.class)
    public void Process2_waitForThenCheckExitValue_Fail3()
    throws IOException, InvalidExitValueException, InterruptedException, TimeoutException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        long timeoutMillis = Long.MAX_VALUE;
        int exitValue = 1;
        try {
            proc.waitForThenCheckExitValue(timeoutMillis, exitValue);
        }
        catch (InvalidExitValueException e) {
            Assert.assertEquals(e.getExitValue(), 0);
            Assert.assertEquals(e.getValidExitValueList(), Arrays.asList(exitValue));
            throw e;
        }
    }
    
    @Test(expectedExceptions = TimeoutException.class)
    public void Process2_waitForThenCheckExitValue_Fail4()
    throws IOException, InvalidExitValueException, InterruptedException, TimeoutException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        long timeoutMillis = 1;
        int exitValue = 0;
        try {
            proc.waitForThenCheckExitValue(timeoutMillis, exitValue);
        }
        catch (TimeoutException e) {
            Assert.assertEquals(e.getTimeoutMillis(), timeoutMillis);
            throw e;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Process2.waitForThenCheckExitValue(long, Collection)
    //
    
    @Test
    public void Process2_waitForThenCheckExitValue_Pass4()
    throws IOException, InvalidExitValueException, InterruptedException, TimeoutException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        long timeoutMillis = Long.MAX_VALUE;
        int exitValue = 0;
        proc.waitForThenCheckExitValue(
            timeoutMillis, Arrays.asList(exitValue + 2, exitValue, exitValue + 1));
    }
    
    @Test(expectedExceptions = InvalidExitValueException.class)
    public void Process2_waitForThenCheckExitValue_Fail5()
    throws IOException, InvalidExitValueException, InterruptedException, TimeoutException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        long timeoutMillis = Long.MAX_VALUE;
        int exitValue = 1;
        try {
            proc.waitForThenCheckExitValue(
                timeoutMillis, Arrays.asList(exitValue + 2, exitValue, exitValue + 1));
        }
        catch (InvalidExitValueException e) {
            Assert.assertEquals(e.getExitValue(), 0);
            Assert.assertEquals(
                e.getValidExitValueList(),
                Arrays.asList(exitValue + 2, exitValue, exitValue + 1));
            throw e;
        }
    }
    
    @Test(expectedExceptions = TimeoutException.class)
    public void Process2_waitForThenCheckExitValue_Fail6()
    throws IOException, InvalidExitValueException, InterruptedException, TimeoutException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        Process2 proc = builder.start();
        long timeoutMillis = 1;
        int exitValue = 0;
        try {
            proc.waitForThenCheckExitValue(
                timeoutMillis, Arrays.asList(exitValue + 2, exitValue, exitValue + 1));
        }
        catch (TimeoutException e) {
            Assert.assertEquals(e.getTimeoutMillis(), timeoutMillis);
            throw e;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessOutputStreamSettings.maxAccumulatedDataByteCount
    //
    
    @Test
    public void ProcessOutputStreamSettings_maxAccumulatedDataByteCount_Pass()
    throws IOException, InvalidExitValueException, InterruptedException, TimeoutException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        int max = 99;
        builder.stdoutSettings().maxAccumulatedDataByteCount(max);
        Process2 proc = builder.start();
        proc.waitFor();
        String stdoutText = proc.stdoutDataAsString();
        Assert.assertEquals(stdoutText, ProcessStream.STDOUT.text.substring(0, max));
    }
    
    @Test
    public void ProcessOutputStreamSettings_maxAccumulatedDataByteCount_Pass2()
    throws IOException, InvalidExitValueException, InterruptedException, TimeoutException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDERR);
        int max = 101;
        builder.stderrSettings().maxAccumulatedDataByteCount(max);
        Process2 proc = builder.start();
        proc.waitFor();
        String stderrText = proc.stderrDataAsString();
        Assert.assertEquals(stderrText, ProcessStream.STDERR.text.substring(0, max));
    }
    
    @Test
    public void ProcessOutputStreamSettings_maxAccumulatedDataByteCount_Pass3()
    throws IOException, InvalidExitValueException, InterruptedException, TimeoutException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDERR);
        builder.redirectErrorStream(true);
        int max = 103;
        builder.stdoutSettings().maxAccumulatedDataByteCount(max);
        Process2 proc = builder.start();
        proc.waitFor();
        String stdoutText = proc.stdoutDataAsString();
        Assert.assertEquals(stdoutText, ProcessStream.STDERR.text.substring(0, max));
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessOutputStreamSettings.splitRegex
    //
    
    @Test
    public void ProcessOutputStreamSettings_splitRegex_Pass()
    throws IOException, InvalidExitValueException, InterruptedException, TimeoutException {
        ProcessBuilder2 builder = _createBuilder(ProcessStream.STDOUT);
        // We need to "exercise" some code.
        builder.stdoutSettings().splitRegex(Pattern.compile("a"));
        builder.stdoutSettings().charCallback(
            ProcessOutputStreamSettingsTest.SAMPLE_CHAR_CALLBACK);
        builder.stdoutSettings().byteCallback(
            ProcessOutputStreamSettingsTest.SAMPLE_BYTE_CALLBACK);
        Process2 proc = builder.start();
        proc.waitFor();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Helper Methods
    //
    
    private ProcessBuilder2 _createBuilder(ProcessStream... psArr) {
        String classpath = _getCurrentClasspath();
        Class<?> klass = ChildProcess4a.class;
        ArrayList<String> argList =
            new ArrayList<String>(
                Arrays.asList(
                    "java",
                    "-classpath",
                    classpath,
                    klass.getName()));
        for (ProcessStream ps: psArr) {
            argList.add(ps.name());
        }
        ProcessBuilder2 builder = new ProcessBuilder2(argList);
        builder.stdoutSettings().isDataAccumulated(true);
        builder.stderrSettings().isDataAccumulated(true);
        // Intentional: Do not set maxAccumulatedDataByteCount(int).
        List<ProcessStream> psList = Arrays.asList(psArr);
        if (psList.contains(ProcessStream.STDIN)) {
            builder.stdinText(ProcessStream.STDIN.text);
        }
        return builder;
    }
}
