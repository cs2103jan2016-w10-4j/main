package Handler;

import java.util.ArrayList;
import java.util.Collections;

import Storage.Storage;
import main.Task;

public class Display {
	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	Storage mainStorage;
	
	public Display(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage, Storage mainStorage){
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.mainStorage = mainStorage;
	}
	public String display(String[] task) {
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
