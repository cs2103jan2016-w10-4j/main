/*
 * This class is the GUI for the button for Settings. It currently allows the user to change the colors of the display output, command output and the buttons.
 * It also allows the user to change the font and font size of the whole program.
 * Five colors are currently allowed to be changed. They are named Default, Option 1, Option 2, Option 3, Option 4.
 * 
 * @@author A0113761M
 */
package UserInterface;

import java.awt.Button;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.Constants;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SettingsUI {
	private static final int COLOR_OPTION_INDEX = 0;

	static JFrame f = new JFrame(Constants.SETTINGS_TEXT);
	JPanel jPanel3 = new JPanel();
	JRadioButton defaultRadioButton = new JRadioButton();
	JRadioButton optionOneRadioButton = new JRadioButton();
	JRadioButton optionTwoRadioButton = new JRadioButton();
	JRadioButton optionThreeRadioButton = new JRadioButton();
	JRadioButton optionFourRadioButton = new JRadioButton();
	JTextPane textPane1 = new JTextPane();
	JTextPane textPane2 = new JTextPane();
	JScrollPane jScrollPane1 = new JScrollPane();
	JScrollPane jScrollPane2 = new JScrollPane();
	Button cancelButton = new Button();
	JComboBox<String> fontSizeBox = new JComboBox<String>();
	JComboBox<String> fontFamilyBox = new JComboBox<String>();
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	Button saveButton = new Button();

	private static ReadWriteXml prop = new ReadWriteXml();
	private static ArrayList<String> properties = prop.readToArrayList();
	private static String fontFamily = properties.get(ReadWriteXml.FONT_FAMILY_INDEX);
	private static String fontSize = properties.get(ReadWriteXml.FONT_SIZE_INDEX);

	private static ColorsForSettings colors = new ColorsForSettings();

	public SettingsUI(JTextPane outputDisplay, JTextArea cmdDisplay, JLabel commandText, JTextField cmdEntry,
			JButton home, JButton overdue, JButton all, JButton done, JButton help, JButton settings) {
		initComponents(outputDisplay, cmdDisplay, commandText, cmdEntry, home, overdue, all, done, help, settings);
	}

	private void initComponents(JTextPane outputDisplay, JTextArea cmdDisplay, JLabel commandText, JTextField cmdEntry,
			JButton home, JButton overdue, JButton all, JButton done, JButton help, JButton settings) {
		jScrollPane1.setViewportView(textPane1);
		jScrollPane2.setViewportView(textPane2);

		setIcon();
		fontSizeBoxSettings();
		fontFamilyBoxSettings();
		jLabelSettings();
		cancelAndSaveButtonSettings();
		ButtonGroup button = radioButtonsSettings();

		radioButtonSelectionAndDisplayExample(button);

		setLayoutForSettingsUI();

		SettingsUIController controller = new SettingsUIController();
		controller.action(f, defaultRadioButton, optionOneRadioButton, optionTwoRadioButton, optionThreeRadioButton,
				optionFourRadioButton, textPane1, textPane2, saveButton, cancelButton, properties, outputDisplay,
				cmdDisplay, fontSizeBox, fontFamilyBox, commandText, cmdEntry, home, overdue, all, done, help,
				settings);

		lookAndFeel();

		f.pack();
		f.setVisible(true);
	}

	private void setLayoutForSettingsUI() {
		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		panelHorizontalLayout(jPanel3Layout);
		panelVerticalLayout(jPanel3Layout);

		GroupLayout layout = new GroupLayout(f.getContentPane());
		f.getContentPane().setLayout(layout);
		horizontalLayout(layout);
		verticalLayout(layout);
	}

	private void verticalLayout(GroupLayout layout) {
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
						.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))));
	}

	private void horizontalLayout(GroupLayout layout) {
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
								.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup()
										.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(1, 1, 1).addComponent(cancelButton, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(jScrollPane2))));
	}

	private void panelVerticalLayout(GroupLayout jPanel3Layout) {
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(defaultRadioButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(optionOneRadioButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(optionTwoRadioButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(optionThreeRadioButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(optionFourRadioButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1).addComponent(fontSizeBox, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2).addComponent(fontFamilyBox, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(21, Short.MAX_VALUE)));
	}

	private void panelHorizontalLayout(GroupLayout jPanel3Layout) {
		jPanel3Layout
				.setHorizontalGroup(
						jPanel3Layout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
										.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(defaultRadioButton).addComponent(optionOneRadioButton)
												.addComponent(optionTwoRadioButton).addComponent(optionThreeRadioButton)
												.addComponent(optionFourRadioButton))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
						.addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(fontSizeBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(fontFamilyBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));
	}

	private void lookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private static void setIcon() {
		ImageIcon img = new ImageIcon(UserInterface.class.getResource(Constants.DOCKET_ICON_PATH));
		f.setIconImage(img.getImage());
	}

	private void cancelAndSaveButtonSettings() {
		cancelButton.setLabel(Constants.CANCEL_BUTTON_TEXT);
		saveButton.setLabel(Constants.SAVE_BUTTON_TEXT);
	}

	private void jLabelSettings() {
		jLabel1.setText(Constants.FONT_SIZE_TEXT);
		jLabel2.setText(Constants.FONT_TEXT);
	}

	private void fontFamilyBoxSettings() {
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontFamilyBox.setModel(new DefaultComboBoxModel<String>(fonts));
		if (fontFamily == null || fontFamily == "") {
			fontFamilyBox.setSelectedItem(Constants.DEFAULT_FONT_FAMILY);
		} else {
			fontFamilyBox.setSelectedItem(prop.read(Constants.FONT_FAMILY_KEY, Constants.PROPERTIES_FILE_NAME));
		}
	}

	private void fontSizeBoxSettings() {
		fontSizeBox.setModel(new DefaultComboBoxModel<String>(new String[] { "8", "9", "10", "11", "12", "13", "14",
				"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
		if (fontSize == null || fontSize == "") {
			fontSizeBox.setSelectedItem(Constants.DEFAULT_FONT_SIZE);
		} else {
			fontSizeBox.setSelectedItem(prop.read(Constants.FONT_SIZE_KEY, Constants.PROPERTIES_FILE_NAME));
		}
	}

	private ButtonGroup radioButtonsSettings() {
		defaultRadioButton.setText(Constants.DEFAULT_COLOR_TEXT);
		optionOneRadioButton.setText(Constants.OPTION_1_TEXT);
		optionTwoRadioButton.setText(Constants.OPTION_2_TEXT);
		optionThreeRadioButton.setText(Constants.OPTION_3_TEXT);
		optionFourRadioButton.setText(Constants.OPTION_4_TEXT);

		ButtonGroup button = new ButtonGroup();
		button.add(defaultRadioButton);
		button.add(optionOneRadioButton);
		button.add(optionTwoRadioButton);
		button.add(optionThreeRadioButton);
		button.add(optionFourRadioButton);
		return button;
	}

	private void radioButtonSelectionAndDisplayExample(ButtonGroup button) {
		String colorOption = properties.get(COLOR_OPTION_INDEX);
		if (colorOption == null || colorOption == Constants.EMPTY_STRING) {
			button.setSelected(defaultRadioButton.getModel(), true);
			colors.defaultColor(textPane1, textPane2);
		} else if (colorOption.equals(Constants.DEFAULT_COLOR_TEXT)) {
			button.setSelected(defaultRadioButton.getModel(), true);
			colors.defaultColor(textPane1, textPane2);
		} else if (colorOption.equals(Constants.OPTION_1_TEXT)) {
			button.setSelected(optionOneRadioButton.getModel(), true);
			colors.optionOneColor(textPane1, textPane2);
		} else if (colorOption.equals(Constants.OPTION_2_TEXT)) {
			button.setSelected(optionTwoRadioButton.getModel(), true);
			colors.optionTwoColor(textPane1, textPane2);
		} else if (colorOption.equals(Constants.OPTION_3_TEXT)) {
			button.setSelected(optionThreeRadioButton.getModel(), true);
			colors.optionThreeColor(textPane1, textPane2);
		} else if (colorOption.equals(Constants.OPTION_4_TEXT)) {
			button.setSelected(optionFourRadioButton.getModel(), true);
			colors.optionFourColor(textPane1, textPane2);
		}
	}
}
