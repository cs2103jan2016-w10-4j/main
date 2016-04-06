//@@author A0135779M

package Handler;

import main.Task;
import main.Constants;

public class Done implements Command {
	ArraylistStorage arraylistStorage_;

	public Done(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = arraylistStorage_.getTaskByIndex(taskID - 1);
		if (eachTask == null) {
			return Constants.MESSAGE_DONE_FAIL;
		} else {
			// if(eachTask.isRecurring()&& eachTask.getStartDate()!=null){
			// eachTask.done();
			// mainStorage.write(notDoneYetStorage, doneStorage);
			// clearAndAdd(previousInputStorage, new
			// PreviousInput(Constants.MESSAGE_ACTION_DONE, eachTask));
			// assert eachTask.getName() != null:
			// Constants.ASSERT_TASKNAME_EXISTENCE;
			// return String.format(Constants.MESSAGE_DONE_PASS,
			// eachTask.getName());
			// } else{
			// }
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			// remember previous state
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			arraylistStorage_.delTaskFromNotDoneStorage(eachTask);
			arraylistStorage_.addTaskToDoneStorage(eachTask);
			// write to mainStorage
			arraylistStorage_.writeToStorage();
			// remember previous state
			// arraylistStorage_.addTaskToPreInputStorage(new
			// PreviousInput(Constants.MESSAGE_ACTION_DONE, eachTask));
			assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
			return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
		}
	}
}
