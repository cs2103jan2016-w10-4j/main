//@@author A0135779M
package Handler;

import main.Task;
import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;

public class Done implements Command {

	/////// UNUSED////////
	// @@author A0149174Y-unused
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
		Task eachTask = handlerMemory.findByTaskID(handlerMemory.getNotDoneYetStorage_OLD(), taskID);
		if (eachTask == null) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_DONE_FAIL;
		} else {
			if (eachTask.isRecurring() && eachTask.getEndDate() != null) {
				// eachTask.done(); //done() function was implemented in the
				// previous version.
				forEachTask = eachTask;
				assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
				commandState = COMMAND_STATE.RECURRINGDONE;
				return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
			} else {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
				forEachTask = eachTask;
				commandState = COMMAND_STATE.NONRECURRINGDONE;
				return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
			}
		}
	}
	// @@author
	/////// UNUSED////////

	// @@author A0135779M
	ArraylistStorage arraylistStorage_;

	public Done(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = arraylistStorage_.getTaskByIndex(taskID - 1);
		if (eachTask == null) {
			return Constants.MESSAGE_DONE_FAIL;
		} else if (eachTask.isRecurring()) {
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			eachTask.nextStartDate();
			eachTask.nextEndDate();
			arraylistStorage_.writeToStorage();
			assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
			return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
		} else {
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			// remember previous state
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			arraylistStorage_.delTaskFromNotDoneStorage(eachTask);
			arraylistStorage_.addTaskToDoneStorage(eachTask);
			// write to mainStorage
			arraylistStorage_.writeToStorage();
			// remember previous state
			// arraylistStorage_.addTaskToPreInputStorage(new
			// PreviousInput(Constants.MESSAGE_ACTION_DONE, eachTask));
			assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
			return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
		}
	}
}
