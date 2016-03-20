package Handler;

import main.Constants;
import java.util.ArrayList;

import Storage.Storage;
import main.Task;

public class Add {
	
	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Add(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}
	public String add(String[] task) {
		Task eachTask = new Task(task[0].trim());
		String action;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i].trim();
			assert action!= null: "Add action is null";
			switch (action) {
			case Constants.MESSAGE_ADD_ACTION_DATE:
				eachTask.setDate(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_START:
				eachTask.setStartTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_END:
			case Constants.MESSAGE_ADD_ACTION_TIME:
				eachTask.setEndTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_DETAILS:
				eachTask.setDetails(task[i + 1].trim());
				break;
			}
		}
		// remember previous state
		clearAndAdd(previousInputStorage, new PreviousInput("add", eachTask));
		// add to arraylist storage
		handlerMemory.add(eachTask);
		// write to mainStorage
		mainStorage.write(handlerMemory, doneStorage);
		return String.format(Constants.MESSAGE_ADD_PASS, eachTask.getName());
	}
	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}
}