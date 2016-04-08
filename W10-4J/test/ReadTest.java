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

import org.junit.Before;
import org.junit.Test;

import Storage.Read;
import Storage.Write;
import Storage.Storage;
import main.Task;

public class ReadTest {
	ArrayList<Task> toDoTaskList = new ArrayList<Task> ();
	ArrayList<Task> doneTaskList = new ArrayList<Task> ();
	ArrayList<ArrayList<Task>> taskList = new ArrayList<ArrayList<Task>> ();
	CompareTask compareTask = new CompareTask();
	Write taskWriter = Write.getInstance();
	Read taskReader = Read.getInstance();
	
	@Before
	public void setUpBefore() {
		Task taskOne = new Task("Task 1");
		taskOne.setStartDate("1/4/2016");
		
		Task taskTwo = new Task("Task 2");
		taskTwo.setStartDate("2/4/2016");
		taskTwo.setEndDate("2/4/2016");
		
		Task taskThree = new Task("Task 3");		
		taskThree.setStartDate("2/4/2016");
		taskThree.setEndDate("5/4/2016");
		taskThree.setStartTime("14:00");
		taskThree.setEndTime("18:00");
		
		Task taskFour = new Task("Task 4");
		taskFour.setStartDate("2/4/2016");
		taskFour.setWeek(true);
		
		Task taskFive = new Task("Task 5");
		taskFive.setStartDate("2/4/2016");
		taskFive.setEndDate("5/4/2016");
		taskFive.setStartTime("15:00");
		taskFive.setEndTime("17:00");
		taskFive.setWeek(true);
		
		toDoTaskList.add(taskOne);
		toDoTaskList.add(taskTwo);
		toDoTaskList.add(taskThree);
		toDoTaskList.add(taskFour);
		doneTaskList.add(taskFive);
		taskList.add(toDoTaskList);
		taskList.add(doneTaskList);
	}

	@Test
	public void testReadDefaultFile() {
		taskWriter.writeToFile(toDoTaskList, doneTaskList);
		ArrayList<ArrayList<Task>> readFromFileTaskList = taskReader.readFromFile();
		boolean isSameTaskList = checkIfTaskListEquals(readFromFileTaskList);
		assertEquals(true, isSameTaskList);
	}
	
	@Test
	public void testReadFromGivenFile() throws FileNotFoundException {
		taskWriter.writeToFile(taskList.get(0), taskList.get(1));
		BufferedReader reader = new BufferedReader(new FileReader(Storage.filename));		
		ArrayList<ArrayList<Task>> readFromFileTaskList = taskReader.readFromFile(reader);
		boolean isSameTaskList = checkIfTaskListEquals(readFromFileTaskList);
		assertEquals(true, isSameTaskList);
	}
	
	@Test
	public void testReadFromInvalidFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("doesNotExistFile.txt"));		
			ArrayList<ArrayList<Task>> readFromFileTaskList = taskReader.readFromFile(reader);
			assertEquals(null, readFromFileTaskList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean checkIfTaskListEquals(ArrayList<ArrayList<Task>> readFromFileTaskList) {
		boolean isSameTaskList;
		ArrayList<Task> readFromFileToDoTaskList = readFromFileTaskList.get(0);
		ArrayList<Task> readFromFileDoneTaskList = readFromFileTaskList.get(1);
		
		boolean isSameToDoList = compareTask.checkTask(readFromFileToDoTaskList, toDoTaskList);
		boolean isSameDoneList = compareTask.checkTask(readFromFileDoneTaskList, doneTaskList);
		if(isSameToDoList && isSameDoneList) {
			isSameTaskList = true;
		} else {
			isSameTaskList = false;
		}
		
		return isSameTaskList;
	}
}