//@@author A0135779M

package Handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;
import main.Task;

public class Recurrence implements Command {
	
	///////UNUSED////////
	//@@author A0149174Y-unused
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
	//@@author
	
	//@@author A0135779M
	private Task cloneTask(Task task,int taskID) {
		Task result = new Task(task.getName());
		result.setStartDate(task.getStartDate());
		result.setEndDate(task.getEndDate());
		result.setStartTime(task.getStartTime());
		result.setEndTime(task.getEndTime());
		result.setDetails(task.getDetails());
		result.setTaskID(task.getTaskID());
		result.setYear(task.getYear());
		result.setMonth(task.getMonth());
		result.setWeek(task.getWeek());
		result.setDay(task.getDay());
		return result;
	}
    ///////UNUSED////////
	
	
	//@@author A0140114A
	ArraylistStorage arraylistStorage_;

	public Recurrence(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		
		LOGGER.log(Level.INFO, Constants.MESSAGE_FUNCTION_EXECUTION);
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = arraylistStorage_.getTaskByIndex(taskID - 1);
		switch (task[1]) {
		case Constants.MESSAGE_REPEAT_DAY:
			eachTask.setDay(true);
			break;
		case Constants.MESSAGE_REPEAT_WEEK:
			eachTask.setWeek(true);
			break;
		case Constants.MESSAGE_REPEAT_MONTH:
			eachTask.setMonth(true);
			break;
		case Constants.MESSAGE_REPEAT_YEAR:
			eachTask.setYear(true);
			break;
		}
		LOGGER.log(Level.INFO, Constants.MESSAGE_TASK_SET);
		arraylistStorage_.writeToStorage();
		LOGGER.log(Level.INFO, Constants.MESSAGE_WRITETOSTORAGE);
		return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
	}
}
