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
	Write taskWriter = Write.getInstance();
	Read taskReader = Read.getInstance();
	CompareTask compareTask = new CompareTask();
	
	@Before
	public void setUpBefore() {
		Task a = new Task("A");
		a.setStartDate("1/4/2016");
		
		Task b = new Task("B");
		b.setStartDate("2/4/2016");
		b.setEndDate("2/4/2016");
		
		Task c = new Task("C");		
		c.setStartDate("2/4/2016");
		c.setEndDate("5/4/2016");
		c.setStartTime("14:00");
		c.setEndTime("18:00");
		
		Task d = new Task("D");
		d.setStartDate("2/4/2016");
		d.setWeek(true);
		
		Task e = new Task("E");
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