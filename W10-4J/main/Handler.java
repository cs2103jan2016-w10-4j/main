package main;

import java.util.ArrayList;

import main.Constants.COMMAND_TYPE;

class PreviousInput {
	private String action_;
	private Task task_;
	
	public PreviousInput(String action, Task task){
		action_ = action;
		task_ = task;
	}
	
	public String getAction(){
		return action_;
	}
	public Task getTask(){
		return task_;
	}
	public void setAction(String action){
		action_ = action;
	}
	public void setTask(Task task){
		task_ = task;
	}
}

public class Handler {
	
	Storage mainStorage = new Storage();
	private static ArrayList<Task> handlerMemory = new ArrayList<Task>();
	private static ArrayList<PreviousInput> previousInputStorage = new ArrayList<PreviousInput>();
	private static ArrayList<Task> doneStorage = new ArrayList<Task>();
	
	private static String MESSAGE_ADD_PASS = ("Task has been added.");
	private static String MESSAGE_DELETE_PASS = ("Task has been deleted.");
	private static String MESSAGE_DELETE_FAIL = ("Task cannot be deleted.");
	private static String MESSAGE_EDIT_PASS = ("Task has been edited.");

	public String executeCommand(COMMAND_TYPE command, String[] task) {

		switch (command) {
		case ADD:
			return add(task);
		case EDIT:
			return edit(task);
		case DELETE:
			return delete(task);
		case DONE:
			return done(task);
		case DISPLAY:
			return display(task);
		case SEARCH:
			return search(task);
		case RETRIEVE:
			return retrieve(task);
		case UNDO:
			return undo(task);
		case EXIT:
			System.exit(0);
		case INVALID:
			return String.format(Constants.MESSAGE_INVALID_FORMAT);
		default:
			throw new Error("Unrecognized command type");
		}
	}
	
	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task){
		taskArray.clear();
		taskArray.add(task);
	}

	private String add(String[] task) {
		Task eachTask = new Task(task[0]);
		String action;
		for (int i=1; i<task.length; i+=2){
			action = task[i];
			switch (action)
			{
				case "date":
					eachTask.setDate(task[i+1]);
					break;
				case "start":
					eachTask.setStartTime(task[i+1]);
					break;
				case "end":
				case "time":
					eachTask.setEndTime(task[i+1]);
					break;
				case "details":
					eachTask.setDetails(task[i+1]);
					break;
			}
		}
		// remember previous state
		clearAndAdd(previousInputStorage, new PreviousInput("add", eachTask));
		// add to arraylist storage
		handlerMemory.add(eachTask);
		return MESSAGE_ADD_PASS;
	}
	
	private String delete(String[] task) {
		int taskID = Integer.parseInt(task[0]);
		if (taskID<=0 || taskID>handlerMemory.size()){
			return MESSAGE_DELETE_FAIL;
		} else {
			Task eachTask = handlerMemory.get(taskID-1);
			handlerMemory.remove(eachTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("delete", eachTask));
			return MESSAGE_DELETE_PASS;
		}
	}

	private String edit(String[] task) {
		int taskID = Integer.parseInt(task[0]);
		Task eachTask = handlerMemory.get(taskID-1);
		fieldEditor(eachTask, task);
		return MESSAGE_EDIT_PASS;
	}
	
	private void fieldEditor(Task eachTask, String[] task){
		String action;
		for (int i=1; i<task.length; i+=2){
			action = task[i];
			switch (action)
			{
				case "rename":
					eachTask.setName(task[i+1]);
					break;
				case "date":
					eachTask.setDate(task[i+1]);
					break;
				case "start":
					eachTask.setStartTime(task[i+1]);
					break;
				case "end":
					eachTask.setEndTime(task[i+1]);
					break;
				case "details":
					eachTask.setDetails(task[i+1]);
					break;
			}
		}
	}

	private String done(String[] task) {
		return "";
	}

	private String display(String[] task) {
		return "";
	}

	private String search(String[] task) {
		return "";
	}

	private String retrieve(String[] task) {
		return "";
	}

	private String undo(String[] task) {
		return "";
	}
}
