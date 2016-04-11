//@@author A0135779M
package Handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;
import main.Task;

public class Retrieve implements Command{
	
///////UNUSED////////
	//@@author A0149174Y-unused
	private COMMAND_STATE commandState;
	private Task forCurrentTask;
	private Task forOldTask;
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
	//@@author 
///////UNUSED////////
	
	//@@author A0135779M
	ArraylistStorage arraylistStorage_;
	private final Logger LOGGER = Logger.getLogger(Retrieve.class.getName());
	
	public Retrieve(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		LOGGER.log(Level.INFO, Constants.MESSAGE_FUNCTION_EXECUTION);
		try {
			assert task[0] != null : Constants.ASSERT_FIELD_EXISTENCE;
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			LOGGER.log(Level.INFO, Constants.MESSAGE_PREVIOUSSTATE_STORED);
			arraylistStorage_.combineArrays(task[0]);
			arraylistStorage_.writeToStorage();
			LOGGER.log(Level.INFO, Constants.MESSAGE_WRITETOSTORAGE);
			return Constants.MESSAGE_RETRIEVE_PASS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.MESSAGE_RETRIEVE_FAIL;
	}
}