package main;

import java.util.ArrayList;

import main.Constants.COMMAND_TYPE;

public class Handler {
	
	Storage mainStorage = new Storage();
	private static ArrayList<Task> handlerMemory = new ArrayList<Task>();
	private static Task[] previousInputStorage = new Task[1];
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

	private String add(String[] task) {
		Task eachTask = new Task(task[0]);
		// 1st add format with 9 parameters
		if (task.length == 9){
			eachTask.setDate(task[2]);
			eachTask.setStartTime(task[4]);
			eachTask.setEndTime(task[6]);
			eachTask.setDetails(task[8]);
		} 
		// 2nd add format with 7 parameters
		else if (task.length == 7){
			eachTask.setDate(task[2]);
			eachTask.setEndTime(task[4]);
			eachTask.setDetails(task[6]);
		}
		// remember the just added task
		previousInputStorage[0] = eachTask;
		// add to arraylist storage
		handlerMemory.add(eachTask);
		return MESSAGE_ADD_PASS;
	}
	
	private String delete(String[] task) {
		int taskID = Integer.parseInt(task[0]);
		if (taskID<=0 || taskID>handlerMemory.size()){
			return MESSAGE_DELETE_FAIL;
		} else {
			handlerMemory.remove(taskID-1);
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
