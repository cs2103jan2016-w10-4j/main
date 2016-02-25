package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileWriter;

public class Storage {
	private PrintWriter print;
	private BufferedReader read;
	private String taskOnHand = "Tasks on hand:";
	private String taskDone = "Tasks that are done:";
	private String noTaskOnHand = "No tasks on hand!";
	private String noTaskDone = "No tasks are done!";
	
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
					String counter = count + ".";
					String taskHeader = content.substring(0, content.indexOf(" "));
					String taskName = content.substring(content.indexOf(": ") + 1);

					if(counter.equals(taskHeader)){
						task = new Task(taskName);
						readToDoTaskList.add(task);
						count++;
					} else {
						readTaskDetails(readToDoTaskList, content, task);
					}
				} else {
					String counter = count + ".";
					String taskHeader = content.substring(0, content.indexOf(": "));
					String taskName = content.substring(content.indexOf(": ") + 1);

					if(counter.equals(taskHeader)){
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
	
	private String checkFileExists(String filename){
		String statement;
		File file = new File (filename);
		if(file.exists()) {
			statement = "Success";
		} else {
			statement = "Failure";
		}
		
		return statement;
	}
	
	private void getTaskDetails(String task, ArrayList<Task> taskList) {
		print.println(task);

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
	
	private void readTaskDetails(ArrayList<Task> readTaskList, String content, Task taskContent){
		String taskHeader = content.substring(0, content.indexOf(": "));

		content = content.substring(content.indexOf(": ") + 1);
		if (taskHeader.equals("Date")){
			String taskDate = content;
			taskContent.setDate(taskDate);
		}else if (taskHeader.equals("Start Time")){
			String taskStartTime = content;
			taskContent.setStartTime(taskStartTime);
		}else if (taskHeader.equals("End Time")){
			String taskEndTime = content;
			taskContent.setEndTime(taskEndTime);
		}else {
			String taskDetails = content;
			taskContent.setDetails(taskDetails);
		}
	}
}