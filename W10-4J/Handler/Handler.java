package Handler;

import main.*;
import java.util.ArrayList;

import Storage.Storage;
import main.Constants.COMMAND_TYPE;

public class Handler {
	private static ArrayList<Task> handlerMemory;
	private static ArrayList<Task> doneStorage;
	private static ArrayList<PreviousInput> previousInputStorage;
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
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read();
		handlerMemory = getFromStorage.get(0);
		doneStorage = getFromStorage.get(1);
		previousInputStorage = new ArrayList<PreviousInput>();

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
			return taskAdder.add(task);
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
		Task eachTask = handlerMemory.get(taskID - 1);
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

	private String help(String[] task) {
		if (task.length == 0) {
			return help.helpFullString();
		} else {
			return help.helpSpecific(task[0]);
		}
	}
}
