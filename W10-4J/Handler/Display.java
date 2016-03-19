package Handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
		int counter = 1;
		String output = "<table><tr style=\"border-bottom:1px solid #B6B6B4\"><th></th><th align=\"left\"> Event </th><th align=\"left\"> Date </th><th align=\"left\"> Start Time </th><th align=\"left\"> End Time </th><th align=\"left\"> Details </th><th align=\"left\"> Repeat </th></tr>";
		
		/*
		 * Red - Exceed the stipulated date and endtime
		 * Green - Have yet to exceed the stipulated date and endtime
		 * Black - Default color
		 */
		String red = "<font color=#ff0000>";
		String green = "<font color=#00FF00>";
		String black = "<font color=#000000>";
		String color = black;
		
		for (Task t : sortedList) {
			// Determine which color to display
			if(t.getDate() != null && t.getEndTime() == null) {
				Date date = new Date();
			    SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy/MM/dd");
			    
			    if(t.getDate().trim().compareTo(dateFormat.format(date)) < 0) {
					color = red;
				} else {
					color = green;
				}
			} else if(t.getEndTime() != null && t.getDate() != null) {
				Date time = new Date();
			    SimpleDateFormat timeFormat = new SimpleDateFormat ("HH:mm");
				
			    Date date = new Date();
			    SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy/MM/dd");
			    
			    if(t.getDate().trim().compareTo(dateFormat.format(date)) < 0) {
					color = red;
				} else if (t.getEndTime().trim().compareTo(timeFormat.format(time)) > 0 && t.getDate().compareTo(dateFormat.format(date)) == 0 ) {
					color = red;
				} else {
					color = green;
				} 
			}
			
			String repeat;
			if (t.getDay()){
				repeat = "Every Day";
			} else if (t.getMonth()){
				repeat = "Every Month";
			} else if (t.getWeek()){
				repeat = "Every Week";
			} else if (t.getYear()){
				repeat = "Every Year";
			} else {
				repeat = null;
			}
			
			output += "<tr style=\"border-bottom:1px solid #E5E4E2\"><td align=\"right\">" + color + counter + ")</font></td><td>" + color + t.getName() + "</font></td>";
			if (t.getDate() != null){
				output += "<td>" + color + t.getDate() + "</font></td>";
			} else {
				output += "<td></td>"; 
			}
			if (t.getStartTime() != null){
				output += "<td>" + color + t.getStartTime() + "</font></td>";
			} else {
				output += "<td></td>"; 
			}
			if (t.getEndTime() != null){
				output += "<td>" + color + t.getEndTime() + "</font></td>";
			} else {
				output += "<td></td>"; 
			}
			if (t.getDetails() != null){
				output += "<td>" + color + t.getDetails() + "</font></td>";
			} else {
				output += "<td></td>"; 
			}
			if (repeat != null){
				output += "<td>" + color + repeat + "</font></td>";
			}
			counter++;
		}

		return output;
	}
}
