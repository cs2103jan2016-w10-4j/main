package Parser;

import main.Constants.COMMAND_TYPE;

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
		default:
			return true;
		}
	}

	public boolean isAddValid(String[] arguments) {
		if (arguments.length == 0) {
			return false;
		}
		for (int i = 1; i < arguments.length; i += 2) {
			if (!commandList_.getAddArgumentList().contains(arguments[i])) {
				return false;
			} else {
				if (i + 1 == arguments.length) {
					return false;
				} else if (arguments[i].equals("date")) {
					String date = naturalDate_.getDate(arguments[i + 1]);
					if (date == null) {
						invalidDate = true;
						return false;
					} else {
						arguments[i + 1] = date;
					}
				} else if (arguments[i].equals("start") || arguments[i].equals("end")) {
					String time = naturalTime_.getTime(arguments[i + 1]);
					if (time == null) {
						invalidTime = true;
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
			if (!!commandList_.getEditArgumentList().contains(arguments[i])) {
				return false;
			} else {
				if (i + 1 == arguments.length) {
					return false;
				} else if (arguments[i].equals("date")) {
					String date = naturalDate_.getDate(arguments[i + 1]);
					if (date == null) {
						invalidDate = true;
						return false;
					} else {
						arguments[i + 1] = date;
					}
				} else if (arguments[i].equals("start") || arguments[i].equals("end")) {
					String time = naturalTime_.getTime(arguments[i + 1]);
					if (time == null) {
						invalidTime = true;
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
		if (arguments.length == 0) {
			return true;
		} else if (arguments.length == 2) {
			return arguments[0].equals("by") && !commandList_.getDisplayArgumentList().contains(arguments[1]);
		}
		return false;
	}

	public boolean isHelpValid(String[] arguments) {
		if (arguments.length == 0) {
			return true;
		} else if (arguments.length == 1) {
			return !commandList_.getHelpArgumentList().contains(arguments[0]);
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

	public boolean isRetrieveValid(String[] arguments) {
		if (arguments.length == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRecurrenceValid(String[] arguments) {
		if (arguments.length != 2) {
			return false;
		} else if (!isInteger(arguments[0])) {
			return false;
		} else {
			return !commandList_.getRecurrenceArgumentList().contains(arguments[1]);
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