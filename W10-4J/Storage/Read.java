package Storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.Task;

public class Read {
	private final Logger LOGGER = Logger.getLogger(Read.class.getName());
	private String taskOnHand = "Tasks on hand:";
	private String taskDone = "Tasks that are done:";
	private String noTaskOnHand = "No tasks on hand!";
	private String noTaskDone = "No tasks are done!";
	private String pathVariable = "PATH:";
	private static Read read;
	private Task task = null;
	
	// Show Singleton
	public static Read getInstance() {
		if(read == null) {
			read = new Read();
		}
		return read;
	}
	
	// This method is called by Handler
	public ArrayList<ArrayList<Task>> readFromFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Storage.filename));
			ArrayList<ArrayList<Task>> readTaskList = readTask(reader);
			reader.close();
			LOGGER.log(Level.INFO, "Read the contents from file successfully");
			return readTaskList;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to read the contents in the file");
			return null;
		}
	}

	/*
	 * Method overloading for read()
	 * This method is called by Retrieve
	 */
	public ArrayList<ArrayList<Task>> readFromFile(BufferedReader reader) {
		try {
			ArrayList<ArrayList<Task>> readTaskList = readTask(reader);
			reader.close();
			LOGGER.log(Level.INFO, "Read the contents from file successfully");
			return readTaskList;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to read the contents in the file");
			return null;
		}
	}
	
	private ArrayList<ArrayList<Task>> readTask(BufferedReader read) {
		try {
			String content;
			String index = null;
			int count = 0;
			ArrayList<Task> readToDoTaskList = new ArrayList<Task>();
			ArrayList<Task> readDoneTaskList = new ArrayList<Task>();
			ArrayList<ArrayList<Task>> readTaskList = new ArrayList<ArrayList<Task>>();

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
					count = addTaskIntoArrayList(count, content, readToDoTaskList);
				} else {
					count = addTaskIntoArrayList(count, content, readDoneTaskList);
				}
			}
			readTaskList.add(readToDoTaskList);
			readTaskList.add(readDoneTaskList);
			LOGGER.log(Level.INFO, "Read all tasks successfully");
			return readTaskList;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to read all tasks");
			return null;
		}
	}
	
	private int addTaskIntoArrayList (int count, String content, ArrayList<Task> taskList) {
		String numberingCounter = count + ".";
		String numberingOnTask = content.substring(0, content.indexOf(" "));
		String taskName = content.substring(content.indexOf(": ") + 1).trim();

		// If the number of both the task and counter are equal,
		// implies its the start of a new task
		if (numberingCounter.equals(numberingOnTask)) {
			task = new Task(taskName);
			taskList.add(task);
			count++;
		} else {
			readTaskDetails(taskList, content, task);
		}
		
		return count;
	}
	
	private void readTaskDetails(ArrayList<Task> readTaskList, String taskContent, Task task) {
		String taskHeader = taskContent.substring(0, taskContent.indexOf(": "));
		taskContent = taskContent.substring(taskContent.indexOf(": ") + 1).trim();
		
		if (taskHeader.equals("Date")) {
			String taskDate = taskContent;
			task.setDate(taskDate);
		} else if (taskHeader.equals("Start Time")) {
			String taskStartTime = taskContent;
			task.setStartTime(taskStartTime);
		} else if (taskHeader.equals("End Time")) {
			String taskEndTime = taskContent;
			task.setEndTime(taskEndTime);
		} else if (taskHeader.equals("Details")){
			String taskDetails = taskContent;
			task.setDetails(taskDetails);
		} else if (taskHeader.equals("Day")){
			String taskDay = taskContent;
			boolean dayValue = Boolean.parseBoolean(taskDay);
			task.setDay(dayValue);
		} else if (taskHeader.equals("Week")){
			String taskWeek = taskContent;
			boolean weekValue = Boolean.parseBoolean(taskWeek);
			task.setWeek(weekValue);
		} else if (taskHeader.equals("Month")){
			String taskMonth = taskContent;
			boolean monthValue = Boolean.parseBoolean(taskMonth);
			task.setMonth(monthValue);
		} else {
			String taskYear = taskContent;
			boolean yearValue = Boolean.parseBoolean(taskYear);
			task.setYear(yearValue);
		}
	}
}