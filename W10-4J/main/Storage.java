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
			print.println("   Date: " + taskList.get(i).getDate());
			print.println("   Start Time: " + taskList.get(i).getStartTime());
			print.println("   End Time: " + taskList.get(i).getEndTime());
		}
		print.close();
	}

	public ArrayList<Task> read() throws IOException {
		
		String line;
		String content = null;
		String eventArr [];
		String dateArr[];
		String startTimeArr[];
		String endTimeArr[];
		int count = 0;
		Task taskContent = null;
		ArrayList<Task> readTaskList = new ArrayList<Task>();
		
		while ((line = read.readLine()) != null) {	
			if(line.contains("Event")){
				
				// Start adding into the arraylist after it reaches a new task
				if(count != 0) {
					readTaskList.add(taskContent);
				}
				
				content = removeNumbering(line);
				eventArr = content.split("Event: ");
				taskContent = new Task(eventArr[1]);
				count++;
			}
			else if (line.contains("Date")){
				dateArr = line.split("Date: ");
				taskContent.setDate(dateArr[1]);
			}
			else if (line.contains("Start Time")){
				startTimeArr = line.split("Start Time: ");
				taskContent.setStartTime(startTimeArr[1]);
			}
			else {
				endTimeArr = line.split("End Time: ");
				taskContent.setEndTime(endTimeArr[1]);
			}
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