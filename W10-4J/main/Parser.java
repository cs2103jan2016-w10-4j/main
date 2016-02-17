package main;

import java.util.LinkedList;

import main.Constants.COMMAND_TYPE;

public class Parser {
	LinkedList<String> addCommandList = new LinkedList<>();
	LinkedList<String> deleteCommandList = new LinkedList<>();
	LinkedList<String> editCommandList = new LinkedList<>();
	LinkedList<String> doneCommandList = new LinkedList<>();
	LinkedList<String> displayCommandList = new LinkedList<>();
	LinkedList<String> searchCommandList = new LinkedList<>();
	LinkedList<String> retrieveCommandList = new LinkedList<>();
	LinkedList<String> undoCommandList = new LinkedList<>();
	LinkedList<String> exitCommandList = new LinkedList<>();

	LinkedList<String> addArgumentList = new LinkedList<>();
	LinkedList<String> editArgumentList = new LinkedList<>();
	LinkedList<String> displayArgumentList = new LinkedList<>();
	LinkedList<String> searchArgumentList = new LinkedList<>();

	Handler h;

	public Parser() {
		generateCommandList();
		h = new Handler();
	}

	public String parse(String command) {
		String commandTypeString = getFirstWord(command);
		String[] arguments = getArguments(command);
		COMMAND_TYPE commandType = getAction(commandTypeString, arguments);

		return h.executeCommand(commandType, getArguments(command));
	}

	public COMMAND_TYPE getAction(String command, String[] arguments) {
		if (isCommandType(command, addCommandList)) {
			if (isAddValid(arguments)) {
				return COMMAND_TYPE.ADD;
			} else {
				return COMMAND_TYPE.INVALID;
			}
		} else if (isCommandType(command, deleteCommandList)) {
			return COMMAND_TYPE.DELETE;
		} else if (isCommandType(command, editCommandList)) {
			if (isEditValid(arguments)) {
				return COMMAND_TYPE.EDIT;
			} else {
				return COMMAND_TYPE.INVALID;
			}
		} else if (isCommandType(command, doneCommandList)) {
			return COMMAND_TYPE.DONE;
		} else if (isCommandType(command, displayCommandList)) {
			if (isDisplayValid(arguments)) {
				return COMMAND_TYPE.DISPLAY;
			} else {
				return COMMAND_TYPE.INVALID;
			}
		} else if (isCommandType(command, searchCommandList)) {
			return COMMAND_TYPE.SEARCH;
		} else if (isCommandType(command, retrieveCommandList)) {
			return COMMAND_TYPE.RETRIEVE;
		} else if (isCommandType(command, undoCommandList)) {
			return COMMAND_TYPE.UNDO;
		} else if (isCommandType(command, exitCommandList)) {
			return COMMAND_TYPE.EXIT;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}

	public boolean isAddValid(String[] arguments) {
		if (arguments.length == 0) {
			return false;
		}
		for (int i = 1; i < arguments.length; i += 2) {
			if (!addArgumentList.contains(arguments[i])) {
				return false;
			} else {
				if (i + 1 == arguments.length) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isEditValid(String[] arguments) {
		if (arguments.length == 0) {
			return false;
		}
		if (!isInteger(arguments[0])) {
			return false;
		}
		for (int i = 1; i < arguments.length; i += 2) {
			if (!editArgumentList.contains(arguments[i])) {
				return false;
			} else {
				if (i + 1 == arguments.length) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isDisplayValid(String[] arguments) {
		if (arguments.length > 0 && !displayArgumentList.contains(arguments[0])) {
			return false;
		}
		return true;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public String[] getArguments(String command) {
		LinkedList<String> tokens = new LinkedList<String>();
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
		return tokens.toArray(new String[0]);
	}

	public String getFirstWord(String command) {
		return command.split(" ")[0];
	}

	public boolean isCommandType(String command, LinkedList<String> commandList) {
		if (commandList.contains(command)) {
			return true;
		} else {
			return false;
		}
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
		for (int i = 0; i < Constants.retrieveDefaultCommandList.length; i++) {
			retrieveCommandList.add(Constants.retrieveDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.undoDefaultCommandList.length; i++) {
			undoCommandList.add(Constants.undoDefaultCommandList[i]);
		}
		for (int i = 0; i < Constants.exitDefaultCommandList.length; i++) {
			exitCommandList.add(Constants.exitDefaultCommandList[i]);
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
	}
}