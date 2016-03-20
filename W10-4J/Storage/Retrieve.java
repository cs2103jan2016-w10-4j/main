package Storage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.Task;

public class Retrieve {
	private final Logger LOGGER = Logger.getLogger(Retrieve.class.getName());
	private String failure = "Failure";
	private static Retrieve retrieve;
	
	// Show Singleton
	public static Retrieve getInstance() {
		if(retrieve == null) {
			retrieve = new Retrieve();
		}
		return retrieve;
	}
	
	public ArrayList<ArrayList<Task>> retrieveTaskListFromFile(String filename) throws FileNotFoundException {		
		try {
			ArrayList<ArrayList<Task>> taskList = new ArrayList<ArrayList<Task>>();
			
			// Check if file and directory exists before proceeding on to read
			// the content in the file
			String outcome = Storage.checkFileExists(filename);
			if (outcome.equals(failure)) {
				LOGGER.log(Level.INFO, "Unable to find the file");
				throw new FileNotFoundException();
			} else {
				BufferedReader reader = new BufferedReader(new FileReader(filename));
				Read read = Read.getInstance();
				taskList = read.readFromFile(reader);
				reader.close();
			
				Write write = Write.getInstance();
				write.writeToFile(Storage.filename, taskList);
				LOGGER.log(Level.INFO, "Retrieve task list from file successfully");
			}
			
			return taskList;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to create file");
			return null;
		}
	}
}