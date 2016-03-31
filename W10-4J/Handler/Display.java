package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Constants;
import main.Task;

public class Display implements Command {
	private ArrayList<Task> notDoneYetStorage;
	private ArrayList<Task> doneStorage;
	Storage mainStorage;
	String outputDisplay;
	Sorting sort = new Sorting();
	
	public Display(ArrayList<Task> notDoneYetStorage, ArrayList<Task> doneStorage, Storage mainStorage){
		this.notDoneYetStorage = notDoneYetStorage;
		this.doneStorage = doneStorage;
		this.mainStorage = mainStorage;
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		if(task.length==0){ 
			mainStorage.write(notDoneYetStorage, doneStorage);
			outputDisplay = DisplayByStartDate.displayFormat(sort, notDoneYetStorage);
		}else{
			String displayField = task[0].trim();
			assert displayField != null: Constants.ASSERT_FIELD_EXISTENCE;
			//ArrayList<Task> clonenotDoneYetStorage = cloneArray(notDoneYetStorage);
			switch (displayField)
			{
				case Constants.MESSAGE_DISPLAY_FIELD_NAME:
					sort.sortByName(notDoneYetStorage);
					mainStorage.write(notDoneYetStorage, doneStorage);
					outputDisplay = DisplayDefault.displayDefaultFormat(notDoneYetStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_ID:
					sort.sortByID(notDoneYetStorage);
					mainStorage.write(notDoneYetStorage, doneStorage);
					outputDisplay = DisplayDefault.displayDefaultFormat(notDoneYetStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_OVERDUE:
					mainStorage.write(notDoneYetStorage, doneStorage);
					outputDisplay = DisplayOverdue.displayOverdue(sort, notDoneYetStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_TODAY:
					mainStorage.write(notDoneYetStorage, doneStorage);
					outputDisplay = DisplayToday.displayToday(sort, notDoneYetStorage);
					break;
				case Constants.MESSAGE_DISPLAY_FIELD_TASKS:
					return DisplayByStartDate.displayFormat(sort, notDoneYetStorage);
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
