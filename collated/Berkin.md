# Berkin
###### W10-4J\Handler\Add.java
``` java
public class Add implements Command{
```
###### W10-4J\Handler\AddTest.java
``` java
package Handler;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Storage.Storage;
import main.Constants;
import main.Task;

public class AddTest {

	private ArrayList<Task> handlerMemory = new ArrayList<Task>();
	private ArrayList<Task> doneStorage = new ArrayList<Task>();
	private ArrayList<PreviousInput> previousInputStorage = new ArrayList<PreviousInput>();
	Storage mainStorage = new Storage();
	
	@Test
	public void test() {
		
		String task1[]={"test1","2016/03/22","09:00","21:00","None"};
		String task2[]={"test2","2016/02/23","00:00","10:00","None"};
				
				
				Add add = new Add(handlerMemory, doneStorage, previousInputStorage, mainStorage);
				
				// test the delete method
				assertEquals(String.format(Constants.MESSAGE_ADD_PASS,"test1"), add.execute(task1,1));
				assertEquals(String.format(Constants.MESSAGE_ADD_PASS,"test2"), add.execute(task2,2));
				assertEquals("test2",handlerMemory.get(handlerMemory.size()-1).getName());
				assertEquals("test1",handlerMemory.get(handlerMemory.size()-2).getName());
	}

}
```
###### W10-4J\Handler\Command.java
``` java
package Handler;

public interface Command {

	String execute(String[] task, int taskID);

}
```
###### W10-4J\Handler\Command.java
``` java

```
###### W10-4J\Handler\DoneTest.java
``` java
package Handler;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Storage.Storage;
import main.Constants;
import main.Task;

public class DoneTest {

	private ArrayList<Task> handlerMemory = new ArrayList<Task>();
	private ArrayList<Task> doneStorage = new ArrayList<Task>();
	private ArrayList<PreviousInput> previousInputStorage = new ArrayList<PreviousInput>();
	Storage mainStorage = new Storage();
	
	@Test
	public void test() {
		String task1[]={"test1","2016/03/22","09:00","21:00","None"};
		String task2[]={"test2","2016/02/23","00:00","10:00","None"};
		Add add = new Add(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		add.execute(task1,1);
		add.execute(task2,2);
		String doneTask1[]={"1","test1","2016/03/22","09:00","21:00","None"};
		Done done=new Done(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		assertEquals(String.format(Constants.MESSAGE_DONE_PASS, "test1"),done.execute(doneTask1,0));
		assertTrue(doneStorage.get(0).getName()=="test1");
		assertTrue(handlerMemory.get(0).getName()=="test2");
	}

}
```
###### W10-4J\Handler\Handler.java
``` java
	public String executeCommand(COMMAND_TYPE command, String[] task) {
		try{
			Command cmd = createCommand(command,task);
			return cmd.execute(task,taskID);
		} catch (IllegalArgumentException invalidCommandFormat) {
			return Constants.MESSAGE_INVALID_FORMAT;
		} catch (IllegalStateException unrecognizedCommand) {
			return Constants.MESSAGE_UNRECOGNÝZED_COMMAND;
			}
		}
	private Command createCommand(COMMAND_TYPE command, String[] task) throws IllegalArgumentException, IllegalStateException {
		switch (command) {
		case ADD:
			return new Add(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		case EDIT:
			return new Edit(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		case DELETE:
			return new Delete(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		case DONE:
			return new Done(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		case DISPLAY:
			return new Display(handlerMemory, doneStorage, mainStorage);
		case SEARCH:
			return new Search(handlerMemory, mainStorage);
		case SETDIR:
			return new SetDir(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		case RETRIEVE:
			return new Retrieve(handlerMemory, doneStorage, mainStorage);
		case RECURRENCE:
			return new Recurrence(handlerMemory,doneStorage,previousInputStorage,mainStorage);
		case UNDO:
			return new Undo(handlerMemory, doneStorage, previousInputStorage, mainStorage);
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
```
###### W10-4J\Handler\UndoTest.java
``` java
package Handler;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Storage.Storage;
import main.Constants;
import main.Task;

public class UndoTest {

	private ArrayList<Task> handlerMemory = new ArrayList<Task>();
	private ArrayList<Task> doneStorage = new ArrayList<Task>();
	private ArrayList<PreviousInput> previousInputStorage = new ArrayList<PreviousInput>();
	Storage mainStorage = new Storage();
	
	@Test
	public void test() {
		String task1[]={"test1","2016/03/22","09:00","21:00","None"};
		String task2[]={"test2","2016/02/23","00:00","10:00","None"};
		Add add = new Add(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		add.execute(task1,1);
		assertEquals("test1",previousInputStorage.get(0).getTask().getName());
		assertEquals("add",previousInputStorage.get(0).getAction());
		add.execute(task2,2);
		assertEquals("test2",previousInputStorage.get(0).getTask().getName());
		assertEquals("add",previousInputStorage.get(0).getAction());
		Undo undo=new Undo(handlerMemory, doneStorage, previousInputStorage, mainStorage);
		assertEquals(Constants.MESSAGE_UNDO_PASS,undo.execute(null,0));
		assertTrue(handlerMemory.get(handlerMemory.size()-1).getName()!="test2");
		assertTrue(handlerMemory.get(handlerMemory.size()-1).getName()=="test1");
	}

}
```
