package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {
	private PrintWriter print;
	private BufferedReader read;
	
	public Storage() {
		try {
			read = new BufferedReader(new FileReader(Constants.fileName));
			print = new PrintWriter(new FileWriter(Constants.fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(ArrayList <Task> taskList) {
		for(int i = 0; i < taskList.size(); i++) {
			int index = i + 1;
			print.println(index + ". Event: " + taskList.get(i).getName());
			print.println("Date: " + taskList.get(i).getDate());
			print.println("Start Time: " + taskList.get(i).getStartTime());
			print.println("End Time: " + taskList.get(i).getEndTime());
		}
		print.close();
	}

	public ArrayList<Task> read() throws IOException {
		String content;
		String taskHeader;
		int count = 0;
		Task taskContent = null;
		ArrayList<Task> readTaskList = new ArrayList<Task>();
		
		while ((content = read.readLine()) != null) {	
			
			// Remove the numbering at the start of each task
			if(count%4 == 0) {
				content = removeNumbering(content);
			}
			
			taskHeader = content.substring(0, content.indexOf(": "));
			content = content.substring(content.indexOf(": ") + 1);
			
			if(taskHeader.equals("Event")){
				String taskName = content;
				taskContent = new Task(taskName);
				readTaskList.add(taskContent);
			} else if (taskHeader.equals("Date")){
				String taskDate = content;
				taskContent.setDate(taskDate);
			}else if (taskHeader.equals("Start Time")){
				String taskStartTime = content;
				taskContent.setStartTime(taskStartTime);
			}else {
				String taskEndTime = content;
				taskContent.setEndTime(taskEndTime);
			}
			count++;
		}
		return readTaskList;
	}
	
	private String removeNumbering(String taskDetails) {
		String withoutNumbering = taskDetails.replace(getNumbering(taskDetails), "").trim();
		return withoutNumbering;
	}

	private String getNumbering(String taskDetails) {
		String getNumbering = taskDetails.trim().split("\\s+")[0];
		return getNumbering;
	}
}