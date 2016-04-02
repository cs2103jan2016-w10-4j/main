/*
 * This is the class for the overall UI for Docket.
 * 
 * @@author A0113761M
 */
package UserInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import Parser.Parser;


public class UserInterface{
	private static int topFontColorIndex = 1;
	private static int bottomBgIndex = 3;
	private static int bottomFontColorIndex = 4;
	private static int fontFamilyIndex = 6;
	private static int fontSizeIndex = 5;
	
	private static JFrame f = new JFrame("Docket");
	private static JScrollPane jScrollPane1 = new JScrollPane();
	private static JTextPane outputDisplay = new JTextPane();
	private static JButton homeButton = new JButton();
	private static JButton overdueButton = new JButton();
	private static JButton doneButton = new JButton();
	private static JButton allButton = new JButton();
	private static JButton helpButton = new JButton();
	private static JButton settingsButton = new JButton();
	private static JScrollPane jScrollPane2 = new JScrollPane();
	private static JTextArea cmdDisplay = new JTextArea();
	private static JTextField cmdEntry = new JTextField();
	private static JLabel commandText = new JLabel();
	
	private static Color placeholderForeground = new Color(160, 160, 160);
	
	public static void main(String[] args){
		initComponents(null);
	}
	
	/* initComponents method is set to return a string array for JUnit testing purposes.*/
    public static String[] initComponents(String s){
    	Parser p = new Parser();
		UIController uiControl = new UIController();

		setIcon();
		commandTextSettings();
		cmdEntrySettings();
		textAreaSettings();
		textPaneSettings(outputDisplay);
		buttonSettings(homeButton, overdueButton, doneButton, allButton, helpButton, settingsButton);
		setWelcomeMessage(outputDisplay);
		Timer timer = setTodayTaskMessage(p);

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(outputDisplay);
        jScrollPane2.setViewportView(cmdDisplay);

        setLayoutForUI(f, jScrollPane1, homeButton, overdueButton, doneButton,
				allButton, helpButton, settingsButton, jScrollPane2, cmdEntry,
				commandText);
		
    	uiControl.keyboardActions(outputDisplay, cmdEntry, jScrollPane1);

        uiControl.commandAction(timer, s, overdueButton, allButton, doneButton, helpButton, settingsButton, homeButton, cmdEntry, cmdDisplay, outputDisplay, commandText);
        
		lookAndFeel();
		f.pack();
		f.setVisible(true);
        return returnOutput();
    }
    
    private static String[] returnOutput(){
    	String[] output = new String[2];
    	output[0] = outputDisplay.getText();
    	output[1] = cmdDisplay.getText();
    	return output;
    }
    
	private static void setLayoutForUI(JFrame f, JScrollPane jScrollPane1,
			JButton homeButton, JButton overdueButton, JButton doneButton,
			JButton allButton, JButton helpButton, JButton settingsButton,
			JScrollPane jScrollPane2, JTextField cmdEntry, JLabel commandText) {
		GroupLayout layout = new GroupLayout(f.getContentPane());
        f.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(overdueButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(doneButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(allButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(helpButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(settingsButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
                .addComponent(jScrollPane1))
            .addGroup(layout.createSequentialGroup()
            		.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
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
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup()
                		.addComponent(cmdEntry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );
	}
    
	private static void setIcon() {
				String icon = "/main/icon/d.png";
				ImageIcon img = new ImageIcon(UserInterface.class.getResource(icon));
				f.setIconImage(img.getImage());
	}
	
	private static void commandTextSettings(){
		ReadWriteXml prop = new ReadWriteXml();
		ArrayList<String> properties = prop.readToArrayList();
		commandText.setText("command: ");
		String fontFamily = properties.get(fontFamilyIndex);
		String fontSize = properties.get(fontSizeIndex);
		Font font;
		if (fontFamily == null || fontSize == null){
			font = commandText.getFont();
		} else {
			int fontStyle = commandText.getFont().getStyle();
			font = new Font(fontFamily, fontStyle, Integer.valueOf(fontSize));
		}
		commandText.setFont(font);
		
	}
	
	private static void cmdEntrySettings(){
		ReadWriteXml prop = new ReadWriteXml();
		ArrayList<String> properties = prop.readToArrayList();
		String fontFamily = properties.get(fontFamilyIndex);
		String fontSize = properties.get(fontSizeIndex);
		int fontStyle = cmdEntry.getFont().getStyle();
		Font font;
		if (fontFamily == null || fontSize == null){
			font = new Font(Font.MONOSPACED, Font.BOLD, 12);
		} else {
			font = new Font(fontFamily, fontStyle, Integer.valueOf(fontSize));
		}
		cmdEntry.setFont(font);
		setPlaceholderFontColor();
		placeholder();
	}
	
	/*private static String customisePlaceholderText(){
		return 
	}*/
	
	private static void placeholder(){
        cmdEntry.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (cmdEntry.getText().trim().length() != 0) {
                	setOriginalColor();
                }
 
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (cmdEntry.getText().trim().length() == 0) {
                	setPlaceholderFontColor();
                }
            }
        });
	}
	
	private static void setPlaceholderFontColor(){
		cmdEntry.setForeground(placeholderForeground);
		cmdEntry.setText("Enter commands here");
	}
	
	private static void setOriginalColor(){
		cmdEntry.setForeground(Color.BLACK);
        cmdEntry.setText("");
	}
    private static void textAreaSettings(){
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
        	cmdDisplay.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
    	} else {
    		cmdDisplay.setBackground(prop.rgbColor(bottomBg));
    		cmdDisplay.setForeground(prop.rgbColor(properties.get(bottomFontColorIndex)));
    		String fontFamily = cmdDisplay.getFont().getName();
    		int fontStyle = cmdDisplay.getFont().getStyle();
    		int fontSize = Integer.valueOf(properties.get(fontSizeIndex));
    		Font font = new Font(fontFamily, fontStyle, fontSize);
    		cmdDisplay.setFont(font);
    	}
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
    		String fontFamily = properties.get(fontFamilyIndex);
    		int fontStyle = outputDisplay.getFont().getStyle();
    		int fontSize = Integer.valueOf(properties.get(fontSizeIndex));
    		Font font = new Font(fontFamily, fontStyle, fontSize);
    		outputDisplay.setFont(font);
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
		String home = "/main/icon/home.png";
		ImageIcon homeImg = new ImageIcon(UserInterface.class.getResource(home));
		homeButton.setIcon(homeImg);
		String overdue = "/main/icon/overdue.png";
		ImageIcon overdueImg = new ImageIcon(UserInterface.class.getResource(overdue));
    	overdueButton.setIcon(overdueImg);
		String done = "/main/icon/done.png";
		ImageIcon doneImg = new ImageIcon(UserInterface.class.getResource(done));
    	doneButton.setIcon(doneImg);
		String all = "/main/icon/all.png";
		ImageIcon allImg = new ImageIcon(UserInterface.class.getResource(all));
    	allButton.setIcon(allImg);
		String help = "/main/icon/help.png";
		ImageIcon helpImg = new ImageIcon(UserInterface.class.getResource(help));
    	helpButton.setIcon(helpImg);
		String settings = "/main/icon/settings.png";
		ImageIcon settingsImg = new ImageIcon(UserInterface.class.getResource(settings));
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
	
	private static String displayToday(Parser p){
		return p.parse("display today").substring(1);
	}
	public static Timer setTodayTaskMessage(Parser p) {
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                outputDisplay.setText(displayToday(p));
                outputDisplay.repaint();
            }
        });
        timer.setRepeats(false);
        timer.setCoalesce(true);
        timer.start();
        return timer;
    }

}