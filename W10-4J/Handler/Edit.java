package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.Constants.COMMAND_STATE;
import main.Constants;
public class Edit implements Command{
	private HandlerMemory handlerMemory;
	private Task forEachTask;
	private Task forOldTask;
	private COMMAND_STATE commandState;
	
	public Edit(HandlerMemory handlerMemory){
		
		this.handlerMemory=handlerMemory;
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = handlerMemory.findByTaskID(HandlerMemory.getNotDoneYetStorage(), taskID);
		if (eachTask==null){
			commandState=COMMAND_STATE.FAILED;
			return Constants.MESSAGE_EDIT_FAIL;
		} else if (taskID <= 0 || taskID > HandlerMemory.getTaskID()){
			commandState=COMMAND_STATE.FAILED;
			return Constants.MESSAGE_EDIT_FAIL;
		} else {
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			Task oldTask = cloneTask(eachTask);
			// edits the task
			fieldEditor(eachTask, task);
			
			if(isTimeValid(eachTask)){
				commandState=COMMAND_STATE.PASS;
				forEachTask=eachTask;
				forOldTask=oldTask;
				return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
			} else{
				/*notDoneYetStorage.remove(eachTask);
				notDoneYetStorage.add(oldTask);*/ // No need, eachtask is not put into the storage yet.
				commandState=COMMAND_STATE.FAILED;
				return Constants.MESSAGE_TIME_FAIL;
			}
		}
	}

	public Task cloneTask(Task task) {
		Task result = new Task(task.getName());
		result.setDate(task.getDate());
		result.setStartTime(task.getStartTime());
		result.setEndTime(task.getEndTime());
		result.setDetails(task.getDetails());
		result.setTaskID(task.getTaskID());
		result.setYear(task.getYear());
		result.setMonth(task.getMonth());
		result.setWeek(task.getWeek());
		result.setDay(task.getDay());
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
			case Constants.MESSAGE_EDIT_ACTION_REPEAT:
				switch (task[i + 1].trim()) {
				case Constants.MESSAGE_REPEAT_DAY:
					eachTask.setDay(true);
					break;
				case Constants.MESSAGE_REPEAT_WEEK:
					eachTask.setWeek(true);
					break;
				case Constants.MESSAGE_REPEAT_MONTH:
					eachTask.setMonth(true);
					break;
				case Constants.MESSAGE_REPEAT_YEAR:
					eachTask.setYear(true);
					break;
				default:
					assert false;
				}
				break;
			}
		}
	}
	
	private boolean isTimeValid(Task task) {
		int starttime = task.getStartTimeInt();
		int endtime = task.getEndTimeInt();
		System.out.println(starttime + endtime);
		if (starttime != -1 && endtime != -1) {
			return endtime > starttime;
		} else {
			return true;
		}
	}
	// add a start 1730 end 1930
	public Task returnEachTask()
	{
		return forEachTask;
	}
	public Task returnOldTask()
	{
		return forOldTask;
	}
	public COMMAND_STATE returnCommandState() {
		return commandState;
	}
}
