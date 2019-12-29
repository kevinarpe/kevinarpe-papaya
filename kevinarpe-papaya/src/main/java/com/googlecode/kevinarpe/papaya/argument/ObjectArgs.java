package com.googlecode.kevinarpe.papaya.argument;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

/**
 * This is a collection of static methods to check arguments received by methods.  They range from
 * the very simple but venerable {@link #checkNotNull(Object, String)} to much more complex.
 * The goal is to provide a wide range of checks that produce readable, detailed errors.
 * <p>
 * A few examples:
 * <ul>
 *   <li>{@link CollectionArgs#checkIndexAndCount(java.util.Collection, int, int, String, String, String)}</li>
 *   <li>{@link ArrayArgs#checkNotEmptyAndElementsNotNull(Object[], String)}
 *   <li>{@link MapArgs#checkKeysAndValuesNotNull(java.util.Map, String)}</li>
 * </ul>
 * <p>
 * Like {@link com.google.common.base.Preconditions}, most methods return the validated input.
 * Javadocs carefully document all exceptions, both checked and unchecked, that may be
 * thrown under various scenarios.
 * <p>
 * With the exception of {@link PathArgs} (and possibly a few others), all methods throw unchecked
 * exceptions -- {@link RuntimeException} and its subclasses.  Most frequently,
 * {@link IllegalArgumentException} is thrown.
 * <pre>{@code
 *     public void myMethod(List<String> strList) {
 *         this._strList = ObjectsArgs.checkNotNull(strList, "strList");
 *         // Do work here.
 *     }}</pre>
 * <b>Argument Names</b> ({@code argName}, {@code indexArgName}, {@code countArgName}, etc.)
 * <p>
 * Argument names should not be null, empty (""), or only whitespace ("   ").  This would undermine
 * efforts to report useful exception messages.  If (and only if) a check fails and the argument
 * name is considered invalid, additional text is appended to the exception message in the form of
 * a warning.  Example: {@code "WARNING: Argument name 'indexArgName' is null"}
 * <p>
 * This will alert developers about deficiencies in arguments passed to the check method without
 * changing the behavior of the validation rules.
 * <p>
 * Finally, a note about my motivation to create this package of classes:
 * I have written similiar argument checking routines libraries in C, C++, Perl, Python,
 * JavaScript, VBA, C#, and Java over the years.  It's time to put these ideas into open source.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ObjectArgs {
    
    // Disable default constructor
    private ObjectArgs() {
    }

    /**
     * Tests if an object reference passed as an argument is not null.
     * 
     * @param ref
     *        an object reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated object reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     *         
     * @see #checkIsNull(Object, String)
     */
    @FullyTested
    public static <T> T checkNotNull(T ref, String argName) {
        if (null == ref) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            throw new NullPointerException(String.format("Argument '%s' is null%s", argName, w));
        }
        return ref;
    }

    /**
     * Tests if an object reference is {@code null}.
     *
     * @param ref
     *        an object reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated object reference -- <b>always</b> {@code null}!
     *
     * @throws IllegalArgumentException
     *         if {@code ref} is not {@code null}
     *
     * @see #checkNotNull(Object, String)
     */
    @FullyTested
    public static <T> T checkIsNull(T ref, String argName) {
        if (null != ref) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            throw new IllegalArgumentException(String.format("Argument '%s' is not null%s", argName, w));
        }
        return ref;
    }

    /**
     * This is a convenience method for {@link #checkInstanceOfType(Object, Class, String, String)}
     * where {@code destClassArgName = "destClass"}.
     */
    @FullyTested
    public static <T> T checkInstanceOfType(T ref, Class<?> destClass, String refArgName) {
        checkInstanceOfType(ref, destClass, refArgName, "destClass");
        return ref;
    }
    
    /**
     * Tests if a reference can be safely cast to another type.  If you only have {@link Class}
     * reference and not an object reference, see
     * {@link #checkAssignableToType(Class, Class, String, String)}.
     * <p>
     * Type cast rules are defined by {@link Class#isInstance(Object)}.
     * <p>
     * Examples of <b>valid</b> type casts:
     * <pre>
     * String str = "abc";
     * CharSequence seq = str;  // OK 
     * CharSequence seq2 = (CharSequence) str;  // OK 
     * CharSequence seq3 = (CharSequence) ((Object) str);  // OK 
     * </pre>
     * Examples of <b>invalid</b> type casts:
     * <pre>
     * String str = "abc";
     * Long num = str;  // compile-time type check error
     * Long num = (Long) str;  // compile-time type check error
     * Long num = (Long) ((Object) str);  // throws ClassCastException at run-time
     * </pre>
     * 
     * @param ref
     *        an object reference
     * @param destClass
     *        class for destination type after cast
     * @param refArgName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param destClassArgName
     *        argument name for {@code destClass}, e.g., "strListClass" or "searchRegexClass"
     *
     * @return the validated object reference
     *
     * @throws NullPointerException
     *         if {@code ref} or {@code destClass} is {@code null}
     * @throws ClassCastException
     *         if {@code ref} cannot be safely cast to type {@code destClass}
     *
     * @see #checkInstanceOfType(Object, Class, String)
     * @see #checkAssignableToType(Class, Class, String, String)
     */
    @FullyTested
    public static <T> T checkInstanceOfType(
            T ref, Class<?> destClass, String refArgName, String destClassArgName) {
        checkNotNull(ref, refArgName);
        checkNotNull(destClass, destClassArgName);
        _coreCheckInstanceOfType(ref, destClass, refArgName, destClassArgName);
        return ref;
    }

    private static <T> void _coreCheckInstanceOfType(
            T ref, Class<?> destClass, String refArgName, String destClassArgName) {
        // From the Javadoc for Class.isInstance():
        // Determines if the specified Object is assignment-compatible with the object represented
        // by this Class.
        if (!destClass.isInstance(ref)) {
            Class<?> refClass = ref.getClass();
            String w = StringArgs._getArgNameWarning(refArgName, "refArgName");
            String w2 = StringArgs._getArgNameWarning(destClassArgName, "destClassArgName");
            String msg = String.format("Argument '%s': Cannot assign type %s to type %s%s%s",
                refArgName, refClass.getName(), destClass.getName(), w, w2);
            throw new ClassCastException(msg);
        }
    }

    /**
     * This is a convenience method for {@link #checkCast(Object, Class, String, String)}
     * where {@code clazzArgName = "clazz"}.
     */
    public static <TSrc, TDest>
    TDest checkCast(TSrc ref, Class<TDest> clazz, String refArgName) {
        TDest x = checkCast(ref, clazz, refArgName, "clazz");
        return x;
    }

    /**
     * Tests if a reference can be safely cast to another type.  If you only have {@link Class}
     * reference and not an object reference, see
     * {@link #checkAssignableToType(Class, Class, String, String)}.  If you don't need to cast the
     * value, see {@link #checkInstanceOfType(Object, Class, String, String)}.
     * <p>
     * Type cast rules are defined by {@link Class#isInstance(Object)}.
     * <p>
     * Examples of <b>valid</b> type casts:
     * <pre>
     * String str = "abc";
     * CharSequence seq = str;  // OK
     * CharSequence seq2 = (CharSequence) str;  // OK
     * CharSequence seq3 = (CharSequence) ((Object) str);  // OK
     * </pre>
     * Examples of <b>invalid</b> type casts:
     * <pre>
     * String str = "abc";
     * Long num = str;  // compile-time type check error
     * Long num = (Long) str;  // compile-time type check error
     * Long num = (Long) ((Object) str);  // throws ClassCastException at run-time
     * </pre>
     *
     * @param ref
     *        an object reference, including {@code null}
     * @param clazz
     *        class for destination type after cast
     * @param refArgName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param clazzArgName
     *        argument name for {@code clazz}, e.g., "strListClass" or "searchRegexClass"
     * @param <TSrc>
     *        source/input type
     * @param <TDest>
     *        destination/output type
     *
     * @return the validated object reference cast to type {@code TDest}
     *
     * @throws NullPointerException
     *         if {@code clazz} is {@code null}
     * @throws ClassCastException
     *         if {@code ref} cannot be safely cast to type {@code clazz}
     *
     * @see #checkInstanceOfType(Object, Class, String, String)
     * @see #checkAssignableToType(Class, Class, String, String)
     */
    public static <TSrc, TDest>
    TDest checkCast(TSrc ref, Class<TDest> clazz, String refArgName, String clazzArgName) {
        checkNotNull(clazz, clazzArgName);
        if (null == ref) {
            return null;
        }
        _coreCheckInstanceOfType(ref, clazz, refArgName, clazzArgName);
        TDest x = clazz.cast(ref);
        return x;
    }

    /**
     * This is a convenience method for
     * {@link #checkAssignableToType(Class, Class, String, String)}
     * where {@code destClassArgName = "destClass"}.
     */
    @FullyTested
    public static <TSrc, TDest> void checkAssignableToType(
            Class<TSrc> srcClass, Class<TDest> destClass, String srcClassArgName) {
        checkAssignableToType(srcClass, destClass, srcClassArgName, "destClass");
    }
    
    /**
     * Tests if a type can be safely cast to another type.  If you have an object reference, see
     * {@link #checkInstanceOfType(Object, Class, String, String)}.
     * <p>
     * Type cast rules are defined by {@link Class#isAssignableFrom(Class)}.
     * <p>
     * For examples of valid and invalid type casts, see
     * {@link #checkInstanceOfType(Object, Class, String, String)}.
     * 
     * @param srcClass
     *        class for source type before cast
     * @param destClass
     *        class for destination type after cast
     * @param srcClassArgName
     *        argument name for {@code srcClass},
     *        e.g., "strListClass" or "searchRegexClass"
     * @param destClassArgName
     *        argument name for {@code destClass},
     *        e.g., "strListClass" or "searchRegexClass"
     *
     * @throws NullPointerException
     *         if {@code srcClass} or {@code destClass} is {@code null}
     * @throws ClassCastException
     *         if type {@code srcClass} cannot be safely cast to type {@code destClass}
     *
     * @see #checkAssignableToType(Class, Class, String)
     * @see #checkInstanceOfType(Object, Class, String, String)
     */
    @FullyTested
    public static <TSrc, TDest> void checkAssignableToType(
            Class<TSrc> srcClass,
            Class<TDest> destClass,
            String srcClassArgName,
            String destClassArgName) {
        checkNotNull(srcClass, srcClassArgName);
        checkNotNull(destClass, destClassArgName);
        // From the Javadoc for Class.isAssignableFrom():
        // Determines if the class or interface represented by this Class object is either the same
        // as, or is a superclass or superinterface of, the class or interface represented by the
        // specified Class parameter.
        if (!destClass.isAssignableFrom(srcClass)) {
            String w = StringArgs._getArgNameWarning(srcClassArgName, "srcClassArgName");
            String w2 = StringArgs._getArgNameWarning(destClassArgName, "destClassArgName");
            String msg = String.format("Argument '%s': Cannot assign type %s to type %s%s%s",
                srcClassArgName, srcClass.getName(), destClass.getName(), w, w2);
            throw new ClassCastException(msg);
        }
    }
}
