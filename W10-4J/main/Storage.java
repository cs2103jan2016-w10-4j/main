package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
	private PrintWriter print;
	private BufferedReader read;
	private String taskOnHand = "Tasks on hand:";
	private String taskDone = "Tasks that are done:";
	private String noTaskOnHand = "No tasks on hand!";
	private String noTaskDone = "No tasks are done!";
	private String success = "Success";
	private String failure = "Failure";
	
	public Storage() {
		try {		
			read = new BufferedReader(new FileReader(Constants.fileName));
			print = new PrintWriter(new FileWriter(Constants.fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String write(ArrayList <Task> toDoTaskList, ArrayList <Task> doneTaskList) {
		String status = checkFileExists(Constants.fileName);
		
		if(toDoTaskList.isEmpty()) {
			print.println(noTaskOnHand);
		} else {
			getTaskDetails(taskOnHand, toDoTaskList);
		}
				
		if (doneTaskList.isEmpty()) {
			print.println(noTaskDone);
		} else {
			getTaskDetails(taskDone, doneTaskList);
		}
		
		print.close();
		return status;
	}

	public ArrayList<ArrayList<Task>> read() {
		try {		
			String content;
			String index = null;
			int count = 0;
			Task task = null;
			ArrayList<Task> readToDoTaskList = new ArrayList<Task>();
			ArrayList<Task> readDoneTaskList = new ArrayList<Task>();
			ArrayList <ArrayList <Task>> readTaskList = new ArrayList<ArrayList<Task>>();

			while ((content = read.readLine()) != null) {	
				if(content.equals(taskOnHand)) {
					count = 1;
					index = taskOnHand; 
				} else if (content.equals(taskDone)) {
					count = 1;
					index = taskDone;
				} else if (!content.equals(taskOnHand) && !content.equals(taskDone) && index.equals(taskOnHand)){
					String numberingCounter = count + ".";
					String numberingOnTask = content.substring(0, content.indexOf(" "));
					String taskName = content.substring(content.indexOf(": ") + 1);
					
					// If the number of both the task and counter are equal, implies its the start of a new task
					if(numberingCounter.equals(numberingOnTask)){
						task = new Task(taskName);
						readToDoTaskList.add(task);
						count++;
					} else {
						readTaskDetails(readToDoTaskList, content, task);
					}
				} else {
					String numberingCounter = count + ".";
					String numberingOnTask = content.substring(0, content.indexOf(" "));
					String taskName = content.substring(content.indexOf(": ") + 1);

					// If the number of both the task and counter are equal, implies its the start of a new task
					if(numberingCounter.equals(numberingOnTask)){
						task = new Task(taskName);
						readDoneTaskList.add(task);
						count++;
					} else {
						readTaskDetails(readDoneTaskList, content, task);
					}
				}
			}

			readTaskList.add(readToDoTaskList);
			readTaskList.add(readDoneTaskList);
			return readTaskList;
			
		} catch (IOException e) {
			return null;
		}
	}
	
	// Method overloading for read()
	public ArrayList<ArrayList<Task>> read(BufferedReader reader) {
		try {
			String content;
			String index = null;
			int count = 0;
			Task task = null;
			ArrayList<Task> readToDoTaskList = new ArrayList<Task>();
			ArrayList<Task> readDoneTaskList = new ArrayList<Task>();
			ArrayList <ArrayList <Task>> readTaskList = new ArrayList<ArrayList<Task>>();

			while ((content = reader.readLine()) != null) {	
				if(content.equals(taskOnHand)) {
					count = 1;
					index = taskOnHand; 
				} else if (content.equals(taskDone)) {
					count = 1;
					index = taskDone;
				} else if (!content.equals(taskOnHand) && !content.equals(taskDone) && index.equals(taskOnHand)){
					String numberingCounter = count + ".";
					String numberingOnTask = content.substring(0, content.indexOf(" "));
					String taskName = content.substring(content.indexOf(": ") + 1);
					
					// If the number of both the task and counter are equal, implies its the start of a new task
					if(numberingCounter.equals(numberingOnTask)){
						task = new Task(taskName);
						readToDoTaskList.add(task);
						count++;
					} else {
						readTaskDetails(readToDoTaskList, content, task);
					}
				} else {
					String numberingCounter = count + ".";
					String numberingOnTask = content.substring(0, content.indexOf(" "));
					String taskName = content.substring(content.indexOf(": ") + 1);

					// If the number of both the task and counter are equal, implies its the start of a new task
					if(numberingCounter.equals(numberingOnTask)){
						task = new Task(taskName);
						readDoneTaskList.add(task);
						count++;
					} else {
						readTaskDetails(readDoneTaskList, content, task);
					}
				}
			}

			readTaskList.add(readToDoTaskList);
			readTaskList.add(readDoneTaskList);
			return readTaskList;
		} catch (IOException e) {
			return null;
		}
	}
	
	public ArrayList<ArrayList<Task>> retrieve(String filename) {
		try {
			// Check if file and directory exists before proceeding on to read the content in the file
			String outcome = checkFileExists(filename);
			if(outcome.equals(failure)){
				File file = new File (filename);
				Path path = FileSystems.getDefault().getPath(filename);
				
				if(file.isDirectory()){
					Files.createFile(path);
				} else {
					String excludeFileName = filename.substring(0, filename.lastIndexOf("/") + 1); 
					Path pathWithoutFileName = Paths.get(excludeFileName);
					Files.createDirectories(pathWithoutFileName);
					Files.createFile(path);
				}
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			ArrayList<ArrayList<Task>> taskList = read(reader);
			reader.close();
			return taskList;
		} catch (IOException e) {
			System.out.println(e);
			return null;
		}
	}
	
	private String checkFileExists(String filename){
		String outcome;
		File file = new File (filename);

		if(file.exists()) {
			outcome = success;
		} else {
			outcome = failure;
		}
		
		return outcome;
	}
	
	private void getTaskDetails(String taskCategory, ArrayList<Task> taskList) {
		print.println(taskCategory);

		for(int i = 0; i < taskList.size(); i++) {
			int index = i + 1;
			if(!(taskList.get(i).getName() == null)){
				print.println(index + ". Event: " + taskList.get(i).getName());
			}
			
			if(!(taskList.get(i).getDate() == null)){
				print.println("Date: " + taskList.get(i).getDate());
			}
			
			if(!(taskList.get(i).getStartTime() == null)){
				print.println("Start Time: " + taskList.get(i).getStartTime());
			}
			
			if(!(taskList.get(i).getEndTime() == null)){
				print.println("End Time: " + taskList.get(i).getEndTime());
			}
			
			if(!(taskList.get(i).getDetails() == null)){
				print.println("Details: " + taskList.get(i).getDetails());
			}
		}
	}
	
	private void readTaskDetails(ArrayList<Task> readTaskList, String taskContent, Task task){
		String taskHeader = taskContent.substring(0, taskContent.indexOf(": "));

		taskContent = taskContent.substring(taskContent.indexOf(": ") + 1);
		if (taskHeader.equals("Date")){
			String taskDate = taskContent;
			task.setDate(taskDate);
		} else if (taskHeader.equals("Start Time")){
			String taskStartTime = taskContent;
			task.setStartTime(taskStartTime);
		} else if (taskHeader.equals("End Time")){
			String taskEndTime = taskContent;
			task.setEndTime(taskEndTime);
		} else {
			String taskDetails = taskContent;
			task.setDetails(taskDetails);
		}
	}
}