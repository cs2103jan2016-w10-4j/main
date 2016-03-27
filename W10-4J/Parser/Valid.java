package Parser;

import java.util.ArrayList;

import main.Constants;
import main.Constants.COMMAND_TYPE;

public class Valid {
	private NaturalTime naturalTime;
	private NaturalDate naturalDate;
	
	private ArrayList<String> addArgumentList = new ArrayList<>();
	private ArrayList<String> editArgumentList = new ArrayList<>();
	private ArrayList<String> displayArgumentList = new ArrayList<>();
	private ArrayList<String> searchArgumentList = new ArrayList<>();
	private ArrayList<String> recurrenceArgumentList = new ArrayList<>();
	private ArrayList<String> helpArgumentList = new ArrayList<>();
	
	private boolean invalidDate, invalidTime;
	
	public Valid(){
		naturalTime = new NaturalTime();
		naturalDate = new NaturalDate();
		generateCommandList();
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
			if (!addArgumentList.contains(arguments[i])) {
				return false;
			} else {
				if (i + 1 == arguments.length) {
					return false;
				} else if (arguments[i].equals("date")) {
					String date = naturalDate.getDate(arguments[i + 1]);
					if (date == null) {
						invalidDate = true;
						return false;
					} else {
						arguments[i + 1] = date;
					}
				} else if (arguments[i].equals("start") || arguments[i].equals("end")) {
					String time = naturalTime.getTime(arguments[i + 1]);
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
		if (arguments.length == 0) {
			return false;
		}
		return isInteger(arguments[0]);
	}

	public boolean isEditValid(String[] arguments) {
		if (arguments.length == 0 || arguments.length == 1) {
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
						invalidDate = true;
						return false;
					} else {
						arguments[i + 1] = date;
					}
				} else if (arguments[i].equals("start") || arguments[i].equals("end")) {
					String time = naturalTime.getTime(arguments[i + 1]);
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

	public boolean isRecurrenceValid(String[] arguments) {
		if (arguments.length != 2) {
			return false;
		} else if (!isInteger(arguments[0])) {
			return false;
		} else {
			return recurrenceArgumentList.contains(arguments[1]);
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
	
	public void generateCommandList() {
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
	}
	
	public boolean getInvalidDate(){
		return invalidDate;
	}
	
	public boolean getInvalidTime(){
		return invalidTime;
	}
}
