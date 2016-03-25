package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.Constants;

public class Done implements Command {
	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;

	public Done(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage,
			ArrayList<PreviousInput> previousInputStorage, Storage mainStorage) {
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}

	public String done(String[] task) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = findByTaskID(handlerMemory, taskID);
		if (eachTask == null) {
			return Constants.MESSAGE_DONE_FAIL;
		} else if (taskID <= 0 || taskID > handlerMemory.size()) {
			return Constants.MESSAGE_DONE_FAIL;
		} else {
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			if (eachTask.isRecurring() && eachTask.getDate() != null) {
				eachTask.done();
				mainStorage.write(handlerMemory, doneStorage);
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, eachTask));
				assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
				return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
			} else {
				handlerMemory.remove(eachTask);
				doneStorage.add(eachTask);
				// write to mainStorage
				mainStorage.write(handlerMemory, doneStorage);
				// remember previous state
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, eachTask));
				assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
				return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
			}
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

	@Override
	public String execute(String[] task, int taskID) {
		// TODO Auto-generated method stub
		return null;
	}
}
