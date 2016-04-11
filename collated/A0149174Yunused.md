# A0149174Yunused
###### W10-4J\Handler\Add.java
``` java
	private COMMAND_STATE commandState;
	private Task forCurrentTask; //currentTask the command is working on which will be updated in the HandlerMemory later.
	private Task forOldTask;//The Task which after this command will be an oldTask to be stored in the previousInputStorage by HandlerMemory.
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}

	public String execute_OLD(String[] allInputInfoRelatedToTask, int taskID) {
		assert allInputInfoRelatedToTask[0] != null : Constants.ASSERT_FIELD_EXISTENCE;
		assert taskID > 0 : Constants.ASSERT_TASKID_EXISTENCE;
		
		Task newTask = new Task(allInputInfoRelatedToTask[0].trim()); //creates the new task to be added by the name of the task.
		newTask.setTaskID(taskID);
		for (int i = 1; i < allInputInfoRelatedToTask.length; i += 2) {
			String taskInfo = allInputInfoRelatedToTask[i].trim();
			assert taskInfo != null : Constants.ASSERT_ACTION_EXISTENCE;
			setTaskInfoToNewTask(allInputInfoRelatedToTask, newTask, i, taskInfo);
		}
		return checkWhetherDateAndTimeValidAndReturnMessage(newTask);
	}
	
	private String checkWhetherDateAndTimeValidAndReturnMessage(Task newTask) {
		if (isDateAndTimeValid(newTask)) {
			forCurrentTask = newTask;
			return String.format(Constants.MESSAGE_ADD_PASS, newTask.getName());
		} else {
			commandState = HandlerMemory.COMMAND_STATE.FAILED;
			return Constants.MESSAGE_TIME_FAIL;
		}
	}

	private void setTaskInfoToNewTask(String[] task, Task newTask, int i, String taskInfo) {
		switch (taskInfo) {
		case Constants.MESSAGE_ADD_ACTION_STARTDATE:
			newTask.setStartDate(task[i + 1].trim());
			break;
		case Constants.MESSAGE_ADD_ACTION_ENDDATE:
			newTask.setEndDate(task[i + 1].trim());
			break;
		case Constants.MESSAGE_ADD_ACTION_START:
			newTask.setStartTime(task[i + 1].trim());
			break;
		case Constants.MESSAGE_ADD_ACTION_END:
			newTask.setEndTime(task[i + 1].trim());
			break;
		case Constants.MESSAGE_ADD_ACTION_DETAILS:
			newTask.setDetails(task[i + 1].trim());
			break;
		case Constants.MESSAGE_ADD_ACTION_REPEAT:
			setDurationForRecurringTasks(task, newTask, i);
			break;
		default:
			assert false;
		}
	}

	private void setDurationForRecurringTasks(String[] task, Task newTask, int i) {
		switch (task[i + 1].trim()) {
		case Constants.MESSAGE_REPEAT_DAY:
			newTask.setDay(true);
			break;
		case Constants.MESSAGE_REPEAT_WEEK:
			newTask.setWeek(true);
			break;
		case Constants.MESSAGE_REPEAT_MONTH:
			newTask.setMonth(true);
			break;
		case Constants.MESSAGE_REPEAT_YEAR:
			newTask.setYear(true);
			break;
		default:
			assert false;
		}
	}
```
###### W10-4J\Handler\Command.java
``` java
	Task returnCurrentTask(); //The task the command is currently editing.
	COMMAND_STATE returnCommandState(); //Current state of the command execute function.
	Task returnOldTask(); //The old Task the command is editing or reaching.
}
```
###### W10-4J\Handler\Delete.java
``` java
	private COMMAND_STATE commandState;
	private Task forCurrentTask; //currentTask the command is working on which will be updated in the HandlerMemory later.
	private Task forOldTask;//The Task which after this command will be an oldTask to be stored in the previousInputStorage by HandlerMemory.
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}

	public String execute_OLD(String[] task, int notUsedInThisCommand) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task currentTask = handlerMemory.findByTaskID(HandlerMemory.getNotDoneYetStorage_OLD(), taskID);
		if (currentTask == null) {
			currentTask = handlerMemory.findByTaskID(HandlerMemory.getDoneStorage_OLD(), taskID);
			if (currentTask == null) {
				commandState = HandlerMemory.COMMAND_STATE.FAILED;
				return Constants.MESSAGE_DELETE_FAIL;
			} else {
				forCurrentTask = currentTask;
				commandState = HandlerMemory.COMMAND_STATE.DELETEDONETASK;
				return String.format(Constants.MESSAGE_DELETE_PASS, currentTask.getName());
			}
		} else if (taskID <= 0 || taskID > HandlerMemory.getTaskID()) {
			commandState = HandlerMemory.COMMAND_STATE.FAILED;
			return Constants.MESSAGE_DELETE_FAIL;
		} else {
			assert currentTask != null : Constants.ASSERT_TASK_EXISTENCE;
			forCurrentTask = currentTask;
			commandState = HandlerMemory.COMMAND_STATE.DELETEUNDONETASK;
			return String.format(Constants.MESSAGE_DELETE_PASS, currentTask.getName());
		}
	}
```
###### W10-4J\Handler\Done.java
``` java
	private COMMAND_STATE commandState;
	private Task forCurrentTask; //currentTask the command is working on which will be updated in the HandlerMemory later.
	private Task forOldTask;//The Task which after this command will be an oldTask to be stored in the previousInputStorage by HandlerMemory.
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}

	public String execute_OLD(String[] task, int notUsedInThisCommand) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task currentTask = handlerMemory.findByTaskID(handlerMemory.getNotDoneYetStorage_OLD(), taskID);
		if (currentTask == null) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_DONE_FAIL;
		} else {
			if (currentTask.isRecurring() && currentTask.getEndDate() != null) {
				forCurrentTask = currentTask;
				assert currentTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
				commandState = COMMAND_STATE.RECURRINGDONE;
				return String.format(Constants.MESSAGE_DONE_PASS, currentTask.getName());
			} else {
				assert currentTask != null : Constants.ASSERT_TASK_EXISTENCE;
				assert currentTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
				forCurrentTask = currentTask;
				commandState = COMMAND_STATE.NONRECURRINGDONE;
				return String.format(Constants.MESSAGE_DONE_PASS, currentTask.getName());
			}
		}
	}
```
###### W10-4J\Handler\Edit.java
``` java
	private final Logger LOGGER = Logger.getLogger(Edit.class.getName());
	private COMMAND_STATE commandState;
	private Task forCurrentTask; //currentTask the command is working on which will be updated in the HandlerMemory later.
	private Task forOldTask;//The Task which after this command will be an oldTask to be stored in the previousInputStorage by HandlerMemory.
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}

	public String execute_OLD(String[] task, int notUsedInThisCommand) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task currentTask = handlerMemory.findByTaskID(HandlerMemory.getNotDoneYetStorage_OLD(), taskID);
		if (currentTask == null) {
			commandState = COMMAND_STATE.FAILED;
			LOGGER.log(Level.WARNING, Constants.MESSAGE_EDIT_FAIL);
			return Constants.MESSAGE_EDIT_FAIL;
		} else if (taskID <= 0 || taskID > HandlerMemory.getTaskID()) {
			commandState = COMMAND_STATE.FAILED;
			LOGGER.log(Level.WARNING, Constants.MESSAGE_EDIT_FAIL);
			return Constants.MESSAGE_EDIT_FAIL;
		} else {
			assert currentTask != null : Constants.ASSERT_TASK_EXISTENCE;
			Task oldTask = cloneTask(currentTask);
			fieldEditor(currentTask, task); //edits the task.
			LOGGER.log(Level.INFO, Constants.MESSAGE_EDIT_PASS);
			return checkWhetherDateAndTimeValidAndReturnMessage(currentTask, oldTask);
		}
	}
	
	private String checkWhetherDateAndTimeValidAndReturnMessage(Task eachTask, Task oldTask) {
		if (isDateAndTimeValid(eachTask)) {
			forCurrentTask = eachTask;
			forOldTask = oldTask;
			return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
		} else {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_TIME_FAIL;
		}
	}
```
###### W10-4J\Handler\Handler.java
``` java
	public String executeCommand_OLD(COMMAND_TYPE command, String[] task) {
		try {
			Command cmd = getCommand(command, task);
			String toBeReturned=cmd.execute(task);
			HandlerMemory.updateMemory(cmd,command);
			return toBeReturned;
		} catch (IllegalArgumentException invalidCommandFormat) {
			LOGGER.log(Level.WARNING, Constants.MESSAGE_INVALID_FORMAT);
			return Constants.MESSAGE_INVALID_FORMAT;
		} catch (IllegalStateException unrecognizedCommand) {
			LOGGER.log(Level.WARNING, Constants.MESSAGE_UNRECOGNISED_COMMAND);
			return Constants.MESSAGE_UNRECOGNISED_COMMAND;
		}
	}
```
###### W10-4J\Handler\HandlerMemory.java
``` java
/**
 * @author A0149174Y
 *The idea to handle all the sequential storage in the handler through a separate class called HandlerMemory was proposed by me before V0.3.
 *I implemented all the unused codes present under the name for A0149174Y-unused until V0.3.
 *Basically it stores all the ArrayLists and updates all of them after each command operation. 
 *Rather than updating the memory in each execute function, the command just remembers it's end state after it's executed.
 *It also remembers the current task and the old task it is asked by the user to work on as a static object. 
 *According to which state the command ended in (for example in FAIL state) the updateMemory function decides how to update the memory accordingly by using the currentTask and oldTask.
 *After V0.3 this design was decided to be changed by my team mates. Now the class ArrayListStorage handles the job of HandlerMemory.
 *Now I changed my execute functions as execute_OLD and changed all the ArrayList names as _OLD as well so that it wont collide with the new version.
 */
package Handler;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Storage.Storage;
import main.Constants;
import main.Constants.COMMAND_TYPE;
import main.Task;

public class HandlerMemory {

	public enum COMMAND_STATE {
		FAILED,UNDOADD,UNDODELETE,UNDOUNDO,UNDODONE,UNDOEDIT,DELETEDONETASK,DELETEUNDONETASK,RECURRINGDONE,NONRECURRINGDONE
	};
	private final static Logger LOGGER = Logger.getLogger(HandlerMemory.class.getName());
	private static ArrayList<Task> notDoneYetStorage_OLD;
	private static ArrayList<Task> doneStorage_OLD;
	private static ArrayList<PreviousInput> previousInputStorage_OLD;
	private static int taskID;
	static Storage mainStorage_OLD = new Storage();

	public HandlerMemory() {
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage_OLD.read(Constants.MESSAGE_ACTION_READ, Constants.DEFAULT_FILENAME);
		notDoneYetStorage_OLD = getFromStorage.get(0);
		doneStorage_OLD = getFromStorage.get(1);
		previousInputStorage_OLD = new ArrayList<PreviousInput>();
		taskID = HandlerMemory.determineTaskID();
	}

	public static void updateMemory(Command cmd, COMMAND_TYPE command) {
		if (cmd.returnCommandState() != COMMAND_STATE.FAILED) {
			switch (command) {
			case ADD:
				addUpdateMemory(cmd);
				break;
			case EDIT:
				editUpdateMemory(cmd);
				break;
			case DELETE:
				deleteUpdateMemory(cmd);
				break;
			case DONE:
				doneUpdateMemory(cmd);
				break;
			case DISPLAY:
				break;
			case SEARCH:
				break;
			case SETDIR:
				setdirUpdateMemory();
				break;
			case RETRIEVE:
				break;
			case RECURRENCE:
				recurrenceUpdateMemory(cmd);
				break;
			case UNDO:
				undoUpdateMemory(cmd);
				break;
			case EXIT:
				assert false;
			case HELP:
				break;
			case INVALID:
				assert false;
			default:
				assert false;
			}
		}
	}

	private static void undoUpdateMemory(Command cmd) {
		switch (cmd.returnCommandState()) {
		case UNDOADD:
			updateMemoryUndoAdd(cmd);
			break;
		case UNDODELETE:
			updateMemoryUndoDelete(cmd);
			break;
		case UNDOEDIT:
			updateMemoryUndoEdit(cmd);
			break;
		case UNDODONE:
			updateMemoryUndoDone(cmd);
			break;
		case UNDOUNDO:
			updateMemoryUndoUndo(cmd);
		default:
			LOGGER.log(Level.INFO, Constants.MESSAGE_ACTION_UNDO+cmd.returnCommandState());
			break;
		}
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
	}

	private static void updateMemoryUndoUndo(Command cmd) {
		notDoneYetStorage_OLD.remove(cmd.returnCurrentTask());
		doneStorage_OLD.add(cmd.returnCurrentTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnCurrentTask()));
	}

	private static void updateMemoryUndoDone(Command cmd) {
		doneStorage_OLD.remove(cmd.returnCurrentTask());
		notDoneYetStorage_OLD.add(cmd.returnCurrentTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_UNDO, cmd.returnCurrentTask()));
	}

	private static void updateMemoryUndoEdit(Command cmd) {
		// to restore the previous state, must edit again
		Task eachTask = previousInputStorage_OLD.get(0).getEditedTask();
		notDoneYetStorage_OLD.remove(eachTask);
		notDoneYetStorage_OLD.add(cmd.returnCurrentTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, eachTask, cmd.returnCurrentTask()));
	}

	private static void updateMemoryUndoDelete(Command cmd) {
		notDoneYetStorage_OLD.add(cmd.returnCurrentTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnCurrentTask()));
	}

	private static void updateMemoryUndoAdd(Command cmd) {
		// to restore to previous state, must unadd the task
		notDoneYetStorage_OLD.remove(cmd.returnCurrentTask());
		// remember previous state
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnCurrentTask()));
	}

	private static void recurrenceUpdateMemory(Command cmd) {
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, cmd.returnOldTask(), cmd.returnCurrentTask()));
	}

	private static void setdirUpdateMemory() {
		notDoneYetStorage_OLD.clear();
		doneStorage_OLD.clear();
		previousInputStorage_OLD.clear();
	}

	private static void doneUpdateMemory(Command cmd) {
		if (cmd.returnCommandState() == COMMAND_STATE.RECURRINGDONE) {
			updateMemoryForReccurringTask(cmd);
		} else {
			updateMemoryForNonReccurringTask(cmd);
		}
	}

	private static void updateMemoryForNonReccurringTask(Command cmd) {
		assert cmd.returnCommandState() == COMMAND_STATE.NONRECURRINGDONE;
		notDoneYetStorage_OLD.remove(cmd.returnCurrentTask());
		doneStorage_OLD.add(cmd.returnCurrentTask());
		updateMemoryForReccurringTask(cmd);
	}

	private static void updateMemoryForReccurringTask(Command cmd) {
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnCurrentTask()));
	}

	private static void deleteUpdateMemory(Command cmd) {
		if (cmd.returnCommandState() == COMMAND_STATE.DELETEDONETASK) {
			updateMemoryForDeletedDoneTask(cmd);
		} else {
			updateMemoryForDeletedNotYetDoneTask(cmd);
		}
	}

	private static void updateMemoryForDeletedNotYetDoneTask(Command cmd) {
		assert cmd.returnCommandState() == COMMAND_STATE.DELETEUNDONETASK;
		notDoneYetStorage_OLD.remove(cmd.returnCurrentTask());
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnCurrentTask()));
	}

	private static void updateMemoryForDeletedDoneTask(Command cmd) {
		doneStorage_OLD.remove(cmd.returnCurrentTask());
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnCurrentTask()));
	}

	private static void editUpdateMemory(Command cmd) {
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, cmd.returnOldTask(), cmd.returnCurrentTask()));
	}

	private static void addUpdateMemory(Command cmd) {
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnCurrentTask()));
		notDoneYetStorage_OLD.add(cmd.returnCurrentTask());
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
	}
```
###### W10-4J\Handler\Recurrence.java
``` java
	private final Logger LOGGER = Logger.getLogger(Recurrence.class.getName());
	private COMMAND_STATE commandState;
	private Task forCurrentTask; //currentTask the command is working on which will be updated in the HandlerMemory later.
	private Task forOldTask;//The Task which after this command will be an oldTask to be stored in the previousInputStorage by HandlerMemory.
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}

	public String execute_OLD(String[] task, int notUsedInThisCommand) {
		int taskID = Integer.parseInt(task[0].trim());
		Task currentTask = handlerMemory.findByTaskID(handlerMemory.getNotDoneYetStorage_OLD(), taskID);
		if (currentTask == null) {
			commandState = COMMAND_STATE.FAILED;
			LOGGER.log(Level.WARNING, Constants.MESSAGE_RECUR_FAIL);
			return Constants.MESSAGE_RECUR_FAIL;
		} else {
			Task oldTask = cloneTask(currentTask,taskID); //oldTask will be stored as PreviousInput later.
			setRecurringDurationForCurrentTask(task, currentTask);
			forCurrentTask = currentTask;
			forOldTask = oldTask;
			LOGGER.log(Level.INFO, Constants.MESSAGE_RECUR_PASS);
			return String.format(Constants.MESSAGE_RECUR_PASS, currentTask.getName());
		}
	}

	private void setRecurringDurationForCurrentTask(String[] task, Task currentTask) {
		switch (task[1]) {
		case "day":
			currentTask.setDay(true);
			break;
		case "week":
			currentTask.setWeek(true);
			break;
		case "month":
			currentTask.setMonth(true);
			break;
		case "year":
			currentTask.setYear(true);
			break;
		}
	}
```
###### W10-4J\Handler\Retrieve.java
``` java
	private COMMAND_STATE commandState;
	private Task forCurrentTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}
```
###### W10-4J\Handler\Search.java
``` java
	private COMMAND_STATE commandState;
	private Task forCurrentTask; //currentTask the command is working on which will be updated in the HandlerMemory later.
	private Task forOldTask;//The Task which after this command will be an oldTask to be stored in the previousInputStorage by HandlerMemory.
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}
	public String execute_OLD(String[] task, int notUsedInThisCommand) {
		ArrayList<Task> searchNotDoneYetStorage = new ArrayList<Task>();
		if (task.length > 1) {
			for (Task eachTask : HandlerMemory.getNotDoneYetStorage_OLD()) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				//inclusiveSearch(eachTask, task, searchNotDoneYetStorage_OLD);//new version does not support this method.
			}
		} else {
			for (Task eachTask : HandlerMemory.getNotDoneYetStorage_OLD()) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				//exclusiveSearch(eachTask, task, searchNotDoneYetStorage_OLD);//new version does not support this method.
			}
		}
		if (searchNotDoneYetStorage.size() != 0) {
			return DisplayFormat.displayFormat(searchNotDoneYetStorage);
		}
		else {
		commandState = COMMAND_STATE.FAILED;
		return Constants.MESSAGE_SEARCH_FAIL;
		}
	}
```
###### W10-4J\Handler\Undo.java
``` java
	private COMMAND_STATE commandState;
	private Task forCurrentTask; //currentTask the command is working on which will be updated in the HandlerMemory later.
	private Task forOldTask;//The Task which after this command will be an oldTask to be stored in the previousInputStorage by HandlerMemory.
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}

	public String execute_OLD(String[] notUsedInThisCommand, int notUsedInThisCommand2) {
		if (HandlerMemory.getPreviousInputStorage_OLD().size() == 0) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_UNDO_FAIL;
		}
		String actionToBeUndone = HandlerMemory.getPreviousInputStorage_OLD().get(0).getAction();
		Task previousTask = HandlerMemory.getPreviousInputStorage_OLD().get(0).getTask();
		assert actionToBeUndone != null : Constants.ASSERT_ACTION_EXISTENCE;
		assert previousTask != null : Constants.ASSERT_TASK_EXISTENCE;
		undoTheAction(actionToBeUndone, previousTask);
		return Constants.MESSAGE_UNDO_PASS;
	}
	
	private void undoTheAction(String actionToBeUndone, Task previousTask) {
		switch (actionToBeUndone) {
		case Constants.MESSAGE_ACTION_ADD:
			commandState = COMMAND_STATE.UNDOADD;
			forCurrentTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			commandState = COMMAND_STATE.UNDODELETE;
			forCurrentTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			commandState = COMMAND_STATE.UNDOEDIT;
			forCurrentTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_DONE:
			commandState = COMMAND_STATE.UNDODONE;
			forCurrentTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			commandState = COMMAND_STATE.UNDOUNDO;
			forCurrentTask = previousTask;
			break;
		}
	}
```
