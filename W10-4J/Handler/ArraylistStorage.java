package Handler;

import java.util.ArrayList;
import java.util.Collections;
import Storage.Storage;
import main.Constants;
import main.Task;
//@@author A0149174Y
public class ArraylistStorage {
	private ArrayList<Task> notDoneStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	private Storage mainStorage;

	private Sorting sort;
	private ArrayList<Task> additionalNotDoneStorage;
	private ArrayList<Task> additionalDoneStorage;
	private ArrayList<Task> previousNotDoneStorage;
	private ArrayList<Task> previousDoneStorage;
	private String oldFileName;
	private String newFileName;

	public ArraylistStorage() {
		this.mainStorage = new Storage();
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read(Constants.MESSAGE_ACTION_READ, Constants.fileName);
		this.notDoneStorage = getFromStorage.get(0);
		this.doneStorage = getFromStorage.get(1);
		this.previousInputStorage = new ArrayList<PreviousInput>();
		this.sort = new Sorting();
	}
	//@@author A0135779M
	public ArrayList<Task> getNotDoneStorage() {
		return this.notDoneStorage;
	}

	public ArrayList<Task> getDoneStorage() {
		return this.doneStorage;
	}

	public ArrayList<PreviousInput> getPreviousInputStorage() {
		return this.previousInputStorage;
	}

	public String getPreviousInputAction() {
		return this.previousInputStorage.get(0).getAction();
	}

	public Task getPreviousInputTask() {
		return this.previousInputStorage.get(0).getTask();
	}

	public Task getPreviousInputEditedTask() {
		return this.previousInputStorage.get(0).getEditedTask();
	}

	// Only for Retrieve method
	public ArrayList<Task> getPreviousInputNotDoneStorage() {
		return this.previousInputStorage.get(0).getPreviousNotDoneStorage();
	}

	public ArrayList<Task> getPreviousInputDoneStorage() {
		return this.previousInputStorage.get(0).getPreviousDoneStorage();
	}

	public Storage getMainStorage() {
		return this.mainStorage;
	}

	public int getTaskID() {
		ArrayList<Integer> usedID = new ArrayList<>();
		for (int i = 0; i < this.doneStorage.size(); i++) {
			usedID.add(this.doneStorage.get(i).getTaskID());
		}
		for (int i = 0; i < this.notDoneStorage.size(); i++) {
			usedID.add(this.notDoneStorage.get(i).getTaskID());
		}
		Collections.sort(usedID);
		for (int i = 0; i < usedID.size(); i++) {
			if (usedID.get(i) != i + 1) {
				return i + 1;
			}
		}
		return usedID.size() + 1;
	}

	public void addTaskToDoneStorage(Task task) {
		this.doneStorage.add(task);
	}

	public void addTaskToNotDoneStorage(Task task) {
		this.notDoneStorage.add(task);
	}

	public void addTaskToPreInputStorage(PreviousInput task) {
		clearAndAdd(this.previousInputStorage, task);
	}

	private void clearAndAdd(ArrayList<PreviousInput> taskArray, PreviousInput task) {
		taskArray.clear();
		taskArray.add(task);
	}

	public void delTaskFromDoneStorage(Task task) {
		this.doneStorage.remove(task);
	}

	public void delTaskFromNotDoneStorage(Task task) {
		this.notDoneStorage.remove(task);
	}

	// For finding tasks in notDoneStorage
	public Task findByTaskIDNotDoneStorage(int taskID) {
		for (Task task : this.notDoneStorage) {
			if (task.getTaskID() == taskID) {
				return task;
			}
		}
		return null;
	}

	// For finding tasks in doneStorage
	public Task findByTaskIDDoneStorage(int taskID) {
		for (Task task : this.doneStorage) {
			if (task.getTaskID() == taskID) {
				return task;
			}
		}
		return null;
	}

	public void writeToStorage() {
		this.mainStorage.write(notDoneStorage, doneStorage);
	}

	public int getNotDoneStorageSize() {
		return this.notDoneStorage.size();
	}

	public int getDoneStorageSize() {
		return this.doneStorage.size();
	}

	// ** SEARCH METHOD **
	public ArrayList<Task> searchNotDoneStorage(String[] task, boolean hasExcludeField) {
		ArrayList<Task> results = new ArrayList<Task>();
		for (Task eachTask : this.notDoneStorage) {
			if (eachTask.getDetails() == null) {
				if (taskSearchName(eachTask, task, hasExcludeField)) {
					results.add(eachTask);
				}
			} else {
				if (taskSearchNameAndDetails(eachTask, task, hasExcludeField)) {
					results.add(eachTask);
				}
			}
		}
		return results;
	}

	// Search helper for each individual task with both name and details
	private boolean taskSearchNameAndDetails(Task eachTask, String[] task, boolean excludeField) {
		// check whether exclude field exists
		assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
		assert eachTask.getDetails() != null : Constants.ASSERT_TASKDETAILS_EXISTENCE;	
		//@@author A0149174Y
		return ((eachTask.getName().toLowerCase().startsWith(task[0].trim().toLowerCase())) || ((eachTask.getDetails().toLowerCase().startsWith(task[0].trim().toLowerCase()))));
		//@@author A0135779M
	}

	// Search helper for each individual task with only name
	private boolean taskSearchName(Task eachTask, String[] task, boolean excludeField) {
		// check whether exclude field exists
		assert eachTask.getName() != null : Constants.ASSERT_TASKNAME_EXISTENCE;
		//@@author A0149174Y
			return (eachTask.getName().toLowerCase().startsWith(task[0].trim().toLowerCase()));
		//@@author A0135779M
	}

	// ** SET DIR METHOD **
	public boolean setDirectory(String filePathName) {
		return this.mainStorage.setDirectory(filePathName);
	}

	public void clearNotDoneStorage() {
		this.notDoneStorage.clear();
	}

	public void clearDoneStorage() {
		this.doneStorage.clear();
	}

	public void clearPreInputStorage() {
		this.previousInputStorage.clear();
	}
	
	public void addPreviousDirectory(String command){
		ArrayList<Task> cloneNotDoneStorage = (ArrayList<Task>) this.notDoneStorage.clone();
		ArrayList<Task> cloneDoneStorage = (ArrayList<Task>) this.doneStorage.clone();
		addTaskToPreInputStorage(new PreviousInput(command, this.oldFileName, cloneNotDoneStorage, cloneDoneStorage));
	}
	
	public void rememberOldDirectory(){
		this.oldFileName = mainStorage.getCurrentFilename();
	}
	public void getNewDirectory(){
		this.newFileName = this.previousInputStorage.get(0).getFileName();
	}
	public void setNewDirectory(){
		mainStorage.setDirectory(newFileName);
	}

	// ** RETRIEVE METHOD **
	private ArrayList<ArrayList<Task>> getNewStorages(String fileName) {
		return readFromFile(fileName);
	}

	private ArrayList<ArrayList<Task>> readFromFile(String filename) {
		ArrayList<ArrayList<Task>> taskList = this.mainStorage.read(Constants.MESSAGE_ACTION_RETRIEVE, filename);
		assert taskList != null : Constants.ASSERT_TASKLIST_EXISTENCE;
		return taskList;
	}

	private void getNewArrays(String fileName) {
		this.additionalNotDoneStorage = getNewStorages(fileName).get(0);
		this.additionalDoneStorage = getNewStorages(fileName).get(1);
	}

	// For undo function for Retrieve method. Placed in ArraylistStorage since
	// it directly touches the arraylists.
	public void addPreviousInputStorages(String command) {
		ArrayList<Task> cloneNotDoneStorage = cloneStorage(this.notDoneStorage);
		ArrayList<Task> cloneDoneStorage = cloneStorage(this.doneStorage);
		addTaskToPreInputStorage(new PreviousInput(command, cloneNotDoneStorage, cloneDoneStorage));
	}
	// clones the arraylist to prevent referencing problems
	private ArrayList<Task> cloneStorage(ArrayList<Task> storage){
		ArrayList<Task> clone = new ArrayList<Task>();
		for (Task task: storage){
			clone.add(cloneTask(task));
		}
		return clone;
	}
	private Task cloneTask(Task task) {
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

	public void rememberPreviousStorages() {
		this.previousNotDoneStorage = getPreviousInputNotDoneStorage();
		this.previousDoneStorage = getPreviousInputDoneStorage();
	}

	public void setNewStorages() {
		this.notDoneStorage = this.previousNotDoneStorage;
		this.doneStorage = this.previousDoneStorage;
	}

	public void combineArrays(String fileName) {
		getNewArrays(fileName);
		combineDoneStorages();
		combineNotDoneStorages();
	}

	private void combineDoneStorages() {
		mergeWithoutRepeat(this.doneStorage, this.additionalDoneStorage);
	}

	private void combineNotDoneStorages() {
		mergeWithoutRepeat(this.notDoneStorage, this.additionalNotDoneStorage);
	}

	private void mergeWithoutRepeat(ArrayList<Task> originalArray, ArrayList<Task> additionalArray) {
		boolean isSame = false;
		for (Task task1 : additionalArray) {
			for (Task task2 : originalArray) {
				if (compareTo(task1, task2) == true) {
					isSame = true;
				}
			}
			if (isSame == false) {
				originalArray.add(task1);
			}
			isSame = false;
		}
	}

	// Helper functions for compareTo method
	private boolean compareTo(Task task1, Task task2) {
		return task1.getName().equals(task2.getName()) && compareDate(task1, task2) && compareStart(task1, task2)
				&& compareEnd(task1, task2) && compareDetails(task1, task2);
	}

	private boolean compareDate(Task task1, Task task2) {
		if (task1.getStartDate() == null && task2.getStartDate() == null) {
			return true;
		} else if (task1.getStartDate() == null || task2.getStartDate() == null) {
			return false;
		} else {
			return task1.getStartDate().equals(task2.getStartDate());
		}
	}

	private boolean compareStart(Task task1, Task task2) {
		if (task1.getStartTime() == null || task2.getStartTime() == null) {
			return true;
		} else if (task1.getStartTime() == null || task2.getStartTime() == null) {
			return false;
		} else {
			return task1.getStartTime().equals(task2.getStartTime());
		}
	}

	private boolean compareEnd(Task task1, Task task2) {
		if (task1.getEndTime() == null || task2.getEndTime() == null) {
			return true;
		} else if (task1.getEndTime() == null || task2.getEndTime() == null) {
			return false;
		} else {
			return task1.getEndTime().equals(task2.getEndTime());
		}
	}

	private boolean compareDetails(Task task1, Task task2) {
		if (task1.getDetails() == null || task2.getDetails() == null) {
			return true;
		} else if (task1.getDetails() == null || task2.getDetails() == null) {
			return false;
		} else {
			return task1.getDetails().equals(task2.getDetails());
		}
	}

	// ** DISPLAY METHOD **
	public String getNotDoneDisplayFormatByStartDate() {
		return DisplayStartDate.displayFormat(sort, this.notDoneStorage, this.previousInputStorage);
	}

	public String getNotDoneDisplayFormatByDefault() {
		return DisplayTableFormat.displayTableFormat(this.notDoneStorage, this.previousInputStorage);
	}

	public String getDoneDisplayFormatByDefault() {
		return DisplayDone.displayDoneFormat(this.doneStorage, this.previousInputStorage);
	}

	public String getNotDoneDisplayFormatByOverdue() {
		return DisplayOverdue.displayOverdue(sort, this.notDoneStorage, this.previousInputStorage);
	}

	public String getNotDoneDisplayFormatByToday() {
		return DisplayToday.displayToday(sort, this.notDoneStorage, this.previousInputStorage);
	}

	public void sortNotDoneStorageByName() {
		sort.sortByName(this.notDoneStorage);
	}

	public void sortNotDoneStorageByID() {
		sort.sortByID(this.notDoneStorage);
	}

}
