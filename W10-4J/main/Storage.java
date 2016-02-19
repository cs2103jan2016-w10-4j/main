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
		String contentArr [];
		int count = 0;
		Task taskContent = null;
		ArrayList<Task> readTaskList = new ArrayList<Task>();
		
		while ((content = read.readLine()) != null) {	
				
			// Remove the numbering at the start of each task
			if(count%4 == 0) {
				content = removeNumbering(content);
			}

			contentArr = content.split(": ");
			
			if(contentArr[0].equals("Event")) {
				if(count != 0) {
					readTaskList.add(taskContent);
				}
				taskContent = new Task(contentArr[1]);				
			} else if (contentArr[0].equals("Date")) {
				taskContent.setDate(contentArr[1]);
			} else if (contentArr[0].equals("Start Time")) {
				taskContent.setStartTime(contentArr[1]);
			} else {
				taskContent.setEndTime(contentArr[1]);
			}
			
			count++;
		}
		
		readTaskList.add(taskContent);
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