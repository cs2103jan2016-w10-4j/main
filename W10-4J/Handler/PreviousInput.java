//@@author A0135779M

package Handler;

import java.util.ArrayList;

import main.Task;

public class PreviousInput {
	private String action_;
	
/////UNUSED//////
	private Task task_;
	// only for edit method in handler
	private Task editedTask_;
/////UNUSED//////
	
	private ArrayList<Task> previousNotDoneStorage_;
	private ArrayList<Task> previousDoneStorage_;
	// only for setdir method
	private String fileName_;

/////UNUSED//////
	public PreviousInput(String action, Task task) {
		action_ = action;
		task_ = task;
	}
	public PreviousInput(String action, Task task, Task editedTask) {
		action_ = action;
		task_ = task;
		editedTask_ = editedTask;
	}
/////UNUSED//////
	
	public PreviousInput(String action, ArrayList<Task> previousNotDoneStorage, ArrayList<Task> previousDoneStorage) {
		action_ = action;
		previousNotDoneStorage_ = previousNotDoneStorage;
		previousDoneStorage_ = previousDoneStorage;
	}
	
	public PreviousInput(String action, String fileName) {
		action_ = action;
		fileName_ = fileName;
	}

	public String getAction() {
		return action_;
	}

/////UNUSED//////
	public Task getTask() {
		return task_;
	}
	public Task getEditedTask() {
		return editedTask_;
	}
/////UNUSED//////
	
	public ArrayList<Task> getPreviousNotDoneStorage(){
		return previousNotDoneStorage_;
	}
	
	public ArrayList<Task> getPreviousDoneStorage(){
		return previousDoneStorage_;
	}
	
	public String getFileName(){
		return fileName_;
	}

	public void setAction(String action) {
		action_ = action;
	}

/////UNUSED//////
	public void setTask(Task task) {
		task_ = task;
	}
	public void setEditedTask(Task editedTask) {
		editedTask_ = editedTask;
	}
/////UNUSED//////
	
	public void setPreviousStorages(ArrayList<Task> notDoneStorage, ArrayList<Task> doneStorage){
		setPreviousNotDoneStorage(notDoneStorage);
		setPreviousDoneStorage(doneStorage);
	}
	
	public void setPreviousNotDoneStorage(ArrayList<Task> notDoneStorage){
		previousNotDoneStorage_ = notDoneStorage;
	}
	public void setPreviousDoneStorage(ArrayList<Task> doneStorage){
		previousDoneStorage_ = doneStorage;
	}
}