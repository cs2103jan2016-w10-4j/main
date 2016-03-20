package Handler;
import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.Constants;
public class Done {
	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Done(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}
	public String done(String[] task) {
		int taskID = Integer.parseInt(task[0].trim());
			assert task[0] != null: Constants.ASSERT_TASKID_EXISTENCE;
		if (taskID <= 0 || taskID > handlerMemory.size()) {
			return Constants.MESSAGE_DONE_FAIL;
		} else {
			Task eachTask = handlerMemory.get(taskID - 1);
				assert eachTask != null: Constants.ASSERT_TASK_EXISTENCE;
			handlerMemory.remove(eachTask);
			doneStorage.add(eachTask);
			// write to mainStorage
			mainStorage.write(handlerMemory, doneStorage);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, eachTask));
			assert eachTask.getName() != null: Constants.ASSERT_TASKNAME_EXISTENCE;
			return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
		}
	}
	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}
}
