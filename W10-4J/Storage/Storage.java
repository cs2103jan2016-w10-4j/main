package Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
	public static final String defaultFilename = "mytextfile.txt";
	public static String filename = defaultFilename;
	
	private String success = "Success";
	private String failure = "Failure";
	private String pathVariable = "PATH:";
	
	public Storage() {
		try {
			// Check if file and directory exists before proceeding on to read
			// the content in the file
			String outcome = checkFileExists(filename);
			if (outcome.equals(failure)) {
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
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String write(String [] taskList) {
		String status;
		Write write = new Write();
		if (filename.equals(defaultFilename)) {
			status = write.writeToFile(taskList);
		} else {
			updateFilenameIfPathExists();
			status = write.writeToFile(filename, taskList);
		}
		return status;
	}

	public String[] read() {
		Read read = new Read();
		updateFilenameIfPathExists();
		String[] readTaskList = read.readFromFile();
		return readTaskList;
	}

	// Method overloading for read()
	public String[] read(BufferedReader reader) {
		Read read = new Read();
		updateFilenameIfPathExists();
		String[] readTaskList = read.readFromFile(reader);
		return readTaskList;
	}

	public String[] retrieve(String fileName) throws FileNotFoundException {
		Retrieve retrieve = new Retrieve();
		updateFilenameIfPathExists();
		try{
			String [] taskList = retrieve.retrieveTaskListFromFile(fileName);
			return taskList;
		} catch(FileNotFoundException e){
			throw new FileNotFoundException();
		}
	}

	public boolean setDirectory(String filePathName){
		SetDirectory setDirectory = new SetDirectory();
		if(setDirectory.setDirectory(filePathName)){
			filename = filePathName;
			Write write = new Write();
			write.updatePathSentence(filePathName);
			return true;
		} else{
			return false;
		}
	}
	
	private String checkFileExists(String filename) {
		String outcome;
		File file = new File(filename);

		if (file.exists()) {
			outcome = success;
		} else {
			outcome = failure;
		}
		return outcome;
	}
	
	private void updateFilenameIfPathExists() {
		try {
			BufferedReader read = new BufferedReader(new FileReader(defaultFilename));
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
			e.printStackTrace();
		}
	}
	
	private void updateFilenameVariable (String absolutePath) {
		filename = absolutePath;
	}
}