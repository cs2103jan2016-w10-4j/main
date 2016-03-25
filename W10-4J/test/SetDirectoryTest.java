package test;

import static org.junit.Assert.*;

import org.junit.Test;

import Storage.SetDirectory;

public class SetDirectoryTest extends SetDirectory {

	@Test
	public void testDirectoryExists() {
		boolean returnValue = setDirectory("mytextfile.txt");
		assertEquals(true, returnValue);
	}
	
	@Test
	public void testDirectoryDoesNotExists() {
		boolean returnValue = setDirectory("C:/Users/NoSuchFile/noSuchFile.txt");
		assertEquals(false, returnValue);
	}
}