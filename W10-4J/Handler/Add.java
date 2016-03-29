package Handler;

import main.Constants;
import main.Constants.COMMAND_STATE;
import main.Task;

public class Add implements Command {

	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Add(/*
				 * ArrayList<Task> notDoneYetStorage, ArrayList<Task>
				 * doneStorage, /*ArrayList<PreviousInput> previousInputStorage,
				 * Storage mainStorage,
				 */HandlerMemory handlerMemory) {

		this.handlerMemory = handlerMemory;
	}

	public String execute(String[] task, int taskID) {
		assert task[0] != null : Constants.ASSERT_FIELD_EXISTENCE;
		Task eachTask = new Task(task[0].trim());
		assert taskID > 0 : Constants.ASSERT_TASKID_EXISTENCE;
		eachTask.setTaskID(taskID);
		String action;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i].trim();
			assert action != null : Constants.ASSERT_ACTION_EXISTENCE;
			switch (action) {
			case Constants.MESSAGE_ADD_ACTION_DATE:
				eachTask.setDate(task[i + 1].trim());
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
				switch (task[i + 1].trim()) {
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
		// System.out.println(eachTask);
		if (isTimeValid(eachTask)) {

			commandState = Constants.COMMAND_STATE.PASS;
			forEachTask = eachTask;
			return String.format(Constants.MESSAGE_ADD_PASS, eachTask.getName());
		} else {
			commandState = Constants.COMMAND_STATE.FAILED;
			return Constants.MESSAGE_TIME_FAIL;
		}
	}

	private boolean isTimeValid(Task task) {
		int starttime = task.getStartTimeInt();
		int endtime = task.getEndTimeInt();
		if (starttime != -1 && endtime != -1) {
			return endtime > starttime;
		} else {
			return true;
		}
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