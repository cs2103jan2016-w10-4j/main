package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Constants;
import main.Constants.COMMAND_STATE;
import main.Constants.COMMAND_TYPE;
import main.Task;
//@@author Berkin
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
		taskID = HandlerMemory.getTaskID();
	}
	
	public static void updateMemory(Command cmd, COMMAND_TYPE command) {
		if(cmd.returnCommandState()!=COMMAND_STATE.FAILED) {
		switch (command) {
		case ADD:
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnEachTask()));
			notDoneYetStorage.add(cmd.returnEachTask());
			mainStorage.write(notDoneYetStorage, doneStorage);  
		case EDIT:
			mainStorage.write(notDoneYetStorage, doneStorage);
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, cmd.returnOldTask(), cmd.returnEachTask()));
		case DELETE:
			if(cmd.returnCommandState()==COMMAND_STATE.DELETEDONETASK) {
				doneStorage.remove(cmd.returnEachTask());
				mainStorage.write(notDoneYetStorage, doneStorage);
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
			}
			else {
				assert cmd.returnCommandState()==COMMAND_STATE.DELETEUNDONETASK;
				notDoneYetStorage.remove(cmd.returnEachTask());
				mainStorage.write(notDoneYetStorage, doneStorage);
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
			}
		case DONE:
			if(cmd.returnCommandState()==COMMAND_STATE.RECURRINGDONE) {
				mainStorage.write(notDoneYetStorage, doneStorage);
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
			}
			else {
				assert cmd.returnCommandState()==COMMAND_STATE.NONRECURRINGDONE;
				notDoneYetStorage.remove(cmd.returnEachTask());
				doneStorage.add(cmd.returnEachTask());
				mainStorage.write(notDoneYetStorage, doneStorage);
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
			}
		case DISPLAY:
			//fallthrough //This part is done in display class
		case SEARCH:
			
		case SETDIR:
			notDoneYetStorage.clear();
  			doneStorage.clear();
  			previousInputStorage.clear();
		case RETRIEVE:
			//fallthrough //This part is done in retrieve class
		case RECURRENCE:
			mainStorage.write(notDoneYetStorage, doneStorage);
			clearAndAdd(previousInputStorage, new PreviousInput("edit", cmd.returnOldTask(), cmd.returnEachTask()));
		case UNDO:
			switch(cmd.returnCommandState()) {
			case UNDOADD:
				// to restore to previous state, must unadd the task
				notDoneYetStorage.remove(cmd.returnEachTask());
				// remember previous state
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
			case UNDODELETE:
				notDoneYetStorage.add(cmd.returnEachTask());
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnEachTask()));
			case UNDOEDIT:
				// to restore to previous state, must edit again to previous state
				Task eachTask= previousInputStorage.get(0).getEditedTask();
				notDoneYetStorage.remove(eachTask);
				notDoneYetStorage.add(cmd.returnEachTask());
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, eachTask, cmd.returnEachTask()));
			case UNDODONE:
				doneStorage.remove(cmd.returnEachTask());
				notDoneYetStorage.add(cmd.returnEachTask());
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_UNDO, cmd.returnEachTask()));
			case UNDOUNDO:
				notDoneYetStorage.remove(cmd.returnEachTask());
				doneStorage.add(cmd.returnEachTask());
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
			default:
				break;
			}
			mainStorage.write(notDoneYetStorage, doneStorage);
		case EXIT:
			assert false;
		case HELP:
		//Fallthrough	
		case INVALID:
			assert false;
		default:
			assert false;
		}
		}
	}
	//@@author
	
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

	public static int getTaskID() {
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
}


