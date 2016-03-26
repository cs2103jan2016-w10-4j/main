package Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import main.Constants;
import main.Task;

public class Storage {
	private final Logger LOGGER = Logger.getLogger(Storage.class.getName());
	public static String filename = Constants.fileName;

	Write taskWriter;
	Read taskReader;
	SetDirectory taskSetDirectory;
	
	public Storage() {
		taskWriter = Write.getInstance();
		taskReader = Read.getInstance();
		taskSetDirectory = SetDirectory.getInstance();
		setEnvironment();
	}

	/*
	 * Check if file and directory exists before proceeding on to read
	 * the content in the file
	 */
	private void setEnvironment() {
			createDefaultFile();
			createTaskIDFile();
	}
	
	private void createDefaultFile() {
		try {
			String outcome = checkFileExists(filename);
			if (outcome.equals(Constants.MESSAGE_STORAGE_FAILURE)) {
				LOGGER.log(Level.INFO, "Creating file in progress");
				creatingFile(filename);
				writeTaskID(0);
				LOGGER.log(Level.INFO, "File is successfully created");
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to create file");
			e.printStackTrace();
		}
	}
	
	private void createTaskIDFile() {
		try {
			String outcomeTaskIDFile = checkFileExists(Constants.taskFileName);
			if (outcomeTaskIDFile.equals(Constants.MESSAGE_STORAGE_FAILURE)) {
				LOGGER.log(Level.INFO, "Creating taskID file in progress");
				creatingFile(Constants.taskFileName);
				LOGGER.log(Level.INFO, "TaskID File is successfully created");
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to create file");
			e.printStackTrace();
		}
	}
	
	private void creatingFile(String fileName) throws IOException {
		File file = new File(fileName);
		Path path = FileSystems.getDefault().getPath(fileName);
		
		if (file.isDirectory()) {
			Files.createFile(path);
		} else {
			String excludeFileName = fileName.substring(0, fileName.lastIndexOf("/") + 1);
			Path pathWithoutFileName = Paths.get(excludeFileName);
			Files.createDirectories(pathWithoutFileName);
			Files.createFile(path);
		}
	}
	
	public void write(ArrayList<Task> toDoTaskList, ArrayList<Task> doneTaskList) {		
		if (filename.equals(Constants.fileName)) {
			taskWriter.writeToFile(toDoTaskList, doneTaskList);
		} else {
			updateFilenameIfPathExists();
			taskWriter.writeToFile(filename, toDoTaskList, doneTaskList);
		}
	}

	public ArrayList<ArrayList<Task>> read(String method, String nameOfTheFile) {
		ArrayList<ArrayList<Task>> readTaskList = new ArrayList<ArrayList<Task>> ();
		
		if (method.equals(Constants.MESSAGE_ACTION_RETRIEVE)) {
			// Called by Handler Retrieve
			try {
				BufferedReader reader = new BufferedReader(new FileReader(nameOfTheFile));
				readTaskList = taskReader.readFromFile(reader);
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.WARNING, "File does not exist");
				e.printStackTrace();
			}
		} else {
			// Normal reading from Storage.filename 
			updateFilenameIfPathExists();
			readTaskList = taskReader.readFromFile();
		}
		return readTaskList;
	}
	
	public boolean setDirectory(String filePathName) {
		if (taskSetDirectory.setDirectory(filePathName)) {
			filename = filePathName;
			taskWriter.updatePathSentence(filePathName);
			return true;
		} else {
			return false;
		}
	}
	
	public int getTaskID() {
		int taskID = taskReader.readTaskIDFromFile();
		return taskID;
	}
	
	public void writeTaskID(int taskID) {
		taskWriter.writeTaskIDToFile(taskID);
	}
	
	/*
	 * Check if the "PATH: " exists in the beginning of mytextfile.txt
	 * If it does, updates the filename variable
	 */
	private void updateFilenameIfPathExists() {
		try {
			BufferedReader read = new BufferedReader(new FileReader(Constants.fileName));
			String content = read.readLine();
			
			if (content != null) {
				String path = content.substring(0, content.indexOf(" "));
				if (path.equals(Constants.MESSAGE_STORAGE_PATH)) {
					String absolutePath = content.substring(content.indexOf(" ") + 1, content.length());
					if (checkFileExists(absolutePath).equals(Constants.MESSAGE_STORAGE_SUCCESS)) {
						updateFilenameVariable(absolutePath);
					}
				} 
			}
			read.close();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Failed to read PATH: value from mytextfile.txt");
			e.printStackTrace();
		}
	}
	
	private void updateFilenameVariable(String absolutePath) {
		filename = absolutePath;
	}
	
	public static String checkFileExists(String filename) {		
		String outcome;
		File file = new File(filename);

		if (file.exists()) {
			outcome = Constants.MESSAGE_STORAGE_SUCCESS;
		} else {
			outcome = Constants.MESSAGE_STORAGE_FAILURE;
		}
		return outcome;
	}
}