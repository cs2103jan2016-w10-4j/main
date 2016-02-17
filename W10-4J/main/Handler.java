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
		case DONE:
			return done(task);
		case DISPLAY:
			return display(task);
		case SEARCH:
			return search(task);
		case RETRIEVE:
			return retrieve(task);
		case UNDO:
			return undo(task);
		case EXIT:
			System.exit(0);
		case INVALID:
			return String.format(Constants.MESSAGE_INVALID_FORMAT);
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

	private String done(String[] task) {
		return "";
	}

	private String display(String[] task) {
		return "";
	}

	private String search(String[] task) {
		return "";
	}

	private String retrieve(String[] task) {
		return "";
	}

	private String undo(String[] task) {
		return "";
	}
}
