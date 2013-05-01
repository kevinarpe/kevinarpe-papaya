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

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SystemUtilsTest {

    @BeforeClass
    public void oneTimeSetup() {
    }
    
    @AfterClass
    public void oneTimeTearDown() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // SystemUtils.checkCurrentOperatingSystem
    //

    @DataProvider
    private static final SystemUtils.OperatingSystem[][][] _dataForShouldCheckCurrentOperatingSystem() {
    	SystemUtils.OperatingSystem os = SystemUtils.getCurrentOperatingSystem();
    	return new SystemUtils.OperatingSystem[][][] {
    			{ new SystemUtils.OperatingSystem[] { os } },
    			{ new SystemUtils.OperatingSystem[] { os, SystemUtils.OperatingSystem.LINUX, SystemUtils.OperatingSystem.WINDOWS_7 } },
    			{ new SystemUtils.OperatingSystem[] { SystemUtils.OperatingSystem.LINUX, os, SystemUtils.OperatingSystem.WINDOWS_7 } },
    			{ new SystemUtils.OperatingSystem[] { SystemUtils.OperatingSystem.LINUX, SystemUtils.OperatingSystem.WINDOWS_7, os } },
    	};
    }
    
    @Test(dataProvider = "_dataForShouldCheckCurrentOperatingSystem")
    public void shouldCheckCurrentOperatingSystem(SystemUtils.OperatingSystem[] osArr) {
    	SystemUtils.checkCurrentOperatingSystem(osArr);
    }

    @DataProvider
    private static final SystemUtils.OperatingSystem[][][] _dataForShouldNotCheckCurrentOperatingSystem() {
    	SystemUtils.OperatingSystem os = SystemUtils.getCurrentOperatingSystem();
    	SystemUtils.OperatingSystem notThisOs1 = null;
    	SystemUtils.OperatingSystem notThisOs2 = null;
    	for (SystemUtils.OperatingSystem otherOs: SystemUtils.OperatingSystem.values()) {
    		if (!os.equals(otherOs)) {
    			if (null == notThisOs1) {
    				notThisOs1 = otherOs;
    			}
    			else if (null == notThisOs2) {
    				notThisOs2 = otherOs;
    				break;
    			}
    		}
    	}
    	return new SystemUtils.OperatingSystem[][][] {
    			{ new SystemUtils.OperatingSystem[] { notThisOs1 } },
    			{ new SystemUtils.OperatingSystem[] { notThisOs1, notThisOs2 } },
    			{ new SystemUtils.OperatingSystem[] { notThisOs2, notThisOs1 } },
    	};
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckCurrentOperatingSystem",
    		expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckCurrentOperatingSystem(SystemUtils.OperatingSystem[] osArr) {
    	SystemUtils.checkCurrentOperatingSystem(osArr);
    }

    @DataProvider
    private static final SystemUtils.OperatingSystem[][][] _dataForShouldNotCheckCurrentOperatingSystemWithNullRefs() {
    	SystemUtils.OperatingSystem os = SystemUtils.getCurrentOperatingSystem();
    	return new SystemUtils.OperatingSystem[][][] {
    			{ new SystemUtils.OperatingSystem[] { null } },
    			{ new SystemUtils.OperatingSystem[] { os, null } },
    			{ new SystemUtils.OperatingSystem[] { os, SystemUtils.OperatingSystem.LINUX, SystemUtils.OperatingSystem.WINDOWS_7, null } },
    			{ new SystemUtils.OperatingSystem[] { SystemUtils.OperatingSystem.LINUX, os, null, SystemUtils.OperatingSystem.WINDOWS_7 } },
    			{ new SystemUtils.OperatingSystem[] { SystemUtils.OperatingSystem.LINUX, null, SystemUtils.OperatingSystem.WINDOWS_7, os } },
    	};
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckCurrentOperatingSystemWithNullRefs",
    		expectedExceptions = NullPointerException.class)
    public void shouldNotCheckCurrentOperatingSystemWithNullRefs(
    		SystemUtils.OperatingSystem[] osArr) {
    	SystemUtils.checkCurrentOperatingSystem(osArr);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckCurrentOperatingSystemWithEmptyList() {
    	SystemUtils.checkCurrentOperatingSystem();
    }

    ///////////////////////////////////////////////////////////////////////////
    // SystemUtils.checkCurrentOperatingSystemCategory
    //

    @DataProvider
    private static final SystemUtils.OperatingSystemCategory[][][] _dataForShouldCheckCurrentOperatingSystemCategory() {
    	SystemUtils.OperatingSystemCategory cat =
			SystemUtils.getCurrentOperatingSystem().getCategory();
    	return new SystemUtils.OperatingSystemCategory[][][] {
    			{ new SystemUtils.OperatingSystemCategory[] { cat } },
    			{ new SystemUtils.OperatingSystemCategory[] { cat, SystemUtils.OperatingSystemCategory.UNIX, SystemUtils.OperatingSystemCategory.WINDOWS } },
    			{ new SystemUtils.OperatingSystemCategory[] { SystemUtils.OperatingSystemCategory.UNIX, cat, SystemUtils.OperatingSystemCategory.WINDOWS } },
    			{ new SystemUtils.OperatingSystemCategory[] { SystemUtils.OperatingSystemCategory.UNIX, SystemUtils.OperatingSystemCategory.WINDOWS, cat } },
    	};
    }
    
    @Test(dataProvider = "_dataForShouldCheckCurrentOperatingSystemCategory")
    public void shouldCheckCurrentOperatingSystemCategory(SystemUtils.OperatingSystemCategory[] osArr) {
    	SystemUtils.checkCurrentOperatingSystemCategory(osArr);
    }

    @DataProvider
    private static final SystemUtils.OperatingSystemCategory[][][] _dataForShouldNotCheckCurrentOperatingSystemCategory() {
    	SystemUtils.OperatingSystemCategory cat =
			SystemUtils.getCurrentOperatingSystem().getCategory();
    	SystemUtils.OperatingSystemCategory notThisCat1 = null;
    	SystemUtils.OperatingSystemCategory notThisCat2 = null;
    	for (SystemUtils.OperatingSystemCategory otherCat:
    			SystemUtils.OperatingSystemCategory.values()) {
    		if (!cat.equals(otherCat)) {
    			if (null == notThisCat1) {
    				notThisCat1 = otherCat;
    			}
    			else if (null == notThisCat2) {
    				notThisCat2 = otherCat;
    				break;
    			}
    		}
    	}
    	return new SystemUtils.OperatingSystemCategory[][][] {
    			{ new SystemUtils.OperatingSystemCategory[] { notThisCat1 } },
    			{ new SystemUtils.OperatingSystemCategory[] { notThisCat1, notThisCat2 } },
    			{ new SystemUtils.OperatingSystemCategory[] { notThisCat2, notThisCat1 } },
    	};
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckCurrentOperatingSystemCategory",
    		expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckCurrentOperatingSystemCategory(
    		SystemUtils.OperatingSystemCategory[] catArr) {
    	SystemUtils.checkCurrentOperatingSystemCategory(catArr);
    }

    @DataProvider
    private static final SystemUtils.OperatingSystemCategory[][][] _dataForShouldNotCheckCurrentOperatingSystemCategoryWithNullRefs() {
    	SystemUtils.OperatingSystemCategory cat =
			SystemUtils.getCurrentOperatingSystem().getCategory();
    	return new SystemUtils.OperatingSystemCategory[][][] {
    			{ new SystemUtils.OperatingSystemCategory[] { null } },
    			{ new SystemUtils.OperatingSystemCategory[] { cat, null } },
    			{ new SystemUtils.OperatingSystemCategory[] { cat, SystemUtils.OperatingSystemCategory.UNIX, SystemUtils.OperatingSystemCategory.WINDOWS, null } },
    			{ new SystemUtils.OperatingSystemCategory[] { SystemUtils.OperatingSystemCategory.UNIX, cat, null, SystemUtils.OperatingSystemCategory.WINDOWS } },
    			{ new SystemUtils.OperatingSystemCategory[] { SystemUtils.OperatingSystemCategory.UNIX, null, SystemUtils.OperatingSystemCategory.WINDOWS, cat } },
    	};
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckCurrentOperatingSystemCategoryWithNullRefs",
    		expectedExceptions = NullPointerException.class)
    public void shouldNotCheckCurrentOperatingSystemCategoryWithNullRefs(
    		SystemUtils.OperatingSystemCategory[] osArr) {
    	SystemUtils.checkCurrentOperatingSystemCategory(osArr);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckCurrentOperatingSystemCategoryWithEmptyList() {
    	SystemUtils.checkCurrentOperatingSystemCategory();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // SystemUtils.OperatingSystem.isCurrent
    //

    @Test
    public void shouldCheckIsCurrentOperatingSystem() {
    	SystemUtils.OperatingSystem os = SystemUtils.getCurrentOperatingSystem();
    	Assert.assertTrue(os.isCurrent());
    	SystemUtils.OperatingSystem otherOs =
			(os.equals(SystemUtils.OperatingSystem.LINUX)
					? SystemUtils.OperatingSystem.WINDOWS_7
					: SystemUtils.OperatingSystem.LINUX);
    	Assert.assertFalse(otherOs.isCurrent());
    }
}
