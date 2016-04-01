
package Handler;

import main.*;

public class SetDir implements Command {

	ArraylistStorage arraylistStorage_;
	
	public SetDir(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}
	
	
	public String execute(String[] task){
  		if(arraylistStorage_.setDirectory(task[0])){
  			arraylistStorage_.clearNotDoneStorage();
  			arraylistStorage_.clearDoneStorage();
  			arraylistStorage_.clearPreInputStorage();
  			return Constants.MESSAGE_SETDIR_PASS;
  		} else{
  			return Constants.MESSAGE_SETDIR_FAIL;
  		}
  	}
}
