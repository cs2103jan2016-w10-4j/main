package Handler;

import static org.junit.Assert.*;
import main.Task;
import java.util.ArrayList;
import org.junit.Test;
import Storage.Storage;

public class DeleteTest {
	
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
		
		// create string commands, take note of the sequential order of execution as the task IDs will be updated
		String[] input1 = {"1"};
		String[] input2 = {"1"};
		String[] input3 = {"2"};
		
		handlerMemory.add(firstTask);
		handlerMemory.add(secondTask);
		Delete d = new Delete(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		
		// test the delete method
		assertEquals("sampleTask1 has been deleted.", d.delete(input1));
		assertEquals("sampleTask2 has been deleted.", d.delete(input2));
		assertEquals("Task cannot be deleted.", d.delete(input3));
	}
}
