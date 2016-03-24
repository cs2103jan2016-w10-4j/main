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
	public ArrayList<String> read(){
		try{
			File file = new File("properties.xml");
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.loadFromXML(fileInput);
			fileInput.close();
			String colorOption = prop.getProperty("colorOption");
			String fontColor = prop.getProperty("fontColor");
			String topBg = prop.getProperty("topBg");
			String bottomBg = prop.getProperty("bottomBg");
			ArrayList<String> properties = new ArrayList<String>();
			properties.add(colorOption);
			properties.add(fontColor);
			properties.add(topBg);
			properties.add(bottomBg);
			return properties;
		}catch (FileNotFoundException e){
			return null;
		}catch(IOException e){
			return null;
		}
	}
	
	public void setProperties(ArrayList<String> prop){
		Properties properties = new Properties();
		properties.setProperty("colorOption", prop.get(0));
		properties.setProperty("topBg", prop.get(2));
		properties.setProperty("bottomBg", prop.get(3));
		
		File file = new File("properties.xml");
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.storeToXML(fileOut, "colors");
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		return new Color(Integer.valueOf(getRed(color)), Integer.valueOf(getGreen(color)), Integer.valueOf(getBlue(color)));
	}
}
