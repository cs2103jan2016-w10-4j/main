//@@author Berkin
package Handler;

import main.Constants;
import main.Task;
import main.Constants.COMMAND_STATE;

public class Recurrence implements Command {

	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Recurrence(HandlerMemory handlerMemory) {
		this.handlerMemory = handlerMemory;
	}
	// @@author

	public String execute(String[] task, int notUsedInThisCommand) {
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = handlerMemory.findByTaskID(handlerMemory.getNotDoneYetStorage(), taskID);
		if (eachTask == null) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_RECURRENCE_FAIL;
		} else {
			Task oldTask = cloneTask(eachTask);
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
			forOldTask = oldTask;
			return String.format(Constants.MESSAGE_RECURRENCE_PASS, eachTask.getName());
		}
	}

	public Task cloneTask(Task task) {
		Task result = new Task(task.getName());
		result.setDate(task.getDate());
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

	public Task returnEachTask() {
		return forEachTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}
}
