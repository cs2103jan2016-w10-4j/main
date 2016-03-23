package Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.Constants;
import main.Task;

public class Write {
	private final Logger LOGGER = Logger.getLogger(Write.class.getName());
	private PrintWriter print;
	private PrintWriter printPath;
	private BufferedReader reader;
	private File tempFile;
	private String taskOnHand = "Tasks on hand:";
	private String taskDone = "Tasks that are done:";
	private String noTaskOnHand = "No tasks on hand!";
	private String noTaskDone = "No tasks are done!";
	private String pathVariable = "PATH:";
	private static Write write;

	// Show Singleton
	public static Write getInstance() {
		if(write == null) {
			write = new Write();
		}
		return write;
	}
	
	// Applicable if filename == Constants.fileName
	public void writeToFile(ArrayList<Task> toDoTaskList, ArrayList<Task> doneTaskList) {
		getTask(toDoTaskList, doneTaskList);
	}
	
	// Applicable if filename != Constants.fileName
	public void writeToFile(String filePathName, ArrayList<Task> toDoTaskList, ArrayList<Task> doneTaskList) {
		getTask(toDoTaskList, doneTaskList);
		updatePathSentence(filePathName);
	}
	
	private void getTask (ArrayList<Task> toDoTaskList, ArrayList<Task> doneTaskList) {		
		try {
			print = new PrintWriter(new FileWriter(Storage.filename));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to write to file");
			e.printStackTrace();
		}	
		
		if (toDoTaskList.isEmpty()) {
			print.println(noTaskOnHand);
		} else {
			getTaskDetails(taskOnHand, toDoTaskList);
		}
		
		if (doneTaskList.isEmpty()) {
			print.println(noTaskDone);
		} else {
			getTaskDetails(taskDone, doneTaskList);
		}
		
		LOGGER.log(Level.INFO, "Write to file successfully");
		print.close();
	}
	
	private void getTaskDetails(String taskCategory, ArrayList<Task> taskList) {		
		print.println(taskCategory);
		for (int i = 0; i < taskList.size(); i++) {
			int index = i + 1;
			if (!(taskList.get(i).getName() == null)) {
				print.println(index + ". Event: " + taskList.get(i).getName());
			}

			if (!(taskList.get(i).getDate() == null)) {
				print.println("Date: " + taskList.get(i).getDate());
			}

			if (!(taskList.get(i).getStartTime() == null)) {
				print.println("Start Time: " + taskList.get(i).getStartTime());
			}

			if (!(taskList.get(i).getEndTime() == null)) {
				print.println("End Time: " + taskList.get(i).getEndTime());
			}

			if (!(taskList.get(i).getDetails() == null)) {
				print.println("Details: " + taskList.get(i).getDetails());
			}
			
			if (!(taskList.get(i).getDay() == false)) {
				print.println("Day: " + taskList.get(i).getDay());
			}
			
			if (!(taskList.get(i).getWeek() == false)) {
				print.println("Week: " + taskList.get(i).getWeek());
			}
			
			if (!(taskList.get(i).getMonth() == false)) {
				print.println("Month: " + taskList.get(i).getMonth());
			}
			
			if (!(taskList.get(i).getYear() == false)) {
				print.println("Year: " + taskList.get(i).getYear());
			}
		}
		
		LOGGER.log(Level.INFO, "Get all tasks successfully");
	}
	
	public void updatePathSentence (String filePathName) {		
		try {
			tempFile = new File("temp.txt");
			printPath = new PrintWriter(new FileWriter("temp.txt"));
			reader = new BufferedReader(new FileReader(Constants.fileName));
			addPathAndCopyFileContent(filePathName, printPath, reader);

			// Revert the copy back and delete the temp file
			printPath = new PrintWriter(new FileWriter(Constants.fileName));
			reader = new BufferedReader(new FileReader("temp.txt"));
			addPathAndCopyFileContent(filePathName, printPath, reader);
			tempFile.delete();
			LOGGER.log(Level.INFO, "Update PATH: sentence to mytextfile.txt successfully");
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to update PATH: sentence to mytextfile.txt");
			e.printStackTrace();
		}
	}
	
	// Add an additional "PATH: " sentence to the beginning of the file
	private void addPathAndCopyFileContent (String filePathName, PrintWriter printPath, BufferedReader read) {
		try {
			String content;
			String firstSentence = "PATH: " + filePathName;
			printPath.println(firstSentence);

			while ((content = read.readLine()) != null) {
				String path = content.substring(0, content.indexOf(" "));
				if(!(path.equals(pathVariable))) {
					printPath.println(content);
				} 
			}
			
			printPath.close();
			read.close();
			LOGGER.log(Level.INFO, "Add PATH: sentence to mytextfile.txt successfully");
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to add PATH: sentence to mytextfile.txt");
			e.printStackTrace();
		}
	}
}