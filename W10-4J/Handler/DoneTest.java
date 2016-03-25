//@@author Berkin
package Handler;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Storage.Storage;
import main.Constants;
import main.Task;

public class DoneTest {

	private ArrayList<Task> notDoneYetStorage = new ArrayList<Task>();
	private ArrayList<Task> doneStorage = new ArrayList<Task>();
	private ArrayList<PreviousInput> previousInputStorage = new ArrayList<PreviousInput>();
	Storage mainStorage = new Storage();

	@Test
	public void test() {
		String task1[]={"test1","2016/03/22","09:00","21:00","None"};
		String task2[]={"test2","2016/02/23","00:00","10:00","None"};
		Add add = new Add(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		add.execute(task1,1);
		add.execute(task2,2);
		String doneTask1[]={"1","test1","2016/03/22","09:00","21:00","None"};
		Done done=new Done(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		assertEquals(String.format(Constants.MESSAGE_DONE_PASS, "test1"),done.execute(doneTask1,0));
		assertTrue(doneStorage.get(0).getName()=="test1");
		assertTrue(notDoneYetStorage.get(0).getName()=="test2");
	}

}
// @@author