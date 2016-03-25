//@@author Berkin
package Handler;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Storage.Storage;
import main.Constants;
import main.Task;

public class AddTest {

	private ArrayList<Task> notDoneYetStorage = new ArrayList<Task>();
	private ArrayList<Task> doneStorage = new ArrayList<Task>();
	private ArrayList<PreviousInput> previousInputStorage = new ArrayList<PreviousInput>();
	Storage mainStorage = new Storage();
	
	@Test
	public void test() {
		
		String task1[]={"test1","2016/03/22","09:00","21:00","None"};
		String task2[]={"test2","2016/02/23","00:00","10:00","None"};
				
				
				Add add = new Add(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
				
				// test the delete method
				assertEquals(String.format(Constants.MESSAGE_ADD_PASS,"test1"), add.execute(task1,1));
				assertEquals(String.format(Constants.MESSAGE_ADD_PASS,"test2"), add.execute(task2,2));
				assertEquals("test2",notDoneYetStorage.get(notDoneYetStorage.size()-1).getName());
				assertEquals("test1",notDoneYetStorage.get(notDoneYetStorage.size()-2).getName());
	}

}
//@@author