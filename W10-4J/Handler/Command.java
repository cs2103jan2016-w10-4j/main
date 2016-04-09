//@@author A0149174Y
package Handler;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Task;

public interface Command {
	
	String execute(String[] task);
	//@@author A0149174Y-unused
	Task returnEachTask();
	COMMAND_STATE returnCommandState();
	Task returnOldTask();
}