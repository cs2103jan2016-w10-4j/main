//@@author A0135779M
package Handler;

import main.Task;
import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;

public class Done implements Command {

	/////// UNUSED////////
	// @@author A0149174Y-unused
	private COMMAND_STATE commandState;
	private Task forCurrentTask; //currentTask the command is working on which will be updated in the HandlerMemory later.
	private Task forOldTask;//The Task which after this command will be an oldTask to be stored in the previousInputStorage by HandlerMemory.
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
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
		Task currentTask = handlerMemory.findByTaskID(handlerMemory.getNotDoneYetStorage_OLD(), taskID);
		if (currentTask == null) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_DONE_FAIL;
		} else {
			if (currentTask.isRecurring() && currentTask.getEndDate() != null) {
				forCurrentTask = currentTask;
				assert currentTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
				commandState = COMMAND_STATE.RECURRINGDONE;
				return String.format(Constants.MESSAGE_DONE_PASS, currentTask.getName());
			} else {
				assert currentTask != null : Constants.ASSERT_TASK_EXISTENCE;
				assert currentTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
				forCurrentTask = currentTask;
				commandState = COMMAND_STATE.NONRECURRINGDONE;
				return String.format(Constants.MESSAGE_DONE_PASS, currentTask.getName());
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
