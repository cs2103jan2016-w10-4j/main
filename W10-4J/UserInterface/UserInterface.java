/*
 * This is the class for the overall UI for Docket.
 * 
 * @@author A011371M
 */
package UserInterface;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import Parser.Parser;


public class UserInterface{
	private static int topFontColorIndex = 1;
	private static int bottomBgIndex = 3;
	private static int bottomFontColorIndex = 4;
	
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
		JScrollPane jScrollPane1 = new JScrollPane(cmdDisplay);
		JScrollPane jScrollPane2 = new JScrollPane(outputDisplay);
		JLabel status = new JLabel();
		JLabel jLabel1 = new JLabel();
		JButton settings = new JButton("GUI Preferences");
		
		settings.setFocusable(false);
		jLabel1.setText("command: ");
		setIcon(f);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		textAreaSettings(cmdDisplay);
		textPaneSettings(outputDisplay);

		GroupLayout layout = new GroupLayout(f.getContentPane());
		f.getContentPane().setLayout(layout);

		setHoriGroup(layout, settings, jLabel1, cmdEntry, jScrollPane1, jScrollPane2, status);

		setVertGroup(layout, settings, jScrollPane1, jScrollPane2, jLabel1, status, cmdEntry);
		
		setWelcomeMessage(outputDisplay);
		
		uiControl.keyboardActions(outputDisplay, cmdEntry, jScrollPane2);
		uiControl.commandAction(p, settings, cmdEntry, cmdDisplay, outputDisplay);
		lookAndFeel();
		
		f.pack();
		f.setVisible(true);
    }
    
	private static void setIcon(JFrame f) {
				String icon = ".\\main\\icon\\d.png";
				ImageIcon img = new ImageIcon(icon);
				f.setIconImage(img.getImage());
	}
    
    private static void textAreaSettings(JTextArea cmdDisplay){
		ReadProperties prop = new ReadProperties();
		ArrayList<String> properties = prop.readToArrayList();
		String bottomBg = properties.get(bottomBgIndex);
    	cmdDisplay.setColumns(20);
    	cmdDisplay.setLineWrap(true);
    	cmdDisplay.setRows(5);
    	cmdDisplay.setWrapStyleWord(true);
    	cmdDisplay.setEditable(false);
    	cmdDisplay.setFocusable(false);
		// If the background color obtained from the xml file is null, then default colors are used.
    	if (bottomBg == null){
    		cmdDisplay.setBackground(Color.BLACK);
    		cmdDisplay.setForeground(Color.WHITE);
    	} else {
    		cmdDisplay.setBackground(prop.rgbColor(bottomBg));
    		cmdDisplay.setForeground(prop.rgbColor(properties.get(bottomFontColorIndex)));
    	}
    	cmdDisplay.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
    }
    
    private static void textPaneSettings(JTextPane outputDisplay){
		ReadProperties prop = new ReadProperties();
		ArrayList<String> properties = prop.readToArrayList();
		String topBg = properties.get(2);
    	outputDisplay.setEditable(false);
    	outputDisplay.setFocusable(false);
		// If the background color obtained from the xml file is null, then default colors are used.
    	if (topBg == null){
    		outputDisplay.setBackground(Color.WHITE);
    		outputDisplay.setForeground(Color.BLACK);
    	} else {
    		outputDisplay.setBackground(prop.rgbColor(topBg));
    		outputDisplay.setForeground(prop.rgbColor(properties.get(topFontColorIndex)));
    	}
    	outputDisplay.setContentType("text/html");
		outputDisplay.putClientProperty(JTextPane.HONOR_DISPLAY_PROPERTIES, true);
    }
    
    private static void horiGroup3(GroupLayout.SequentialGroup h3, GroupLayout layout, JLabel jLabel1, JTextField cmdEntry){
		// Create a sequential group h3
		h3.addComponent(jLabel1);
		h3.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
		h3.addComponent(cmdEntry, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);
    }
    
    private static void horiGroupHp(GroupLayout.ParallelGroup hp, JButton settings, JScrollPane jScrollPane2, JScrollPane jScrollPane1, JLabel status){
		// Add a scroll pane and a label to the parallel group hp
    	hp.addComponent(settings, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE);
		hp.addComponent(jScrollPane2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE);
		hp.addComponent(status, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE);
		hp.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE);    	
    }
    
    private static void setHoriGroup(GroupLayout layout, JButton settings, JLabel jLabel1, JTextField cmdEntry, JScrollPane jScrollPane1, JScrollPane jScrollPane2, JLabel status){
		// Create a parallel group for the horizontal axis
		GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		
    	// Create a sequential and a parallel groups
		GroupLayout.SequentialGroup hs = layout.createSequentialGroup();
		GroupLayout.ParallelGroup hp = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);

		hs.addContainerGap();

		GroupLayout.SequentialGroup h3 = layout.createSequentialGroup();
		horiGroup3(h3, layout, jLabel1, cmdEntry);

		hp.addGroup(h3);
		
		horiGroupHp(hp, settings, jScrollPane2, jScrollPane1, status);
		
		hs.addGroup(hp);

		hs.addContainerGap();

		hGroup.addGroup(GroupLayout.Alignment.TRAILING, hs);
		layout.setHorizontalGroup(hGroup);
    }
    
    private static GroupLayout.SequentialGroup vertGroup1(GroupLayout layout, JButton settings, JScrollPane jScrollPane2, JScrollPane jScrollPane1, JLabel status, JLabel jLabel1, JTextField cmdEntry){
		GroupLayout.SequentialGroup v1 = layout.createSequentialGroup();
		v1.addContainerGap();
		
		GroupLayout.ParallelGroup v2 = vertGroup2(layout, jLabel1, cmdEntry);
		v1.addComponent(settings);
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
    
    private static void setVertGroup(GroupLayout layout, JButton settings, JScrollPane jScrollPane1, JScrollPane jScrollPane2, JLabel jLabel1, JLabel status, JTextField cmdEntry){
		GroupLayout.ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.SequentialGroup v1 = vertGroup1(layout, settings, jScrollPane2, jScrollPane1, status, jLabel1, cmdEntry);
		vGroup.addGroup(v1);
		layout.setVerticalGroup(vGroup);
	}
    
    private static void setWelcomeMessage(JTextPane displayOutput){
    	String display = "<center style=\"font-size:24px\"><b>Welcome to Docket! </b></center><br> "
    			+ "<center>Docket is a simple command line Windows application that allows you to manage your events and deadlines effectively. </center><br><br>"
    			+ "<center>To start, enter a task in the command field below.</center><br>"
    			+ "<center>For help, enter <b>help</b> in the command field below.</center><br>";
    	int tipsSize = tipMessage().size();
    	Random rand = new Random();
    	int randomNum = rand.nextInt(tipsSize);
    	display += "<center style=\"font-size:18px\"><b>Tips</b></center><br><center>" + tipMessage().get(randomNum) + "</center>";
    	displayOutput.setText(display);
    }
    
    private static ArrayList<String> tipMessage(){
    	ArrayList<String> tips = new ArrayList<String>();
    	tips.add("Use Ctrl + Shift + \"+\" or Ctrl + Shift + \"-\" to increase or decrease font size");
    	tips.add("Use Arrow Up or Arrow Down key to scroll through previous commands entered");
    	tips.add("Use PgUp or PgDown key to scroll through this display output");
    	tips.add("You can save your task file in any folder using <b> set directory</b> command");
    	tips.add("You can get <b>help</b> for specific commands by entering <b>help</b> &#60command&#62");
    	tips.add("Enter <b>display</b> to view your tasks");
    	tips.add("Natural Language is supported in Docket. See Natural Commands section of help for more info");
    	return tips;
    }
	private static void lookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}