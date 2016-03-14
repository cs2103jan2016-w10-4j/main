package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;

public class Edit {
	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Edit(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}
	public String edit(String[] task) {
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = handlerMemory.get(taskID - 1);
		Task oldTask = cloneTask(eachTask);
		// edits the task
		fieldEditor(eachTask, task);
		// write to mainStorage
		mainStorage.write(handlerMemory, doneStorage);
		// remember previous state
		clearAndAdd(previousInputStorage, new PreviousInput("edit", oldTask, eachTask));
		return Constants.MESSAGE_EDIT_PASS;
	}

	private Task cloneTask(Task task){
		Task result = new Task(task.getName());
		result.setDate(task.getDate());
		result.setStartTime(task.getStartTime());
		result.setEndTime(task.getEndTime());
		result.setDetails(task.getDetails());
		return result;
	}
	private void fieldEditor(Task eachTask, String[] task) {
		String action;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i].trim();
			switch (action) {
			case "rename":
				eachTask.setName(task[i + 1].trim());
				break;
			case "date":
				eachTask.setDate(task[i + 1].trim());
				break;
			case "start":
				eachTask.setStartTime(task[i + 1].trim());
				break;
			case "end":
				eachTask.setEndTime(task[i + 1].trim());
				break;
			case "details":
				eachTask.setDetails(task[i + 1].trim());
				break;
			}
		}
		return;
	}
	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}
}
