package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.Constants;
import main.Task;

public class ArraylistStorage {
	private ArrayList<Task> notDoneStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	private Storage mainStorage;
	
	public ArraylistStorage(){
		this.mainStorage = new Storage();
		ArrayList<ArrayList<Task>> getFromStorage = mainStorage.read(Constants.MESSAGE_ACTION_READ, Constants.fileName);
		this.notDoneStorage = getFromStorage.get(0);
		this.doneStorage = getFromStorage.get(1);
		this.previousInputStorage = new ArrayList<PreviousInput>();
	}
	
	public ArrayList<Task> getNotDoneStorage(){
		return this.notDoneStorage;
	}
	public ArrayList<Task> getDoneStorage(){
		return this.doneStorage;
	}
	public ArrayList<PreviousInput> getPreInputStorage(){
		return this.previousInputStorage;
	}
	public Storage getMainStorage(){
		return this.mainStorage;
	}
}
