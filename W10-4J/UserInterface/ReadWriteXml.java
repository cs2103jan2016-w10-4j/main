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

public class ReadWriteXml {
	public static final String BUTTONS_COLOR_KEY = "buttonsColor";
	public static final String FONT_FAMILY_KEY = "fontFamily";
	public static final String FONT_SIZE_KEY = "fontSize";
	public static final String BOTTOM_BG_KEY = "bottomBg";
	public static final String TOP_BG_KEY = "topBg";
	public static final String BOTTOM_FONT_COLOR_KEY = "bottomFontColor";
	public static final String TOP_FONT_COLOR_KEY = "topFontColor";
	public static final String COLOR_OPTION_KEY = "colorOption";
	public static final String EMPTY_STRING = "";
	public static final String FILE_NAME = ".\\properties.xml";
	
	public static final int COLOR_OPTION_INDEX = 0;
	public static final int TOP_FONT_COLOR_INDEX = 1;
	public static final int TOP_BG_INDEX = 2;
	public static final int BOTTOM_BG_INDEX = 3;
	public static final int BOTTOM_FONT_COLOR_INDEX = 4;
	public static final int FONT_SIZE_INDEX = 5;
	public static final int FONT_FAMILY_INDEX = 6;
	public static final int BUTTONS_COLOR_INDEX = 7;
	
	public String read(String action, String FILE_NAME){
		try{
			File file = new File(FILE_NAME);
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.loadFromXML(fileInput);
			fileInput.close();
			String details = prop.getProperty(action);
			if (details.equals(EMPTY_STRING)){
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
		String colorOption = read(COLOR_OPTION_KEY, FILE_NAME);
		String topFontColor = read(TOP_FONT_COLOR_KEY, FILE_NAME);
		String bottomFontColor = read(BOTTOM_FONT_COLOR_KEY, FILE_NAME);
		String topBg = read(TOP_BG_KEY, FILE_NAME);
		String bottomBg = read(BOTTOM_BG_KEY, FILE_NAME);
		String fontSize = read(FONT_SIZE_KEY, FILE_NAME);
		String fontFamily = read(FONT_FAMILY_KEY, FILE_NAME);
		String buttonsColor = read(BUTTONS_COLOR_KEY, FILE_NAME);
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
		properties.setProperty(COLOR_OPTION_KEY, prop.get(COLOR_OPTION_INDEX));
		properties.setProperty(TOP_BG_KEY, prop.get(TOP_BG_INDEX));
		properties.setProperty(BOTTOM_BG_KEY, prop.get(BOTTOM_BG_INDEX));
		properties.setProperty(TOP_FONT_COLOR_KEY, prop.get(TOP_FONT_COLOR_INDEX));
		properties.setProperty(BOTTOM_FONT_COLOR_KEY, prop.get(BOTTOM_FONT_COLOR_INDEX));
		properties.setProperty(FONT_SIZE_KEY, prop.get(FONT_SIZE_INDEX));
		properties.setProperty(FONT_FAMILY_KEY, prop.get(FONT_FAMILY_INDEX));
		properties.setProperty(BUTTONS_COLOR_KEY, prop.get(BUTTONS_COLOR_INDEX));
		
		File file = new File(FILE_NAME);
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.storeToXML(fileOut, "colors");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
