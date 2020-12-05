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

import com.github.kklisura.cdt.protocol.commands.Input;
import com.github.kklisura.cdt.protocol.types.input.DispatchKeyEventType;
import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.annotation.DebugBreakpoint;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.exception.ExceptionThrower;

import javax.annotation.Nullable;

/**
 * FullyTested?  Some code below is impossible to test in a deterministic manner.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Global singleton
@FullyTested
public final class ChromeDevToolsInputServiceImp
implements ChromeDevToolsInputService {

    // package-private for testing
    static final ImmutableMap<Character, _Data> charMap;

    private final ExceptionThrower exceptionThrower;

    public ChromeDevToolsInputServiceImp(ExceptionThrower exceptionThrower) {

        this.exceptionThrower = ObjectArgs.checkNotNull(exceptionThrower, "exceptionThrower");
    }

    // Original Google Group discussion about "send keys": https://groups.google.com/g/chrome-debugging-protocol/c/DQxlrBNSC9w?pli=1
    /** {@inheritDoc} */
    @Override
    public void
    sendKeys(Input chromeDevToolsInput,
             DispatchKeyEventType type,
             String text)
    throws Exception {

        ObjectArgs.checkNotNull(type, "type");
        StringArgs.checkNotEmpty(text, "text");

        final int len = text.length();
        for (int i = 0; i < len; ++i) {

            final char ch = text.charAt(i);
            @Nullable
            final _Data data = charMap.get(ch);
            if (null == data) {

                throw exceptionThrower.throwCheckedException(Exception.class,
                    "Internal error: Unsupported char (%c/%d) at index %d in text [%s]", ch, (int) ch, i, text);
            }

            try {
                // Ref: https://chromedevtools.github.io/devtools-protocol/tot/Input/#method-dispatchKeyEvent
                chromeDevToolsInput.dispatchKeyEvent(type, data.nullableModifiers, data.nullableTimestamp,
                    data.nullableText, data.nullableUnmodifiedText, data.nullableKeyIdentifier, data.nullableCode,
                    data.nullableKey, data.nullableWindowsVirtualKeyCode, data.nullableNativeVirtualKeyCode,
                    data.nullableAutoRepeat, data.nullableIsKeypad, data.nullableIsSystemKey, data.nullableLocation);
            }
            catch (Exception e) {
                throw exceptionThrower.throwChainedCheckedException(Exception.class,
                    e,
                    "Failed to send char (%c/%d) at index %d in text [%s]", ch, (int) ch, i, text);
            }
        }
        @DebugBreakpoint
        int dummy = 1;
    }

    /* Ref: https://godoc.org/github.com/knq/chromedp/kb#Key
type Key struct {
// Code is the key code:
// 								"Enter"     | "Comma"     | "KeyA"     | "MediaStop"
Code string

// Key is the key value:
// 								"Enter"     | ","   "<"   | "a"   "A"  | "MediaStop"
Key string

// Text is the text for printable keys:
// 								"\r"  "\r"  | ","   "<"   | "a"   "A"  | ""
Text string

// Unmodified is the unmodified text for printable keys:
// 								"\r"  "\r"  | ","   ","   | "a"   "a"  | ""
Unmodified string

// Native is the native scan code.
// 								0x13  0x13  | 0xbc  0xbc  | 0x61  0x41 | 0x00ae
Native int64

// Windows is the windows scan code.
// 								0x13  0x13  | 0xbc  0xbc  | 0x61  0x41 | 0xe024
Windows int64

// Shift indicates whether or not the Shift modifier should be sent.
// 								false false | false true  | false true | false
Shift bool

// Print indicates whether or not the character is a printable character
// (ie, should a "char" event be generated).
// 								true  true  | true  true  | true  true | false
Print bool
}
     */
    static {
        final ImmutableMap.Builder<Character, _Data> b = ImmutableMap.builder();
        // Ref: https://github.com/chromedp/chromedp/blob/master/kb/keys.go
        // unix$ perl -p -e 's/^\s*('\''.+'\'')\s*:\s*\{(".*")\s*,\s*(".*")\s*,\s*(".*")\s*,\s*(".*")\s*,\s*(\d+)\s*,\s*(\d+)\s*,\s*.*$/b.put($1, _Data.create($4, $5, $2, $3, $6, $7));/' keys.go
        b.put('\b', _Data.create("", "", "Backspace", "Backspace", 8, 8));
        b.put('\t', _Data.create("", "", "Tab", "Tab", 9, 9));
        {
            final _Data data = _Data.create("\r", "\r", "Enter", "Enter", 13, 13);
            b.put('\r', data);
            // Intentional: Be nice -- also treat '\n' as "Enter" key
            b.put('\n', data);
        }
        b.put('\u001b', _Data.create("", "", "Escape", "Escape", 27, 27));
        b.put(' ', _Data.create(" ", " ", "Space", " ", 32, 32));
        b.put('!', _Data.create("!", "1", "Digit1", "!", 49, 49));
        b.put('"', _Data.create("\"", "'", "Quote", "\"", 222, 222));
        b.put('#', _Data.create("#", "3", "Digit3", "#", 51, 51));
        b.put('$', _Data.create("$", "4", "Digit4", "$", 52, 52));
        b.put('%', _Data.create("%", "5", "Digit5", "%", 53, 53));
        b.put('&', _Data.create("&", "7", "Digit7", "&", 55, 55));
        b.put('\'', _Data.create("'", "'", "Quote", "'", 222, 222));
        b.put('(', _Data.create("(", "9", "Digit9", "(", 57, 57));
        b.put(')', _Data.create(")", "0", "Digit0", ")", 48, 48));
        b.put('*', _Data.create("*", "8", "Digit8", "*", 56, 56));
        b.put('+', _Data.create("+", "=", "Equal", "+", 187, 187));
        b.put(',', _Data.create(",", ",", "Comma", ",", 188, 188));
        b.put('-', _Data.create("-", "-", "Minus", "-", 189, 189));
        b.put('.', _Data.create(".", ".", "Period", ".", 190, 190));
        b.put('/', _Data.create("/", "/", "Slash", "/", 191, 191));
        b.put('0', _Data.create("0", "0", "Digit0", "0", 48, 48));
        b.put('1', _Data.create("1", "1", "Digit1", "1", 49, 49));
        b.put('2', _Data.create("2", "2", "Digit2", "2", 50, 50));
        b.put('3', _Data.create("3", "3", "Digit3", "3", 51, 51));
        b.put('4', _Data.create("4", "4", "Digit4", "4", 52, 52));
        b.put('5', _Data.create("5", "5", "Digit5", "5", 53, 53));
        b.put('6', _Data.create("6", "6", "Digit6", "6", 54, 54));
        b.put('7', _Data.create("7", "7", "Digit7", "7", 55, 55));
        b.put('8', _Data.create("8", "8", "Digit8", "8", 56, 56));
        b.put('9', _Data.create("9", "9", "Digit9", "9", 57, 57));
        b.put(':', _Data.create(":", ";", "Semicolon", ":", 186, 186));
        b.put(';', _Data.create(";", ";", "Semicolon", ";", 186, 186));
        b.put('<', _Data.create("<", ",", "Comma", "<", 188, 188));
        b.put('=', _Data.create("=", "=", "Equal", "=", 187, 187));
        b.put('>', _Data.create(">", ".", "Period", ">", 190, 190));
        b.put('?', _Data.create("?", "/", "Slash", "?", 191, 191));
        b.put('@', _Data.create("@", "2", "Digit2", "@", 50, 50));
        b.put('A', _Data.create("A", "a", "KeyA", "A", 65, 65));
        b.put('B', _Data.create("B", "b", "KeyB", "B", 66, 66));
        b.put('C', _Data.create("C", "c", "KeyC", "C", 67, 67));
        b.put('D', _Data.create("D", "d", "KeyD", "D", 68, 68));
        b.put('E', _Data.create("E", "e", "KeyE", "E", 69, 69));
        b.put('F', _Data.create("F", "f", "KeyF", "F", 70, 70));
        b.put('G', _Data.create("G", "g", "KeyG", "G", 71, 71));
        b.put('H', _Data.create("H", "h", "KeyH", "H", 72, 72));
        b.put('I', _Data.create("I", "i", "KeyI", "I", 73, 73));
        b.put('J', _Data.create("J", "j", "KeyJ", "J", 74, 74));
        b.put('K', _Data.create("K", "k", "KeyK", "K", 75, 75));
        b.put('L', _Data.create("L", "l", "KeyL", "L", 76, 76));
        b.put('M', _Data.create("M", "m", "KeyM", "M", 77, 77));
        b.put('N', _Data.create("N", "n", "KeyN", "N", 78, 78));
        b.put('O', _Data.create("O", "o", "KeyO", "O", 79, 79));
        b.put('P', _Data.create("P", "p", "KeyP", "P", 80, 80));
        b.put('Q', _Data.create("Q", "q", "KeyQ", "Q", 81, 81));
        b.put('R', _Data.create("R", "r", "KeyR", "R", 82, 82));
        b.put('S', _Data.create("S", "s", "KeyS", "S", 83, 83));
        b.put('T', _Data.create("T", "t", "KeyT", "T", 84, 84));
        b.put('U', _Data.create("U", "u", "KeyU", "U", 85, 85));
        b.put('V', _Data.create("V", "v", "KeyV", "V", 86, 86));
        b.put('W', _Data.create("W", "w", "KeyW", "W", 87, 87));
        b.put('X', _Data.create("X", "x", "KeyX", "X", 88, 88));
        b.put('Y', _Data.create("Y", "y", "KeyY", "Y", 89, 89));
        b.put('Z', _Data.create("Z", "z", "KeyZ", "Z", 90, 90));
        b.put('[', _Data.create("[", "[", "BracketLeft", "[", 219, 219));
        b.put('\\', _Data.create("\\", "\\", "Backslash", "\\", 220, 220));
        b.put(']', _Data.create("]", "]", "BracketRight", "]", 221, 221));
        b.put('^', _Data.create("^", "6", "Digit6", "^", 54, 54));
        b.put('_', _Data.create("_", "-", "Minus", "_", 189, 189));
        b.put('`', _Data.create("`", "`", "Backquote", "`", 192, 192));
        b.put('a', _Data.create("a", "a", "KeyA", "a", 65, 65));
        b.put('b', _Data.create("b", "b", "KeyB", "b", 66, 66));
        b.put('c', _Data.create("c", "c", "KeyC", "c", 67, 67));
        b.put('d', _Data.create("d", "d", "KeyD", "d", 68, 68));
        b.put('e', _Data.create("e", "e", "KeyE", "e", 69, 69));
        b.put('f', _Data.create("f", "f", "KeyF", "f", 70, 70));
        b.put('g', _Data.create("g", "g", "KeyG", "g", 71, 71));
        b.put('h', _Data.create("h", "h", "KeyH", "h", 72, 72));
        b.put('i', _Data.create("i", "i", "KeyI", "i", 73, 73));
        b.put('j', _Data.create("j", "j", "KeyJ", "j", 74, 74));
        b.put('k', _Data.create("k", "k", "KeyK", "k", 75, 75));
        b.put('l', _Data.create("l", "l", "KeyL", "l", 76, 76));
        b.put('m', _Data.create("m", "m", "KeyM", "m", 77, 77));
        b.put('n', _Data.create("n", "n", "KeyN", "n", 78, 78));
        b.put('o', _Data.create("o", "o", "KeyO", "o", 79, 79));
        b.put('p', _Data.create("p", "p", "KeyP", "p", 80, 80));
        b.put('q', _Data.create("q", "q", "KeyQ", "q", 81, 81));
        b.put('r', _Data.create("r", "r", "KeyR", "r", 82, 82));
        b.put('s', _Data.create("s", "s", "KeyS", "s", 83, 83));
        b.put('t', _Data.create("t", "t", "KeyT", "t", 84, 84));
        b.put('u', _Data.create("u", "u", "KeyU", "u", 85, 85));
        b.put('v', _Data.create("v", "v", "KeyV", "v", 86, 86));
        b.put('w', _Data.create("w", "w", "KeyW", "w", 87, 87));
        b.put('x', _Data.create("x", "x", "KeyX", "x", 88, 88));
        b.put('y', _Data.create("y", "y", "KeyY", "y", 89, 89));
        b.put('z', _Data.create("z", "z", "KeyZ", "z", 90, 90));
        b.put('{', _Data.create("{", "[", "BracketLeft", "{", 219, 219));
        b.put('|', _Data.create("|", "\\", "Backslash", "|", 220, 220));
        b.put('}', _Data.create("}", "]", "BracketRight", "}", 221, 221));
        b.put('~', _Data.create("~", "`", "Backquote", "~", 192, 192));
        b.put('\u007f', _Data.create("", "", "Delete", "Delete", 46, 46));

        charMap = b.build();
    }

    // package-private for testing
    static final class _Data {

        /**
         * @param text
         *        Ex: {@code "Z"}
         *
         * @param unmodifiedText
         *        Ex: {@code "z"}
         *
         * @param code
         *        Ex: {@code "KeyZ"}
         *
         * @param key
         *        Ex: {@code "Z"}
         *
         * @param windowsVirtualKeyCode
         *        Ex: 90
         *
         * @param nativeVirtualKeyCode
         *        Ex: 90
         */
        private static _Data
        create(String text,
               String unmodifiedText,
               String code,
               String key,
               final int windowsVirtualKeyCode,
               final int nativeVirtualKeyCode) {

            final _Data x =
                new _Data(null, null, text, unmodifiedText,
                    null, code, key, windowsVirtualKeyCode, nativeVirtualKeyCode,
                    null, null, null, null);
            return x;
        }

        /** Ex: {@code null} */
        @Nullable
        public final Integer nullableModifiers;
        /** Ex: {@code null} */
        @Nullable
        public final Double nullableTimestamp;
        /** Ex: {@code "Z"} */
        @Nullable
        public final String nullableText;
        /** Ex: {@code "z"} */
        @Nullable
        public final String nullableUnmodifiedText;
        /** Ex: {@code null} */
        @Nullable
        public final String nullableKeyIdentifier;
        /** Ex: {@code "KeyZ"} */
        @Nullable
        public final String nullableCode;
        /** Ex: {@code "Z"} */
        @Nullable
        public final String nullableKey;
        /** Ex: 90 */
        @Nullable
        public final Integer nullableWindowsVirtualKeyCode;
        /** Ex: 90 */
        @Nullable
        public final Integer nullableNativeVirtualKeyCode;
        /** Ex: {@code null} */
        @Nullable
        public final Boolean nullableAutoRepeat;
        /** Ex: {@code null} */
        @Nullable
        public final Boolean nullableIsKeypad;
        /** Ex: {@code null} */
        @Nullable
        public final Boolean nullableIsSystemKey;
        /** Ex: {@code null} */
        @Nullable
        public final Integer nullableLocation;

        private _Data(@Nullable Integer nullableModifiers,
                      @Nullable Double nullableTimestamp,
                      @Nullable String nullableText,
                      @Nullable String nullableUnmodifiedText,
                      @Nullable String nullableKeyIdentifier,
                      @Nullable String nullableCode,
                      @Nullable String nullableKey,
                      @Nullable Integer nullableWindowsVirtualKeyCode,
                      @Nullable Integer nullableNativeVirtualKeyCode,
                      @Nullable Boolean nullableAutoRepeat,
                      @Nullable Boolean nullableIsKeypad,
                      @Nullable Boolean nullableIsSystemKey,
                      @Nullable Integer nullableLocation) {

            this.nullableModifiers = nullableModifiers;
            this.nullableTimestamp = nullableTimestamp;
            this.nullableText = nullableText;
            this.nullableUnmodifiedText = nullableUnmodifiedText;
            this.nullableKeyIdentifier = nullableKeyIdentifier;
            this.nullableCode = nullableCode;
            this.nullableKey = nullableKey;
            this.nullableWindowsVirtualKeyCode = nullableWindowsVirtualKeyCode;
            this.nullableNativeVirtualKeyCode = nullableNativeVirtualKeyCode;
            this.nullableAutoRepeat = nullableAutoRepeat;
            this.nullableIsKeypad = nullableIsKeypad;
            this.nullableIsSystemKey = nullableIsSystemKey;
            this.nullableLocation = nullableLocation;
        }
    }
}
