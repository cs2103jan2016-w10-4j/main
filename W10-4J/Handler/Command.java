//@@author A0149174Y
package Handler;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Task;

public interface Command {
	
	String execute(String[] task);
	//@@author A0149174Y-unused
	Task returnCurrentTask(); //The task the command is currently editing.
	COMMAND_STATE returnCommandState(); //Current state of the command execute function.
	Task returnOldTask(); //The old Task the command is editing or reaching.
}