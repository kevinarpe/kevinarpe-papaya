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

import com.google.common.base.Joiner;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

/**
 * This is a collection of methods to indentify the current operating system.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class SystemUtils {

    // Disable default constructor
    private SystemUtils() {
    }
    
    private static final OperatingSystem _CURRENT_OPERATING_SYSTEM;
    private static final OperatingSystemCategory _CURRENT_OPERATING_SYSTEM_CATEGORY;
    
    static {
        String name = System.getProperty("os.name");
        name = name.replace('-', '_');
        name = name.replace(' ', '_');
        name = name.replace('/', '_');
        name = name.toUpperCase();
        OperatingSystem os = OperatingSystem.UNKNOWN;
        try {
            os = OperatingSystem.valueOf(name);
        }
        catch (Exception e) {
            if (name.startsWith("WINDOWS")) {
                os = OperatingSystem.WINDOWS_UNKNOWN;
            }
        }
        _CURRENT_OPERATING_SYSTEM = os;
        _CURRENT_OPERATING_SYSTEM_CATEGORY = os.getCategory();
    }
    
    /**
     * This is an attempt to classify values from {@link OperatingSystem}, and should be considered
     * highly subjective.  The intent is the disambiguate UNIX-like vs. Microsoft Windows vs.
     * everything else.
     * 
     * @author Kevin Connor ARPE (kevinarpe@gmail.com)
     *
     * @see OperatingSystem
     * @see OperatingSystem#getCategory()
     */
    @FullyTested
    public enum OperatingSystemCategory {
        
        /**
         * This value is used by {@link OperatingSystem#UNKNOWN}.
         */
        UNKNOWN,
        
        /**
         * This value implies the operating system is UNIX-like, and (mostly)
         * POSIX compliant.
         * 
         * @see #WINDOWS
         * @see #OTHER
         */
        UNIX,
        
        /**
         * This value is used for all variants of Microsoft's Windows operating system.
         * 
         * @see #UNIX
         * @see #OTHER
         */
        WINDOWS,
        
        /**
         * This value is used for known operating systems that are considered neither {@link #UNIX}
         * not {@link #WINDOWS}.
         */
        OTHER;
        
        /**
         * Tests if this value identifies the current operating system category.
         */
        public boolean isCurrent() {
            boolean b = (this == _CURRENT_OPERATING_SYSTEM_CATEGORY);
            return b;
        }
    }

    /**
     * This is a large, but certainly not exhausitive, list of operating systems.  This will grow
     * in the future as other Java virtual machines are discovered/reported.  Now that JDK 1.7 is
     * open source, it is technically possible for any value to be reported for system property
     * {@code os.name}.
     * <p>
     * All said, the vast majority of Java users and programmers are operating on Sun's HotSpot
     * Java virtual machine.  In these cases, this enum can be valuable.
     * 
     * @author Kevin Connor ARPE (kevinarpe@gmail.com)
     *
     * @see SystemUtils#getCurrentOperatingSystem()
     * @see OperatingSystemCategory
     * @see <a href="http://lopica.sourceforge.net/os.html">Source</a>
     */
    @FullyTested
    public enum OperatingSystem {
        
        /**
         * This value is used when it is not possible identify the current operating system via
         * system property {@code os.name}.
         * <p>
         * Category is {@link OperatingSystemCategory#UNKNOWN}.
         */
        UNKNOWN(OperatingSystemCategory.UNKNOWN),
        
        /**
         * This value is used for all varieties and versions of Linux.
         * <p>
         * Category is {@link OperatingSystemCategory#UNIX}.
         */
        LINUX(OperatingSystemCategory.UNIX),
        
        /**
         * This value is used for Macintosh operating systems <b>before</b> Mac OS X.
         * <p>
         * Category is {@link OperatingSystemCategory#OTHER}.
         * 
         * @see #MAC_OS_X
         */
        MAC_OS(OperatingSystemCategory.OTHER),
        
        /**
         * This value is used for all varieties of Mac OS X.
         * <p>
         * Category is {@link OperatingSystemCategory#UNIX}.
         * 
         * @see #MAC_OS
         */
        MAC_OS_X(OperatingSystemCategory.UNIX),
        
        /**
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_95(OperatingSystemCategory.WINDOWS),
        
        /**
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_98(OperatingSystemCategory.WINDOWS),
        
        /**
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_ME(OperatingSystemCategory.WINDOWS),
        
        /**
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_NT(OperatingSystemCategory.WINDOWS),
        
        /**
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_2000(OperatingSystemCategory.WINDOWS),
        
        /**
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_2003(OperatingSystemCategory.WINDOWS),
        
        /**
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_XP(OperatingSystemCategory.WINDOWS),
        
        /**
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_VISTA(OperatingSystemCategory.WINDOWS),
        
        /**
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_7(OperatingSystemCategory.WINDOWS),
        
        /**
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_8(OperatingSystemCategory.WINDOWS),
        
        /**
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_CE(OperatingSystemCategory.WINDOWS),
        
        /**
         * This value is used when system property {@code os.name} begins with "Windows" (case
         * insensitive match).
         * <p>
         * Category is {@link OperatingSystemCategory#WINDOWS}.
         */
        WINDOWS_UNKNOWN(OperatingSystemCategory.WINDOWS),
        
        /**
         * Category is {@link OperatingSystemCategory#OTHER}.
         */
        OS_2(OperatingSystemCategory.OTHER),
        
        /**
         * Category is {@link OperatingSystemCategory#UNIX}.
         */
        SOLARIS(OperatingSystemCategory.UNIX),
        
        /**
         * Category is {@link OperatingSystemCategory#UNIX}.
         */
        SUNOS(OperatingSystemCategory.UNIX),
        
        /**
         * Category is {@link OperatingSystemCategory#UNIX}.
         */
        HP_UX(OperatingSystemCategory.UNIX),
        
        /**
         * Category is {@link OperatingSystemCategory#UNIX}.
         */
        AIX(OperatingSystemCategory.UNIX),
        
        /**
         * Category is {@link OperatingSystemCategory#UNIX}.
         */
        FREEBSD(OperatingSystemCategory.UNIX),
        
        /**
         * Category is {@link OperatingSystemCategory#UNIX}.
         */
        OPENBSD(OperatingSystemCategory.UNIX),
        
        /**
         * Category is {@link OperatingSystemCategory#UNIX}.
         */
        IRIX(OperatingSystemCategory.UNIX),
        
        /**
         * Category is {@link OperatingSystemCategory#OTHER}.
         */
        OS_400(OperatingSystemCategory.OTHER);
        
        private final OperatingSystemCategory _cat;
        
        private OperatingSystem(OperatingSystemCategory cat) {
            ObjectArgs.checkNotNull(cat, "cat");
            
            _cat = cat;
        }
        
        /**
         * @return operating system category for this value
         * 
         * @see OperatingSystemCategory#UNIX
         * @see OperatingSystemCategory#WINDOWS
         */
        public OperatingSystemCategory getCategory() {
            return _cat;
        }
        
        /**
         * Tests if this value identifies the current operating system.
         */
        public boolean isCurrent() {
            boolean b = (this == _CURRENT_OPERATING_SYSTEM);
            return b;
        }
    }
    
    /**
     * Via static initialisation, the current operating system is discovered.  Access that value
     * with this method.  In rare cases where it is not possible identify the current operating
     * system via system property {@code os.name}, a reference to {@link OperatingSystem#UNKNOWN}
     * is returned.
     * 
     * @return current operating system or {@link OperatingSystem#UNKNOWN}
     *
     * @see OperatingSystem
     * @see OperatingSystem#getCategory()
     * @see #getCurrentOperatingSystemCategory()
     * @see OperatingSystemCategory
     * @see #checkCurrentOperatingSystemCategory(OperatingSystemCategory...)
     */
    @FullyTested
    public static OperatingSystem getCurrentOperatingSystem() {
        return _CURRENT_OPERATING_SYSTEM;
    }
    
    /**
     * Via static initialisation, the current operating system is discovered and categorised.
     * Access that category with this method.  In rare cases where it is not possible identify the
     * current operating system via system property {@code os.name},
     * {@link OperatingSystemCategory#UNKNOWN} is returned.
     * 
     * @return current operating system category or {@link OperatingSystemCategory#UNKNOWN}
     *
     * @see #getCurrentOperatingSystem()
     * @see OperatingSystem#getCategory()
     * @see OperatingSystemCategory
     * @see #checkCurrentOperatingSystemCategory(OperatingSystemCategory...)
     */
    @FullyTested
    public static OperatingSystemCategory getCurrentOperatingSystemCategory() {
        return _CURRENT_OPERATING_SYSTEM_CATEGORY;
    }
    
    /**
     * Tests if the current operating system matches any one of a list.
     * <p>
     * Example:<pre>
     * SystemUtils.checkCurrentOperatingSystem(
     *     SystemUtils.OperatingSystem.WINDOWS_XP,
     *     SystemUtils.OperatingSystem.WINDOWS_VISTA,
     *     SystemUtils.OperatingSystem.WINDOWS_7,
     *     SystemUtils.OperatingSystem.WINDOWS_8);
     * </pre>
     * 
     * @param osArr
     *        list of operating systems to match
     *
     * @return current operating system
     *
     * @see #getCurrentOperatingSystem()
     * @see #checkCurrentOperatingSystemCategory(OperatingSystemCategory...)
     */
    @FullyTested
    public static OperatingSystem checkCurrentOperatingSystem(OperatingSystem... osArr) {
        ArrayArgs.checkNotEmptyAndElementsNotNull(osArr, "osArr");
        
        for (OperatingSystem os: osArr) {
            if (os.equals(_CURRENT_OPERATING_SYSTEM)) {
                return os;
            }
        }
        String msg = String.format(
            "Current operating system '%s' does not match test list:%n\t%s",
            _CURRENT_OPERATING_SYSTEM,
            Joiner.on(", ").join(osArr));
        throw new IllegalArgumentException(msg);
    }
    
    /**
     * Tests if the current operating system category matches any one of a list.
     * <p>
     * Example:<pre>
     * SystemUtils.checkCurrentOperatingSystemCategory(
     *     SystemUtils.OperatingSystemCategory.WINDOWS,
     *     SystemUtils.OperatingSystemCategory.UNIX);
     * </pre>
     * 
     * @param catArr
     *        list of categories to match
     *
     * @return current category
     *
     * @see #getCurrentOperatingSystem()
     * @see #checkCurrentOperatingSystem(OperatingSystem...)
     */
    @FullyTested
    public static OperatingSystemCategory checkCurrentOperatingSystemCategory(
            OperatingSystemCategory... catArr) {
        ArrayArgs.checkNotEmptyAndElementsNotNull(catArr, "catArr");
        
        OperatingSystemCategory currCat = _CURRENT_OPERATING_SYSTEM.getCategory();
        for (OperatingSystemCategory cat: catArr) {
            if (cat.equals(currCat)) {
                return cat;
            }
        }
        String msg = String.format(
            "Current operating system category '%s' does not match test list:%n\t%s",
            currCat,
            Joiner.on(", ").join(catArr));
        throw new IllegalArgumentException(msg);
    }
}
