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
import Storage.Write;
import Storage.Storage;
import main.Task;

public class ReadTest extends Read {
	static ArrayList<Task> toDoTaskList = new ArrayList<Task> ();
	static ArrayList<Task> doneTaskList = new ArrayList<Task> ();
	static ArrayList<ArrayList<Task>> taskList = new ArrayList<ArrayList<Task>> ();
	Write taskWriter = Write.getInstance();
	
	@BeforeClass
	public static void setUpBeforeClass() {
		Task a = new Task("A");
		a.setTaskID(1);
		a.setStartDate("1/4/2016");
		
		Task b = new Task("B");
		b.setTaskID(2);
		b.setStartDate("2/4/2016");
		b.setEndDate("2/4/2016");
		
		Task c = new Task("C");		
		c.setTaskID(3);
		c.setStartDate("2/4/2016");
		c.setEndDate("5/4/2016");
		c.setStartTime("14:00");
		c.setEndTime("18:00");
		
		Task d = new Task("D");
		d.setTaskID(4);
		d.setStartDate("2/4/2016");
		d.setWeek(true);
		
		Task e = new Task("E");
		e.setTaskID(5);
		e.setStartDate("2/4/2016");
		e.setEndDate("5/4/2016");
		e.setStartTime("15:00");
		e.setEndTime("17:00");
		e.setWeek(true);
		
		toDoTaskList.add(a);
		toDoTaskList.add(b);
		toDoTaskList.add(c);
		toDoTaskList.add(d);
		doneTaskList.add(e);
		taskList.add(toDoTaskList);
		taskList.add(doneTaskList);
	}

	@Test
	public void testReadDefaultFile() {
		taskWriter.writeToFile(toDoTaskList, doneTaskList);
		ArrayList<ArrayList<Task>> readFromFileTaskList = readFromFile();
		boolean isSameTaskList = checkIfTaskListEquals(readFromFileTaskList);
		assertEquals(true, isSameTaskList);
	}
	
	@Test
	public void testReadFromGivenFile() throws FileNotFoundException {
		taskWriter.writeToFile(taskList.get(0), taskList.get(1));
		BufferedReader reader = new BufferedReader(new FileReader(Storage.filename));		
		ArrayList<ArrayList<Task>> readFromFileTaskList = readFromFile(reader);
		boolean isSameTaskList = checkIfTaskListEquals(readFromFileTaskList);
		assertEquals(true, isSameTaskList);
	}

	public boolean checkIfTaskListEquals(ArrayList<ArrayList<Task>> readFromFileTaskList) {
		boolean isSameTaskList = true;
		ArrayList<Task> readFromFileToDoTaskList = readFromFileTaskList.get(0);
		ArrayList<Task> readFromFileDoneTaskList = readFromFileTaskList.get(1);
		
		if (readFromFileToDoTaskList.size() == toDoTaskList.size()) {
			for (int i = 0; i < readFromFileToDoTaskList.size(); i++) {
				Task readFromFileToDoTask = readFromFileToDoTaskList.get(i);
				Task task = toDoTaskList.get(i);
				if (compareTaskDetails(readFromFileToDoTask, task) == false) {
					isSameTaskList = false;
					break;
				}
			}
		} else if (readFromFileDoneTaskList.size() == doneTaskList.size()) {
			for (int i = 0; i < readFromFileDoneTaskList.size(); i++) {
				Task readFromFileDoneTask = readFromFileDoneTaskList.get(i);
				Task task = doneTaskList.get(i);
				if (compareTaskDetails(readFromFileDoneTask, task) == false) {
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
		} else { 
			if (readFromFileTask.getName().equals(task.getName())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private boolean compareStartDate(Task readFromFileTask, Task task) {
		if (readFromFileTask.getStartDate() == null && task.getStartDate() == null) {
			return true;
		} else if (readFromFileTask.getStartDate() != null && task.getStartDate() == null) {
			return false;
		} else if (readFromFileTask.getStartDate() == null && task.getStartDate() != null) {
			return false;
		} else {
			if (readFromFileTask.getStartDate().equals(task.getStartDate())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private boolean compareEndDate(Task readFromFileTask, Task task) {
		if (readFromFileTask.getEndDate() == null && task.getEndDate() == null) {
			return true;
		} else if (readFromFileTask.getEndDate() != null && task.getEndDate() == null) {
			return false;
		} else if (readFromFileTask.getEndDate() == null && task.getEndDate() != null) {
			return false;
		} else {
			if (readFromFileTask.getEndDate().equals(task.getEndDate())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private boolean compareStartTime(Task readFromFileTask, Task task) {
		if (readFromFileTask.getStartTime() == null && task.getStartTime() == null) {
			return true;
		} else if (readFromFileTask.getStartTime() != null && task.getStartTime() == null) {
			return false;
		} else if (readFromFileTask.getStartTime() == null && task.getStartTime() != null) {
			return false;
		} else {
			if (readFromFileTask.getStartTime().equals(task.getStartTime())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private boolean compareEndTime(Task readFromFileTask, Task task) {
		if (readFromFileTask.getEndTime() == null && task.getEndTime() == null) {
			return true;
		} else if (readFromFileTask.getEndTime() != null && task.getEndTime() == null) {
			return false;
		} else if (readFromFileTask.getEndTime() == null && task.getEndTime() != null) {
			return false;
		} else {
			if (readFromFileTask.getEndTime().equals(task.getEndTime())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private boolean compareDetails(Task readFromFileTask, Task task) {
		if (readFromFileTask.getDetails() == null && task.getDetails() == null) {
			return true;
		} else if (readFromFileTask.getDetails() != null && task.getDetails() == null) {
			return false;
		} else if (readFromFileTask.getDetails() == null && task.getDetails() != null) {
			return false;
		} else {
			if (readFromFileTask.getDetails().equals(task.getDetails())) {
				return true;
			} else {
				return false;
			}
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