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
		JScrollPane jScrollPane1 = new JScrollPane();
		JTextPane outputDisplay = new JTextPane();
		JButton homeButton = new JButton();
		JButton overdueButton = new JButton();
		JButton doneButton = new JButton();
		JButton allButton = new JButton();
		JButton helpButton = new JButton();
		JButton settingsButton = new JButton();
		JScrollPane jScrollPane2 = new JScrollPane();
		JTextArea cmdDisplay = new JTextArea();
		JTextField cmdEntry = new JTextField();
		JLabel commandText = new JLabel();
		commandText.setText("command: ");

		setIcon(f);
		textAreaSettings(cmdDisplay);
		textPaneSettings(outputDisplay);
		buttonSettings(homeButton, overdueButton, doneButton, allButton, helpButton, settingsButton);
		setWelcomeMessage(outputDisplay);

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(outputDisplay);
        jScrollPane2.setViewportView(cmdDisplay);

        GroupLayout layout = new GroupLayout(f.getContentPane());
        f.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(overdueButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(doneButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(allButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(helpButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(settingsButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
                
                .addComponent(jScrollPane1))
            .addGroup(layout.createSequentialGroup().addContainerGap()
            		.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup().addContainerGap()
            		.addComponent(commandText)
            		.addComponent(cmdEntry, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(homeButton, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(doneButton, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(overdueButton, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(allButton, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(helpButton, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(settingsButton, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup()
                		.addComponent(commandText)
                		.addComponent(cmdEntry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );
		
		uiControl.keyboardActions(outputDisplay, cmdEntry, jScrollPane1);
		uiControl.commandAction(p, overdueButton, allButton, doneButton, helpButton, settingsButton, homeButton, welcomeMessage(), cmdEntry, cmdDisplay, outputDisplay);
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
		ReadWriteXml prop = new ReadWriteXml();
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
		ReadWriteXml prop = new ReadWriteXml();
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
    
    private static void buttonSettings(JButton homeButton, JButton overdueButton, JButton doneButton, JButton allButton, JButton helpButton, JButton settingsButton){
    	setToolTipForButtons(homeButton, overdueButton, doneButton, allButton,
				helpButton, settingsButton);
		setIconsForButtons(homeButton, overdueButton, doneButton, allButton,
				helpButton, settingsButton);
		homeButton.setFocusable(false);
		overdueButton.setFocusable(false);
		doneButton.setFocusable(false);
		allButton.setFocusable(false);
		helpButton.setFocusable(false);
		settingsButton.setFocusable(false);
    }

	private static void setIconsForButtons(JButton homeButton,
			JButton overdueButton, JButton doneButton, JButton allButton,
			JButton helpButton, JButton settingsButton) {
		String home = ".\\main\\icon\\home.png";
		ImageIcon homeImg = new ImageIcon(home);
		homeButton.setIcon(homeImg);
		String overdue = ".\\main\\icon\\overdue.png";
		ImageIcon overdueImg = new ImageIcon(overdue);
    	overdueButton.setIcon(overdueImg);
		String done = ".\\main\\icon\\done.png";
		ImageIcon doneImg = new ImageIcon(done);
    	doneButton.setIcon(doneImg);
		String all = ".\\main\\icon\\all.png";
		ImageIcon allImg = new ImageIcon(all);
    	allButton.setIcon(allImg);
		String help = ".\\main\\icon\\help.png";
		ImageIcon helpImg = new ImageIcon(help);
    	helpButton.setIcon(helpImg);
		String settings = ".\\main\\icon\\settings.png";
		ImageIcon settingsImg = new ImageIcon(settings);
    	settingsButton.setIcon(settingsImg);
	}

	private static void setToolTipForButtons(JButton homeButton,
			JButton overdueButton, JButton doneButton, JButton allButton,
			JButton helpButton, JButton settingsButton) {
		homeButton.setToolTipText("Home");
    	overdueButton.setToolTipText("Overdue Tasks");
    	doneButton.setToolTipText("Done Tasks");
    	allButton.setToolTipText("All Tasks");
    	helpButton.setToolTipText("Help");
    	settingsButton.setToolTipText("Gui Preferences");
	}
    
    private static void setWelcomeMessage(JTextPane outputDisplay){
    	String display = welcomeMessage();
    	outputDisplay.setText(display);
    }

	private static String welcomeMessage() {
		String display = "<center style=\"font-size:24px\"><b>Welcome to Docket! </b></center><br> "
    			+ "<center>Docket is a simple command line Windows application that allows you to manage your events and deadlines effectively. </center><br><br>"
    			+ "<center>To start, enter a task in the command field below.</center><br>"
    			+ "<center>For help, enter <b>help</b> in the command field below.</center><br>";
    	int tipsSize = tipMessage().size();
    	Random rand = new Random();
    	int randomNum = rand.nextInt(tipsSize);
    	display += "<center style=\"font-size:18px\"><b>Tips</b></center><br><center>" + tipMessage().get(randomNum) + "</center>";
		return display;
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
			e.printStackTrace();
		}
	}
}