//@@author A0135779M

package Handler;

import main.Constants;

public class Display implements Command {
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
			case Constants.MESSAGE_DISPLAY_FIELD_ID:
				arraylistStorage_.sortNotDoneStorageByID();
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
		return outputDisplay;
	}
}
