package Handler;

import main.Constants;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;

public class Search implements Command {
	private ArrayList<Task> notDoneYetStorage;
	Storage mainStorage;

	public Search(ArrayList<Task> notDoneYetStorage, Storage mainStorage) {
		this.notDoneYetStorage = notDoneYetStorage;
		this.mainStorage = mainStorage;
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		ArrayList<Task> results = new ArrayList<Task>();
		// each task is certain to have a name
		// check whether exclude field exists
		if (task.length > 1) {
			for (Task eachTask : notDoneYetStorage) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				inclusiveSearch(eachTask, task, results);
			}
		} else {
			for (Task eachTask : notDoneYetStorage) {
				assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
				exclusiveSearch(eachTask, task, results);
			}
		}
		if (results.size() != 0) {
			return DisplayDefault.displayDefaultFormat(results);
		}
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
}
