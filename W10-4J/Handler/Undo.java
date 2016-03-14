package Handler;
import main.Constants;
import java.util.ArrayList;

import Storage.Storage;
import main.Task;

public class Undo {
	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Undo(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}
	public String undo() {
		String actionToBeUndone = previousInputStorage.get(0).getAction();
		Task previousTask = previousInputStorage.get(0).getTask();
		Task eachTask;
		switch (actionToBeUndone) {
		case "add":
			// to restore to previous state, must unadd the task
			handlerMemory.remove(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("delete", previousTask));
			break;
		case "delete":
			// to restore to previous state, must add the task
			handlerMemory.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("add", previousTask));
			break;
		case "edit":
			// to restore to previous state, must edit again to previous state
			eachTask = previousInputStorage.get(0).getEditedTask();
			handlerMemory.remove(eachTask);
			handlerMemory.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("edit", eachTask, previousTask));
			break;
		case "done":
			// to restore to previous state, must undone the task
			//eachTask = taskFinder(doneStorage, previousTask);
			doneStorage.remove(previousTask);
			handlerMemory.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("undone", previousTask));
			break;
		case "undone":
			// to restore to previous state, must undone the task
			//eachTask = taskFinder(handlerMemory, previousTask);
			handlerMemory.remove(previousTask);
			doneStorage.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("done", previousTask));
			break;
		}
		// write to mainStorage
		mainStorage.write(handlerMemory, doneStorage);
		return Constants.MESSAGE_UNDO_PASS;
	}
	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}
}
