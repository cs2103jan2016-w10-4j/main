package Handler;

import main.*;
import java.util.ArrayList;
import java.util.Collections;

import Storage.Storage;
import main.Constants.COMMAND_TYPE;

public class Handler {
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
		initialiseAllArrays(arraylistStorage);
	}
	
	public void initialiseAllArrays(ArraylistStorage arraylistStorage){
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
	}

	public String executeCommand(COMMAND_TYPE command, String[] task) {
		try {
			Command cmd = createCommand(command, task);
			return cmd.execute(task);
		} catch (IllegalArgumentException invalidCommandFormat) {
			return Constants.MESSAGE_INVALID_FORMAT;
		} catch (IllegalStateException unrecognizedCommand) {
			return Constants.MESSAGE_UNRECOGNISED_COMMAND;
		}
	}

	private Command createCommand(COMMAND_TYPE command, String[] task)
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

	public int getTaskID() {
		ArrayList<Integer> usedID = new ArrayList<>();
		for (int i = 0; i < arraylistStorage.getDoneStorage().size(); i++) {
			usedID.add(arraylistStorage.getDoneStorage().get(i).getTaskID());
		}
		for (int i = 0; i < arraylistStorage.getNotDoneStorage().size(); i++) {
			usedID.add(arraylistStorage.getNotDoneStorage().get(i).getTaskID());
		}
		Collections.sort(usedID);
		for (int i = 0; i < usedID.size(); i++) {
			if (usedID.get(i) != i + 1) {
				return i + 1;
			}
		}
		return usedID.size() + 1;
	}

	public int getNumberOfTaskTotal() {
		return arraylistStorage.getNotDoneStorage().size();
	}

	// Not implemented yet, waiting for handlerMemory to finish
	public int getNumberOfTaskToday() {
		return -1;
	}

	// Not implemented yet, waiting for handlerMemory to finish
	public int getNumberOfTaskOverdue() {
		return -1;
	}

	public int getNumberOfTaskDone() {
		return arraylistStorage.getDoneStorage().size();
	}
}