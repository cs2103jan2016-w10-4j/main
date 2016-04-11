//@@author A0135779M
package Handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import Handler.HandlerMemory.COMMAND_STATE;
import main.*;

public class SetDir implements Command {
	
///////UNUSED////////
	private COMMAND_STATE commandState;
	private Task forCurrentTask; //currentTask the command is working on which will be updated in the HandlerMemory later.
	private Task forOldTask;//The Task which after this command will be an oldTask to be stored in the previousInputStorage by HandlerMemory.
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
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
	private final Logger LOGGER = Logger.getLogger(SetDir.class.getName());
	
	public SetDir(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}
	
	public String execute(String[] task) {
		LOGGER.log(Level.INFO, Constants.MESSAGE_FUNCTION_EXECUTION);
		assert task[0] != null : Constants.ASSERT_FIELD_EXISTENCE;
		arraylistStorage_.rememberOldDirectory();
		LOGGER.log(Level.INFO, Constants.MESSAGE_EDITARRAYLISTSTORAGE);
		arraylistStorage_.addPreviousDirectory(Constants.MESSAGE_ACTION_SETDIR);
		LOGGER.log(Level.INFO, Constants.MESSAGE_PREVIOUSSTATE_STORED);
  		if (arraylistStorage_.setDirectory(task[0])) {
  			return Constants.MESSAGE_SETDIR_PASS;
  		} else {
  			return Constants.MESSAGE_SETDIR_FAIL;
  		}
  	}
}