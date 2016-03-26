/*
 * This class is used to read and write xml files. 
 * This class is currently used in SettingsUI.java to store properties of display output and command output in the GUI. Generally, it is used to store the background and font colors of both displays.
 * It is also used in UserInterface.java to get the properties of display output and command output so set the GUI.
 * 
 * @@author A0113761M
 */
package UserInterface;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class ReadProperties {
	private int colorOptionIndex = 0;
	private int topFontColorIndex = 1;
	private int topBgIndex = 2;
	private int bottomBgIndex = 3;
	private int bottomFontColorIndex = 4;
	
	public static String read(String action, String fileName){
		try{
			File file = new File(fileName);
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.loadFromXML(fileInput);
			fileInput.close();
			String details = prop.getProperty(action);
			if (details.equals("")){
				return null;
			} else {
				return details; 
			}
		} catch (FileNotFoundException e){
			return null;
		} catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<String> readToArrayList(){
		String fileName = ".\\UserInterface\\properties.xml";
		ArrayList<String> properties = new ArrayList<String>();
		String colorOption = read("colorOption", fileName);
		String topFontColor = read("topFontColor", fileName);
		String bottomFontColor = read("bottomFontColor", fileName);
		String topBg = read("topBg", fileName);
		String bottomBg = read("bottomBg", fileName);
		properties.add(colorOptionIndex, colorOption);
		properties.add(topFontColorIndex, topFontColor);
		properties.add(topBgIndex, topBg);
		properties.add(bottomBgIndex, bottomBg);
		properties.add(bottomFontColorIndex, bottomFontColor);
		return properties;
	}
	
	public void setProperties(ArrayList<String> prop){
		Properties properties = new Properties();
		properties.setProperty("colorOption", prop.get(colorOptionIndex));
		properties.setProperty("topBg", prop.get(topBgIndex));
		properties.setProperty("bottomBg", prop.get(bottomBgIndex));
		properties.setProperty("topFontColor", prop.get(topFontColorIndex));
		properties.setProperty("bottomFontColor", prop.get(bottomFontColorIndex));
		
		File file = new File(".\\UserInterface\\properties.xml");
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.storeToXML(fileOut, "colors");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getRed(String color){
		return color.substring(color.indexOf("r:") + 2, color.indexOf(",")).trim();
	}
	
	public static String getGreen(String color){
		return color.substring(color.indexOf("g:") + 2, color.indexOf(",", color.indexOf("g:"))).trim();
	}
	
	public static String getBlue(String color){
		return color.substring(color.indexOf("b:") + 2).trim();
	}
	
	public Color rgbColor(String color){
		int red = Integer.valueOf(getRed(color));
		int green = Integer.valueOf(getGreen(color));
		int blue = Integer.valueOf(getBlue(color));
		return new Color(red, green, blue);
	}
}
