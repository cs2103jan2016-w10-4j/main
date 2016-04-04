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

public class ColorsForSettingsTest {
	private static String color1 = "button r:192, g:223, b:217 topBg r:233, g:236, b:229 bottomBg r:179, g:194, b:191";
	private static ColorsForSettings colors = new ColorsForSettings();
	
	private static String getButtonColor = colors.getButton(color1);
	private static String getTopBgColor = colors.getTopBg(color1);
	private static String getBottomBgColor = colors.getBottomBg(color1);
	
	@Test
	public void testGetButtonRed(){
		assertEquals(colors.getRed(getButtonColor), 192);
	}

	@Test
	public void testGetButtonGreen(){
		assertEquals(colors.getGreen(getButtonColor), 223);
	}

	@Test
	public void testGetButtonBlue(){
		assertEquals(colors.getBlue(getButtonColor), 217);
	}

	@Test
	public void testRgbColorButton(){
		assertEquals(colors.rgbColor(getButtonColor), new Color(192, 223, 217));
	}
	
	@Test
	public void testGetTopBgRed(){
		assertEquals(colors.getRed(getTopBgColor), 233);
	}

	@Test
	public void testGetTopBgGreen(){
		assertEquals(colors.getGreen(getTopBgColor), 236);
	}

	@Test
	public void testGetTopBgBlue(){
		assertEquals(colors.getBlue(getTopBgColor), 229);
	}

	@Test
	public void testRgbColorTopBg(){
		assertEquals(colors.rgbColor(getTopBgColor), new Color(233, 236, 229));
	}
	
	@Test
	public void testGetBottomBgRed(){
		assertEquals(colors.getRed(getBottomBgColor), 179);
	}

	@Test
	public void testGetBottomBgGreen(){
		assertEquals(colors.getGreen(getBottomBgColor), 194);
	}

	@Test
	public void testGetBottomBgBlue(){
		assertEquals(colors.getBlue(getBottomBgColor), 191);
	}

	@Test
	public void testRgbColorBottomBg(){
		assertEquals(colors.rgbColor(getBottomBgColor), new Color(179, 194, 191));
	}
}
