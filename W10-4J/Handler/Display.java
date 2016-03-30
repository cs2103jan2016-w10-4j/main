package Handler;

import java.util.ArrayList;
import java.util.Collections;

import Storage.Storage;
import main.Constants;
import main.Task;

public class Display implements Command{
	private ArrayList<Task> notDoneYetStorage;
	private ArrayList<Task> doneStorage;
	Storage mainStorage;
	
	public Display(ArrayList<Task> notDoneYetStorage, ArrayList<Task> doneStorage, Storage mainStorage){
		this.notDoneYetStorage = notDoneYetStorage;
		this.doneStorage = doneStorage;
		this.mainStorage = mainStorage;
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		if(task.length==0){
			sortByID(notDoneYetStorage);
			mainStorage.write(notDoneYetStorage, doneStorage);
		}else{
			String displayField = task[1].trim();
			assert displayField != null: Constants.ASSERT_FIELD_EXISTENCE;
			//ArrayList<Task> clonenotDoneYetStorage = cloneArray(notDoneYetStorage);
			switch (displayField)
			{
				case Constants.MESSAGE_DISPLAY_FIELD_NAME:
					sortByName(notDoneYetStorage);
					mainStorage.write(notDoneYetStorage, doneStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_START:
					sortByStart(notDoneYetStorage);
					mainStorage.write(notDoneYetStorage, doneStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_END:
					sortByEnd(notDoneYetStorage);
					mainStorage.write(notDoneYetStorage, doneStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_DATE:
					sortByDate(notDoneYetStorage);
					mainStorage.write(notDoneYetStorage, doneStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_TASKS:
					return DisplayFormat.displayFormat(notDoneYetStorage);
				case Constants.MESSAGE_DISPLAY_FIELD_DONE:
					return DisplayFormat.displayFormat(doneStorage);
			}
		}
		return DisplayFormat.displayFormat(notDoneYetStorage);
	}

	// modularise the display code
	private void sortByID(ArrayList<Task> clonenotDoneYetStorage){
		Collections.sort(clonenotDoneYetStorage, Task.taskIDComparator);
	}
	private void sortByName(ArrayList<Task> clonenotDoneYetStorage){
		Collections.sort(clonenotDoneYetStorage, Task.taskNameComparator);
	}
	private void sortByStart(ArrayList<Task> clonenotDoneYetStorage){
		ArrayList<Task> exclusivenotDoneYetStorage = separateArrayList(clonenotDoneYetStorage, Constants.MESSAGE_DISPLAY_FIELD_START);
		Collections.sort(clonenotDoneYetStorage, Task.taskStarttimeComparator);
		if (exclusivenotDoneYetStorage != null){
			clonenotDoneYetStorage.addAll(exclusivenotDoneYetStorage);
		}
	}
	private void sortByEnd(ArrayList<Task> clonenotDoneYetStorage){
		ArrayList<Task> exclusivenotDoneYetStorage = separateArrayList(clonenotDoneYetStorage, Constants.MESSAGE_DISPLAY_FIELD_END);
		Collections.sort(clonenotDoneYetStorage, Task.taskEndtimeComparator);
		if (exclusivenotDoneYetStorage != null){
			clonenotDoneYetStorage.addAll(exclusivenotDoneYetStorage);
		}
	}
	private void sortByDate(ArrayList<Task> clonenotDoneYetStorage){
		ArrayList<Task> exclusivenotDoneYetStorage = separateArrayList(clonenotDoneYetStorage, Constants.MESSAGE_DISPLAY_FIELD_DATE);
		Collections.sort(clonenotDoneYetStorage, Task.taskDateComparator);
		if (exclusivenotDoneYetStorage != null){
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
				if (task.getStartDate() == null) {
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
