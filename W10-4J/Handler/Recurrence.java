
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
		Task eachTask = arraylistStorage_.findByTaskIDNotDoneStorage(taskID);
		Task oldTask = cloneTask(eachTask);
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
		arraylistStorage_.addTaskToPreInputStorage(new PreviousInput(Constants.MESSAGE_ACTION_EDIT, oldTask, eachTask));
		return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
	}

	public Task cloneTask(Task task) {
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
}
