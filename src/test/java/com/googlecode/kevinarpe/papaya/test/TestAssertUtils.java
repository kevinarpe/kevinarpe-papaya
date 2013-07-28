package com.googlecode.kevinarpe.papaya.test;

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

public final class TestAssertUtils {

    // Disable default constructor
    private TestAssertUtils() {
    }
    
    public static final int DEFAULT_MAX_SAMPLE_LENGTH_FOR_ERROR = 1024;
    
    public static void assertHugeStringEquals(String actual, String expected) {
        assertHugeStringEquals(actual, expected, DEFAULT_MAX_SAMPLE_LENGTH_FOR_ERROR);
    }
    
    public static void assertHugeStringEquals(
            String actual, String expected, int maxSampleLengthForError) {
        if (null == actual && null == expected) {
            return;
        }
        if (null == actual && null != expected) {
            String s = String.format("Actual is null, but expected is not null:%nExpected: %s",
                _getSample(expected, 0, maxSampleLengthForError));
            throw new AssertionError(s);
        }
        if (null != actual && null == expected) {
            String s = String.format("Actual is not null, but expected is null:%nActual: %s",
                _getSample(actual, 0, maxSampleLengthForError));
            throw new AssertionError(s);
        }
        if (actual.equals(expected)) {
            return;
        }
        int actualLen = actual.length();
        int expectedLen = expected.length();
        for (int i = 0; i < actualLen && i < expectedLen; ++i) {
            char ch1 = actual.charAt(i);
            char ch2 = expected.charAt(i);
            if (ch1 != ch2) {
                String s = String.format("Actual and expected differ at char index %d:"
                    + "%nActual  : %s"
                    + "%nExpected: %s",
                    i,
                    _getSample(actual, i, maxSampleLengthForError),
                    _getSample(expected, i, maxSampleLengthForError));
                throw new AssertionError(s);
            }
        }
        String s = String.format("Actual and expected differ only in length:"
            + "%nActual  : %s"
            + "%nExpected: %s",
            actualLen,
            _getSample(actual, 0, maxSampleLengthForError),
            expectedLen,
            _getSample(expected, 0, maxSampleLengthForError));
        throw new AssertionError(s);
    }
    
    /*
java.lang.AssertionError: Actual and expected differ at char index 4000014:
Actual  : (length: 8000028): (sample of 1024 chars from index 4000014) 'Z909a-cf00b10194d4e5140629-961a-4c8b-91e3-88565fb94a960d6039e7-42b1-4675-aa36-a277f998aa29f4a1ff9a-a988-4c57-8575-97a8a764a2a3c6971a1f-2044-42fe-a4a3-e21018cf17870248e667-f4de-4aa3-970a-48533533108924ae2c14-4d20-4538-b958-ca56f4adc8db4c478b50-4a92-4c44-a037-ee378fadd29b3c590d5e-a5dd-476b-8fcf-c390698896b1fdffd677-c435-4028-be71-beba7634a428191a465a-ad51-4f34-bb3e-4ffbd048105812ae60b8-a5a6-40cc-bdc2-84d55899b5679a05be27-af13-4924-a4ad-747383224b0854e0d5b5-05d9-43ab-90ee-118f0155c0e35fde6325-6e16-445c-94ab-fcfdd575369b7554d7ec-77dc-4c04-8eab-6fde39fc2cb6954c67f2-5a41-4ba9-bedb-eebb5791dfc640c91f3d-b095-447b-bdcd-4438a7379e0bcbeea675-168b-46ef-881d-6f834c89290ab07abecc-fe7f-40c8-a395-55939c261e2d8a8812c2-0a22-48a7-ba09-3742c42c76ddc0a6f433-941c-47cd-a425-f4cba66d61a4dad58c54-5a1c-4832-8e97-bc265edc3d20300382d7-621b-412a-bd37-5d54909c1bbbeeb0c8c9-5782-4af1-8957-fb7dda33d710900b95bf-ee3c-4db6-94f3-89b2bf3f72ea1f5020d5-a58f-4688-a522-35f463f3c48fe77c8cec-5d0a-43d3-afbf-a98f62829a21cbf1b5c9-4517-4b22-93bf-035ad3f10e'
Expected: (length: 8000028): (sample of 1024 chars from index 4000014) '-909a-cf00b10194d4e5140629-961a-4c8b-91e3-88565fb94a960d6039e7-42b1-4675-aa36-a277f998aa29f4a1ff9a-a988-4c57-8575-97a8a764a2a3c6971a1f-2044-42fe-a4a3-e21018cf17870248e667-f4de-4aa3-970a-48533533108924ae2c14-4d20-4538-b958-ca56f4adc8db4c478b50-4a92-4c44-a037-ee378fadd29b3c590d5e-a5dd-476b-8fcf-c390698896b1fdffd677-c435-4028-be71-beba7634a428191a465a-ad51-4f34-bb3e-4ffbd048105812ae60b8-a5a6-40cc-bdc2-84d55899b5679a05be27-af13-4924-a4ad-747383224b0854e0d5b5-05d9-43ab-90ee-118f0155c0e35fde6325-6e16-445c-94ab-fcfdd575369b7554d7ec-77dc-4c04-8eab-6fde39fc2cb6954c67f2-5a41-4ba9-bedb-eebb5791dfc640c91f3d-b095-447b-bdcd-4438a7379e0bcbeea675-168b-46ef-881d-6f834c89290ab07abecc-fe7f-40c8-a395-55939c261e2d8a8812c2-0a22-48a7-ba09-3742c42c76ddc0a6f433-941c-47cd-a425-f4cba66d61a4dad58c54-5a1c-4832-8e97-bc265edc3d20300382d7-621b-412a-bd37-5d54909c1bbbeeb0c8c9-5782-4af1-8957-fb7dda33d710900b95bf-ee3c-4db6-94f3-89b2bf3f72ea1f5020d5-a58f-4688-a522-35f463f3c48fe77c8cec-5d0a-43d3-afbf-a98f62829a21cbf1b5c9-4517-4b22-93bf-035ad3f10e'
     */
    
    private static String _getSample(String hugeString, int startIndex, int maxSampleLength) {
        int hugeStringLen = hugeString.length();
        int endIndex = Math.min(hugeStringLen, startIndex + maxSampleLength);
        String sample = String.format("'%s'", hugeString.substring(startIndex, endIndex));
        int sampleLen = sample.length();
        if (sampleLen > maxSampleLength) {
            if (0 == startIndex) {
                sample = String.format("(sample of %d chars) %s", endIndex - startIndex, sample);
            }
            else {
                sample = String.format("(sample of %d chars from index %d) %s",
                    endIndex - startIndex, startIndex, sample);
            }
        }
        else if (0 != startIndex) {
            sample = String.format("(from index %d) %s", startIndex, sample);
        }
        sample = String.format("(length: %d): %s", hugeStringLen, sample);
        return sample;
    }
}
