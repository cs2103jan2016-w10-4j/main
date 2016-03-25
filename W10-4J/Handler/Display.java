package Handler;

import java.util.ArrayList;
import java.util.Collections;

import Storage.Storage;
import main.Constants;
import main.Task;

public class Display implements Command {
	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	Storage mainStorage;

	public Display(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage, Storage mainStorage) {
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.mainStorage = mainStorage;
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		if (task.length == 0) {
			sortByID(handlerMemory);
			mainStorage.write(handlerMemory, doneStorage);
		} else {
			String displayField = task[1].trim();
			assert displayField != null : Constants.ASSERT_FIELD_EXISTENCE;
			// ArrayList<Task> cloneHandlerMemory = cloneArray(handlerMemory);
			switch (displayField) {
			case Constants.MESSAGE_DISPLAY_FIELD_NAME:
				sortByName(handlerMemory);
				mainStorage.write(handlerMemory, doneStorage);
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_START:
				sortByStart(handlerMemory);
				mainStorage.write(handlerMemory, doneStorage);
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_END:
				sortByEnd(handlerMemory);
				mainStorage.write(handlerMemory, doneStorage);
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_DATE:
				sortByDate(handlerMemory);
				mainStorage.write(handlerMemory, doneStorage);
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_TASKS:
				return DisplayFormat.displayFormat(handlerMemory);
			case Constants.MESSAGE_DISPLAY_FIELD_DONE:
				return DisplayFormat.displayFormat(doneStorage);
			}
		}
		return DisplayFormat.displayFormat(handlerMemory);
	}

	// modularise the display code
	private void sortByID(ArrayList<Task> cloneHandlerMemory) {
		Collections.sort(cloneHandlerMemory, Task.taskIDComparator);
	}

	private void sortByName(ArrayList<Task> cloneHandlerMemory) {
		Collections.sort(cloneHandlerMemory, Task.taskNameComparator);
	}

	private void sortByStart(ArrayList<Task> cloneHandlerMemory) {
		ArrayList<Task> exclusiveHandlerMemory = separateArrayList(cloneHandlerMemory,
				Constants.MESSAGE_DISPLAY_FIELD_START);
		Collections.sort(cloneHandlerMemory, Task.taskStarttimeComparator);
		if (exclusiveHandlerMemory != null) {
			cloneHandlerMemory.addAll(exclusiveHandlerMemory);
		}
	}

	private void sortByEnd(ArrayList<Task> cloneHandlerMemory) {
		ArrayList<Task> exclusiveHandlerMemory = separateArrayList(cloneHandlerMemory,
				Constants.MESSAGE_DISPLAY_FIELD_END);
		Collections.sort(cloneHandlerMemory, Task.taskEndtimeComparator);
		if (exclusiveHandlerMemory != null) {
			cloneHandlerMemory.addAll(exclusiveHandlerMemory);
		}
	}

	private void sortByDate(ArrayList<Task> cloneHandlerMemory) {
		ArrayList<Task> exclusiveHandlerMemory = separateArrayList(cloneHandlerMemory,
				Constants.MESSAGE_DISPLAY_FIELD_DATE);
		Collections.sort(cloneHandlerMemory, Task.taskDateComparator);
		if (exclusiveHandlerMemory != null) {
			cloneHandlerMemory.addAll(exclusiveHandlerMemory);
		}
	}

	// separate those tasks with the specific parameters and those that dont
	// have in null list called result
	private ArrayList<Task> separateArrayList(ArrayList<Task> taskArray, String field) {
		// ArrayList<Task> separateArray = new ArrayList<Task>();
		ArrayList<Task> result = exclusiveSeparation(taskArray, field);
		// edit the clone to remove the excluded tasks
		for (Task task : result) {
			if (taskArray.contains(task)) {
				taskArray.remove(task);
			}
		}
		return result;
	}

	private ArrayList<Task> exclusiveSeparation(ArrayList<Task> taskArray, String field) {
		ArrayList<Task> result = new ArrayList<Task>();
		switch (field) {
		case Constants.MESSAGE_DISPLAY_FIELD_START:
			for (Task task : taskArray) {
				if (task.getStartTime() == null) {
					result.add(task);
				}
			}
			break;
		case Constants.MESSAGE_DISPLAY_FIELD_END:
			for (Task task : taskArray) {
				if (task.getEndTime() == null) {
					result.add(task);
				}
			}
			break;
		case Constants.MESSAGE_DISPLAY_FIELD_DATE:
			for (Task task : taskArray) {
				if (task.getDate() == null) {
					result.add(task);
				}
			}
			break;
		}
		return result;
	}

	// private ArrayList<Task> cloneArray(ArrayList<Task> taskArray) {
	// ArrayList<Task> clone = new ArrayList<Task>(taskArray.size());
	// for (Task task : taskArray) {
	// clone.add(task);
	// }
	// return clone;
	// }
}
