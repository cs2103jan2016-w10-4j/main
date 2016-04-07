
package Handler;

import main.Constants;
import main.Task;

public class Recurrence implements Command {

	ArraylistStorage arraylistStorage_;

	public Recurrence(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = arraylistStorage_.getTaskByIndex(taskID - 1);
		int recurCounter = Integer.parseInt((task[2]));
		// Task oldTask = cloneTask(eachTask);
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
		if (recurCounter != 0 && eachTask.getStartDate() == null) {
			eachTask.resetRecursion();
			return Constants.MESSAGE_RECUR_FAIL;
		}
		Task clone = cloneTask(eachTask, arraylistStorage_.getTaskID());
		for (int i = 0; i < recurCounter - 1; i++) {
			clone = cloneTask(clone, arraylistStorage_.getTaskID());
			clone.nextDate();
			arraylistStorage_.addTaskToNotDoneStorage(clone);
		}
		arraylistStorage_.writeToStorage();
		// arraylistStorage_.addTaskToPreInputStorage(new
		// PreviousInput(Constants.MESSAGE_ACTION_EDIT, oldTask, eachTask));
		return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
	}

	public Task cloneTask(Task task, int taskID) {
		Task result = new Task(task.getName());
		result.setStartDate(task.getStartDate());
		result.setEndDate(task.getEndDate());
		result.setStartTime(task.getStartTime());
		result.setEndTime(task.getEndTime());
		result.setDetails(task.getDetails());
		result.setTaskID(taskID);
		result.setYear(task.getYear());
		result.setMonth(task.getMonth());
		result.setWeek(task.getWeek());
		result.setDay(task.getDay());
		return result;
	}
}
