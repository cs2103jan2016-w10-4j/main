/*
 * JUnit test for ReadWriteXml class.
 * 
 * @@author A0113761M
 */
package test;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import UserInterface.ColorsForSettings;
import UserInterface.ReadWriteXml;

public class ReadWriteXmlTest {
	private String color1 = "#0543 r:4, g:23, b: 201";
	private static ColorsForSettings colors = new ColorsForSettings();
	@Test
	public void testGetRed(){
		assertEquals(colors.getRed(color1), 4);
	}

	@Test
	public void testGetGreen(){
		assertEquals(colors.getGreen(color1), 23);
	}

	@Test
	public void testGetBlue(){
		assertEquals(colors.getBlue(color1), 201);
	}

	@Test
	public void testRgbColor(){
		assertEquals(colors.rgbColor(color1), new Color(4, 23, 201));
	}
	private String color2 = "button r:12 , g:13 , b:14 topBg r:15 , g:16 , b:17 bottomBg r:18 , g:19 , b:20 ";
	@Test
	public void testGetButton(){
		System.out.println(colors.getRed(colors.getButton(color2)));
		System.out.println(colors.getTopBg(color2));
		System.out.println(colors.getBottomBg(color2));
	}
}
