//@@author A0135779M

package Handler;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;
import main.Task;

public class Recurrence implements Command {
	
	///////UNUSED////////
	//@@author A0149174Y-unused
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
			Task oldTask = cloneTask(eachTask,taskID); //oldTask will be stored as PreviousInput later.
			setRecurringDurationForEachTask(task, eachTask);
			forEachTask = eachTask;
			forOldTask = oldTask;
			return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
		}
	}

	private void setRecurringDurationForEachTask(String[] task, Task eachTask) {
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
	}
	//@@author
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
		arraylistStorage_.writeToStorage();
		return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
	}
}
