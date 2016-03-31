package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.Constants;
public class Edit implements Command{
	private ArrayList<Task> notDoneStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public Edit(ArraylistStorage arraylistStorage) {
		notDoneStorage = arraylistStorage.getNotDoneStorage();
		doneStorage = arraylistStorage.getDoneStorage();
		previousInputStorage = arraylistStorage.getPreInputStorage();
		mainStorage = arraylistStorage.getMainStorage();
	}

	public String execute(String[] task) {
		assert task[0] != null : Constants.ASSERT_TASKID_EXISTENCE;
		int taskID = Integer.parseInt(task[0].trim());
		Task eachTask = findByTaskID(notDoneStorage, taskID);
		if (eachTask==null){
			return Constants.MESSAGE_EDIT_FAIL;
		} else if (taskID <= 0 || taskID > notDoneStorage.size()){
			return Constants.MESSAGE_EDIT_FAIL;
		} else {
			assert eachTask != null : Constants.ASSERT_TASK_EXISTENCE;
			Task oldTask = cloneTask(eachTask);
			// edits the task
			fieldEditor(eachTask, task);
			
			if(isTimeValid(eachTask)){
				// write to mainStorage
				mainStorage.write(notDoneStorage, doneStorage);
				// remember previous state
				clearAndAdd(previousInputStorage, new PreviousInput(Constants.MESSAGE_ACTION_EDIT, oldTask, eachTask));
				return String.format(Constants.MESSAGE_EDIT_PASS, eachTask.getName());
			} else{
				notDoneStorage.remove(eachTask);
				notDoneStorage.add(oldTask);
				return Constants.MESSAGE_TIME_FAIL;
			}
		}
	}

	public Task cloneTask(Task task) {
		Task result = new Task(task.getName());
		result.setStartDate(task.getStartDate());
		result.setEndDate(task.getEndDate());
		result.setStartTime(task.getStartTime());
		result.setEndTime(task.getEndTime());
		result.setDetails(task.getDetails());
		result.setTaskID(task.getTaskID());
		result.setYear(task.getYear());
		result.setMonth(task.getMonth());
		result.setWeek(task.getWeek());
		result.setDay(task.getDay());
		return result;
	}

	private void fieldEditor(Task eachTask, String[] task) {
		String action;
		for (int i = 1; i < task.length; i += 2) {
			action = task[i].trim();
			switch (action) {
			case Constants.MESSAGE_EDIT_ACTION_RENAME:
				eachTask.setName(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_STARTDATE:
				eachTask.setStartDate(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_ENDDATE:
				eachTask.setEndDate(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_START:
				eachTask.setStartTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_END:
				eachTask.setEndTime(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_DETAILS:
				eachTask.setDetails(task[i + 1].trim());
				break;
			case Constants.MESSAGE_EDIT_ACTION_REPEAT:
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
			}
		}
	}

	public Task findByTaskID(ArrayList<Task> taskList, int taskID) {
		for (Task task : taskList) {
			if (task.getTaskID() == taskID) {
				return task;
			}
		}
		return null;
	}

	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}
	
	private boolean isTimeValid(Task task) {
		int starttime = task.getStartTimeInt();
		int endtime = task.getEndTimeInt();
		System.out.println(starttime + endtime);
		if (starttime != -1 && endtime != -1) {
			return endtime > starttime;
		} else {
			return true;
		}
	}
	// add a start 1730 end 1930
}
