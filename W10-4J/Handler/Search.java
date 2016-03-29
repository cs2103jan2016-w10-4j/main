package Handler;

import java.util.ArrayList;

import main.Constants;
import main.Task;
import main.Constants.COMMAND_STATE;

public class Search implements Command {

	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Search(HandlerMemory handlerMemory) {
		this.handlerMemory = handlerMemory;
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		ArrayList<Task> searchNotDoneYetStorage = new ArrayList<Task>();
		// each task is certain to have a name
		// check whether exclude field exists
		if (task.length > 1) {
			for (Task eachTask : HandlerMemory.getNotDoneYetStorage()) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				inclusiveSearch(eachTask, task, searchNotDoneYetStorage);
			}
		} else {
			for (Task eachTask : HandlerMemory.getNotDoneYetStorage()) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				exclusiveSearch(eachTask, task, searchNotDoneYetStorage);
			}
		}
		if (searchNotDoneYetStorage.size() != 0) {
			return DisplayFormat.displayFormat(searchNotDoneYetStorage);
		}
		commandState = COMMAND_STATE.FAILED;
		return Constants.MESSAGE_SEARCH_FAIL;
	}

	private void inclusiveSearch(Task eachTask, String[] task, ArrayList<Task> searchNotDoneYetStorage) {
		if (eachTask.getDetails() == null) {
			if (searchName(eachTask, task, true)) {
				searchNotDoneYetStorage.add(eachTask);
			}
		} else {
			if (searchNameAndDetails(eachTask, task, true)) {
				searchNotDoneYetStorage.add(eachTask);
			}
		}
	}

	private void exclusiveSearch(Task eachTask, String[] task, ArrayList<Task> searchNotDoneYetStorage) {
		if (eachTask.getDetails() == null) {
			if (searchName(eachTask, task, false)) {
				searchNotDoneYetStorage.add(eachTask);
			}
		} else {
			if (searchNameAndDetails(eachTask, task, false)) {
				searchNotDoneYetStorage.add(eachTask);
			}
		}
	}

	// name field will always exist
	// for each task
	private boolean searchNameAndDetails(Task eachTask, String[] task, boolean excludeField) {
		// check whether exclude field exists
		assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
		assert eachTask.getDetails() != null : Constants.ASSERT_TASKDETAILS_EXISTENCE;
		if (excludeField) {
			if ((eachTask.getName().contains(task[0].trim()) && !eachTask.getName().contains(task[2].trim()))
					|| (eachTask.getDetails().contains(task[0].trim())
							&& !eachTask.getDetails().contains(task[2].trim()))) {
				return true;
			}
		} else {
			if (eachTask.getName().contains(task[0].trim()) || eachTask.getDetails().contains(task[0].trim())) {
				return true;
			}
		}
		return false;
	}

	// for each task
	private boolean searchName(Task eachTask, String[] task, boolean excludeField) {
		// check whether exclude field exists
		assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
		if (excludeField) {
			if ((eachTask.getName().contains(task[0].trim()) && !eachTask.getName().contains(task[2].trim()))) {
				return true;
			}
		} else {
			if (eachTask.getName().contains(task[0].trim())) {
				return true;
			}
		}
		return false;
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
