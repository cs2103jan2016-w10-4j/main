package Parser;

import java.util.ArrayList;

import Handler.Handler;
import main.Constants;
import main.Constants.COMMAND_TYPE;

public class Parser {
	private ArrayList<String> addCommandList = new ArrayList<>();
	private ArrayList<String> deleteCommandList = new ArrayList<>();
	private ArrayList<String> editCommandList = new ArrayList<>();
	private ArrayList<String> doneCommandList = new ArrayList<>();
	private ArrayList<String> displayCommandList = new ArrayList<>();
	private ArrayList<String> searchCommandList = new ArrayList<>();
	private ArrayList<String> setdirCommandList = new ArrayList<>();
	private ArrayList<String> retrieveCommandList = new ArrayList<>();
	private ArrayList<String> recurrenceCommandList = new ArrayList<>();
	private ArrayList<String> undoCommandList = new ArrayList<>();
	private ArrayList<String> exitCommandList = new ArrayList<>();
	private ArrayList<String> helpCommandList = new ArrayList<>();

	private ArrayList<String> addArgumentList = new ArrayList<>();
	private ArrayList<String> editArgumentList = new ArrayList<>();
	private ArrayList<String> displayArgumentList = new ArrayList<>();
	private ArrayList<String> searchArgumentList = new ArrayList<>();
	private ArrayList<String> recurrenceArgumentList = new ArrayList<>();
	private ArrayList<String> helpArgumentList = new ArrayList<>();

	private ArrayList<String> dateArgumentList = new ArrayList<>();
	private ArrayList<String> startArgumentList = new ArrayList<>();
	private ArrayList<String> endArgumentList = new ArrayList<>();
	private ArrayList<String> detailsArgumentList = new ArrayList<>();
	private ArrayList<String> repeatArgumentList = new ArrayList<>();

	private Handler handler;
	private Valid valid;

	public Parser() {
		generateCommandList();
		handler = new Handler();
		valid = new Valid();
	}

	public String parse(String command) {
		String commandTypeString = getFirstWord(command);
		COMMAND_TYPE commandType = getAction(commandTypeString);
		if (commandType == COMMAND_TYPE.INVALID) {
			return Constants.MESSAGE_INVALID_FORMAT;
		}
		String[] arguments = getArguments(commandType, command);
		if (!valid.isValid(commandType, arguments)) {
			if (valid.getInvalidDate()) {
				return Constants.MESSAGE_INVALID_DATE;
			} else if (valid.getInvalidTime()) {
				return Constants.MESSAGE_INVALID_TIME;
			} else {
				return Constants.MESSAGE_INVALID_FORMAT;
			}
		}
		if (commandType == COMMAND_TYPE.DISPLAY || commandType == COMMAND_TYPE.SEARCH
				|| commandType == COMMAND_TYPE.HELP) {
			return "0" + handler.executeCommand(commandType, arguments);
		} else {
			return "1" + handler.executeCommand(commandType, arguments);
		}
	}

	public COMMAND_TYPE getAction(String command) {
		if (isCommandType(command, addCommandList)) {
			return COMMAND_TYPE.ADD;
		} else if (isCommandType(command, deleteCommandList)) {
			return COMMAND_TYPE.DELETE;
		} else if (isCommandType(command, editCommandList)) {
			return COMMAND_TYPE.EDIT;
		} else if (isCommandType(command, doneCommandList)) {
			return COMMAND_TYPE.DONE;
		} else if (isCommandType(command, displayCommandList)) {
			return COMMAND_TYPE.DISPLAY;
		} else if (isCommandType(command, searchCommandList)) {
			return COMMAND_TYPE.SEARCH;
		} else if (isCommandType(command, setdirCommandList)) {
			return COMMAND_TYPE.SETDIR;
		} else if (isCommandType(command, retrieveCommandList)) {
			return COMMAND_TYPE.RETRIEVE;
		} else if (isCommandType(command, recurrenceCommandList)) {
			return COMMAND_TYPE.RECURRENCE;
		} else if (isCommandType(command, undoCommandList)) {
			return COMMAND_TYPE.UNDO;
		} else if (isCommandType(command, exitCommandList)) {
			return COMMAND_TYPE.EXIT;
		} else if (isCommandType(command, helpCommandList)) {
			return COMMAND_TYPE.HELP;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}

	public boolean isCommandType(String command, ArrayList<String> commandList) {
		if (commandList.contains(command)) {
			return true;
		} else {
			return false;
		}
	}

	public String[] getArguments(COMMAND_TYPE commandType, String command) {
		if (commandType == COMMAND_TYPE.RETRIEVE || commandType == COMMAND_TYPE.SETDIR) {
			if (command.contains(" ")) {
				return new String[] { command.substring(command.indexOf(" ") + 1) };
			} else {
				return new String[] {};
			}
		}
		ArrayList<String> tokens = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		boolean insideQuote = false;
		char[] c = command.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (c[i] == '"') {
				insideQuote = !insideQuote;
			} else if (c[i] == ' ' && !insideQuote) {
				tokens.add(sb.toString());

				sb.delete(0, sb.length());
			} else {
				sb.append(c[i]);
			}
		}
		tokens.add(sb.toString());
		tokens.remove(0);
		if (commandType == COMMAND_TYPE.ADD) {
			replaceModifiers(tokens);
			return compactArguments(tokens, addArgumentList);
		} else if (commandType == COMMAND_TYPE.EDIT) {
			replaceModifiers(tokens);
			return compactArguments(tokens, editArgumentList);
		} else if (commandType == COMMAND_TYPE.DISPLAY) {
			return compactArguments(tokens, displayArgumentList);
		} else if (commandType == COMMAND_TYPE.SEARCH) {
			return compactArguments(tokens, searchArgumentList);
		} else {
			return tokens.toArray(new String[0]);
		}
	}

	public String[] compactArguments(ArrayList<String> token, ArrayList<String> argumentList) {
		ArrayList<String> arguments = new ArrayList<>();
		String temp = "";
		for (int i = 0; i < token.size(); i++) {
			if (argumentList.contains(token.get(i))) {
				if (temp.length() != 0) {
					arguments.add(temp);
					temp = "";
				}
				arguments.add(token.get(i));
			} else {
				if (temp.length() != 0) {
					temp += " ";
				}
				temp += token.get(i);
			}
		}
		if (temp.length() != 0) {
			arguments.add(temp);
		}
		return arguments.toArray(new String[0]);
	}

	public void replaceModifiers(ArrayList<String> token) {
		for (int i = 0; i < token.size(); i++) {
			if (dateArgumentList.contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_DATE);
			}
			if (startArgumentList.contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_START);
			}
			if (endArgumentList.contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_END);
			}
			if (detailsArgumentList.contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_DETAILS);
			}
			if (repeatArgumentList.contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_REPEAT);
			}
		}
	}

	public String getFirstWord(String command) {
		return command.split(" ")[0];
	}

	public void generateCommandList() {
		for (int i = 0; i < Constants.addDefaultCommandList.length; i++) {
			addCommandList.add(Constants.addDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.deleteDefaultCommandList.length; i++) {
			deleteCommandList.add(Constants.deleteDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.editDefaultCommandList.length; i++) {
			editCommandList.add(Constants.editDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.doneDefaultCommandList.length; i++) {
			doneCommandList.add(Constants.doneDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.displayDefaultCommandList.length; i++) {
			displayCommandList.add(Constants.displayDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.searchDefaultCommandList.length; i++) {
			searchCommandList.add(Constants.searchDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.setdirDefaultCommandList.length; i++) {
			setdirCommandList.add(Constants.setdirDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.retrieveDefaultCommandList.length; i++) {
			retrieveCommandList.add(Constants.retrieveDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.recurrenceDefaultCommandList.length; i++) {
			recurrenceCommandList.add(Constants.recurrenceDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.undoDefaultCommandList.length; i++) {
			undoCommandList.add(Constants.undoDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.exitDefaultCommandList.length; i++) {
			exitCommandList.add(Constants.exitDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.helpDefaultCommandList.length; i++) {
			helpCommandList.add(Constants.helpDefaultCommandList[i]);
		}

		for (int i = 0; i < Constants.addDefaultArgumentList.length; i++) {
			addArgumentList.add(Constants.addDefaultArgumentList[i]);
		}
		for (int i = 0; i < Constants.editDefaultArgumentList.length; i++) {
			editArgumentList.add(Constants.editDefaultArgumentList[i]);
		}
		for (int i = 0; i < Constants.displayDefaultArgumentList.length; i++) {
			displayArgumentList.add(Constants.displayDefaultArgumentList[i]);
		}
		for (int i = 0; i < Constants.searchDefaultArgumentList.length; i++) {
			searchArgumentList.add(Constants.searchDefaultArgumentList[i]);
		}
		for (int i = 0; i < Constants.recurrenceDefaultArgumentList.length; i++) {
			recurrenceArgumentList.add(Constants.recurrenceDefaultArgumentList[i]);
		}
		for (int i = 0; i < Constants.helpDefaultArgumentList.length; i++) {
			helpArgumentList.add(Constants.helpDefaultArgumentList[i]);
		}

		for (int i = 0; i < Constants.dateDefaultArgumentList.length; i++) {
			dateArgumentList.add(Constants.dateDefaultArgumentList[i]);
		}
		for (int i = 0; i < Constants.startDefaultArgumentList.length; i++) {
			startArgumentList.add(Constants.startDefaultArgumentList[i]);
		}
		for (int i = 0; i < Constants.endDefaultArgumentList.length; i++) {
			endArgumentList.add(Constants.endDefaultArgumentList[i]);
		}
		for (int i = 0; i < Constants.detailsDefaultArgumentList.length; i++) {
			detailsArgumentList.add(Constants.detailsDefaultArgumentList[i]);
		}
		for (int i = 0; i < Constants.repeatDefaultArgumentList.length; i++) {
			repeatArgumentList.add(Constants.repeatDefaultArgumentList[i]);
		}
	}
}