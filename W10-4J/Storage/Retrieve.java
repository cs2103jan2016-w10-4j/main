package Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.Task;

public class Retrieve {
	private String success = "Success";
	private String failure = "Failure";
	
	public ArrayList<ArrayList<Task>> retrieveTaskListFromFile(String filename) throws FileNotFoundException {
		try {
			ArrayList<ArrayList<Task>> taskList = new ArrayList<ArrayList<Task>> ();
			
			// Check if file and directory exists before proceeding on to read
			// the content in the file
			String outcome = checkFileExists(filename);
			if (outcome.equals(failure)) {
				ArrayList<Task> readToDoTaskList = new ArrayList<Task>();
				ArrayList<Task> readDoneTaskList = new ArrayList<Task>();
				taskList.add(readToDoTaskList);
				taskList.add(readDoneTaskList);
			} else {
				BufferedReader reader = new BufferedReader(new FileReader(filename));
				Read read = new Read();
				taskList = read.readFromFile(reader);
				reader.close();
			}
			
			return taskList;
		} catch (IOException e) {
			System.out.println(e);
			return null;
		}
	}
	
	private String checkFileExists(String filename) throws FileNotFoundException {
		String outcome;
		//File file = new File(filename);
		
		String absoluteFileName = "\"" + filename + "\"";
		File file = new File(absoluteFileName);
		if (file.exists()) {
			outcome = success;
		} else {
			outcome = failure;
		}

		return outcome;
	}
}
