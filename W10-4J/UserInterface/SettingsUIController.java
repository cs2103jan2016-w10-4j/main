package UserInterface;

import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
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
	private static int buttonsColorIndex = 7;
	
	private static ColorsForSettings colors = new ColorsForSettings();
	
	private static String black = "#000000 r:0, g:0, b:0";
	
    public void action(JFrame f, JRadioButton defaultRadioButton, JRadioButton seaRadioButton, JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton, JRadioButton natureRadioButton, JTextPane textPane1, JTextPane textPane2, Button save, Button cancel, ArrayList<String> properties, JTextPane outputDisplay, JTextArea cmdDisplay, JComboBox<String> fontSize, JComboBox<String> fontFamily, JLabel commandText, JTextField cmdEntry, JButton home, JButton overdue, JButton all, JButton done, JButton help, JButton settings){
    	defaultRadioListener(defaultRadioButton, textPane1, textPane2);
    	optionOneListener(seaRadioButton, textPane1, textPane2);
    	optionTwoListener(sunsetRadioButton, textPane1, textPane2);
    	optionThreeListener(dawnRadioButton, textPane1, textPane2);
    	optionFourListener(natureRadioButton, textPane1, textPane2, home, overdue, all, done, help, settings);
    	saveButtonListener(f, defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton, natureRadioButton,
				save, properties, outputDisplay, cmdDisplay, fontSize, fontFamily, commandText, cmdEntry, home, overdue, all, done, help, settings);
    	cancelButtonListener(f, cancel);
    }

	private static void saveButtonListener(JFrame f, JRadioButton defaultRadioButton, JRadioButton seaRadioButton,
			JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton, JRadioButton natureRadioButton, Button save,
			ArrayList<String> properties, JTextPane outputDisplay, JTextArea cmdDisplay, JComboBox<String> fontSizeBox, JComboBox<String> fontFamilyBox, JLabel commandText, JTextField cmdEntry, JButton home, JButton overdue, JButton all, JButton done, JButton help,
			JButton settings) {
		save.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			ReadWriteXml prop = new ReadWriteXml();
    			if (defaultRadioButton.isSelected()){
    				setVariables(properties, prop, "default", colors.getTopBg(ColorsForSettings.set5), colors.getBottomBg(ColorsForSettings.set5), black, black, outputDisplay, cmdDisplay, commandText, cmdEntry, fontSizeBox, fontFamilyBox, colors.getButton(ColorsForSettings.set5));
    				setButtonColors(home, overdue, all, done, help, settings, ColorsForSettings.set5);
    			} else if (seaRadioButton.isSelected()){
    				setVariables(properties, prop, "option1", colors.getTopBg(ColorsForSettings.set1), colors.getBottomBg(ColorsForSettings.set1), black, black, outputDisplay, cmdDisplay, commandText, cmdEntry, fontSizeBox, fontFamilyBox, colors.getButton(ColorsForSettings.set1));
    				setButtonColors(home, overdue, all, done, help, settings, ColorsForSettings.set1);
    			} else if (sunsetRadioButton.isSelected()){
    				setVariables(properties, prop, "option2", colors.getTopBg(ColorsForSettings.set2), colors.getBottomBg(ColorsForSettings.set2), black, black, outputDisplay, cmdDisplay, commandText, cmdEntry, fontSizeBox, fontFamilyBox, colors.getButton(ColorsForSettings.set2));
    				setButtonColors(home, overdue, all, done, help, settings, ColorsForSettings.set2);
    			} else if (dawnRadioButton.isSelected()){
    				setVariables(properties, prop, "option3", colors.getTopBg(ColorsForSettings.set3), colors.getBottomBg(ColorsForSettings.set3), black, black, outputDisplay, cmdDisplay, commandText, cmdEntry, fontSizeBox, fontFamilyBox, colors.getButton(ColorsForSettings.set3));
    				setButtonColors(home, overdue, all, done, help, settings, ColorsForSettings.set3);
    			} else if (natureRadioButton.isSelected()){
    				setVariables(properties, prop, "option4", colors.getTopBg(ColorsForSettings.set4), colors.getBottomBg(ColorsForSettings.set4), black, black, outputDisplay, cmdDisplay, commandText, cmdEntry, fontSizeBox, fontFamilyBox, colors.getButton(ColorsForSettings.set4));
    				setButtonColors(home, overdue, all, done, help, settings, ColorsForSettings.set4);
    			} 
    			f.dispose();
    		}
    	});
	}
	
	public static void setVariables(ArrayList<String> properties, ReadWriteXml prop, String colorOption, String topBg, String bottomBg, String bottomFontColor, String topFontColor, JTextPane outputDisplay, JTextArea cmdDisplay, JLabel commandText, JTextField cmdEntry, JComboBox<String> fontSizeBox, JComboBox<String> fontFamilyBox, String buttonsColor){
		int fontSize = Integer.valueOf((String) fontSizeBox.getSelectedItem());
		String fontFamily = (String) fontFamilyBox.getSelectedItem();
		
		setOutputDisplay(topBg, outputDisplay, fontSize, fontFamily);
		setCommandText(commandText, fontSize, fontFamily);
		setCmdDisplay(bottomBg, cmdDisplay, fontSize, fontFamily);
		setCmdEntry(cmdEntry, fontSize, fontFamily);
		
		setProperties(properties, colorOption, topFontColor, bottomFontColor, topBg, bottomBg, fontSize, fontFamily, buttonsColor);
		prop.setProperties(properties);
	}

	private static void setCmdEntry(JTextField cmdEntry, int fontSize, String fontFamily) {
		int fontStylecmdEntry = cmdEntry.getFont().getStyle();
		Font fontCmdEntry = new Font(fontFamily, fontStylecmdEntry, fontSize);
		cmdEntry.setFont(fontCmdEntry);
	}

	private static void setCmdDisplay(String bottomBg, JTextArea cmdDisplay, int fontSize, String fontFamily) {
		int fontStyleCmdDisplay = cmdDisplay.getFont().getStyle();
		Font fontCmdDisplay = new Font(fontFamily, fontStyleCmdDisplay, fontSize);
		cmdDisplay.setFont(fontCmdDisplay);
		cmdDisplay.setBackground(colors.rgbColor(bottomBg));
	}

	private static void setCommandText(JLabel commandText, int fontSize, String fontFamily) {
		int fontStyleCommandText = commandText.getFont().getStyle();
		Font fontCommandText = new Font(fontFamily, fontStyleCommandText, fontSize);
		commandText.setFont(fontCommandText);
	}

	private static void setOutputDisplay(String topBg, JTextPane outputDisplay, int fontSize, String fontFamily) {
		outputDisplay.setBackground(colors.rgbColor(topBg));
		int fontStyleDisplayOutput = outputDisplay.getFont().getStyle();
		Font fontDisplayOutput = new Font(fontFamily, fontStyleDisplayOutput, fontSize);
		outputDisplay.setFont(fontDisplayOutput);
	}
	
	private static void setButtonColors(JButton home, JButton overdue, JButton all, JButton done, JButton help,
			JButton settings, String set){
		home.setBackground(colors.rgbColor(colors.getButton(set)));
		overdue.setBackground(colors.rgbColor(colors.getButton(set)));
		all.setBackground(colors.rgbColor(colors.getButton(set)));
		done.setBackground(colors.rgbColor(colors.getButton(set)));
		help.setBackground(colors.rgbColor(colors.getButton(set)));
		settings.setBackground(colors.rgbColor(colors.getButton(set)));
	}

	private static void cancelButtonListener(JFrame f, Button cancel) {
		cancel.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			f.dispose();
    		}
    	});
	}

	private static void optionFourListener(JRadioButton natureRadioButton, JTextPane textPane1, JTextPane textPane2, JButton home, JButton overdue, JButton all, JButton done, JButton help,
			JButton settings) {
		natureRadioButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
				colors.optionFourColor(textPane1, textPane2);
    		}
    	});
	}

	private static void optionThreeListener(JRadioButton dawnRadioButton, JTextPane textPane1, JTextPane textPane2) {
		dawnRadioButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			colors.optionThreeColor(textPane1, textPane2);
    		}
    	});
	}

	private static void optionTwoListener(JRadioButton sunsetRadioButton, JTextPane textPane1, JTextPane textPane2) {
		sunsetRadioButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			colors.optionTwoColor(textPane1, textPane2);
    		}
    	});
	}

	private static void optionOneListener(JRadioButton seaRadioButton, JTextPane textPane1, JTextPane textPane2) {
		seaRadioButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			colors.optionOneColor(textPane1, textPane2);
    		}
    	});
	}

	private static void defaultRadioListener(JRadioButton defaultRadioButton, JTextPane textPane1,
			JTextPane textPane2) {
		defaultRadioButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			colors.defaultColor(textPane1, textPane2);
    		}
    	});
	}
	
	private static void setProperties(ArrayList<String> properties, String colorOption, String topFont, String bottomFont, String topBg, String bottomBg, int fontSize, String fontFamily, String buttonsColor){
		properties.set(colorOptionIndex, colorOption);
		properties.set(topFontColorIndex, topFont);
		properties.set(bottomFontColorIndex, bottomFont);
		properties.set(topBgIndex, topBg);
		properties.set(bottomBgIndex, bottomBg);
		properties.set(fontSizeIndex, String.valueOf(fontSize));
		properties.set(fontFamilyIndex, fontFamily);
		properties.set(buttonsColorIndex, buttonsColor);
	}
}
