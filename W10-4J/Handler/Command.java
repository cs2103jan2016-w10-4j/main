
package Handler;

import main.Constants.COMMAND_STATE;
import main.Task;

//@@author A0149174Y
public interface Command {
	String execute(String[] task, int taskID);

	COMMAND_STATE returnCommandState();
	Task returnEachTask();
	Task returnOldTask();
}
// @@author