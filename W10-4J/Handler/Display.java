//@@author A0135779M

package Handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import Handler.HandlerMemory.COMMAND_STATE;
import main.Constants;
import main.Task;

public class Display implements Command {

	///////UNUSED////////
	private COMMAND_STATE commandState;
	private Task forCurrentTask; //currentTask the command is working on which will be updated in the HandlerMemory later.
	private Task forOldTask;//The Task which after this command will be an oldTask to be stored in the previousInputStorage by HandlerMemory.
	private HandlerMemory handlerMemory;

	public Task returnCurrentTask() {
		return forCurrentTask;
	}

	public COMMAND_STATE returnCommandState() {
		return commandState;
	}

	public Task returnOldTask() {
		return forOldTask;
	}
    ///////UNUSED////////

	//@@author A0135779M
	ArraylistStorage arraylistStorage_;
	private final Logger LOGGER = Logger.getLogger(Display.class.getName());
	String outputDisplay;
	Sorting sort;

	public Display(ArraylistStorage arraylistStorage) {
		arraylistStorage_ = arraylistStorage;
		sort = new Sorting();
	}

	public String execute(String[] task) {
		LOGGER.log(Level.INFO, Constants.MESSAGE_FUNCTION_EXECUTION);
		if (task.length == 0) {
			arraylistStorage_.writeToStorage();
			LOGGER.log(Level.INFO, Constants.MESSAGE_WRITETOSTORAGE);
			outputDisplay = arraylistStorage_.getNotDoneDisplayFormatByStartDate();
			LOGGER.log(Level.INFO, Constants.MESSAGE_DISPLAY_STRING_STARTDATE);
		} else {
			String displayField = task[0].trim();
			assert displayField != null : Constants.ASSERT_FIELD_EXISTENCE;
			switch (displayField) {
			case Constants.MESSAGE_DISPLAY_FIELD_NAME:
				arraylistStorage_.sortNotDoneStorageByName();
				arraylistStorage_.writeToStorage();
				LOGGER.log(Level.INFO, Constants.MESSAGE_WRITETOSTORAGE);
				outputDisplay = arraylistStorage_.getNotDoneDisplayFormatByDefault();
				LOGGER.log(Level.INFO, Constants.MESSAGE_DISPLAY_STRING_DEFAULT);
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_OVERDUE:
				arraylistStorage_.writeToStorage();
				LOGGER.log(Level.INFO, Constants.MESSAGE_WRITETOSTORAGE);
				outputDisplay = arraylistStorage_.getNotDoneDisplayFormatByOverdue();
				LOGGER.log(Level.INFO, Constants.MESSAGE_DISPLAY_STRING_OVERDUE);
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_TODAY:
				arraylistStorage_.writeToStorage();
				LOGGER.log(Level.INFO, Constants.MESSAGE_WRITETOSTORAGE);
				outputDisplay = arraylistStorage_.getNotDoneDisplayFormatByToday();
				LOGGER.log(Level.INFO, Constants.MESSAGE_DISPLAY_STRING_TODAY);
				break;
			case Constants.MESSAGE_DISPLAY_FIELD_TASKS:
				outputDisplay = arraylistStorage_.getNotDoneDisplayFormatByStartDate();
				LOGGER.log(Level.INFO, Constants.MESSAGE_DISPLAY_STRING_STARTDATE);
			case Constants.MESSAGE_DISPLAY_FIELD_DONE:
				outputDisplay = arraylistStorage_.getDoneDisplayFormatByDefault();
				LOGGER.log(Level.INFO, Constants.MESSAGE_DISPLAY_STRING_DEFAULT);
			}
		}
		LOGGER.log(Level.INFO, Constants.MESSAGE_DISPLAY_STRING_FORMATTED);
		return "0" + outputDisplay;
	}
}
