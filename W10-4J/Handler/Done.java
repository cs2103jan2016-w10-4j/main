package Handler;
import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.Constants;
public class Done {
	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Done(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}
	public String done(String[] task) {
		int taskID = Integer.parseInt(task[0].trim());
		if (taskID <= 0 || taskID > handlerMemory.size()) {
			return Constants.MESSAGE_DONE_FAIL;
		} else {
			Task eachTask = handlerMemory.get(taskID - 1);
			handlerMemory.remove(eachTask);
			doneStorage.add(eachTask);
			// write to mainStorage
			mainStorage.write(handlerMemory, doneStorage);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("done", eachTask));
			return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
		}
	}
	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}
}
