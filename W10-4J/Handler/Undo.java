package Handler;
import main.Constants;
import java.util.ArrayList;

import Storage.Storage;
import main.Task;

public class Undo implements Command{
	private ArrayList<Task> notDoneYetStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Undo(ArrayList<Task> notDoneYetStorage, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.notDoneYetStorage = notDoneYetStorage;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}
	public String execute(String [] notUsedInThisCommand, int notUsedInThisCommand2) {
		String actionToBeUndone = previousInputStorage.get(0).getAction();
		Task previousTask = previousInputStorage.get(0).getTask();
			assert actionToBeUndone != null: Constants.ASSERT_ACTION_EXISTENCE;
			assert previousTask != null: Constants.ASSERT_TASK_EXISTENCE;
		Task eachTask;
		switch (actionToBeUndone) {
		case Constants.MESSAGE_ACTION_ADD:
			// to restore to previous state, must unadd the task
			notDoneYetStorage.remove(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, previousTask));
			break;
		case Constants.MESSAGE_ACTION_DELETE:
			// to restore to previous state, must add the task
			notDoneYetStorage.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_ADD, previousTask));
			break;
		case Constants.MESSAGE_ACTION_EDIT:
			// to restore to previous state, must edit again to previous state
			eachTask = previousInputStorage.get(0).getEditedTask();
			notDoneYetStorage.remove(eachTask);
			notDoneYetStorage.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, eachTask, previousTask));
			break;
		case Constants.MESSAGE_ACTION_DONE:
			// to restore to previous state, must undone the task
			//eachTask = taskFinder(doneStorage, previousTask);
			doneStorage.remove(previousTask);
			notDoneYetStorage.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_UNDO, previousTask));
			break;
		case Constants.MESSAGE_ACTION_UNDO:
			// to restore to previous state, must undone the task
			//eachTask = taskFinder(notDoneYetStorage, previousTask);
			notDoneYetStorage.remove(previousTask);
			doneStorage.add(previousTask);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, previousTask));
			break;
		}
		// write to mainStorage
		mainStorage.write(notDoneYetStorage, doneStorage);
		return Constants.MESSAGE_UNDO_PASS;
	}
	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}
}
