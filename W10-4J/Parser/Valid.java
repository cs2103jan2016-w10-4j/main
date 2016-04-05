package Parser;

import java.util.ArrayList;

import main.Constants;
import main.Constants.COMMAND_TYPE;
import main.Date;

public class Valid {
	private NaturalTime naturalTime_;
	private NaturalDate naturalDate_;
	private CommandList commandList_;
	private boolean invalidDate, invalidTime;

	public Valid(CommandList commandList) {
		naturalTime_ = new NaturalTime();
		naturalDate_ = new NaturalDate();
		commandList_ = commandList;
	}

	public boolean isValid(COMMAND_TYPE commandType, String[] arguments) {
		invalidDate = false;
		invalidTime = false;
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
		case SETDIR:
			return isSetdirValid(arguments);
		case RETRIEVE:
			return isRetrieveValid(arguments);
		case RECURRENCE:
			return isRecurrenceValid(arguments);
		case HELP:
			return isHelpValid(arguments);
		case ALIAS:
			return isAliasValid(arguments);
		default:
			return true;
		}
	}

	public boolean isAddValid(String[] arguments) {
		if (arguments.length == 0) {
			return false;
		}
		try {
			for (int i = 1; i < arguments.length; i += 2) {
				System.out.println(arguments[i]);
				if (!commandList_.getAddArgumentList().contains(arguments[i])) {
					return false;
				} else {
					if (i + 1 == arguments.length) {
						return false;
					} else if (arguments[i].equals("startdate") || arguments[i].equals("enddate")) {
						String date = naturalDate_.getDate(arguments[i + 1]);
						if (date == null) {
							invalidDate = true;
							return false;
						} else {
							assert Date.isLegalDate(date) : Constants.ASSERT_VALID_DATE;
							arguments[i + 1] = date;
						}
					} else if (arguments[i].equals("starttime") || arguments[i].equals("endtime")) {
						String time = naturalTime_.getTime(arguments[i + 1]);
						if (time == null) {
							invalidTime = true;
							return false;
						} else {
							arguments[i + 1] = time;
						}
					} else if (arguments[i].equals("repeat")) {
						String[] repeatArgument = arguments[i + 1].split(" ");
						try {
							if (!commandList_.getRecurrenceArgumentList().contains(repeatArgument[0])
									|| !isInteger(repeatArgument[1])) {
								return false;
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							return false;
						}
					}
				}
			}
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	public boolean isDeleteValid(String[] arguments) {
		// if (arguments.length == 0) {
		// return false;
		// }
		// for(int i=0;i<arguments.length;i++){
		// if(!isInteger(arguments[i])){
		// return false;
		// }
		// }
		// return true;
		if (arguments.length != 1) {
			return false;
		} else {
			return isInteger(arguments[0]);
		}
	}

	public boolean isEditValid(String[] arguments) {
		if (arguments.length == 0 || arguments.length == 1) {
			return false;
		}
		if (!isInteger(arguments[0])) {
			return false;
		}
		for (int i = 1; i < arguments.length; i += 2) {
			System.out.println(arguments[i]);
			if (!commandList_.getEditArgumentList().contains(arguments[i])) {
				return false;
			} else {
				if (i + 1 == arguments.length) {
					return false;
				} else if (arguments[i].equals("startdate") || arguments[i].equals("enddate")) {
					String date = naturalDate_.getDate(arguments[i + 1]);
					if (date == null) {
						invalidDate = true;
						return false;
					} else {
						assert Date.isLegalDate(date) : Constants.ASSERT_VALID_DATE;
						arguments[i + 1] = date;
					}
				} else if (arguments[i].equals("starttime") || arguments[i].equals("endtime")) {
					String time = naturalTime_.getTime(arguments[i + 1]);
					if (time == null) {
						invalidTime = true;
						return false;
					} else {
						arguments[i + 1] = time;
					}
				} else if (arguments[i].equals("repeat")) {
					String[] repeatArgument = arguments[i + 1].split(" ");
					try {
						if (!commandList_.getRecurrenceArgumentList().contains(repeatArgument[0])
								|| !isInteger(repeatArgument[1])) {
							return false;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						return false;
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
		if (arguments.length == 0) {
			return true;
		} else if (arguments.length == 1) {
			return commandList_.getDisplayArgumentList().contains(arguments[0]);
		}
		return false;
	}

	public boolean isHelpValid(String[] arguments) {
		if (arguments.length == 0) {
			return true;
		} else if (arguments.length == 1) {
			return commandList_.getHelpArgumentList().contains(arguments[0]);
		} else {
			return false;
		}
	}

	public boolean isSetdirValid(String[] arguments) {
		if (arguments.length == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isSearchValid(String[] arguments) {
		return arguments.length != 0;
	}

	public boolean isRetrieveValid(String[] arguments) {
		if (arguments.length == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRecurrenceValid(String[] arguments) {
		if (arguments.length != 3) {
			return false;
		} else if (!isInteger(arguments[0]) || !isInteger(arguments[2])) {
			return false;
		} else if (Integer.parseInt(arguments[2]) <= 1) {
			return false;
		} else {
			return commandList_.getRecurrenceArgumentList().contains(arguments[1]);
		}
	}

	public boolean isAliasValid(String[] arguments) {
		if (arguments.length != 2) {
			return false;
		}
		COMMAND_TYPE command1 = getAction(arguments[0]);
		COMMAND_TYPE command2 = getAction(arguments[1]);
		if (command1 != COMMAND_TYPE.INVALID && command2 == COMMAND_TYPE.INVALID) {
			return true;
		} else {
			return false;
		}
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

	public boolean isCommandType(String command, ArrayList<String> commandList) {
		if (commandList.contains(command)) {
			return true;
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

	public boolean getInvalidDate() {
		return invalidDate;
	}

	public boolean getInvalidTime() {
		return invalidTime;
	}
}