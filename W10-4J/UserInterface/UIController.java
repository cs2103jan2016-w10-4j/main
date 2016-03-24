package UserInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import Parser.Parser;

public class UIController {
	
	private static ArrayList<String> commands = new ArrayList<String>();
	private static int commandIndex = commands.size();
	private static int scroll = 0;
	private static int minCommandIndex = 0;
    
    public void commandAction(Parser p, JButton settings, JTextField cmdEntry, JTextArea cmdDisplay, JTextPane displayOutput){
    	cmdEntry.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = cmdEntry.getText();
				cmdEntry.setText("");
				commands.add(s);
				commandIndex = commands.size();
				printInCommandDisplay(cmdDisplay, "> " + s);
				String output = p.parse(s);
				assert output != null;
				if (isDisplay(output)){
					printInDisplayOutput(displayOutput, output.substring(1));
				} else {
					printInCommandDisplay(cmdDisplay, output.substring(1));
				}
				displayOutput.setCaretPosition(0);
			}
		});
    	settings.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			SettingsUI settings = new SettingsUI(displayOutput, cmdDisplay);
    		}
    	});
    }
    
    public void keyboardActions(JTextPane outputDisplay, JTextField cmdEntry, JScrollPane outputScrollpane){
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher(){
			public boolean dispatchKeyEvent(KeyEvent e){
				keyPressed(e, outputDisplay, cmdEntry, outputScrollpane);
				return false;
			}
		});
    }
    
    public static boolean isDisplay(String s){
    	return s.substring(0, 1).equals("0");
    }
    
    private static void printInCommandDisplay(JTextArea cmdDisplay, String content){
    	cmdDisplay.append(content + "\n");
    }
    
    private static void printInDisplayOutput(JTextPane displayOutput, String s) {
    	displayOutput.setText(s);
    }
    
	private static void keyPressed(KeyEvent e, JTextPane outputDisplay, JTextField cmdEntry, JScrollPane outputScrollpane){
		if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown() && e.isShiftDown() && (e.getKeyCode() == KeyEvent.VK_EQUALS)){
			fontSizeChange(outputDisplay, "increase");
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown() && e.isShiftDown() && (e.getKeyCode() == KeyEvent.VK_MINUS)){
			fontSizeChange(outputDisplay, "decrease");
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_DOWN){
			arrowDownPreviousInput(cmdEntry);
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_UP){
			arrowUpPreviousInput(cmdEntry);
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_PAGE_UP){
			pageUpChange(outputScrollpane);
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_PAGE_DOWN){
			pageDownChange(outputScrollpane);
		}
	}

	/* These are the set of boolean methods for keyboard shortcuts*/
	private static boolean arrowDownTrue() {
		return commandIndex >= minCommandIndex && (commandIndex + 1) < commands.size();
	}

	private static boolean arrowUpTrue() {
		return (commandIndex - 1) >= minCommandIndex;
	}

	public static boolean pageUpTrue(int minScroll) {
		return scroll >= minScroll;
	}

	public static boolean pageDownTrue(int maxScroll) {
		return scroll <= maxScroll;
	}

	/* These are the set of implementation methods for the keyboard shortcuts*/
	private static void fontSizeChange(JTextPane outputDisplay, String change) {
		int fontSize = outputDisplay.getFont().getSize();
		String fontName = outputDisplay.getFont().getFontName();
		int fontStyle = outputDisplay.getFont().getStyle();
		Font font;
		if (change.equals("increase")){
			font = new Font(fontName, fontStyle, fontSize + 1);
		} else {
			font = new Font(fontName, fontStyle, fontSize - 1);
		}
		outputDisplay.setFont(font);
	}
	
	private static void arrowDownPreviousInput(JTextField cmdEntry) {
		if (arrowDownTrue()){
			commandIndex += 1;
			cmdEntry.setText(commands.get(commandIndex));
		} else {
			cmdEntry.setText("");
		}
	}

	private static void arrowUpPreviousInput(JTextField cmdEntry) {
		if (arrowUpTrue()){
			commandIndex -= 1;
			cmdEntry.setText(commands.get(commandIndex));
		} else {
			cmdEntry.setText(commands.get(commandIndex));
		}
	}

	private static void pageUpChange(JScrollPane outputScrollpane) {
		int minScroll = 0;
		if (pageUpTrue(minScroll)){
			scroll -= 200;
		} else {
			scroll = minScroll;
		}
		outputScrollpane.getVerticalScrollBar().setValue(scroll);
	}

	private static void pageDownChange(JScrollPane outputScrollpane) {
		int maxScroll = outputScrollpane.getVerticalScrollBar().getMaximum();
		if (pageDownTrue(maxScroll)){
			scroll += 200;
		} else {
			scroll = maxScroll;
		}
		outputScrollpane.getVerticalScrollBar().setValue(scroll);
	}
}
