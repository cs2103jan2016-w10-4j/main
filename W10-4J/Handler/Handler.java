package Handler;

import main.*;
import java.util.ArrayList;

import Storage.Storage;
import main.Constants.COMMAND_TYPE;

public class Handler {
	private static ArrayList<Task> handlerMemory;
	private static ArrayList<Task> doneStorage;
	private static ArrayList<PreviousInput> previousInputStorage;
	private int taskID;
	Storage mainStorage = new Storage();
	Add taskAdder;
	Edit taskEditor;
	Delete taskDeleter;
	Done taskDoner;
	Display taskDisplayer;
	Search taskSearcher;
	Retrieve taskRetriever;
	Undo taskUndoer;
	Help help = new Help();

	public Handler() {
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read("Read", Constants.fileName);
		handlerMemory = getFromStorage.get(0);
		doneStorage = getFromStorage.get(1);
		previousInputStorage = new ArrayList<PreviousInput>();
		taskID = getTaskId();

		taskAdder = new Add(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		taskEditor = new Edit(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		taskDeleter = new Delete(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		taskDoner = new Done(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		taskDisplayer = new Display(handlerMemory, doneStorage, mainStorage);
		taskSearcher = new Search(handlerMemory, mainStorage);
		taskRetriever = new Retrieve(handlerMemory, doneStorage, mainStorage);
		taskUndoer = new Undo(handlerMemory, doneStorage, previousInputStorage, mainStorage);
	}

	public String executeCommand(COMMAND_TYPE command, String[] task) {

		switch (command) {
		case ADD:
			return taskAdder.add(task, taskID++);
		case EDIT:
			return taskEditor.edit(task);
		case DELETE:
			return taskDeleter.delete(task);
		case DONE:
			return taskDoner.done(task);
		case DISPLAY:
			return taskDisplayer.display(task);
		case SEARCH:
			return taskSearcher.search(task);
		case SETDIR:
			return setdir(task);
		case RETRIEVE:
			return taskRetriever.retrieve(task);
		case RECURRENCE:
			return recurrence(task);
		case UNDO:
			return taskUndoer.undo();
		case EXIT:
			System.exit(0);
		case HELP:
			return help(task);
		case INVALID:
			return String.format(Constants.MESSAGE_INVALID_FORMAT);
		default:
			throw new Error("Unrecognized command type");
		}
	}
	
	public String recurrence(String[] task) {
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
	
	private String setdir(String[] task){
  		if(mainStorage.setDirectory(task[0])){
  			handlerMemory.clear();
  			doneStorage.clear();
  			previousInputStorage.clear();
  			return Constants.MESSAGE_SETDIR_PASS;
  		} else{
  			return Constants.MESSAGE_SETDIR_FAIL;
  		}
  	}
	
	private String help(String[] task) {
		if (task.length == 0) {
			return help.helpFullString();
		} else {
			return help.helpSpecific(task[0]);
		}
	}
	
	private int getTaskId(){
		int id = 0;
		for(int i = 0 ;i <handlerMemory.size();i++){
			int currentId = handlerMemory.get(i).getTaskID();
			if(currentId>id){
				id = currentId;
			}
		}
		for(int i = 0 ;i <doneStorage.size();i++){
			int currentId = doneStorage.get(i).getTaskID();
			if(currentId>id){
				id = currentId;
			}
		}
		return ++id;
	}
}
