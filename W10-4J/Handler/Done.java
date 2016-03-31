package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.Constants;

public class Done implements Command{
	private ArrayList<Task> notDoneStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Done(ArraylistStorage arraylistStorage) {
		notDoneStorage = arraylistStorage.getNotDoneStorage();
		doneStorage = arraylistStorage.getDoneStorage();
		previousInputStorage = arraylistStorage.getPreInputStorage();
		mainStorage = arraylistStorage.getMainStorage();
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

	public String execute(String[] task) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());

		Task eachTask = findByTaskID(notDoneStorage, taskID);
		if (eachTask==null){
			return Constants.MESSAGE_DONE_FAIL;
		} else {
//			if(eachTask.isRecurring()&& eachTask.getStartDate()!=null){
//				eachTask.done();
//				mainStorage.write(notDoneYetStorage, doneStorage);
//				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, eachTask));
//				assert eachTask.getName() != null: Constants.ASSERT_TASKNAME_EXISTENCE;
//				return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
//			} else{
//			}
			assert eachTask != null: Constants.ASSERT_TASK_EXISTENCE;
			notDoneStorage.remove(eachTask);
			doneStorage.add(eachTask);
			// write to mainStorage
			mainStorage.write(notDoneStorage, doneStorage);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, eachTask));
			assert eachTask.getName() != null: Constants.ASSERT_TASKNAME_EXISTENCE;
			return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
		}
	}
}
