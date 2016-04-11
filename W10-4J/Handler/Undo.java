//@@author A0135779M
package Handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;
import main.Task;

public class Undo implements Command{
	
///////UNUSED////////
	//@@author A0149174Y-unused
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

	public String execute_OLD(String[] notUsedInThisCommand, int notUsedInThisCommand2) {
		if (HandlerMemory.getPreviousInputStorage_OLD().size() == 0) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_UNDO_FAIL;
		}
		String actionToBeUndone = HandlerMemory.getPreviousInputStorage_OLD().get(0).getAction();
		Task previousTask = HandlerMemory.getPreviousInputStorage_OLD().get(0).getTask();
		assert actionToBeUndone != null : Constants.ASSERT_ACTION_EXISTENCE;
		assert previousTask != null : Constants.ASSERT_TASK_EXISTENCE;
		undoTheAction(actionToBeUndone, previousTask);
		return Constants.MESSAGE_UNDO_PASS;
	}
	
	private void undoTheAction(String actionToBeUndone, Task previousTask) {
		switch (actionToBeUndone) {
		case Constants.MESSAGE_ACTION_ADD:
			commandState = COMMAND_STATE.UNDOADD;
			forCurrentTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			commandState = COMMAND_STATE.UNDODELETE;
			forCurrentTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			commandState = COMMAND_STATE.UNDOEDIT;
			forCurrentTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_DONE:
			commandState = COMMAND_STATE.UNDODONE;
			forCurrentTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			commandState = COMMAND_STATE.UNDOUNDO;
			forCurrentTask = previousTask;
			break;
		}
	}
	//@@author
  ///////UNUSED////////
	
	//@@author A0135779M
	ArraylistStorage arraylistStorage_;
	private final Logger LOGGER = Logger.getLogger(Undo.class.getName());
	
	public Undo(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] notUsedInThisCommand) {
		LOGGER.log(Level.INFO, Constants.MESSAGE_FUNCTION_EXECUTION);
		String actionToBeUndone;
		try{
			actionToBeUndone = arraylistStorage_.getPreviousInputAction();
		} catch(IndexOutOfBoundsException e){
			LOGGER.log(Level.WARNING, Constants.MESSAGE_UNDO_FAIL);
			return Constants.MESSAGE_UNDO_FAIL;
		}
		assert actionToBeUndone != null : Constants.ASSERT_ACTION_EXISTENCE;
		switch (actionToBeUndone) {
		
		case Constants.MESSAGE_ACTION_BASICOP:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			LOGGER.log(Level.INFO, Constants.MESSAGE_PREVIOUSSTATE_STORED);
			arraylistStorage_.setNewStorages();
			LOGGER.log(Level.INFO, Constants.MESSAGE_EDITARRAYLISTSTORAGE);
			break;
		
		//@@author
			
		/////UNUSED/////
		//@@author A0135779M-unused	
		case Constants.MESSAGE_ACTION_ADD:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_DELETE);
			arraylistStorage_.setNewStorages();
			break;
			
		case Constants.MESSAGE_ACTION_DELETE:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_ADD);
			arraylistStorage_.setNewStorages();
			break;
			
		case Constants.MESSAGE_ACTION_EDIT:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_EDIT);
			arraylistStorage_.setNewStorages();
			break;
			
		case Constants.MESSAGE_ACTION_DONE:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_UNDO);
			arraylistStorage_.setNewStorages();
			break;
			
		case Constants.MESSAGE_ACTION_UNDO:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_DONE);
			arraylistStorage_.setNewStorages();
			break;
		
		case Constants.MESSAGE_ACTION_RETRIEVE:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_UNRETRIEVE);
			arraylistStorage_.setNewStorages();
			break;
			
		case Constants.MESSAGE_ACTION_UNRETRIEVE:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_RETRIEVE);
			arraylistStorage_.setNewStorages();
			break;
			
		//@@author
		/////UNUSED/////
			
		//@@author A0135779M
		case Constants.MESSAGE_ACTION_SETDIR:
			arraylistStorage_.getNewDirectory();
			arraylistStorage_.rememberOldDirectory();
			LOGGER.log(Level.INFO, Constants.MESSAGE_DIRECTORY_REMEMBERED);
			arraylistStorage_.addPreviousDirectory(Constants.MESSAGE_ACTION_SETDIR);
			LOGGER.log(Level.INFO, Constants.MESSAGE_PREVIOUSSTATE_STORED);
			arraylistStorage_.setNewDirectory();
			break;
		//@@author
			
		/////UNUSED/////	
		//@@author A0135779M-unused
		case Constants.MESSAGE_ACTION_UNSETDIR:
			arraylistStorage_.rememberOldDirectory();
			arraylistStorage_.getNewDirectory();
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousDirectory(Constants.MESSAGE_ACTION_SETDIR);
			arraylistStorage_.setNewDirectory();
			arraylistStorage_.setNewStorages();
			break;
		//@@author
		/////UNUSED/////	
			
		//@@author A0135779M
		}
		// write to mainStorage
		arraylistStorage_.writeToStorage();
		LOGGER.log(Level.INFO, Constants.MESSAGE_WRITETOSTORAGE);
		return Constants.MESSAGE_UNDO_PASS;
	}
}
