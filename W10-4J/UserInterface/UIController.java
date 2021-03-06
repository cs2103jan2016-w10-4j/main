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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;

import Parser.Parser;
import main.Constants;

public class UIController {
	private static final int SCROLL_AMOUNT = 200;
	private static ArrayList<String> commands = new ArrayList<String>();
	private static int commandIndex = commands.size();
	private static int scroll = 0;
	private static int minCommandIndex = 0;
	
	private final static Logger LOGGER = Logger.getLogger(UIController.class.getName());

	/*
	 * This method implements the listeners which listens to the actions of the
	 * user on the UI
	 */
	public void commandAction(Timer timer, String command, JButton overdue, JButton all, JButton done, JButton help,
			JButton settings, JButton home, JTextField cmdEntry, JTextArea cmdDisplay, JTextPane displayOutput,
			JLabel commandText) {
		Parser p = new Parser();
		if (command != null) {
			commandEnteredAction(timer, command, cmdEntry, cmdDisplay, displayOutput, p);
		} else {
			cmdEntryListener(timer, p, cmdEntry, cmdDisplay, displayOutput);
			settingsListener(timer, settings, cmdDisplay, displayOutput, commandText, cmdEntry, home, overdue, all,
					done, help);
			allListener(timer, all, p, displayOutput);
			doneListener(timer, done, p, displayOutput);
			helpListener(timer, help, p, displayOutput);
			homeListener(timer, home, p, displayOutput);
			overdueListener(timer, overdue, p, displayOutput);
		}
	}

	private void settingsListener(final Timer timer, final JButton settings, final JTextArea cmdDisplay, final JTextPane displayOutput,
			final JLabel commandText, final JTextField cmdEntry, final JButton home, final JButton overdue, final JButton all, final JButton done,
			final JButton help) {
		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				SettingsUI settingsUI = new SettingsUI(displayOutput, cmdDisplay, commandText, cmdEntry, home, overdue, all, done, help,
						settings);
			}
		});
	}

	private void allListener(final Timer timer, JButton all, final Parser p, final JTextPane displayOutput) {
		all.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				String output = p.parse(Constants.DISPLAY_COMMAND);
				assert output != null || output == Constants.EMPTY_STRING;
				printInDisplayOutput(displayOutput, output.substring(1));
				displayOutput.setCaretPosition(0);
			}
		});
	}

	private void doneListener(final Timer timer, JButton done, final Parser p, final JTextPane displayOutput) {
		done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				String output = p.parse(Constants.DISPLAY_DONE_COMMAND);
				assert output != null || output == Constants.EMPTY_STRING;
				printInDisplayOutput(displayOutput, output.substring(1));
				displayOutput.setCaretPosition(0);
			}
		});
	}

	private void homeListener(final Timer timer, JButton home, final Parser p, final JTextPane displayOutput) {
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				String todayTask = p.parse(Constants.DISPLAY_TODAY_COMMAND).substring(1);
				printInDisplayOutput(displayOutput, todayTask);
				displayOutput.setCaretPosition(0);
			}
		});
	}

	private void helpListener(final Timer timer, JButton help, final Parser p, final JTextPane displayOutput) {
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				String output = p.parse(Constants.HELP_COMMAND);
				assert output != null || output == Constants.EMPTY_STRING;
				printInDisplayOutput(displayOutput, output.substring(1));
				displayOutput.setCaretPosition(0);
			}
		});
	}

	private void overdueListener(final Timer timer, JButton overdue, final Parser p, final JTextPane displayOutput) {
		overdue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(timer);
				String output = p.parse(Constants.DISPLAY_OVERDUE_COMMAND);
				assert output != null || output == Constants.EMPTY_STRING;
				printInDisplayOutput(displayOutput, output.substring(1));
				displayOutput.setCaretPosition(0);
			}
		});
	}

	private void cmdEntryListener(final Timer timer, final Parser p, final JTextField cmdEntry, final JTextArea cmdDisplay,
			final JTextPane displayOutput) {
		cmdEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = cmdEntry.getText();
				commandEnteredAction(timer, s, cmdEntry, cmdDisplay, displayOutput, p);
			}
		});
	}

	private void commandEnteredAction(Timer timer, String command, JTextField cmdEntry, JTextArea cmdDisplay,
			JTextPane displayOutput, Parser p) {
		stopTimer(timer);
		cmdEntry.setText(Constants.EMPTY_STRING);
		commands.add(command);
		commandIndex = commands.size();
		printInCommandDisplay(cmdDisplay, command);
		LOGGER.log(Level.INFO, "Command entered: " + command);
		String output = p.parse(command);
		assert output != null || output == Constants.EMPTY_STRING;
		determiningDisplayToWhichDisplayOutput(cmdDisplay, displayOutput, p, output);
		setCaretPositionForHighlightedTasks(displayOutput);
	}

	private void setCaretPositionForHighlightedTasks(JTextPane displayOutput) {
		int indexOfHighlight = displayOutput.getText().indexOf(Constants.HIGHLIGHT_HEX);
		int caretPositionToBeSet = 0;
		int indexOfHighlightedPoint;
		if (indexOfHighlight > 0){
			indexOfHighlightedPoint = displayOutput.getText().indexOf(Constants.FONT_COLOR_STRING, indexOfHighlight) + 21;
			int endOfIndexOfHighlightedPoint = displayOutput.getText().indexOf(Constants.ANGLE_BRACKET_LESS, indexOfHighlightedPoint);
			if (endOfIndexOfHighlightedPoint > 0){
				String highlightedPoint = displayOutput.getText().substring(indexOfHighlightedPoint, endOfIndexOfHighlightedPoint);
				int lengthOfDisplayOutputDocument = displayOutput.getDocument().getLength();
				try {
					caretPositionToBeSet = displayOutput.getDocument().getText(0, lengthOfDisplayOutputDocument).indexOf(highlightedPoint);
				} catch (BadLocationException e) {
					caretPositionToBeSet = 0;
					e.printStackTrace();
				}
			}
		}
		LOGGER.log(Level.INFO, "Caret position: " + caretPositionToBeSet);
		displayOutput.setCaretPosition(caretPositionToBeSet);
	}

	/*
	 * This stops the timer which changes the display on startup to today's
	 * tasks
	 */
	private static void stopTimer(Timer timer) {
		if (timer.isRunning()) {
			timer.stop();
			LOGGER.log(Level.INFO, "Timer stopped");
		}
	}

	/*
	 * These are the set of display methods. They display to either the top
	 * display or the bottom display.
	 */
	private void determiningDisplayToWhichDisplayOutput(JTextArea cmdDisplay, JTextPane displayOutput, Parser p,
			String output) {
		if (isDisplay(output)) {
			printInDisplayOutput(displayOutput, output.substring(1));
			LOGGER.log(Level.INFO, "Set text to display output");
		} else if (isCmdDisplay(output)) {
			printInCommandDisplay(cmdDisplay, output.substring(1));
			printInDisplayOutput(displayOutput, p.parse(Constants.DISPLAY_COMMAND).substring(1));
			LOGGER.log(Level.INFO, "Set text to display output and command display");
		} else if (isInvalidMessages(output)) {
			printInCommandDisplay(cmdDisplay, output.substring(1));
			LOGGER.log(Level.INFO, "Set text to command display");
		} else {
			printInCommandDisplay(cmdDisplay, output.substring(1));
			LOGGER.log(Level.WARNING, "Unable to determine where to set text. Include flag");
		}
	}

	public static boolean isDisplay(String s) {
		return s.substring(0, 1).equals(Constants.IS_DISPLAY_FLAG);
	}

	public static boolean isCmdDisplay(String s) {
		return s.substring(0, 1).equals(Constants.CMD_DISPLAY_FLAG);
	}

	public static boolean isInvalidMessages(String s) {
		return s.substring(0, 1).equals(Constants.INVALID_MESSAGE_FLAG);
	}

	private static void printInCommandDisplay(JTextArea cmdDisplay, String content) {
		cmdDisplay.append("> " + content + "\n");
	}

	private static void printInDisplayOutput(JTextPane displayOutput, String s) {
		displayOutput.setText(s);
	}

	/* This listens to the keyboard shortcuts that the user used in Docket */
	public void keyboardActions(final JTextPane outputDisplay, final JTextField cmdEntry, final JScrollPane outputScrollpane) {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				keyPressed(e, outputDisplay, cmdEntry, outputScrollpane);
				return false;
			}
		});
	}

	private static void keyPressed(KeyEvent e, JTextPane outputDisplay, JTextField cmdEntry,
			JScrollPane outputScrollpane) {
		if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown() && e.isShiftDown()
				&& (e.getKeyCode() == KeyEvent.VK_EQUALS)) {
			fontSizeChange(outputDisplay, Constants.FONT_SIZE_INCREASE_FLAG);
		} else if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown() && e.isShiftDown()
				&& (e.getKeyCode() == KeyEvent.VK_MINUS)) {
			fontSizeChange(outputDisplay, Constants.FONT_SIZE_DECREASE_FLAG);
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
		if (change.equals(Constants.FONT_SIZE_INCREASE_FLAG)) {
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
			cmdEntry.setText(Constants.EMPTY_STRING);
		}
	}

	private static void arrowUpPreviousInput(JTextField cmdEntry) {
		if (commands.size() == 0) {
			cmdEntry.setText(Constants.EMPTY_STRING);
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
			scroll -= SCROLL_AMOUNT;
		} else {
			scroll = minScroll;
		}
		outputScrollpane.getVerticalScrollBar().setValue(scroll);
	}

	private static void pageDownChange(JScrollPane outputScrollpane) {
		int maxScroll = outputScrollpane.getVerticalScrollBar().getMaximum();
		if (pageDownTrue(maxScroll)) {
			scroll += SCROLL_AMOUNT;
		} else {
			scroll = maxScroll;
		}
		outputScrollpane.getVerticalScrollBar().setValue(scroll);
	}
}
