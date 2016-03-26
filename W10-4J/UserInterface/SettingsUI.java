package UserInterface;

import java.awt.Button;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

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
        JRadioButton jRadioButton1 = new JRadioButton();
        JRadioButton jRadioButton2 = new JRadioButton();
        JRadioButton jRadioButton3 = new JRadioButton();
        JRadioButton jRadioButton4 = new JRadioButton();
        JRadioButton jRadioButton5 = new JRadioButton();
        JScrollPane jScrollPane1 = new JScrollPane();
        JScrollPane jScrollPane2 = new JScrollPane();
        Button button1 = new Button();
        Button button2 = new Button();
        ReadProperties prop = new ReadProperties();
        
        JTextPane textPane1 = new JTextPane();
        jScrollPane1.setViewportView(textPane1);
        JTextPane textPane2 = new JTextPane();
        jScrollPane2.setViewportView(textPane2);

        ArrayList<String> properties = prop.readToArrayList();
        String colorOption = properties.get(0);
        
        jRadioButton1.setText("Default Colour");
        jRadioButton2.setText("Sea");
        jRadioButton3.setText("Sunset");
        jRadioButton4.setText("Dawn");
        jRadioButton5.setText("Nature");
        
        ButtonGroup button = new ButtonGroup();
        button.add(jRadioButton1);
        button.add(jRadioButton2);
        button.add(jRadioButton3);
        button.add(jRadioButton4);
        button.add(jRadioButton5);
        if (colorOption.equals("default")){
        	button.setSelected(jRadioButton1.getModel(), true);
        	textPane1.setBackground(Color.WHITE);
        	textPane2.setBackground(Color.BLACK);
        } else if (colorOption.equals("option1")){
        	button.setSelected(jRadioButton2.getModel(), true);
			textPane1.setBackground(new Color(180, 216, 231));
			textPane2.setBackground(new Color(86, 186, 236));
        } else if (colorOption.equals("option2")) {
        	button.setSelected(jRadioButton3.getModel(), true);
			textPane1.setBackground(new Color(195, 195, 229));
			textPane2.setBackground(new Color(68, 50, 102));
        } else if (colorOption.equals("option3")) {
        	button.setSelected(jRadioButton4.getModel(), true);
			textPane1.setBackground(new Color(238, 205, 134));
			textPane2.setBackground(new Color(225, 137, 66));
        } else if (colorOption.equals("option4")) {
        	button.setSelected(jRadioButton5.getModel(), true);
			textPane1.setBackground(new Color(199, 225, 196));
			textPane2.setBackground(new Color(57, 114, 73));
        }

        setIcon(f);
        
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton5))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton5)
                .addContainerGap(247, Short.MAX_VALUE))
        );

        button1.setLabel("Cancel");

        button2.setLabel("Save");

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
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        action(f, jRadioButton1, jRadioButton2, jRadioButton3, jRadioButton4, jRadioButton5, textPane1, textPane2, button2, button1, properties, outputDisplay, cmdDisplay);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        f.pack();
        f.setVisible(true);
    }
    
    private static void action(JFrame f, JRadioButton button1, JRadioButton button2, JRadioButton button3, JRadioButton button4, JRadioButton button5, JTextPane textPane1, JTextPane textPane2, Button save, Button cancel, ArrayList<String> properties, JTextPane outputDisplay, JTextArea cmdDisplay){
    	button1.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			textPane1.setBackground(Color.WHITE);
    			textPane2.setBackground(Color.BLACK);
    		}
    	});
    	button2.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
				textPane1.setBackground(new Color(180, 216, 231));
				textPane2.setBackground(new Color(86, 186, 236));
    		}
    	});
    	button3.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
				textPane1.setBackground(new Color(195, 195, 229));
				textPane2.setBackground(new Color(68, 50, 102));
    		}
    	});
    	button4.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			textPane1.setBackground(new Color(238, 205, 134));
    			textPane2.setBackground(new Color(225, 137, 66));
    		}
    	});
    	button5.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
				textPane1.setBackground(new Color(199, 225, 196));
				textPane2.setBackground(new Color(57, 114, 73));
    		}
    	});
    	save.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			String colorOption, topBg, bottomBg;
    			ReadProperties prop = new ReadProperties();
    			if (button1.isSelected()){
    				colorOption = "default";
    				topBg = "#ffffff r:255, g:255, b:255";
    				bottomBg = "#000000 r:0, g:0, b:0";
    				setProperties(properties, colorOption, black, white, topBg, bottomBg);
    				prop.setProperties(properties);
    				outputDisplay.setBackground(new Color(255, 255, 255));
    				cmdDisplay.setBackground(new Color(0, 0, 0));
    				cmdDisplay.setForeground(Color.WHITE);
    			} else if (button2.isSelected()){
    				colorOption = "option1";
    				topBg = "#B4D8E7 r:180, g:216, b: 231";
    				bottomBg = "#56BAEC r:86, g:186, b:236";
    				setProperties(properties, colorOption, black, white, topBg, bottomBg);
    				prop.setProperties(properties);
    				outputDisplay.setBackground(new Color(180, 216, 231));
    				cmdDisplay.setBackground(new Color(86, 186, 236));
    				cmdDisplay.setForeground(Color.BLACK);
    			} else if (button3.isSelected()){
    				colorOption = "option2";
    				topBg = "#C3C3E5 r:195, g:195, b: 229";
    				bottomBg = "#443266 r:68, g:50, b:102";
    				setProperties(properties, colorOption, black, white, topBg, bottomBg);
    				prop.setProperties(properties);
    				outputDisplay.setBackground(new Color(195, 195, 229));
    				cmdDisplay.setBackground(new Color(68, 50, 102));
    				cmdDisplay.setForeground(Color.WHITE);
    			} else if (button4.isSelected()){
    				colorOption = "option3";
    				topBg = "#EECD86 r:238, g:205, b: 134";
    				bottomBg = "#E18942 r:225, g:137, b:66";
    				setProperties(properties, colorOption, black, black, topBg, bottomBg);
    				prop.setProperties(properties);
    				outputDisplay.setBackground(new Color(238, 205, 134));
    				cmdDisplay.setBackground(new Color(225, 137, 66));
    				cmdDisplay.setForeground(Color.BLACK);
    			} else if (button5.isSelected()){
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
    	cancel.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			f.dispose();
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
