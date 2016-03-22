package Handler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Storage.Read;
import Storage.Storage;
import main.Constants;
import main.Task;

public class Retrieve {
	private ArrayList<Task> handlerMemory;
	private ArrayList<Task> doneStorage;
	Storage mainStorage;
	
	public Retrieve(ArrayList<Task> handlerMemory, ArrayList<Task> doneStorage, Storage mainStorage){
		this.handlerMemory = handlerMemory;
		this.doneStorage = doneStorage;
		this.mainStorage = mainStorage;
	}
	
	public String retrieve(String[] task){
		try {
			assert task[0]!= null: Constants.ASSERT_FIELD_EXISTENCE;
			ArrayList<ArrayList<Task>> getFromStorage = readFromFile(task[0]);
			ArrayList<Task> addtionalHandlerMemory = getFromStorage.get(0);
			ArrayList<Task> additionalDoneStorage = getFromStorage.get(1);
			
			handlerMemory.addAll(addtionalHandlerMemory);
			doneStorage.addAll(additionalDoneStorage);
			
			mainStorage.write(handlerMemory, doneStorage);
//			taskAdder = new Add(handlerMemory, doneStorage, previousInputStorage, mainStorage);
//			taskEditor = new Edit(handlerMemory, doneStorage, previousInputStorage, mainStorage);
//			taskDeleter = new Delete(handlerMemory, doneStorage, previousInputStorage, mainStorage);
//			taskDoner = new Done(handlerMemory, doneStorage, previousInputStorage, mainStorage);
//			taskDisplayer = new Display(handlerMemory, doneStorage, mainStorage);
//			taskSearcher = new Search(handlerMemory, mainStorage);
//			taskUndoer = new Undo(handlerMemory, doneStorage, previousInputStorage, mainStorage);
			
			return Constants.MESSAGE_RETRIEVE_PASS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.MESSAGE_RETRIEVE_FAIL;
	}
	public ArrayList<ArrayList<Task>> readFromFile(String filename)throws IOException{
		ArrayList<ArrayList<Task>> taskList = null;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			Read read = Read.getInstance();
			taskList = read.readFromFile(reader);
			reader.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		assert taskList!= null: Constants.ASSERT_TASKLIST_EXISTENCE;
		return taskList;
	}
}
