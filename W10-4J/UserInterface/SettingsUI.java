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

@SuppressWarnings("serial")
public class SettingsUI {
    public SettingsUI(JTextPane outputDisplay, JTextArea cmdDisplay) {
        initComponents(outputDisplay, cmdDisplay);
    }
    
    private void initComponents(JTextPane outputDisplay, JTextArea cmdDisplay) {
    	JFrame f = new JFrame("Settings");
        JPanel jPanel3 = new JPanel();
        JRadioButton jRadioButton1 = new JRadioButton();
        JRadioButton jRadioButton2 = new JRadioButton();
        JRadioButton jRadioButton3 = new JRadioButton();
        JScrollPane jScrollPane1 = new JScrollPane();
        JScrollPane jScrollPane2 = new JScrollPane();
        Button button1 = new Button();
        Button button2 = new Button();
        ReadProperties prop = new ReadProperties();
        
        JTextPane textPane1 = new JTextPane();
        jScrollPane1.setViewportView(textPane1);
        JTextPane textPane2 = new JTextPane();
        jScrollPane2.setViewportView(textPane2);

        ArrayList<String> properties = prop.read();
        String colorOption = properties.get(0);
        
        jRadioButton1.setText("Default Colour");
        jRadioButton2.setText("Colour 1");
        jRadioButton3.setText("Colour 2");
        
        ButtonGroup button = new ButtonGroup();
        button.add(jRadioButton1);
        button.add(jRadioButton2);
        button.add(jRadioButton3);
        if (colorOption.equals("default")){
        	button.setSelected(jRadioButton1.getModel(), true);
        	textPane1.setBackground(Color.WHITE);
        	textPane2.setBackground(Color.BLACK);
        } else if (colorOption.equals("option1")){
        	button.setSelected(jRadioButton2.getModel(), true);
        	textPane1.setBackground(new Color(142,210,201));
        	textPane2.setBackground(new Color(252, 244, 217));
        } else if (colorOption.equals("option2")) {
        	button.setSelected(jRadioButton3.getModel(), true);
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
                    .addComponent(jRadioButton3))
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

        action(f, jRadioButton1, jRadioButton2, jRadioButton3, textPane1, textPane2, button2, button1, properties, outputDisplay, cmdDisplay);
        
        f.pack();
        f.setVisible(true);
    }
    
    private static void action(JFrame f, JRadioButton button1, JRadioButton button2, JRadioButton button3, JTextPane textPane1, JTextPane textPane2, Button save, Button cancel, ArrayList<String> properties, JTextPane outputDisplay, JTextArea cmdDisplay){
    	button1.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			textPane1.setBackground(Color.WHITE);
    			textPane2.setBackground(Color.BLACK);
    		}
    	});
    	button2.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			textPane2.setBackground(new Color(142,210,201));
    			textPane1.setBackground(new Color(252, 244, 217));
    		}
    	});
    	button3.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			textPane1.setText("yyy");
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
    				properties.set(0, colorOption);
    				properties.set(2, topBg);
    				properties.set(3, bottomBg);
    				prop.setProperties(properties);
    				outputDisplay.setBackground(new Color(255, 255, 255));
    				cmdDisplay.setBackground(new Color(0, 0, 0));
    			} else if (button2.isSelected()){
    				colorOption = "option1";
    				topBg = "#FCF4D9 r:252, g:244, b: 217";
    				bottomBg = "#8ED2C9 r:142, g:210, b:201";
    				properties.set(0, colorOption);
    				properties.set(2, topBg);
    				properties.set(3, bottomBg);
    				prop.setProperties(properties);
    				outputDisplay.setBackground(new Color(252, 244, 217));
    				cmdDisplay.setBackground(new Color(142, 210, 201));
    			} else if (button3.isSelected()){
    				colorOption = "option2";
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
}
