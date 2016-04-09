/*
 * @@author A0126129J
 */
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
	
	// Build Empty Constructor
	private Read() {
	}
	
	// Show Singleton
	public static Read getInstance() {
		if (read == null) {
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
			String fileContent;
			String index = null;
			ArrayList<Task> readToDoTaskList = new ArrayList<Task>();
			ArrayList<Task> readDoneTaskList = new ArrayList<Task>();
			ArrayList<ArrayList<Task>> readTaskList = new ArrayList<ArrayList<Task>>();

			while ((fileContent = read.readLine()) != null) {
				String path = fileContent.substring(0, fileContent.indexOf(Constants.READ_SPACE));
				if (path.equals(Constants.WRITE_READ_PATH)) {
					continue;
				} else if (fileContent.equals(Constants.WRITE_READ_NOTASKDONE) 
						|| fileContent.equals(Constants.WRITE_READ_NOTASKONHAND)) {
					continue;
				} else if (fileContent.equals(Constants.WRITE_READ_TASKONHAND)) {
					index = Constants.WRITE_READ_TASKONHAND;
				} else if (fileContent.equals(Constants.WRITE_READ_TASKDONE)) {
					index = Constants.WRITE_READ_TASKDONE;
				} else if (!fileContent.equals(Constants.WRITE_READ_TASKONHAND) 
						&& !fileContent.equals(Constants.WRITE_READ_TASKDONE) 
						&& index.equals(Constants.WRITE_READ_TASKONHAND)) {
					addTaskIntoArrayList(fileContent, readToDoTaskList);
				} else if (!fileContent.equals(Constants.WRITE_READ_TASKONHAND) 
						&& !fileContent.equals(Constants.WRITE_READ_TASKDONE) 
						&& index.equals(Constants.WRITE_READ_TASKDONE)) {
					addTaskIntoArrayList(fileContent, readDoneTaskList);
				} else {
					return null;
				}
			}
			
			readTaskList.add(readToDoTaskList);
			readTaskList.add(readDoneTaskList);
			LOGGER.log(Level.INFO, "Read all tasks successfully");
			return readTaskList;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Unable to read all tasks");
			return null;
		}
	}
	
	private void addTaskIntoArrayList(String fileContent, ArrayList<Task> taskList) {
		int colonIndex = fileContent.indexOf(Constants.READ_COLON);
		String lastThreeCharInTaskCategory = fileContent.substring(colonIndex - 3, colonIndex);
		
		if (lastThreeCharInTaskCategory.equals(Constants.READ_LAST_THREE_CHAR_IN_EVENT)) {
			String taskName = fileContent.substring(fileContent.indexOf(Constants.READ_COLON_WITH_SPACE) + 1).trim();
			task = new Task(taskName);
			taskList.add(task);
		} else {
			setTaskDetails(taskList, fileContent, task);
		}
	}
	
	private void setTaskDetails(ArrayList<Task> readTaskList, String taskContent, Task task) {
		String taskCategory = taskContent.substring(0, taskContent.indexOf(Constants.READ_COLON_WITH_SPACE)).trim();
		taskContent = taskContent.substring(taskContent.indexOf(Constants.READ_COLON_WITH_SPACE) + 1).trim();
		
		if (taskCategory.equals(Constants.READ_STARTDATE)) {
			String taskStartDate = taskContent;
			task.setStartDate(taskStartDate);
		} else if (taskCategory.equals(Constants.READ_ENDDATE)) {
			String taskEndDate = taskContent;
			task.setEndDate(taskEndDate);
		} else if (taskCategory.equals(Constants.READ_STARTTIME)) {
			String taskStartTime = taskContent;
			task.setStartTime(taskStartTime);
		} else if (taskCategory.equals(Constants.READ_ENDTIME)) {
			String taskEndTime = taskContent;
			task.setEndTime(taskEndTime);
		} else if (taskCategory.equals(Constants.READ_DETAILS)) {
			String taskDetails = taskContent;
			task.setDetails(taskDetails);
		} else if (taskCategory.equals(Constants.READ_DAY)) {
			String taskDay = taskContent;
			boolean dayValue = Boolean.parseBoolean(taskDay);
			task.setDay(dayValue);
		} else if (taskCategory.equals(Constants.READ_WEEK)) {
			String taskWeek = taskContent;
			boolean weekValue = Boolean.parseBoolean(taskWeek);
			task.setWeek(weekValue);
		} else if (taskCategory.equals(Constants.READ_MONTH)) {
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