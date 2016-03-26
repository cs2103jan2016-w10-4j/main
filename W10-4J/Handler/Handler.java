package Handler;

import main.*;
import java.util.ArrayList;
import Storage.Storage;
import main.Constants.COMMAND_TYPE;

public class Handler {
	private static ArrayList<Task> notDoneYetStorage;
	private static ArrayList<Task> doneStorage;
	private static ArrayList<PreviousInput> previousInputStorage;
	private int taskID;
	Storage mainStorage = new Storage();

	public Handler() {
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read(Constants.MESSAGE_ACTION_READ, Constants.fileName);
		notDoneYetStorage = getFromStorage.get(0);
		doneStorage = getFromStorage.get(1);
		previousInputStorage = new ArrayList<PreviousInput>();
		//taskID = getTaskId();
		taskID = mainStorage.getTaskID();
	}

	// @@author Berkin
	public String executeCommand(COMMAND_TYPE command, String[] task) {
		try {
			Command cmd = createCommand(command, task);
			taskID = mainStorage.getTaskID();
			//taskID = getTaskId() + 1;
			return cmd.execute(task, taskID);
		} catch (IllegalArgumentException invalidCommandFormat) {
			return Constants.MESSAGE_INVALID_FORMAT;
		} catch (IllegalStateException unrecognizedCommand) {
			return Constants.MESSAGE_UNRECOGNÝZED_COMMAND;
		}
	}

	private Command createCommand(COMMAND_TYPE command, String[] task)
			throws IllegalArgumentException, IllegalStateException {
		switch (command) {
		case ADD:
			taskID++;
			mainStorage.writeTaskID(taskID);
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
			taskID = 0;
			mainStorage.writeTaskID(taskID);
			return new SetDir(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case RETRIEVE:
			return new Retrieve(notDoneYetStorage, doneStorage, mainStorage);
		case RECURRENCE:
			return new Recurrence(notDoneYetStorage,doneStorage,previousInputStorage,mainStorage);
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
	
	/*
	 *  What if I delete the last element (taskID = 3) and insert a new element,
	 *  should new element be taskID 3 or 4? Since taskID is unique, i think it should be 4 instead. 
	 */
	private int getTaskId() {
		int id = 0;
		for(int i = 0 ;i <notDoneYetStorage.size();i++){
			int currentId = notDoneYetStorage.get(i).getTaskID();
			if(currentId>id){
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
}