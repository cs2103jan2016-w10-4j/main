//@@author A0135779M
package Handler;

import main.Constants;

public class Retrieve implements Command{
	ArraylistStorage arraylistStorage_;
	
	public Retrieve(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
	}

	public String execute(String[] task) {
		try {
			assert task[0] != null : Constants.ASSERT_FIELD_EXISTENCE;
			arraylistStorage_.addPreviousInputStorages(Constants.MESSAGE_ACTION_BASICOP);
			arraylistStorage_.combineArrays(task[0]);
			arraylistStorage_.writeToStorage();
			return Constants.MESSAGE_RETRIEVE_PASS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.MESSAGE_RETRIEVE_FAIL;
	}
}