package Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Write {
	private PrintWriter print;
	private PrintWriter printPath;
	private BufferedReader reader;
	private File tempFile;
	private String success = "Success";
	private String failure = "Failure";
	private String pathVariable = "PATH:";
	
	// Applicable if filename == Constants.fileName
	public String writeToFile(String[] taskList) {
		try {
			print = new PrintWriter(new FileWriter(Storage.filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String status = checkFileExists(Storage.filename);
		if (status.equals(success)) {
			printTaskDetails(print, taskList);
		}
		
		print.close();
		return status;
	}
	
	// Applicable if filename != Constants.fileName
	public String writeToFile (String filePathName, String[] taskList) {
		try {
			print = new PrintWriter(new FileWriter(Storage.filename));
			updatePathSentence(filePathName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String status = checkFileExists(Storage.filename);
		
		if(status.equals(success)) {
			printTaskDetails(print, taskList);
		}
		
		print.close();
		return status;
	}
	
	// Applicable for Retrieve without overwriting the Path found in the default text
	public void writeToFile(int index, String filePathName, String[] taskList) {
		try {
			print = new PrintWriter(new FileWriter(Storage.filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String status = checkFileExists(Storage.filename);
		
		if(status.equals(success)) {
			printTaskDetails(print, taskList);
		}
		
		print.close();
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
	
	private void printTaskDetails(PrintWriter print, String [] taskList) {
		for(int i = 0; i < taskList.length; i++) {
			print.println(taskList[i]);
		}
	}
	
	public void updatePathSentence (String filePathName) {
		try {
			tempFile = new File("temp.txt");
			printPath = new PrintWriter(new FileWriter("temp.txt"));
			reader = new BufferedReader(new FileReader(Storage.defaultFilename));
			copyFileContent(filePathName, printPath, reader);

			// Revert the copy back and delete the temp file
			printPath = new PrintWriter(new FileWriter(Storage.defaultFilename));
			reader = new BufferedReader(new FileReader("temp.txt"));
			copyFileContent(filePathName, printPath, reader);
			tempFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Add an additional "PATH: " sentence at the beginning of the file
	private void copyFileContent (String filePathName, PrintWriter printPath, BufferedReader read) {
		try {
			String content;
			String firstSentence = "PATH: " + filePathName;
			printPath.println(firstSentence);

			while ((content = read.readLine()) != null) {
				String path = content.substring(0, content.indexOf(" "));
				if(!(path.equals(pathVariable))) {
					printPath.println(content);
				} 
			}
			printPath.close();
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
