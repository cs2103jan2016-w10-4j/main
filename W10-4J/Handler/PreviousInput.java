package Handler;

import main.Task;

public class PreviousInput {
	private String action_;
	private Task task_;
	// only for edit method in handler
	private Task editedTask_;

	public PreviousInput(String action, Task task) {
		action_ = action;
		task_ = task;
	}

	public PreviousInput(String action, Task task, Task editedTask) {
		action_ = action;
		task_ = task;
		editedTask_ = editedTask;
	}

	public String getAction() {
		return action_;
	}

	public Task getTask() {
		return task_;
	}

	public Task getEditedTask() {
		return editedTask_;
	}

	public void setAction(String action) {
		action_ = action;
	}

	public void setTask(Task task) {
		task_ = task;
	}

	public void setEditedTask(Task editedTask) {
		editedTask_ = editedTask;
	}
}