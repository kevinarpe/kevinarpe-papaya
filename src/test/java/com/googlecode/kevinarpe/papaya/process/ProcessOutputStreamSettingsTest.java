package com.googlecode.kevinarpe.papaya.process;

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

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.GenericFactory;
import com.googlecode.kevinarpe.papaya.appendable.AbstractSimplifiedAppendable;
import com.googlecode.kevinarpe.papaya.appendable.ByteAppendable;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.process.ProcessBuilder2;
import com.googlecode.kevinarpe.papaya.process.ProcessOutputStreamSettings;
import com.googlecode.kevinarpe.papaya.test.TestCharsetUtils;

public class ProcessOutputStreamSettingsTest {
    
    @DataProvider
    private static final Object[][] _dataForStdxxxSettings() {
        ProcessBuilder2 x = new ProcessBuilder2();
        return new Object[][] {
                { x.stdoutSettings() },
                { x.stderrSettings() },
        };
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessOutputStreamSettings.charset
    //
    
    @Test(dataProvider = "_dataForStdxxxSettings")
    public void charset_Pass(ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");
        
        Assert.assertEquals(x.charset(), Charset.defaultCharset());
        Charset cs = TestCharsetUtils.getRandomNonDefaultCharset();
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.charset(cs));
        Assert.assertEquals(x.charset(), cs);
    }
    
    @Test(dataProvider = "_dataForStdxxxSettings",
            expectedExceptions = NullPointerException.class)
    public void charset_FailWithNull(
            ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");
        
        x.charset(null);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessOutputStreamSettings.splitRegex
    //
    
    @Test(dataProvider = "_dataForStdxxxSettings")
    public void splitRegex_Pass(ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");
        
        Assert.assertEquals(
            x.splitRegex(),
            ProcessOutputStreamSettings.DEFAULT_SPLIT_REGEX);
        Pattern regex = Pattern.compile("abc");
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.splitRegex(regex));
        Assert.assertEquals(x.splitRegex(), regex);
        Assert.assertTrue(x == x.splitRegex(null));
        Assert.assertEquals(x.splitRegex(), null);
    }
    
    @Test(dataProvider = "_dataForStdxxxSettings",
            expectedExceptions = IllegalArgumentException.class)
    public void splitRegex_FailWithRegexToMatchEmptyString(
            ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");
        
        x.splitRegex(Pattern.compile("\\s*"));
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessOutputStreamSettings.charCallback & .charCallbackFactory
    //
    
    public static final Appendable SAMPLE_CHAR_CALLBACK =
        new AbstractSimplifiedAppendable() {
            @Override
            public Appendable append(CharSequence csq)
            throws IOException {
                return null;
            }
        };
    
    public static final GenericFactory<Appendable> SAMPLE_CHAR_CALLBACK_FACTORY =
        new GenericFactory<Appendable>() {
            @Override
            public Appendable create() {
                return null;
            }
        };
    
    @Test(dataProvider = "_dataForStdxxxSettings")
    public void charCallback_And_charCallbackFactory_Pass(ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");

        {
        // Test charCallback
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), null);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.charCallback(SAMPLE_CHAR_CALLBACK));
        Assert.assertEquals(x.charCallback(), SAMPLE_CHAR_CALLBACK);
        Assert.assertEquals(x.charCallbackFactory(), null);
        Assert.assertTrue(x == x.charCallback(null));
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), null);
        }
        
        {
        // Test charCallbackFactory
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), null);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.charCallbackFactory(SAMPLE_CHAR_CALLBACK_FACTORY));
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), SAMPLE_CHAR_CALLBACK_FACTORY);
        Assert.assertTrue(x == x.charCallbackFactory(null));
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), null);
        }
        
        {
        // Test charCallback & charCallbackFactory
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), null);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.charCallback(SAMPLE_CHAR_CALLBACK));
        Assert.assertEquals(x.charCallback(), SAMPLE_CHAR_CALLBACK);
        Assert.assertEquals(x.charCallbackFactory(), null);
        
        // Prove setting callback factory to null also clears callback.
        Assert.assertTrue(x == x.charCallbackFactory(null));
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), null);
        
        Assert.assertTrue(x == x.charCallback(SAMPLE_CHAR_CALLBACK));
        Assert.assertEquals(x.charCallback(), SAMPLE_CHAR_CALLBACK);
        Assert.assertEquals(x.charCallbackFactory(), null);
        
        // Prove setting callback factory to non-null also clears callback.
        Assert.assertTrue(x == x.charCallbackFactory(SAMPLE_CHAR_CALLBACK_FACTORY));
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), SAMPLE_CHAR_CALLBACK_FACTORY);
        
        // Prove setting callback to null also clears callback factory.
        Assert.assertTrue(x == x.charCallback(null));
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), null);
        
        Assert.assertTrue(x == x.charCallbackFactory(SAMPLE_CHAR_CALLBACK_FACTORY));
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), SAMPLE_CHAR_CALLBACK_FACTORY);
        
        // Prove setting callback to non-null also clears callback factory.
        Assert.assertTrue(x == x.charCallback(SAMPLE_CHAR_CALLBACK));
        Assert.assertEquals(x.charCallback(), SAMPLE_CHAR_CALLBACK);
        Assert.assertEquals(x.charCallbackFactory(), null);
        }
    }
    
    @Test(dataProvider = "_dataForStdxxxSettings",
            expectedExceptions = IllegalArgumentException.class)
    public void charCallback_FailWithStringBuilder(
            ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");
        
        x.charCallback(new StringBuilder());
    }
    
    @Test(dataProvider = "_dataForStdxxxSettings")
    public void charCallback_FailWithStringBuilderButCharCallbackFactoryUnchanged(
            ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");
        
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), null);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.charCallbackFactory(SAMPLE_CHAR_CALLBACK_FACTORY));
        Assert.assertEquals(x.charCallback(), null);
        Assert.assertEquals(x.charCallbackFactory(), SAMPLE_CHAR_CALLBACK_FACTORY);
        try {
            x.charCallback(new StringBuilder());
            throw new IllegalStateException("Previous line should have thrown an exception");
        }
        catch (IllegalArgumentException e) {
            Assert.assertEquals(x.charCallback(), null);
            Assert.assertEquals(x.charCallbackFactory(), SAMPLE_CHAR_CALLBACK_FACTORY);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessOutputStreamSettings.byteCallback & .byteCallbackFactory
    //
    
    public static final ByteAppendable SAMPLE_BYTE_CALLBACK =
        new ByteAppendable() {
            @Override
            public void append(byte[] byteArr)
            throws IOException {
                // do nothing
            }
        };
    
    public static final GenericFactory<ByteAppendable> SAMPLE_BYTE_CALLBACK_FACTORY =
        new GenericFactory<ByteAppendable>() {
            @Override
            public ByteAppendable create() {
                return null;
            }
        };
    
    @Test(dataProvider = "_dataForStdxxxSettings")
    public void byteCallback_And_byteCallbackFactory_Pass(ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");
        
        Assert.assertEquals(x.byteCallback(), null);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.byteCallback(SAMPLE_BYTE_CALLBACK));
        Assert.assertEquals(x.byteCallback(), SAMPLE_BYTE_CALLBACK);
        Assert.assertTrue(x == x.byteCallback(null));
        Assert.assertEquals(x.byteCallback(), null);
        
        {
        // Test byteCallback
        Assert.assertEquals(x.byteCallback(), null);
        Assert.assertEquals(x.byteCallbackFactory(), null);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.byteCallback(SAMPLE_BYTE_CALLBACK));
        Assert.assertEquals(x.byteCallback(), SAMPLE_BYTE_CALLBACK);
        Assert.assertEquals(x.byteCallbackFactory(), null);
        Assert.assertTrue(x == x.byteCallback(null));
        Assert.assertEquals(x.byteCallback(), null);
        Assert.assertEquals(x.byteCallbackFactory(), null);
        }
        
        {
        // Test byteCallbackFactory
        Assert.assertEquals(x.byteCallback(), null);
        Assert.assertEquals(x.byteCallbackFactory(), null);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.byteCallbackFactory(SAMPLE_BYTE_CALLBACK_FACTORY));
        Assert.assertEquals(x.byteCallback(), null);
        Assert.assertEquals(x.byteCallbackFactory(), SAMPLE_BYTE_CALLBACK_FACTORY);
        Assert.assertTrue(x == x.byteCallbackFactory(null));
        Assert.assertEquals(x.byteCallback(), null);
        Assert.assertEquals(x.byteCallbackFactory(), null);
        }
        
        {
        // Test byteCallback & byteCallbackFactory
        Assert.assertEquals(x.byteCallback(), null);
        Assert.assertEquals(x.byteCallbackFactory(), null);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.byteCallback(SAMPLE_BYTE_CALLBACK));
        Assert.assertEquals(x.byteCallback(), SAMPLE_BYTE_CALLBACK);
        Assert.assertEquals(x.byteCallbackFactory(), null);
        
        // Prove setting callback factory to null also clears callback.
        Assert.assertTrue(x == x.byteCallbackFactory(null));
        Assert.assertEquals(x.byteCallback(), null);
        Assert.assertEquals(x.byteCallbackFactory(), null);
        
        Assert.assertTrue(x == x.byteCallback(SAMPLE_BYTE_CALLBACK));
        Assert.assertEquals(x.byteCallback(), SAMPLE_BYTE_CALLBACK);
        Assert.assertEquals(x.byteCallbackFactory(), null);
        
        // Prove setting callback factory to non-null also clears callback.
        Assert.assertTrue(x == x.byteCallbackFactory(SAMPLE_BYTE_CALLBACK_FACTORY));
        Assert.assertEquals(x.byteCallback(), null);
        Assert.assertEquals(x.byteCallbackFactory(), SAMPLE_BYTE_CALLBACK_FACTORY);
        
        // Prove setting callback to null also clears callback factory.
        Assert.assertTrue(x == x.byteCallback(null));
        Assert.assertEquals(x.byteCallback(), null);
        Assert.assertEquals(x.byteCallbackFactory(), null);
        
        Assert.assertTrue(x == x.byteCallbackFactory(SAMPLE_BYTE_CALLBACK_FACTORY));
        Assert.assertEquals(x.byteCallback(), null);
        Assert.assertEquals(x.byteCallbackFactory(), SAMPLE_BYTE_CALLBACK_FACTORY);
        
        // Prove setting callback to non-null also clears callback factory.
        Assert.assertTrue(x == x.byteCallback(SAMPLE_BYTE_CALLBACK));
        Assert.assertEquals(x.byteCallback(), SAMPLE_BYTE_CALLBACK);
        Assert.assertEquals(x.byteCallbackFactory(), null);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessOutputStreamSettings.isDataAccumulated
    //
    
    @Test(dataProvider = "_dataForStdxxxSettings")
    public void isDataAccumulated_Pass(
            ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");
        
        Assert.assertEquals(
            x.isDataAccumulated(),
            ProcessOutputStreamSettings.DEFAULT_IS_DATA_ACCUMULATED);
        boolean b = !ProcessOutputStreamSettings.DEFAULT_IS_DATA_ACCUMULATED;
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.isDataAccumulated(b));
        Assert.assertEquals(x.isDataAccumulated(), b);
        Assert.assertTrue(x == x.isDataAccumulated(!b));
        Assert.assertEquals(x.isDataAccumulated(), !b);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ProcessOutputStreamSettings.maxAccumulatedDataByteCount
    //
    
    @Test(dataProvider = "_dataForStdxxxSettings")
    public void maxAccumulatedDataByteCount_Pass(
            ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");
        
        Assert.assertEquals(
            x.maxAccumulatedDataByteCount(),
            ProcessOutputStreamSettings.DEFAULT_MAX_ACCUMULATED_DATA_BYTE_COUNT);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == x.maxAccumulatedDataByteCount(25));
        Assert.assertEquals(x.maxAccumulatedDataByteCount(), 25);
        Assert.assertTrue(
            x == x.maxAccumulatedDataByteCount(
                    ProcessOutputStreamSettings.DEFAULT_MAX_ACCUMULATED_DATA_BYTE_COUNT));
        Assert.assertEquals(
            x.maxAccumulatedDataByteCount(),
            ProcessOutputStreamSettings.DEFAULT_MAX_ACCUMULATED_DATA_BYTE_COUNT);
    }
    
    @Test(dataProvider = "_dataForStdxxxSettings",
            expectedExceptions = IllegalArgumentException.class)
    public void maxAccumulatedDataByteCount_FailWithZero(
            ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");
        
        x.maxAccumulatedDataByteCount(0);
    }
}
