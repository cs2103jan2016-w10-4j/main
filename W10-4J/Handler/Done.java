package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.Constants.COMMAND_STATE;
import main.Constants;

public class Done implements Command{
	
	private  COMMAND_STATE commandState;
	private  Task forEachTask;
	private  Task forOldTask;
	private  HandlerMemory handlerMemory;
	
	public Done(HandlerMemory handlerMemory){
		
		this.handlerMemory=handlerMemory;
	}

	
	public String execute(String[] task, int notUsedInThisCommand) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());

		Task eachTask = handlerMemory.findByTaskID(handlerMemory.getNotDoneYetStorage(), taskID);
		if (eachTask==null){
			commandState=COMMAND_STATE.FAILED;
			return Constants.MESSAGE_DONE_FAIL;
		} else {
			if(eachTask.isRecurring()&& eachTask.getDate()!=null){
				eachTask.done();
				forEachTask=eachTask;
				assert eachTask.getName() != null: Constants.ASSERT_TASKNAME_EXISTENCE;
				commandState=COMMAND_STATE.RECURRINGDONE;
				return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
			} else{
				assert eachTask != null: Constants.ASSERT_TASK_EXISTENCE;
				assert eachTask.getName() != null: Constants.ASSERT_TASKNAME_EXISTENCE;
				forEachTask=eachTask;
				commandState=COMMAND_STATE.NONRECURRINGDONE;
				return String.format(Constants.MESSAGE_DONE_PASS, eachTask.getName());
			}
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
