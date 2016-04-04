/*
 * JUnit test for Read class
 * @@author A0126129J
 */
package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import Storage.Read;
import Storage.Storage;
import Storage.Write;
import main.Task;

public class ReadTest extends Read {
	static ArrayList<Task> toDoTaskList = new ArrayList<Task> ();
	static ArrayList<Task> doneTaskList = new ArrayList<Task> ();
	static ArrayList<ArrayList<Task>> taskList = new ArrayList<ArrayList<Task>> ();
	Write taskWriter = Write.getInstance();
	
	@BeforeClass
	public static void setUpBeforeClass() {
		// Set up write class
		Task a = new Task("A");
		Task b = new Task("B");
		toDoTaskList.add(a);
		toDoTaskList.add(b);
		taskList.add(toDoTaskList);
		taskList.add(doneTaskList);
	}

	@Test
	public void testReadDefaultFile() {
		taskWriter.writeToFile(toDoTaskList, doneTaskList);
		ArrayList<ArrayList<Task>> returnTaskList = readFromFile();
		boolean checkValue = checkTaskListEquals(returnTaskList);
		assertEquals(true, checkValue);
	}
	
	@Test
	public void testReadFromGivenFile() throws FileNotFoundException {
		taskWriter.writeToFile(taskList.get(0), taskList.get(1));
		BufferedReader reader = new BufferedReader(new FileReader(Storage.filename));		
		ArrayList<ArrayList<Task>> returnTaskList = readFromFile(reader);
		boolean returnValue = checkTaskListEquals(returnTaskList);
		assertEquals(true, returnValue);
	}

	public boolean checkTaskListEquals(ArrayList<ArrayList<Task>> returnTaskList) {
		boolean returnValue = true;
		ArrayList<Task> returnToDoTaskList = returnTaskList.get(0);
		ArrayList<Task> returnDoneTaskList = returnTaskList.get(1);
		
		if (returnToDoTaskList.size() == toDoTaskList.size()) {
			for (int i = 0; i < returnToDoTaskList.size(); i++) {
				Task returnTask = returnToDoTaskList.get(i);
				Task task = toDoTaskList.get(i);
				if (compareTaskDetails(returnTask,task) == false) {
					returnValue = false;
					break;
				}
			}
		} else if (returnDoneTaskList.size() == doneTaskList.size()) {
			for (int i = 0; i < returnDoneTaskList.size(); i++) {
				Task returnTask = returnDoneTaskList.get(i);
				Task task = doneTaskList.get(i);
				if (compareTaskDetails(returnTask,task) == false) {
					returnValue = false;
					break;
				}
			}
		} else {
			returnValue = false;
		}
		
		return returnValue;
	}

	private boolean compareTaskDetails(Task returnTask, Task task) {		
		boolean nameEqual = compareName(returnTask, task);
		boolean dateEqual = compareDate(returnTask, task);
		boolean startTimeEqual = compareStartTime(returnTask, task);
		boolean endTimeEqual = compareEndTime(returnTask, task);
		boolean detailsEqual = compareDetails(returnTask, task);
		boolean dayEqual = compareDay(returnTask, task);
		boolean weekEqual = compareWeek(returnTask, task);
		boolean monthEqual = compareMonth(returnTask, task);
		boolean yearEqual = compareYear(returnTask, task);
		
		if(nameEqual && dateEqual && startTimeEqual && endTimeEqual && detailsEqual && dayEqual && weekEqual && monthEqual && yearEqual) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean compareName(Task returnTask, Task task) {
		if (!(returnTask.getName() == null && task.getName() == null)) {
			if (returnTask.getName().trim().equals(task.getName().trim())) {
				return true;
			}
		} else if (returnTask.getName() == null && task.getName() == null) {
			return true;
		}
		return false;
	}
	
	private boolean compareDate(Task returnTask, Task task) {
		if (!(returnTask.getStartDate() == null && task.getStartDate() == null)) {
			if (returnTask.getStartDate().trim().equals(task.getStartDate().trim())) {
				return true;			
			}			
		} else if (returnTask.getStartDate() == null && task.getStartDate() == null) {
			return true;
		}
		return false;
	}
	
	private boolean compareStartTime(Task returnTask, Task task) {
		if (!(returnTask.getStartTime() == null && task.getStartTime() == null)) {
			if (returnTask.getStartTime().trim().equals(task.getStartTime().trim())) {
				return true;
			}				
		} else if (returnTask.getStartTime() == null && task.getStartTime() == null) {
			return true;
		}
		return false;
	}
	
	private boolean compareEndTime(Task returnTask, Task task) {
		if (!(returnTask.getEndTime() == null && task.getEndTime() == null)) {
			if (returnTask.getEndTime().trim().equals(task.getEndTime().trim())) {
				return true;
			}
		} else if (returnTask.getEndTime() == null && task.getEndTime() == null) {
			return true;
		}
		return false;
	}
	
	private boolean compareDetails(Task returnTask, Task task) {
		if (!(returnTask.getDetails() == null && task.getDetails() == null)) {
			if (returnTask.getDetails().trim().equals(task.getDetails().trim())) {
				return true;
			}			
		} else if (returnTask.getDetails() == null && task.getDetails() == null) {
			return true;
		}
		return false;
	}
	
	private boolean compareDay(Task returnTask, Task task) {
		if (!(returnTask.getDay() == false && task.getDay() == false)) {
			if (returnTask.getDay() == (task.getDay())) {
				return true;
			}			
		} else if (returnTask.getDay() == false && task.getDay() == false) {
			return true;
		}
		return false;
	}
	
	private boolean compareWeek(Task returnTask, Task task) {
		if (!(returnTask.getWeek() == false && task.getWeek() == false)) {
			if (returnTask.getWeek() == (task.getWeek())) {
				return true;
			}				
		} else if (returnTask.getWeek() == false && task.getWeek() == false) {
			return true;
		}
		return false;
	}
	
	private boolean compareMonth(Task returnTask, Task task) {
		if (!(returnTask.getMonth() == false && task.getMonth() == false)) {
			if (returnTask.getMonth() == (task.getMonth())) {
				return true;
			}				
		} else if (returnTask.getMonth() == false && task.getMonth() == false) {
			return true;
		}
		return false;
	}
	
	private boolean compareYear(Task returnTask, Task task) {
		if (!(returnTask.getYear() == false && task.getYear() == false)) {
			if (returnTask.getYear() == (task.getYear())) {
				return true;
			}				
		} else if (returnTask.getYear() == false && task.getYear() == false) {
			return true;
		}
		return false;
	}
}