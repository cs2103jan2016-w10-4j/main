//@@author A0140114A
package Parser;

import java.util.ArrayList;

import Handler.Handler;
import main.Constants;
import main.Constants.COMMAND_TYPE;

public class Parser {
	private Handler handler_;
	private Valid valid_;
	private CommandList commandList_;

	public Parser() {
		commandList_ = CommandList.getInstance();
		handler_ = new Handler();
		valid_ = new Valid(commandList_);
	}

	public String parse(String command) {
//		System.out.println("Command entered: " + command);
		assert command != null;
		String commandTypeString = getFirstWord(command);
		COMMAND_TYPE commandType = getAction(commandTypeString);
		if (commandType == COMMAND_TYPE.INVALID) {
			return "1" + Constants.MESSAGE_UNRECOGNISED_COMMAND;
		}
		String[] arguments = getArguments(commandType, command);
//		for (String s : arguments) {
//			System.out.print(s + ",");
//		}
//		System.out.println("*");
		if (!valid_.isValid(commandType, arguments)) {
			return getInvalidReturnMessage();
		}
		if (commandType == COMMAND_TYPE.ALIAS) {
			setAlias(arguments);
			return "1" + Constants.MESSAGE_ALIAS_PASS;
		}
//		for (String s : arguments) {
//			System.out.print(s +",");
//		}
//		System.out.println("*");
		if (commandType == COMMAND_TYPE.DISPLAY || commandType == COMMAND_TYPE.SEARCH
				|| commandType == COMMAND_TYPE.HELP) {
			return "0" + handler_.executeCommand(commandType, arguments);
		} else {
			return "1" + handler_.executeCommand(commandType, arguments);
		}
	}

	public String getFirstWord(String command) {
		return command.trim().split(" ")[0];
	}

	public COMMAND_TYPE getAction(String command) {
		if (isCommandType(command, commandList_.getAddCommandList())) {
			return COMMAND_TYPE.ADD;
		} else if (isCommandType(command, commandList_.getDeleteCommandList())) {
			return COMMAND_TYPE.DELETE;
		} else if (isCommandType(command, commandList_.getEditCommandList())) {
			return COMMAND_TYPE.EDIT;
		} else if (isCommandType(command, commandList_.getDoneCommandList())) {
			return COMMAND_TYPE.DONE;
		} else if (isCommandType(command, commandList_.getDisplayCommandList())) {
			return COMMAND_TYPE.DISPLAY;
		} else if (isCommandType(command, commandList_.getSearchCommandList())) {
			return COMMAND_TYPE.SEARCH;
		} else if (isCommandType(command, commandList_.getSetdirCommandList())) {
			return COMMAND_TYPE.SETDIR;
		} else if (isCommandType(command, commandList_.getRetrieveCommandList())) {
			return COMMAND_TYPE.RETRIEVE;
		} else if (isCommandType(command, commandList_.getRecurrenceCommandList())) {
			return COMMAND_TYPE.RECURRENCE;
		} else if (isCommandType(command, commandList_.getUndoCommandList())) {
			return COMMAND_TYPE.UNDO;
		} else if (isCommandType(command, commandList_.getExitCommandList())) {
			return COMMAND_TYPE.EXIT;
		} else if (isCommandType(command, commandList_.getHelpCommandList())) {
			return COMMAND_TYPE.HELP;
		} else if (isCommandType(command, commandList_.getAliasCommandList())) {
			return COMMAND_TYPE.ALIAS;
		} else {
			return COMMAND_TYPE.INVALID;
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
				if (sb.toString().trim().length() > 0) {
					tokens.add(sb.toString());
				}
				sb.delete(0, sb.length());
			} else {
				sb.append(c[i]);
			}
		}
		tokens.add(sb.toString().trim());
		tokens.remove(0);
		if (commandType == COMMAND_TYPE.ADD) {
			replaceModifiers(tokens);
			return compactArguments(tokens, commandList_.getAddArgumentList());
		} else if (commandType == COMMAND_TYPE.EDIT) {
			replaceModifiers(tokens);
			return compactArguments(tokens, commandList_.getEditArgumentList());
		} else if (commandType == COMMAND_TYPE.DISPLAY) {
			return compactArguments(tokens, commandList_.getDisplayArgumentList());
		} else if (commandType == COMMAND_TYPE.SEARCH) {
			return compactArguments(tokens, commandList_.getSearchArgumentList());
		} else {
			return tokens.toArray(new String[0]);
		}
	}

	public String getInvalidReturnMessage() {
		if (valid_.getInvalidDate()) {
			return "1" + Constants.MESSAGE_INVALID_DATE;
		} else if (valid_.getInvalidTime()) {
			return "1" + Constants.MESSAGE_INVALID_TIME;
		} else {
			return "1" + Constants.MESSAGE_INVALID_FORMAT;
		}
	}

	public boolean isCommandType(String command, ArrayList<String> commandList) {
		if (commandList.contains(command)) {
			return true;
		} else {
			return false;
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
			if (commandList_.getStartDateArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_STARTDATE);
			}
			if (commandList_.getEndDateArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_ENDDATE);
			}
			if (commandList_.getStartArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_START);
			}
			if (commandList_.getEndArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_END);
			}
			if (commandList_.getDetailsArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_DETAILS);
			}
			if (commandList_.getRepeatArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_REPEAT);
			}
		}
	}

	private void setAlias(String[] arguments) {
		COMMAND_TYPE commandType = getAction(arguments[0]);
		String alias = arguments[1];
		commandList_.setAlias(commandType, alias);
	}

	public int getNumberOfTaskTotal() {
		return handler_.getNumberOfTaskTotal();
	}

	public int getNumberOfTaskToday() {
		return handler_.getNumberOfTaskToday();
	}

	public int getNumberOfTaskOverdue() {
		return handler_.getNumberOfTaskOverdue();
	}

	public int getNumberOfTaskDone() {
		return handler_.getNumberOfTaskDone();
	}
}