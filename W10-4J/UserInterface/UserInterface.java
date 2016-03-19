package UserInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import Parser.Parser;


public class UserInterface{
	public static void main(String[] args){
		initComponents();
	}
    private static void initComponents() {
    	Parser p = new Parser();
    	UIController uiControl = new UIController();
		JFrame f = new JFrame("Docket");
		JTextField cmdEntry = new JTextField();
		JTextPane outputDisplay = new JTextPane();
		JTextArea cmdDisplay = new JTextArea();
		JLabel status = new JLabel();
		JLabel jLabel1 = new JLabel();
		
		setIcon(f);

		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		textAreaSettings(cmdDisplay);
		textPaneSettings(outputDisplay);
		
		JScrollPane jScrollPane1 = new JScrollPane(cmdDisplay);
		JScrollPane jScrollPane2 = new JScrollPane(outputDisplay);
		jLabel1.setText("command: ");

		GroupLayout layout = new GroupLayout(f.getContentPane());
		f.getContentPane().setLayout(layout);

		setHoriGroup(layout, jLabel1, cmdEntry, jScrollPane1, jScrollPane2, status);

		setVertGroup(layout, jScrollPane1, jScrollPane2, jLabel1, status, cmdEntry);
		
		setWelcomeMessage(outputDisplay);
		
		uiControl.keyboardActions(outputDisplay, cmdEntry, jScrollPane2);
		uiControl.commandAction(p, cmdEntry, cmdDisplay, outputDisplay);
		
		f.pack();
		f.setVisible(true);
    }
    
	private static void setIcon(JFrame f) {
				String icon = ".\\main\\icon\\d.png";
				ImageIcon img = new ImageIcon(icon);
				f.setIconImage(img.getImage());
	}
    
    private static void textAreaSettings(JTextArea cmdDisplay){
    	cmdDisplay.setColumns(20);
    	cmdDisplay.setLineWrap(true);
    	cmdDisplay.setRows(5);
    	cmdDisplay.setWrapStyleWord(true);
    	cmdDisplay.setEditable(false);
    	cmdDisplay.setFocusable(false);
    	cmdDisplay.setBackground(Color.BLACK);
    	cmdDisplay.setForeground(Color.WHITE);
    	cmdDisplay.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
    }
    
    private static void textPaneSettings(JTextPane outputDisplay){
    	outputDisplay.setEditable(false);
    	outputDisplay.setFocusable(false);
    	outputDisplay.setContentType("text/html");
		outputDisplay.putClientProperty(JTextPane.HONOR_DISPLAY_PROPERTIES, true);
    }
    
    private static void horiGroup3(GroupLayout.SequentialGroup h3, GroupLayout layout, JLabel jLabel1, JTextField cmdEntry){
		// Create a sequential group h3
		h3.addComponent(jLabel1);
		h3.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
		h3.addComponent(cmdEntry, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);
    }
    
    private static void horiGroupHp(GroupLayout.ParallelGroup hp, JScrollPane jScrollPane2, JScrollPane jScrollPane1, JLabel status){
		// Add a scroll pane and a label to the parallel group hp
		hp.addComponent(jScrollPane2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE);
		hp.addComponent(status, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE);
		hp.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE);    	
    }
    
    private static void setHoriGroup(GroupLayout layout, JLabel jLabel1, JTextField cmdEntry, JScrollPane jScrollPane1, JScrollPane jScrollPane2, JLabel status){
		// Create a parallel group for the horizontal axis
		GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		
    	// Create a sequential and a parallel groups
		GroupLayout.SequentialGroup hs = layout.createSequentialGroup();
		GroupLayout.ParallelGroup hp = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);

		hs.addContainerGap();

		GroupLayout.SequentialGroup h3 = layout.createSequentialGroup();
		horiGroup3(h3, layout, jLabel1, cmdEntry);

		hp.addGroup(h3);
		
		horiGroupHp(hp, jScrollPane2, jScrollPane1, status);
		
		hs.addGroup(hp);

		hs.addContainerGap();

		hGroup.addGroup(GroupLayout.Alignment.TRAILING, hs);
		layout.setHorizontalGroup(hGroup);
    }
    
    private static GroupLayout.SequentialGroup vertGroup1(GroupLayout layout, JScrollPane jScrollPane2, JScrollPane jScrollPane1, JLabel status, JLabel jLabel1, JTextField cmdEntry){
		GroupLayout.SequentialGroup v1 = layout.createSequentialGroup();
		v1.addContainerGap();
		
		GroupLayout.ParallelGroup v2 = vertGroup2(layout, jLabel1, cmdEntry);
		
		v1.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE);
		v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
		v1.addComponent(status);
		v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
		v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
		v1.addGroup(v2);
		v1.addContainerGap();
		
		return v1;
    }
    
    private static GroupLayout.ParallelGroup vertGroup2(GroupLayout layout, JLabel jLabel1, JTextField cmdEntry){
		GroupLayout.ParallelGroup v2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		v2.addComponent(jLabel1);
		v2.addComponent(cmdEntry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		return v2;
    }
    
    private static void setVertGroup(GroupLayout layout, JScrollPane jScrollPane1, JScrollPane jScrollPane2, JLabel jLabel1, JLabel status, JTextField cmdEntry){
		GroupLayout.ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.SequentialGroup v1 = vertGroup1(layout, jScrollPane2, jScrollPane1, status, jLabel1, cmdEntry);
		vGroup.addGroup(v1);
		layout.setVerticalGroup(vGroup);
	}
    
    private static void setWelcomeMessage(JTextPane displayOutput){
    	displayOutput.setText("<center style=\"font-size:24px\"><b>Welcome to Docket! </b></center><br> "
    			+ "Docket is a simple command line Windows application that allows you to manage your events and deadlines effectively. <br><br>"
    			+ "To start, enter a task in the command field below.");
    }
}