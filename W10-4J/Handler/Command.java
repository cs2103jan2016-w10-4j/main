
package Handler;

import main.Constants.COMMAND_STATE;
import main.Task;
//@@author Berkin
public interface Command {
	
	String execute(String[] task, int taskID);
	COMMAND_STATE returnCommandState();
	Task returnEachTask();
	Task returnOldTask();
}
// @@author