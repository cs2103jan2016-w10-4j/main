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
	private static String success = "Success";
	private static String failure = "Failure";
	private String pathVariable = "PATH:";
	public static String filename = Constants.fileName;

	public Storage() {
		setEnvironment();
	}

	/*
	 * Check if file and directory exists before proceeding on to read
	 * the content in the file
	 */
	private void setEnvironment() {
		try {
			String outcome = checkFileExists(filename);
			if (outcome.equals(failure)) {
				LOGGER.log(Level.INFO, "Creating file in progress");
				
				File file = new File(filename);
				Path path = FileSystems.getDefault().getPath(filename);

				if (file.isDirectory()) {
					Files.createFile(path);
				} else {
					String excludeFileName = filename.substring(0, filename.lastIndexOf("/") + 1);
					Path pathWithoutFileName = Paths.get(excludeFileName);
					Files.createDirectories(pathWithoutFileName);
					Files.createFile(path);
				}
				
				LOGGER.log(Level.INFO, "File is successfully created");
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to create file");
			e.printStackTrace();
		}
	}
	
	public String write(ArrayList<Task> toDoTaskList, ArrayList<Task> doneTaskList) {
		String status;
		Write write = Write.getInstance();
		
		if (filename.equals(Constants.fileName)) {
			status = write.writeToFile(toDoTaskList, doneTaskList);
		} else {
			updateFilenameIfPathExists();
			status = write.writeToFile(filename, toDoTaskList, doneTaskList);
		}
		return status;
	}

	public ArrayList<ArrayList<Task>> read() {
		Read read = Read.getInstance();
		updateFilenameIfPathExists();
		ArrayList<ArrayList<Task>> readTaskList = read.readFromFile();
		return readTaskList;
	}

	public ArrayList<ArrayList<Task>> retrieve(String fileName) throws FileNotFoundException {
		Retrieve retrieve = Retrieve.getInstance();
		updateFilenameIfPathExists();
		try{
			ArrayList<ArrayList<Task>> taskList = retrieve.retrieveTaskListFromFile(fileName);
			return taskList;
		} catch(FileNotFoundException e){
			LOGGER.log(Level.WARNING, "Unable to find file");
			throw new FileNotFoundException();
		}
	}

	public boolean setDirectory(String filePathName){
		SetDirectory setDirectory = SetDirectory.getInstance();
		if(setDirectory.setDirectory(filePathName)){
			filename = filePathName;
			Write write = Write.getInstance();
			write.updatePathSentence(filePathName);
			return true;
		} else{
			return false;
		}
	}
	
	/*
	 * Check if the "PATH: " exists in the beginning of mytextfile.txt
	 * If it does, updates the filename variable
	 */
	private void updateFilenameIfPathExists() {
		try {
			BufferedReader read = new BufferedReader(new FileReader(Constants.fileName));
			String content = read.readLine();
			
			if(content != null) {
				String path = content.substring(0, content.indexOf(" "));
				if(path.equals(pathVariable)) {
					String absolutePath = content.substring(content.indexOf(" ") + 1, content.length());
					if(checkFileExists(absolutePath).equals(success)) {
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
	
	private void updateFilenameVariable (String absolutePath) {
		assert absolutePath != null;
		filename = absolutePath;
	}
	
	public static String checkFileExists(String filename) {		
		String outcome;
		File file = new File(filename);

		if (file.exists()) {
			outcome = success;
		} else {
			outcome = failure;
		}
		return outcome;
	}
}