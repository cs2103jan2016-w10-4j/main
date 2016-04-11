// @@author A0126129J
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
	public static String filename = Constants.DEFAULT_FILENAME;

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
	 * Check if file and directory exists before proceeding 
	 * on to read the content in the file
	 */
	private void setEnvironment() {
			createDefaultFile();
	}
	
	private void createDefaultFile() {
		try {
			if (!(isFileExists(filename))) {
				LOGGER.log(Level.INFO, "Creating file in progress");
				creatingFile(filename);
				LOGGER.log(Level.INFO, "File is successfully created");
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
		assert toDoTaskList != null && doneTaskList != null 
				: Constants.ASSERT_WRITE_ARRAYLISTS;
		
		if (filename.equals(Constants.DEFAULT_FILENAME)) {
			taskWriter.writeToFile(toDoTaskList, doneTaskList);
		} else {
			updateFilenameIfPathExists();
			taskWriter.writeToFile(filename, toDoTaskList, doneTaskList);
		}
	}

	public ArrayList<ArrayList<Task>> read(String method, String nameOfTheFile) {
		assert (method != Constants.MESSAGE_ACTION_RETRIEVE ||  method != Constants.MESSAGE_ACTION_READ) 
		&& (nameOfTheFile != null && nameOfTheFile.length() != 0) 
		: Constants.ASSERT_READ_WRONGMETHOD_FILENAME_EMPTYNULL;
	
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
		} else if (method.equals(Constants.MESSAGE_ACTION_READ)) {
			// Normal reading from Storage.filename 
			updateFilenameIfPathExists();
			readTaskList = taskReader.readFromFile();
		} else {
			return null;
		}
		return readTaskList;
	}
	
	public ArrayList<ArrayList<Task>> setDirectory(String filePathName) {
		assert filePathName.length() != 0 : Constants.ASSERT_SETDIRECTORY_FILENAME_EMPTY;
		
		filePathName = setFileName(filePathName);
		ArrayList<ArrayList<Task>> taskList = taskSetDirectory.setDirectory(filePathName);
		if (taskList != null) {
			filename = filePathName;
			taskWriter.updatePathSentence(filePathName);
			LOGGER.log(Level.INFO, "Set directory successfully");
		}
		return taskList;
	}
	
	/*
	 * Check if the "PATH: " exists in the beginning of mytextfile.txt
	 * If it does, updates the filename variable
	 */
	private void updateFilenameIfPathExists() {
		try {
			BufferedReader read = new BufferedReader(new FileReader(Constants.DEFAULT_FILENAME));
			String fileContent = read.readLine();
			
			if (fileContent != null) {
				String path = fileContent.substring(0, fileContent.indexOf(" "));
				if (path.equals(Constants.STORAGE_PATH)) {
					String absolutePath = fileContent.substring(fileContent.indexOf(" ") + 1, fileContent.length());
					if (isFileExists(absolutePath)) {
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
	
	private boolean isFileExists(String filename) {		
		File file = new File(filename);

		if (file.exists()) {
			LOGGER.log(Level.INFO, "File exist");
			return true;
		} else {
			LOGGER.log(Level.INFO, "File does not exist");
			return false;
		}
	}
	
	/*
	 * Check if user has input the filename.
	 * If it does not, the filename will be assigned with a default name [dirdefaultfile.txt]
	 * Else, the filename will be assigned based on the name indicated by user
	 */
	private String setFileName(String filename) {
		int pathLength = filename.length();
		if(pathLength <= (Constants.SETDIR_TEXTFILE_EXTENSION.length())) {
			filename = filename.concat(Constants.STORAGE_SLASH + Constants.setDirFileName);
		} else {
			String lastFourChar = filename.substring(pathLength - 4, pathLength);
			if (!(lastFourChar.equalsIgnoreCase(Constants.SETDIR_TEXTFILE_EXTENSION))) {
				filename = filename.concat(Constants.STORAGE_SLASH + Constants.setDirFileName);
			}
		}
		return filename;
	}
	
	// Needed for Handler undo setdir
	public String getCurrentFilename() {
		return filename;
	}
}