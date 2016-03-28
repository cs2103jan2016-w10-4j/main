# Berkin
###### W10-4J\Handler\Command.java
``` java
public interface Command {
	
	String execute(String[] task, int taskID);
	COMMAND_STATE returnCommandState();
	Task returnEachTask();
	Task returnOldTask();
}
```
###### W10-4J\Handler\Handler.java
``` java
	public String executeCommand(COMMAND_TYPE command, String[] task) {
		try {
			Command cmd = createCommand(command, task);
			String toBeReturned= cmd.execute(task, HandlerMemory.getTaskID());
			HandlerMemory.updateMemory(cmd,command);
			return toBeReturned;
		} catch (IllegalArgumentException invalidCommandFormat) {
			return Constants.MESSAGE_INVALID_FORMAT;
		} catch (IllegalStateException unrecognizedCommand) {
			return Constants.MESSAGE_UNRECOGNÝZED_COMMAND;
		}
	}

	private Command createCommand(COMMAND_TYPE command, String[] task)
			throws IllegalArgumentException, IllegalStateException {
		int taskID=HandlerMemory.getTaskID();
		switch (command) {
		case ADD:
			HandlerMemory.setTaskID(taskID++);
			return new Add(handlerMemory);
		case EDIT:
			return new Edit(handlerMemory);
		case DELETE:
			return new Delete(handlerMemory);
		case DONE:
			return new Done(handlerMemory);
		case DISPLAY:
			return new Display(handlerMemory);
		case SEARCH:
			return new Search(handlerMemory);
		case SETDIR:
			taskID = 0;
			return new SetDir(handlerMemory);
		case RETRIEVE:
			return new Retrieve(handlerMemory);
		case RECURRENCE:
			return new Recurrence(handlerMemory);
		case UNDO:
			return new Undo(handlerMemory);
		case EXIT:
			System.exit(0);
		case HELP:
			return new Help();
		case INVALID:
			throw new IllegalArgumentException();
		default:
			throw new IllegalStateException();
		}
	}
}
```
###### W10-4J\Handler\HandlerMemory.java
``` java
public class HandlerMemory {

	private static ArrayList<Task> notDoneYetStorage;
	private static ArrayList<Task> doneStorage;
	private static ArrayList<PreviousInput> previousInputStorage;
	private static int taskID;
	static Storage mainStorage = new Storage();
	
	public HandlerMemory() {
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read(Constants.MESSAGE_ACTION_READ, Constants.fileName);
		notDoneYetStorage = getFromStorage.get(0);
		doneStorage = getFromStorage.get(1);
		previousInputStorage = new ArrayList<PreviousInput>();
		taskID = HandlerMemory.getTaskID();
	}
	
	public static void updateMemory(Command cmd, COMMAND_TYPE command) {
		if(cmd.returnCommandState()!=COMMAND_STATE.FAILED) {
		switch (command) {
		case ADD:
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnEachTask()));
			notDoneYetStorage.add(cmd.returnEachTask());
			mainStorage.write(notDoneYetStorage, doneStorage);  
		case EDIT:
			mainStorage.write(notDoneYetStorage, doneStorage);
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, cmd.returnOldTask(), cmd.returnEachTask()));
		case DELETE:
			if(cmd.returnCommandState()==COMMAND_STATE.DELETEDONETASK) {
				doneStorage.remove(cmd.returnEachTask());
				mainStorage.write(notDoneYetStorage, doneStorage);
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
			}
			else {
				assert cmd.returnCommandState()==COMMAND_STATE.DELETEUNDONETASK;
				notDoneYetStorage.remove(cmd.returnEachTask());
				mainStorage.write(notDoneYetStorage, doneStorage);
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
			}
		case DONE:
			if(cmd.returnCommandState()==COMMAND_STATE.RECURRINGDONE) {
				mainStorage.write(notDoneYetStorage, doneStorage);
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
			}
			else {
				assert cmd.returnCommandState()==COMMAND_STATE.NONRECURRINGDONE;
				notDoneYetStorage.remove(cmd.returnEachTask());
				doneStorage.add(cmd.returnEachTask());
				mainStorage.write(notDoneYetStorage, doneStorage);
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
			}
		case DISPLAY:
			//fallthrough //This part is done in display class
		case SEARCH:
			
		case SETDIR:
			notDoneYetStorage.clear();
  			doneStorage.clear();
  			previousInputStorage.clear();
		case RETRIEVE:
			//fallthrough //This part is done in retrieve class
		case RECURRENCE:
			mainStorage.write(notDoneYetStorage, doneStorage);
			clearAndAdd(previousInputStorage, new PreviousInput("edit", cmd.returnOldTask(), cmd.returnEachTask()));
		case UNDO:
			switch(cmd.returnCommandState()) {
			case UNDOADD:
				// to restore to previous state, must unadd the task
				notDoneYetStorage.remove(cmd.returnEachTask());
				// remember previous state
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
			case UNDODELETE:
				notDoneYetStorage.add(cmd.returnEachTask());
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnEachTask()));
			case UNDOEDIT:
				// to restore to previous state, must edit again to previous state
				Task eachTask= previousInputStorage.get(0).getEditedTask();
				notDoneYetStorage.remove(eachTask);
				notDoneYetStorage.add(cmd.returnEachTask());
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, eachTask, cmd.returnEachTask()));
			case UNDODONE:
				doneStorage.remove(cmd.returnEachTask());
				notDoneYetStorage.add(cmd.returnEachTask());
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_UNDO, cmd.returnEachTask()));
			case UNDOUNDO:
				notDoneYetStorage.remove(cmd.returnEachTask());
				doneStorage.add(cmd.returnEachTask());
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
			default:
				break;
			}
			mainStorage.write(notDoneYetStorage, doneStorage);
		case EXIT:
			assert false;
		case HELP:
		//Fallthrough	
		case INVALID:
			assert false;
		default:
			assert false;
		}
		}
	}
```
###### W10-4J\Handler\Recurrence.java
``` java
package Handler;

import java.util.ArrayList;
import main.Constants;
import Storage.Storage;
import main.Task;
import main.Constants.COMMAND_STATE;

public class Recurrence implements Command {

	private  COMMAND_STATE commandState;
	private  Task forEachTask;
	private  Task forOldTask;
	private  HandlerMemory handlerMemory;
	
	public Recurrence(HandlerMemory handlerMemory){
		this.handlerMemory=handlerMemory;
}
```
###### W10-4J\test\AddTest.java
``` java
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

public class AddTest {

	private HandlerMemory handlerMemory;

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
```
###### W10-4J\test\DoneTest.java
``` java
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Handler.Add;
import Handler.Done;
import Handler.HandlerMemory;
import Handler.PreviousInput;
import Storage.Storage;
import main.Constants;
import main.Task;

public class DoneTest {

	private HandlerMemory handlerMemory;

	@Test
	public void test() {
		String task1[]={"test1","2016/03/22","09:00","21:00","None"};
		String task2[]={"test2","2016/02/23","00:00","10:00","None"};
		Add add = new Add(handlerMemory);
		add.execute(task1,1);
		add.execute(task2,2);
		String doneTask1[]={"1","test1","2016/03/22","09:00","21:00","None"};
		Done done=new Done(handlerMemory);
		assertEquals(String.format(Constants.MESSAGE_DONE_PASS, "test1"),done.execute(doneTask1,0));
		assertTrue(handlerMemory.getDoneStorage().get(0).getName()=="test1");
		assertTrue(handlerMemory.getNotDoneYetStorage().get(0).getName()=="test2");
	}

}
```
###### W10-4J\test\UndoTest.java
``` java
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
```
