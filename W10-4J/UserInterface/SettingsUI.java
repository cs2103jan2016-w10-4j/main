/*
 * This class is the GUI for the button GUI preferences. It currently allows the user to change the colors of the display output and command output.
 * Five colors are currently allowed to be changed. They are named Sea, Sunset, Dawn, Nature.
 * 
 * @@author A0113761M
 */
package UserInterface;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class SettingsUI {
	private static int colorOptionIndex = 0;
	private static int topFontColorIndex = 1;
	private static int topBgIndex = 2;
	private static int bottomBgIndex = 3;
	private static int bottomFontColorIndex = 4;
	
	private static String black = "#000000 r:0, g:0, b:0";
	private static String white = "#ffffff r:255, g:255, b:255";
	
    public SettingsUI(JTextPane outputDisplay, JTextArea cmdDisplay) {
        initComponents(outputDisplay, cmdDisplay);
    }
    
    private void initComponents(JTextPane outputDisplay, JTextArea cmdDisplay) {
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
        Button saveButton = new Button();
        ReadProperties prop = new ReadProperties();
        
        JTextPane textPane1 = new JTextPane();
        jScrollPane1.setViewportView(textPane1);
        JTextPane textPane2 = new JTextPane();
        jScrollPane2.setViewportView(textPane2);

        ArrayList<String> properties = prop.readToArrayList();
        String colorOption = properties.get(0);
        
        ButtonGroup button = radioButtonsSettings(defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton,
				natureRadioButton);
        
        radioButtonSelectionAndDisplayExample(defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton, natureRadioButton,
				textPane1, textPane2, colorOption, button);

        setIcon(f);
        cancelButton.setLabel("Cancel");
        saveButton.setLabel("Save");
        
        setLayoutForSettingsUI(f, jPanel3, defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton, natureRadioButton,
				jScrollPane1, jScrollPane2, cancelButton, saveButton);

        action(f, defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton, natureRadioButton, textPane1, textPane2, saveButton, cancelButton, properties, outputDisplay, cmdDisplay);

		lookAndFeel();
		
        f.pack();
        f.setVisible(true);
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
			Button saveButton) {
		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
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
                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(defaultRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(seaRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sunsetRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dawnRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(natureRadioButton)
                .addContainerGap(247, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(f.getContentPane());
        f.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
	}

	private void radioButtonSelectionAndDisplayExample(JRadioButton defaultRadioButton, JRadioButton seaRadioButton,
			JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton, JRadioButton natureRadioButton, JTextPane textPane1,
			JTextPane textPane2, String colorOption, ButtonGroup button) {
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
    
    private static void action(JFrame f, JRadioButton defaultRadioButton, JRadioButton seaRadioButton, JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton, JRadioButton natureRadioButton, JTextPane textPane1, JTextPane textPane2, Button save, Button cancel, ArrayList<String> properties, JTextPane outputDisplay, JTextArea cmdDisplay){
    	defaultRadioListener(defaultRadioButton, textPane1, textPane2);
    	seaRadioListener(seaRadioButton, textPane1, textPane2);
    	sunsetRadioListener(sunsetRadioButton, textPane1, textPane2);
    	dawnRadioListener(dawnRadioButton, textPane1, textPane2);
    	natureRadioListener(natureRadioButton, textPane1, textPane2);
    	saveButtonListener(f, defaultRadioButton, seaRadioButton, sunsetRadioButton, dawnRadioButton, natureRadioButton,
				save, properties, outputDisplay, cmdDisplay);
    	cancelButtonListener(f, cancel);
    }

	private static void saveButtonListener(JFrame f, JRadioButton defaultRadioButton, JRadioButton seaRadioButton,
			JRadioButton sunsetRadioButton, JRadioButton dawnRadioButton, JRadioButton natureRadioButton, Button save,
			ArrayList<String> properties, JTextPane outputDisplay, JTextArea cmdDisplay) {
		save.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			String colorOption, topBg, bottomBg;
    			ReadProperties prop = new ReadProperties();
    			if (defaultRadioButton.isSelected()){
    				colorOption = "default";
    				topBg = "#ffffff r:255, g:255, b:255";
    				bottomBg = "#000000 r:0, g:0, b:0";
    				setProperties(properties, colorOption, black, white, topBg, bottomBg);
    				prop.setProperties(properties);
    				outputDisplay.setBackground(new Color(255, 255, 255));
    				cmdDisplay.setBackground(new Color(0, 0, 0));
    				cmdDisplay.setForeground(Color.WHITE);
    			} else if (seaRadioButton.isSelected()){
    				colorOption = "option1";
    				topBg = "#B4D8E7 r:180, g:216, b: 231";
    				bottomBg = "#56BAEC r:86, g:186, b:236";
    				setProperties(properties, colorOption, black, white, topBg, bottomBg);
    				prop.setProperties(properties);
    				outputDisplay.setBackground(new Color(180, 216, 231));
    				cmdDisplay.setBackground(new Color(86, 186, 236));
    				cmdDisplay.setForeground(Color.BLACK);
    			} else if (sunsetRadioButton.isSelected()){
    				colorOption = "option2";
    				topBg = "#C3C3E5 r:195, g:195, b: 229";
    				bottomBg = "#443266 r:68, g:50, b:102";
    				setProperties(properties, colorOption, black, white, topBg, bottomBg);
    				prop.setProperties(properties);
    				outputDisplay.setBackground(new Color(195, 195, 229));
    				cmdDisplay.setBackground(new Color(68, 50, 102));
    				cmdDisplay.setForeground(Color.WHITE);
    			} else if (dawnRadioButton.isSelected()){
    				colorOption = "option3";
    				topBg = "#EECD86 r:238, g:205, b: 134";
    				bottomBg = "#E18942 r:225, g:137, b:66";
    				setProperties(properties, colorOption, black, black, topBg, bottomBg);
    				prop.setProperties(properties);
    				outputDisplay.setBackground(new Color(238, 205, 134));
    				cmdDisplay.setBackground(new Color(225, 137, 66));
    				cmdDisplay.setForeground(Color.BLACK);
    			} else if (natureRadioButton.isSelected()){
    				colorOption = "option4";
    				topBg = "#C7E1BA r:199, g:225, b: 186";
    				bottomBg = "#397249 r:57, g:114, b:73";
    				setProperties(properties, colorOption, black, white, topBg, bottomBg);
    				prop.setProperties(properties);
    				outputDisplay.setBackground(new Color(199, 225, 196));
    				cmdDisplay.setBackground(new Color(57, 114, 73));
    				cmdDisplay.setForeground(Color.WHITE);
    			}
    			f.dispose();
    		}
    	});
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
    
	private void setIcon(JFrame f) {
				String icon = ".\\main\\icon\\d.png";
				ImageIcon img = new ImageIcon(icon);
				f.setIconImage(img.getImage());
	}
	
	private static void setProperties(ArrayList<String> properties, String colorOption, String topFont, String bottomFont, String topBg, String bottomBg){
		properties.set(colorOptionIndex, colorOption);
		properties.set(topFontColorIndex, topFont);
		properties.set(bottomFontColorIndex, bottomFont);
		properties.set(topBgIndex, topBg);
		properties.set(bottomBgIndex, bottomBg);
		
	}
}
