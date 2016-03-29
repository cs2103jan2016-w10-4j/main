package Handler;

import main.*;
import main.Constants.COMMAND_STATE;

public class SetDir implements Command {

	
	private  COMMAND_STATE commandState;
	private  Task forEachTask;
	private  Task forOldTask;
	private  HandlerMemory handlerMemory;
	
	public SetDir(HandlerMemory handlerMemory){
		
		this.handlerMemory=handlerMemory;
		}
	
	public String execute(String[] task, int notUsedInThisCommand){
  		if(handlerMemory.getMainStorage().setDirectory(task[0])){
			HandlerMemory.setTaskID(0);
  			return Constants.MESSAGE_SETDIR_PASS;
  		} else{
  			commandState=COMMAND_STATE.FAILED;
  			return Constants.MESSAGE_SETDIR_FAIL;
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
