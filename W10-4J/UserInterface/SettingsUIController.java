/*
 * This class is the controller for the settings UI. Generally it determines which options for colors, font size and font family that the user selected, and sets those preferences.
 * The preferences are then saved into the properties.xml file.
 * 
 * @@author A0113761M
 */
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

import main.Constants;

public class SettingsUIController {
	private static ColorsForSettings colors = new ColorsForSettings();
	private static ReadWriteXml prop = new ReadWriteXml();

	public void action(JFrame f, JRadioButton defaultRadioButton, JRadioButton seaRadioButton,
			JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton, JRadioButton natureRadioButton,
			JTextPane textPane1, JTextPane textPane2, Button save, Button cancel, ArrayList<String> properties,
			JTextPane outputDisplay, JTextArea cmdDisplay, JComboBox<String> fontSize, JComboBox<String> fontFamily,
			JLabel commandText, JTextField cmdEntry, JButton home, JButton overdue, JButton all, JButton done,
			JButton help, JButton settings) {
		defaultRadioListener(defaultRadioButton, textPane1, textPane2);
		optionOneListener(seaRadioButton, textPane1, textPane2);
		optionTwoListener(sunsetRadioButton, textPane1, textPane2);
		optionThreeListener(dawnRadioButton, textPane1, textPane2);
		optionFourListener(natureRadioButton, textPane1, textPane2, home, overdue, all, done, help, settings);
		saveButtonListener(f, defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton, natureRadioButton,
				save, properties, outputDisplay, cmdDisplay, fontSize, fontFamily, commandText, cmdEntry, home, overdue,
				all, done, help, settings);
		cancelButtonListener(f, cancel);
	}

	/* These are the sets of listeners for the various buttons in SettingsUI */
	private static void saveButtonListener(JFrame f, JRadioButton defaultRadioButton, JRadioButton seaRadioButton,
			JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton, JRadioButton natureRadioButton, Button save,
			ArrayList<String> properties, JTextPane outputDisplay, JTextArea cmdDisplay, JComboBox<String> fontSizeBox,
			JComboBox<String> fontFamilyBox, JLabel commandText, JTextField cmdEntry, JButton home, JButton overdue,
			JButton all, JButton done, JButton help, JButton settings) {
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (defaultRadioButton.isSelected()) {
					setVariables(properties, prop, Constants.DEFAULT_COLOR_TEXT, colors.getTopBg(Constants.SET_5),
							colors.getBottomBg(Constants.SET_5), outputDisplay, cmdDisplay, commandText, cmdEntry,
							fontSizeBox, fontFamilyBox, colors.getButton(Constants.SET_5));
					setButtonColors(home, overdue, all, done, help, settings, Constants.SET_5);
				} else if (seaRadioButton.isSelected()) {
					setVariables(properties, prop, Constants.OPTION_1_TEXT, colors.getTopBg(Constants.SET_1),
							colors.getBottomBg(Constants.SET_1), outputDisplay, cmdDisplay, commandText, cmdEntry,
							fontSizeBox, fontFamilyBox, colors.getButton(Constants.SET_1));
					setButtonColors(home, overdue, all, done, help, settings, Constants.SET_1);
				} else if (sunsetRadioButton.isSelected()) {
					setVariables(properties, prop, Constants.OPTION_2_TEXT, colors.getTopBg(Constants.SET_2),
							colors.getBottomBg(Constants.SET_2), outputDisplay, cmdDisplay, commandText, cmdEntry,
							fontSizeBox, fontFamilyBox, colors.getButton(Constants.SET_2));
					setButtonColors(home, overdue, all, done, help, settings, Constants.SET_2);
				} else if (dawnRadioButton.isSelected()) {
					setVariables(properties, prop, Constants.OPTION_3_TEXT, colors.getTopBg(Constants.SET_3),
							colors.getBottomBg(Constants.SET_3), outputDisplay, cmdDisplay, commandText, cmdEntry,
							fontSizeBox, fontFamilyBox, colors.getButton(Constants.SET_3));
					setButtonColors(home, overdue, all, done, help, settings, Constants.SET_3);
				} else if (natureRadioButton.isSelected()) {
					setVariables(properties, prop, Constants.OPTION_4_TEXT, colors.getTopBg(Constants.SET_4),
							colors.getBottomBg(Constants.SET_4), outputDisplay, cmdDisplay, commandText, cmdEntry,
							fontSizeBox, fontFamilyBox, colors.getButton(Constants.SET_4));
					setButtonColors(home, overdue, all, done, help, settings, Constants.SET_4);
				}
				f.dispose();
			}
		});
	}

	private static void cancelButtonListener(JFrame f, Button cancel) {
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.dispose();
			}
		});
	}

	private static void optionFourListener(JRadioButton natureRadioButton, JTextPane textPane1, JTextPane textPane2,
			JButton home, JButton overdue, JButton all, JButton done, JButton help, JButton settings) {
		natureRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colors.optionFourColor(textPane1, textPane2);
			}
		});
	}

	private static void optionThreeListener(JRadioButton dawnRadioButton, JTextPane textPane1, JTextPane textPane2) {
		dawnRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colors.optionThreeColor(textPane1, textPane2);
			}
		});
	}

	private static void optionTwoListener(JRadioButton sunsetRadioButton, JTextPane textPane1, JTextPane textPane2) {
		sunsetRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colors.optionTwoColor(textPane1, textPane2);
			}
		});
	}

	private static void optionOneListener(JRadioButton seaRadioButton, JTextPane textPane1, JTextPane textPane2) {
		seaRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colors.optionOneColor(textPane1, textPane2);
			}
		});
	}

	private static void defaultRadioListener(JRadioButton defaultRadioButton, JTextPane textPane1,
			JTextPane textPane2) {
		defaultRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colors.defaultColor(textPane1, textPane2);
			}
		});
	}

	public static void setVariables(ArrayList<String> properties, ReadWriteXml prop, String colorOption, String topBg,
			String bottomBg, JTextPane outputDisplay, JTextArea cmdDisplay, JLabel commandText, JTextField cmdEntry,
			JComboBox<String> fontSizeBox, JComboBox<String> fontFamilyBox, String buttonsColor) {
		int fontSize = Integer.valueOf((String) fontSizeBox.getSelectedItem());
		String fontFamily = (String) fontFamilyBox.getSelectedItem();

		setOutputDisplay(topBg, outputDisplay, fontSize, fontFamily);
		setCommandText(commandText, fontSize, fontFamily);
		setCmdDisplay(bottomBg, cmdDisplay, fontSize, fontFamily);
		setCmdEntry(cmdEntry, fontSize, fontFamily);

		setPropertiesArrayList(properties, colorOption, topBg, bottomBg, fontSize, fontFamily, buttonsColor);
		prop.setProperties(properties);
	}

	/*
	 * These methods sets the components of the UI to the user's preferred
	 * settings after the save button is pressed.
	 */
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
			JButton settings, String set) {
		home.setBackground(colors.rgbColor(colors.getButton(set)));
		overdue.setBackground(colors.rgbColor(colors.getButton(set)));
		all.setBackground(colors.rgbColor(colors.getButton(set)));
		done.setBackground(colors.rgbColor(colors.getButton(set)));
		help.setBackground(colors.rgbColor(colors.getButton(set)));
		settings.setBackground(colors.rgbColor(colors.getButton(set)));
	}

	private static void setPropertiesArrayList(ArrayList<String> properties, String colorOption, String topBg,
			String bottomBg, int fontSize, String fontFamily, String buttonsColor) {
		properties.set(ReadWriteXml.COLOR_OPTION_INDEX, colorOption);
		properties.set(ReadWriteXml.TOP_BG_INDEX, topBg);
		properties.set(ReadWriteXml.BOTTOM_BG_INDEX, bottomBg);
		properties.set(ReadWriteXml.FONT_SIZE_INDEX, String.valueOf(fontSize));
		properties.set(ReadWriteXml.FONT_FAMILY_INDEX, fontFamily);
		properties.set(ReadWriteXml.BUTTONS_COLOR_INDEX, buttonsColor);
	}
}
