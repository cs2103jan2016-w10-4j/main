package UserInterface;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class SettingsUIController {
	private static int colorOptionIndex = 0;
	private static int topFontColorIndex = 1;
	private static int topBgIndex = 2;
	private static int bottomBgIndex = 3;
	private static int bottomFontColorIndex = 4;
	private static int fontSizeIndex = 5;
	private static int fontFamilyIndex = 6;
	
	private static String black = "#000000 r:0, g:0, b:0";
	private static String white = "#ffffff r:255, g:255, b:255";
	
    public void action(JFrame f, JRadioButton defaultRadioButton, JRadioButton seaRadioButton, JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton, JRadioButton natureRadioButton, JTextPane textPane1, JTextPane textPane2, Button save, Button cancel, ArrayList<String> properties, JTextPane outputDisplay, JTextArea cmdDisplay, JComboBox<String> fontSize, JComboBox<String> fontFamily, JLabel commandText, JTextField cmdEntry){
    	defaultRadioListener(defaultRadioButton, textPane1, textPane2);
    	seaRadioListener(seaRadioButton, textPane1, textPane2);
    	sunsetRadioListener(sunsetRadioButton, textPane1, textPane2);
    	dawnRadioListener(dawnRadioButton, textPane1, textPane2);
    	natureRadioListener(natureRadioButton, textPane1, textPane2);
    	saveButtonListener(f, defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton, natureRadioButton,
				save, properties, outputDisplay, cmdDisplay, fontSize, fontFamily, commandText, cmdEntry);
    	cancelButtonListener(f, cancel);
    }

	private static void saveButtonListener(JFrame f, JRadioButton defaultRadioButton, JRadioButton seaRadioButton,
			JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton, JRadioButton natureRadioButton, Button save,
			ArrayList<String> properties, JTextPane outputDisplay, JTextArea cmdDisplay, JComboBox<String> fontSizeBox, JComboBox<String> fontFamilyBox, JLabel commandText, JTextField cmdEntry) {
		save.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			ReadWriteXml prop = new ReadWriteXml();
    			if (defaultRadioButton.isSelected()){
    				setVariables(properties, prop, "default", "#ffffff r:255, g:255, b:255", "#000000 r:0, g:0, b:0", white, black, outputDisplay, cmdDisplay, commandText, cmdEntry, fontSizeBox, fontFamilyBox);
    			} else if (seaRadioButton.isSelected()){
    				setVariables(properties, prop, "option1", "#B4D8E7 r:180, g:216, b: 231", "#56BAEC r:86, g:186, b:236", white, black, outputDisplay, cmdDisplay, commandText, cmdEntry, fontSizeBox, fontFamilyBox);
    			} else if (sunsetRadioButton.isSelected()){
    				setVariables(properties, prop, "option2", "#C3C3E5 r:195, g:195, b: 229", "#443266 r:68, g:50, b:102", white, black, outputDisplay, cmdDisplay, commandText, cmdEntry, fontSizeBox, fontFamilyBox);
    			} else if (dawnRadioButton.isSelected()){
    				setVariables(properties, prop, "option3", "#EECD86 r:238, g:205, b: 134", "#E18942 r:225, g:137, b:66", black, black, outputDisplay, cmdDisplay, commandText, cmdEntry, fontSizeBox, fontFamilyBox);
    			} else if (natureRadioButton.isSelected()){
    				setVariables(properties, prop, "option4", "#C7E1BA r:199, g:225, b: 186", "#397249 r:57, g:114, b:73", white, black, outputDisplay, cmdDisplay, commandText, cmdEntry, fontSizeBox, fontFamilyBox);
    			} 
    			f.dispose();
    		}
    	});
	}
	
	public static void setVariables(ArrayList<String> properties, ReadWriteXml prop, String colorOption, String topBg, String bottomBg, String bottomFontColor, String topFontColor, JTextPane outputDisplay, JTextArea cmdDisplay, JLabel commandText, JTextField cmdEntry, JComboBox<String> fontSizeBox, JComboBox<String> fontFamilyBox){
		outputDisplay.setBackground(prop.rgbColor(topBg));
		cmdDisplay.setBackground(prop.rgbColor(bottomBg));
		cmdDisplay.setForeground(Color.WHITE);

		String fontFamily = (String) fontFamilyBox.getSelectedItem();
		int fontStyleCmdDisplay = cmdDisplay.getFont().getStyle();
		int fontStyleDisplayOutput = outputDisplay.getFont().getStyle();
		int fontStyleCommandText = commandText.getFont().getStyle();
		int fontStylecmdEntry = cmdEntry.getFont().getStyle();
		int fontSize = Integer.valueOf((String) fontSizeBox.getSelectedItem());
		Font fontCmdDisplay = new Font(fontFamily, fontStyleCmdDisplay, fontSize);
		Font fontDisplayOutput = new Font(fontFamily, fontStyleDisplayOutput, fontSize);
		Font fontCommandText = new Font(fontFamily, fontStyleCommandText, fontSize);
		Font fontCmdEntry = new Font(fontFamily, fontStylecmdEntry, fontSize);
		cmdDisplay.setFont(fontDisplayOutput);
		outputDisplay.setFont(fontCmdDisplay);
		commandText.setFont(fontCommandText);
		cmdEntry.setFont(fontCmdEntry);
		
		setProperties(properties, colorOption, topFontColor, bottomFontColor, topBg, bottomBg, fontSize, fontFamily);
		prop.setProperties(properties);
	}

	private static void cancelButtonListener(JFrame f, Button cancel) {
		cancel.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			f.dispose();
    		}
    	});
	}

	private static void natureRadioListener(JRadioButton natureRadioButton, JTextPane textPane1, JTextPane textPane2) {
		natureRadioButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
				natureColor(textPane1, textPane2);
    		}
    	});
	}

	private static void dawnRadioListener(JRadioButton dawnRadioButton, JTextPane textPane1, JTextPane textPane2) {
		dawnRadioButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			dawnColor(textPane1, textPane2);
    		}
    	});
	}

	private static void sunsetRadioListener(JRadioButton sunsetRadioButton, JTextPane textPane1, JTextPane textPane2) {
		sunsetRadioButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
				sunsetColor(textPane1, textPane2);
    		}
    	});
	}

	private static void seaRadioListener(JRadioButton seaRadioButton, JTextPane textPane1, JTextPane textPane2) {
		seaRadioButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
				seaColor(textPane1, textPane2);
    		}
    	});
	}

	private static void defaultRadioListener(JRadioButton defaultRadioButton, JTextPane textPane1,
			JTextPane textPane2) {
		defaultRadioButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			defaultColor(textPane1, textPane2);
    		}
    	});
	}

	private static void natureColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(new Color(199, 225, 196));
		textPane2.setBackground(new Color(57, 114, 73));
	}

	private static void dawnColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(new Color(238, 205, 134));
		textPane2.setBackground(new Color(225, 137, 66));
	}

	private static void sunsetColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(new Color(195, 195, 229));
		textPane2.setBackground(new Color(68, 50, 102));
	}

	private static void seaColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(new Color(180, 216, 231));
		textPane2.setBackground(new Color(86, 186, 236));
	}

	private static void defaultColor(JTextPane textPane1, JTextPane textPane2) {
		textPane1.setBackground(Color.WHITE);
		textPane2.setBackground(Color.BLACK);
	}
	
	private static void setProperties(ArrayList<String> properties, String colorOption, String topFont, String bottomFont, String topBg, String bottomBg, int fontSize, String fontFamily){
		properties.set(colorOptionIndex, colorOption);
		properties.set(topFontColorIndex, topFont);
		properties.set(bottomFontColorIndex, bottomFont);
		properties.set(topBgIndex, topBg);
		properties.set(bottomBgIndex, bottomBg);
		properties.set(fontSizeIndex, String.valueOf(fontSize));
		properties.set(fontFamilyIndex, fontFamily);
		
	}
}
