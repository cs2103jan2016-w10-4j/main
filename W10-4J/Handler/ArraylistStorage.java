package Handler;

import java.util.ArrayList;
import java.util.Collections;
import Storage.Storage;
import main.Constants;
import main.Task;

public class ArraylistStorage {
	private ArrayList<Task> notDoneStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	private Storage mainStorage;

	public ArraylistStorage() {
		this.mainStorage = new Storage();
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read(Constants.MESSAGE_ACTION_READ, Constants.fileName);
		this.notDoneStorage = getFromStorage.get(0);
		this.doneStorage = getFromStorage.get(1);
		this.previousInputStorage = new ArrayList<PreviousInput>();
	}

	public ArrayList<Task> getNotDoneStorage() {
		return this.notDoneStorage;
	}

	public ArrayList<Task> getDoneStorage() {
		return this.doneStorage;
	}

	public ArrayList<PreviousInput> getPreviousInputStorage() {
		return this.previousInputStorage;
	}

	public Storage getMainStorage() {
		return this.mainStorage;
	}

	public int getTaskID() {
		ArrayList<Integer> usedID = new ArrayList<>();
		for (int i = 0; i < doneStorage.size(); i++) {
			usedID.add(doneStorage.get(i).getTaskID());
		}
		for (int i = 0; i < notDoneStorage.size(); i++) {
			usedID.add(notDoneStorage.get(i).getTaskID());
		}
		Collections.sort(usedID);
		for (int i = 0; i < usedID.size(); i++) {
			if (usedID.get(i) != i + 1) {
				return i + 1;
			}
		}
		return usedID.size() + 1;
	}
}
