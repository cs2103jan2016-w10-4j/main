package Handler;

import java.util.ArrayList;
import java.util.Collections;

import Storage.Storage;
import main.Constants;
import main.Task;
import main.Constants.COMMAND_TYPE;


public class Handler {
	
	private static ArrayList<Task> handlerMemory;
	private static ArrayList<Task> doneStorage;
	private static ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage = new Storage();
	
	public Handler(){
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read();
		handlerMemory = getFromStorage.get(0);
		doneStorage = getFromStorage.get(1);
		previousInputStorage = new ArrayList<PreviousInput>();
	}

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
		Task eachTask = new Task(task[0].trim());
		String action;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i].trim();
			switch (action) {
			case "date":
				eachTask.setDate(task[i + 1].trim());
				break;
			case "start":
				eachTask.setStartTime(task[i + 1].trim());
				break;
			case "end":
			case "time":
				eachTask.setEndTime(task[i + 1].trim());
				break;
			case "details":
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
		return Constants.MESSAGE_ADD_PASS;
	}

	private String delete(String[] task) {
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
			return Constants.MESSAGE_DELETE_PASS;
		}
	}

	private String edit(String[] task) {
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

	private String done(String[] task) {
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
			return Constants.MESSAGE_DONE_PASS;
		}
	}

	private String display(String[] task) {
		if(task.length==0){
			return displayFormat(handlerMemory);
		}else{
			String displayField = task[1].trim();
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
					break;
				case "done":
					return displayFormat(doneStorage);
			}
			if (exclusiveHandlerMemory != null){
				cloneHandlerMemory.addAll(exclusiveHandlerMemory);
			}
			return displayFormat(cloneHandlerMemory);
		}
	}
	
	// separate those tasks with the specific parameters and those that dont have in null list called result
	private ArrayList<Task> separateArrayList(ArrayList<Task> taskArray, String field){
		ArrayList<Task> separateArray = new ArrayList<Task>();
		ArrayList<Task> result = exclusiveSeparation(taskArray, separateArray, field);
		// edit the clone to remove the excluded tasks
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
		ArrayList<Task> searchHandlerMemory = new ArrayList<Task>();
		// each task is certain to have a name
		// check whether exclude field exists
		if (task.length > 1) {
			for (Task eachTask : handlerMemory) {
				if (eachTask.getDetails()==null){
					if (searchName(eachTask, task, true)){
						searchHandlerMemory.add(eachTask);
					}
				} else {
					if (searchNameAndDetails(eachTask, task, true)){
						searchHandlerMemory.add(eachTask);
					}
				}
			}
		} else {
			for (Task eachTask : handlerMemory) {
				if (eachTask.getDetails()==null){
					if (searchName(eachTask, task, false)){
						searchHandlerMemory.add(eachTask);
					}
				} else {
					if (searchNameAndDetails(eachTask, task, false)){
						searchHandlerMemory.add(eachTask);
					}
				}
			}
		}
		if (searchHandlerMemory.size()!=0){
			return displayFormat(searchHandlerMemory);
		}
		return Constants.MESSAGE_SEARCH_FAIL;
	}

	private boolean searchNameAndDetails(Task eachTask, String[] task, boolean excludeField){
		// check whether exclude field exists
		if (excludeField) {
			if ((eachTask.getName().contains(task[0].trim()) && !eachTask.getName().contains(task[2].trim()))
					|| (eachTask.getDetails().contains(task[0].trim()) && !eachTask.getDetails().contains(task[2].trim()))) {
				return true;
			}
		} else {
			if (eachTask.getName().contains(task[0].trim()) || eachTask.getDetails().contains(task[0].trim())) {
				return true;
			}
		}
		return false;
	}
	private boolean searchName(Task eachTask, String[] task, boolean excludeField){
		// check whether exclude field exists
		if (excludeField) {
			if ((eachTask.getName().contains(task[0].trim()) && !eachTask.getName().contains(task[2].trim()))) {
				return true;
			}
		} else {
			if (eachTask.getName().contains(task[0].trim())) {
				return true;
			}
		}
		return false;
	}
	private String retrieve(String[] task) {
		try{
			ArrayList<ArrayList<Task>> getFromStorage = mainStorage.retrieve(task[0]);
			handlerMemory = getFromStorage.get(0);
			doneStorage = getFromStorage.get(1);
			previousInputStorage = new ArrayList<PreviousInput>();
		} catch(Exception e){
			e.printStackTrace();
			return Constants.MESSAGE_RETRIEVE_FAIL;
		}
		return Constants.MESSAGE_RETRIEVE_PASS;
	}

	private String undo() {
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

//	private Task taskFinder(ArrayList<Task> taskArray, Task task) {
//		for (Task eachTask : handlerMemory) {
//			if (Task.taskNameComparator.compare(eachTask, task) == 0
//					&& Task.taskStarttimeComparator.compare(eachTask, task) == 0
//					&& Task.taskEndtimeComparator.compare(eachTask, task) == 0
//					&& Task.taskDateComparator.compare(eachTask, task) == 0
//					&& Task.taskDetailsComparator.compare(eachTask, task) == 0) {
//				return eachTask;
//			}
//		}
//		return null;
//	}

	private String displayFormat(ArrayList<Task> sortedList) {
		String output = "";
		int counter = 1;
		for (Task t : sortedList) {
			output += "<tr><td align=\"right\">" + counter + ")</td>" + "<td> Event: </td><td>" + t.getName() + "</td></tr>";
			if (t.getDate() != null) {
				output += "<tr padding-top=0><td></td><td>" + "Date: </td><td>" + t.getDate() + "</td></tr>";
			}
			if (t.getStartTime() != null) {
				output += "<tr><td></td><td>" + "StartTime: </td><td>" + t.getStartTime() + "</td></tr>";
			}
			if (t.getEndTime() != null) {
				output += "<tr><td></td><td>" + "EndTime: </td><td>" + t.getEndTime() + "</td></tr>";
			}
			if (t.getDetails() != null) {
				output += "<tr><td></td><td>" + "Details: </td><td>" + t.getDetails() + "</td></tr>";
			}
			counter++;
		}

		return output;
	}
}
