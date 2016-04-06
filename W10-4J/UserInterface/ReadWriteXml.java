/*
 * This class is used to read and write xml files. 
 * This class is currently used in SettingsUI.java to store properties of display output and command output in the GUI. Generally, it is used to store the background and font colors of both displays.
 * It is also used in UserInterface.java to get the properties of display output and command output to set the GUI.
 * 
 * @@author A0113761M
 */
package UserInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import main.Constants;

public class ReadWriteXml {
	
	public static final int COLOR_OPTION_INDEX = 0;
	public static final int TOP_FONT_COLOR_INDEX = 1;
	public static final int TOP_BG_INDEX = 2;
	public static final int BOTTOM_BG_INDEX = 3;
	public static final int BOTTOM_FONT_COLOR_INDEX = 4;
	public static final int FONT_SIZE_INDEX = 5;
	public static final int FONT_FAMILY_INDEX = 6;
	public static final int BUTTONS_COLOR_INDEX = 7;
	
	public String read(String action, String fileName){
		try{
			File file = new File(fileName);
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.loadFromXML(fileInput);
			fileInput.close();
			String details = prop.getProperty(action);
			if (details.equals(Constants.EMPTY_STRING)){
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
		ArrayList<String> properties = new ArrayList<String>();
		String colorOption = read(Constants.COLOR_OPTION_KEY, Constants.PROPERTIES_FILE_NAME);
		String topFontColor = read(Constants.TOP_FONT_COLOR_KEY, Constants.PROPERTIES_FILE_NAME);
		String bottomFontColor = read(Constants.BOTTOM_FONT_COLOR_KEY, Constants.PROPERTIES_FILE_NAME);
		String topBg = read(Constants.TOP_BG_KEY, Constants.PROPERTIES_FILE_NAME);
		String bottomBg = read(Constants.BOTTOM_BG_KEY, Constants.PROPERTIES_FILE_NAME);
		String fontSize = read(Constants.FONT_SIZE_KEY, Constants.PROPERTIES_FILE_NAME);
		String fontFamily = read(Constants.FONT_FAMILY_KEY, Constants.PROPERTIES_FILE_NAME);
		String buttonsColor = read(Constants.BUTTONS_COLOR_KEY, Constants.PROPERTIES_FILE_NAME);
		properties.add(COLOR_OPTION_INDEX, colorOption);
		properties.add(TOP_FONT_COLOR_INDEX, topFontColor);
		properties.add(TOP_BG_INDEX, topBg);
		properties.add(BOTTOM_BG_INDEX, bottomBg);
		properties.add(BOTTOM_FONT_COLOR_INDEX, bottomFontColor);
		properties.add(FONT_SIZE_INDEX, fontSize);
		properties.add(FONT_FAMILY_INDEX, fontFamily);
		properties.add(BUTTONS_COLOR_INDEX, buttonsColor);
		return properties;
	}
	
	public void setProperties(ArrayList<String> prop){
		Properties properties = new Properties();
		properties.setProperty(Constants.COLOR_OPTION_KEY, prop.get(COLOR_OPTION_INDEX));
		properties.setProperty(Constants.TOP_BG_KEY, prop.get(TOP_BG_INDEX));
		properties.setProperty(Constants.BOTTOM_BG_KEY, prop.get(BOTTOM_BG_INDEX));
		properties.setProperty(Constants.TOP_FONT_COLOR_KEY, prop.get(TOP_FONT_COLOR_INDEX));
		properties.setProperty(Constants.BOTTOM_FONT_COLOR_KEY, prop.get(BOTTOM_FONT_COLOR_INDEX));
		properties.setProperty(Constants.FONT_SIZE_KEY, prop.get(FONT_SIZE_INDEX));
		properties.setProperty(Constants.FONT_FAMILY_KEY, prop.get(FONT_FAMILY_INDEX));
		properties.setProperty(Constants.BUTTONS_COLOR_KEY, prop.get(BUTTONS_COLOR_INDEX));
		
		File file = new File(Constants.PROPERTIES_FILE_NAME);
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.storeToXML(fileOut, "colors");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
