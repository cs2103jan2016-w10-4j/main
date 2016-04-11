//@@author A0135779M

package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;

import main.Task;

import org.junit.Test;


import Handler.ArraylistStorage;
import Handler.Delete;
public class DeleteTest {
	
	@Before
	public void Before() {
		try {
			File f = new File("mytextfile.txt");
			f.delete();
			f = new File("a.txt");
			f.delete();
		} catch (Exception e) {
		}
	}

	@After
	public void After() {
		try {
			File f = new File("mytextfile.txt");
			f.delete();
			f = new File("a.txt");
			f.delete();
		} catch (Exception e) {
		}
	}
	
	private ArraylistStorage arraylistStorage = new ArraylistStorage();
	private Delete deleteTask = new Delete(arraylistStorage);

	@Test
	public void test() {
		// create sample task
		Task firstTask = new Task("sampleTask1");
		firstTask.setStartDate("2016/03/04");
		firstTask.setStartTime("12:00");
		firstTask.setEndTime("16:00");
		firstTask.setDetails("None");

		Task secondTask = new Task("sampleTask2");
		secondTask.setStartDate("2014/01/05");
		secondTask.setStartTime("10:00");
		secondTask.setEndTime("14:00");
		secondTask.setDetails("Nil");
		
		// create string commands, take note of the sequential order of execution as the task IDs will be updated
		String[] input1 = {"1"};
		String[] input2 = {"1"};
		String[] input3 = {"2"};
		
		arraylistStorage.getNotDoneStorage().add(firstTask);
		arraylistStorage.getNotDoneStorage().add(secondTask);

		// test the delete method
		assertEquals("1sampleTask1 has been deleted.", deleteTask.execute(input1));
		assertEquals("1sampleTask2 has been deleted.", deleteTask.execute(input2));
		assertEquals("2Task cannot be deleted.", deleteTask.execute(input3));
	}
}
