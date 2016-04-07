//@@author A0135779M

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
		results = arraylistStorage_.searchNotDoneStorage(task);
		if (results.size() != 0) {
			return DisplayTableFormat.displayTableFormat(results, arraylistStorage_.getPreviousInputStorage());
		}
		return Constants.MESSAGE_SEARCH_FAIL;
	}
}
