//@@author A0149174Y-unused
/**
 * @author A0149174Y
 *The idea to handle all the sequential storage in the handler through a separate class called HandlerMemory was proposed by me before V0.3.
 *I implemented all the unused codes present under the name for A0149174Y-unused until V0.3.
 *Basically it stores all the arraylists and updates all of them after each command operation. 
 *Rather than updating the memory in each execute function, the command just stores it's end state after it's executed.
 *According to which state the command ended in (forexample in FAIL state) the updateMemory function decides how to update the memory accordingly.
 *After V0.3 this design was decided to be changed by my teammates. Now the class ArrayListStorage handles the job of HandlerMemory.
 *Now I changed my execute functions as execute_OLD and change all the ArrayList names as _OLD as well so that it wont collide with the new version.
 */
package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Constants;
import main.Constants.COMMAND_TYPE;
import main.Task;

public class HandlerMemory {

	public enum COMMAND_STATE {
		FAILED,UNDOADD,UNDODELETE,UNDOUNDO,UNDODONE,UNDOEDIT,DELETEDONETASK,DELETEUNDONETASK,RECURRINGDONE,NONRECURRINGDONE
	};
	
	private static ArrayList<Task> notDoneYetStorage_OLD;
	private static ArrayList<Task> doneStorage_OLD;
	private static ArrayList<PreviousInput> previousInputStorage_OLD;
	private static int taskID;
	static Storage mainStorage_OLD = new Storage();

	public HandlerMemory() {
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage_OLD.read(Constants.MESSAGE_ACTION_READ, Constants.DEFAULT_FILENAME);
		notDoneYetStorage_OLD = getFromStorage.get(0);
		doneStorage_OLD = getFromStorage.get(1);
		previousInputStorage_OLD = new ArrayList<PreviousInput>();
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
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
	}

	private static void updateMemoryUndoUndo(Command cmd) {
		notDoneYetStorage_OLD.remove(cmd.returnEachTask());
		doneStorage_OLD.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoDone(Command cmd) {
		doneStorage_OLD.remove(cmd.returnEachTask());
		notDoneYetStorage_OLD.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_UNDO, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoEdit(Command cmd) {
		// to restore the previous state, must edit again
		Task eachTask = previousInputStorage_OLD.get(0).getEditedTask();
		notDoneYetStorage_OLD.remove(eachTask);
		notDoneYetStorage_OLD.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, eachTask, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoDelete(Command cmd) {
		notDoneYetStorage_OLD.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoAdd(Command cmd) {
		// to restore to previous state, must unadd the task
		notDoneYetStorage_OLD.remove(cmd.returnEachTask());
		// remember previous state
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
	}

	private static void recurrenceUpdateMemory(Command cmd) {
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, cmd.returnOldTask(), cmd.returnEachTask()));
	}

	private static void setdirUpdateMemory() {
		notDoneYetStorage_OLD.clear();
		doneStorage_OLD.clear();
		previousInputStorage_OLD.clear();
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
		notDoneYetStorage_OLD.remove(cmd.returnEachTask());
		doneStorage_OLD.add(cmd.returnEachTask());
		updateMemoryForReccurringTask(cmd);
	}

	private static void updateMemoryForReccurringTask(Command cmd) {
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
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
		notDoneYetStorage_OLD.remove(cmd.returnEachTask());
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
	}

	private static void updateMemoryForDeletedDoneTask(Command cmd) {
		doneStorage_OLD.remove(cmd.returnEachTask());
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
	}

	private static void editUpdateMemory(Command cmd) {
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, cmd.returnOldTask(), cmd.returnEachTask()));
	}

	private static void addUpdateMemory(Command cmd) {
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnEachTask()));
		notDoneYetStorage_OLD.add(cmd.returnEachTask());
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
	}
	//@@author

	//@@author A0135779M

	private static void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}

	Task findByTaskID(ArrayList<Task> taskList, int taskID) {
		for (Task task : taskList) {
			if (task.getTaskID() == taskID) {
				return task;
			}
		}
		return null;
	}
	
	static int getTaskID(){
		return taskID;
	}
	
	private static int determineTaskID() {
		int id = 0;
		for (int i = 0; i < doneStorage_OLD.size(); i++) {
			int currentId = doneStorage_OLD.get(i).getTaskID();
			if (currentId > id) {
				id = currentId;
			}
		}
		for (int i = 0; i < notDoneYetStorage_OLD.size(); i++) {
			int currentId = notDoneYetStorage_OLD.get(i).getTaskID();
			if (currentId > id) {
				id = currentId;
			}
		}
		return id;
	}

	public static void setTaskID(int id) {
		taskID = id;
	}
	
	/////////GETTERS&SETTERS////////
	
	public static ArrayList<Task> getNotDoneYetStorage_OLD() {
		return notDoneYetStorage_OLD;
	}

	public static void setNotDoneYetStorage_OLD(ArrayList<Task> notDoneYetStorage_OLD) {
		HandlerMemory.notDoneYetStorage_OLD = notDoneYetStorage_OLD;
	}

	public static ArrayList<Task> getDoneStorage_OLD() {
		return doneStorage_OLD;
	}

	public static void setDoneStorage_OLD(ArrayList<Task> doneStorage_OLD) {
		HandlerMemory.doneStorage_OLD = doneStorage_OLD;
	}

	public static ArrayList<PreviousInput> getPreviousInputStorage_OLD() {
		return previousInputStorage_OLD;
	}

	public static void setPreviousInputStorage_OLD(ArrayList<PreviousInput> previousInputStorage_OLD) {
		HandlerMemory.previousInputStorage_OLD = previousInputStorage_OLD;
	}

	public Storage getMainStorage_OLD() {
		return mainStorage_OLD;
	}

	public void setMainStorage_OLD(Storage mainStorage_OLD) {
		this.mainStorage_OLD = mainStorage_OLD;
	}
}
//@@author