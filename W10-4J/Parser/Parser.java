//@@author A0140114A
package Parser;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Handler.Handler;
import main.Constants;
import main.Constants.COMMAND_TYPE;

public class Parser {
	private final Logger LOGGER = Logger.getLogger(Parser.class.getName());
	private Handler handler_;
	private Valid valid_;
	private CommandList commandList_;
	private NaturalLanguage naturalLanguage_;

	public Parser() {
		this.commandList_ = CommandList.getInstance();
		this.handler_ = new Handler();
		this.valid_ = new Valid(this.commandList_);
		this.naturalLanguage_ = new NaturalLanguage(this.commandList_);
	}

	public String parse(String command) {
		assert command != null : Constants.ASSERT_NULL_COMMAND;
		String commandTypeString = getFirstWord(command);
		COMMAND_TYPE commandType = getAction(commandTypeString);
		if (commandType == COMMAND_TYPE.INVALID) {
			LOGGER.log(Level.WARNING, "Invalid mesage: " + Constants.MESSAGE_UNRECOGNISED_COMMAND);
			return Constants.MESSAGE_UNRECOGNISED_COMMAND;
		}
		LOGGER.log(Level.INFO, "Command type: " + commandType.toString());
		String[] arguments = getArguments(commandType, command);
		if (arguments.length == 1) {
			arguments = naturalInterpretation(commandType, arguments);
		}
		if (!this.valid_.isValid(commandType, arguments)) {
			String invalidMessage = getInvalidReturnMessage();
			LOGGER.log(Level.WARNING, "Invalid mesage: " + invalidMessage);
			return invalidMessage;
		}
		if (commandType == COMMAND_TYPE.ALIAS) {
			setAlias(arguments);
			LOGGER.log(Level.INFO, Constants.MESSAGE_ALIAS_PASS);
			return Constants.MESSAGE_ALIAS_PASS;
		}
		return this.handler_.executeCommand(commandType, arguments);
	}

	public String getFirstWord(String command) {
		assert command != null : Constants.ASSERT_NULL_COMMAND;
		return command.trim().split(Constants.WHITESPACE)[0];
	}

	public COMMAND_TYPE getAction(String command) {
		if (isCommandType(command, this.commandList_.getAddCommandList())) {
			return COMMAND_TYPE.ADD;
		} else if (isCommandType(command, this.commandList_.getDeleteCommandList())) {
			return COMMAND_TYPE.DELETE;
		} else if (isCommandType(command, this.commandList_.getEditCommandList())) {
			return COMMAND_TYPE.EDIT;
		} else if (isCommandType(command, this.commandList_.getDoneCommandList())) {
			return COMMAND_TYPE.DONE;
		} else if (isCommandType(command, this.commandList_.getDisplayCommandList())) {
			return COMMAND_TYPE.DISPLAY;
		} else if (isCommandType(command, this.commandList_.getSearchCommandList())) {
			return COMMAND_TYPE.SEARCH;
		} else if (isCommandType(command, this.commandList_.getSetdirCommandList())) {
			return COMMAND_TYPE.SETDIR;
		} else if (isCommandType(command, this.commandList_.getRetrieveCommandList())) {
			return COMMAND_TYPE.RETRIEVE;
		} else if (isCommandType(command, this.commandList_.getRecurrenceCommandList())) {
			return COMMAND_TYPE.RECURRENCE;
		} else if (isCommandType(command, this.commandList_.getUndoCommandList())) {
			return COMMAND_TYPE.UNDO;
		} else if (isCommandType(command, this.commandList_.getExitCommandList())) {
			return COMMAND_TYPE.EXIT;
		} else if (isCommandType(command, this.commandList_.getHelpCommandList())) {
			return COMMAND_TYPE.HELP;
		} else if (isCommandType(command, this.commandList_.getAliasCommandList())) {
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
			replaceModifiers(tokens);
			return compactArguments(tokens, this.commandList_.getAddArgumentList());
		} else if (commandType == COMMAND_TYPE.EDIT) {
			replaceModifiers(tokens);
			return compactArguments(tokens, this.commandList_.getEditArgumentList());
		} else if (commandType == COMMAND_TYPE.DISPLAY) {
			return compactArguments(tokens, this.commandList_.getDisplayArgumentList());
		} else if (commandType == COMMAND_TYPE.SEARCH) {
			return compactArguments(tokens, this.commandList_.getSearchArgumentList());
		} else {
			return tokens.toArray(new String[0]);
		}
	}

	public String[] naturalInterpretation(COMMAND_TYPE commandType, String[] arguments) {
		if (commandType == COMMAND_TYPE.ADD) {
			arguments = this.naturalLanguage_.interpretAddArguments(arguments);
			String s = "";
			for(int i = 0 ;i < arguments.length;i++){
				s += arguments[i] + " ";
			}
			LOGGER.log(Level.INFO, "Natural Arguments:" + s);
		} else if (commandType == COMMAND_TYPE.EDIT) {
			arguments = this.naturalLanguage_.interpretEditArguments(arguments);
		}
		return arguments;
	}

	public String getInvalidReturnMessage() {
		if (this.valid_.getInvalidDate()) {
			return Constants.MESSAGE_INVALID_DATE;
		} else if (this.valid_.getInvalidTime()) {
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
			if (this.commandList_.getStartDateArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_STARTDATE);
			}
			if (this.commandList_.getEndDateArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_ENDDATE);
			}
			if (this.commandList_.getStartArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_START);
			}
			if (this.commandList_.getEndArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_END);
			}
			if (this.commandList_.getDetailsArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_DETAILS);
			}
			if (this.commandList_.getRepeatArgumentList().contains(token.get(i))) {
				token.set(i, Constants.MESSAGE_ADD_ACTION_REPEAT);
			}
		}
	}

	private void setAlias(String[] arguments) {
		COMMAND_TYPE commandType = getAction(arguments[0]);
		String alias = arguments[1];
		this.commandList_.setAlias(commandType, alias);
	}
}