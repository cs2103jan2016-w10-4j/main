//@@author A0135779M

package test;

import static org.junit.Assert.*;
import main.Task;
import org.junit.Test;

import Handler.ArraylistStorage;
import Handler.Edit;

public class EditTest {
	
	private ArraylistStorage arraylistStorage = new ArraylistStorage();
	private Edit editTask = new Edit(arraylistStorage);

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

		Task editedFirstTask = new Task("SAMPLETASK1");
		editedFirstTask.setStartDate("2000/01/01");
		editedFirstTask.setStartTime("12:00");
		editedFirstTask.setEndTime("16:00");
		editedFirstTask.setDetails("None");

		Task editedSecondTask = new Task("sampleTask2");
		editedSecondTask.setStartDate("2014/01/05");
		editedSecondTask.setStartTime("03:00");
		editedSecondTask.setEndTime("06:00");
		editedSecondTask.setDetails("Nil");

		String[] sampleCommand1 = { "1", "rename", "SAMPLETASK1", "date", "2000/01/01" };
		String[] sampleCommand2 = { "2", "start", "03:00", "end", "06:00" };

		// Test cloneTask method
		checkFields(firstTask, editTask.cloneTask(firstTask));
		checkFields(secondTask, editTask.cloneTask(secondTask));
		
		// Test edit method
		arraylistStorage.getNotDoneStorage().add(firstTask);
		arraylistStorage.getNotDoneStorage().add(secondTask);
		editTask.execute(sampleCommand1);
		editTask.execute(sampleCommand2);
		
		// for string output of both commands
		assertEquals("testEdit1", "SAMPLETASK1 has been edited.", editTask.execute(sampleCommand1));
		assertEquals("testEdit2", "sampleTask2 has been edited.", editTask.execute(sampleCommand2));
		// for comparing actual task objects of both commands
		checkFields(editedFirstTask, arraylistStorage.getNotDoneStorage().get(0));
		checkFields(editedSecondTask, arraylistStorage.getNotDoneStorage().get(1));
	}

	public void checkFields(Task task1, Task task2) {
		assertEquals(task1.getName(), task2.getName());
		assertEquals(task1.getStartDate(), task2.getStartDate());
		assertEquals(task1.getEndDate(), task2.getEndDate());
		assertEquals(task1.getStartTime(), task2.getStartTime());
		assertEquals(task1.getEndTime(), task2.getEndTime());
		assertEquals(task1.getDetails(), task2.getDetails());
	}
}
