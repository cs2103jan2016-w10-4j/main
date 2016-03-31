package Handler;

import main.Constants;
import java.util.ArrayList;

import Storage.Storage;
import main.Task;

public class Undo implements Command{
	private ArrayList<Task> notDoneStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Undo(ArraylistStorage arraylistStorage) {
		notDoneStorage = arraylistStorage.getNotDoneStorage();
		doneStorage = arraylistStorage.getDoneStorage();
		previousInputStorage = arraylistStorage.getPreviousInputStorage();
		mainStorage = arraylistStorage.getMainStorage();
	}

	public String execute(String[] notUsedInThisCommand) {
		String actionToBeUndone = previousInputStorage.get(0).getAction();
		Task previousTask = previousInputStorage.get(0).getTask();
		assert actionToBeUndone != null : Constants.ASSERT_ACTION_EXISTENCE;
		assert previousTask != null : Constants.ASSERT_TASK_EXISTENCE;
		Task eachTask;
		switch (actionToBeUndone) {
		case Constants.MESSAGE_ACTION_ADD:
			// to restore to previous state, must unadd the task
			notDoneStorage.remove(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, previousTask));
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			// to restore to previous state, must add the task
			notDoneStorage.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_ADD, previousTask));
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			// to restore to previous state, must edit again to previous state
			eachTask = previousInputStorage.get(0).getEditedTask();
			notDoneStorage.remove(eachTask);
			notDoneStorage.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, eachTask, previousTask));
			break;
		case Constants.MESSAGE_ACTION_DONE:
			// to restore to previous state, must undone the task
			// eachTask = taskFinder(doneStorage, previousTask);
			doneStorage.remove(previousTask);
			notDoneStorage.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_UNDO, previousTask));
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			// to restore to previous state, must undone the task
			//eachTask = taskFinder(notDoneYetStorage, previousTask);
			notDoneStorage.remove(previousTask);
			doneStorage.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, previousTask));
			break;
		}
		// write to mainStorage
		mainStorage.write(notDoneStorage, doneStorage);
		return Constants.MESSAGE_UNDO_PASS;
	}

	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}
}
