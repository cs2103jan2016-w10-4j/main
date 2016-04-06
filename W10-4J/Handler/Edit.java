//@@ A0135779M

package Handler;

import main.Task;
import main.Constants;

public class Edit implements Command {
	ArraylistStorage arraylistStorage_;

	public Edit(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = arraylistStorage_.getTaskByIndex(taskID - 1);
		if (eachTask == null) {
			return Constants.MESSAGE_EDIT_FAIL;
		} else if (taskID <= 0) {
			return Constants.MESSAGE_EDIT_FAIL;
		} else {
			// remember previous state
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			Task oldTask = cloneTask(eachTask);
			// edits the task
			int recurCounter = fieldEditor(eachTask, task); // fieldEditor edits the element itself
			if (recurCounter != -1 && eachTask.getStartDate() == null) {
				eachTask.resetRecursion();
				return Constants.MESSAGE_RECUR_FAIL;
			}
			Task clone = cloneTask(eachTask, arraylistStorage_.getTaskID());
			for (int i = 0; i < recurCounter - 1; i++) {
				clone = cloneTask(clone, arraylistStorage_.getTaskID());
				clone.nextDate();
				arraylistStorage_.addTaskToNotDoneStorage(clone);
			}
			if (isDateAndTimeValid(eachTask)) {
				// write to mainStorage
				arraylistStorage_.writeToStorage();
				return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
			} else {
				arraylistStorage_.delTaskFromNotDoneStorage(eachTask);
				arraylistStorage_.addTaskToNotDoneStorage(oldTask);
				return Constants.MESSAGE_TIME_FAIL;
			}
		}
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

	private int fieldEditor(Task eachTask, String[] task) {
		String action;
		int recurCounter = -1;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i].trim();
			switch (action) {
			case Constants.MESSAGE_EDIT_ACTION_RENAME:
				eachTask.setName(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_STARTDATE:
				eachTask.setStartDate(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_ENDDATE:
				eachTask.setEndDate(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_START:
				eachTask.setStartTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_END:
				eachTask.setEndTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_DETAILS:
				eachTask.setDetails(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_REPEAT:
				String[] repeatArgument = task[i + 1].split(" ");
				switch (repeatArgument[0]) {
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
				default:
					assert false;
				}
				recurCounter = Integer.parseInt(repeatArgument[1]);
				break;
			}
		}
		return recurCounter;
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
	//@@author A0140114A
	private boolean isDateAndTimeValid(Task task) {
		int starttime = task.getStartTimeInt();
		int endtime = task.getEndTimeInt();
		switch (task.isDateValid()) {
		case 1:
			return true;
		case -1:
			return false;
		case 0:
			if (starttime != -1 && endtime != -1) {
				return endtime > starttime;
			} else {
				return true;
			}
		}
		assert false;
		return false;
	}
}
