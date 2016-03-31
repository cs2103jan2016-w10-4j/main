package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Constants;
import main.Task;

public class Display implements Command{
	private ArrayList<Task> notDoneStorage;
	private ArrayList<Task> doneStorage;
	Storage mainStorage;
	String outputDisplay;
	Sorting sort = new Sorting();
	
	public Display(ArraylistStorage arraylistStorage) {
		notDoneStorage = arraylistStorage.getNotDoneStorage();
		doneStorage = arraylistStorage.getDoneStorage();
		mainStorage = arraylistStorage.getMainStorage();
	}

	public String execute(String[] task) {
		if(task.length==0){
			sort.sortByID(notDoneStorage);
			mainStorage.write(notDoneStorage, doneStorage);
			outputDisplay = DisplayByStartDate.displayFormat(sort, notDoneStorage);
		}else{
			String displayField = task[0].trim();
			assert displayField != null: Constants.ASSERT_FIELD_EXISTENCE;
			//ArrayList<Task> clonenotDoneYetStorage = cloneArray(notDoneYetStorage);
			switch (displayField)
			{
				case Constants.MESSAGE_DISPLAY_FIELD_NAME:
					sort.sortByName(notDoneStorage);
					mainStorage.write(notDoneStorage, doneStorage);
					outputDisplay = DisplayDefault.displayDefaultFormat(notDoneStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_ID:
					sort.sortByID(notDoneStorage);
					mainStorage.write(notDoneStorage, doneStorage);
					outputDisplay = DisplayDefault.displayDefaultFormat(notDoneStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_OVERDUE:
					mainStorage.write(notDoneStorage, doneStorage);
					outputDisplay = DisplayOverdue.displayOverdue(sort, notDoneStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_TODAY:
					mainStorage.write(notDoneStorage, doneStorage);
					outputDisplay = DisplayToday.displayToday(sort, notDoneStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_TASKS:
					return DisplayByStartDate.displayFormat(sort, notDoneStorage);
				case Constants.MESSAGE_DISPLAY_FIELD_DONE:
					return DisplayDefault.displayDefaultFormat(doneStorage);
			}
		}
		return outputDisplay;
	}
	// private ArrayList<Task> cloneArray(ArrayList<Task> taskArray) {
	// ArrayList<Task> clone = new ArrayList<Task>(taskArray.size());
	// for (Task task : taskArray) {
	// clone.add(task);
	// }
	// return clone;
	// }
}
