//@@author A0135779M
package Handler;

import main.Task;
import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;

public class Edit implements Command {
	
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
			fieldEditor(eachTask, task); //edits the task.
			return checkWhetherDateAndTimeValidAndReturnMessage(eachTask, oldTask);
		}
	}
	
	private String checkWhetherDateAndTimeValidAndReturnMessage(Task eachTask, Task oldTask) {
		if (isDateAndTimeValid(eachTask)) {
			forEachTask = eachTask;
			forOldTask = oldTask;
			return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
		} else {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_TIME_FAIL;
		}
	}
	//@@author
  ///////UNUSED////////
	
	//@@author A0135779M
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
			fieldEditor(eachTask, task);
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

	private void fieldEditor(Task eachTask, String[] task) {
		String action;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i].trim();
			switch (action) {
			case Constants.MESSAGE_EDIT_ACTION_RENAME:
				eachTask.setName(task[i + 1]);
				break;
			case Constants.MESSAGE_EDIT_ACTION_STARTDATE:
				eachTask.setStartDate(task[i + 1]);
				break;
			case Constants.MESSAGE_EDIT_ACTION_ENDDATE:
				eachTask.setEndDate(task[i + 1]);
				break;
			case Constants.MESSAGE_EDIT_ACTION_START:
				eachTask.setStartTime(task[i + 1]);
				break;
			case Constants.MESSAGE_EDIT_ACTION_END:
				eachTask.setEndTime(task[i + 1]);
				break;
			case Constants.MESSAGE_EDIT_ACTION_DETAILS:
				eachTask.setDetails(task[i + 1]);
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
				break;
			}
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

	// @@author A0140114A
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
