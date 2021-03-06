//@@author A0135779M
package Handler;

import main.Constants;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Task;

public class Search implements Command {
	
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
		ArrayList<Task> searchNotDoneYetStorage = new ArrayList<Task>();
		if (task.length > 1) {
			for (Task eachTask : HandlerMemory.getNotDoneYetStorage_OLD()) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				//inclusiveSearch(eachTask, task, searchNotDoneYetStorage_OLD);//new version does not support this method.
			}
		} else {
			for (Task eachTask : HandlerMemory.getNotDoneYetStorage_OLD()) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				//exclusiveSearch(eachTask, task, searchNotDoneYetStorage_OLD);//new version does not support this method.
			}
		}
		if (searchNotDoneYetStorage.size() != 0) {
			return DisplayFormat.displayFormat(searchNotDoneYetStorage);
		}
		else {
		commandState = COMMAND_STATE.FAILED;
		return Constants.MESSAGE_SEARCH_FAIL;
		}
	}
	//@@author
///////UNUSED////////
	
	//@@author A0135779M
	ArraylistStorage arraylistStorage_;
	private final Logger LOGGER = Logger.getLogger(Search.class.getName());

	public Search(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		LOGGER.log(Level.INFO, Constants.MESSAGE_FUNCTION_EXECUTION);
		ArrayList<Task> results = new ArrayList<Task>();
		// each task is certain to have a name
		results = arraylistStorage_.searchNotDoneStorage(task);
		LOGGER.log(Level.INFO, Constants.MESSAGE_SEARCHARRAYLISTSTORAGE);
		if (results.size() != 0) {
			LOGGER.log(Level.INFO, Constants.MESSAGE_SEARCH_PASS);
			return Constants.IS_DISPLAY_FLAG  + DisplayTableFormat.displayTableFormat(results, arraylistStorage_.getPreviousInputStorage());
		}
		return Constants.MESSAGE_SEARCH_FAIL;
	}
}