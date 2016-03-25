package Handler;
import main.Constants;
import java.util.ArrayList;

import Storage.Storage;
import main.Task;

public class Delete implements Command{
	private ArrayList<Task> notDoneYetStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Delete(ArrayList<Task> notDoneYetStorage, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.notDoneYetStorage = notDoneYetStorage;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}
	public String execute(String[] task, int notUsedInThisCommand) {
		assert task[0] != null: Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = findByTaskID(notDoneYetStorage, taskID);
		if (eachTask==null){
			return Constants.MESSAGE_DELETE_FAIL;
		} else if (taskID <= 0 || taskID > notDoneYetStorage.size()) {
			return Constants.MESSAGE_DELETE_FAIL;
		} else {
			assert eachTask != null: Constants.ASSERT_TASK_EXISTENCE;
			notDoneYetStorage.remove(eachTask);
			// write to mainStorage
			mainStorage.write(notDoneYetStorage, doneStorage);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_DELETE, eachTask));
			return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
		}
	}
	public Task findByTaskID(ArrayList<Task> taskList, int taskID){
		for (Task task: taskList){
			if (task.getTaskID()==taskID){
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
