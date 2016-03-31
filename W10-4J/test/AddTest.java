
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import Handler.Add;
import Handler.ArraylistStorage;
import main.Constants;

public class AddTest {
	private ArraylistStorage arraylistStorage = new ArraylistStorage();
	private Add addTask = new Add(arraylistStorage);

	@Test
	public void test() {
		
		String task1[]={"test1","2016/03/22","09:00","21:00","None"};
		String task2[]={"test2","2016/02/23","00:00","10:00","None"};
				
				// test the delete method
				assertEquals(String.format(Constants.MESSAGE_ADD_PASS,"test1"), addTask.execute(task1));
				assertEquals(String.format(Constants.MESSAGE_ADD_PASS,"test2"), addTask.execute(task2));
				assertEquals("test2",arraylistStorage.getNotDoneStorage().get(arraylistStorage.getNotDoneStorage().size()-1).getName());
				assertEquals("test1",arraylistStorage.getNotDoneStorage().get(arraylistStorage.getNotDoneStorage().size()-2).getName());

	}

}
