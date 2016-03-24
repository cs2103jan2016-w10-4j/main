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
			
			combineArray(handlerMemory, addtionalHandlerMemory);
			combineArray(doneStorage, additionalDoneStorage);
			
			mainStorage.write(handlerMemory, doneStorage);
			
			return Constants.MESSAGE_RETRIEVE_PASS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.MESSAGE_RETRIEVE_FAIL;
	}
	public boolean compareTo(Task task1, Task task2){
		return task1.getName().equals(task2.getName()) && compareDate(task1, task2) && 
				compareStart(task1, task2) && compareEnd(task1, task2) &&
				compareDetails(task1, task2);
	}
	public boolean compareDate(Task task1, Task task2){
		if (task1.getDate()==null && task2.getDate()==null){
			return true;
		} else if (task1.getDate()==null || task2.getDate()==null){
			return false;
		} else {
			return task1.getDate().equals(task2.getDate());
		}
	}
	public boolean compareStart(Task task1, Task task2){
		if (task1.getStartTime()==null || task2.getStartTime()==null){
			return true;
		} else if (task1.getStartTime()==null || task2.getStartTime()==null){
			return false;
		} else {
			return task1.getStartTime().equals(task2.getStartTime());
		}
	}
	public boolean compareEnd(Task task1, Task task2){
		if (task1.getEndTime()==null || task2.getEndTime()==null){
			return true;
		} else if (task1.getEndTime()==null || task2.getEndTime()==null){
			return false;
		} else {
			return task1.getEndTime().equals(task2.getEndTime());
		}
	}
	public boolean compareDetails(Task task1, Task task2){
		if (task1.getDetails()==null || task2.getDetails()==null){
			return true;
		} else if (task1.getDetails()==null || task2.getDetails()==null){
			return false;
		} else {
			return task1.getDetails().equals(task2.getDetails());
		}
	}
	public void combineArray(ArrayList<Task> originalArray, ArrayList<Task> additionalArray){
		boolean isSame = false;
		for (Task task1: additionalArray){
			for (Task task2: originalArray){
				if (compareTo(task1, task2)==true){
					isSame = true;
				}
			}
			System.out.println(isSame);
			if(isSame==false){
				originalArray.add(task1);
			}
			isSame = false;
		}
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
		return taskList;
	}
}
