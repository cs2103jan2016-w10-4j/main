//@@author A0135779M
package Handler;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;
import main.Task;

public class Add implements Command {
	
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
	//@@author
    ///////UNUSED////////
	
	//@@author A0135779M
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
				switch (task[i + 1]) {
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
			default:
				assert false;
			}
		}
		if (isDateAndTimeValid(eachTask)) {
			// remember previous state via arraylistStorage
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			// add to arraylist storage
			arraylistStorage_.addTaskToNotDoneStorage(eachTask);
			// write to mainStorage via arraylistStorage
			arraylistStorage_.writeToStorage();
			return String.format(Constants.MESSAGE_ADD_PASS, eachTask.getName());
		} else {
			return Constants.MESSAGE_TIME_FAIL;
		}
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