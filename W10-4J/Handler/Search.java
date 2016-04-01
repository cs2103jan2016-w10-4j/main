package Handler;

import main.Constants;

import java.util.ArrayList;
import main.Task;

public class Search implements Command {
	ArraylistStorage arraylistStorage_;

	public Search(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		ArrayList<Task> results = new ArrayList<Task>();
		// each task is certain to have a name
		// check whether exclude field exists
		if (task.length > 1) {
			results = arraylistStorage_.searchNotDoneStorage(task, true);
		} else {
			results = arraylistStorage_.searchNotDoneStorage(task, false);
		}
		if (results.size() != 0) {
			return DisplayDefault.displayDefaultFormat(results);
		}
		return Constants.MESSAGE_SEARCH_FAIL;
	}
}
