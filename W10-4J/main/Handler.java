package main;

import java.util.ArrayList;
import java.util.Collections;

import main.Constants.COMMAND_TYPE;

class PreviousInput {
	private String action_;
	private Task task_;
	// only for edit method in handler
	private Task editedTask_;

	public PreviousInput(String action, Task task) {
		action_ = action;
		task_ = task;
	}

	public PreviousInput(String action, Task task, Task editedTask) {
		action_ = action;
		task_ = task;
		editedTask_ = editedTask;
	}

	public String getAction() {
		return action_;
	}

	public Task getTask() {
		return task_;
	}

	public Task getEditedTask() {
		return editedTask_;
	}

	public void setAction(String action) {
		action_ = action;
	}

	public void setTask(Task task) {
		task_ = task;
	}

	public void setEditedTask(Task editedTask) {
		editedTask_ = editedTask;
	}
}

public class Handler {
	
	private static ArrayList<Task> handlerMemory;
	private static ArrayList<Task> doneStorage;
	private static ArrayList<PreviousInput> previousInputStorage;
	
	public Handler(){
		Storage mainStorage = new Storage();
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read();
		handlerMemory = getFromStorage.get(0);
		doneStorage = getFromStorage.get(1);
		previousInputStorage = new ArrayList<PreviousInput>();
	}

	private static String MESSAGE_ADD_PASS = ("Task has been added.");
	private static String MESSAGE_DELETE_PASS = ("Task has been deleted.");
	private static String MESSAGE_DELETE_FAIL = ("Task cannot be deleted.");
	private static String MESSAGE_EDIT_PASS = ("Task has been edited.");
	private static String MESSAGE_DONE_PASS = ("Task has been set to done.");
	private static String MESSAGE_DONE_FAIL = ("Task cannot be set to done.");
	private static String MESSAGE_SEARCH_PASS = ("Search successful.");
	private static String MESSAGE_SEARCH_FAIL = ("Search unsuccessful.");
	private static String MESSAGE_UNDO_PASS = ("Undo successful.");

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
			return undo();
		case EXIT:
			System.exit(0);
		case INVALID:
			return String.format(Constants.MESSAGE_INVALID_FORMAT);
		default:
			throw new Error("Unrecognized command type");
		}
	}

	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}

	private String add(String[] task) {
		Task eachTask = new Task(task[0]);
		String action;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i];
			switch (action) {
			case "date":
				eachTask.setDate(task[i + 1]);
				break;
			case "start":
				eachTask.setStartTime(task[i + 1]);
				break;
			case "end":
			case "time":
				eachTask.setEndTime(task[i + 1]);
				break;
			case "details":
				eachTask.setDetails(task[i + 1]);
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
		if (taskID <= 0 || taskID > handlerMemory.size()) {
			return MESSAGE_DELETE_FAIL;
		} else {
			Task eachTask = handlerMemory.get(taskID - 1);
			handlerMemory.remove(eachTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("delete", eachTask));
			return MESSAGE_DELETE_PASS;
		}
	}

	private String edit(String[] task) {
		int taskID = Integer.parseInt(task[0]);
		Task eachTask = handlerMemory.get(taskID - 1);
		Task editedTask = fieldEditor(eachTask, task);
		// remember previous state
		clearAndAdd(previousInputStorage, new PreviousInput("edit", eachTask, editedTask));
		return MESSAGE_EDIT_PASS;
	}

	private Task fieldEditor(Task eachTask, String[] task) {
		String action;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i];
			switch (action) {
			case "rename":
				eachTask.setName(task[i + 1]);
				break;
			case "date":
				eachTask.setDate(task[i + 1]);
				break;
			case "start":
				eachTask.setStartTime(task[i + 1]);
				break;
			case "end":
				eachTask.setEndTime(task[i + 1]);
				break;
			case "details":
				eachTask.setDetails(task[i + 1]);
				break;
			}
		}
		return eachTask;
	}

	private String done(String[] task) {
		int taskID = Integer.parseInt(task[0]);
		if (taskID <= 0 || taskID > handlerMemory.size()) {
			return MESSAGE_DONE_FAIL;
		} else {
			Task eachTask = handlerMemory.get(taskID - 1);
			handlerMemory.remove(eachTask);
			doneStorage.add(eachTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("done", eachTask));
			return MESSAGE_DONE_PASS;
		}
	}

	private String display(String[] task) {
		String displayField = task[1];
		ArrayList<Task> cloneHandlerMemory = cloneArray(handlerMemory);
		ArrayList<Task> exclusiveHandlerMemory = null;
		switch (displayField)
		{
			case "alphabetical order":
				exclusiveHandlerMemory = separateArrayList(cloneHandlerMemory, "name");
				Collections.sort(cloneHandlerMemory, Task.taskNameComparator);
				break;
			case "starttime":
				exclusiveHandlerMemory = separateArrayList(cloneHandlerMemory, "starttime");
				Collections.sort(cloneHandlerMemory, Task.taskStarttimeComparator);
				break;
			case "endtime":
				exclusiveHandlerMemory = separateArrayList(cloneHandlerMemory, "endtime");
				Collections.sort(cloneHandlerMemory, Task.taskEndtimeComparator);
				break;
			case "date":
				exclusiveHandlerMemory = separateArrayList(cloneHandlerMemory, "date");
				Collections.sort(cloneHandlerMemory, Task.taskDateComparator);
				break;
			case "tasks":
				// display tasks in cloneHandlerMemory only
				break;
			case "done":
				// display done tasks in doneStorage only
				break;
		}
		cloneHandlerMemory.addAll(exclusiveHandlerMemory);
		// ***need to consider sorting the done section and the tasks that dun have parameters like dates*** 
		return "";
	}
	
	// separate those tasks with the specific parameters and those that dont have in null list
	private ArrayList<Task> separateArrayList(ArrayList<Task> taskArray, String field){
		ArrayList<Task> separateArray = new ArrayList<Task>();
		ArrayList<Task> result = exclusiveSeparation(taskArray, separateArray, field);
		for (Task task: result){
			if (taskArray.contains(task)){
				taskArray.remove(task);
			}
		}
		return result;
	}
	
	private ArrayList<Task> exclusiveSeparation (ArrayList<Task> taskArray, ArrayList<Task> result, String field){
		switch (field)
		{
			case "name":
				 for (Task task: taskArray){
						if (task.getName()==null){
							result.add(task);
						}
					}
				break;
			case "starttime":
				 for (Task task: taskArray){
						if (task.getStartTime()==null){
							result.add(task);
						}
					}
				break;
			case "endtime":
				 for (Task task: taskArray){
						if (task.getEndTime()==null){
							result.add(task);
						}
					}
				break;
			case "date":
				 for (Task task: taskArray){
						if (task.getDate()==null){
							result.add(task);
						}
					}
				break;
		}
		return result;
	}
	
	private ArrayList<Task> cloneArray(ArrayList<Task> taskArray) {
		ArrayList<Task> clone = new ArrayList<Task>(taskArray.size());
		for (Task task : taskArray) {
			clone.add(task);
		}
		return clone;
	}

	private String search(String[] task) {
		// check whether exclude field exists
		if (task[1] != null) {
			for (Task eachTask : handlerMemory) {
				if ((eachTask.getName().contains(task[0]) && !eachTask.getName().contains(task[2]))
						|| (eachTask.getDetails().contains(task[0]) && !eachTask.getDetails().contains(task[2]))) {
					return MESSAGE_SEARCH_PASS;
				}
			}
		} else {
			for (Task eachTask : handlerMemory) {
				if (eachTask.getName().contains(task[0]) || eachTask.getDetails().contains(task[0])) {
					return MESSAGE_SEARCH_PASS;
				}
			}
		}
		return MESSAGE_SEARCH_FAIL;
	}

	private String retrieve(String[] task) {
		return "";
	}

	private String undo() {
		String actionToBeUndone = previousInputStorage.get(0).getAction();
		Task previousTask = previousInputStorage.get(0).getTask();
		switch (actionToBeUndone) {
		case "add":
			// to restore to previous state, must unadd the task
			handlerMemory.remove(taskFinder(handlerMemory, previousTask));
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
			Task eachTask = previousInputStorage.get(0).getEditedTask();
			handlerMemory.remove(eachTask);
			handlerMemory.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("edit", eachTask, previousTask));
			break;
		case "done":
			// to restore to previous state, must undone the task
			eachTask = taskFinder(doneStorage, previousTask);
			doneStorage.remove(eachTask);
			handlerMemory.add(eachTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("undone", eachTask));
			break;
		case "undone":
			// to restore to previous state, must undone the task
			eachTask = taskFinder(handlerMemory, previousTask);
			handlerMemory.remove(eachTask);
			doneStorage.add(eachTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput("done", eachTask));
			break;
		}
		return MESSAGE_UNDO_PASS;
	}

	private Task taskFinder(ArrayList<Task> taskArray, Task task) {
		for (Task eachTask : handlerMemory) {
			if (Task.taskNameComparator.compare(eachTask, task) == 0
					&& Task.taskStarttimeComparator.compare(eachTask, task) == 0
					&& Task.taskEndtimeComparator.compare(eachTask, task) == 0
					&& Task.taskDateComparator.compare(eachTask, task) == 0
					&& Task.taskDetailsComparator.compare(eachTask, task) == 0) {
				return eachTask;
			}
		}
		return null;
	}

	private String displayFormat(ArrayList<Task> sortedList) {
		String output = "";
		for (Task t : sortedList) {
			output += "Event: " + t.getName() + "<br>";
			if (t.getDate() != null) {
				output += "Date: " + t.getDate() + "<br>";
			}
			if (t.getStartTime() != null) {
				output += "StartTime: " + t.getStartTime() + "<br>";
			}
			if (t.getEndTime() != null) {
				output += "EndTime: " + t.getEndTime() + "<br>";
			}
			if (t.getDetails() != null) {
				output += "Details: " + t.getDetails() + "<br>";
			}
		}

		return output;
	}
}
