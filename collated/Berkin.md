# Berkin
###### W10-4J\Handler\Add.java
``` java
public class Add implements Command{
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
###### W10-4J\Handler\Handler.java
``` java
	public String executeCommand(COMMAND_TYPE command, String[] task) {
		try {
			Command cmd = createCommand(command, task);
			return cmd.execute(task, taskID);
		} catch (IllegalArgumentException invalidCommandFormat) {
			return Constants.MESSAGE_INVALID_FORMAT;
		} catch (IllegalStateException unrecognizedCommand) {
			return Constants.MESSAGE_UNRECOGNÝZED_COMMAND;
		}
	}

	private Command createCommand(COMMAND_TYPE command, String[] task)
			throws IllegalArgumentException, IllegalStateException {
		switch (command) {
		case ADD:
			taskID++;
			return new Add(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case EDIT:
			return new Edit(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case DELETE:
			return new Delete(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case DONE:
			return new Done(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case DISPLAY:
			return new Display(notDoneYetStorage, doneStorage, mainStorage);
		case SEARCH:
			return new Search(notDoneYetStorage, mainStorage);
		case SETDIR:
			return new SetDir(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
		case RETRIEVE:
			return new Retrieve(notDoneYetStorage, doneStorage, mainStorage);
		case RECURRENCE:
			return new Recurrence(notDoneYetStorage,doneStorage,previousInputStorage,mainStorage);
		case UNDO:
			return new Undo(notDoneYetStorage, doneStorage, previousInputStorage, mainStorage);
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
###### W10-4J\Handler\Recurrence.java
``` java
package Handler;

import java.util.ArrayList;
import main.Constants;
import Storage.Storage;
import main.Task;

public class Recurrence implements Command {

	private ArrayList<Task> notDoneYetStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Recurrence(ArrayList<Task> notDoneYetStorage, ArrayList<Task> doneStorage,
			ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
	this.notDoneYetStorage = notDoneYetStorage;
	this.doneStorage = doneStorage;
	this.previousInputStorage = previousInputStorage;
	this.mainStorage = mainStorage;
}
```
###### W10-4J\Handler\SetDir.java
``` java
package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.*;

public class SetDir implements Command {

	private ArrayList<Task> notDoneYetStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public SetDir(ArrayList<Task> notDoneYetStorage, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.notDoneYetStorage = notDoneYetStorage;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}
```
###### W10-4J\test\AddTest.java
``` java
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Handler.Add;
import Handler.PreviousInput;
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
```
###### W10-4J\test\AllTests.java
``` java
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ HandlerTestSuite.class, ParserTestSuite.class, StorageTestSuite.class, UITestSuite.class })
public class AllTests {

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
import Handler.PreviousInput;
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
```
###### W10-4J\test\HandlerTestSuite.java
``` java
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddTest.class, DeleteTest.class, DoneTest.class, EditTest.class, UndoTest.class })
public class HandlerTestSuite {

}
```
###### W10-4J\test\ParserTestSuite.java
``` java
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ NaturalLanguageTest.class, ParserTest.class })
public class ParserTestSuite {

}
```
###### W10-4J\test\StorageTestSuite.java
``` java
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ReadTest.class, SetDirectoryTest.class, WriteTest.class })
public class StorageTestSuite {

}
```
###### W10-4J\test\UITestSuite.java
``` java
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UIControllerTest.class })
public class UITestSuite {

}
```
###### W10-4J\test\UndoTest.java
``` java
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Handler.Add;
import Handler.PreviousInput;
import Handler.Undo;
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
```
