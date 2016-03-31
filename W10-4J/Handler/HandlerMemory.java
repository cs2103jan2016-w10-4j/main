package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Constants;
import main.Constants.COMMAND_STATE;
import main.Constants.COMMAND_TYPE;
import main.Task;

//@@author A0149174Y
public class HandlerMemory {

	private static ArrayList<Task> notDoneYetStorage;
	private static ArrayList<Task> doneStorage;
	private static ArrayList<PreviousInput> previousInputStorage;
	private static int taskID;
	static Storage mainStorage = new Storage();

	public HandlerMemory() {
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read(Constants.MESSAGE_ACTION_READ, Constants.fileName);
		notDoneYetStorage = getFromStorage.get(0);
		doneStorage = getFromStorage.get(1);
		previousInputStorage = new ArrayList<PreviousInput>();
		taskID = HandlerMemory.determineTaskID();
	}

	public static void updateMemory(Command cmd, COMMAND_TYPE command) {
		if (cmd.returnCommandState() != COMMAND_STATE.FAILED) {
			switch (command) {
			case ADD:
				addUpdateMemory(cmd);
				break;
			case EDIT:
				editUpdateMemory(cmd);
				break;
			case DELETE:
				deleteUpdateMemory(cmd);
				break;
			case DONE:
				doneUpdateMemory(cmd);
				break;
			case DISPLAY:
				break;
			case SEARCH:
				break;
			case SETDIR:
				setdirUpdateMemory();
				break;
			case RETRIEVE:
				break;
			case RECURRENCE:
				recurrenceUpdateMemory(cmd);
				break;
			case UNDO:
				undoUpdateMemory(cmd);
				break;
			case EXIT:
				assert false;
			case HELP:
				break;
			case INVALID:
				assert false;
			default:
				assert false;
			}
		}
	}

	private static void undoUpdateMemory(Command cmd) {
		switch (cmd.returnCommandState()) {
		case UNDOADD:
			updateMemoryUndoAdd(cmd);
			break;
		case UNDODELETE:
			updateMemoryUndoDelete(cmd);
			break;
		case UNDOEDIT:
			updateMemoryUndoEdit(cmd);
			break;
		case UNDODONE:
			updateMemoryUndoDone(cmd);
			break;
		case UNDOUNDO:
			updateMemoryUndoUndo(cmd);
		default:
			System.out.println(cmd.returnCommandState());
			break;
		}
		mainStorage.write(notDoneYetStorage, doneStorage);
	}

	private static void updateMemoryUndoUndo(Command cmd) {
		notDoneYetStorage.remove(cmd.returnEachTask());
		doneStorage.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoDone(Command cmd) {
		doneStorage.remove(cmd.returnEachTask());
		notDoneYetStorage.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_UNDO, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoEdit(Command cmd) {
		// to restore the previous state, must edit again
		Task eachTask = previousInputStorage.get(0).getEditedTask();
		notDoneYetStorage.remove(eachTask);
		notDoneYetStorage.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, eachTask, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoDelete(Command cmd) {
		notDoneYetStorage.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoAdd(Command cmd) {
		// to restore to previous state, must unadd the task
		notDoneYetStorage.remove(cmd.returnEachTask());
		// remember previous state
		clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
	}

	private static void recurrenceUpdateMemory(Command cmd) {
		mainStorage.write(notDoneYetStorage, doneStorage);
		clearAndAdd(previousInputStorage, new PreviousInput("edit", cmd.returnOldTask(), cmd.returnEachTask()));
	}

	private static void setdirUpdateMemory() {
		notDoneYetStorage.clear();
		doneStorage.clear();
		previousInputStorage.clear();
	}

	private static void doneUpdateMemory(Command cmd) {
		if (cmd.returnCommandState() == COMMAND_STATE.RECURRINGDONE) {
			updateMemoryForReccurringTask(cmd);
		} else {
			updateMemoryForNonReccurringTask(cmd);
		}
	}

	private static void updateMemoryForNonReccurringTask(Command cmd) {
		assert cmd.returnCommandState() == COMMAND_STATE.NONRECURRINGDONE;
		notDoneYetStorage.remove(cmd.returnEachTask());
		doneStorage.add(cmd.returnEachTask());
		updateMemoryForReccurringTask(cmd);
	}

	private static void updateMemoryForReccurringTask(Command cmd) {
		mainStorage.write(notDoneYetStorage, doneStorage);
		clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
	}

	private static void deleteUpdateMemory(Command cmd) {
		if (cmd.returnCommandState() == COMMAND_STATE.DELETEDONETASK) {
			updateMemoryForDeletedDoneTask(cmd);
		} else {
			updateMemoryForDeletedNotYetDoneTask(cmd);
		}
	}

	private static void updateMemoryForDeletedNotYetDoneTask(Command cmd) {
		assert cmd.returnCommandState() == COMMAND_STATE.DELETEUNDONETASK;
		notDoneYetStorage.remove(cmd.returnEachTask());
		mainStorage.write(notDoneYetStorage, doneStorage);
		clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
	}

	private static void updateMemoryForDeletedDoneTask(Command cmd) {
		doneStorage.remove(cmd.returnEachTask());
		mainStorage.write(notDoneYetStorage, doneStorage);
		clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
	}

	private static void editUpdateMemory(Command cmd) {
		mainStorage.write(notDoneYetStorage, doneStorage);
		clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, cmd.returnOldTask(), cmd.returnEachTask()));
	}

	private static void addUpdateMemory(Command cmd) {
		clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnEachTask()));
		notDoneYetStorage.add(cmd.returnEachTask());
		mainStorage.write(notDoneYetStorage, doneStorage);
	}
	//@@author


	public static int determineTaskID() {
		int id = 0;
		for (int i = 0; i < doneStorage.size(); i++) {
			int currentId = doneStorage.get(i).getTaskID();
			if (currentId > id) {
				id = currentId;
			}
		}
		for (int i = 0; i < notDoneYetStorage.size(); i++) {
			int currentId = notDoneYetStorage.get(i).getTaskID();
			if (currentId > id) {
				id = currentId;
			}
		}
		return id;
	}

	public static void setTaskID(int id) {
		taskID = id;
	}

	private static void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}

	public Task findByTaskID(ArrayList<Task> taskList, int taskID) {
		for (Task task : taskList) {
			if (task.getTaskID() == taskID) {
				return task;
			}
		}
		return null;
	}
	
	public static int getTaskID(){
		return taskID;
	}
	
	/////////GETTERS&SETTERS////////
	
	public static ArrayList<Task> getNotDoneYetStorage() {
		return notDoneYetStorage;
	}

	public static void setNotDoneYetStorage(ArrayList<Task> notDoneYetStorage) {
		HandlerMemory.notDoneYetStorage = notDoneYetStorage;
	}

	public static ArrayList<Task> getDoneStorage() {
		return doneStorage;
	}

	public static void setDoneStorage(ArrayList<Task> doneStorage) {
		HandlerMemory.doneStorage = doneStorage;
	}

	public static ArrayList<PreviousInput> getPreviousInputStorage() {
		return previousInputStorage;
	}

	public static void setPreviousInputStorage(ArrayList<PreviousInput> previousInputStorage) {
		HandlerMemory.previousInputStorage = previousInputStorage;
	}

	public Storage getMainStorage() {
		return mainStorage;
	}

	public void setMainStorage(Storage mainStorage) {
		this.mainStorage = mainStorage;
	}
}
