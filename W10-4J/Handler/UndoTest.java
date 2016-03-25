//@@author Berkin
package Handler;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Storage.Storage;
import main.Constants;
import main.Task;

public class UndoTest {

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
		assertEquals("test1",previousInputStorage.get(0).getTask().getName());
		assertEquals("add",previousInputStorage.get(0).getAction());
		add.execute(task2,2);
		assertEquals("test2",previousInputStorage.get(0).getTask().getName());
		assertEquals("add",previousInputStorage.get(0).getAction());
		Undo undo=new Undo(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		assertEquals(Constants.MESSAGE_UNDO_PASS,undo.execute(null,0));
		assertTrue(notDoneYetStorage.get(notDoneYetStorage.size()-1).getName()!="test2");
		assertTrue(notDoneYetStorage.get(notDoneYetStorage.size()-1).getName()=="test1");
	}

}
//@@author