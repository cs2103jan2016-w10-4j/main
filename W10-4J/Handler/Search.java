package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;

public class Search {
	private ArrayList<Task> handlerMemory;
	Storage mainStorage;
	
	public Search(ArrayList<Task> handlerMemory, Storage mainStorage){
		this.handlerMemory = handlerMemory;
		this.mainStorage = mainStorage;
	}
	public String search(String[] task) {
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
	// name field will always exist
	// for each task
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
	// for each task
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
