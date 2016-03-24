package Storage;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import main.Task;

public class ReadTest extends Read {
	static ArrayList<Task> toDoTaskList = new ArrayList<Task> ();
	static ArrayList<Task> doneTaskList = new ArrayList<Task> ();
	static ArrayList<ArrayList<Task>> taskList = new ArrayList<ArrayList<Task>> ();
	Write write = new Write();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		write.writeToFile(toDoTaskList, doneTaskList);
		ArrayList<ArrayList<Task>> returnTaskList = readFromFile();
		boolean checkValue = checkIfEquals(returnTaskList);
		assertEquals(true, checkValue);
	}
	
	@Test
	public void testReadFromGivenFile() throws FileNotFoundException {
		write.writeToFile(taskList.get(0), taskList.get(1));
		BufferedReader reader = new BufferedReader(new FileReader(Storage.filename));		
		ArrayList<ArrayList<Task>> returnTaskList = readFromFile(reader);
		boolean returnValue = checkIfEquals(returnTaskList);
		assertEquals(true, returnValue);
	}

	public boolean checkIfEquals (ArrayList<ArrayList<Task>> returnTaskList) {
		boolean returnValue = true;

		if(returnTaskList.get(0).size() == toDoTaskList.size()) {
			for(int i = 0; i < returnTaskList.get(0).size(); i++) {
				Task returnTask = returnTaskList.get(0).get(i);
				Task task = toDoTaskList.get(i);
				if(getTaskDetails(returnTask,task) == false) {
					returnValue = false;
					break;
				}
			}
		} else if (returnTaskList.get(1).size() == doneTaskList.size()) {
			for(int i = 0; i < returnTaskList.get(0).size(); i++) {
				Task returnTask = returnTaskList.get(0).get(i);
				Task task = doneTaskList.get(i);
				if(getTaskDetails(returnTask,task) == false) {
					returnValue = false;
					break;
				}
			}
		} else {
			returnValue = false;
		}
		return returnValue;
	}

	private boolean getTaskDetails(Task returnTask, Task task) {		
		int counter = 0;

		if (!(returnTask.getName() == null && task.getName() == null)) {
			if(returnTask.getName().trim().equals(task.getName().trim())) {
				counter++;
			}
		} else if (returnTask.getName() == null && task.getName() == null) {
			counter++;
		}
		
		if (!(returnTask.getDate() == null && task.getDate() == null)) {
			if(returnTask.getDate().trim().equals(task.getDate().trim())) {
				counter++;
			}			
		} else if (returnTask.getDate() == null && task.getDate() == null) {
			counter++;
		}

		if (!(returnTask.getStartTime() == null && task.getStartTime() == null)) {
			if(returnTask.getStartTime().trim().equals(task.getStartTime().trim())) {
				counter++;
			}				
		} else if (returnTask.getStartTime() == null && task.getStartTime() == null) {
			counter++;
		}

		if (!(returnTask.getEndTime() == null && task.getEndTime() == null)) {
			if(returnTask.getEndTime().trim().equals(task.getEndTime().trim())) {
				counter++;
			}
		} else if (returnTask.getEndTime() == null && task.getEndTime() == null) {
			counter++;
		}

		if (!(returnTask.getDetails() == null && task.getDetails() == null)) {
			if(returnTask.getDetails().trim().equals(task.getDetails().trim())) {
				counter++;
			}			
		} else if (returnTask.getDetails() == null && task.getDetails() == null) {
			counter++;
		}

		if (!(returnTask.getDay() == false && task.getDay() == false)) {
			if(returnTask.getDay() == (task.getDay())) {
				counter++;
			}			
		} else if (returnTask.getDay() == false && task.getDay() == false) {
			counter++;
		}

		if (!(returnTask.getWeek() == false && task.getWeek() == false)) {
			if(returnTask.getWeek() == (task.getWeek())) {
				counter++;
			}				
		} else if (returnTask.getWeek() == false && task.getWeek() == false) {
			counter++;
		}

		if (!(returnTask.getMonth() == false && task.getMonth() == false)) {
			if(returnTask.getMonth() == (task.getMonth())) {
				counter++;
			}				
		} else if (returnTask.getMonth() == false && task.getMonth() == false) {
			counter++;
		}

		if (!(returnTask.getYear() == false && task.getYear() == false)) {
			if(returnTask.getYear() == (task.getYear())) {
				counter++;
			}				
		} else if (returnTask.getYear() == false && task.getYear() == false) {
			counter++;
		}

		if(counter == 9) {
			return true;
		} else {
			return false;
		}
	}
}
