
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Handler.Add;
import Handler.HandlerMemory;
import Handler.PreviousInput;
import Storage.Storage;
import main.Constants;
import main.Task;
//@@author A0149174Y
public class AddTest {

	private HandlerMemory handlerMemory=new HandlerMemory();

	@Test
	public void test() {
		
		String task1[]={"test1","2016/03/22","09:00","21:00","None"};
		String task2[]={"test2","2016/02/23","00:00","10:00","None"};
				
				
				Add add = new Add(handlerMemory);
				
				// test the delete method
				assertEquals(String.format(Constants.MESSAGE_ADD_PASS,"test1"), add.execute(task1,1));
				assertEquals(String.format(Constants.MESSAGE_ADD_PASS,"test2"), add.execute(task2,2));
				assertEquals("test2",HandlerMemory.getNotDoneYetStorage().get(HandlerMemory.getNotDoneYetStorage().size()-1).getName());
				assertEquals("test1",HandlerMemory.getNotDoneYetStorage().get(HandlerMemory.getNotDoneYetStorage().size()-2).getName());

	}

}
//@@author