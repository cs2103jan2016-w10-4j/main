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
		assert command != null : Constants.ASSERT_NULL_COMMAND;
		String commandTypeString = getFirstWord(command);
		COMMAND_TYPE commandType = getAction(commandTypeString);
		if (commandType == COMMAND_TYPE.INVALID) {
			return Constants.MESSAGE_UNRECOGNISED_COMMAND;
		}
		String[] arguments = getArguments(commandType, command);
		if (!valid_.isValid(commandType, arguments)) {
			return getInvalidReturnMessage();
		}
		if (commandType == COMMAND_TYPE.ALIAS) {
			setAlias(arguments);
			return Constants.MESSAGE_ALIAS_PASS;
		}
		if (commandType == COMMAND_TYPE.DISPLAY || commandType == COMMAND_TYPE.SEARCH
				|| commandType == COMMAND_TYPE.HELP) {
			return handler_.executeCommand(commandType, arguments);
		} else {
			return handler_.executeCommand(commandType, arguments);
		}
	}

	public String getFirstWord(String command) {
		return command.trim().split(Constants.WHITESPACE)[0];
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
			if (command.contains(Constants.WHITESPACE)) {
				return new String[] { command.substring(command.indexOf(Constants.WHITESPACE) + 1) };
			} else {
				return new String[] {};
			}
		}
		ArrayList<String> tokens = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		boolean insideQuote = false;
		char[] c = command.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (c[i] == Constants.QUOTE_CHAR) {
				insideQuote = !insideQuote;
			} else if (c[i] == Constants.EMPTY_CHAR && !insideQuote) {
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
			// replaceModifiers(tokens);
			// return compactArguments(tokens,
			// commandList_.getAddArgumentList());
			return parserSpecial(tokens);
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
			return Constants.MESSAGE_INVALID_DATE;
		} else if (valid_.getInvalidTime()) {
			return Constants.MESSAGE_INVALID_TIME;
		} else {
			return Constants.MESSAGE_INVALID_FORMAT;
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
		String temp = Constants.EMPTY_STRING;
		for (int i = 0; i < token.size(); i++) {
			if (argumentList.contains(token.get(i))) {
				if (temp.length() != 0) {
					arguments.add(temp);
					temp = Constants.EMPTY_STRING;
				}
				arguments.add(token.get(i));
			} else {
				if (temp.length() != 0) {
					temp += Constants.WHITESPACE;
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

	public String[] parserSpecial(ArrayList<String> token) {
		NaturalDate ndate = new NaturalDate();
		NaturalTime ntime = new NaturalTime();
		String name = null, startdate = null, starttime = null, enddate = null, endtime = null, details = null;
		String recurtype = null;
		int recurCount = -1;

		// Isolate Recurrence
		for (int i = 0; i < token.size(); i++) {
			String s = token.get(i);
			if (commandList_.getRecurrenceArgumentList().contains(s)) {
				try {
					recurCount = Integer.parseInt(token.get(i + 1));
					if (recurCount <= 1) {
						throw new NumberFormatException();
					} else {
						recurtype = s;
						token.set(i, null);
						token.set(i + 1, null);
					}
				} catch (NumberFormatException e) {
					recurCount = -1;
					recurtype = null;
				} catch (IndexOutOfBoundsException e) {
					recurCount = -1;
					recurtype = null;
				}
			}
		}

		// Isolate Date
		for (int i = 1; i <= token.size(); i++) {
			ArrayList<String> tempToken = new ArrayList<>();
			for (int j = 0; j < token.size(); j++) {
				String tempString = "";
				for (int k = 0; k < i; k++) {
					try {
						if (tempString.equals("")) {
							tempString += token.get(j + k);
						} else {
							tempString += " " + token.get(j + k);
						}
					} catch (Exception e) {
						break;
					}
				}
				tempToken.add(tempString);
			}
			for (String s : tempToken) {
				String date = ndate.getDate(s);
				if (date != null) {
					if (startdate == null) {
						startdate = date;
						for (String str : s.split(" ")) {
							token.set(token.indexOf(str), null);
						}
					} else if (enddate == null) {
						enddate = date;
						for (String str : s.split(" ")) {
							token.set(token.indexOf(str), null);
						}
					}
				}
			}
		}

		// Isolate Time
		for (int i = 1; i <= token.size(); i++) {
			ArrayList<String> tempToken = new ArrayList<>();
			for (int j = 0; j < token.size(); j++) {
				String tempString = "";
				for (int k = 0; k < i; k++) {
					try {
						String toAdd = token.get(j + k);
						if (toAdd == null) {
							break;
						}
						tempString += toAdd;
					} catch (Exception e) {
						break;
					}
				}
				if (tempString != "") {
					tempToken.add(tempString);
				}
			}
			for (String s : tempToken) {
				String time = ntime.getTime(s);
				if (time != null) {
					if (starttime == null) {
						starttime = time;
						for (String str : s.split(" ")) {
							token.set(token.indexOf(str), null);
						}
					} else if (endtime == null) {
						endtime = time;
						for (String str : s.split(" ")) {
							token.set(token.indexOf(str), null);
						}
					}
				}
			}
		}

		// Isolate Name and Details
		ArrayList<String> tempToken = new ArrayList<>();
		String tempString = "";
		for (int i = 0; i < token.size(); i++) {
			String s = token.get(i);
			if (s == null) {
				if (!tempString.equals("")) {
					tempToken.add(tempString);
					tempString = "";
				}
			} else {
				if (tempString.equals("")) {
					tempString += s;
				} else {
					tempString += " " + s;
				}
			}
		}
		if (!tempString.equals("")) {
			tempToken.add(tempString);
		}
		switch (tempToken.size()) {
		case 2:
			details = tempToken.get(1);
		case 1:
			name = tempToken.get(0);
			break;
		}

		// Build output
		ArrayList<String> output = new ArrayList<>();
		if (!name.equals("")) {
			output.add(name);
		} else {
			return null;
		}
		if (startdate != null) {
			output.add("startdate");
			output.add(startdate);
		}
		if (enddate != null) {
			output.add("enddate");
			output.add(enddate);
		}
		if (starttime != null) {
			output.add("starttime");
			output.add(starttime);
		}
		if (endtime != null) {
			output.add("endtime");
			output.add(endtime);
		}
		if (details != null) {
			output.add("details");
			output.add(details);
		}
		if (recurCount > 1 && recurtype != null) {
			output.add("repeat");
			output.add(recurtype + " " + Integer.toString(recurCount));
		}
		String[] out = new String[output.size()];
		for (int i = 0; i < output.size(); i++) {
			out[i] = output.get(i);
		}
		System.out.println(output);
		return out;
	}
}