package Handler;

import main.Constants;
import java.util.ArrayList;

import Storage.Storage;
import main.Task;

public class Delete implements Command {
	private ArrayList<Task> notDoneStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;

	public Delete(ArraylistStorage arraylistStorage) {
		notDoneStorage = arraylistStorage.getNotDoneStorage();
		doneStorage = arraylistStorage.getDoneStorage();
		previousInputStorage = arraylistStorage.getPreInputStorage();
		mainStorage = arraylistStorage.getMainStorage();
	}

	public String execute(String[] task) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = findByTaskID(notDoneStorage, taskID);
		if (eachTask == null) {
			eachTask = findByTaskID(doneStorage, taskID);
			if(eachTask == null){
				return Constants.MESSAGE_DELETE_FAIL;
			} else{
				doneStorage.remove(eachTask);
				mainStorage.write(notDoneStorage, doneStorage);
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, eachTask));
				return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
			} 
		} else if (taskID <= 0 || taskID > notDoneStorage.size()) {
			return Constants.MESSAGE_DELETE_FAIL;
		} else {
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			notDoneStorage.remove(eachTask);
			// write to mainStorage
			mainStorage.write(notDoneStorage, doneStorage);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, eachTask));
			return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
		}
	}

	public Task findByTaskID(ArrayList<Task> taskList, int taskID) {
		for (Task task : taskList) {
			if (task.getTaskID() == taskID) {
				return task;
			}
		}
		return null;
	}

	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}
}
