/*
 * This class is the color settings for Docket.
 * 
 * @@author A0113761M
 * */
package UserInterface;

import java.awt.Color;

import javax.swing.JTextPane;

import main.Constants;

public class ColorsForSettings {
	/*public static String SET_1 = "button r:218, g:216, b:167 topBg r:255, g:158, b:157 bottomBg r:127, g:199, b:175";
	public static String SET_2 = "button r:194, g:212, b:216 topBg r:219, g:233, b:216 bottomBg r:176, g:170, b:194";
	public static String SET_3 = "button r:201, g:216, b:197 topBg r:237, g:217, b:192 bottomBg r:168, g:182, b:191";
	public static String SET_4 = "button r:233, g:236, b:229 topBg r:192, g:223, b:217 bottomBg r:179, g:194, b:191";
	public static String SET_5 = "button r:217, g:206, b:178 topBg r:213, g:222, b:217 bottomBg r:148, g:140, b:117";
	*/
	/* These are the sets of colors used for the User Interface. Set 1 to Set 4 corresponds to Option 1 to Option 4, Set 5 corresponds to Default*/
	
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
	
	public String getButton(String set){
		return set.substring(set.indexOf("button") + 6, set.indexOf("topBg"));
	}
	
	public String getTopBg(String set){
		return set.substring(set.indexOf("topBg") + 5, set.indexOf("bottomBg"));
	}
	
	public String getBottomBg(String set){
		return set.substring(set.indexOf("bottomBg") + 8);
	}
	
	public int getRed(String color){
		return Integer.valueOf(color.substring(color.indexOf("r:") + 2, color.indexOf(",")).trim());
	}
	
	public int getGreen(String color){
		return Integer.valueOf(color.substring(color.indexOf("g:") + 2, color.indexOf(",", color.indexOf("g:"))).trim());
	}
	
	public int getBlue(String color){
		return Integer.valueOf(color.substring(color.indexOf("b:") + 2).trim());
	}
	
	public Color rgbColor(String color){
		int red = getRed(color);
		int green = getGreen(color);
		int blue = getBlue(color);
		return new Color(red, green, blue);
	}
}
