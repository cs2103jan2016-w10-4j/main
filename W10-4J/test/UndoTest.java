
package test;

import static org.junit.Assert.*;


import org.junit.Test;

import Handler.Add;
import Handler.ArraylistStorage;
import Handler.Undo;
import main.Constants;
//@@author A0149174Y
public class UndoTest {

	private ArraylistStorage arraylistStorage = new ArraylistStorage();
	private Add addTask = new Add(arraylistStorage);
	private Undo undoTask;
	
	@Test
	public void test() {
		String task1[]={"test1","2016/03/22","09:00","21:00","None"};
		String task2[]={"test2","2016/02/23","00:00","10:00","None"};
		addTask.execute(task1);
		assertEquals("test1",arraylistStorage.getPreviousInputStorage().get(0).getTask().getName());
		assertEquals("add",arraylistStorage.getPreviousInputStorage().get(0).getAction());
		addTask.execute(task2);
		assertEquals("test2",arraylistStorage.getPreviousInputStorage().get(0).getTask().getName());
		assertEquals("add",arraylistStorage.getPreviousInputStorage().get(0).getAction());
		
		// test undo
		undoTask = new Undo(arraylistStorage);
		assertEquals(Constants.MESSAGE_UNDO_PASS,undoTask.execute(null));
		assertTrue(arraylistStorage.getNotDoneStorage().get(arraylistStorage.getNotDoneStorage().size()-1).getName()!="test2");
		assertTrue(arraylistStorage.getNotDoneStorage().get(arraylistStorage.getNotDoneStorage().size()-1).getName()=="test1");
	}
}
//@@author