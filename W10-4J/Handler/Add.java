//@@author A0135779M

package Handler;

import main.Constants;
import main.Task;

public class Add implements Command {
	ArraylistStorage arraylistStorage_;

	public Add(ArraylistStorage arraylistStorage) {
		this.arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		assert task[0] != null : Constants.ASSERT_FIELD_EXISTENCE;
		Task eachTask = new Task(task[0].trim());
		int taskID = arraylistStorage_.getTaskID();
		assert taskID > 0 : Constants.ASSERT_TASKID_EXISTENCE;
		eachTask.setTaskID(taskID);
		String action;
		int recurCounter = 0;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i].trim();
			assert action != null : Constants.ASSERT_ACTION_EXISTENCE;
			switch (action) {
			case Constants.MESSAGE_ADD_ACTION_STARTDATE:
				eachTask.setStartDate(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_ENDDATE:
				eachTask.setEndDate(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_START:
				eachTask.setStartTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_END:
				eachTask.setEndTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_DETAILS:
				eachTask.setDetails(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_REPEAT:
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
			default:
				assert false;
			}
		}
		if (recurCounter != 0 && eachTask.getStartDate() == null) {
			return Constants.MESSAGE_RECUR_FAIL;
		}
		if (isDateAndTimeValid(eachTask)) {
			// remember previous state via arraylistStorage
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			// add to arraylist storage
			arraylistStorage_.addTaskToNotDoneStorage(eachTask);
			Task clone = cloneTask(eachTask, arraylistStorage_.getTaskID());
			for (int i = 0; i < recurCounter - 1; i++) {
				clone = cloneTask(clone, arraylistStorage_.getTaskID());
				clone.nextDate();
				arraylistStorage_.addTaskToNotDoneStorage(clone);
			}
			// write to mainStorage via arraylistStorage
			arraylistStorage_.writeToStorage();
			return String.format(Constants.MESSAGE_ADD_PASS, eachTask.getName());
		} else {
			return Constants.MESSAGE_TIME_FAIL;
		}
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
		switch(task.isDateValid()){
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