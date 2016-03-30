package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.Constants;

public class Done implements Command{
	private ArrayList<Task> notDoneYetStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Done(ArrayList<Task> notDoneYetStorage, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.notDoneYetStorage = notDoneYetStorage;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
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

	public String execute(String[] task, int notUsedInThisCommand) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());

		Task eachTask = findByTaskID(notDoneYetStorage, taskID);
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
			notDoneYetStorage.remove(eachTask);
			doneStorage.add(eachTask);
			// write to mainStorage
			mainStorage.write(notDoneYetStorage, doneStorage);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DONE, eachTask));
			assert eachTask.getName() != null: Constants.ASSERT_TASKNAME_EXISTENCE;
			return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
		}
	}
}
