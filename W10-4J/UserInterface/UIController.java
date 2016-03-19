package UserInterface;

import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Parser.Parser;

public class UIController {
	
	private static ArrayList<String> commands = new ArrayList<String>();
	private static int commandIndex = commands.size();
	private static int scroll = 100;
    
    public void commandAction(Parser p, JTextField cmdEntry, JTextArea cmdDisplay, JTextPane displayOutput){
    	cmdEntry.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = cmdEntry.getText();
				cmdEntry.setText("");
				commands.add(s);
		    	scroll = 0;
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
    }
	
    public void keyboardActions(JTextPane outputDisplay, JTextField cmdEntry, JScrollPane outputScrollpane){
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher(){
			public boolean dispatchKeyEvent(KeyEvent e){
				keyPressed(e, outputDisplay, cmdEntry, outputScrollpane);
				return false;
			}
		});
    }
    
    private static boolean isDisplay(String s){
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
			int fontSize = outputDisplay.getFont().getSize();
			String fontName = outputDisplay.getFont().getFontName();
			int fontStyle = outputDisplay.getFont().getStyle();
			Font font = new Font(fontName, fontStyle, fontSize + 1);
			outputDisplay.setFont(font);
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown() && e.isShiftDown() && (e.getKeyCode() == KeyEvent.VK_MINUS)){
			int fontSize = outputDisplay.getFont().getSize();
			String fontName = outputDisplay.getFont().getFontName();
			int fontStyle = outputDisplay.getFont().getStyle();
			Font font = new Font(fontName, fontStyle, fontSize - 1);
			outputDisplay.setFont(font);
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_DOWN){
			if (commandIndex >= 0 && (commandIndex + 1) < commands.size()){
				commandIndex += 1;
				cmdEntry.setText(commands.get(commandIndex));
			} else {
				cmdEntry.setText("");
			}
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_UP){
			if ((commandIndex - 1) >= 0){
				commandIndex -= 1;
				cmdEntry.setText(commands.get(commandIndex));
			} else {
				cmdEntry.setText(commands.get(commandIndex));
			}
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_PAGE_UP){
			int minScroll = outputScrollpane.getVerticalScrollBar().getMinimum();
			if (scroll <= minScroll){
				scroll = minScroll;
			} else {
				scroll -= 200;
			}
			outputScrollpane.getVerticalScrollBar().setValue(scroll);
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_PAGE_DOWN){
			int maxScroll = outputScrollpane.getVerticalScrollBar().getMaximum();
			if (scroll >= maxScroll){
				scroll = maxScroll;
			} else {
				scroll += 200;
			}
			outputScrollpane.getVerticalScrollBar().setValue(scroll);
		}
	}

}
