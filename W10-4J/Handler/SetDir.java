//@@author Berkin
package Handler;

import java.util.ArrayList;

import Storage.Storage;
import main.*;

public class SetDir implements Command {

	private ArrayList<Task> notDoneYetStorage;
	private ArrayList<Task> doneStorage;
	private ArrayList<PreviousInput> previousInputStorage;
	Storage mainStorage;
	
	public SetDir(ArrayList<Task> notDoneYetStorage, ArrayList<Task> doneStorage,
				ArrayList<PreviousInput> previousInputStorage, Storage mainStorage){
		this.notDoneYetStorage = notDoneYetStorage;
		this.doneStorage = doneStorage;
		this.previousInputStorage = previousInputStorage;
		this.mainStorage = mainStorage;
	}
	//@@author 
	
	public String execute(String[] task, int notUsedInThisCommand){
  		if(mainStorage.setDirectory(task[0])){
  			notDoneYetStorage.clear();
  			doneStorage.clear();
  			previousInputStorage.clear();
  			return Constants.MESSAGE_SETDIR_PASS;
  		} else{
  			return Constants.MESSAGE_SETDIR_FAIL;
  		}
  	}
}
