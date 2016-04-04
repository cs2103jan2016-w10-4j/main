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

import javax.swing.BorderFactory;
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
	private static int topBgIndex = 2;
	private static int bottomBgIndex = 3;
	private static int bottomFontColorIndex = 4;
	private static int fontSizeIndex = 5;
	private static int fontFamilyIndex = 6;
	private static int buttonColorIndex = 7;
	
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
	
	private static String focusString = "";
	
	private static ColorsForSettings colors = new ColorsForSettings();
	
	private static ReadWriteXml prop = new ReadWriteXml();
	private static ArrayList<String> properties = prop.readToArrayList();
	private static String fontFamily = properties.get(fontFamilyIndex);
	private static String fontSize = properties.get(fontSizeIndex);
	private static String bottomBg = properties.get(bottomBgIndex);
	private static String topBg = properties.get(topBgIndex);
	private static String buttonColors = properties.get(buttonColorIndex);
	private static Font font;
	
	public static void main(String[] args){
		initComponents(null);
	}
	
	/* initComponents method is set to return a string array for JUnit testing purposes.*/
    public static String[] initComponents(String command){
    	Parser p = new Parser();
		UIController uiControl = new UIController();

		setIcon();
		commandTextSettings();
		cmdEntrySettings();
		textAreaSettings();
		textPaneSettings(outputDisplay);
		buttonSettings();
		setWelcomeMessage(outputDisplay);
		Timer timer = setTodayTaskMessage(p);

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(outputDisplay);
        jScrollPane2.setViewportView(cmdDisplay);

        setLayoutForUI();
		
    	uiControl.keyboardActions(outputDisplay, cmdEntry, jScrollPane1);

        uiControl.commandAction(timer, command, overdueButton, allButton, doneButton, helpButton, settingsButton, homeButton, cmdEntry, cmdDisplay, outputDisplay, commandText);
        
		lookAndFeel();
		f.pack();
		f.setVisible(true);
        return returnOutput();
    }
    
	private static void setLayoutForUI() {
		int maxWidth = (int)overdueButton.getMaximumSize().getWidth();
		GroupLayout layout = new GroupLayout(f.getContentPane());
        f.getContentPane().setLayout(layout);
        setHoriGroup(maxWidth, layout);
        setVertiGroup(layout);
	}

	private static void setVertiGroup(GroupLayout layout) {
		layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(doneButton, GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(overdueButton, GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(allButton, GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(helpButton, GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(settingsButton, GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup()
                		.addComponent(commandText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                		.addComponent(cmdEntry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );
	}

	private static void setHoriGroup(int maxWidth, GroupLayout layout) {
		layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(homeButton, GroupLayout.DEFAULT_SIZE, 50, maxWidth)
                        .addComponent(overdueButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, maxWidth)
                        .addComponent(doneButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, maxWidth)
                        .addComponent(allButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, maxWidth)
                        .addComponent(helpButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 50, maxWidth)
                        .addComponent(settingsButton, GroupLayout.DEFAULT_SIZE, 50, maxWidth)))
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 600, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
            		.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
            		.addComponent(commandText)
            		.addComponent(cmdEntry))
        );
	}
	
	private static void lookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
    
	private static void setIcon() {
		String icon = "/main/icon/d.png";
		ImageIcon img = new ImageIcon(UserInterface.class.getResource(icon));
		f.setIconImage(img.getImage());
	}
	
	private static void commandTextSettings(){
		commandText.setText(" Command: ");
		if (fontFamily == null || fontSize == null){
			font = commandText.getFont();
		} else {
			int fontStyle = commandText.getFont().getStyle();
			font = new Font(fontFamily, fontStyle, Integer.valueOf(fontSize));
		}
		commandText.setOpaque(true);
        commandText.setBackground(Color.WHITE);
		commandText.setFont(font);
        commandText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.WHITE));
		
	}
	
	private static void cmdEntrySettings(){
		int fontStyle = cmdEntry.getFont().getStyle();
		if (fontFamily == null || fontSize == null){
			font = new Font(Font.MONOSPACED, Font.BOLD, 12);
		} else {
			font = new Font(fontFamily, fontStyle, Integer.valueOf(fontSize));
		}
		cmdEntry.setFont(font);
        cmdEntry.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.WHITE));
		setPlaceholderFontColor();
		placeholder();
	}
	
    private static void textAreaSettings(){
    	cmdDisplay.setColumns(20);
    	cmdDisplay.setLineWrap(true);
    	cmdDisplay.setWrapStyleWord(true);
    	cmdDisplay.setEditable(false);
    	cmdDisplay.setFocusable(false);
		// If the background color obtained from the xml file is null, then default colors are used.
    	if (bottomBg == null){
    		cmdDisplay.setBackground(Color.BLACK);
    		cmdDisplay.setForeground(Color.WHITE);
        	cmdDisplay.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
    	} else {
    		cmdDisplay.setBackground(colors.rgbColor(bottomBg));
    		cmdDisplay.setForeground(colors.rgbColor(properties.get(bottomFontColorIndex)));
    		String fontFamily = cmdDisplay.getFont().getName();
    		int fontStyle = cmdDisplay.getFont().getStyle();
    		int fontSize = Integer.valueOf(properties.get(fontSizeIndex));
    		font = new Font(fontFamily, fontStyle, fontSize);
    		cmdDisplay.setFont(font);
    	}
    }
	
	private static void placeholder(){
        cmdEntry.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (cmdEntry.getText().trim().length() != 0) {
                	setOriginalColor();
                }
                cmdEntry.setText(focusString);
 
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (cmdEntry.getText().trim().length() == 0) {
                	setPlaceholderFontColor();
                	focusString = "";
                } else {
                	focusString = cmdEntry.getText();
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
    
    private static void textPaneSettings(JTextPane outputDisplay){
    	outputDisplay.setEditable(false);
    	outputDisplay.setFocusable(false);
		// If the background color obtained from the xml file is null, then default colors are used.
    	if (topBg == null){
    		outputDisplay.setBackground(Color.WHITE);
    		outputDisplay.setForeground(Color.BLACK);
    	} else {
    		outputDisplay.setBackground(colors.rgbColor(topBg));
    		outputDisplay.setForeground(colors.rgbColor(properties.get(topFontColorIndex)));
    		int fontStyle = outputDisplay.getFont().getStyle();
    		int fontSize = Integer.valueOf(properties.get(fontSizeIndex));
    		font = new Font(fontFamily, fontStyle, fontSize);
    		outputDisplay.setFont(font);
    	}
    	outputDisplay.setContentType("text/html");
		outputDisplay.putClientProperty(JTextPane.HONOR_DISPLAY_PROPERTIES, true);
    }
    
    private static void buttonSettings(){
    	setToolTipForButtons();
		setIconsForButtons();
		//homeButton.setBorderPainted(false);
		//homeButton.setBorder(BorderFactory.createCompoundBorder());
		//overdueButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 10), BorderFactory.createLineBorder(Color.BLACK)));
		homeButton.setFocusable(false);
		overdueButton.setFocusable(false);
		doneButton.setFocusable(false);
		allButton.setFocusable(false);
		helpButton.setFocusable(false);
		settingsButton.setFocusable(false);
    	if (buttonColors == null){
    		homeButton.setBackground(Color.gray);
    		overdueButton.setBackground(Color.gray);
    		doneButton.setBackground(Color.gray);
    		allButton.setBackground(Color.gray);
    		helpButton.setBackground(Color.gray);
    		settingsButton.setBackground(Color.gray);
    	} else {
    		homeButton.setBackground(colors.rgbColor(buttonColors));
    		overdueButton.setBackground(colors.rgbColor(buttonColors));
    		doneButton.setBackground(colors.rgbColor(buttonColors));
    		allButton.setBackground(colors.rgbColor(buttonColors));
    		helpButton.setBackground(colors.rgbColor(buttonColors));
    		settingsButton.setBackground(colors.rgbColor(buttonColors));
    	}
    }

	private static void setIconsForButtons() {
		getAndSetIconsForButtons("/main/icon/home.png", homeButton);
		getAndSetIconsForButtons("/main/icon/overdue.png", overdueButton);
		getAndSetIconsForButtons("/main/icon/done.png", doneButton);
		getAndSetIconsForButtons("/main/icon/all.png", allButton);
		getAndSetIconsForButtons("/main/icon/help.png", helpButton);
		getAndSetIconsForButtons("/main/icon/settings.png", settingsButton);
	}
	
	private static void getAndSetIconsForButtons(String path, JButton button){
		ImageIcon homeImg = new ImageIcon(UserInterface.class.getResource(path));
		button.setIcon(homeImg);
	}

	private static void setToolTipForButtons() {
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
    
    private static String[] returnOutput(){
    	String[] output = new String[2];
    	output[0] = outputDisplay.getText();
    	output[1] = cmdDisplay.getText();
    	return output;
    }
}