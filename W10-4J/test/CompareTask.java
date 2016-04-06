/*
 * Extra class needed for ReadTest.java, SetDirectoryTest.java
 * @@author A0126129J
 */
package test;

import java.util.ArrayList;

import main.Task;

public class CompareTask {
	public boolean checkTask(ArrayList<Task> readFromFileTaskList, ArrayList<Task> taskList) {
		boolean isSameTaskList = true;
		if (readFromFileTaskList.size() == taskList.size()) {
			for (int i = 0; i < readFromFileTaskList.size(); i++) {
				Task readFromFileToDoTask = readFromFileTaskList.get(i);
				Task task = taskList.get(i);
				if (compareTaskDetails(readFromFileToDoTask, task) == false) {
					isSameTaskList = false;
					break;
				}
			}
		} else {
			isSameTaskList = false;
		}
		return isSameTaskList;
	}
	
	private boolean compareTaskDetails(Task readFromFileTask, Task task) {		
		boolean isSameName = compareName(readFromFileTask, task);
		boolean isSameStartDate = compareStartDate(readFromFileTask, task);
		boolean isSameEndDate = compareEndDate(readFromFileTask, task);
		boolean isSameStartTime = compareStartTime(readFromFileTask, task);
		boolean isSameEndTime = compareEndTime(readFromFileTask, task);
		boolean isSameDetails = compareDetails(readFromFileTask, task);
		boolean isSameDay = compareDay(readFromFileTask, task);
		boolean isSameWeek = compareWeek(readFromFileTask, task);
		boolean isSameMonth = compareMonth(readFromFileTask, task);
		boolean isSameYear = compareYear(readFromFileTask, task);

		if (isSameName && isSameStartDate && isSameEndDate && isSameStartTime && isSameEndTime && isSameDetails && isSameDay && isSameWeek && isSameMonth && isSameYear) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean compareName(Task readFromFileTask, Task task) {
		if (readFromFileTask.getName() == null && task.getName() == null) {
			return true;
		} else if (readFromFileTask.getName() != null && task.getName() == null) {
			return false;
		} else if (readFromFileTask.getName() == null && task.getName() != null) {
			return false;
		} else if (readFromFileTask.getName().equals(task.getName())) { 
			return true;
		} else {
			return false;
		}
	}
	
	private boolean compareStartDate(Task readFromFileTask, Task task) {
		if (readFromFileTask.getStartDate() == null && task.getStartDate() == null) {
			return true;
		} else if (readFromFileTask.getStartDate() != null && task.getStartDate() == null) {
			return false;
		} else if (readFromFileTask.getStartDate() == null && task.getStartDate() != null) {
			return false;
		} else if (readFromFileTask.getStartDate().equals(task.getStartDate())) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean compareEndDate(Task readFromFileTask, Task task) {
		if (readFromFileTask.getEndDate() == null && task.getEndDate() == null) {
			return true;
		} else if (readFromFileTask.getEndDate() != null && task.getEndDate() == null) {
			return false;
		} else if (readFromFileTask.getEndDate() == null && task.getEndDate() != null) {
			return false;
		} else if (readFromFileTask.getEndDate().equals(task.getEndDate())) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean compareStartTime(Task readFromFileTask, Task task) {
		if (readFromFileTask.getStartTime() == null && task.getStartTime() == null) {
			return true;
		} else if (readFromFileTask.getStartTime() != null && task.getStartTime() == null) {
			return false;
		} else if (readFromFileTask.getStartTime() == null && task.getStartTime() != null) {
			return false;
		} else if (readFromFileTask.getStartTime().equals(task.getStartTime())) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean compareEndTime(Task readFromFileTask, Task task) {
		if (readFromFileTask.getEndTime() == null && task.getEndTime() == null) {
			return true;
		} else if (readFromFileTask.getEndTime() != null && task.getEndTime() == null) {
			return false;
		} else if (readFromFileTask.getEndTime() == null && task.getEndTime() != null) {
			return false;
		} else if (readFromFileTask.getEndTime().equals(task.getEndTime())) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean compareDetails(Task readFromFileTask, Task task) {
		if (readFromFileTask.getDetails() == null && task.getDetails() == null) {
			return true;
		} else if (readFromFileTask.getDetails() != null && task.getDetails() == null) {
			return false;
		} else if (readFromFileTask.getDetails() == null && task.getDetails() != null) {
			return false;
		} else if (readFromFileTask.getDetails().equals(task.getDetails())) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean compareDay(Task readFromFileTask, Task task) {
		if (readFromFileTask.getDay() == false && task.getDay() == false) {
			return true;
		} else if (readFromFileTask.getDay() == false && task.getDay() == true) {
			return false;
		} else if (readFromFileTask.getDay() == true && task.getDay() == false) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean compareWeek(Task readFromFileTask, Task task) {
		if (readFromFileTask.getWeek() == false && task.getWeek() == false) {
			return true;
		} else if (readFromFileTask.getWeek() == false && task.getWeek() == true) {
			return false;
		} else if (readFromFileTask.getWeek() == true && task.getWeek() == false) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean compareMonth(Task readFromFileTask, Task task) {
		if (readFromFileTask.getMonth() == false && task.getMonth() == false) {
			return true;
		} else if (readFromFileTask.getMonth() == false && task.getMonth() == true) {
			return false;
		} else if (readFromFileTask.getMonth() == true && task.getMonth() == false) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean compareYear(Task readFromFileTask, Task task) {
		if (readFromFileTask.getYear() == false && task.getYear() == false) {
			return true;
		} else if (readFromFileTask.getYear() == false && task.getYear() == true) {
			return false;
		}  else if (readFromFileTask.getYear() == true && task.getYear() == false) {
			return false;
		} else {
			return true;
		}
	}
}