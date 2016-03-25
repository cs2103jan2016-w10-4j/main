package Handler;
import main.Constants;
import java.util.ArrayList;

import Storage.Storage;
import main.Task;

public class Delete implements Command{
	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Delete(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}
	public String execute(String[] task, int notUsedInThisCommand) {
		assert task[0] != null: Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = findByTaskID(handlerMemory, taskID);
		if (eachTask==null){
			return Constants.MESSAGE_DELETE_FAIL;
		} else if (taskID <= 0 || taskID > handlerMemory.size()) {
			return Constants.MESSAGE_DELETE_FAIL;
		} else {
			assert eachTask != null: Constants.ASSERT_TASK_EXISTENCE;
			handlerMemory.remove(eachTask);
			// write to mainStorage
			mainStorage.write(handlerMemory, doneStorage);
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
