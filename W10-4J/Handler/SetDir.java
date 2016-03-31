
package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.*;

public class SetDir implements Command {

	private ArrayList<Task> notDoneStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public SetDir(ArraylistStorage arraylistStorage) {
		notDoneStorage = arraylistStorage.getNotDoneStorage();
		doneStorage = arraylistStorage.getDoneStorage();
		previousInputStorage = arraylistStorage.getPreviousInputStorage();
		mainStorage = arraylistStorage.getMainStorage();
	}
	
	
	public String execute(String[] task){
  		if(mainStorage.setDirectory(task[0])){
  			notDoneStorage.clear();
  			doneStorage.clear();
  			previousInputStorage.clear();
  			return Constants.MESSAGE_SETDIR_PASS;
  		} else{
  			return Constants.MESSAGE_SETDIR_FAIL;
  		}
  	}
}
