package Handler;

import static org.junit.Assert.*;
import main.Task;
import java.util.ArrayList;
import org.junit.Test;
import Storage.Storage;

public class EditTest {
	
	private ArrayList<Task> handlerMemory = new ArrayList<Task>();
	private ArrayList<Task> doneStorage = new ArrayList<Task>();
	private ArrayList<PreviousInput> previousInputStorage = new ArrayList<PreviousInput>();
	Storage mainStorage = new Storage();
	
	
	@Test
	public void test(){
		// create sample task
		Task firstTask = new Task("sampleTask1");
		firstTask.setDate("2016/03/04");
		firstTask.setStartTime("12:00");
		firstTask.setEndTime("16:00");
		firstTask.setDetails("None");
		
		Task secondTask = new Task("sampleTask2");
		secondTask.setDate("2014/01/05");
		secondTask.setStartTime("10:00");
		secondTask.setEndTime("14:00");
		secondTask.setDetails("Nil");
		
		Task editedFirstTask = new Task("SAMPLETASK1");
		editedFirstTask.setDate("2000/01/01");
		editedFirstTask.setStartTime("12:00");
		editedFirstTask.setEndTime("16:00");
		editedFirstTask.setDetails("None");
		
		Task editedSecondTask = new Task("sampleTask2");
		editedSecondTask.setDate("2014/01/05");
		editedSecondTask.setStartTime("03:00");
		editedSecondTask.setEndTime("06:00");
		editedSecondTask.setDetails("Nil");
		
		String[] sampleCommand1 = {"1","rename","SAMPLETASK1", "date", "2000/01/01"};
		String[] sampleCommand2 = {"2","start","03:00", "end", "06:00"};
		
		// Test cloneTask method
		Edit e1 = new Edit(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		checkFields(firstTask, e1.cloneTask(firstTask));
		checkFields(secondTask, e1.cloneTask(secondTask));
		// Test edit method
		handlerMemory.add(firstTask);
		handlerMemory.add(secondTask);
		Edit e2 = new Edit(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		e2.execute(sampleCommand1,0);
		e2.execute(sampleCommand2,0);
		// for string output of both commands
		assertEquals("testEdit1","SAMPLETASK1 has been edited.",e2.execute(sampleCommand1,0));
		assertEquals("testEdit2","sampleTask2 has been edited.",e2.execute(sampleCommand2,0));
		// for comparing actual task objects of both commands
		checkFields(editedFirstTask, handlerMemory.get(0));
		checkFields(editedSecondTask, handlerMemory.get(1));
	}
	public void checkFields(Task task1, Task task2){
		assertEquals(task1.getName(), task2.getName());
		assertEquals(task1.getDate(), task2.getDate());
		assertEquals(task1.getStartTime(), task2.getStartTime());
		assertEquals(task1.getEndTime(), task2.getEndTime());
		assertEquals(task1.getDetails(), task2.getDetails());
	}
}
