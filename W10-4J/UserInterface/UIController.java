/*
 * This class is the controller for UserInterface class. Generally, it detects any action by the user and determines the jobs to be done.
 * Jobs to be done could be to send commands to the Parser for further action, or to direct to SettingsUI for user to change colors.
 * It can also be keyboard actions, i.e. keyboard shortcuts used by user. 
 * 
 * @@author A0113761M
 */
package UserInterface;

import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;

import Parser.Parser;
import main.Constants;

public class UIController {

	private static ArrayList<String> commands = new ArrayList<String>();
	private static int commandIndex = commands.size();
	private static int scroll = 0;
	private static int minCommandIndex = 0;

	public void commandAction(Timer timer, String command, JButton overdue, JButton all, JButton done, JButton help,
			JButton settings, JButton home, JTextField cmdEntry, JTextArea cmdDisplay, JTextPane displayOutput,
			JLabel commandText) {
		Parser p = new Parser();
		if (command != null) {
			commandEnteredAction(timer, command, cmdEntry, cmdDisplay, displayOutput, p);
		} else {
			cmdEntryListener(timer, p, cmdEntry, cmdDisplay, displayOutput);
			settingsListener(timer, settings, cmdDisplay, displayOutput, commandText, cmdEntry, home, overdue, all, done, help);
			allListener(timer, all, p, displayOutput);
			doneListener(timer, done, p, displayOutput);
			helpListener(timer, help, p, displayOutput);
			homeListener(timer, home, p, displayOutput);
			overdueListener(timer, overdue, p, displayOutput);
		}
	}

	private void commandEnteredAction(Timer timer, String command, JTextField cmdEntry, JTextArea cmdDisplay,
			JTextPane displayOutput, Parser p) {
		stopTimer(timer);
		cmdEntry.setText("");
		commands.add(command);
		commandIndex = commands.size();
		printInCommandDisplay(cmdDisplay, command);
		String output = p.parse(command);
		assert output != null;
		if (isDisplay(output)) {
			printInDisplayOutput(displayOutput, output.substring(1));
		} else {
			printInCommandDisplay(cmdDisplay, output.substring(1));
			printInDisplayOutput(displayOutput, p.parse("display").substring(1));
		}
		displayOutput.setCaretPosition(0);
	}
	
	private static void stopTimer(Timer timer){
		if (timer.isRunning()){
			timer.stop();
		}
	}

	private static boolean isInvalidMessages(String message) {
		return message.equals(Constants.MESSAGE_INVALID_DATE) || message.equals(Constants.MESSAGE_INVALID_FORMAT)
				|| message.equals(Constants.MESSAGE_INVALID_TIME)
				|| message.equals(Constants.MESSAGE_UNRECOGNISED_COMMAND)
				|| message.equals(Constants.MESSAGE_RECUR_FAIL);
	}

	private void settingsListener(Timer timer, JButton settings, JTextArea cmdDisplay, JTextPane displayOutput, JLabel commandText,
			JTextField cmdEntry, JButton home, JButton overdue, JButton all, JButton done, JButton help) {
		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				new SettingsUI(displayOutput, cmdDisplay, commandText, cmdEntry, home, overdue, all, done, help, settings);
			}
		});
	}

	private void allListener(Timer timer, JButton all, Parser p, JTextPane displayOutput) {
		all.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				String output = p.parse("display");
				printInDisplayOutput(displayOutput, output.substring(1));
				displayOutput.setCaretPosition(0);
			}
		});
	}

	private void doneListener(Timer timer, JButton done, Parser p, JTextPane displayOutput) {
		done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				String output = p.parse("display done");
				printInDisplayOutput(displayOutput, output.substring(1));
				displayOutput.setCaretPosition(0);
			}
		});
	}

	private void homeListener(Timer timer, JButton home, Parser p, JTextPane displayOutput) {
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				String todayTask = p.parse("display today").substring(1);
				printInDisplayOutput(displayOutput, todayTask);
				displayOutput.setCaretPosition(0);
			}
		});
	}

	private void helpListener(Timer timer, JButton help, Parser p, JTextPane displayOutput) {
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				String output = p.parse("help");
				printInDisplayOutput(displayOutput, output.substring(1));
				displayOutput.setCaretPosition(0);
			}
		});
	}

	private void overdueListener(Timer timer, JButton overdue, Parser p, JTextPane displayOutput) {
		overdue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				String output = p.parse("display overdue");
				printInDisplayOutput(displayOutput, output.substring(1));
				displayOutput.setCaretPosition(0);
			}
		});
	}

	private void cmdEntryListener(Timer timer, Parser p, JTextField cmdEntry, JTextArea cmdDisplay,
			JTextPane displayOutput) {
		cmdEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = cmdEntry.getText();
				commandEnteredAction(timer, s, cmdEntry, cmdDisplay, displayOutput, p);
			}
		});
	}

	public void keyboardActions(JTextPane outputDisplay, JTextField cmdEntry, JScrollPane outputScrollpane) {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				keyPressed(e, outputDisplay, cmdEntry, outputScrollpane);
				return false;
			}
		});
	}

	public static boolean isDisplay(String s) {
		return s.substring(0, 1).equals("0");
	}

	private static void printInCommandDisplay(JTextArea cmdDisplay, String content) {
		cmdDisplay.append("> " + content + "\n");
	}

	private static void printInDisplayOutput(JTextPane displayOutput, String s) {
		displayOutput.setText(s);
	}

	private static void keyPressed(KeyEvent e, JTextPane outputDisplay, JTextField cmdEntry,
			JScrollPane outputScrollpane) {
		if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown() && e.isShiftDown()
				&& (e.getKeyCode() == KeyEvent.VK_EQUALS)) {
			fontSizeChange(outputDisplay, "increase");
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown() && e.isShiftDown()
				&& (e.getKeyCode() == KeyEvent.VK_MINUS)) {
			fontSizeChange(outputDisplay, "decrease");
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_DOWN) {
			arrowDownPreviousInput(cmdEntry);
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_UP) {
			arrowUpPreviousInput(cmdEntry);
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			pageUpChange(outputScrollpane);
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			pageDownChange(outputScrollpane);
		}
	}

	/* These are the set of boolean methods for keyboard shortcuts */
	private static boolean arrowDownTrue() {
		return commandIndex >= minCommandIndex && (commandIndex + 1) < commands.size();
	}

	private static boolean arrowUpTrue() {
		return (commandIndex) > minCommandIndex;
	}

	public static boolean pageUpTrue(int minScroll) {
		return scroll >= minScroll;
	}

	public static boolean pageDownTrue(int maxScroll) {
		return scroll <= maxScroll;
	}

	/* These are the set of implementation methods for the keyboard shortcuts */
	private static void fontSizeChange(JTextPane outputDisplay, String change) {
		int fontSize = outputDisplay.getFont().getSize();
		String fontName = outputDisplay.getFont().getFontName();
		int fontStyle = outputDisplay.getFont().getStyle();
		Font font;
		if (change.equals("increase")) {
			font = new Font(fontName, fontStyle, fontSize + 1);
		} else {
			font = new Font(fontName, fontStyle, fontSize - 1);
		}
		outputDisplay.setFont(font);
	}

	private static void arrowDownPreviousInput(JTextField cmdEntry) {
		if (arrowDownTrue()) {
			commandIndex += 1;
			cmdEntry.setText(commands.get(commandIndex));
		} else {
			cmdEntry.setText("");
		}
	}

	private static void arrowUpPreviousInput(JTextField cmdEntry) {
		if (commands.size() == 0) {
			cmdEntry.setText("");
		} else if (arrowUpTrue()) {
			commandIndex -= 1;
			cmdEntry.setText(commands.get(commandIndex));
		} else {
			cmdEntry.setText(commands.get(commandIndex));
		}
	}

	private static void pageUpChange(JScrollPane outputScrollpane) {
		int minScroll = 0;
		if (pageUpTrue(minScroll)) {
			scroll -= 200;
		} else {
			scroll = minScroll;
		}
		outputScrollpane.getVerticalScrollBar().setValue(scroll);
	}

	private static void pageDownChange(JScrollPane outputScrollpane) {
		int maxScroll = outputScrollpane.getVerticalScrollBar().getMaximum();
		if (pageDownTrue(maxScroll)) {
			scroll += 200;
		} else {
			scroll = maxScroll;
		}
		outputScrollpane.getVerticalScrollBar().setValue(scroll);
	}
}
