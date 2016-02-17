package main;

import main.Constants.COMMAND_TYPE;

public class Parser {
	Handler h = new Handler();

	public String parse(String command) {
		String commandTypeString = getFirstWord(command);
		COMMAND_TYPE commandType = getAction(commandTypeString);

		return h.execute(commandType, getTask(command));
	}

	private COMMAND_TYPE getAction(String command) {
		if (command.equalsIgnoreCase("add")) {
			return COMMAND_TYPE.ADD;
		} else if (command.equalsIgnoreCase("edit")) {
			return COMMAND_TYPE.EDIT;
		} else if (command.equalsIgnoreCase("delete")) {
			return COMMAND_TYPE.DELETE;
		} else if (command.equalsIgnoreCase("display")) {
			return COMMAND_TYPE.DISPLAY;
		} else if (command.equalsIgnoreCase("exit")) {
			return COMMAND_TYPE.EXIT;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}

	private String[] getTask(String command) {
		return command.substring(command.indexOf(" ")).split(" ");
	}

	private String getFirstWord(String command) {
		return command.split(" ")[0];
	}
}