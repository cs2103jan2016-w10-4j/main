
package test;

import static org.junit.Assert.*;


import org.junit.Test;

import Handler.Add;
import Handler.ArraylistStorage;
import Handler.Done;
import main.Constants;

public class DoneTest {

	private ArraylistStorage arraylistStorage = new ArraylistStorage();
	private Add addTask = new Add(arraylistStorage);
	private Done doneTask = new Done(arraylistStorage);
	
	@Test
	public void test() {
		String task1[]={"test1","2016/03/22","09:00","21:00","None"};
		String task2[]={"test2","2016/02/23","00:00","10:00","None"};
		addTask.execute(task1);
		addTask.execute(task2);
		String doneTask1[]={"1"};
		assertEquals(String.format(Constants.MESSAGE_DONE_PASS, "test1"),doneTask.execute(doneTask1));
		assertTrue(arraylistStorage.getDoneStorage().get(0).getName()=="test1");
		assertTrue(arraylistStorage.getNotDoneStorage().get(0).getName()=="test2");
	}

}
