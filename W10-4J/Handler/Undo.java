package Handler;


import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;
import main.Task;

public class Undo implements Command{
///////UNUSED////////
	//@@author A0149174Y-unused
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

	public String execute_OLD(String[] notUsedInThisCommand, int notUsedInThisCommand2) {
		if (HandlerMemory.getPreviousInputStorage_OLD().size() == 0) {
			commandState = COMMAND_STATE.FAILED;
			return Constants.MESSAGE_UNDO_FAIL;
		}
		String actionToBeUndone = HandlerMemory.getPreviousInputStorage_OLD().get(0).getAction();
		Task previousTask = HandlerMemory.getPreviousInputStorage_OLD().get(0).getTask();
		assert actionToBeUndone != null : Constants.ASSERT_ACTION_EXISTENCE;
		assert previousTask != null : Constants.ASSERT_TASK_EXISTENCE;
		Task eachTask = null;
		switch (actionToBeUndone) {
		case Constants.MESSAGE_ACTION_ADD:
			commandState = COMMAND_STATE.UNDOADD;
			forEachTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			commandState = COMMAND_STATE.UNDODELETE;
			forEachTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			commandState = COMMAND_STATE.UNDOEDIT;
			forEachTask = previousTask;
			forOldTask = eachTask;
			break;
		case Constants.MESSAGE_ACTION_DONE:
			commandState = COMMAND_STATE.UNDODONE;
			forEachTask = previousTask;
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			commandState = COMMAND_STATE.UNDOUNDO;
			forEachTask = previousTask;
			break;
		}
		return Constants.MESSAGE_UNDO_PASS;
	}
	//@@author
  ///////UNUSED////////
	//@@author A0135779M
	ArraylistStorage arraylistStorage_;
	
	public Undo(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] notUsedInThisCommand) {
		String actionToBeUndone;
		try{
			actionToBeUndone = arraylistStorage_.getPreviousInputAction();
		} catch(IndexOutOfBoundsException e){
			return Constants.MESSAGE_UNDO_FAIL;
		}
		assert actionToBeUndone != null : Constants.ASSERT_ACTION_EXISTENCE;
		switch (actionToBeUndone) {
		
		case Constants.MESSAGE_ACTION_BASICOP:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			arraylistStorage_.setNewStorages();
			break;
		
//		case Constants.MESSAGE_ACTION_ADD:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_DELETE);
//			arraylistStorage_.setNewStorages();
//			break;
//			
//		case Constants.MESSAGE_ACTION_DELETE:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_ADD);
//			arraylistStorage_.setNewStorages();
//			break;
//			
//		case Constants.MESSAGE_ACTION_EDIT:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_EDIT);
//			arraylistStorage_.setNewStorages();
//			break;
//			
//		case Constants.MESSAGE_ACTION_DONE:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_UNDO);
//			arraylistStorage_.setNewStorages();
//			break;
//			
//		case Constants.MESSAGE_ACTION_UNDO:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_DONE);
//			arraylistStorage_.setNewStorages();
//			break;
//		
//		case Constants.MESSAGE_ACTION_RETRIEVE:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_UNRETRIEVE);
//			arraylistStorage_.setNewStorages();
//			break;
//			
//		case Constants.MESSAGE_ACTION_UNRETRIEVE:
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_RETRIEVE);
//			arraylistStorage_.setNewStorages();
//			break;
			
		case Constants.MESSAGE_ACTION_SETDIR:
			arraylistStorage_.rememberOldDirectory();
			arraylistStorage_.getNewDirectory();
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousDirectory(Constants.MESSAGE_ACTION_SETDIR);
			arraylistStorage_.setNewDirectory();
			arraylistStorage_.setNewStorages();
			break;
			
//		case Constants.MESSAGE_ACTION_UNSETDIR:
//			arraylistStorage_.rememberOldDirectory();
//			arraylistStorage_.getNewDirectory();
//			arraylistStorage_.rememberPreviousStorages();
//			arraylistStorage_.addPreviousDirectory(Constants.MESSAGE_ACTION_SETDIR);
//			arraylistStorage_.setNewDirectory();
//			arraylistStorage_.setNewStorages();
//			break;
//			
		}
		// write to mainStorage
		arraylistStorage_.writeToStorage();
		return Constants.MESSAGE_UNDO_PASS;
	}
}
