/*
 * JUnit test for SetDirectory class
 * @@author A0126129J
 */
package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.Test;

import Storage.Read;
import Storage.SetDirectory;
import main.Task;

public class SetDirectoryTest {
	CompareTask compareTask = new CompareTask();
	SetDirectory taskSetDirectory = SetDirectory.getInstance();
	Read taskReader = Read.getInstance();

	@Test
	public void testDirectoryExists() throws FileNotFoundException {
		boolean isSameTaskList;
		ArrayList<ArrayList<Task>> taskList = taskSetDirectory.setDirectory("mytextfile.txt");
		BufferedReader reader = new BufferedReader(new FileReader("mytextfile.txt"));		
		ArrayList<ArrayList<Task>> readFromFileTaskList = taskReader.readFromFile(reader);
		
		ArrayList<Task> toDoTaskList = taskList.get(0);
		ArrayList<Task> doneTaskList = taskList.get(1);
		ArrayList<Task> readFromFileToDoTaskList = readFromFileTaskList.get(0);
		ArrayList<Task> readFromFileDoneTaskList = readFromFileTaskList.get(1);
		
		boolean isSameToDoList = compareTask.checkTask(readFromFileToDoTaskList, toDoTaskList);
		boolean isSameDoneList = compareTask.checkTask(readFromFileDoneTaskList, doneTaskList);
		if(isSameToDoList && isSameDoneList) {
			isSameTaskList = true;
		} else {
			isSameTaskList = false;
		}
		assertEquals(true, isSameTaskList);
	}
	
	@Test
	public void testDirectoryDoesNotExists() {
		ArrayList<ArrayList<Task>> taskList = taskSetDirectory.setDirectory("C:/Users/NoSuchFile/noSuchFile.txt");
		assertEquals(null, taskList);
	}
}