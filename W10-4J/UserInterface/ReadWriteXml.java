/*
 * This class is used to read and write xml files. 
 * This class is currently used in SettingsUI.java to store properties of display output and command output in the GUI. Generally, it is used to store the background and font colors of both displays.
 * It is also used in UserInterface.java to get the properties of display output and command output so set the GUI.
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
	private int colorOptionIndex = 0;
	private int topFontColorIndex = 1;
	private int topBgIndex = 2;
	private int bottomBgIndex = 3;
	private int bottomFontColorIndex = 4;
	private int fontSizeIndex = 5;
	private int fontFamilyIndex = 6;
	private int buttonsColorIndex = 7;
	
	public String read(String action, String fileName){
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
		String fileName = ".\\properties.xml";
		ArrayList<String> properties = new ArrayList<String>();
		String colorOption = read("colorOption", fileName);
		String topFontColor = read("topFontColor", fileName);
		String bottomFontColor = read("bottomFontColor", fileName);
		String topBg = read("topBg", fileName);
		String bottomBg = read("bottomBg", fileName);
		String fontSize = read("fontSize", fileName);
		String fontFamily = read("fontFamily", fileName);
		String buttonsColor = read("buttonsColor", fileName);
		properties.add(colorOptionIndex, colorOption);
		properties.add(topFontColorIndex, topFontColor);
		properties.add(topBgIndex, topBg);
		properties.add(bottomBgIndex, bottomBg);
		properties.add(bottomFontColorIndex, bottomFontColor);
		properties.add(fontSizeIndex, fontSize);
		properties.add(fontFamilyIndex, fontFamily);
		properties.add(buttonsColorIndex, buttonsColor);
		return properties;
	}
	
	public void setProperties(ArrayList<String> prop){
		Properties properties = new Properties();
		properties.setProperty("colorOption", prop.get(colorOptionIndex));
		properties.setProperty("topBg", prop.get(topBgIndex));
		properties.setProperty("bottomBg", prop.get(bottomBgIndex));
		properties.setProperty("topFontColor", prop.get(topFontColorIndex));
		properties.setProperty("bottomFontColor", prop.get(bottomFontColorIndex));
		properties.setProperty("fontSize", prop.get(fontSizeIndex));
		properties.setProperty("fontFamily", prop.get(fontFamilyIndex));
		properties.setProperty("buttonsColor", prop.get(buttonsColorIndex));
		
		File file = new File(".\\properties.xml");
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.storeToXML(fileOut, "colors");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
