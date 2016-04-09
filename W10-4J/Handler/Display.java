//@@author A0135779M

package Handler;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;
import main.Task;

public class Display implements Command {

	///////UNUSED////////
	private COMMAND_STATE commandState;
	private Task forEachTask;
	private Task forOldTask;
	private HandlerMemory handlerMemory;

	public Task returnEachTask() {
		return forEachTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}
    ///////UNUSED////////

	ArraylistStorage arraylistStorage_;
	String outputDisplay;
	Sorting sort;

	public Display(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
		sort = new Sorting();
	}

	public String execute(String[] task) {
		if (task.length == 0) {
			arraylistStorage_.writeToStorage();
			outputDisplay = arraylistStorage_.getNotDoneDisplayFormatByStartDate();
		} else {
			String displayField = task[0].trim();
			assert displayField != null : Constants.ASSERT_FIELD_EXISTENCE;
			switch (displayField) {
			case Constants.MESSAGE_DISPLAY_FIELD_NAME:
				arraylistStorage_.sortNotDoneStorageByName();
				arraylistStorage_.writeToStorage();
				outputDisplay = arraylistStorage_.getNotDoneDisplayFormatByDefault();
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_OVERDUE:
				arraylistStorage_.writeToStorage();
				outputDisplay = arraylistStorage_.getNotDoneDisplayFormatByOverdue();
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_TODAY:
				arraylistStorage_.writeToStorage();
				outputDisplay = arraylistStorage_.getNotDoneDisplayFormatByToday();
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_TASKS:
				outputDisplay = arraylistStorage_.getNotDoneDisplayFormatByStartDate();
			case Constants.MESSAGE_DISPLAY_FIELD_DONE:
				outputDisplay = arraylistStorage_.getDoneDisplayFormatByDefault();
			}
		}
		return "0" + outputDisplay;
	}
}
