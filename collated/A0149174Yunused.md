# A0149174Yunused
###### W10-4J\Handler\Add.java
``` java
	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Task returnEachTask() {
		return forEachTask;
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
	
	private String checkWhetherDateAndTimeValidAndReturnMessage(Task eachTask) {
		if (isDateAndTimeValid(eachTask)) {
			forEachTask = eachTask;
			return String.format(Constants.MESSAGE_ADD_PASS, eachTask.getName());
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
	Task returnEachTask();
	COMMAND_STATE returnCommandState();
	Task returnOldTask();
}
```
###### W10-4J\Handler\Delete.java
``` java
	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Task returnEachTask() {
		return forEachTask;
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
		Task eachTask = handlerMemory.findByTaskID(HandlerMemory.getNotDoneYetStorage_OLD(), taskID);
		if (eachTask == null) {
			eachTask = handlerMemory.findByTaskID(HandlerMemory.getDoneStorage_OLD(), taskID);
			if (eachTask == null) {
				commandState = HandlerMemory.COMMAND_STATE.FAILED;
				return Constants.MESSAGE_DELETE_FAIL;
			} else {
				forEachTask = eachTask;
				commandState = HandlerMemory.COMMAND_STATE.DELETEDONETASK;

				return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
			}
		} else if (taskID <= 0 || taskID > HandlerMemory.getTaskID()) {
			commandState = HandlerMemory.COMMAND_STATE.FAILED;
			return Constants.MESSAGE_DELETE_FAIL;
		} else {
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			forEachTask = eachTask;
			commandState = HandlerMemory.COMMAND_STATE.DELETEUNDONETASK;

			return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
		}
	}
```
###### W10-4J\Handler\Done.java
``` java
	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Task returnEachTask() {
		return forEachTask;
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
		Task eachTask = handlerMemory.findByTaskID(handlerMemory.getNotDoneYetStorage_OLD(), taskID);
		if (eachTask == null) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_DONE_FAIL;
		} else {
			if (eachTask.isRecurring() && eachTask.getEndDate() != null) {
				// eachTask.done(); //done() function was implemented in the
				// previous version.
				forEachTask = eachTask;
				assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
				commandState = COMMAND_STATE.RECURRINGDONE;
				return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
			} else {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
				forEachTask = eachTask;
				commandState = COMMAND_STATE.NONRECURRINGDONE;
				return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
			}
		}
	}
```
###### W10-4J\Handler\Edit.java
``` java
	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Task returnEachTask() {
		return forEachTask;
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
		Task eachTask = handlerMemory.findByTaskID(HandlerMemory.getNotDoneYetStorage_OLD(), taskID);
		if (eachTask == null) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_EDIT_FAIL;
		} else if (taskID <= 0 || taskID > HandlerMemory.getTaskID()) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_EDIT_FAIL;
		} else {
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			Task oldTask = cloneTask(eachTask);
			fieldEditor(eachTask, task);

			if (isDateAndTimeValid(eachTask)) {
				forEachTask = eachTask;
				forOldTask = oldTask;
				return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
			} else {
				commandState = COMMAND_STATE.FAILED;
				return Constants.MESSAGE_TIME_FAIL;
			}
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
			return Constants.MESSAGE_INVALID_FORMAT;
		} catch (IllegalStateException unrecognizedCommand) {
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
 *Basically it stores all the arraylists and updates all of them after each command operation. 
 *Rather than updating the memory in each execute function, the command just stores it's end state after it's executed.
 *According to which state the command ended in (forexample in FAIL state) the updateMemory function decides how to update the memory accordingly.
 *After V0.3 this design was decided to be changed by my teammates. Now the class ArrayListStorage handles the job of HandlerMemory.
 *Now I changed my execute functions as execute_OLD and change all the ArrayList names as _OLD as well so that it wont collide with the new version.
 */
package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Constants;
import main.Constants.COMMAND_TYPE;
import main.Task;

public class HandlerMemory {

	public enum COMMAND_STATE {
		FAILED,UNDOADD,UNDODELETE,UNDOUNDO,UNDODONE,UNDOEDIT,DELETEDONETASK,DELETEUNDONETASK,RECURRINGDONE,NONRECURRINGDONE
	};
	
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
			System.out.println(cmd.returnCommandState());
			break;
		}
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
	}

	private static void updateMemoryUndoUndo(Command cmd) {
		notDoneYetStorage_OLD.remove(cmd.returnEachTask());
		doneStorage_OLD.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoDone(Command cmd) {
		doneStorage_OLD.remove(cmd.returnEachTask());
		notDoneYetStorage_OLD.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_UNDO, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoEdit(Command cmd) {
		// to restore the previous state, must edit again
		Task eachTask = previousInputStorage_OLD.get(0).getEditedTask();
		notDoneYetStorage_OLD.remove(eachTask);
		notDoneYetStorage_OLD.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, eachTask, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoDelete(Command cmd) {
		notDoneYetStorage_OLD.add(cmd.returnEachTask());
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnEachTask()));
	}

	private static void updateMemoryUndoAdd(Command cmd) {
		// to restore to previous state, must unadd the task
		notDoneYetStorage_OLD.remove(cmd.returnEachTask());
		// remember previous state
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
	}

	private static void recurrenceUpdateMemory(Command cmd) {
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput("edit", cmd.returnOldTask(), cmd.returnEachTask()));
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
		notDoneYetStorage_OLD.remove(cmd.returnEachTask());
		doneStorage_OLD.add(cmd.returnEachTask());
		updateMemoryForReccurringTask(cmd);
	}

	private static void updateMemoryForReccurringTask(Command cmd) {
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DONE, cmd.returnEachTask()));
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
		notDoneYetStorage_OLD.remove(cmd.returnEachTask());
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
	}

	private static void updateMemoryForDeletedDoneTask(Command cmd) {
		doneStorage_OLD.remove(cmd.returnEachTask());
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, cmd.returnEachTask()));
	}

	private static void editUpdateMemory(Command cmd) {
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, cmd.returnOldTask(), cmd.returnEachTask()));
	}

	private static void addUpdateMemory(Command cmd) {
		clearAndAdd(previousInputStorage_OLD, new PreviousInput(Constants.MESSAGE_ACTION_ADD, cmd.returnEachTask()));
		notDoneYetStorage_OLD.add(cmd.returnEachTask());
		mainStorage_OLD.write(notDoneYetStorage_OLD, doneStorage_OLD);
	}
```
###### W10-4J\Handler\Recurrence.java
``` java
	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Task returnEachTask() {
		return forEachTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}

	public String execute_OLD(String[] task, int notUsedInThisCommand) {
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = handlerMemory.findByTaskID(handlerMemory.getNotDoneYetStorage_OLD(), taskID);
		if (eachTask == null) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_RECUR_FAIL;
		} else {
//			Task oldTask = cloneTask(eachTask,taskID);
			switch (task[1]) {
			case "day":
				eachTask.setDay(true);
				break;
			case "week":
				eachTask.setWeek(true);
				break;
			case "month":
				eachTask.setMonth(true);
				break;
			case "year":
				eachTask.setYear(true);
				break;
			}
			forEachTask = eachTask;
//			forOldTask = oldTask;
			return String.format(Constants.MESSAGE_RECUR_FAIL, eachTask.getName());
		}
	}
```
###### W10-4J\Handler\Search.java
``` java
	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Task returnEachTask() {
		return forEachTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}
	public String execute_OLD(String[] task, int notUsedInThisCommand) {
		ArrayList<Task> searchNotDoneYetStorage_OLD = new ArrayList<Task>();
		if (task.length > 1) {
			for (Task eachTask : HandlerMemory.getNotDoneYetStorage_OLD()) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				//inclusiveSearch(eachTask, task, searchNotDoneYetStorage_OLD);//new version does not support thism function.
			}
		} else {
			for (Task eachTask : HandlerMemory.getNotDoneYetStorage_OLD()) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				//exclusiveSearch(eachTask, task, searchNotDoneYetStorage_OLD);//new version does not support thism function.
			}
		}
		if (searchNotDoneYetStorage_OLD.size() != 0) {
			return DisplayFormat.displayFormat(searchNotDoneYetStorage_OLD);
		}
		commandState = COMMAND_STATE.FAILED;
		return Constants.MESSAGE_SEARCH_FAIL;
	}
```
###### W10-4J\Handler\Undo.java
``` java
	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Task returnEachTask() {
		return forEachTask;
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
		Task eachTask = null;
		switch (actionToBeUndone) {
		case Constants.MESSAGE_ACTION_ADD:
			commandState = COMMAND_STATE.UNDOADD;
			forEachTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			commandState = COMMAND_STATE.UNDODELETE;
			forEachTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			commandState = COMMAND_STATE.UNDOEDIT;
			forEachTask = previousTask;
			forOldTask = eachTask;
			break;
		case Constants.MESSAGE_ACTION_DONE:
			commandState = COMMAND_STATE.UNDODONE;
			forEachTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			commandState = COMMAND_STATE.UNDOUNDO;
			forEachTask = previousTask;
			break;
		}
		return Constants.MESSAGE_UNDO_PASS;
	}
```
