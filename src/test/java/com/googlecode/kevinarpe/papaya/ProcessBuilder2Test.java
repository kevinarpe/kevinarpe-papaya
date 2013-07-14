package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.appendable.AbstractSimplifiedAppendable;
import com.googlecode.kevinarpe.papaya.appendable.ByteAppendable;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.process.Process2;
import com.googlecode.kevinarpe.papaya.process.ProcessBuilder2;
import com.googlecode.kevinarpe.papaya.process.ProcessOutputStreamSettings;
import com.googlecode.kevinarpe.papaya.test.TestAssertUtils;
import com.googlecode.kevinarpe.papaya.test.TestCharsetUtils;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ProcessBuilder2Test {
    
    /**
     * This class is used by the child processes which only have access to JDK libraries.
     * Do not use any classes outside the JDK framework.
     */
    public static enum ProcessStream {
        STDIN,
        STDOUT,
        STDERR;
        
        public final File dataFilePath;
        public final String text;
        public final List<String> textList;
        
        private ProcessStream() {
            String name = this.name();
            // TODO: Generate if missing.  Create a special directory and add to .gitignore
            dataFilePath =
                new File(String.format("src/test/resources/%s.txt", name.toLowerCase()));
            try {
                // TODO: Create min/max/exact/range file size checkers
                if (!dataFilePath.isFile()) {
                    throw new IOException(String.format("%s: File does not exist: '%s'",
                        this.name(), dataFilePath.getAbsolutePath()));
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
    
//    public static final File STDIN_DATA_FILE_PATH =
//        new File("src/test/resources/stdin.txt");
//    
//    public static final String STDIN_DATA;
//    public static final List<String> STDIN_DATA_LIST;
//    
//    public static final File STDOUT_DATA_FILE_PATH =
//        new File("src/test/resources/stdout.txt");
//    
//    public static final File STDERR_DATA_FILE_PATH =
//        new File("src/test/resources/stderr.txt");
//    
//    private static final List<File> FILE_PATH_LIST =
//        ImmutableList.of(STDIN_DATA_FILE_PATH, STDOUT_DATA_FILE_PATH, STDERR_DATA_FILE_PATH);
//    
//    public static final int MIN_DATA_FILE_CHAR_COUNT = 8000000;
//    
//    static {
//        
//    }
//    
//    private static void _readDataFile(File path, StringBuilder sb, List<String> list) {
//        sb.setLength(0);
//        list.clear();
//        
//    }
    
//    @BeforeClass
//    public void setup()
//    throws IOException {
//        Object o = ProcessStream.STDIN;
//        teardown();
//        StringBuilder sb = new StringBuilder();
//        for (File path: FILE_PATH_LIST) {
//            sb.setLength(0);
//            while (sb.length() < MIN_DATA_FILE_CHAR_COUNT) {
//                sb.append(UUID.randomUUID().toString());
//            }
//            String s = sb.toString();
//            FileWriter out = new FileWriter(path);
//            out.write(s);
//            out.close();
//        }
//        int dummy = 1;  // debug breakpoint
//    }
//    
//    @AfterClass
//    public void teardown()
//    throws PathException {
//        for (File path: FILE_PATH_LIST) {
//            PathUtils.removeFile(path);
//        }
//    }
    
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
        // Ref: http://stackoverflow.com/a/11747859/257299
        String classpath = ChildProcess1.class.getProtectionDomain().getCodeSource().getLocation().getPath();
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
        
        // Ref: http://stackoverflow.com/a/11747859/257299
        String classpath =
            klass.getProtectionDomain().getCodeSource().getLocation().getPath();
        ProcessBuilder2 builder =
            new ProcessBuilder2("java", "-classpath", classpath, klass.getName());
        builder.stdoutSettings().isDataAccumulated(true);
        builder.stderrSettings().isDataAccumulated(true);
        Process2 proc = builder.start();
        int exitValue = proc.waitFor();
        // TODO: Get this added to TestNG
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
    
    @Test
    public void ChildProcess4a_Pass()
    throws IOException, InterruptedException {
        String classpath = _getCurrentClasspath();
        
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
            if (psList.contains(ProcessStream.STDIN)) {
                builder.stdinText(ProcessStream.STDIN.text);
            }
            for (ProcessStream ps: psArr) {
                String name = ps.name();
                // This will also add to ProcessBuilder2's arg list.  (By-Ref)
                argList.add(name);
            }
            
            Process2 proc = builder.start();
            int exitValue = proc.waitFor();
            
            String expectedStdout =
                (psList.contains(ProcessStream.STDOUT) ? ProcessStream.STDOUT.text : "");
            String actualStdout = proc.stdoutDataAsString();
            
            String expectedStderr =
                (psList.contains(ProcessStream.STDERR) ? ProcessStream.STDERR.text : "");
            String actualStderr = proc.stderrDataAsString();
            
            TestAssertUtils.assertHugeStringEquals(actualStdout, expectedStdout);
            TestAssertUtils.assertHugeStringEquals(actualStderr, expectedStderr);
            Assert.assertEquals(exitValue, 0);
            _ProcessBuilder2AndProcess2Equals_Pass(builder, proc);
            
            System.out.println("\tRun again with STDERR redirected to STDOUT...");
            builder.redirectErrorStream(true);
            boolean wasRemoved = builder.command().remove(ProcessStream.STDOUT.name());
            Process2 proc2 = builder.start();
            int exitValue2 = proc2.waitFor();
            
            String expectedStdout2 = expectedStderr;
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
    private static final Object[][] _ctor_Pass_Data() {
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
    private static final Object[][] _ctor_FailWithNullsInList_Data() {
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
    private static final Object[][] _ctor2_Pass_Data() {
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
    private static final Object[][] _ctor2_FailWithNullsInList_Data() {
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
    // ProcessBuilder2.ctor(ProcessBuilder2)
    //

    // TODO: LAST
//    @Test
    public void blah() {
        List<String> argList = Arrays.asList("abc", "def");
        String envKey = UUID.randomUUID().toString();
        String envValue = UUID.randomUUID().toString();
        File dirPath = new File(UUID.randomUUID().toString());
        byte[] stdinData = UUID.randomUUID().toString().getBytes();
        String stdinText = UUID.randomUUID().toString();
        Charset nonDefaultCharset = TestCharsetUtils.getRandomNonDefaultCharset();
        Pattern splitRegex = Pattern.compile("xyz");
        Appendable charCallback = new StringBuffer();
        ByteAppendable byteCallback = new ByteAppendable() {
            @Override
            public void append(byte[] byteArr)
            throws IOException {
                // do nothing
            }
        };
        
        ProcessBuilder2 pb1 = new ProcessBuilder2();
        pb1.environment().put(envKey, envValue);
        Map<String, String> env1 = new HashMap<String, String>(pb1.environment());
        pb1.directory(dirPath);
        pb1.redirectErrorStream(!pb1.redirectErrorStream());
        pb1.stdinData(stdinData);
        
        pb1.stdoutSettings().charset(nonDefaultCharset);
        
    }
    
    private void _setStdxxxSettings(ProcessOutputStreamSettings stdxxxSettings) {
        
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessBuilder2.equals
    //

    @DataProvider
    private static final Object[][] _hashCode_Pass_Data() {
        return new Object[][] {
                { new String[] { "abc" } },
                { new String[] { "abc", "def" } },
                { new String[] { "abc", "def", "ghi" } },
        };
    }
    
    @Test(dataProvider = "_hashCode_Pass_Data")
    public void hashCode_Pass(String[] commandArr) {
        ProcessBuilder2 a = new ProcessBuilder2(commandArr);
        ProcessBuilder2 b = new ProcessBuilder2(commandArr);
        Assert.assertEquals(a, b);
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
}
