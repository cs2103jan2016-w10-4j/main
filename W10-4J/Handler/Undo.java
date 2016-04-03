package Handler;


import main.Constants;
import main.Task;

public class Undo implements Command{
	ArraylistStorage arraylistStorage_;
	
	public Undo(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] notUsedInThisCommand) {
		String actionToBeUndone = arraylistStorage_.getPreviousInputAction();
		Task previousTask;
		Task eachTask;
		assert actionToBeUndone != null : Constants.ASSERT_ACTION_EXISTENCE;
		switch (actionToBeUndone) {
		
		case Constants.MESSAGE_ACTION_ADD:
			previousTask = arraylistStorage_.getPreviousInputTask();
			arraylistStorage_.delTaskFromNotDoneStorage(previousTask);
			arraylistStorage_.addTaskToPreInputStorage(new PreviousInput(Constants.MESSAGE_ACTION_DELETE, previousTask));
			break;
			
		case Constants.MESSAGE_ACTION_DELETE:
			previousTask = arraylistStorage_.getPreviousInputTask();
			arraylistStorage_.addTaskToNotDoneStorage(previousTask);
			arraylistStorage_.addTaskToPreInputStorage(new PreviousInput(Constants.MESSAGE_ACTION_ADD, previousTask));
			break;
			
		case Constants.MESSAGE_ACTION_EDIT:
			previousTask = arraylistStorage_.getPreviousInputTask();
			eachTask = arraylistStorage_.getPreviousInputEditedTask();
			arraylistStorage_.delTaskFromNotDoneStorage(eachTask);
			arraylistStorage_.addTaskToNotDoneStorage(previousTask);
			arraylistStorage_.addTaskToPreInputStorage(new PreviousInput(Constants.MESSAGE_ACTION_EDIT, eachTask, previousTask));
			break;
			
		case Constants.MESSAGE_ACTION_DONE:
			previousTask = arraylistStorage_.getPreviousInputTask();
			arraylistStorage_.delTaskFromDoneStorage(previousTask);
			arraylistStorage_.addTaskToNotDoneStorage(previousTask);
			arraylistStorage_.addTaskToPreInputStorage(new PreviousInput(Constants.MESSAGE_ACTION_UNDO, previousTask));
			break;
			
		case Constants.MESSAGE_ACTION_UNDO:
			previousTask = arraylistStorage_.getPreviousInputTask();
			arraylistStorage_.delTaskFromNotDoneStorage(previousTask);
			arraylistStorage_.addTaskToDoneStorage(previousTask);
			arraylistStorage_.addTaskToPreInputStorage(new PreviousInput(Constants.MESSAGE_ACTION_DONE, previousTask));
			break;
		
		case Constants.MESSAGE_ACTION_RETRIEVE:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_UNRETRIEVE);
			arraylistStorage_.setNewStorages();
			arraylistStorage_.writeToStorage();
			break;
			
		case Constants.MESSAGE_ACTION_UNRETRIEVE:
			arraylistStorage_.rememberPreviousStorages();
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_RETRIEVE);
			arraylistStorage_.setNewStorages();
			arraylistStorage_.writeToStorage();
			break;
			
		case Constants.MESSAGE_ACTION_SETDIR:
			
			break;
			
		case Constants.MESSAGE_ACTION_UNSETDIR:
			break;
			
		}
		// write to mainStorage
		arraylistStorage_.writeToStorage();
		return Constants.MESSAGE_UNDO_PASS;
	}
}
