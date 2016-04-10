//@@author A0135779M
package Handler;

import Handler.HandlerMemory.COMMAND_STATE;
import main.*;

public class SetDir implements Command {
	
///////UNUSED////////
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
  ///////UNUSED////////
	
	//@@author A0135779M
	ArraylistStorage arraylistStorage_;
	
	public SetDir(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}
	
	public String execute(String[] task) {
		assert task[0] != null : Constants.ASSERT_FIELD_EXISTENCE;
		arraylistStorage_.rememberOldDirectory();
		arraylistStorage_.addPreviousDirectory(Constants.MESSAGE_ACTION_SETDIR);
  		if (arraylistStorage_.setDirectory(task[0])) {
  			return Constants.MESSAGE_SETDIR_PASS;
  		} else {
  			return Constants.MESSAGE_SETDIR_FAIL;
  		}
  	}
}