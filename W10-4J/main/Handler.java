package main;

import main.Constants.COMMAND_TYPE;

public class Handler {

	public String executeCommand(COMMAND_TYPE command, String[] task) {

		switch (command) {
		case ADD:
			return add(task);
		case EDIT:
			return edit(task);
		case DELETE:
			return delete(task);
		case DISPLAY:
			return display();
		case INVALID:
			return String.format(Constants.MESSAGE_INVALID_FORMAT, command);
		case EXIT:
			System.exit(0);
		default:
			throw new Error("Unrecognized command type");
		}
	}

	private String add(String[] task) {
		return "";
	}
	
	private String edit(String[] task) {
		return "";
	}

	private String delete(String[] task) {
		return "";
	}

	private String display() {
		return "";
	}
}
