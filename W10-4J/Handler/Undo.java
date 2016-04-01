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
		Task previousTask = arraylistStorage_.getPreviousInputTask();
		assert actionToBeUndone != null : Constants.ASSERT_ACTION_EXISTENCE;
		assert previousTask != null : Constants.ASSERT_TASK_EXISTENCE;
		switch (actionToBeUndone) {
		
		case Constants.MESSAGE_ACTION_ADD:
			arraylistStorage_.delTaskFromNotDoneStorage(previousTask);
			arraylistStorage_.addTaskToPreInputStorage(new PreviousInput(Constants.MESSAGE_ACTION_DELETE, previousTask));
			break;
			
		case Constants.MESSAGE_ACTION_DELETE:
			arraylistStorage_.addTaskToNotDoneStorage(previousTask);
			arraylistStorage_.addTaskToPreInputStorage(new PreviousInput(Constants.MESSAGE_ACTION_ADD, previousTask));
			break;
			
		case Constants.MESSAGE_ACTION_EDIT:
			Task eachTask = arraylistStorage_.getPreviousInputEditedTask();
			arraylistStorage_.delTaskFromNotDoneStorage(eachTask);
			arraylistStorage_.addTaskToNotDoneStorage(previousTask);
			arraylistStorage_.addTaskToPreInputStorage(new PreviousInput(Constants.MESSAGE_ACTION_EDIT, eachTask, previousTask));
			break;
			
		case Constants.MESSAGE_ACTION_DONE:
			arraylistStorage_.delTaskFromDoneStorage(previousTask);
			arraylistStorage_.addTaskToNotDoneStorage(previousTask);
			arraylistStorage_.addTaskToPreInputStorage(new PreviousInput(Constants.MESSAGE_ACTION_UNDO, previousTask));
			break;
			
		case Constants.MESSAGE_ACTION_UNDO:
			arraylistStorage_.delTaskFromNotDoneStorage(previousTask);
			arraylistStorage_.addTaskToDoneStorage(previousTask);
			arraylistStorage_.addTaskToPreInputStorage(new PreviousInput(Constants.MESSAGE_ACTION_DONE, previousTask));
			break;
		}
		// write to mainStorage
		arraylistStorage_.writeToStorage();
		return Constants.MESSAGE_UNDO_PASS;
	}
}
