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
	private static Write write;
	private PrintWriter print;
	private PrintWriter printPath;
	private BufferedReader reader;
	private File tempFile;

	// Show Singleton
	public static Write getInstance() {
		if (write == null) {
			write = new Write();
		}
		return write;
	}
	
	public void writeTaskIDToFile(int taskID) {
		try {
			PrintWriter printer = new PrintWriter(new FileWriter(Constants.taskFileName));
			printer.println(taskID);
			printer.close();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to write taskID to file");
			e.printStackTrace();
		}
	}
	
	// Applicable if filename == Constants.fileName
	public void writeToFile(ArrayList<Task> toDoTaskList, ArrayList<Task> doneTaskList) {
		printTask(toDoTaskList, doneTaskList);
	}
	
	// Applicable if filename != Constants.fileName
	public void writeToFile(String filePathName, ArrayList<Task> toDoTaskList, ArrayList<Task> doneTaskList) {
		printTask(toDoTaskList, doneTaskList);
		updatePathSentence(filePathName);
	}
	
	private void printTask(ArrayList<Task> toDoTaskList, ArrayList<Task> doneTaskList) {		
		try {
			print = new PrintWriter(new FileWriter(Storage.filename));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to write to file");
			e.printStackTrace();
		}	
		
		if (toDoTaskList.isEmpty()) {
			print.println(Constants.MESSAGE_WRITE_READ_NOTASKONHAND);
		} else {
			printTaskDetails(Constants.MESSAGE_WRITE_READ_TASKONHAND, toDoTaskList);
		}
		
		if (doneTaskList.isEmpty()) {
			print.println(Constants.MESSAGE_WRITE_READ_NOTASKDONE);
		} else {
			printTaskDetails(Constants.MESSAGE_WRITE_READ_TASKDONE, doneTaskList);
		}
		
		LOGGER.log(Level.INFO, "Write to file successfully");
		print.close();
	}
	
	private void printTaskDetails(String taskCategory, ArrayList<Task> taskList) {		
		print.println(taskCategory);
		for (int i = 0; i < taskList.size(); i++) {
			int taskID = taskList.get(i).getTaskID();
			
			if (!(taskList.get(i).getName() == null)) {
				print.println(String.format(Constants.MESSAGE_WRITE_EVENT, taskID, taskList.get(i).getName()));
			}

			if (!(taskList.get(i).getDate() == null)) {
				print.println(String.format(Constants.MESSAGE_WRITE_DATE, taskList.get(i).getDate()));
			}

			if (!(taskList.get(i).getStartTime() == null)) {
				print.println(String.format(Constants.MESSAGE_WRITE_STARTTIME, taskList.get(i).getStartTime()));
			}

			if (!(taskList.get(i).getEndTime() == null)) {
				print.println(String.format(Constants.MESSAGE_WRITE_ENDTIME, taskList.get(i).getEndTime()));
			}

			if (!(taskList.get(i).getDetails() == null)) {
				print.println(String.format(Constants.MESSAGE_WRITE_DETAILS, taskList.get(i).getDetails()));
			}
			
			if (!(taskList.get(i).getDay() == false)) {
				print.println(String.format(Constants.MESSAGE_WRITE_DAY, taskList.get(i).getDay()));
			}
			
			if (!(taskList.get(i).getWeek() == false)) {
				print.println(String.format(Constants.MESSAGE_WRITE_WEEK, taskList.get(i).getWeek()));
			}
			
			if (!(taskList.get(i).getMonth() == false)) {
				print.println(String.format(Constants.MESSAGE_WRITE_MONTH, taskList.get(i).getMonth()));
			}
			
			if (!(taskList.get(i).getYear() == false)) {
				print.println(String.format(Constants.MESSAGE_WRITE_YEAR, taskList.get(i).getYear()));
			}
		}
		
		LOGGER.log(Level.INFO, "Get all tasks successfully");
	}
	
	public void updatePathSentence(String filePathName) {		
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
	private void addPathAndCopyFileContent(String filePathName, PrintWriter printPath, BufferedReader read) {
		try {
			String content;
			String firstSentence = "PATH: " + filePathName;
			printPath.println(firstSentence);

			while ((content = read.readLine()) != null) {
				String path = content.substring(0, content.indexOf(" "));
				if(!(path.equals(Constants.MESSAGE_WRITE_READ_PATH))) {
					printPath.println(content);
				} 
			}
			
			printPath.close();
			read.close();
			LOGGER.log(Level.INFO, "Add PATH: sentence successfully");
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to add PATH: sentence");
			e.printStackTrace();
		}
	}
}