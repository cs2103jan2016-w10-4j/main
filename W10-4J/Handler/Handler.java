//@@author A0135779M
package Handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.*;
import main.Constants.COMMAND_TYPE;

public class Handler {
	private final Logger LOGGER = Logger.getLogger(Handler.class.getName());
	private ArraylistStorage arraylistStorage;
	private Add addTask;
	private Delete deleteTask;
	private Display displayTask;
	private Done doneTask;
	private Edit editTask;
	private Recurrence recurrTask;
	private Retrieve retrieveTask;
	private Search searchTask;
	private SetDir setDirTask;
	private Undo undoTask;
	private Help helpTask;

	public Handler() {
		arraylistStorage = new ArraylistStorage();
		createAllFunctions(arraylistStorage);
	}
	
	public void createAllFunctions(ArraylistStorage arraylistStorage){
		addTask = new Add(arraylistStorage);
		deleteTask = new Delete(arraylistStorage);
		displayTask = new Display(arraylistStorage);
		doneTask = new Done(arraylistStorage);
		editTask = new Edit(arraylistStorage);
		recurrTask = new Recurrence(arraylistStorage);
		retrieveTask = new Retrieve(arraylistStorage);
		searchTask = new Search(arraylistStorage);
		setDirTask = new SetDir(arraylistStorage);
		undoTask = new Undo(arraylistStorage);
		helpTask = new Help();
		LOGGER.log(Level.INFO, Constants.MESSAGE_FUNCTION_INITIALISATION);
	}
	//@@author 
	//@@author A0149174Y-unused
	public String executeCommand_OLD(COMMAND_TYPE command, String[] task) {
		try {
			Command cmd = getCommand(command, task);
			String toBeReturned=cmd.execute(task);
			HandlerMemory.updateMemory(cmd,command);
			return toBeReturned;
		} catch (IllegalArgumentException invalidCommandFormat) {
			LOGGER.log(Level.WARNING, Constants.MESSAGE_INVALID_FORMAT);
			return Constants.MESSAGE_INVALID_FORMAT;
		} catch (IllegalStateException unrecognizedCommand) {
			LOGGER.log(Level.WARNING, Constants.MESSAGE_UNRECOGNISED_COMMAND);
			return Constants.MESSAGE_UNRECOGNISED_COMMAND;
		}
	}
	//@@author A0149174Y
	public String executeCommand(COMMAND_TYPE command, String[] task) {
		try {
			Command cmd = getCommand(command, task);
			return cmd.execute(task);
		} catch (IllegalArgumentException invalidCommandFormat) {
			LOGGER.log(Level.WARNING, Constants.MESSAGE_INVALID_FORMAT);
			return Constants.MESSAGE_INVALID_FORMAT;
		} catch (IllegalStateException unrecognizedCommand) {
			LOGGER.log(Level.WARNING, Constants.MESSAGE_UNRECOGNISED_COMMAND);
			return Constants.MESSAGE_UNRECOGNISED_COMMAND;
		}
	}

	private Command getCommand(COMMAND_TYPE command, String[] task)
			throws IllegalArgumentException, IllegalStateException {
		switch (command) {
		case ADD:
			return addTask;
		case EDIT:
			return editTask;
		case DELETE:
			return deleteTask;
		case DONE:
			return doneTask;
		case DISPLAY:
			return displayTask;
		case SEARCH:
			return searchTask;
		case SETDIR:
			return setDirTask;
		case RETRIEVE:
			return retrieveTask;
		case RECURRENCE:
			return recurrTask;
		case UNDO:
			return undoTask;
		case HELP:
			return helpTask;
		case EXIT:
			System.exit(0);
		case INVALID:
			throw new IllegalArgumentException();
		default:
			throw new IllegalStateException();
		}
	}
	//@@author 
}