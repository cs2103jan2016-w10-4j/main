package Handler;

import java.util.ArrayList;
import java.util.Collections;

import main.Constants;
import main.Task;

public class Sorting {
	public void sortByID(ArrayList<Task> clonenotDoneYetStorage){
		Collections.sort(clonenotDoneYetStorage, Task.taskIDComparator);
	}

	public void sortByName(ArrayList<Task> clonenotDoneYetStorage){
		Collections.sort(clonenotDoneYetStorage, Task.taskNameComparator);
	}

	public void sortByStartDate(ArrayList<Task> clonenotDoneYetStorage){
		ArrayList<Task> exclusivenotDoneYetStorage = separateArrayList(clonenotDoneYetStorage, Constants.MESSAGE_DISPLAY_FIELD_STARTDATE);
		Collections.sort(clonenotDoneYetStorage, Task.taskStartDateComparator);
		if (exclusivenotDoneYetStorage != null){
			clonenotDoneYetStorage.addAll(exclusivenotDoneYetStorage);
		}
	}

	public void sortByStartDateAndName(ArrayList<Task> clonenotDoneYetStorage){
		Collections.sort(clonenotDoneYetStorage, Task.taskStartDateAndNameComparator);
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
		case Constants.MESSAGE_DISPLAY_FIELD_STARTDATE:
			for (Task task : taskArray) {
				if (task.getStartDate() == null) {
					result.add(task);
				}
			}
			break;
			/*case Constants.MESSAGE_DISPLAY_FIELD_START:
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
			case Constants.MESSAGE_DISPLAY_FIELD_ENDDATE:
				for (Task task : taskArray) {
					if (task.getStartDate() == null) {
						result.add(task);
					}
				}
			break;*/
		}
		return result;
	}

	/*private void sortByStart(ArrayList<Task> clonenotDoneYetStorage){
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
	 
	private void sortByEndDate(ArrayList<Task> clonenotDoneYetStorage){
		ArrayList<Task> exclusivenotDoneYetStorage = separateArrayList(clonenotDoneYetStorage, Constants.MESSAGE_DISPLAY_FIELD_ENDDATE);
		Collections.sort(clonenotDoneYetStorage, Task.taskDateComparator);
		if (exclusivenotDoneYetStorage != null){
			clonenotDoneYetStorage.addAll(exclusivenotDoneYetStorage);
		}
	}
	*/
}