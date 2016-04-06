/*
 * @@author A0126129J
 */
package Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.Task;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SetDirectory {
	private final Logger LOGGER = Logger.getLogger(SetDirectory.class.getName());
	private static SetDirectory setDirectory;
	Read taskReader;

	private SetDirectory() {
		taskReader = Read.getInstance();
	}
	
	// Show Singleton
	public static SetDirectory getInstance() {
		if (setDirectory == null) {
			setDirectory = new SetDirectory();
		}
		return setDirectory;
	}

	/*
	 * Set the directory based on the absolute file path given by user
	 * This function will check if its a directory, if the absolute file path is a 
	 * directory but there is no such file, it will create the text file for user
	 */
	public ArrayList<ArrayList<Task>> setDirectory(String filePathName) {
		ArrayList<ArrayList<Task>> taskList = new ArrayList<ArrayList<Task>>();
		try {
			File file = new File(filePathName);
			if (!(isADirectory(file))) {
				Path path = FileSystems.getDefault().getPath(filePathName);
				createFile(file, path, filePathName);
			}
			BufferedReader reader = new BufferedReader(new FileReader(filePathName));
			taskList = taskReader.readFromFile(reader);
			LOGGER.log(Level.INFO, "Set directory successfully");
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Set directory unsuccessfully");
			return null;
		}
		return taskList;
	}

	private boolean isADirectory(File file) {
		if (file.exists()) {
			LOGGER.log(Level.INFO, "File exist");
			return true;
		} else {
			LOGGER.log(Level.INFO, "File does not exist");
			return false;
		}
	}
	
	private void createFile(File file, Path path, String filePathName) throws IOException {
		String excludeFileName = filePathName.substring(0, filePathName.lastIndexOf("/") + 1);
		Path pathWithoutFileName = Paths.get(excludeFileName);
		Files.createDirectories(pathWithoutFileName);
		Files.createFile(path);
	}
}