package Handler;
import main.Constants;
import java.util.ArrayList;

import Storage.Storage;
import main.Task;

public class Delete {
	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Delete(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}
	public String delete(String[] task) {
		int taskID = Integer.parseInt(task[0].trim());
		if (taskID <= 0 || taskID > handlerMemory.size()) {
			return Constants.MESSAGE_DELETE_FAIL;
		} else {
			Task eachTask = handlerMemory.get(taskID - 1);
			handlerMemory.remove(eachTask);
			// write to mainStorage
			mainStorage.write(handlerMemory, doneStorage);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("delete", eachTask));
			return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
		}
	}
	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}
}
