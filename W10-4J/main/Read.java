package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Read {
	private BufferedReader read;
	private String taskOnHand = "Tasks on hand:";
	private String taskDone = "Tasks that are done:";
	private String noTaskOnHand = "No tasks on hand!";
	private String noTaskDone = "No tasks are done!";
	private String pathVariable = "PATH:";

	public ArrayList<ArrayList<Task>> readFromFile() {
		try {
			String content;
			String index = null;
			int count = 0;
			Task task = null;
			ArrayList<Task> readToDoTaskList = new ArrayList<Task>();
			ArrayList<Task> readDoneTaskList = new ArrayList<Task>();
			ArrayList<ArrayList<Task>> readTaskList = new ArrayList<ArrayList<Task>>();
			read = new BufferedReader(new FileReader(Storage.filename));
			
			while ((content = read.readLine()) != null) {
				String path = content.substring(0, content.indexOf(" "));
				
				if(path.equals(pathVariable)) {
					continue;
				} else if(content.equals(noTaskDone) || content.equals(noTaskOnHand)) {
					continue;
				} else if (content.equals(taskOnHand)) {
					count = 1;
					index = taskOnHand;
				} else if (content.equals(taskDone)) {
					count = 1;
					index = taskDone;
				} else if (!content.equals(taskOnHand) && !content.equals(taskDone) && index.equals(taskOnHand)) {
					String numberingCounter = count + ".";
					String numberingOnTask = content.substring(0, content.indexOf(" "));
					String taskName = content.substring(content.indexOf(": ") + 1);

					// If the number of both the task and counter are equal,
					// implies its the start of a new task
					if (numberingCounter.equals(numberingOnTask)) {
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

					// If the number of both the task and counter are equal,
					// implies its the start of a new task
					if (numberingCounter.equals(numberingOnTask)) {
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
			read.close();
			return readTaskList;
		} catch (IOException e) {
			return null;
		}
	}

	// Method overloading for read()
	public ArrayList<ArrayList<Task>> readFromFile(BufferedReader reader) {
		try {
			String content;
			String index = null;
			int count = 0;
			Task task = null;
			ArrayList<Task> readToDoTaskList = new ArrayList<Task>();
			ArrayList<Task> readDoneTaskList = new ArrayList<Task>();
			ArrayList<ArrayList<Task>> readTaskList = new ArrayList<ArrayList<Task>>();
				
			while ((content = reader.readLine()) != null) {
				String path = content.substring(0, content.indexOf(" "));
				if(path.equals(pathVariable)) {
					continue;
				} else if(content.equals(noTaskDone) || content.equals(noTaskOnHand)) {
					continue;
				} else if (content.equals(taskOnHand)) {
					count = 1;
					index = taskOnHand;
				} else if (content.equals(taskDone)) {
					count = 1;
					index = taskDone;
				} else if (!content.equals(taskOnHand) && !content.equals(taskDone) && index.equals(taskOnHand)) {
					String numberingCounter = count + ".";
					String numberingOnTask = content.substring(0, content.indexOf(" "));
					String taskName = content.substring(content.indexOf(": ") + 1);

					// If the number of both the task and counter are equal,
					// implies its the start of a new task
					if (numberingCounter.equals(numberingOnTask)) {
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

					// If the number of both the task and counter are equal,
					// implies its the start of a new task
					if (numberingCounter.equals(numberingOnTask)) {
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
	
	private void readTaskDetails(ArrayList<Task> readTaskList, String taskContent, Task task) {
		String taskHeader = taskContent.substring(0, taskContent.indexOf(": "));

		taskContent = taskContent.substring(taskContent.indexOf(": ") + 1);
		if (taskHeader.equals("Date")) {
			String taskDate = taskContent;
			task.setDate(taskDate);
		} else if (taskHeader.equals("Start Time")) {
			String taskStartTime = taskContent;
			task.setStartTime(taskStartTime);
		} else if (taskHeader.equals("End Time")) {
			String taskEndTime = taskContent;
			task.setEndTime(taskEndTime);
		} else {
			String taskDetails = taskContent;
			task.setDetails(taskDetails);
		}
	}
}
