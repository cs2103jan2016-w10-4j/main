package UserInterface;

import java.awt.Color;

import javax.swing.JTextPane;

public class ColorsForSettings {
	public static String set1 = "button r:192, g:223, b:217 topBg r:233, g:236, b:229 bottomBg r:179, g:194, b:191";
	public static String set2 = "button r:201, g:216, b:197 topBg r:237, g:217, b:192 bottomBg r:168, g:182, b:191";
	public static String set3 = "button r:194, g:212, b:216 topBg r:219, g:233, b:216 bottomBg r:176, g:170, b:194";

	public void defaultColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(Color.WHITE);
		textPane2.setBackground(Color.BLACK);
	}

	public void natureColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(rgbColor(getTopBg(set1)));
		textPane2.setBackground(rgbColor(getBottomBg(set1)));
	}

	public void dawnColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(rgbColor(getTopBg(set2)));
		textPane2.setBackground(rgbColor(getBottomBg(set2)));
	}

	public void sunsetColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(rgbColor(getTopBg(set3)));
		textPane2.setBackground(rgbColor(getBottomBg(set3)));
	}

	public void seaColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(new Color(180, 216, 231));
		textPane2.setBackground(new Color(86, 186, 236));
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
