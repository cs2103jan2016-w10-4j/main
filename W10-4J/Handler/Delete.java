package Handler;

import main.Constants;
import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.Constants.COMMAND_STATE;

public class Delete implements Command {

	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;
	/*
	private ArrayList<Task> notDoneYetStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;*/

	public Delete(/*ArrayList<Task> notDoneYetStorage, ArrayList<Task> doneStorage,
			ArrayList<PreviousInput> previousInputStorage, Storage mainStorage,*/HandlerMemory handlerMemory) {
		this.handlerMemory=handlerMemory;/*
		this.notDoneYetStorage = notDoneYetStorage;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;*/
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = handlerMemory.findByTaskID(HandlerMemory.getNotDoneYetStorage(), taskID);
		if (eachTask == null) {
			eachTask = handlerMemory.findByTaskID(HandlerMemory.getDoneStorage(), taskID);
			if(eachTask == null){
				commandState=Constants.COMMAND_STATE.FAILED;
				return Constants.MESSAGE_DELETE_FAIL;
			} else{
				forEachTask=eachTask;
				commandState=Constants.COMMAND_STATE.DELETEDONETASK;
				return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
			} 
		} else if (taskID <= 0 || taskID > HandlerMemory.getTaskID()) {
			commandState=Constants.COMMAND_STATE.FAILED;
			return Constants.MESSAGE_DELETE_FAIL;
		} else {
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			forEachTask=eachTask;
			commandState=Constants.COMMAND_STATE.DELETEUNDONETASK;
			return String.format(Constants.MESSAGE_DELETE_PASS, eachTask.getName());
		}
	}

	public Task returnEachTask()
	{
		return forEachTask;
	}
	public COMMAND_STATE returnCommandState() {
		return commandState;
	}
	public Task returnOldTask()
	{
		return forOldTask;
	}
}
