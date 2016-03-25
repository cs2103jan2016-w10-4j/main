package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Task;
import main.*;

public class SetDir implements Command {

	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;

	public SetDir(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage,
			ArrayList<PreviousInput> previousInputStorage, Storage mainStorage) {
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}

	public String execute(String[] task, int notUsedInThisCommand) {
		if (mainStorage.setDirectory(task[0])) {
			handlerMemory.clear();
			doneStorage.clear();
			previousInputStorage.clear();
			return Constants.MESSAGE_SETDIR_PASS;
		} else {
			return Constants.MESSAGE_SETDIR_FAIL;
		}
	}
}
