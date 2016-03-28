package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.*;
import main.Constants.COMMAND_STATE;
import main.Constants.COMMAND_TYPE;

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
