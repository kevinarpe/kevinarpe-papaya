package com.googlecode.kevinarpe.papaya.logging.slf4j.mock;

import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JMarkerNone;
import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JMockLoggerConfigImplTest {

    private static class _SampleMarker
    implements Marker {

        @Override
        public String getName() {
            return null;
        }

        @Override
        public void add(Marker reference) {

        }

        @Override
        public boolean remove(Marker reference) {
            return false;
        }

        @Override
        public boolean hasChildren() {
            return false;
        }

        @Override
        public boolean hasReferences() {
            return false;
        }

        @Override
        public Iterator iterator() {
            return null;
        }

        @Override
        public boolean contains(Marker other) {
            return false;
        }

        @Override
        public boolean contains(String name) {
            return false;
        }
    }

    private SLF4JMockLoggerConfigImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new SLF4JMockLoggerConfigImpl();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private void _assertDefaultIsEnabled(SLF4JMockLoggerConfigImpl config) {
        for (SLF4JLogLevel logLevel : SLF4JLogLevel.values()) {
            assertEquals(
                config.isEnabled(logLevel),
                SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);
        }
        for (SLF4JLogLevel logLevel : SLF4JLogLevel.values()) {
            assertEquals(
                config.isEnabled(SLF4JMockLoggerConfigImpl.DEFAULT_MARKER, logLevel),
                SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);
        }
    }

    private void _whenSetEnabled(SLF4JMockLoggerConfigImpl config, _SampleMarker sampleMarker) {
        config.setEnabled(
            SLF4JLogLevel.TRACE, !SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);
        config.setEnabled(
            sampleMarker, SLF4JLogLevel.TRACE, !SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);
        config.setEnabled(
            sampleMarker, SLF4JLogLevel.DEBUG, !SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);
    }

    private void _assertIsEnabled(SLF4JMockLoggerConfigImpl config, _SampleMarker sampleMarker) {
        assertEquals(
            config.isEnabled(SLF4JLogLevel.TRACE),
            !SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);
        assertEquals(
            config.isEnabled(sampleMarker, SLF4JLogLevel.TRACE),
            !SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);
        assertEquals(
            config.isEnabled(sampleMarker, SLF4JLogLevel.DEBUG),
            !SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerConfigImpl.ctor()
    //

    @Test
    public void ctor_Pass() {
        _assertDefaultIsEnabled(classUnderTest);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerConfigImpl.ctor(SLF4JMockLoggerConfigImpl)
    //

    @Test
    public void ctor2_Pass() {
        _SampleMarker sampleMarker = new _SampleMarker();
        _whenSetEnabled(classUnderTest, sampleMarker);

        SLF4JMockLoggerConfigImpl classUnderTestCopy =
            new SLF4JMockLoggerConfigImpl(classUnderTest);
        _assertIsEnabled(classUnderTestCopy, sampleMarker);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor2_FailWithNull() {
        new SLF4JMockLoggerConfigImpl((SLF4JMockLoggerConfigImpl) null);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerConfigImpl.isEnabled(SLF4JLogLevel)
    //

    @Test
    public void isEnabled_Pass() {
        _assertDefaultIsEnabled(classUnderTest);
        _SampleMarker sampleMarker = new _SampleMarker();
        for (SLF4JLogLevel logLevel : SLF4JLogLevel.values()) {
            assertEquals(
                classUnderTest.isEnabled(sampleMarker, logLevel),
                SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void isEnabled_FailWithNull() {
        classUnderTest.isEnabled((SLF4JLogLevel) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerConfigImpl.isEnabled(Marker, SLF4JLogLevel)
    //

    @Test
    public void isEnabledMarker_Pass() {
        _assertDefaultIsEnabled(classUnderTest);
        _SampleMarker sampleMarker = new _SampleMarker();
        for (SLF4JLogLevel logLevel : SLF4JLogLevel.values()) {
            assertEquals(
                classUnderTest.isEnabled(sampleMarker, logLevel),
                SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);
        }
    }

    @DataProvider
    private static Object[][] _isEnabledMarker_FailWithNull_Data() {
        return new Object[][] {
            { SLF4JMarkerNone.INSTANCE, (SLF4JLogLevel) null },
            { (Marker) null, SLF4JLogLevel.INFO },
            { (Marker) null, (SLF4JLogLevel) null },
        };
    }

    @Test(dataProvider = "_isEnabledMarker_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void isEnabledMarker_FailWithNull(Marker marker, SLF4JLogLevel logLevel) {
        classUnderTest.isEnabled(marker, logLevel);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerConfigImpl.setEnabled(Marker, SLF4JLogLevel, boolean)
    //

    @Test
    public void setEnabledMarker_Pass() {
        _SampleMarker sampleMarker = new _SampleMarker();
        _whenSetEnabled(classUnderTest, sampleMarker);
        _assertIsEnabled(classUnderTest, sampleMarker);
    }

    @DataProvider
    private static Object[][] _setEnabledMarker_FailWithNull_Data() {
        return new Object[][] {
            { SLF4JMarkerNone.INSTANCE, (SLF4JLogLevel) null, true },
            { (Marker) null, SLF4JLogLevel.INFO, true },
            { (Marker) null, (SLF4JLogLevel) null, true },

            { SLF4JMarkerNone.INSTANCE, (SLF4JLogLevel) null, false },
            { (Marker) null, SLF4JLogLevel.INFO, false },
            { (Marker) null, (SLF4JLogLevel) null, false },
        };
    }

    @Test(dataProvider = "_setEnabledMarker_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void setEnabledMarker_FailWithNull(
            Marker marker, SLF4JLogLevel logLevel, boolean isEnabled) {
        classUnderTest.setEnabled(marker, logLevel, isEnabled);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerConfigImpl.copy()
    //

    @Test
    public void copy_Pass() {
        _SampleMarker sampleMarker = new _SampleMarker();
        _whenSetEnabled(classUnderTest, sampleMarker);
        _assertIsEnabled(classUnderTest, sampleMarker);

        SLF4JMockLoggerConfigImpl classUnderTestCopy = classUnderTest.copy();
        _assertIsEnabled(classUnderTestCopy, sampleMarker);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerConfigImpl.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        _SampleMarker sampleMarker = new _SampleMarker();
        // This looks a bit weird to the casual reader.
        // Each call to EqualsTester.addEqualityGroup() expects objects that are equal/same hash
        // code.  However, values between calls to addEqualityGroup() must not be equals.
        // Get your head around that.
        // Why call new AbstractLexicographicalComparatorImpl(cs) twice?  If hashCode is badly implemented, it will fail.
        // Example: The default hashCode implementation might just return the internal object ID.
        EqualsTester equalsTester = new EqualsTester();
        equalsTester.addEqualityGroup(
            new SLF4JMockLoggerConfigImpl(),
            new SLF4JMockLoggerConfigImpl());
        equalsTester.addEqualityGroup(
            _newInstance(sampleMarker),
            _newInstance(sampleMarker));
        equalsTester.testEquals();
    }

    private SLF4JMockLoggerConfigImpl _newInstance(_SampleMarker sampleMarker) {
        SLF4JMockLoggerConfigImpl x = new SLF4JMockLoggerConfigImpl();
        _whenSetEnabled(x, sampleMarker);
        return x;
    }
}
