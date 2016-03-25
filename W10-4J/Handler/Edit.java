package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.Constants;
public class Edit implements Command{
	private ArrayList<Task> notDoneYetStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Edit(ArrayList<Task> notDoneYetStorage, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.notDoneYetStorage = notDoneYetStorage;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = findByTaskID(notDoneYetStorage, taskID);
		if (eachTask==null){
			return Constants.MESSAGE_EDIT_FAIL;
		} else if (taskID<=0 || taskID>notDoneYetStorage.size()){
			return Constants.MESSAGE_EDIT_FAIL;
		} else {
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			Task oldTask = cloneTask(eachTask);
			// edits the task
			fieldEditor(eachTask, task);
			// write to mainStorage
			mainStorage.write(notDoneYetStorage, doneStorage);
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, oldTask, eachTask));
			return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
		}
	}

	public Task cloneTask(Task task) {
		Task result = new Task(task.getName());
		result.setDate(task.getDate());
		result.setStartTime(task.getStartTime());
		result.setEndTime(task.getEndTime());
		result.setDetails(task.getDetails());
		return result;
	}

	private void fieldEditor(Task eachTask, String[] task) {
		String action;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i].trim();
			switch (action) {
			case Constants.MESSAGE_EDIT_ACTION_RENAME:
				eachTask.setName(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_DATE:
				eachTask.setDate(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_START:
				eachTask.setStartTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_END:
				eachTask.setEndTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_DETAILS:
				eachTask.setDetails(task[i + 1].trim());
				break;
			}
		}
		return;
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
