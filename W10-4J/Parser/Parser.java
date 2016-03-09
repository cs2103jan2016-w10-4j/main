package Parser;

import java.util.ArrayList;

import Handler.Handler;

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

	ArrayList<String> addArgumentList = new ArrayList<>();
	ArrayList<String> editArgumentList = new ArrayList<>();
	ArrayList<String> displayArgumentList = new ArrayList<>();
	ArrayList<String> searchArgumentList = new ArrayList<>();

	public static final String MESSAGE_INVALID_FORMAT = "invalid command format";
	public static final String[] addDefaultCommandList = {"add","new","+"};
	public static final String[] deleteDefaultCommandList = {"delete","del","remove","rm","bin","thrash","-"};
	public static final String[] editDefaultCommandList = {"edit","change","edittask","e"};
	public static final String[] doneDefaultCommandList = {"done","finish","complete"};
	public static final String[] displayDefaultCommandList = {"display","ls","list","show","print"};
	public static final String[] searchDefaultCommandList = {"search","find","contains"};
	public static final String[] setdirDefaultCommandList = {"setdir","cd","setdirectory","set directory"};
	public static final String[] retrieveDefaultCommandList = {"storage","get","open","grab","grep","retrieve"};
	public static final String[] undoDefaultCommandList = {"undo","whoops","mb"};
	public static final String[] exitDefaultCommandList = {"exit","quit"};
	
	public static final String[] addDefaultArgumentList = {"date","start","end","details","by","at"};
	public static final String[] editDefaultArgumentList = {"rename","date","start","end","details","by","at"};
	public static final String[] displayDefaultArgumentList = {"by","alphabetical order","name","starttime","endtime","date","tasks","done"};
	public static final String[] searchDefaultArgumentList = {"excl","exclude"};

	Handler h;
	NaturalLanguage n;

	public Parser() {
		generateCommandList();
		h = new Handler();
		n = new NaturalLanguage();
	}

	public String parse(String command) {
		String commandTypeString = getFirstWord(command);
		String commandType = getAction(commandTypeString);
		if (commandType.equals("invalid")) {
			return MESSAGE_INVALID_FORMAT;
		}
		String[] arguments = getArguments(commandType, command);
		if (!isValid(commandType, arguments)) {
			return MESSAGE_INVALID_FORMAT;
		}
		if(commandType.equals("display") || commandType.equals("search")){
			return "0"+h.executeCommand(commandType, arguments);
		} else{
			return "1"+h.executeCommand(commandType, arguments);
		}
	}

	public String getAction(String command) {
		if (isCommandType(command, addCommandList)) {
			return "add";
		} else if (isCommandType(command, deleteCommandList)) {
			return "delete";
		} else if (isCommandType(command, editCommandList)) {
			return "edit";
		} else if (isCommandType(command, doneCommandList)) {
			return "done";
		} else if (isCommandType(command, displayCommandList)) {
			return "display";
		} else if (isCommandType(command, searchCommandList)) {
			return "search";
		} else if (isCommandType(command, setdirCommandList)) {
			return "setdir";
		} else if (isCommandType(command, retrieveCommandList)) {
			return "retrieve";
		} else if (isCommandType(command, undoCommandList)) {
			return "undo";
		} else if (isCommandType(command, exitCommandList)) {
			return "exit";
		} else {
			return "invalid";
		}
	}

	public boolean isCommandType(String command, ArrayList<String> commandList) {
		if (commandList.contains(command)) {
			return true;
		} else {
			return false;
		}
	}

	public String[] getArguments(String commandType, String command) {
		if (commandType.equals("retrieve") || commandType.equals("setdir")) {
			if(command.contains(" ")){
				return new String[]{command.substring(command.indexOf(" ")+1)};
			} else{
				return new String[]{};
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
		if (commandType.equals("add")) {
			return compactArguments(tokens, addArgumentList);
		} else if (commandType.equals("edit")) {
			return compactArguments(tokens, editArgumentList);
		} else if (commandType.equals("display")) {
			return compactArguments(tokens, displayArgumentList);
		} else if (commandType.equals("search")) {
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

	public boolean isValid(String commandType, String[] arguments) {
		switch (commandType) {
		case "add":
			return isAddValid(arguments);
		case "delete":
			return isDeleteValid(arguments);
		case "edit":
			return isEditValid(arguments);
		case "done":
			return isDoneValid(arguments);
		case "display":
			return isDisplayValid(arguments);
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
					String date = n.getDate(arguments[i + 1]);
					if (date == null) {
						return false;
					} else {
						arguments[i + 1] = date;
					}
				} else if (arguments[i].equals("start") || arguments[i].equals("end")) {
					String time = n.getTime(arguments[i + 1]);
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
					String date = n.getDate(arguments[i + 1]);
					if (date == null) {
						return false;
					} else {
						arguments[i + 1] = date;
					}
				} else if (arguments[i].equals("start") || arguments[i].equals("end")) {
					String time = n.getTime(arguments[i + 1]);
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

	public String getFirstWord(String command) {
		return command.split(" ")[0];
	}

	public void generateCommandList() {
		for (int i = 0; i < addDefaultCommandList.length; i++) {
			addCommandList.add(addDefaultCommandList[i]);
		}
		for (int i = 0; i < deleteDefaultCommandList.length; i++) {
			deleteCommandList.add(deleteDefaultCommandList[i]);
		}
		for (int i = 0; i < editDefaultCommandList.length; i++) {
			editCommandList.add(editDefaultCommandList[i]);
		}
		for (int i = 0; i < doneDefaultCommandList.length; i++) {
			doneCommandList.add(doneDefaultCommandList[i]);
		}
		for (int i = 0; i < displayDefaultCommandList.length; i++) {
			displayCommandList.add(displayDefaultCommandList[i]);
		}
		for (int i = 0; i < searchDefaultCommandList.length; i++) {
			searchCommandList.add(searchDefaultCommandList[i]);
		}
		for (int i = 0; i < setdirDefaultCommandList.length; i++) {
			setdirCommandList.add(setdirDefaultCommandList[i]);
		}
		for (int i = 0; i < retrieveDefaultCommandList.length; i++) {
			retrieveCommandList.add(retrieveDefaultCommandList[i]);
		}
		for (int i = 0; i < undoDefaultCommandList.length; i++) {
			undoCommandList.add(undoDefaultCommandList[i]);
		}
		for (int i = 0; i < exitDefaultCommandList.length; i++) {
			exitCommandList.add(exitDefaultCommandList[i]);
		}

		for (int i = 0; i < addDefaultArgumentList.length; i++) {
			addArgumentList.add(addDefaultArgumentList[i]);
		}
		for (int i = 0; i < editDefaultArgumentList.length; i++) {
			editArgumentList.add(editDefaultArgumentList[i]);
		}
		for (int i = 0; i < displayDefaultArgumentList.length; i++) {
			displayArgumentList.add(displayDefaultArgumentList[i]);
		}
		for (int i = 0; i < searchDefaultArgumentList.length; i++) {
			searchArgumentList.add(searchDefaultArgumentList[i]);
		}
	}
}