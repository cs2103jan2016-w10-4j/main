//@@author A0135779M

package Handler;

import main.Constants;
import main.Task;

public class Delete implements Command {
	ArraylistStorage arraylistStorage_;
	
	public Delete(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = arraylistStorage_.getTaskByIndex(taskID);
		if (eachTask == null) {
			eachTask = arraylistStorage_.findByTaskIDDoneStorage(taskID);
			if(eachTask == null){
				return Constants.MESSAGE_DELETE_FAIL;
			} else{
				// remember previous state
				arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
				arraylistStorage_.delTaskFromDoneStorage(eachTask);
				arraylistStorage_.writeToStorage();
				return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
			} 
		} else if (taskID <= 0) {
			return Constants.MESSAGE_DELETE_FAIL;
		} else {
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			// remember previous state
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			arraylistStorage_.delTaskFromNotDoneStorage(eachTask);
			// write to mainStorage
			arraylistStorage_.writeToStorage();
			return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
		}
	}
}
