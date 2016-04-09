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
	public static final int TOP_BG_INDEX = 1;
	public static final int BOTTOM_BG_INDEX = 2;
	public static final int FONT_SIZE_INDEX = 3;
	public static final int FONT_FAMILY_INDEX = 4;
	public static final int BUTTONS_COLOR_INDEX = 5;

	ArrayList<String> properties = new ArrayList<String>();

	public ReadWriteXml() {
		properties = readToArrayList();
		setProperties(properties);
	}

	public String read(String action, String fileName) {
		try {
			File file = new File(fileName);
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.loadFromXML(fileInput);
			fileInput.close();
			String details = prop.getProperty(action);
			if (details.equals(Constants.EMPTY_STRING)) {
				return null;
			} else {
				return details;
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<String> readToArrayList() {
		try {
			for (int i = 0; i < Constants.PROPERTIES_KEYS.length; i++) {
				String keys = Constants.PROPERTIES_KEYS[i];
				String propertiesList = read(keys, Constants.PROPERTIES_FILE_NAME);
				if (propertiesList == null) {
					properties.add(Constants.EMPTY_STRING);
				} else {
					properties.add(propertiesList);
				}
			}
		} catch (NullPointerException e) {
			for (int i = 0; i < Constants.PROPERTIES_KEYS.length; i++) {
				properties.add(Constants.EMPTY_STRING);
			}
		}
		return properties;
	}

	public void setProperties(ArrayList<String> prop) {
		Properties properties = new Properties();
		for (int i = 0; i < Constants.PROPERTIES_KEYS.length; i++) {
			properties.setProperty(Constants.PROPERTIES_KEYS[i], prop.get(i));
		}

		File file = new File(Constants.PROPERTIES_FILE_NAME);
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.storeToXML(fileOut, Constants.COLORS_COMMENT_FOR_XML);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
