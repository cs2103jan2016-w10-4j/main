package Handler;

import main.Constants;
import java.util.ArrayList;
import Storage.Storage;
import main.Task;

//@@author Berkin
public class Add implements Command {
	// @@author
	private ArrayList<Task> notDoneStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;

	public Add(ArraylistStorage arraylistStorage) {
		notDoneStorage = arraylistStorage.getNotDoneStorage();
		doneStorage = arraylistStorage.getDoneStorage();
		previousInputStorage = arraylistStorage.getPreInputStorage();
		mainStorage = arraylistStorage.getMainStorage();
	}

	public String execute(String[] task) {
		assert task[0] != null : Constants.ASSERT_FIELD_EXISTENCE;
		Task eachTask = new Task(task[0].trim());
//		assert taskID > 0 : Constants.ASSERT_TASKID_EXISTENCE;
//		eachTask.setTaskID(taskID);
		String action;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i].trim();
			assert action != null : Constants.ASSERT_ACTION_EXISTENCE;
			switch (action) {
			case Constants.MESSAGE_ADD_ACTION_STARTDATE:
				eachTask.setStartDate(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_ENDDATE:
				eachTask.setEndDate(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_START:
				eachTask.setStartTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_END:
				eachTask.setEndTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_DETAILS:
				eachTask.setDetails(task[i + 1].trim());
				break;
			case Constants.MESSAGE_ADD_ACTION_REPEAT:
				switch (task[i + 1].trim()) {
				case Constants.MESSAGE_REPEAT_DAY:
					eachTask.setDay(true);
					break;
				case Constants.MESSAGE_REPEAT_WEEK:
					eachTask.setWeek(true);
					break;
				case Constants.MESSAGE_REPEAT_MONTH:
					eachTask.setMonth(true);
					break;
				case Constants.MESSAGE_REPEAT_YEAR:
					eachTask.setYear(true);
					break;
				default:
					assert false;
				}
				break;
			default:
				assert false;
			}
		}
		if (isTimeValid(eachTask)) {
			// remember previous state
			clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_ADD, eachTask));
			// add to arraylist storage
			notDoneStorage.add(eachTask);
			// write to mainStorage
			mainStorage.write(notDoneStorage, doneStorage);
			return String.format(Constants.MESSAGE_ADD_PASS, eachTask.getName());
		} else {
			return Constants.MESSAGE_TIME_FAIL;
		}
	}

	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}

	private boolean isTimeValid(Task task) {
		int starttime = task.getStartTimeInt();
		int endtime = task.getEndTimeInt();
		if (starttime != -1 && endtime != -1) {
			return endtime > starttime;
		} else {
			return true;
		}
	}
}