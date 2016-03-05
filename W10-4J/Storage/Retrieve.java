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
				throw new FileNotFoundException();
			} else {
				BufferedReader reader = new BufferedReader(new FileReader(filename));
				Read read = new Read();
				taskList = read.readFromFile(reader);
				reader.close();
				Write write = new Write();
				write.writeToFile(filename, taskList);
			}
			
			return taskList;
		} catch (IOException e) {
			return null;
		}
	}
	
	private String checkFileExists(String filename){
		String outcome;
		File file = new File(filename);
		if (file.exists()) {
			outcome = success;
		} else {
			outcome = failure;
		}
		return outcome;
	}
}
