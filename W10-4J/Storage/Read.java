package Storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.Task;
import main.Constants;

public class Read {
	private final Logger LOGGER = Logger.getLogger(Read.class.getName());
	private static Read read;
	private Task task = null;
	
	// Show Singleton
	public static Read getInstance() {
		if (read == null) {
			read = new Read();
		}
		return read;
	}
	
	public int readTaskIDFromFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Constants.taskFileName));
			String content;
			int taskID = 0;
			
			if ((content = reader.readLine()) != null) {
				taskID = Integer.valueOf(content);
			} 
			reader.close();
			return taskID;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to read taskID in the file");
			return -1;
		}
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

	 // This method is called by Handler Retrieve method
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
			ArrayList<Task> readToDoTaskList = new ArrayList<Task>();
			ArrayList<Task> readDoneTaskList = new ArrayList<Task>();
			ArrayList<ArrayList<Task>> readTaskList = new ArrayList<ArrayList<Task>>();

			while ((content = read.readLine()) != null) {
				String path = content.substring(0, content.indexOf(" "));
				if (path.equals(Constants.MESSAGE_WRITE_READ_PATH)) {
					continue;
				} else if (content.equals(Constants.MESSAGE_WRITE_READ_NOTASKDONE) || content.equals(Constants.MESSAGE_WRITE_READ_NOTASKONHAND)) {
					continue;
				} else if (content.equals(Constants.MESSAGE_WRITE_READ_TASKONHAND)) {
					index = Constants.MESSAGE_WRITE_READ_TASKONHAND;
				} else if (content.equals(Constants.MESSAGE_WRITE_READ_TASKDONE)) {
					index = Constants.MESSAGE_WRITE_READ_TASKDONE;
				} else if (!content.equals(Constants.MESSAGE_WRITE_READ_TASKONHAND) && !content.equals(Constants.MESSAGE_WRITE_READ_TASKDONE) && index.equals(Constants.MESSAGE_WRITE_READ_TASKONHAND)) {
					addTaskIntoArrayList(content, readToDoTaskList);
				} else {
					addTaskIntoArrayList(content, readDoneTaskList);
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
	
	private void addTaskIntoArrayList(String content, ArrayList<Task> taskList) {
		int colonIndex = content.indexOf(":");
		String lastThreeCharInTaskCategory = content.substring(colonIndex - 3, colonIndex);
		String lastThreeCharInEvent = "ent";
		
		if (lastThreeCharInTaskCategory.equals(lastThreeCharInEvent)) {
			String numberingOnTask = content.substring(0, content.indexOf(".")).trim();
			String taskName = content.substring(content.indexOf(": ") + 1).trim();
			int taskID = Integer.parseInt(numberingOnTask);
			
			task = new Task(taskName);
			task.setTaskID(taskID);
			taskList.add(task);
		} else {
			setTaskDetails(taskList, content, task);
		}
	}
	
	private void setTaskDetails(ArrayList<Task> readTaskList, String taskContent, Task task) {
		String taskHeader = taskContent.substring(0, taskContent.indexOf(": ")).trim();
		taskContent = taskContent.substring(taskContent.indexOf(": ") + 1).trim();
		
		if (taskHeader.equals(Constants.MESSAGE_READ_DATE)) {
			String taskDate = taskContent;
			task.setDate(taskDate);
		} else if (taskHeader.equals(Constants.MESSAGE_READ_STARTTIME)) {
			String taskStartTime = taskContent;
			task.setStartTime(taskStartTime);
		} else if (taskHeader.equals(Constants.MESSAGE_READ_ENDTIME)) {
			String taskEndTime = taskContent;
			task.setEndTime(taskEndTime);
		} else if (taskHeader.equals(Constants.MESSAGE_READ_DETAILS)) {
			String taskDetails = taskContent;
			task.setDetails(taskDetails);
		} else if (taskHeader.equals(Constants.MESSAGE_READ_DAY)) {
			String taskDay = taskContent;
			boolean dayValue = Boolean.parseBoolean(taskDay);
			task.setDay(dayValue);
		} else if (taskHeader.equals(Constants.MESSAGE_READ_WEEK)) {
			String taskWeek = taskContent;
			boolean weekValue = Boolean.parseBoolean(taskWeek);
			task.setWeek(weekValue);
		} else if (taskHeader.equals(Constants.MESSAGE_READ_MONTH)) {
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