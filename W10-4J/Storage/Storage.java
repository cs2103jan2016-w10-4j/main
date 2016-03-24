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

	Write taskWriter;
	Read taskReader;
	SetDirectory taskSetDirectory;
	
	public Storage() {
		setEnvironment();
		taskWriter = Write.getInstance();
		taskReader = Read.getInstance();
		taskSetDirectory = SetDirectory.getInstance();
	}

	/*
	 * Check if file and directory exists before proceeding on to read
	 * the content in the file
	 */
	private void setEnvironment() {
		try{
			String outcome = checkFileExists(filename);
			if (outcome.equals(failure)) {
				LOGGER.log(Level.INFO, "Creating file in progress");
				File file = new File(filename);
				Path path = FileSystems.getDefault().getPath(filename);
				createFile(file, path);
				LOGGER.log(Level.INFO, "File is successfully created");
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to create file");
			e.printStackTrace();
		}
	}
	
	private void createFile(File file, Path path) throws IOException {
		if (file.isDirectory()) {
			Files.createFile(path);
		} else {
			String excludeFileName = filename.substring(0, filename.lastIndexOf("/") + 1);
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
		
		if(method.equals("Retrieve")) {
			// Called by Handler Retrieve
			try {
				BufferedReader reader = new BufferedReader(new FileReader(nameOfTheFile));
				readTaskList = taskReader.readFromFile(reader);
			} catch (FileNotFoundException e){
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
	
	public boolean setDirectory(String filePathName){
		if(taskSetDirectory.setDirectory(filePathName)){
			filename = filePathName;
			taskWriter.updatePathSentence(filePathName);
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