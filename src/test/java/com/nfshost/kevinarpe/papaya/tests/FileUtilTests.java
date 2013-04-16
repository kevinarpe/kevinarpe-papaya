/**
 * Copyright 2013 Kevin Connor ARPE
 * 
 * This file is part of Papaya.
 * 
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nfshost.kevinarpe.papaya.tests;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.nfshost.kevinarpe.papaya.FileUtil;

public class FileUtilTests {

	@BeforeClass
	public void oneTimeSetup() {
	}
	
	@AfterClass
	public void oneTimeTearDown() {
	}

	@Test
	public void testCreateDirectory()
	throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path dir = fs.getPath("/home/kca/find.txt");
		Files.createDirectory(dir);
	}
	
	@Test
	public void testWriteTextFile()
	throws IOException {
		try {
			FileUtil.staticWriteTextFile("/home/kca/test.txt", "blah");
		}
		catch (IOException e) {
			e.printStackTrace();
			int dummy = 0;
		}
	}
}