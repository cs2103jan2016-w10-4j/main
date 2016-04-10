//@@author A0135779M
package Handler;

import main.Constants;

import java.util.ArrayList;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Task;

public class Search implements Command {
	
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
		ArrayList<Task> searchNotDoneYetStorage_OLD = new ArrayList<Task>();
		if (task.length > 1) {
			for (Task eachTask : HandlerMemory.getNotDoneYetStorage_OLD()) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				//inclusiveSearch(eachTask, task, searchNotDoneYetStorage_OLD);//new version does not support thism function.
			}
		} else {
			for (Task eachTask : HandlerMemory.getNotDoneYetStorage_OLD()) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				//exclusiveSearch(eachTask, task, searchNotDoneYetStorage_OLD);//new version does not support thism function.
			}
		}
		if (searchNotDoneYetStorage_OLD.size() != 0) {
			return DisplayFormat.displayFormat(searchNotDoneYetStorage_OLD);
		}
		commandState = COMMAND_STATE.FAILED;
		return Constants.MESSAGE_SEARCH_FAIL;
	}
	//@@author
///////UNUSED////////
	
	//@@author A0135779M
	ArraylistStorage arraylistStorage_;

	public Search(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		ArrayList<Task> results = new ArrayList<Task>();
		// each task is certain to have a name
		results = arraylistStorage_.searchNotDoneStorage(task);
		if (results.size() != 0) {
			return Constants.IS_DISPLAY_FLAG  + DisplayTableFormat.displayTableFormat(results, arraylistStorage_.getPreviousInputStorage());
		}
		return Constants.MESSAGE_SEARCH_FAIL;
	}
}