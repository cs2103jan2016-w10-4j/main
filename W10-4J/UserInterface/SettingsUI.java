/*
 * This class is the GUI for the button GUI preferences. It currently allows the user to change the colors of the display output and command output.
 * Five colors are currently allowed to be changed. They are named Sea, Sunset, Dawn, Nature.
 * 
 * @@author A0113761M
 */
package UserInterface;

import java.awt.Button;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SettingsUI {
	private static String fileName = ".\\properties.xml";
	
    public SettingsUI(JTextPane outputDisplay, JTextArea cmdDisplay, JLabel commandText, JTextField cmdEntry) {
        initComponents(outputDisplay, cmdDisplay, commandText, cmdEntry);
    }
    
    private void initComponents(JTextPane outputDisplay, JTextArea cmdDisplay, JLabel commandText, JTextField cmdEntry) {
    	JFrame f = new JFrame("Settings");
        JPanel jPanel3 = new JPanel();
        JRadioButton defaultRadioButton = new JRadioButton();
        JRadioButton seaRadioButton = new JRadioButton();
        JRadioButton sunsetRadioButton = new JRadioButton();
        JRadioButton dawnRadioButton = new JRadioButton();
        JRadioButton natureRadioButton = new JRadioButton();
        JScrollPane jScrollPane1 = new JScrollPane();
        JScrollPane jScrollPane2 = new JScrollPane();
        Button cancelButton = new Button();
        JComboBox<String> fontSizeBox = new JComboBox<String>();
        JComboBox<String> fontFamilyBox = new JComboBox<String>();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        Button saveButton = new Button();
        ReadWriteXml prop = new ReadWriteXml();
        
        ArrayList<String> properties = prop.readToArrayList();
        
        JTextPane textPane1 = new JTextPane();
        jScrollPane1.setViewportView(textPane1);
        JTextPane textPane2 = new JTextPane();
        jScrollPane2.setViewportView(textPane2);

        setIcon(f);
        fontSizeBoxSettings(fontSizeBox, prop);
        fontFamilyBoxSettings(fontFamilyBox, prop);
        jLabelSettings(jLabel1, jLabel2);
        cancelAndSaveButtonSettings(cancelButton, saveButton);
        ButtonGroup button = radioButtonsSettings(defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton,
				natureRadioButton);
        
        radioButtonSelectionAndDisplayExample(defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton, natureRadioButton,
				textPane1, textPane2, properties, button);
        
        setLayoutForSettingsUI(f, jPanel3, defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton, natureRadioButton,
				jScrollPane1, jScrollPane2, cancelButton, saveButton, fontSizeBox, fontFamilyBox, jLabel1, jLabel2);

        SettingsUIController controller = new SettingsUIController();
        controller.action(f, defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton, natureRadioButton, textPane1, textPane2, saveButton, cancelButton, properties, outputDisplay, cmdDisplay, fontSizeBox, fontFamilyBox, commandText, cmdEntry);

		lookAndFeel();
		
        f.pack();
        f.setVisible(true);
    }

	private void cancelAndSaveButtonSettings(Button cancelButton,
			Button saveButton) {
		cancelButton.setLabel("Cancel");
        saveButton.setLabel("Save");
	}

	private void jLabelSettings(JLabel jLabel1, JLabel jLabel2) {
		jLabel1.setText("Font Size");
        jLabel2.setText("Font");
	}

	private void fontFamilyBoxSettings(JComboBox<String> fontFamilyBox,
			ReadWriteXml prop) {
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
		fontFamilyBox.setModel(new DefaultComboBoxModel<String>(fonts));
        fontFamilyBox.setSelectedItem(prop.read("fontFamily", fileName));
	}

	private void fontSizeBoxSettings(JComboBox<String> fontSizeBox, ReadWriteXml prop) {
		fontSizeBox.setModel(new DefaultComboBoxModel<String>(new String[] { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        fontSizeBox.setSelectedItem(prop.read("fontSize", fileName));
	}

	private void lookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private void setLayoutForSettingsUI(JFrame f, JPanel jPanel3, JRadioButton defaultRadioButton,
			JRadioButton seaRadioButton, JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton,
			JRadioButton natureRadioButton, JScrollPane jScrollPane1, JScrollPane jScrollPane2, Button cancelButton,
			Button saveButton, JComboBox<String> fontSizeBox, JComboBox<String> fontFamilyBox, JLabel jLabel1, JLabel jLabel2) {
		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		panelHorizontalLayout(defaultRadioButton, seaRadioButton,
				sunsetRadioButton, dawnRadioButton, natureRadioButton,
				fontSizeBox, fontFamilyBox, jLabel1, jLabel2, jPanel3Layout);
        panelVerticalLayout(defaultRadioButton, seaRadioButton,
				sunsetRadioButton, dawnRadioButton, natureRadioButton,
				fontSizeBox, fontFamilyBox, jLabel1, jLabel2, jPanel3Layout);

        GroupLayout layout = new GroupLayout(f.getContentPane());
        f.getContentPane().setLayout(layout);
        horizontalLayout(jPanel3, jScrollPane1, jScrollPane2, cancelButton,
				saveButton, layout);
        verticalLayout(jPanel3, jScrollPane1, jScrollPane2, cancelButton,
				saveButton, layout);
	}

	private void verticalLayout(JPanel jPanel3, JScrollPane jScrollPane1,
			JScrollPane jScrollPane2, Button cancelButton, Button saveButton,
			GroupLayout layout) {
		layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );
	}

	private void horizontalLayout(JPanel jPanel3, JScrollPane jScrollPane1,
			JScrollPane jScrollPane2, Button cancelButton, Button saveButton,
			GroupLayout layout) {
		layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)))
        );
	}

	private void panelVerticalLayout(JRadioButton defaultRadioButton,
			JRadioButton seaRadioButton, JRadioButton sunsetRadioButton,
			JRadioButton dawnRadioButton, JRadioButton natureRadioButton,
			JComboBox<String> fontSizeBox, JComboBox<String> fontFamilyBox,
			JLabel jLabel1, JLabel jLabel2, GroupLayout jPanel3Layout) {
		jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(defaultRadioButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(seaRadioButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sunsetRadioButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dawnRadioButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(natureRadioButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(fontSizeBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(fontFamilyBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
	}

	private void panelHorizontalLayout(JRadioButton defaultRadioButton,
			JRadioButton seaRadioButton, JRadioButton sunsetRadioButton,
			JRadioButton dawnRadioButton, JRadioButton natureRadioButton,
			JComboBox<String> fontSizeBox, JComboBox<String> fontFamilyBox,
			JLabel jLabel1, JLabel jLabel2, GroupLayout jPanel3Layout) {
		jPanel3Layout.setHorizontalGroup(
				jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(jPanel3Layout.createSequentialGroup()
			                    .addContainerGap()
								.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(defaultRadioButton)
										.addComponent(seaRadioButton)
										.addComponent(sunsetRadioButton)
										.addComponent(dawnRadioButton)
										.addComponent(natureRadioButton))
	                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(jPanel3Layout.createSequentialGroup()
	                    .addContainerGap()
						.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(fontSizeBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(fontFamilyBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
	}

	private void radioButtonSelectionAndDisplayExample(JRadioButton defaultRadioButton, JRadioButton seaRadioButton,
			JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton, JRadioButton natureRadioButton, JTextPane textPane1,
			JTextPane textPane2, ArrayList<String> properties, ButtonGroup button) {
        String colorOption = properties.get(0);
		if (colorOption == null){
        	button.setSelected(defaultRadioButton.getModel(), true);
        	defaultColor(textPane1, textPane2);
        } else if (colorOption.equals("default")){
        	button.setSelected(defaultRadioButton.getModel(), true);
        	defaultColor(textPane1, textPane2);
        } else if (colorOption.equals("option1")){
        	button.setSelected(seaRadioButton.getModel(), true);
			seaColor(textPane1, textPane2);
        } else if (colorOption.equals("option2")) {
        	button.setSelected(sunsetRadioButton.getModel(), true);
			sunsetColor(textPane1, textPane2);
        } else if (colorOption.equals("option3")) {
        	button.setSelected(dawnRadioButton.getModel(), true);
			dawnColor(textPane1, textPane2);
        } else if (colorOption.equals("option4")) {
        	button.setSelected(natureRadioButton.getModel(), true);
			natureColor(textPane1, textPane2);
        }
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

	private ButtonGroup radioButtonsSettings(JRadioButton defaultRadioButton, JRadioButton seaRadioButton,
			JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton, JRadioButton natureRadioButton) {
		defaultRadioButton.setText("Default Colour");
        seaRadioButton.setText("Sea");
        sunsetRadioButton.setText("Sunset");
        dawnRadioButton.setText("Dawn");
        natureRadioButton.setText("Nature");
        
        ButtonGroup button = new ButtonGroup();
        button.add(defaultRadioButton);
        button.add(seaRadioButton);
        button.add(sunsetRadioButton);
        button.add(dawnRadioButton);
        button.add(natureRadioButton);
		return button;
	}
    
	private void setIcon(JFrame f) {
				String icon = ".\\main\\icon\\d.png";
				ImageIcon img = new ImageIcon(icon);
				f.setIconImage(img.getImage());
	}
}
