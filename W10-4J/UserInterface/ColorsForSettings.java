/*
 * This class is the color settings for Docket.
 * 
 * @@author A0113761M
 */
package UserInterface;

import java.awt.Color;

import javax.swing.JTextPane;

import main.Constants;

public class ColorsForSettings {
	/*
	 * These are the sets of colors used for the User Interface. Set 1 to Set 4
	 * corresponds to Option 1 to Option 4, Set 5 corresponds to Default
	 */

	public void defaultColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(rgbColor(getTopBg(Constants.SET_5)));
		textPane2.setBackground(rgbColor(getBottomBg(Constants.SET_5)));
	}

	public void optionFourColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(rgbColor(getTopBg(Constants.SET_4)));
		textPane2.setBackground(rgbColor(getBottomBg(Constants.SET_4)));
	}

	public void optionThreeColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(rgbColor(getTopBg(Constants.SET_3)));
		textPane2.setBackground(rgbColor(getBottomBg(Constants.SET_3)));
	}

	public void optionTwoColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(rgbColor(getTopBg(Constants.SET_2)));
		textPane2.setBackground(rgbColor(getBottomBg(Constants.SET_2)));
	}

	public void optionOneColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(rgbColor(getTopBg(Constants.SET_1)));
		textPane2.setBackground(rgbColor(getBottomBg(Constants.SET_1)));
	}

	public String getButton(String set) {
		return set.substring(set.indexOf("button") + 6, set.indexOf("topBg"));
	}

	public String getTopBg(String set) {
		return set.substring(set.indexOf("topBg") + 5, set.indexOf("bottomBg"));
	}

	public String getBottomBg(String set) {
		return set.substring(set.indexOf("bottomBg") + 8);
	}

	public int getRed(String color) {
		return Integer.valueOf(color.substring(color.indexOf("r:") + 2, color.indexOf(",")).trim());
	}

	public int getGreen(String color) {
		return Integer
				.valueOf(color.substring(color.indexOf("g:") + 2, color.indexOf(",", color.indexOf("g:"))).trim());
	}

	public int getBlue(String color) {
		return Integer.valueOf(color.substring(color.indexOf("b:") + 2).trim());
	}

	public Color rgbColor(String color) {
		int red = getRed(color);
		int green = getGreen(color);
		int blue = getBlue(color);
		return new Color(red, green, blue);
	}
}
