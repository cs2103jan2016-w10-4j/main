//@@author A0135779M
package Handler;

import main.*;

public class SetDir implements Command {
	ArraylistStorage arraylistStorage_;
	
	public SetDir(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}
	
	public String execute(String[] task) {
		assert task[0] != null : Constants.ASSERT_FIELD_EXISTENCE;
		arraylistStorage_.rememberOldDirectory();
		arraylistStorage_.addPreviousDirectory(Constants.MESSAGE_ACTION_SETDIR);
  		if (arraylistStorage_.setDirectory(task[0])) {
  			//arraylistStorage_.addPreviousDirectory(Constants.MESSAGE_ACTION_SETDIR);
  			return Constants.MESSAGE_SETDIR_PASS;
  		} else {
  			return Constants.MESSAGE_SETDIR_FAIL;
  		}
  	}
}