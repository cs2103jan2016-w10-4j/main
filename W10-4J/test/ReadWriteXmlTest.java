/*
 * JUnit test for ReadWriteXml class.
 * 
 * @@author A0113761M
 */
package test;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import UserInterface.ReadWriteXml;

public class ReadWriteXmlTest {
	private String color1 = "#0543 r:4, g:23, b: 201";
	@Test
	public void testGetRed(){
		assertEquals(ReadWriteXml.getRed(color1), 4);
	}

	@Test
	public void testGetGreen(){
		assertEquals(ReadWriteXml.getGreen(color1), 23);
	}

	@Test
	public void testGetBlue(){
		assertEquals(ReadWriteXml.getBlue(color1), 201);
	}

	@Test
	public void testRgbColor(){
		ReadWriteXml rwxml = new ReadWriteXml();
		assertEquals(rwxml.rgbColor(color1), new Color(4, 23, 201));
	}
}
