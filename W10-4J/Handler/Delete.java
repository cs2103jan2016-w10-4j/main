//@@author A0135779M
package Handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;
import main.Task;

public class Delete implements Command {
	
///////UNUSED////////
	//@@author A0149174Y-unused
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
		Task currentTask = handlerMemory.findByTaskID(HandlerMemory.getNotDoneYetStorage_OLD(), taskID);
		if (currentTask == null) {
			currentTask = handlerMemory.findByTaskID(HandlerMemory.getDoneStorage_OLD(), taskID);
			if (currentTask == null) {
				commandState = HandlerMemory.COMMAND_STATE.FAILED;
				return Constants.MESSAGE_DELETE_FAIL;
			} else {
				forCurrentTask = currentTask;
				commandState = HandlerMemory.COMMAND_STATE.DELETEDONETASK;
				return String.format(Constants.MESSAGE_DELETE_PASS, currentTask.getName());
			}
		} else if (taskID <= 0 || taskID > HandlerMemory.getTaskID()) {
			commandState = HandlerMemory.COMMAND_STATE.FAILED;
			return Constants.MESSAGE_DELETE_FAIL;
		} else {
			assert currentTask != null : Constants.ASSERT_TASK_EXISTENCE;
			forCurrentTask = currentTask;
			commandState = HandlerMemory.COMMAND_STATE.DELETEUNDONETASK;
			return String.format(Constants.MESSAGE_DELETE_PASS, currentTask.getName());
		}
	}
	//@@author 
  ///////UNUSED////////
	
	//@@author A0135779M
	ArraylistStorage arraylistStorage_;
	private final Logger LOGGER = Logger.getLogger(Delete.class.getName());

	public Delete(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		LOGGER.log(Level.INFO, Constants.MESSAGE_FUNCTION_EXECUTION);
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = arraylistStorage_.getTaskByIndex(taskID - 1);
		LOGGER.log(Level.INFO, Constants.MESSAGE_TASK_EXISTENCE_CHECK);
		if (eachTask == null) {
			eachTask = arraylistStorage_.getDoneTaskByIndex(taskID - 1 - arraylistStorage_.getNotDoneStorageSize());
			if (eachTask == null) {
				return Constants.MESSAGE_DELETE_FAIL;
			} else {
				// remember previous state
				arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
				LOGGER.log(Level.INFO, Constants.MESSAGE_PREVIOUSSTATE_STORED);
				arraylistStorage_.delTaskFromDoneStorage(eachTask);
				LOGGER.log(Level.INFO, Constants.MESSAGE_TASK_REMOVEDFROMSTORAGE);
				arraylistStorage_.writeToStorage();
				LOGGER.log(Level.INFO, Constants.MESSAGE_WRITETOSTORAGE);
				return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
			}
		} else if (taskID <= 0) {
			LOGGER.log(Level.WARNING, Constants.MESSAGE_INVALIDFIELD);
			return Constants.MESSAGE_DELETE_FAIL;
		} else {
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			// remember previous state
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			LOGGER.log(Level.INFO, Constants.MESSAGE_PREVIOUSSTATE_STORED);
			arraylistStorage_.delTaskFromNotDoneStorage(eachTask);
			LOGGER.log(Level.INFO, Constants.MESSAGE_TASK_REMOVEDFROMSTORAGE);
			// write to mainStorage
			arraylistStorage_.writeToStorage();
			LOGGER.log(Level.INFO, Constants.MESSAGE_WRITETOSTORAGE);
			return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
		}
	}
}
