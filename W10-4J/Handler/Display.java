package Handler;

import java.util.ArrayList;
import java.util.Collections;

import main.Constants;
import main.Task;
import main.Constants.COMMAND_STATE;

public class Display implements Command {

	private HandlerMemory handlerMemory;
	private Task forEachTask;
	private Task forOldTask;
	private COMMAND_STATE commandState;

	public Display(HandlerMemory handlerMemory) {
		this.handlerMemory = handlerMemory;
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		if (task.length == 0) {
			sortByID(HandlerMemory.getNotDoneYetStorage());
			handlerMemory.getMainStorage().write(HandlerMemory.getNotDoneYetStorage(), HandlerMemory.getDoneStorage());
		} else {
			String displayField = task[1].trim();
			assert displayField != null : Constants.ASSERT_FIELD_EXISTENCE;
			// ArrayList<Task> clonenotDoneYetStorage =
			// cloneArray(notDoneYetStorage);
			switch (displayField) {
			case Constants.MESSAGE_DISPLAY_FIELD_NAME:
				sortByName(HandlerMemory.getNotDoneYetStorage());
				handlerMemory.getMainStorage().write(HandlerMemory.getNotDoneYetStorage(),
						HandlerMemory.getDoneStorage());
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_START:
				sortByStart(HandlerMemory.getNotDoneYetStorage());
				handlerMemory.getMainStorage().write(HandlerMemory.getNotDoneYetStorage(),
						HandlerMemory.getDoneStorage());
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_END:
				sortByEnd(HandlerMemory.getNotDoneYetStorage());
				handlerMemory.getMainStorage().write(HandlerMemory.getNotDoneYetStorage(),
						HandlerMemory.getDoneStorage());
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_DATE:
				sortByDate(HandlerMemory.getNotDoneYetStorage());
				handlerMemory.getMainStorage().write(HandlerMemory.getNotDoneYetStorage(),
						HandlerMemory.getDoneStorage());
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_TASKS:
				return DisplayFormat.displayFormat(HandlerMemory.getNotDoneYetStorage());
			case Constants.MESSAGE_DISPLAY_FIELD_DONE:
				return DisplayFormat.displayFormat(HandlerMemory.getDoneStorage());
			}
		}
		return DisplayFormat.displayFormat(HandlerMemory.getNotDoneYetStorage());
	}

	// modularise the display code
	private void sortByID(ArrayList<Task> clonenotDoneYetStorage) {
		Collections.sort(clonenotDoneYetStorage, Task.taskIDComparator);
	}

	private void sortByName(ArrayList<Task> clonenotDoneYetStorage) {
		Collections.sort(clonenotDoneYetStorage, Task.taskNameComparator);
	}

	private void sortByStart(ArrayList<Task> clonenotDoneYetStorage) {
		ArrayList<Task> exclusivenotDoneYetStorage = separateArrayList(clonenotDoneYetStorage,
				Constants.MESSAGE_DISPLAY_FIELD_START);
		Collections.sort(clonenotDoneYetStorage, Task.taskStarttimeComparator);
		if (exclusivenotDoneYetStorage != null) {
			clonenotDoneYetStorage.addAll(exclusivenotDoneYetStorage);
		}
	}

	private void sortByEnd(ArrayList<Task> clonenotDoneYetStorage) {
		ArrayList<Task> exclusivenotDoneYetStorage = separateArrayList(clonenotDoneYetStorage,
				Constants.MESSAGE_DISPLAY_FIELD_END);
		Collections.sort(clonenotDoneYetStorage, Task.taskEndtimeComparator);
		if (exclusivenotDoneYetStorage != null) {
			clonenotDoneYetStorage.addAll(exclusivenotDoneYetStorage);
		}
	}

	private void sortByDate(ArrayList<Task> clonenotDoneYetStorage) {
		ArrayList<Task> exclusivenotDoneYetStorage = separateArrayList(clonenotDoneYetStorage,
				Constants.MESSAGE_DISPLAY_FIELD_DATE);
		Collections.sort(clonenotDoneYetStorage, Task.taskDateComparator);
		if (exclusivenotDoneYetStorage != null) {
			clonenotDoneYetStorage.addAll(exclusivenotDoneYetStorage);
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

	public Task returnEachTask() {
		return forEachTask;
	}

	public Task returnOldTask() {
		return forOldTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}
	// private ArrayList<Task> cloneArray(ArrayList<Task> taskArray) {
	// ArrayList<Task> clone = new ArrayList<Task>(taskArray.size());
	// for (Task task : taskArray) {
	// clone.add(task);
	// }
	// return clone;
	// }
}
