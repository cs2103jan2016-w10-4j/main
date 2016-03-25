package Handler;

import java.util.ArrayList;
import main.Constants;
import Storage.Storage;
import main.Task;

public class Recurrence implements Command{

	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Recurrence(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage,
			ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
	this.handlerMemory = handlerMemory;
	this.doneStorage = doneStorage;
	this.previousInputStorage = previousInputStorage;
	this.mainStorage = mainStorage;
}
	
	public String execute(String[] task, int notUsedInThisCommand) {
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = findByTaskID(handlerMemory, taskID);
		Task oldTask = cloneTask(eachTask);
		switch (task[1]) {
		case "day":
			eachTask.setDay(true);
			break;
		case "week":
			eachTask.setWeek(true);
			break;
		case "month":
			eachTask.setMonth(true);
			break;
		case "year":
			eachTask.setYear(true);
			break;
		}
		mainStorage.write(handlerMemory, doneStorage);
		clearAndAdd(previousInputStorage, new PreviousInput("edit", oldTask, eachTask));
		return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
	}
	
	public Task findByTaskID(ArrayList<Task> taskList, int taskID){
		for (Task task: taskList){
			if (task.getTaskID()==taskID){
				return task;
			}
		}
		return null;
	}
	
	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}
	
	private Task cloneTask(Task task) {
		Task result = new Task(task.getName());
		result.setDate(task.getDate());
		result.setStartTime(task.getStartTime());
		result.setEndTime(task.getEndTime());
		result.setDetails(task.getDetails());
		return result;
	}
}
