//@@author Berkin
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Handler.Add;
import Handler.HandlerMemory;
import Handler.PreviousInput;
import Handler.Undo;
import Storage.Storage;
import main.Constants;
import main.Task;

public class UndoTest {

	private HandlerMemory handlerMemory;
	
	

	@Test
	public void test() {
		String task1[]={"test1","2016/03/22","09:00","21:00","None"};
		String task2[]={"test2","2016/02/23","00:00","10:00","None"};
		Add add = new Add(handlerMemory);
		add.execute(task1,1);
		assertEquals("test1",handlerMemory.getPreviousInputStorage().get(0).getTask().getName());
		assertEquals("add",handlerMemory.getPreviousInputStorage().get(0).getAction());
		add.execute(task2,2);
		assertEquals("test2",handlerMemory.getPreviousInputStorage().get(0).getTask().getName());
		assertEquals("add",handlerMemory.getPreviousInputStorage().get(0).getAction());
		Undo undo=new Undo(handlerMemory);
		assertEquals(Constants.MESSAGE_UNDO_PASS,undo.execute(null,0));
		assertTrue(handlerMemory.getNotDoneYetStorage().get(handlerMemory.getNotDoneYetStorage().size()-1).getName()!="test2");
		assertTrue(handlerMemory.getNotDoneYetStorage().get(handlerMemory.getNotDoneYetStorage().size()-1).getName()=="test1");
	}

}
// @@author