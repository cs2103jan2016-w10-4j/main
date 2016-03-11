package Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import main.Constants;
import main.Task;

public class Write {
	private PrintWriter print;
	private PrintWriter printPath;
	private BufferedReader reader;
	private File tempFile;
	private String taskOnHand = "Tasks on hand:";
	private String taskDone = "Tasks that are done:";
	private String noTaskOnHand = "No tasks on hand!";
	private String noTaskDone = "No tasks are done!";
	private String success = "Success";
	private String failure = "Failure";
	private String pathVariable = "PATH:";
	
	// Applicable if filename == Constants.fileName
	public String writeToFile(ArrayList<Task> toDoTaskList, ArrayList<Task> doneTaskList) {
		try {
			print = new PrintWriter(new FileWriter(Storage.filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String status = checkFileExists(Storage.filename);
		if (status.equals(success)) {
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
		}
		
		print.close();
		return status;
	}
	
	// Applicable if filename != Constants.fileName
	public String writeToFile(String filePathName, ArrayList<Task> toDoTaskList, ArrayList<Task> doneTaskList) {
		try {
			print = new PrintWriter(new FileWriter(Storage.filename));
			updatePathSentence(filePathName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String status = checkFileExists(Storage.filename);
		
		if(status.equals(success)) {
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
		}
		
		print.close();
		return status;
	}
	
	// Applicable for Retrieve without overwriting the Path found in the default text
	public void writeToFile(String filePathName, ArrayList<ArrayList<Task>> taskList) {
		try {
			print = new PrintWriter(new FileWriter(Storage.filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String status = checkFileExists(Storage.filename);
		ArrayList<Task> toDoTaskList = taskList.get(0);
		ArrayList<Task> doneTaskList = taskList.get(1);
		
		if(status.equals(success)) {
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
		}
		
		print.close();
	}
	
	private String checkFileExists(String filename) {
		String outcome;
		File file = new File(filename);
		if (file.exists()) {
			outcome = success;
		} else {
			outcome = failure;
		}

		return outcome;
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
		}
	}
	
	public void updatePathSentence (String filePathName) {
		try {
			tempFile = new File("temp.txt");
			printPath = new PrintWriter(new FileWriter("temp.txt"));
			reader = new BufferedReader(new FileReader(Constants.fileName));
			copyFileContent(filePathName, printPath, reader);

			// Revert the copy back and delete the temp file
			printPath = new PrintWriter(new FileWriter(Constants.fileName));
			reader = new BufferedReader(new FileReader("temp.txt"));
			copyFileContent(filePathName, printPath, reader);
			tempFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Add an additional "PATH: " sentence at the beginning of the file
	private void copyFileContent (String filePathName, PrintWriter printPath, BufferedReader read) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
