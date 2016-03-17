package Parser;

import java.util.ArrayList;

import Handler.Handler;
import main.Constants;
import main.Constants.COMMAND_TYPE;

public class Parser {
	ArrayList<String> addCommandList = new ArrayList<>();
	ArrayList<String> deleteCommandList = new ArrayList<>();
	ArrayList<String> editCommandList = new ArrayList<>();
	ArrayList<String> doneCommandList = new ArrayList<>();
	ArrayList<String> displayCommandList = new ArrayList<>();
	ArrayList<String> searchCommandList = new ArrayList<>();
	ArrayList<String> setdirCommandList = new ArrayList<>();
	ArrayList<String> retrieveCommandList = new ArrayList<>();
	ArrayList<String> undoCommandList = new ArrayList<>();
	ArrayList<String> exitCommandList = new ArrayList<>();
	ArrayList<String> helpCommandList = new ArrayList<>();

	ArrayList<String> addArgumentList = new ArrayList<>();
	ArrayList<String> editArgumentList = new ArrayList<>();
	ArrayList<String> displayArgumentList = new ArrayList<>();
	ArrayList<String> searchArgumentList = new ArrayList<>();
	ArrayList<String> helpArgumentList = new ArrayList<>();

	Handler handler;
	NaturalTime naturalTime;
	NaturalDate naturalDate;

	public Parser() {
		generateCommandList();
		handler = new Handler();
		naturalTime = new NaturalTime();
		naturalDate = new NaturalDate();
	}

	public String parse(String command) {
		String commandTypeString = getFirstWord(command);
		COMMAND_TYPE commandType = getAction(commandTypeString);
		if (commandType == COMMAND_TYPE.INVALID) {
			return Constants.MESSAGE_INVALID_FORMAT;
		}
		String[] arguments = getArguments(commandType, command);
		if (!isValid(commandType, arguments)) {
			return Constants.MESSAGE_INVALID_FORMAT;
		}
		if (commandType == COMMAND_TYPE.DISPLAY || commandType == COMMAND_TYPE.SEARCH || commandType == COMMAND_TYPE.HELP) {
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
			return compactArguments(tokens, addArgumentList);
		} else if (commandType == COMMAND_TYPE.EDIT) {
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

	public boolean isValid(COMMAND_TYPE commandType, String[] arguments) {
		switch (commandType) {
		case ADD:
			return isAddValid(arguments);
		case DELETE:
			return isDeleteValid(arguments);
		case EDIT:
			return isEditValid(arguments);
		case DONE:
			return isDoneValid(arguments);
		case DISPLAY:
			return isDisplayValid(arguments);
		case HELP:
			return isHelpValid(arguments);
		default:
			return true;
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
				} else if (arguments[i].equals("date")) {
					String date = naturalDate.getDate(arguments[i + 1]);
					if (date == null) {
						return false;
					} else {
						arguments[i + 1] = date;
					}
				} else if (arguments[i].equals("start") || arguments[i].equals("end")) {
					String time = naturalTime.getTime(arguments[i + 1]);
					if (time == null) {
						return false;
					} else {
						arguments[i + 1] = time;
					}
				}
			}
		}
		return true;
	}

	public boolean isDeleteValid(String[] arguments) {
		if (arguments.length == 0) {
			return false;
		}
		return isInteger(arguments[0]);
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
				} else if (arguments[i].equals("date")) {
					String date = naturalDate.getDate(arguments[i + 1]);
					if (date == null) {
						return false;
					} else {
						arguments[i + 1] = date;
					}
				} else if (arguments[i].equals("start") || arguments[i].equals("end")) {
					String time = naturalTime.getTime(arguments[i + 1]);
					if (time == null) {
						return false;
					} else {
						arguments[i + 1] = time;
					}
				}
			}
		}
		return true;
	}

	public boolean isDoneValid(String[] arguments) {
		if (arguments.length == 0) {
			return false;
		}
		return isInteger(arguments[0]);
	}

	public boolean isDisplayValid(String[] arguments) {
		if(arguments.length == 0){
			return true;
		} else if(arguments.length ==2 ){
			return arguments[0].equals("by") && displayArgumentList.contains(arguments[1]);
		}
		return false;
	}

	public boolean isHelpValid(String[] arguments) {
		if (arguments.length == 0) {
			return true;
		} else if (arguments.length == 1) {
			return helpArgumentList.contains(arguments[0]);
		} else {
			return false;
		}
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
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
		for (int i = 0; i < Constants.helpDefaultArgumentList.length; i++){
			helpArgumentList.add(Constants.helpDefaultArgumentList[i]);
		}
	}
}