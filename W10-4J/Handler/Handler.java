package Handler;

import main.*;
import java.util.ArrayList;
import java.util.Collections;

import Storage.Storage;
import main.Constants.COMMAND_TYPE;

public class Handler {
	private static ArrayList<Task> notDoneYetStorage;
	private static ArrayList<Task> doneStorage;
	private static ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage = new Storage();

	public Handler() {
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read(Constants.MESSAGE_ACTION_READ, Constants.fileName);
		notDoneYetStorage = getFromStorage.get(0);
		doneStorage = getFromStorage.get(1);
		previousInputStorage = new ArrayList<PreviousInput>();
	}

	// @@author Berkin
	public String executeCommand(COMMAND_TYPE command, String[] task) {
		try {
			Command cmd = createCommand(command, task);
			return cmd.execute(task, getTaskID());
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
			return new Add(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case EDIT:
			return new Edit(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case DELETE:
			return new Delete(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case DONE:
			return new Done(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case DISPLAY:
			return new Display(notDoneYetStorage, doneStorage, mainStorage);
		case SEARCH:
			return new Search(notDoneYetStorage, mainStorage);
		case SETDIR:
			return new SetDir(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case RETRIEVE:
			return new Retrieve(notDoneYetStorage, doneStorage, mainStorage);
		case RECURRENCE:
			return new Recurrence(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case UNDO:
			return new Undo(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case EXIT:
			System.exit(0);
		case HELP:
			return new Help();
		case INVALID:
			throw new IllegalArgumentException();
		default:
			throw new IllegalStateException();
		}
	}

	public static int getTaskID() {
		ArrayList<Integer> usedID = new ArrayList<>();
		for (int i = 0; i < doneStorage.size(); i++) {
			usedID.add(doneStorage.get(i).getTaskID());
		}
		for (int i = 0; i < notDoneYetStorage.size(); i++) {
			usedID.add(notDoneYetStorage.get(i).getTaskID());
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
		return notDoneYetStorage.size();
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
		return doneStorage.size();
	}
}